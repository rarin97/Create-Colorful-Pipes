package net.rarin.colorfulpipes;

import com.simibubi.create.foundation.data.CreateRegistrate;

import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;

import net.createmod.catnip.lang.FontHelper;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorfulPipes implements ModInitializer {
	public static final String ID = "colorfulpipes";
	public static final String NAME = "ColorfulPipes";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID)
			.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
							.andThen(TooltipModifier.mapNull(KineticStats.create(item))));


	@Override
	public void onInitialize() {
		LOGGER.info("initializing Create Colorful Pipes");
		CCPCreativeTabs.register();
		CCPBlocks.register();
		CCPBlockEntityTypes.register();
		REGISTRATE.register();

		CCPMountedStorageTypes.register();
		CCPDisplaySources.register();
	}

	public static CreateRegistrate getRegistrate() {
		return REGISTRATE;
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}
}
