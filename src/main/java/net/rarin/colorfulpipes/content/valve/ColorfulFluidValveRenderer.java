package net.rarin.colorfulpipes.content.valve;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.fluids.pipes.valve.FluidValveBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.CCPPartialModels;

public class ColorfulFluidValveRenderer extends KineticBlockEntityRenderer<ColorfulFluidValveBlockEntity> {

	public ColorfulFluidValveRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(ColorfulFluidValveBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
							  int light, int overlay) {

		if (VisualizationManager.supportsVisualization(be.getLevel())) return;

		super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
		BlockState blockState = be.getBlockState();
		DyeColor color = ((ColorfulFluidValveBlock) blockState.getBlock()).color;
		SuperByteBuffer pointer = CachedBuffers.partial(CCPPartialModels.COLORFUL_FLUID_VALVE_POINTER.get(color), blockState);
		Direction facing = blockState.getValue(FluidValveBlock.FACING);

		float pointerRotation = Mth.lerp(be.pointer.getValue(partialTicks), 0, -90);
		Direction.Axis pipeAxis = FluidValveBlock.getPipeAxis(blockState);
		Direction.Axis shaftAxis = getRotationAxisOf(be);

		int pointerRotationOffset = 0;
		if (pipeAxis.isHorizontal() && shaftAxis == Direction.Axis.X || pipeAxis.isVertical())
			pointerRotationOffset = 90;

		pointer.center()
				.rotateYDegrees(AngleHelper.horizontalAngle(facing))
				.rotateXDegrees(facing == Direction.UP ? 0 : facing == Direction.DOWN ? 180 : 90)
				.rotateYDegrees(pointerRotationOffset + pointerRotation)
				.uncenter()
				.light(light)
				.renderInto(ms, buffer.getBuffer(RenderType.solid()));
	}

	@Override
	protected BlockState getRenderedBlockState(ColorfulFluidValveBlockEntity be) {
		return shaft(getRotationAxisOf(be));
	}
}
