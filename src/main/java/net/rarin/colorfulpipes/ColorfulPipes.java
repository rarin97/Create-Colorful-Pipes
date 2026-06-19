package net.rarin.colorfulpipes;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.rarin.colorfulpipes.compat.CreateDragonsPlus.CDPBlockEntityTypes;
import net.rarin.colorfulpipes.compat.CreateDragonsPlus.CDPBlocks;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.CEIBlockEntityTypes;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.CEIBlocks;
import net.rarin.colorfulpipes.compat.Create_Connected.CCBlockEntityTypes;
import net.rarin.colorfulpipes.compat.Create_Connected.CCBlocks;
import net.rarin.colorfulpipes.compat.Create_Connected.content.ColorfulFluidVesselBlockEntity;
import net.rarin.colorfulpipes.compat.Mods;

import net.rarin.colorfulpipes.config.CCPConfigs;
import net.rarin.colorfulpipes.content.drain.ColorfulDrainBlockEntity;
import net.rarin.colorfulpipes.content.hosePulley.ColorfulHosePulleyBlockEntity;
import net.rarin.colorfulpipes.content.portableFluidInterface.ColorfulPortableFluidInterfaceBlockEntity;
import net.rarin.colorfulpipes.content.spout.ColorfulSpoutBlockEntity;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankBlockEntity;

import org.slf4j.Logger;

@Mod(ColorfulPipes.ID)
public class ColorfulPipes {
    public static final String ID = "colorfulpipes";
    public static final String NAME = "ColorfulPipes";
	public static final Logger LOGGER = LogUtils.getLogger();

    public static IEventBus modEventBus;
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
			.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
							.andThen(TooltipModifier.mapNull(KineticStats.create(item))));


    public ColorfulPipes(IEventBus eventBus, ModContainer modContainer) {
        modEventBus = eventBus;
        REGISTRATE.registerEventListeners(eventBus);

        CCPCreativeTabs.register(eventBus);

        CCPBlocks.register();
		CCPPaletteBlocks.register();
        CCPBlockEntityTypes.register();

		CCPMountedStorageTypes.register();
		CCPDisplaySources.register();

		CCPConfigs.register(modContainer);

		if (Mods.CREATE_CONNECTED.isLoaded()) {
			CCBlocks.register();
			CCBlockEntityTypes.register();
		}

		if (Mods.CREATE_DRAGONS_PLUS.isLoaded()) {
			CDPBlocks.register();
			CDPBlockEntityTypes.register();
		}

		if (Mods.CREATE_ENCHANTMENT_INDUSTRY.isLoaded()) {
			CEIBlocks.register();
			CEIBlockEntityTypes.register();
		}

		modEventBus.addListener(EventPriority.HIGHEST, ColorfulPipesDatagen::gatherDataHighPriority);
		modEventBus.addListener(EventPriority.LOWEST, ColorfulPipesDatagen::gatherData);

    }

	@EventBusSubscriber(modid = ColorfulPipes.ID)
	public class ModBusEvents {

		@SubscribeEvent
		public static void registerCapabilities(RegisterCapabilitiesEvent event) {
			ColorfulFluidTankBlockEntity.registerCapabilities(event);
			ColorfulDrainBlockEntity.registerCapabilities(event);
			ColorfulHosePulleyBlockEntity.registerCapabilities(event);
			ColorfulSpoutBlockEntity.registerCapabilities(event);
			ColorfulPortableFluidInterfaceBlockEntity.registerCapabilities(event);
			if (Mods.CREATE_CONNECTED.isLoaded()) {
				ColorfulFluidVesselBlockEntity.registerCapabilities(event);
			}
			if (Mods.CREATE_ENCHANTMENT_INDUSTRY.isLoaded()) {
				CEIBlockEntityTypes.registerCapabilities(event);
			}
		}
	}

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

	public static CreateRegistrate registrate() {
		return REGISTRATE;
	}
}













