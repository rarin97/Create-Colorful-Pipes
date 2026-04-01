package net.rarin.colorfulpipes.Datagen;

import java.util.EnumMap;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.data.recipe.ItemApplicationRecipeGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.item.DyeColor;
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.ColorfulPipes;

public class CCPItemApplicationRecipeGen extends ItemApplicationRecipeGen {

	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_PIPES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_SMART_FLUID_PIPES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_PUMPS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_VALVES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_TANKS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_SPOUTS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_DRAINS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_INTERFACES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_STEAM_WHISTLES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_CASING = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_SCAFFOLD = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_LADDER = new EnumMap<>(DyeColor.class);

	{
		for (DyeColor color : DyeColor.values()) {
//			COLORFUL_FLUID_PIPES.put(color, create(color.getName() + "_fluid_pipe",
//					b -> b.require(AllBlocks.FLUID_PIPE.asItem())
//							.require(color.getTag())
//							.output(CCPBlocks.COLORFUL_FLUID_PIPES.get(color))));

//			COLORFUL_SMART_FLUID_PIPES.put(color, create(color.getName() + "_smart_fluid_pipe",
//					b -> b.require(AllBlocks.SMART_FLUID_PIPE.asItem())
//							.require(color.getTag())
//							.output(CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(color))));

//			COLORFUL_PUMPS.put(color, create(color.getName() + "_mechanical_pump",
//					b -> b.require(AllBlocks.MECHANICAL_PUMP.asItem())
//							.require(color.getTag())
//							.output(CCPBlocks.COLORFUL_PUMPS.get(color))));
//
//			COLORFUL_FLUID_VALVES.put(color, create(color.getName() + "_fluid_valve",
//					b -> b.require(AllBlocks.FLUID_VALVE.asItem())
//							.require(color.getTag())
//							.output(CCPBlocks.COLORFUL_FLUID_VALVES.get(color))));

			COLORFUL_FLUID_TANKS.put(color, create(color.getName() + "_fluid_tank",
					b -> b.require(AllBlocks.FLUID_TANK.asItem())
							.require(color.getTag())
							.output(CCPBlocks.COLORFUL_FLUID_TANKS.get(color))));

			COLORFUL_SPOUTS.put(color, create(color.getName() + "_spout",
					b -> b.require(AllBlocks.SPOUT.asItem())
							.require(color.getTag())
							.output(CCPBlocks.COLORFUL_SPOUTS.get(color))));

			COLORFUL_DRAINS.put(color, create(color.getName() + "_item_drain",
					b -> b.require(AllBlocks.ITEM_DRAIN.asItem())
							.require(color.getTag())
							.output(CCPBlocks.COLORFUL_DRAINS.get(color))));

//			COLORFUL_FLUID_INTERFACES.put(color, create(color.getName() + "_portable_fluid_interface",
//					b -> b.require(AllBlocks.PORTABLE_FLUID_INTERFACE.asItem())
//							.require(color.getTag())
//							.output(CCPBlocks.COLORFUL_FLUID_INTERFACES.get(color))));

//			COLORFUL_STEAM_WHISTLES.put(color, create(color.getName() + "_steam_whistle",
//					b -> b.require(AllBlocks.STEAM_WHISTLE.asItem())
//							.require(color.getTag())
//							.output(CCPBlocks.COLORFUL_STEAM_WHISTLES.get(color))));

			COLORFUL_COPPER_CASING.put(color, create(color.getName() + "_copper_casing",
					b -> b.require(AllBlocks.COPPER_CASING.asItem())
							.require(color.getTag())
							.output(CCPBlocks.COLORFUL_COPPER_CASING.get(color))));

			COLORFUL_COPPER_SCAFFOLD.put(color, create(color.getName() + "_copper_scaffolding",
					b -> b.require(/*ColorfulItemTags.COPPER_SCAFFOLDS.tag*/AllBlocks.COPPER_SCAFFOLD.asItem())
							.require(color.getTag())
							.output(CCPBlocks.COLORFUL_COPPER_SCAFFOLD.get(color))));

//			COLORFUL_COPPER_LADDER.put(color, create(color.getName() + "_copper_ladder",
//					b -> b.require(ColorfulItemTags.COPPER_LADDERS.tag)
//							.require(color.getTag())
//							.output(CCPBlocks.COLORFUL_COPPER_LADDER.get(color))));
		}

	}

	public CCPItemApplicationRecipeGen(FabricDataOutput output) {
		super(output, ColorfulPipes.ID);
	}
}
