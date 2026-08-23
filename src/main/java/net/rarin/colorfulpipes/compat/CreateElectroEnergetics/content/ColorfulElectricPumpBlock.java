package net.rarin.colorfulpipes.compat.CreateElectroEnergetics.content;

import com.george_vi.electroenergetics.content.electric_pump.ElectricPumpBlock;

import com.george_vi.electroenergetics.content.electric_pump.ElectricPumpBlockEntity;

import com.simibubi.create.content.fluids.FluidPropagator;

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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.compat.CreateElectroEnergetics.CEEBlockEntityTypes;
import net.rarin.colorfulpipes.compat.CreateElectroEnergetics.CEEBlocks;

public class ColorfulElectricPumpBlock extends ElectricPumpBlock {

	protected final DyeColor color;

	public ColorfulElectricPumpBlock(Properties properties, DyeColor color) {
		super(properties);
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

				BlockState newState = CEEBlocks.COLORFUL_ELECTRIC_PUMPS.get(dyeColor).getDefaultState()
						.setValue(FACING, state.getValue(FACING))
						.setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED));

				level.setBlock(pos, newState, Block.UPDATE_ALL);
				FluidPropagator.propagateChangedPipe(level, pos, newState);
			}
			return ItemInteractionResult.SUCCESS;
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	@Override
	public BlockEntityType<? extends ElectricPumpBlockEntity> getBlockEntityType() {
		return CEEBlockEntityTypes.ELECTRIC_PUMP.get();
	}
}
