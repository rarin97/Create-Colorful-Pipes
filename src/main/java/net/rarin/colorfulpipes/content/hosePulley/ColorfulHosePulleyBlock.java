package net.rarin.colorfulpipes.content.hosePulley;

import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlock;
import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlockEntity;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPBlocks;

public class ColorfulHosePulleyBlock extends HosePulleyBlock {

	protected final DyeColor color;

	public ColorfulHosePulleyBlock(Properties properties, DyeColor color) {
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

				HosePulleyBlockEntity oldPulley = (HosePulleyBlockEntity) level.getBlockEntity(pos);
				net.minecraft.nbt.CompoundTag oldData = null;
				if (oldPulley != null) {
					oldData = new net.minecraft.nbt.CompoundTag();
					oldPulley.saveAdditional(oldData);
				}

				level.setBlock(pos, CCPBlocks.COLORFUL_HOSE_PULLEYS.get(dyeColor).getDefaultState()
						.setValue(BlockStateProperties.HORIZONTAL_FACING, state.getValue(BlockStateProperties.HORIZONTAL_FACING)), 3);

				HosePulleyBlockEntity newPulley = (HosePulleyBlockEntity) level.getBlockEntity(pos);
				if (oldData != null && newPulley != null) {
					newPulley.load(oldData);
					}
			}
			return InteractionResult.SUCCESS;
		}
		return super.use(state, level, pos, player, hand, hit);
	}

	@Override
	public BlockEntityType<? extends HosePulleyBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_HOSE_PULLEYS.get();
	}
}
