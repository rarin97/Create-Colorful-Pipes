package net.rarin.colorfulpipes.content.drain;

import com.simibubi.create.content.fluids.drain.ItemDrainBlock;
import com.simibubi.create.content.fluids.drain.ItemDrainBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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

public class ColorfulDrainBlock extends ItemDrainBlock {

	protected final DyeColor color;

	public ColorfulDrainBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public 	ItemInteractionResult useItemOn(ItemStack stack,BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color)
				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				ItemDrainBlockEntity oldDrain = (ItemDrainBlockEntity) level.getBlockEntity(pos);
				net.minecraft.nbt.CompoundTag oldData = null;
				if (oldDrain != null) {
					oldData = new net.minecraft.nbt.CompoundTag();
					oldDrain.saveAdditional(oldData, level.registryAccess());
				}

				level.setBlock(pos, CCPBlocks.COLORFUL_DRAINS.get(dyeColor).getDefaultState(), 3);

				ItemDrainBlockEntity newDrain = (ItemDrainBlockEntity) level.getBlockEntity(pos);
				if (oldData != null && newDrain != null) {
					newDrain.loadWithComponents(oldData, level.registryAccess());
				}
			}
			return ItemInteractionResult.SUCCESS;
		}
		 return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	public BlockEntityType<? extends ItemDrainBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_DRAINS.get();
	}
}

