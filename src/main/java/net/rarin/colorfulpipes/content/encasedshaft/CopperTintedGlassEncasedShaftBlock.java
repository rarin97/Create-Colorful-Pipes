package net.rarin.colorfulpipes.content.encasedshaft;

import java.util.function.Supplier;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPPaletteBlocks;

import net.rarin.colorfulpipes.content.casing.CopperCasingTintedGlassBlock;

import org.jetbrains.annotations.NotNull;

public class CopperTintedGlassEncasedShaftBlock extends EncasedShaftBlock {
	public CopperTintedGlassEncasedShaftBlock(Properties properties, Supplier<Block> casing) {
		super(properties, casing);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
		return adjacentBlockState.getBlock() instanceof CopperTintedGlassEncasedShaftBlock || adjacentBlockState.getBlock() instanceof CopperCasingTintedGlassBlock
				|| super.skipRendering(state, adjacentBlockState, side);
	}

	@Override
	public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
		return false;
	}

	@Override
	public int getLightBlock(@NotNull BlockState state, BlockGetter getter, @NotNull BlockPos pos) {
		return getter.getMaxLightLevel();
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				level.setBlock(pos, CCPPaletteBlocks.COLORFUL_TINTED_COPPER_GLASS_ENCASED_SHAFT.get(dyeColor).getDefaultState()
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
