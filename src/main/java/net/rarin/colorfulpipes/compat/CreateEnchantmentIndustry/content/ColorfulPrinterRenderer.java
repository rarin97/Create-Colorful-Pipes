package net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.platform.NeoForgeCatnipServices;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.rarin.colorfulpipes.CCPPartialModels;
import net.rarin.colorfulpipes.mixin.accessor.PrinterBlockEntityAccessor;
import plus.dragons.createenchantmentindustry.client.model.CEIPartialModels;
import plus.dragons.createenchantmentindustry.common.fluids.printer.PrinterBlockEntity;
import plus.dragons.createenchantmentindustry.common.fluids.printer.PrinterRenderer;

public class ColorfulPrinterRenderer extends PrinterRenderer {
	private static final PartialModel[] NOZZLE = { CEIPartialModels.PRINTER_NOZZLE_TOP, CEIPartialModels.PRINTER_NOZZLE_BOTTOM };

	public ColorfulPrinterRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(PrinterBlockEntity printer, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(printer, partialTicks, poseStack, buffer, light, overlay);
		SmartFluidTankBehaviour.TankSegment tank =((PrinterBlockEntityAccessor)printer).getTank().getPrimaryTank();
		FluidStack fluidStack = tank.getRenderedFluid();
		float fluidLevel = tank.getFluidLevel().getValue(partialTicks);
		if (!fluidStack.isEmpty() && fluidLevel != 0) {
			boolean top = fluidStack.getFluid().getFluidType().isLighterThanAir();
			fluidLevel = Math.max(fluidLevel, 0.175f) * (11 / 16f);
			float min = 2.5f / 16f;
			float max = min + (11 / 16f);
			float minY = top ? (max - fluidLevel) : min;
			float maxY = top ? max : (min + fluidLevel);
			NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(fluidStack,
					min, minY, min,
					max, maxY, max,
					buffer, poseStack, light,
					false, true);
		}

		float progress = getProgress(printer.processingTicks - partialTicks);

		BlockState state = printer.getBlockState();
		poseStack.pushPose();
		for (PartialModel nozzle : NOZZLE) {
			poseStack.translate(0, 3 * progress / 32f, 0);
			CachedBuffers.partial(nozzle, state)
					.light(light)
					.renderInto(poseStack, buffer.getBuffer(RenderType.solid()));
		}
		poseStack.popPose();

		ColorfulPrinterBlock block = (ColorfulPrinterBlock) printer.getBlockState().getBlock();
		DyeColor color = block.getColor();

		CachedBuffers.partial(CCPPartialModels.PRINTER_PISTON.get(color), state)
				.translate(0, -progress / 2f, 0)
				.light(light)
				.renderInto(poseStack, buffer.getBuffer(RenderType.solid()));
	}
}
