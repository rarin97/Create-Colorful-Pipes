package net.rarin.colorfulpipes.content.encasedcogwheel;

import java.util.function.Supplier;

import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;

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
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPPaletteBlocks;

public class CopperEncasedCogwheelBlock extends EncasedCogwheelBlock {
	public CopperEncasedCogwheelBlock(Properties properties, boolean large, Supplier<Block> casing) {
		super(properties, large, casing);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
	ItemStack stack = player.getItemInHand(hand);

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				level.setBlock(pos,(isLarge ? CCPPaletteBlocks.COLORFUL_COPPER_ENCASED_LARGE_COGWHEEL.get(dyeColor)
						: CCPPaletteBlocks.COLORFUL_COPPER_ENCASED_COGWHEEL.get(dyeColor)).getDefaultState()
						.setValue(AXIS, state.getValue(AXIS)).setValue(TOP_SHAFT, state.getValue(TOP_SHAFT))
						.setValue(BOTTOM_SHAFT, state.getValue(BOTTOM_SHAFT)), 3);
			}

			return InteractionResult.SUCCESS;
		}

		return super.use(state, level, pos, player, hand, hit);
	}

	@Override
	public BlockEntityType<? extends SimpleKineticBlockEntity> getBlockEntityType() {
		return isLarge ? CCPBlockEntityTypes.ENCASED_LARGE_COGWHEEL.get() : CCPBlockEntityTypes.ENCASED_COGWHEEL.get();
	}
}
