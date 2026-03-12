package net.rarin.colorfulpipes.content.valve;

import com.simibubi.create.content.fluids.pipes.valve.FluidValveRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class ColorfulFluidValveRenderer extends FluidValveRenderer {
	public ColorfulFluidValveRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

//	@Override
//	protected void renderSafe(FluidValveBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
//							  int light, int overlay) {
//
//		if (VisualizationManager.supportsVisualization(be.getLevel())) return;
//
//		super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
//		BlockState blockState = be.getBlockState();
//		ColorfulFluidValveBlock block = (ColorfulFluidValveBlock) blockState.getBlock();
//		DyeColor color = block.getColor();
//		SuperByteBuffer pointer = CachedBuffers.partial(CCPPartialModels.COLORFUL_FLUID_VALVE_POINTER.get(color), blockState);
//		Direction facing = blockState.getValue(FluidValveBlock.FACING);
//
//		//float pointerRotation = Mth.lerp(be.pointer.getValue(partialTicks), 0, -90);
//		Direction.Axis pipeAxis = FluidValveBlock.getPipeAxis(blockState);
//		Direction.Axis shaftAxis = getRotationAxisOf(be);
//
//		int pointerRotationOffset = 0;
//		if (pipeAxis.isHorizontal() && shaftAxis == Direction.Axis.X || pipeAxis.isVertical())
//			pointerRotationOffset = 90;
//
//		pointer.center()
//				.rotateYDegrees(AngleHelper.horizontalAngle(facing))
//				.rotateXDegrees(facing == Direction.UP ? 0 : facing == Direction.DOWN ? 180 : 90)
//				.rotateYDegrees(pointerRotationOffset + pointerRotation)
//				.uncenter()
//				.light(light)
//				.renderInto(ms, buffer.getBuffer(RenderType.solid()));
//	}
}
