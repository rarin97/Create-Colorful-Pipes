package net.rarin.colorfulpipes.content.casing;

import com.simibubi.create.content.decoration.encasing.CasingBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.content.encasedshaft.CopperGlassEncasedShaftBlock;

public class CopperCasingGlassBlock extends CasingBlock {
	public CopperCasingGlassBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
		return adjacentBlockState.getBlock() instanceof CopperCasingGlassBlock || adjacentBlockState.getBlock() instanceof CopperGlassEncasedShaftBlock
				|| super.skipRendering(state, adjacentBlockState, side);
	}

	@Override
	public float getShadeBrightness(BlockState state, BlockGetter getter, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter getter, BlockPos pos) {
		return true;
	}

//	@Override
//		public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//		ItemStack stack = player.getItemInHand(hand);
//
//		if (stack.getItem() instanceof DyeItem dye) {
//			DyeColor dyeColor = dye.getDyeColor();
//
//			if (!level.isClientSide) {
//				level.levelEvent(2001, pos, Block.getId(state));
//
//				level.setBlock(pos, CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(dyeColor).getDefaultState(),3);
//			}
//			return InteractionResult.SUCCESS;
//		}
//
//		return super.use(state, level, pos, player, hand, hit);
//	}
}
