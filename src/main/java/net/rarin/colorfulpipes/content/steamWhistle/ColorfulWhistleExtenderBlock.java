package net.rarin.colorfulpipes.content.steamWhistle;

import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.content.decoration.steamWhistle.WhistleExtenderBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPTags;

public class ColorfulWhistleExtenderBlock extends WhistleExtenderBlock {

	public ColorfulWhistleExtenderBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		ItemStack stack = player.getItemInHand(hand);

		if (player == null)
			return InteractionResult.PASS;

		if (!(stack.getItem() instanceof BlockItem blockItem
				&& blockItem.getBlock() instanceof WhistleBlock))
			return InteractionResult.PASS;

		BlockPos findRoot = findRoot(level, pos);
		BlockState blockState = level.getBlockState(findRoot);

		if (blockState.getBlock() instanceof ColorfulWhistleBlock whistle) {
			return whistle.use(blockState, level, findRoot, player, hand,
					new BlockHitResult(hitResult.getLocation(), hitResult.getDirection(), findRoot, hitResult.isInside()));
		}
		if (blockState.getBlock() instanceof WhistleBlock) {
			if (stack.is(CCPTags.ColorfulItemTags.COLORFUL_STEAM_WHISTLES.tag)) {
				ColorfulWhistleBlock.incrementSize(level, findRoot);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

	public static BlockPos findRoot(LevelAccessor pLevel, BlockPos pPos) {
		BlockPos currentPos = pPos.below();
		while (true) {
			BlockState blockState = pLevel.getBlockState(currentPos);
			if (blockState.getBlock() instanceof WhistleExtenderBlock) {
				currentPos = currentPos.below();
				continue;
			}
			return currentPos;
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState below = level.getBlockState(pos.below());

		return (below.getBlock() instanceof WhistleExtenderBlock
				&& below.hasProperty(SHAPE)
				&& below.getValue(SHAPE) != WhistleExtenderShape.SINGLE)
				|| below.getBlock() instanceof WhistleBlock;
	}
}
