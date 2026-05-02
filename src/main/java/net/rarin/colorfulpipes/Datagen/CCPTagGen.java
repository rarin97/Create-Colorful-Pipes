package net.rarin.colorfulpipes.Datagen;

import com.hlysine.create_connected.CCBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.CCPPaletteBlocks;
import net.rarin.colorfulpipes.ColorfulPipes;
import net.rarin.colorfulpipes.CCPTags.ColorfulItemTags;
import net.rarin.colorfulpipes.compat.Mods;

public class CCPTagGen {
	public static void addGenerators() {
		ColorfulPipes.registrate().addDataGenerator(ProviderType.BLOCK_TAGS, CCPTagGen::genBlockTags);
		ColorfulPipes.registrate().addDataGenerator(ProviderType.ITEM_TAGS, CCPTagGen::genItemTags);
	}

	private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
		TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);

	}

	private static void genItemTags(RegistrateTagsProvider<Item> provIn) {
		TagGen.CreateTagsProvider<Item> prov = new TagGen.CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);

		prov.tag(ColorfulItemTags.FLUID_PIPES.tag)
				.add(AllBlocks.FLUID_PIPE.get().asItem());

		prov.tag(ColorfulItemTags.SMART_FLUID_PIPES.tag)
				.add(AllBlocks.SMART_FLUID_PIPE.get().asItem());

		prov.tag(ColorfulItemTags.FLUID_VALVES.tag)
				.add(AllBlocks.FLUID_VALVE.get().asItem());

		prov.tag(ColorfulItemTags.MECHANICAL_PUMPS.tag)
				.add(AllBlocks.MECHANICAL_PUMP.get().asItem());

		prov.tag(ColorfulItemTags.FLUID_TANKS.tag)
				.add(AllBlocks.FLUID_TANK.get().asItem());

		prov.tag(ColorfulItemTags.SPOUTS.tag)
				.add(AllBlocks.SPOUT.get().asItem());

		prov.tag(ColorfulItemTags.ITEM_DRAINS.tag)
				.add(AllBlocks.ITEM_DRAIN.get().asItem());

		prov.tag(ColorfulItemTags.PORTABLE_FLUID_INTERFACES.tag)
				.add(AllBlocks.PORTABLE_FLUID_INTERFACE.get().asItem());

		prov.tag(ColorfulItemTags.HOSE_PULLEYS.tag)
				.add(AllBlocks.HOSE_PULLEY.get().asItem());

		prov.tag(ColorfulItemTags.STEAM_WHISTLES.tag)
				.add(AllBlocks.STEAM_WHISTLE.get().asItem());

		prov.tag(ColorfulItemTags.COPPER_TABLE_CLOTHS.tag)
				.add(AllBlocks.COPPER_TABLE_CLOTH.get().asItem());

		prov.tag(ColorfulItemTags.COPPER_CASINGS.tag)
				.add(AllBlocks.COPPER_CASING.get().asItem());

		prov.tag(ColorfulItemTags.COPPER_SCAFFOLDS.tag)
				.add(AllBlocks.COPPER_SCAFFOLD.get().asItem());

		prov.tag(ColorfulItemTags.COPPER_LADDERS.tag)
				.add(AllBlocks.COPPER_LADDER.get().asItem());

		prov.tag(ColorfulItemTags.COPPER_BARS.tag)
				.add(AllBlocks.COPPER_BARS.get().asItem());

		prov.tag(ColorfulItemTags.COPPER_DOORS.tag)
				.add(AllBlocks.COPPER_DOOR.get().asItem());

		if (Mods.CREATE_CONNECTED.isLoaded()) {
			prov.tag(ColorfulItemTags.FLUID_VESSELS.tag)
					.add(CCBlocks.FLUID_VESSEL.get().asItem());
		}

		prov.tag(Tags.Items.DYED_WHITE)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.WHITE).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.WHITE).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.WHITE).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.WHITE).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.WHITE).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.WHITE).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.WHITE).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.WHITE).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.WHITE).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.WHITE).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.WHITE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.WHITE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.WHITE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.WHITE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.WHITE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.WHITE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.WHITE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.WHITE).asItem());


		prov.tag(Tags.Items.DYED_LIGHT_GRAY)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.LIGHT_GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.LIGHT_GRAY).asItem());

		prov.tag(Tags.Items.DYED_GRAY)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.GRAY).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.GRAY).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.GRAY).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.GRAY).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.GRAY).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.GRAY).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.GRAY).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.GRAY).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.GRAY).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.GRAY).asItem());

		prov.tag(Tags.Items.DYED_BLACK)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.BLACK).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.BLACK).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.BLACK).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.BLACK).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.BLACK).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.BLACK).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.BLACK).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.BLACK).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.BLACK).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.BLACK).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.BLACK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.BLACK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.BLACK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.BLACK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.BLACK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.BLACK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.BLACK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.BLACK).asItem());

		prov.tag(Tags.Items.DYED_BROWN)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.BROWN).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.BROWN).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.BROWN).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.BROWN).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.BROWN).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.BROWN).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.BROWN).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.BROWN).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.BROWN).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.BROWN).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.BROWN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.BROWN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.BROWN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.BROWN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.BROWN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.BROWN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.BROWN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.BROWN).asItem());

		prov.tag(Tags.Items.DYED_RED)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.RED).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.RED).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.RED).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.RED).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.RED).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.RED).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.RED).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.RED).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.RED).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.RED).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.RED).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.RED).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.RED).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.RED).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.RED).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.RED).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.RED).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.RED).asItem());

		prov.tag(Tags.Items.DYED_ORANGE)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.ORANGE).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.ORANGE).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.ORANGE).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.ORANGE).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.ORANGE).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.ORANGE).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.ORANGE).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.ORANGE).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.ORANGE).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.ORANGE).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.ORANGE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.ORANGE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.ORANGE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.ORANGE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.ORANGE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.ORANGE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.ORANGE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.ORANGE).asItem());

		prov.tag(Tags.Items.DYED_YELLOW)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.YELLOW).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.YELLOW).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.YELLOW).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.YELLOW).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.YELLOW).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.YELLOW).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.YELLOW).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.YELLOW).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.YELLOW).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.YELLOW).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.YELLOW).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.YELLOW).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.YELLOW).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.YELLOW).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.YELLOW).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.YELLOW).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.YELLOW).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.YELLOW).asItem());

		prov.tag(Tags.Items.DYED_LIME)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.LIME).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.LIME).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.LIME).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.LIME).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.LIME).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.LIME).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.LIME).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.LIME).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.LIME).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.LIME).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.LIME).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.LIME).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.LIME).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.LIME).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.LIME).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.LIME).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.LIME).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.LIME).asItem());

		prov.tag(Tags.Items.DYED_GREEN)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.GREEN).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.GREEN).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.GREEN).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.GREEN).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.GREEN).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.GREEN).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.GREEN).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.GREEN).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.GREEN).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.GREEN).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.GREEN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.GREEN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.GREEN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.GREEN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.GREEN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.GREEN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.GREEN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.GREEN).asItem());

		prov.tag(Tags.Items.DYED_CYAN)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.CYAN).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.CYAN).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.CYAN).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.CYAN).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.CYAN).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.CYAN).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.CYAN).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.CYAN).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.CYAN).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.CYAN).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.CYAN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.CYAN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.CYAN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.CYAN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.CYAN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.CYAN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.CYAN).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.CYAN).asItem());

		prov.tag(Tags.Items.DYED_LIGHT_BLUE)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.LIGHT_BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.LIGHT_BLUE).asItem());

		prov.tag(Tags.Items.DYED_BLUE)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.BLUE).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.BLUE).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.BLUE).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.BLUE).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.BLUE).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.BLUE).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.BLUE).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.BLUE).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.BLUE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.BLUE).asItem());

		prov.tag(Tags.Items.DYED_PURPLE)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.PURPLE).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.PURPLE).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.PURPLE).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.PURPLE).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.PURPLE).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.PURPLE).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.PURPLE).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.PURPLE).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.PURPLE).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.PURPLE).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.PURPLE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.PURPLE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.PURPLE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.PURPLE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.PURPLE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.PURPLE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.PURPLE).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.PURPLE).asItem());

		prov.tag(Tags.Items.DYED_MAGENTA)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.MAGENTA).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.MAGENTA).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.MAGENTA).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.MAGENTA).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.MAGENTA).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.MAGENTA).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.MAGENTA).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.MAGENTA).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.MAGENTA).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.MAGENTA).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.MAGENTA).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.MAGENTA).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.MAGENTA).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.MAGENTA).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.MAGENTA).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.MAGENTA).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.MAGENTA).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.MAGENTA).asItem());

		prov.tag(Tags.Items.DYED_PINK)
				.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(DyeColor.PINK).asItem(),
						CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.PINK).asItem(),
						CCPBlocks.COLORFUL_PUMPS.get(DyeColor.PINK).asItem(),
						CCPBlocks.COLORFUL_FLUID_VALVES.get(DyeColor.PINK).asItem(),
						CCPBlocks.COLORFUL_FLUID_TANKS.get(DyeColor.PINK).asItem(),
						CCPBlocks.COLORFUL_SPOUTS.get(DyeColor.PINK).asItem(),
						CCPBlocks.COLORFUL_DRAINS.get(DyeColor.PINK).asItem(),
						CCPBlocks.COLORFUL_FLUID_INTERFACES.get(DyeColor.PINK).asItem(),
						CCPBlocks.COLORFUL_HOSE_PULLEYS.get(DyeColor.PINK).asItem(),
						CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(DyeColor.PINK).asItem(),
						CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(DyeColor.PINK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.PINK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.PINK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.PINK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(DyeColor.PINK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(DyeColor.PINK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(DyeColor.PINK).asItem(),
						CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(DyeColor.PINK).asItem());

	}
}
