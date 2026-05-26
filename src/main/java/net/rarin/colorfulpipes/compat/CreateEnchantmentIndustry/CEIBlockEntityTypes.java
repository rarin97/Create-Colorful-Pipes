package net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry;

import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content.ColorfulPrinterBlockEntity;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content.ColorfulPrinterRenderer;
import plus.dragons.createenchantmentindustry.client.model.CEIPartialModels;
import plus.dragons.createenchantmentindustry.common.fluids.experience.ExperienceHatchBlockEntity;
import plus.dragons.createenchantmentindustry.common.fluids.lantern.ExperienceLanternBlockEntity;
import plus.dragons.createenchantmentindustry.common.fluids.printer.PrinterBlockEntity;
import plus.dragons.createenchantmentindustry.common.fluids.printer.PrinterRenderer;
import plus.dragons.createenchantmentindustry.common.kinetics.grindstone.GrindstoneDrainBlockEntity;
import plus.dragons.createenchantmentindustry.common.kinetics.grindstone.GrindstoneDrainRenderer;

import static net.rarin.colorfulpipes.ColorfulPipes.REGISTRATE;

public class CEIBlockEntityTypes {

	public static final BlockEntityEntry<ExperienceHatchBlockEntity> COLORFUL_EXPERIENCE_HATCHES = REGISTRATE
			.blockEntity("experience_hatch", ExperienceHatchBlockEntity::new)
			.renderer(() -> SmartBlockEntityRenderer::new)
			.validBlocks(CEIBlocks.COLORFUL_EXPERIENCE_HATCHES.toArray())
			.register();

	public static final BlockEntityEntry<ExperienceLanternBlockEntity> COLORFUL_EXPERIENCE_LANTERN = REGISTRATE
			.blockEntity("experience_lantern", ExperienceLanternBlockEntity::new)
			.validBlocks(CEIBlocks.COLORFUL_EXPERIENCE_LANTERNS.toArray())
			.register();

	public static final BlockEntityEntry<ColorfulPrinterBlockEntity> COLORFUL_PRINTER = REGISTRATE
			.blockEntity("printer", ColorfulPrinterBlockEntity::new)
			.renderer(() -> ColorfulPrinterRenderer::new)
			.validBlocks(CEIBlocks.COLORFUL_PRINTERS.toArray())
			.register();

	public static final BlockEntityEntry<GrindstoneDrainBlockEntity> COLORFUL_GRINDSTONE_DRAIN = REGISTRATE
			.blockEntity("grindstone_drain", GrindstoneDrainBlockEntity::new)
			.visual(() -> SingleAxisRotatingVisual.of(CEIPartialModels.MECHANICAL_GRINDSTONE), true)
			.renderer(() -> GrindstoneDrainRenderer::new)
			.validBlocks(CEIBlocks.COLORFUL_GRINDSTONE_DRAIN.toArray())
			.register();



	public static void register() {
	}

	@SubscribeEvent
	public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
				COLORFUL_GRINDSTONE_DRAIN.get(), GrindstoneDrainBlockEntity::getItemHandler);
		event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
				COLORFUL_GRINDSTONE_DRAIN.get(), GrindstoneDrainBlockEntity::getFluidHandler);
		event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
				COLORFUL_PRINTER.get(), PrinterBlockEntity::getFluidHandler);
		event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
				COLORFUL_EXPERIENCE_LANTERN.get(), ExperienceLanternBlockEntity::getFluidHandler);
	}
}
