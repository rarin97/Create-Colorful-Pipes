package com.example.modid;

import com.example.modid.ponder.ExamplePonderPlugin;

import net.createmod.ponder.foundation.PonderIndex;
import net.fabricmc.api.ClientModInitializer;

public class ExampleClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
		PonderIndex.addPlugin(new ExamplePonderPlugin());

	}
}
