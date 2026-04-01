package net.rarin.colorfulpipes.content.ScaffoldingBlock;

import com.simibubi.create.content.decoration.MetalScaffoldingBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlocks;

public class ColorfulMetalScaffoldingBlock extends MetalScaffoldingBlock {

	protected final DyeColor color;

	public ColorfulMetalScaffoldingBlock(Properties Properties, DyeColor color) {
		super(Properties);
		this.color = color;
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color)
				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				level.setBlock(pos, CCPBlocks.COLORFUL_COPPER_SCAFFOLD.get(dyeColor).getDefaultState()
						.setValue(BOTTOM, state.getValue(BOTTOM))
						.setValue(WATERLOGGED, state.getValue(WATERLOGGED)), 3);
			}
			return ItemInteractionResult.SUCCESS;
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}
}
