package net.rarin.colorfulpipes.content.tank.oldTank;//package net.rarin.colorfulpipes.content.tank;
//
//import com.simibubi.create.content.fluids.tank.FluidTankBlock;
//import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.ItemInteractionResult;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.DyeColor;
//import net.minecraft.world.item.DyeItem;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.BlockHitResult;
//import net.rarin.colorfulpipes.CCPBlockEntityTypes;
//import net.rarin.colorfulpipes.CCPBlocks;
//
//public class ColorfulFluidTankBlockTest extends FluidTankBlock {
//
//	protected final DyeColor color;
//
//	public ColorfulFluidTankBlockTest(Properties properties, DyeColor color) {
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
//			if (!level.isClientSide) {
//				level.levelEvent(2001, pos, Block.getId(state));
//
//				level.setBlock(pos, CCPBlocks.COLORFUL_FLUID_TANKS_TEST.get(dyeColor).getDefaultState(), 3);
//			}
//			return ItemInteractionResult.SUCCESS;
//		}
//
//		if (stack.is(Items.SHEARS.asItem())) {
//
//			if (!level.isClientSide) {
//				level.levelEvent(2001, pos, Block.getId(state));
//
//				level.setBlock(pos, CCPBlocks.COLORFUL_FLUID_TANKS.get(this.color).getDefaultState(), 3);
//
//				if (!player.isCreative())
//					stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
//
//			}
//			return ItemInteractionResult.SUCCESS;
//
//
//		}
//		return super.useItemOn(stack, state, level, pos, player, hand, hit);
//	}
//
//	public BlockEntityType<? extends FluidTankBlockEntity> getBlockEntityType() {
//		return CCPBlockEntityTypes.COLORFUL_FLUID_TANKS_TEST.get();
//	}
//}
