package net.rarin.colorfulpipes.Datagen;

import com.hlysine.create_connected.registries.CCBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.CCPPaletteBlocks;
import net.rarin.colorfulpipes.CCPTags.ColorfulBlockTags;
import net.rarin.colorfulpipes.ColorfulPipes;
import net.rarin.colorfulpipes.CCPTags.ColorfulItemTags;
import plus.dragons.createdragonsplus.common.registry.CDPBlocks;
import plus.dragons.createenchantmentindustry.common.registry.CEIBlocks;

public class CCPTagGen {
	public static void addGenerators() {
		ColorfulPipes.registrate().addDataGenerator(ProviderType.BLOCK_TAGS, CCPTagGen::genBlockTags);
		ColorfulPipes.registrate().addDataGenerator(ProviderType.ITEM_TAGS, CCPTagGen::genItemTags);
	}

	private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
		TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);

		prov.tag(ColorfulBlockTags.LIGHT.tag)
				.addOptionalTag(ColorfulBlockTags.COLORFUL_FLUID_HATCHES.tag);

		prov.tag(ColorfulBlockTags.SUPER_LIGHT.tag)
				.addOptionalTag(ColorfulBlockTags.COLORFUL_COPPER_LADDERS.tag)
				.addOptionalTag(ColorfulBlockTags.COLORFUL_COPPER_DOORS.tag)
				.addOptionalTag(ColorfulBlockTags.COLORFUL_COPPER_BARS.tag);

		prov.tag(ColorfulBlockTags.QUARTER_VOLUMES.tag)
				.addOptionalTag(ColorfulBlockTags.COLORFUL_COPPER_LADDERS.tag)
				.addOptionalTag(ColorfulBlockTags.COLORFUL_COPPER_DOORS.tag)
				.addOptionalTag(ColorfulBlockTags.COLORFUL_COPPER_BARS.tag);

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

		prov.tag(ColorfulItemTags.STEAM_ENGINES.tag)
				.add(AllBlocks.STEAM_ENGINE.get().asItem());

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

		prov.tag(ColorfulItemTags.FLUID_VESSELS.tag)
				.add(CCBlocks.FLUID_VESSEL.get().asItem());

		prov.tag(ColorfulItemTags.FLUID_HATCHES.tag)
				.add(CDPBlocks.FLUID_HATCH.get().asItem());

		prov.tag(ColorfulItemTags.EXPERIENCE_HATCHES.tag)
				.add(CEIBlocks.EXPERIENCE_HATCH.get().asItem());

		prov.tag(ColorfulItemTags.EXPERIENCE_LANTERNS.tag)
				.add(CEIBlocks.EXPERIENCE_LANTERN.get().asItem());

		prov.tag(ColorfulItemTags.PRINTERS.tag)
				.add(CEIBlocks.PRINTER.get().asItem());


		for (var color : DyeColor.values()) {

			TagKey<Item> tag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dyed/" + color.getSerializedName()));

			prov.tag(tag)
					.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(color).asItem(),
							CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(color).asItem(),
							CCPBlocks.COLORFUL_PUMPS.get(color).asItem(),
							CCPBlocks.COLORFUL_FLUID_VALVES.get(color).asItem(),
							CCPBlocks.COLORFUL_FLUID_TANKS.get(color).asItem(),
							CCPBlocks.COLORFUL_SPOUTS.get(color).asItem(),
							CCPBlocks.COLORFUL_DRAINS.get(color).asItem(),
							CCPBlocks.COLORFUL_FLUID_INTERFACES.get(color).asItem(),
							CCPBlocks.COLORFUL_HOSE_PULLEYS.get(color).asItem(),
							CCPBlocks.COLORFUL_STEAM_ENGINES.get(color).asItem(),
							CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(color).asItem(),
							CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(color).asItem(),
							CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color).asItem(),
							CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(color).asItem(),
							CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(color).asItem(),
							CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(color).asItem(),
							CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(color).asItem(),
							CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(color).asItem(),
							CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(color).asItem());
		}
	}
}
