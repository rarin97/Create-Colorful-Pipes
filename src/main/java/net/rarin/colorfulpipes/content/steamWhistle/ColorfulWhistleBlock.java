package net.rarin.colorfulpipes.content.steamWhistle;

import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlockEntity;
import com.simibubi.create.content.decoration.steamWhistle.WhistleExtenderBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPBlocks;

public class ColorfulWhistleBlock extends WhistleBlock {

	protected final DyeColor color;

	public ColorfulWhistleBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

		@Override
		public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		if (player == null)
				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

		if (stack.getItem() instanceof BlockItem blockItem
				&& blockItem.getBlock() instanceof WhistleBlock) {
			incrementSize(level, pos);
			return ItemInteractionResult.SUCCESS;
		}


		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color)
				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));
				level.setBlock(pos, CCPBlocks.COLORFUL_STEAM_WHISTLES.get(dyeColor).getDefaultState()
						.setValue(FACING, state.getValue(FACING)).setValue(SIZE, state.getValue(SIZE))
						.setValue(POWERED, state.getValue(POWERED)).setValue(WALL, state.getValue(WALL)), 3);
			}
			return ItemInteractionResult.SUCCESS;
		}
			return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	public static void incrementSize(LevelAccessor pLevel, BlockPos pPos) {
		BlockState base = pLevel.getBlockState(pPos);
		if (!base.hasProperty(SIZE))
			return;
		WhistleSize size = base.getValue(SIZE);
		SoundType soundtype = base.getSoundType();
		BlockPos currentPos = pPos.above();

		for (int i = 1; i <= 6; i++) {
			BlockState blockState = pLevel.getBlockState(currentPos);
			float pVolume = (soundtype.getVolume() + 1.0F) / 2.0F;
			SoundEvent growSound = SoundEvents.NOTE_BLOCK_XYLOPHONE.value();
			SoundEvent hitSound = soundtype.getHitSound();

			if (blockState.getBlock() instanceof WhistleExtenderBlock) {
				if (blockState.getValue(WhistleExtenderBlock.SHAPE) == WhistleExtenderBlock.WhistleExtenderShape.SINGLE) {
					pLevel.setBlock(currentPos,
							blockState.setValue(WhistleExtenderBlock.SHAPE, WhistleExtenderBlock.WhistleExtenderShape.DOUBLE), 3);
					if (soundtype != null) {
						float pPitch = (float) Math.pow(2, -(i * 2) / 12.0);
						pLevel.playSound(null, currentPos, growSound, SoundSource.BLOCKS, pVolume / 4f, pPitch);
						pLevel.playSound(null, currentPos, hitSound, SoundSource.BLOCKS, pVolume, pPitch);
					}
					return;
				}
				currentPos = currentPos.above();
				continue;
			}

			if (!blockState.canBeReplaced())
				return;

			pLevel.setBlock(currentPos, CCPBlocks.COLORFUL_STEAM_WHISTLE_EXTENSION.getDefaultState()
					.setValue(WhistleExtenderBlock.SIZE, size), 3);
			if (soundtype != null) {
				float pPitch = (float) Math.pow(2, -(i * 2 - 1) / 12.0);
				pLevel.playSound(null, currentPos, growSound, SoundSource.BLOCKS, pVolume / 4f, pPitch);
				pLevel.playSound(null, currentPos, hitSound, SoundSource.BLOCKS, pVolume, pPitch);
			}
			return;
		}
	}

	@Override
	public BlockEntityType<? extends WhistleBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_STEAM_WHISTLES.get();
	}
}
