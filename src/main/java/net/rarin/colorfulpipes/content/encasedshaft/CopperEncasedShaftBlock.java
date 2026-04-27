package net.rarin.colorfulpipes.content.encasedshaft;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPPaletteBlocks;

import java.util.function.Supplier;

public class CopperEncasedShaftBlock extends EncasedShaftBlock {
	public CopperEncasedShaftBlock(Properties properties, Supplier<Block> casing) {
		super(properties, casing);
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				level.setBlock(pos, CCPPaletteBlocks.COLORFUL_COPPER_ENCASED_SHAFT.get(dyeColor).getDefaultState()
						.setValue(AXIS, state.getValue(AXIS)), 3);
			}
			return ItemInteractionResult.SUCCESS;
		}


		if(stack.is(Tags.Items.GLASS_BLOCKS_COLORLESS)) {

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				level.setBlock(pos, CCPPaletteBlocks.COPPER_GLASS_ENCASED_SHAFT.getDefaultState()
						.setValue(AXIS, state.getValue(AXIS)), 3);
			}
			return ItemInteractionResult.SUCCESS;
		}

		if(stack.is(Items.TINTED_GLASS)) {

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				level.setBlock(pos, CCPPaletteBlocks.COPPER_TINTED_GLASS_ENCASED_SHAFT.getDefaultState()
						.setValue(AXIS, state.getValue(AXIS)), 3);
			}
			return ItemInteractionResult.SUCCESS;
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	@Override
	public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.ENCASED_SHAFT.get();
	}
}
