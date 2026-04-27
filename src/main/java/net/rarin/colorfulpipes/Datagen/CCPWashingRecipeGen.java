package net.rarin.colorfulpipes.Datagen;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.data.recipe.WashingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.rarin.colorfulpipes.CCPPaletteBlocks;
import net.rarin.colorfulpipes.ColorfulPipes;
import net.rarin.colorfulpipes.CCPTags.ColorfulItemTags;

import java.util.concurrent.CompletableFuture;

public class CCPWashingRecipeGen extends WashingRecipeGen {

	GeneratedRecipe

			COLORFUL_FLUID_PIPES = create("fluid_pipe",
			b -> b.require(ColorfulItemTags.COLORFUL_PIPES.tag)
					.output(AllBlocks.FLUID_PIPE.asItem())),

	COLORFUL_SMART_FLUID_PIPES = create("smart_fluid_pipe",
			b -> b.require(ColorfulItemTags.COLORFUL_SMART_PIPES.tag)
					.output(AllBlocks.SMART_FLUID_PIPE.asItem())),

	COLORFUL_PUMPS = create("mechanical_pump",
			b -> b.require(ColorfulItemTags.COLORFUL_PUMPS.tag)
					.output(AllBlocks.MECHANICAL_PUMP.asItem())),

	COLORFUL_FLUID_VALVES = create("fluid_valve",
			b -> b.require(ColorfulItemTags.COLORFUL_FLUID_VALVES.tag)
					.output(AllBlocks.FLUID_VALVE.asItem())),

	COLORFUL_FLUID_TANKS = create("fluid_tank",
			b -> b.require(ColorfulItemTags.COLORFUL_FLUID_TANKS.tag)
					.output(AllBlocks.FLUID_TANK.asItem())),

	COLORFUL_SPOUTS = create("spout",
			b -> b.require(ColorfulItemTags.COLORFUL_SPOUTS.tag)
					.output(AllBlocks.SPOUT.asItem())),

	COLORFUL_DRAINS = create("item_drain",
			b -> b.require(ColorfulItemTags.COLORFUL_DRAINS.tag)
					.output(AllBlocks.ITEM_DRAIN.asItem())),

	COLORFUL_FLUID_INTERFACES = create("portable_fluid_interface",
			b -> b.require(ColorfulItemTags.COLORFUL_PORTABLE_FLUID_INTERFACES.tag)
					.output(AllBlocks.PORTABLE_FLUID_INTERFACE.asItem())),

	COLORFUL_HOSE_PULLEYS = create("hose_pulley",
			b -> b.require(ColorfulItemTags.COLORFUL_HOSE_PULLEYS.tag)
					.output(AllBlocks.HOSE_PULLEY.asItem())),

	COLORFUL_STEAM_WHISTLES = create("steam_whistle",
			b -> b.require(ColorfulItemTags.COLORFUL_STEAM_WHISTLES.tag)
					.output(AllBlocks.STEAM_WHISTLE.asItem())),

	COLORFUL_COPPER_CASING = create("copper_casing",
			b -> b.require(ColorfulItemTags.COLORFUL_COPPER_CASINGS.tag)
					.output(AllBlocks.COPPER_CASING.asItem())),

	COLORFUL_COPPER_SCAFFOLD = create("copper_scaffolding",
			b -> b.require(ColorfulItemTags.COLORFUL_COPPER_SCAFFOLDS.tag)
					.output(AllBlocks.COPPER_SCAFFOLD.asItem())),

	COLORFUL_COPPER_LADDER = create("copper_ladder",
			b -> b.require(ColorfulItemTags.COLORFUL_COPPER_LADDERS.tag)
					.output(AllBlocks.COPPER_LADDER.asItem())),

	COLORFUL_COPPER_GLASS_CASING = create("copper_glass_casing",
			b -> b.require(ColorfulItemTags.COLORFUL_COPPER_GLASS_CASINGS.tag)
					.output(CCPPaletteBlocks.COPPER_GLASS_CASING.asItem())),

	COLORFUL_COPPER_TINTED_GLASS_CASING = create("copper_tinted_glass_casing",
			b -> b.require(ColorfulItemTags.COLORFUL_COPPER_TINTED_GLASS_CASINGS.tag)
					.output(CCPPaletteBlocks.COPPER_TINTED_GLASS_CASING.asItem())),

	COLORFUL_COPPER_BARS= create("copper_bar",
			b -> b.require(ColorfulItemTags.COLORFUL_COPPER_BARS.tag)
					.output(AllBlocks.COPPER_BARS.asItem())),

	COLORFUL_COPPER_DOOR = create("copper_door",
			b -> b.require(ColorfulItemTags.COLORFUL_COPPER_DOORS.tag)
					.output(AllBlocks.COPPER_DOOR.asItem()));



	public CCPWashingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, ColorfulPipes.ID);
	}
}
