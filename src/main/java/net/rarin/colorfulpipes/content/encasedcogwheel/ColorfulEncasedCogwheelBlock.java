package net.rarin.colorfulpipes.content.encasedcogwheel;

import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;

import net.minecraft.core.BlockPos;
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
import net.rarin.colorfulpipes.CCPPaletteBlocks;

import java.util.function.Supplier;

public class ColorfulEncasedCogwheelBlock extends EncasedCogwheelBlock {

	protected final DyeColor color;

	public ColorfulEncasedCogwheelBlock(Properties properties, boolean large,  DyeColor color,  Supplier<Block> casing) {
		super(properties, large, casing);
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

				level.setBlock(pos,(isLarge ? CCPPaletteBlocks.COLORFUL_COPPER_ENCASED_LARGE_COGWHEEL.get(dyeColor)
						: CCPPaletteBlocks.COLORFUL_COPPER_ENCASED_COGWHEEL.get(dyeColor)).getDefaultState()
						.setValue(AXIS, state.getValue(AXIS)).setValue(TOP_SHAFT, state.getValue(TOP_SHAFT))
						.setValue(BOTTOM_SHAFT, state.getValue(BOTTOM_SHAFT)), 3);
			}

			return ItemInteractionResult.SUCCESS;
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	@Override
	public BlockEntityType<? extends SimpleKineticBlockEntity> getBlockEntityType() {
		return isLarge ? CCPBlockEntityTypes.ENCASED_LARGE_COGWHEEL.get() : CCPBlockEntityTypes.ENCASED_COGWHEEL.get();
	}
}
