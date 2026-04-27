package net.rarin.colorfulpipes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.Create;
import com.simibubi.create.content.decoration.MetalLadderBlock;
import com.simibubi.create.content.decoration.MetalScaffoldingBlock;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlock;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.createmod.catnip.data.Couple;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.Tags;
import net.rarin.colorfulpipes.Datagen.CCPMetalBarsGen;
import net.rarin.colorfulpipes.content.ScaffoldingBlock.ColorfulMetalScaffoldingBlock;
import net.rarin.colorfulpipes.content.casing.ColorfulCasingBlock;
import net.rarin.colorfulpipes.content.casing.ColorfulCasingGlassBlock;
import net.rarin.colorfulpipes.content.casing.ColorfulCasingTintedGlassBlock;
import net.rarin.colorfulpipes.content.casing.CopperCasingGlassBlock;
import net.rarin.colorfulpipes.content.casing.CopperCasingTintedGlassBlock;
import net.rarin.colorfulpipes.content.encasedcogwheel.ColorfulEncasedCogwheelBlock;
import net.rarin.colorfulpipes.content.encasedcogwheel.CopperEncasedCogwheelBlock;
import net.rarin.colorfulpipes.content.encasedshaft.ColorfulEncasedShaftBlock;
import net.rarin.colorfulpipes.content.encasedshaft.ColorfulGlassEncasedShaftBlock;
import net.rarin.colorfulpipes.content.encasedshaft.ColorfulTintedGlassEncasedShaftBlock;
import net.rarin.colorfulpipes.content.encasedshaft.CopperEncasedShaftBlock;
import net.rarin.colorfulpipes.content.encasedshaft.CopperGlassEncasedShaftBlock;
import net.rarin.colorfulpipes.content.encasedshaft.CopperTintedGlassEncasedShaftBlock;
import net.rarin.colorfulpipes.content.ladder.ColorfulMetalLadderBlock;
import net.rarin.colorfulpipes.content.slidingDoor.ColorfulSlidingDoorBlock;
import net.rarin.colorfulpipes.content.steamWhistle.ColorfulWhistleBlock;
import net.rarin.colorfulpipes.content.steamWhistle.ColorfulWhistleExtenderBlock;
import net.rarin.colorfulpipes.content.steamWhistle.ColorfulWhistleGenerator;
import net.rarin.colorfulpipes.content.table_cloth.ColorfulTableClothBlock;

import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class CCPPaletteBlocks {
	private static final CreateRegistrate REGISTRATE = ColorfulPipes.REGISTRATE;

	static {
		REGISTRATE.setCreativeTab(CCPCreativeTabs.PALETTES);
	}

	public static final DyedBlockList<WhistleBlock> COLORFUL_STEAM_WHISTLES = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_steam_whistle", p -> new ColorfulWhistleBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly())
				.blockstate(new ColorfulWhistleGenerator(color)::generate)
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(CCPTags.ColorfulItemTags.STEAM_WHISTLES.tag)
						.unlockedBy("has_steam_whistle", RegistrateRecipeProvider.has(AllBlocks.STEAM_WHISTLE.asItem()))
						.save(p, ColorfulPipes.asResource("steam_whistle/" + c.getName()))
				)
				.tag(CCPTags.ColorfulBlockTags.COLORFUL_STEAM_WHISTLES.tag)
				.item()
				.tag(CCPTags.ColorfulItemTags.COLORFUL_STEAM_WHISTLES.tag)
				.tag(CCPTags.ColorfulItemTags.STEAM_WHISTLES.tag)
				.model((c, p) ->
						p.withExistingParent(c.getName(), Create.asResource("block/steam_whistle/item"))
								.texture("1", ColorfulPipes.asResource("block/engine/" + colorName))
								.texture("2", ColorfulPipes.asResource("block/copper_redstone_plate/" + colorName))
				)
				.build()
				.register();
	});

	public static final BlockEntry<ColorfulWhistleExtenderBlock> COLORFUL_STEAM_WHISTLE_EXTENSION =
			REGISTRATE.block("steam_whistle_extension", ColorfulWhistleExtenderBlock::new)
					.initialProperties(SharedProperties::copperMetal)
					.properties(p -> p.mapColor(MapColor.GOLD).forceSolidOn())
					.transform(pickaxeOnly())
					.blockstate(BlockStateGen.whistleExtender())
					.register();

	public static final DyedBlockList<TableClothBlock> COLORFUL_TABLE_CLOTHS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_copper_table_cloth", p -> new ColorfulTableClothBlock(p, "copper", color))
				.transform(CCPBuilderTransformers.colorfultableCloth(color, SharedProperties::copperMetal, false))
				.properties(p -> p.mapColor(color))
				.blockstate((c, p) -> {
					p.simpleBlock(c.get(), p.models().withExistingParent(c.getName(), Create.asResource("block/table_cloth/block"))
							.texture("0", ColorfulPipes.asResource("block/table_cloth/" + colorName)));
				})
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(CCPTags.ColorfulItemTags.COPPER_TABLE_CLOTHS.tag)
						.unlockedBy("has_copper_table_cloth", RegistrateRecipeProvider.has(AllBlocks.COPPER_TABLE_CLOTH.asItem()))
						.save(p, ColorfulPipes.asResource("copper_table_cloth/" + c.getName()))
				)
				.register();
	});

	public static final DyedBlockList<CasingBlock> COLORFUL_COPPER_CASING = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_copper_casing", p -> new ColorfulCasingBlock(p, color))
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(CCPBuilderTransformers.colorfulCasing(color))
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(CCPTags.ColorfulItemTags.COPPER_CASINGS.tag)
						.unlockedBy("has_copper_casing", RegistrateRecipeProvider.has(AllBlocks.COPPER_CASING.asItem()))
						.save(p, ColorfulPipes.asResource("copper_casing/" + c.getName()))
				)
				.register();
	});

	public static final BlockEntry<CopperCasingGlassBlock> COPPER_GLASS_CASING =
			REGISTRATE.block("copper_glass_casing", CopperCasingGlassBlock::new)
					.properties(p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).sound(SoundType.COPPER))
					.transform(CCPBuilderTransformers.copperglassCasing(() -> CCPSpriteShifts.COPPER_GLASS_CASING))
					.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
							.requires(Tags.Items.GLASS_BLOCKS_COLORLESS)
							.requires(AllBlocks.COPPER_CASING.asItem())
							.unlockedBy("has_copper_glass_casing", RegistrateRecipeProvider.has(AllBlocks.COPPER_CASING.asItem()))
							.save(p, ColorfulPipes.asResource("copper_glass_casing/" + c.getName()))
					)
					.register();

	public static final DyedBlockList<CasingBlock> COLORFUL_COPPER_GLASS_CASING = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_copper_glass_casing", p -> new ColorfulCasingGlassBlock(p, color))
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(CCPBuilderTransformers.colorfulglassCasing(color))
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(CCPTags.ColorfulItemTags.COPPER_GLASS_CASINGS.tag)
						.unlockedBy("has_copper_glass_casing", RegistrateRecipeProvider.has(AllBlocks.COPPER_CASING.asItem()))
						.save(p, ColorfulPipes.asResource("copper_glass_casing/" + c.getName()))
				)
				.register();
	});

	public static final BlockEntry<CopperCasingTintedGlassBlock> COPPER_TINTED_GLASS_CASING =
			REGISTRATE.block("copper_tinted_glass_casing", CopperCasingTintedGlassBlock::new)
					.properties(p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).sound(SoundType.COPPER))
					.transform(CCPBuilderTransformers.coppertintedglassCasing(() -> CCPSpriteShifts.COPPER_TINTED_GLASS_CASING))
					.register();

	public static final DyedBlockList<CasingBlock> COLORFUL_COPPER_TINTED_GLASS_CASING = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_copper_tinted_glass_casing", p -> new ColorfulCasingTintedGlassBlock(p, color))
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(CCPBuilderTransformers.colorfultintedglassCasing(color))
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(CCPTags.ColorfulItemTags.COPPER_TINTED_GLASS_CASINGS.tag)
						.unlockedBy("has_copper_tinted_glass_casing", RegistrateRecipeProvider.has(AllBlocks.COPPER_CASING.asItem()))
						.save(p, ColorfulPipes.asResource("copper_tinted_glass_casing/" + c.getName()))
				)
				.register();
	});

	public static final DyedBlockList<MetalLadderBlock> COLORFUL_COPPER_LADDER = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_copper_ladder", p -> new ColorfulMetalLadderBlock(p, color))
				.transform(CCPBuilderTransformers.colorfulladder(color))
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(CCPTags.ColorfulItemTags.COPPER_LADDERS.tag)
						.unlockedBy("has_copper_ladder", RegistrateRecipeProvider.has(AllBlocks.COPPER_LADDER.asItem()))
						.save(p, ColorfulPipes.asResource("copper_ladder/" + c.getName()))
				)
				.register();
	});

	public static final DyedBlockList<MetalScaffoldingBlock> COLORFUL_COPPER_SCAFFOLD = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_copper_scaffolding", p -> new ColorfulMetalScaffoldingBlock(p, color))
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(CCPBuilderTransformers.colorfulscaffold(CCPSpriteShifts.COLORFUL_COPPER_SCAFFOLD.get(color),
						CCPSpriteShifts.COLORFUL_COPPER_SCAFFOLD_INSIDE.get(color), CCPSpriteShifts.COLORFUL_COPPER_CASING.get(color)))
				.blockstate((c, p) -> p.getVariantBuilder(c.get())
						.forAllStatesExcept(s -> {
							String suffix = s.getValue(MetalScaffoldingBlock.BOTTOM) ? "_horizontal" : "";
							return ConfiguredModel.builder()
									.modelFile(p.models()
											.withExistingParent(c.getName() + suffix, Create.asResource("block/scaffold/block" + suffix))
											.texture("top", ColorfulPipes.asResource("block/copper_funnel_frame/" + colorName))
											.texture("inside", ColorfulPipes.asResource("block/copper_scaffold_inside/" + colorName))
											.texture("side", ColorfulPipes.asResource("block/copper_scaffold/" + colorName))
											.texture("casing", ColorfulPipes.asResource("block/copper_casing/" + colorName))
											.texture("particle", ColorfulPipes.asResource("block/copper_scaffold/" + colorName)))
									.build();
						}, MetalScaffoldingBlock.WATERLOGGED, MetalScaffoldingBlock.DISTANCE))
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(CCPTags.ColorfulItemTags.COPPER_SCAFFOLDS.tag)
						.unlockedBy("has_copper_scaffolding", RegistrateRecipeProvider.has(AllBlocks.COPPER_SCAFFOLD.asItem()))
						.save(p, ColorfulPipes.asResource("copper_scaffolding/" + c.getName()))
				)
				.tag(CCPTags.ColorfulBlockTags.COLORFUL_COPPER_SCAFFOLDS.tag)
				.register();
	});

	public static final DyedBlockList<IronBarsBlock> COLORFUL_COPPER_BARS = new DyedBlockList<>(color ->
			CCPMetalBarsGen.createBars(color.getSerializedName() + "_copper", true, color.getMapColor()));

	public static final DyedBlockList<ColorfulSlidingDoorBlock> COLORFUL_COPPER_DOOR = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_copper_door", p -> ColorfulSlidingDoorBlock.stone(p, true, color))
				.transform(CCPBuilderTransformers.colorfulslidingDoor(colorName + "_copper"))
				.properties(p -> p.mapColor(color).noOcclusion())
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(CCPTags.ColorfulItemTags.COPPER_DOORS.tag)
						.unlockedBy("has_copper_doors", RegistrateRecipeProvider.has(AllBlocks.COPPER_SCAFFOLD.asItem()))
						.save(p, ColorfulPipes.asResource("copper_doors/" + c.getName()))
				)
				.register();
	});

	public static final BlockEntry<CopperEncasedShaftBlock> COPPER_ENCASED_SHAFT =
			REGISTRATE.block("copper_encased_shaft", p -> new CopperEncasedShaftBlock(p, AllBlocks.COPPER_CASING::get))
					.properties(p -> p.mapColor(MapColor.TERRACOTTA_ORANGE))
					.transform(CCPBuilderTransformers.encasedShaft(() -> AllSpriteShifts.COPPER_CASING))
					.transform(EncasingRegistry.addVariantTo(AllBlocks.SHAFT))
					.transform(axeOrPickaxe())
					.register();

	public static final DyedBlockList<ColorfulEncasedShaftBlock> COLORFUL_COPPER_ENCASED_SHAFT = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_copper_encased_shaft", p -> new ColorfulEncasedShaftBlock(p, color, () -> CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color).get()))
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(CCPBuilderTransformers.colorfulencasedShaft(color))
				.transform(EncasingRegistry.addVariantTo(AllBlocks.SHAFT))
				.transform(axeOrPickaxe())
				.register();
	});

	public static final BlockEntry<CopperGlassEncasedShaftBlock> COPPER_GLASS_ENCASED_SHAFT =
			REGISTRATE.block("copper_glass_encased_shaft", p -> new CopperGlassEncasedShaftBlock(p, CCPPaletteBlocks.COPPER_GLASS_CASING::get))
					.properties(p -> p.mapColor(MapColor.TERRACOTTA_ORANGE).noOcclusion())
					.transform(CCPBuilderTransformers.glassencasedShaft(() -> CCPSpriteShifts.COPPER_GLASS_CASING))
					.transform(EncasingRegistry.addVariantTo(AllBlocks.SHAFT))
					.transform(axeOrPickaxe())
					.register();

	public static final DyedBlockList<ColorfulGlassEncasedShaftBlock> COLORFUL_COPPER_GLASS_ENCASED_SHAFT = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_copper_glass_encased_shaft", p -> new ColorfulGlassEncasedShaftBlock(p, color, () -> CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(color).get()))
				.properties(p -> p.mapColor(color.getMapColor()).noOcclusion())
				.transform(CCPBuilderTransformers.colorfulglassencasedShaft(color))
				.transform(EncasingRegistry.addVariantTo(AllBlocks.SHAFT))
				.transform(axeOrPickaxe())
				.register();
	});

	public static final BlockEntry<CopperTintedGlassEncasedShaftBlock> COPPER_TINTED_GLASS_ENCASED_SHAFT =
			REGISTRATE.block("copper_tinted_glass_encased_shaft", p -> new CopperTintedGlassEncasedShaftBlock(p, CCPPaletteBlocks.COPPER_TINTED_GLASS_CASING::get))
					.properties(p -> p.mapColor(MapColor.TERRACOTTA_ORANGE).noOcclusion())
					.transform(CCPBuilderTransformers.tintedglassencasedShaft(() -> CCPSpriteShifts.COPPER_TINTED_GLASS_CASING))
					.transform(EncasingRegistry.addVariantTo(AllBlocks.SHAFT))
					.transform(axeOrPickaxe())
					.register();

	public static final DyedBlockList<ColorfulTintedGlassEncasedShaftBlock> COLORFUL_TINTED_COPPER_GLASS_ENCASED_SHAFT = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_copper_tinted_glass_encased_shaft", p -> new ColorfulTintedGlassEncasedShaftBlock(p, color, () -> CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(color).get()))
				.properties(p -> p.mapColor(color.getMapColor()).noOcclusion())
				.transform(CCPBuilderTransformers.colorfultintedglassencasedShaft(color))
				.transform(EncasingRegistry.addVariantTo(AllBlocks.SHAFT))
				.transform(axeOrPickaxe())
				.register();
	});

	public static final BlockEntry<CopperEncasedCogwheelBlock> COPPER_ENCASED_COGWHEEL = REGISTRATE
			.block("copper_encased_cogwheel", p -> new CopperEncasedCogwheelBlock(p, false, AllBlocks.COPPER_CASING::get))
			.properties(p -> p.mapColor(MapColor.PODZOL))
			.transform(CCPBuilderTransformers.encasedCogwheel(() -> AllSpriteShifts.COPPER_CASING))
			.transform(EncasingRegistry.addVariantTo(AllBlocks.COGWHEEL))
			.onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.COPPER_CASING,
					Couple.create(CCPSpriteShifts.COPPER_ENCASED_COGWHEEL_SIDE,
							CCPSpriteShifts.COPPER_ENCASED_COGWHEEL_OTHERSIDE))))
			.transform(axeOrPickaxe())
			.register();

	public static final BlockEntry<CopperEncasedCogwheelBlock> COPPER_ENCASED_LARGE_COGWHEEL = REGISTRATE
			.block("copper_encased_large_cogwheel", p -> new CopperEncasedCogwheelBlock(p, true, AllBlocks.COPPER_CASING::get))
			.properties(p -> p.mapColor(MapColor.PODZOL))
			.transform(CCPBuilderTransformers.encasedLargeCogwheel(() -> AllSpriteShifts.COPPER_CASING))
			.transform(EncasingRegistry.addVariantTo(AllBlocks.LARGE_COGWHEEL))
			.transform(axeOrPickaxe())
			.register();

	public static final DyedBlockList<ColorfulEncasedCogwheelBlock> COLORFUL_COPPER_ENCASED_COGWHEEL = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_copper_encased_cogwheel", p -> new ColorfulEncasedCogwheelBlock(p, false, color, () -> CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color).get()))
			.properties(p -> p.mapColor(MapColor.PODZOL))
			.transform(CCPBuilderTransformers.colorfulencasedCogwheel(() -> CCPSpriteShifts.COLORFUL_COPPER_CASING.get(color), color))
			.transform(EncasingRegistry.addVariantTo(AllBlocks.COGWHEEL))
				.onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(CCPSpriteShifts.COLORFUL_COPPER_CASING.get(color),
						Couple.create(CCPSpriteShifts.COLORFUL_COPPER_ENCASED_COGWHEEL_SIDE.get(color),
								CCPSpriteShifts.COLORFUL_COPPER_ENCASED_COGWHEEL_OTHERSIDE.get(color)))))
			.transform(axeOrPickaxe())
			.register();
	});

	public static final DyedBlockList<ColorfulEncasedCogwheelBlock> COLORFUL_COPPER_ENCASED_LARGE_COGWHEEL = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_copper_encased_large_cogwheel", p -> new ColorfulEncasedCogwheelBlock(p, true, color, () -> CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color).get()))
				.properties(p -> p.mapColor(MapColor.PODZOL))
				.transform(CCPBuilderTransformers.colorfulencasedLargeCogwheel(() -> CCPSpriteShifts.COLORFUL_COPPER_CASING.get(color), color))
				.transform(EncasingRegistry.addVariantTo(AllBlocks.LARGE_COGWHEEL))
				.transform(axeOrPickaxe())
				.register();
	});

	public static void register() {
	}
}
