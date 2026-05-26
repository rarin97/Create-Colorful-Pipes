package net.rarin.colorfulpipes.compat.Create_Connected.content;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlockEntity;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselItem;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.equipment.symmetryWand.SymmetryWandItem;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.foundation.block.IBE;

import net.createmod.catnip.math.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public class ColorfulFluidVesselItem extends FluidVesselItem {
	public ColorfulFluidVesselItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.literal("Create: Connected")
				.withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));

		super.appendHoverText(stack, context, tooltip, flag);
	}


	public InteractionResult place(BlockPlaceContext ctx) {
		InteractionResult initialResult = super.place(ctx);
		if (!initialResult.consumesAction()) {
			return initialResult;
		} else {
			this.tryMultiPlace(ctx);
			return initialResult;
		}
	}

	protected boolean updateCustomBlockEntityTag(BlockPos blockPos, Level level, Player player, ItemStack itemStack, BlockState blockState) {
		MinecraftServer minecraftserver = level.getServer();
		if (minecraftserver == null) {
			return false;
		} else {
			CustomData blockEntityData = (CustomData)itemStack.get(DataComponents.BLOCK_ENTITY_DATA);
			if (blockEntityData != null) {
				CompoundTag nbt = blockEntityData.copyTag();
				nbt.remove("Luminosity");
				nbt.remove("Size");
				nbt.remove("Height");
				nbt.remove("Controller");
				nbt.remove("LastKnownPos");
				if (nbt.contains("TankContent")) {
					FluidStack fluid = FluidStack.parseOptional(minecraftserver.registryAccess(), nbt.getCompound("TankContent"));
					if (!fluid.isEmpty()) {
						fluid.setAmount(Math.min(FluidTankBlockEntity.getCapacityMultiplier(), fluid.getAmount()));
						nbt.put("TankContent", fluid.saveOptional(minecraftserver.registryAccess()));
					}
				}

				BlockEntity.addEntityType(nbt, ((IBE)this.getBlock()).getBlockEntityType());
				itemStack.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(nbt));
			}

			return super.updateCustomBlockEntityTag(blockPos, level, player, itemStack, blockState);
		}
	}

	private void tryMultiPlace(BlockPlaceContext ctx) {
		Player player = ctx.getPlayer();
		if (player != null) {
			if (!player.isShiftKeyDown()) {
				Direction face = ctx.getClickedFace();
				if (face.getAxis().isHorizontal()) {
					ItemStack stack = ctx.getItemInHand();
					Level world = ctx.getLevel();
					BlockPos pos = ctx.getClickedPos();
					BlockPos placedOnPos = pos.relative(face.getOpposite());
					BlockState placedOnState = world.getBlockState(placedOnPos);
					if (FluidVesselBlock.isVessel(placedOnState)) {
						if (!SymmetryWandItem.presentInHotbar(player)) {
							FluidVesselBlockEntity tankAt = ConnectivityHandler.partAt(((IBE)this.getBlock()).getBlockEntityType(), world, placedOnPos);
							if (tankAt != null) {
								FluidVesselBlockEntity controllerBE = tankAt.getControllerBE();
								if (controllerBE != null) {
									int width = controllerBE.getWidth();
									if (width != 1) {
										int tanksToPlace = 0;
										Direction.Axis vesselAxis = (Direction.Axis)placedOnState.getOptionalValue(FluidVesselBlock.AXIS).orElse((Direction.Axis) null);
										if (vesselAxis != null) {
											if (face.getAxis() == vesselAxis) {
												Direction vesselFacing = Direction.fromAxisAndDirection(vesselAxis, Direction.AxisDirection.POSITIVE);
												BlockPos startPos = face == vesselFacing.getOpposite() ? controllerBE.getBlockPos().relative(vesselFacing.getOpposite()) : controllerBE.getBlockPos().relative(vesselFacing, controllerBE.getHeight());
												if (VecHelper.getCoordinate(startPos, vesselAxis) == VecHelper.getCoordinate(pos, vesselAxis)) {
													for(int xOffset = 0; xOffset < width; ++xOffset) {
														for(int zOffset = 0; zOffset < width; ++zOffset) {
															BlockPos offsetPos = vesselAxis == Direction.Axis.X ? startPos.offset(0, xOffset, zOffset) : startPos.offset(xOffset, zOffset, 0);
															BlockState blockState = world.getBlockState(offsetPos);
															if (!FluidVesselBlock.isVessel(blockState)) {
																if (!blockState.canBeReplaced()) {
																	return;
																}

																++tanksToPlace;
															}
														}
													}

													if (player.isCreative() || stack.getCount() >= tanksToPlace) {
														for(int xOffset = 0; xOffset < width; ++xOffset) {
															for(int zOffset = 0; zOffset < width; ++zOffset) {
																BlockPos offsetPos = vesselAxis == Direction.Axis.X ? startPos.offset(0, xOffset, zOffset) : startPos.offset(xOffset, zOffset, 0);
																BlockState blockState = world.getBlockState(offsetPos);
																if (!FluidVesselBlock.isVessel(blockState)) {
																	BlockPlaceContext context = BlockPlaceContext.at(ctx, offsetPos, face);
																	player.getPersistentData().putBoolean("SilenceVesselSound", true);
																	super.place(context);
																	player.getPersistentData().remove("SilenceVesselSound");
																}
															}
														}

													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
