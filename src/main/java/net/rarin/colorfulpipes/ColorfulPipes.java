package net.rarin.colorfulpipes;

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
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(ColorfulPipes.ID)
public class ColorfulPipes {
    public static final String ID = "colorfulpipes";
    public static final String NAME = "ColorfulPipes";

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
        CCPBlockEntityTypes.register();

        CCPMountedStorageTypes.register();
        CCPDisplaySources.register();

		modEventBus.addListener(EventPriority.HIGHEST, ColorfulPipesDatagen::gatherDataHighPriority);
		modEventBus.addListener(EventPriority.LOWEST, ColorfulPipesDatagen::gatherData);

    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

	public static CreateRegistrate registrate() {
		return REGISTRATE;
	}
}













