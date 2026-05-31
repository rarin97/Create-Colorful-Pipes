//package net.rarin.colorfulpipes.content.tank.oldTank;
//
//import java.util.ArrayDeque;
//import java.util.HashSet;
//import java.util.Queue;
//import java.util.Set;
//
//import com.simibubi.create.api.connectivity.ConnectivityHandler;
//import com.simibubi.create.content.fluids.tank.FluidTankBlock;
//import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.ItemInteractionResult;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.DyeColor;
//import net.minecraft.world.item.DyeItem;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.BlockHitResult;
//import net.rarin.colorfulpipes.CCPBlockEntityTypes;
//import net.rarin.colorfulpipes.CCPBlocks;
//import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankBlockEntity;
//
//public class ColorfulFluidTankBlock extends FluidTankBlock {
//
//	protected final DyeColor color;
//
//	public ColorfulFluidTankBlock(Properties properties, DyeColor color) {
//		super(properties, false);
//		this.color = color;
//	}
//
//	public DyeColor getColor() {
//		return color;
//	}
//
//	@Override
//	public 	ItemInteractionResult useItemOn(ItemStack stack,BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//
//		if (stack.getItem() instanceof DyeItem dye) {
//			DyeColor dyeColor = dye.getDyeColor();
//
//			if (dyeColor == this.color)
//				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
//
//            if (!level.isClientSide) {
//				level.levelEvent(2001, pos, Block.getId(state));
//
//                if (player.isShiftKeyDown()) {
//                    level.setBlock(pos, CCPBlocks.COLORFUL_FLUID_TANKS.get(dyeColor).getDefaultState(), 3);
//                    return ItemInteractionResult.SUCCESS;
//                }
//
//                ColorfulFluidTankBlockEntity controller =
//						ConnectivityHandler.partAt(CCPBlockEntityTypes.COLORFUL_FLUID_TANKS.get(this.color).get(), level, pos);
//
//				if (controller == null)
//					return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
//
//				BlockPos controllerPos = controller.getController();
//
//				Set<BlockPos> tanks = new HashSet<>();
//				Queue<BlockPos> queue = new ArrayDeque<>();
//
//				queue.add(pos);
//				tanks.add(pos);
//
//				while (!queue.isEmpty()) {
//					BlockPos current = queue.poll();
//
//					for (Direction dir : Direction.values()) {
//						BlockPos next = current.relative(dir);
//
//						if (tanks.contains(next))
//							continue;
//
//						BlockEntity be = level.getBlockEntity(next);
//
//						if (!(be instanceof ColorfulFluidTankBlockEntity tank))
//							continue;
//
//
//						if (!tank.getController().equals(controllerPos))
//							continue;
//
//						tanks.add(next);
//						queue.add(next);
//					}
//				}
//
//				for (BlockPos p : tanks) {
//					level.setBlock(p, CCPBlocks.COLORFUL_FLUID_TANKS.get(dyeColor).getDefaultState(), 3);
//				}
//			}
//			return ItemInteractionResult.SUCCESS;
//		}
//
//
////		if (stack.is(Items.SHEARS.asItem())) {
////
////			if (!level.isClientSide) {
////
////				ColorfulFluidTankBlockEntity controller =
////						ConnectivityHandler.partAt(CCPBlockEntityTypes.COLORFUL_FLUID_TANKS.get(this.color).get(), level, pos);
////
////				if (controller == null)
////					return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
////
////				BlockPos controllerPos = controller.getController();
////
////				Set<BlockPos> visited = new HashSet<>();
////				Queue<BlockPos> queue = new ArrayDeque<>();
////
////				queue.add(pos);
////				visited.add(pos);
////
////				while (!queue.isEmpty()) {
////					BlockPos current = queue.poll();
////
////					for (Direction dir : Direction.values()) {
////						BlockPos next = current.relative(dir);
////
////						if (visited.contains(next))
////							continue;
////
////						BlockEntity be = level.getBlockEntity(next);
////
////						if (!(be instanceof ColorfulFluidTankBlockEntity tank))
////							continue;
////
////
////						if (!tank.getController().equals(controllerPos))
////							continue;
////
////						visited.add(next);
////						queue.add(next);
////					}
////				}
////
////				level.levelEvent(2001, pos, Block.getId(state));
////
////				for (BlockPos p : visited) {
////					level.setBlock(p, CCPBlocks.COLORFUL_FLUID_TANKS_TEST.get(this.color).getDefaultState(), 3);
////				}
////
////				if (!player.isCreative())
////					stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
////			}
////			return ItemInteractionResult.SUCCESS;
////		}
//		 return super.useItemOn(stack, state, level, pos, player, hand, hit);
//	}
//
//	public BlockEntityType<? extends FluidTankBlockEntity> getBlockEntityType() {
//		return CCPBlockEntityTypes.COLORFUL_FLUID_TANKS.get(color).get();
//	}
//}
