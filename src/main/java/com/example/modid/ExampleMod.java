package com.example.modid;

import com.simibubi.create.Create;

import com.simibubi.create.foundation.data.CreateRegistrate;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String ID = "modid";
	public static final String NAME = "Example Mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);


	@Override
	public void onInitialize() {
		REGISTRATE.register();
		CBlockEntityTypes.register();
		CBlocks.register();
		CItems.register();

	}

	public static CreateRegistrate getRegistrate() {
		return REGISTRATE;
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}
