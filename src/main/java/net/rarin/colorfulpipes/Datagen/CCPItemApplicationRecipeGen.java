package net.rarin.colorfulpipes.Datagen;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.data.recipe.ItemApplicationRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.CCPPaletteBlocks;
import net.rarin.colorfulpipes.ColorfulPipes;
import net.rarin.colorfulpipes.compat.CreateDragonsPlus.CDPBlocks;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.CEIBlocks;
import net.rarin.colorfulpipes.compat.Mods;

import java.util.EnumMap;
import java.util.concurrent.CompletableFuture;

public class CCPItemApplicationRecipeGen extends ItemApplicationRecipeGen {

	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_SPOUTS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_DRAINS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_EXPERIENCE_HATCHES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_CASING = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_GLASS_CASING = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_TINTED_GLASS_CASING = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_SCAFFOLD = new EnumMap<>(DyeColor.class);

	{
		for (DyeColor color : DyeColor.values()) {
			COLORFUL_SPOUTS.put(color, create(color.getName() + "_spout",
					b -> b.require(AllBlocks.SPOUT.asItem())
							.require(color.getTag())
							.output(CCPBlocks.COLORFUL_SPOUTS.get(color))));

			COLORFUL_DRAINS.put(color, create(color.getName() + "_item_drain",
					b -> b.require(AllBlocks.ITEM_DRAIN.asItem())
							.require(color.getTag())
							.output(CCPBlocks.COLORFUL_DRAINS.get(color))));

			COLORFUL_COPPER_CASING.put(color, create(color.getName() + "_copper_casing",
					b -> b.require(AllBlocks.COPPER_CASING.asItem())
							.require(color.getTag())
							.output(CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))));

			COLORFUL_COPPER_GLASS_CASING.put(color, create(color.getName() + "_copper_glass_casing",
					b -> b.require(CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))
							.require(Tags.Items.GLASS_BLOCKS_COLORLESS)
							.output(CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(color))));

			COLORFUL_COPPER_GLASS_CASING.put(color, create(color.getName() + "_copper_glass_casing_other",
					b -> b.require(CCPPaletteBlocks.COPPER_GLASS_CASING.asItem())
							.require(color.getTag())
							.output(CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(color))));

			COLORFUL_COPPER_GLASS_CASING.put(color, create(color.getName() + "_copper_tinted_glass_casing",
					b -> b.require(CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))
							.require(Items.TINTED_GLASS)
							.output(CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(color))));

			COLORFUL_COPPER_TINTED_GLASS_CASING.put(color, create(color.getName() + "_copper_tinted_glass_casing_other",
					b -> b.require(CCPPaletteBlocks.COPPER_TINTED_GLASS_CASING.asItem())
							.require(color.getTag())
							.output(CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(color))));

			COLORFUL_COPPER_SCAFFOLD.put(color, create(color.getName() + "_copper_scaffolding",
					b -> b.require(AllBlocks.COPPER_SCAFFOLD.asItem())
							.require(color.getTag())
							.output(CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(color))));

			COLORFUL_EXPERIENCE_HATCHES.put(color, create(color.getName() + "_experience_hatch",
					b -> b.require(CDPBlocks.COLORFUL_FLUID_HATCHES.get(color))
							.require(AllBlocks.EXPERIENCE_BLOCK)
							.output(CEIBlocks.COLORFUL_EXPERIENCE_HATCHES.get(color))
							.withCondition(new ModLoadedCondition(Mods.CREATE_ENCHANTMENT_INDUSTRY.id()))));
		}

	}

	GeneratedRecipe

			COPPER_GLASS_CASING = create("copper_glass_casing",
			b -> b.require(AllBlocks.COPPER_CASING.asItem())
					.require(Tags.Items.GLASS_BLOCKS_COLORLESS)
					.output(CCPPaletteBlocks.COPPER_GLASS_CASING.asStack())),

	COPPER_TINTED_GLASS_CASING = create("copper_tinted_glass_casing",
			b -> b.require(AllBlocks.COPPER_CASING.asItem())
					.require(Items.TINTED_GLASS)
					.output(CCPPaletteBlocks.COPPER_TINTED_GLASS_CASING.asStack()));

	public CCPItemApplicationRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, ColorfulPipes.ID);
	}
}
