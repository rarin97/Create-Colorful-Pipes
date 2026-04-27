package net.rarin.colorfulpipes.content.casing;

import com.simibubi.create.content.decoration.encasing.CasingBlock;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPPaletteBlocks;
import net.rarin.colorfulpipes.content.encasedshaft.ColorfulTintedGlassEncasedShaftBlock;

public class ColorfulCasingTintedGlassBlock extends CasingBlock {

	protected final DyeColor color;

	public ColorfulCasingTintedGlassBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
		return adjacentBlockState.getBlock() instanceof ColorfulCasingTintedGlassBlock || adjacentBlockState.getBlock() instanceof ColorfulTintedGlassEncasedShaftBlock
				|| super.skipRendering(state, adjacentBlockState, side);
	}

	@Override
	public boolean propagatesSkylightDown( BlockState state, BlockGetter getter, BlockPos pos) {
		return false;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter getter, BlockPos pos) {
		return getter.getMaxLightLevel();
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color)
				return InteractionResult.PASS;

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				level.setBlock(pos, CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(dyeColor).getDefaultState(), 3);
			}
			return InteractionResult.SUCCESS;
		}

		return super.use (state, level, pos, player, hand, hit);
	}
}
