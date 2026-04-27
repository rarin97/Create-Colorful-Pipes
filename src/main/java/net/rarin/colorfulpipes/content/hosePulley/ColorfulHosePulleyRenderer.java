package net.rarin.colorfulpipes.content.hosePulley;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlock;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.rarin.colorfulpipes.CCPPartialModels;

public class ColorfulHosePulleyRenderer extends AbstractPulleyRenderer<ColorfulHosePulleyBlockEntity> {

	public ColorfulHosePulleyRenderer(BlockEntityRendererProvider.Context context) {
		super(context, AllPartialModels.HOSE_HALF, AllPartialModels.HOSE_HALF_MAGNET);
	}

	@Override
	protected Direction.Axis getShaftAxis(ColorfulHosePulleyBlockEntity be) {
		return be.getBlockState()
				.getValue(HosePulleyBlock.HORIZONTAL_FACING)
				.getClockWise()
				.getAxis();
	}

	@Override
	protected PartialModel getCoil() {
		return AllPartialModels.HOSE_COIL;
	}

	@Override
	protected SuperByteBuffer renderRope(ColorfulHosePulleyBlockEntity be) {
		return CachedBuffers.partial(AllPartialModels.HOSE, be.getBlockState());
	}

	@Override
	protected SuperByteBuffer renderMagnet(ColorfulHosePulleyBlockEntity be) {
		DyeColor color = ((ColorfulHosePulleyBlock) be.getBlockState().getBlock()).color;
		return CachedBuffers.partial(CCPPartialModels.COLORFUL_HOSE_MAGNET.get(color), be.getBlockState());
	}

	protected SuperByteBuffer renderhalfMagnet(ColorfulHosePulleyBlockEntity be) {
		DyeColor color = ((ColorfulHosePulleyBlock) be.getBlockState().getBlock()).color;
		return CachedBuffers.partial(CCPPartialModels.COLORFUL_HOSE_HALF_MAGNET.get(color), be.getBlockState());
	}

	@Override
	protected float getOffset(ColorfulHosePulleyBlockEntity be, float partialTicks) {
		return be.getInterpolatedOffset(partialTicks);
	}

	@Override
	protected SpriteShiftEntry getCoilShift() {
		return AllSpriteShifts.HOSE_PULLEY_COIL;
	}

	@Override
	protected boolean isRunning(ColorfulHosePulleyBlockEntity be) {
		return true;
	}

}
