package net.rarin.colorfulpipes.Datagen;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.rarin.colorfulpipes.CCPTags.ColorfulBlockTags;
import net.rarin.colorfulpipes.CCPTags.ColorfulItemTags;
import net.rarin.colorfulpipes.ColorfulPipes;

public class CCPTagGen {
	public static void addGenerators() {
		ColorfulPipes.registrate().addDataGenerator(ProviderType.BLOCK_TAGS, CCPTagGen::genBlockTags);
		ColorfulPipes.registrate().addDataGenerator(ProviderType.ITEM_TAGS, CCPTagGen::genItemTags);
	}

	private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
		TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);

		prov.tag(ColorfulBlockTags.COLORFUL_PIPES.tag)
				.add(AllBlocks.FLUID_PIPE.get());
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
	}
}
