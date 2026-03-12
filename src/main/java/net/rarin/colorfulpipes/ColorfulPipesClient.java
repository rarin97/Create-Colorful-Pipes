package net.rarin.colorfulpipes;

import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static net.rarin.colorfulpipes.ColorfulPipes.modEventBus;

@Mod(value = ColorfulPipes.ID, dist = Dist.CLIENT)
public class ColorfulPipesClient {
	public ColorfulPipesClient(ModContainer modContainer) {
		CCPPartialModels.register();
		modEventBus.addListener(ColorfulPipesClient::init);
	}

	public static void init(final FMLClientSetupEvent event) {
		PonderIndex.addPlugin(new CCPPonderPlugin());
	}
}