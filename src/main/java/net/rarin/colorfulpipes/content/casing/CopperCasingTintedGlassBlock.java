package net.rarin.colorfulpipes.content.casing;

import com.simibubi.create.content.decoration.encasing.CasingBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import net.rarin.colorfulpipes.content.encasedshaft.CopperTintedGlassEncasedShaftBlock;

import org.jetbrains.annotations.NotNull;

public class CopperCasingTintedGlassBlock extends CasingBlock {
	public CopperCasingTintedGlassBlock(Properties properties) {
		super(properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
		return adjacentBlockState.getBlock() instanceof CopperCasingTintedGlassBlock || adjacentBlockState.getBlock() instanceof CopperTintedGlassEncasedShaftBlock
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
}
