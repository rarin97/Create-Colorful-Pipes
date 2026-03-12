package net.rarin.colorfulpipes.content.spout;

import com.simibubi.create.content.fluids.spout.SpoutBlock;
import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;

import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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

public class ColorfulSpoutBlock extends SpoutBlock {

	protected final DyeColor color;

	public ColorfulSpoutBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	public DyeColor getColor() {
		return color;
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

				SpoutBlockEntity oldSpout = (SpoutBlockEntity) level.getBlockEntity(pos);
				CompoundTag oldData = null;
				if (oldSpout != null) {
					oldData = new CompoundTag();
					oldSpout.saveAdditional(oldData);
				}

				level.setBlock(pos, CCPBlocks.COLORFUL_SPOUTS.get(dyeColor).getDefaultState(), 3);

				SpoutBlockEntity newSpout = (SpoutBlockEntity) level.getBlockEntity(pos);
				if (oldData != null && newSpout != null) {
					newSpout.load(oldData);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return super.use(state, level, pos, player, hand, hit);
	}

	public BlockEntityType<? extends SpoutBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_SPOUTS.get();
	}
}
