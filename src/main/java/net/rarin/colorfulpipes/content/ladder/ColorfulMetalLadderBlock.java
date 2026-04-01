package net.rarin.colorfulpipes.content.ladder;

import com.simibubi.create.content.decoration.MetalLadderBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlocks;

public class ColorfulMetalLadderBlock extends MetalLadderBlock {

	protected final DyeColor color;

	public ColorfulMetalLadderBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos,
								 Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color)
				return InteractionResult.PASS;

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				level.setBlock(pos, CCPBlocks.COLORFUL_COPPER_LADDER.get(dyeColor).getDefaultState()
						.setValue(FACING, state.getValue(FACING))
						.setValue(WATERLOGGED, state.getValue(WATERLOGGED)), 3);
			}

			return InteractionResult.SUCCESS;
		}

		return super.use(state, level, pos, player, hand, hit);
	}
}
