package net.rarin.colorfulpipes.content.hosePulley;

import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlock;
import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPBlocks;

public class ColorfulHosePulleyBlock  extends HosePulleyBlock {

	protected final DyeColor color;

	public ColorfulHosePulleyBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	public DyeColor getColor() {
		return color;
	}

	@Override
	public 	ItemInteractionResult useItemOn(ItemStack stack,BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color)
				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				CompoundTag oldData = null;

				if (level.getBlockEntity(pos) instanceof HosePulleyBlockEntity oldPulley) {
					oldData = new CompoundTag();
					oldPulley.saveAdditional(oldData, level.registryAccess());
				}

				level.setBlock(pos,CCPBlocks.COLORFUL_HOSE_PULLEYS.get(dyeColor).getDefaultState()
								.setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)), 3);

				if (oldData != null &&
						level.getBlockEntity(pos) instanceof HosePulleyBlockEntity newPulley) {
					newPulley.loadWithComponents(oldData, level.registryAccess());
				}
			}

			return ItemInteractionResult.SUCCESS;
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	@Override
	public Class<HosePulleyBlockEntity> getBlockEntityClass() {
		return HosePulleyBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends HosePulleyBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_HOSE_PULLEYS.get();
	}

}
