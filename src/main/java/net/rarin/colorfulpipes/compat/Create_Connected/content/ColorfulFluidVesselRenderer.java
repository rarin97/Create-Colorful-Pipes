package net.rarin.colorfulpipes.compat.Create_Connected.content;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlockEntity;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.CCPPartialModels;

public class ColorfulFluidVesselRenderer extends FluidVesselRenderer {

	public ColorfulFluidVesselRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	protected void renderAsBoiler(FluidVesselBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		ColorfulFluidVesselBlock block = (ColorfulFluidVesselBlock) be.getBlockState().getBlock();
		DyeColor color = block.getColor();

		BlockState blockState = be.getBlockState();
		VertexConsumer vb = buffer.getBuffer(RenderType.cutout());
		ms.pushPose();
		var msr = TransformStack.of(ms);
		Axis axis = be.getAxis();
		msr.translate(axis == Axis.X ? be.getHeight() / 2f : be.getWidth() / 2f, 0.5, axis == Axis.Z ? be.getHeight() / 2f : be.getWidth() / 2f);

		float dialPivotY = 6f / 16;
		float dialPivotZ = 8f / 16;
		float progress = be.boiler.gauge.getValue(partialTicks);

		for (Direction d : Iterate.horizontalDirections) {
			if (be.boiler.occludedDirections[d.get2DDataValue()])
				continue;
			if (d.getAxis() != axis)
				continue;
			ms.pushPose();
			float yRot = -d.toYRot() - 90;
			CachedBuffers.partial(CCPPartialModels.COLORFUL_BOILER_GAUGE.get(color), blockState)
					.rotateYDegrees(yRot)
					.uncenter()
					.translate(be.getWidth() / 2f - 6 / 16f, 0, 0)
					.light(light)
					.renderInto(ms, vb);
			CachedBuffers.partial(CCPPartialModels.COLORFUL_BOILER_GAUGE_DIAL.get(color), blockState)
					.rotateYDegrees(yRot)
					.uncenter()
					.translate(be.getWidth() / 2f - 6 / 16f, 0, 0)
					.translate(0, dialPivotY, dialPivotZ)
					.rotateXDegrees(-145 * progress + 90)
					.translate(0, -dialPivotY, -dialPivotZ)
					.light(light)
					.renderInto(ms, vb);
			ms.popPose();
		}

		ms.popPose();
	}
}
