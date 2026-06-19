package net.rarin.colorfulpipes.content.encasedPipe;

import java.util.function.Supplier;

import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.content.fluids.pipes.EncasedPipeBlock;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlockEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;

import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.content.casing.ColorfulCasingGlassBlock;

public class ColorfulEncasedGlassPipeBlock extends EncasedPipeBlock {

	protected final DyeColor color;

	public ColorfulEncasedGlassPipeBlock(Properties properties, DyeColor color, Supplier<Block> casing) {
		super(properties, casing);
		this.color = color;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
		return adjacentBlockState.getBlock() instanceof ColorfulCasingGlassBlock || adjacentBlockState.getBlock() instanceof ColorfulEncasedGlassPipeBlock
				|| super.skipRendering(state, adjacentBlockState, side);
	}

	@Override
	public float getShadeBrightness(BlockState state, BlockGetter getter, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter getter, BlockPos pos) {
		return true;
	}

	@Override
	public InteractionResult onWrenched(BlockState state, UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();

		if (level.isClientSide)
			return InteractionResult.SUCCESS;

		context.getLevel()
				.levelEvent(2001, context.getClickedPos(), Block.getId(state));
		BlockState equivalentPipe = transferSixWayProperties(state, CCPBlocks.COLORFUL_FLUID_PIPES.get(color).getDefaultState());

		Direction firstFound = Direction.UP;
		for (Direction d : Iterate.directions)
			if (state.getValue(FACING_TO_PROPERTY_MAP.get(d))) {
				firstFound = d;
				break;
			}

		FluidTransportBehaviour.cacheFlows(level, pos);
		level.setBlockAndUpdate(pos, CCPBlocks.COLORFUL_FLUID_PIPES.get(color).get()
				.updateBlockState(equivalentPipe, firstFound, null, level, pos));
		FluidTransportBehaviour.loadFlows(level, pos);
		return InteractionResult.SUCCESS;
	}

	@Override
	public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
		AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
	}

	public BlockEntityType<? extends FluidPipeBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_ENCASED_FLUID_PIPES.get();
	}
}
