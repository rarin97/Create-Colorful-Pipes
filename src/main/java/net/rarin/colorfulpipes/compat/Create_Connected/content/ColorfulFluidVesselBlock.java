package net.rarin.colorfulpipes.compat.Create_Connected.content;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlockEntity;

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
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.compat.Create_Connected.CCBlockEntityTypes;
import net.rarin.colorfulpipes.compat.Create_Connected.CCBlocks;

public class ColorfulFluidVesselBlock extends FluidVesselBlock {

	protected final DyeColor color;

	public ColorfulFluidVesselBlock(Properties properties, DyeColor color) {
		super(properties, false);
		this.color = color;
	}

	public DyeColor getColor() {
		return color;
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color)
				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				level.setBlock(pos, CCBlocks.COLORFUL_FLUID_VESSELS.get(dyeColor).getDefaultState()
						.setValue(AXIS, state.getValue(AXIS)), 3);

			}
			return ItemInteractionResult.SUCCESS;
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	@Override
	public BlockEntityType<? extends FluidVesselBlockEntity> getBlockEntityType() {
		return CCBlockEntityTypes.COLORFUL_FLUID_VESSELS.get(color).get();
	}
}
