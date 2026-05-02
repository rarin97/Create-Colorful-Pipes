package net.rarin.colorfulpipes.compat.Create_Connected.content;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlockEntity;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.platform.NeoForgeCatnipServices;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.rarin.colorfulpipes.CCPPartialModels;

public class ColorfulFluidVesselRenderer extends FluidVesselRenderer {

	public ColorfulFluidVesselRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	protected void renderSafe(FluidVesselBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		if (be.isController()) {
			if (!be.hasWindow()) {
				if (be.boiler.isActive()) {
					this.renderAsBoiler(be, partialTicks, ms, buffer, light, overlay);
				}

			} else {
				LerpedFloat fluidLevel = be.getFluidLevel();
				if (fluidLevel != null) {
					float capSize = 0.25F;
					float tankHullSize = 0.0703125F;
					float minPuddleHeight = 0.0625F;
					float totalHeight = (float)be.getWidth() - 2.0F * tankHullSize - minPuddleHeight;
					float level = fluidLevel.getValue(partialTicks);
					if (!(level < 1.0F / (512.0F * totalHeight))) {
						float clampedLevel = Mth.clamp(level * totalHeight, 0.0F, totalHeight);
						FluidTank tank = be.getTankInventory();
						FluidStack fluidStack = tank.getFluid();
						if (!fluidStack.isEmpty()) {
							boolean top = fluidStack.getFluid().getFluidType().isLighterThanAir();
							Direction.Axis axis = be.getAxis();
							float xMin = axis == Axis.X ? capSize : tankHullSize;
							float xMax = axis == Axis.X ? xMin + (float)be.getHeight() - 2.0F * capSize : xMin + (float)be.getWidth() - 2.0F * tankHullSize;
							float yMin = totalHeight + tankHullSize + minPuddleHeight - clampedLevel;
							float yMax = yMin + clampedLevel;
							if (top) {
								yMin += totalHeight - clampedLevel;
								yMax += totalHeight - clampedLevel;
							}

							float zMin = axis == Axis.Z ? capSize : tankHullSize;
							float zMax = axis == Axis.Z ? zMin + (float)be.getHeight() - 2.0F * capSize : zMin + (float)be.getWidth() - 2.0F * tankHullSize;
							ms.pushPose();
							ms.translate(0.0F, clampedLevel - totalHeight, 0.0F);
							NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(fluidStack, xMin, yMin, zMin, xMax, yMax, zMax, buffer, ms, light, false, true);
							ms.popPose();
						}
					}
				}
			}
		}
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
