package net.rarin.colorfulpipes.mixin.dyed;

import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlock;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.compat.Create_Connected.content.ColorfulFluidVesselBlock;
import net.rarin.colorfulpipes.compat.Mods;
import net.rarin.colorfulpipes.config.CCPConfigs;
import net.rarin.colorfulpipes.content.pipe.ColorfulFluidPipeBlock;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidPipeBlock.class)
public class FluidPipeBlockMixin {

	@Unique
	private static final ThreadLocal<BlockState> CURRENT_PIPE_STATE = new ThreadLocal<>();

	@Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
	private void useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
						   BlockHitResult hitResult, CallbackInfoReturnable<ItemInteractionResult> cir) {

		if (!CCPConfigs.server().recipes.enablePipeItemApplication.get())
			return;

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				BlockState newState  = CCPBlocks.COLORFUL_FLUID_PIPES.get(dyeColor).getDefaultState()
						.setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED));

				Direction firstFound = Direction.UP;
				for (Direction d : Iterate.directions)
					if (state.getValue(FluidPipeBlock.PROPERTY_BY_DIRECTION.get(d))) {
						firstFound = d;
						break;
					}

				FluidTransportBehaviour.cacheFlows(level, pos);

				level.setBlockAndUpdate(pos, CCPBlocks.COLORFUL_FLUID_PIPES.get(dyeColor).get()
						.updateBlockState(newState , firstFound, null, level, pos));

				FluidTransportBehaviour.loadFlows(level, pos);

				if (!player.isCreative())
					stack.shrink(1);
			}
			cir.setReturnValue(ItemInteractionResult.SUCCESS);
		}
	}

	@Unique
	private static boolean checkConfigs(BlockState neighbour) {
		if (neighbour.getBlock() instanceof ColorfulFluidPipeBlock)
			return !CCPConfigs.server().fluids.MixedPipeConnections.get();
		if (neighbour.getBlock() instanceof ColorfulFluidTankBlock)
			return !CCPConfigs.server().fluids.MixedTankConnections.get();
		if (Mods.CREATE_CONNECTED.isLoaded() && neighbour.getBlock() instanceof ColorfulFluidVesselBlock)
			return !CCPConfigs.server().fluids.MixedVesselConnections.get();
		return false;
	}

	@Unique
	private static DyeColor getColor(BlockState state) {
		if (state.getBlock() instanceof ColorfulFluidPipeBlock pipe)
			return pipe.getColor();
		if (state.getBlock() instanceof ColorfulFluidTankBlock tank)
			return tank.getColor();
		if (Mods.CREATE_CONNECTED.isLoaded() && state.getBlock() instanceof ColorfulFluidVesselBlock vessel)
			return vessel.getColor();
		return null;
	}

	@Inject(method = "updateBlockState", at = @At("HEAD"))
	private void captureCurrentPipeState(BlockState state, Direction preferredDirection, Direction ignore, BlockAndTintGetter world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
		CURRENT_PIPE_STATE.set(state);
	}

	@Inject(method = "updateBlockState", at = @At("RETURN"))
	private void clearCurrentPipeState(BlockState state, Direction preferredDirection, Direction ignore, BlockAndTintGetter world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
		CURRENT_PIPE_STATE.remove();
	}

	@Inject(method = "canConnectTo", at = @At("HEAD"), cancellable = true)
	private static void blockColorConnections(BlockAndTintGetter world, BlockPos neighbourPos, BlockState neighbour,
												   Direction direction, CallbackInfoReturnable<Boolean> cir) {
		BlockState state = CURRENT_PIPE_STATE.get();
		if (state == null)
			state = world.getBlockState(neighbourPos.relative(direction.getOpposite()));
		if (!checkConfigs(neighbour))
			return;
		DyeColor color = getColor(state);
		DyeColor neighbourColor = getColor(neighbour);
		if (color != null && neighbourColor != null && color != neighbourColor) {
			cir.setReturnValue(false);
		}
	}
}
