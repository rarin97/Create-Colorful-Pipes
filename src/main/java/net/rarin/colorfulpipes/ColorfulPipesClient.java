package net.rarin.colorfulpipes;

import net.createmod.ponder.foundation.PonderIndex;
import net.fabricmc.api.ClientModInitializer;

public class ColorfulPipesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
		CCPCreativeTabs.register();
		PonderIndex.addPlugin(new CCPPonderPlugin());
		CCPPartialModels.register();
	}
}
