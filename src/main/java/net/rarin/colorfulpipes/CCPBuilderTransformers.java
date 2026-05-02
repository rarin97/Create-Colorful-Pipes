package net.rarin.colorfulpipes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.contraptions.behaviour.DoorMovingInteraction;
import com.simibubi.create.content.decoration.MetalScaffoldingBlockItem;
import com.simibubi.create.content.decoration.MetalScaffoldingCTBehaviour;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorMovementBehaviour;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockItem;
import com.simibubi.create.content.logistics.tableCloth.TableClothModel;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.rarin.colorfulpipes.content.ColorfulEncasedCTBehaviour;
import net.rarin.colorfulpipes.CCPTags.ColorfulItemTags;
import net.rarin.colorfulpipes.CCPTags.ColorfulBlockTags;
import net.rarin.colorfulpipes.content.ColorfulGlassCTBehaviour;
import net.rarin.colorfulpipes.content.ColorfulTintedGlassCTBehaviour;
import net.rarin.colorfulpipes.content.slidingDoor.ColorfulSlidingDoorBlock;

import java.util.function.Supplier;

import static com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour.interactionBehaviour;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.BlockStateGen.axisBlock;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class CCPBuilderTransformers {

	public static <B extends CasingBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> colorfulCasing(DyeColor color) {
		return b -> b.initialProperties(SharedProperties::stone)
				.properties(p -> p.sound(SoundType.COPPER))
				.transform(axeOrPickaxe())
				.blockstate((c, p) -> p.simpleBlock(c.get(), p.models().cubeAll(c.getName(),
								ColorfulPipes.asResource("block/copper_casing/" + color.getSerializedName()))))
				.onRegister(connectedTextures(() ->new ColorfulEncasedCTBehaviour(color)))
				.onRegister(casingConnectivity((block, cc) -> cc.make(block, CCPSpriteShifts.COLORFUL_COPPER_CASING.get(color))))
				.tag(AllTags.AllBlockTags.CASING.tag)
				.tag(CCPTags.ColorfulBlockTags.COLORFUL_COPPER_CASINGS.tag)
				.item()
				.tag(CCPTags.ColorfulItemTags.COLORFUL_COPPER_CASINGS.tag)
				.tag(CCPTags.ColorfulItemTags.COPPER_CASINGS.tag)
				.tag(AllTags.AllItemTags.CASING.tag)
				.build();
	}

	public static <B extends CasingBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> colorfulglassCasing(DyeColor color) {
		return b -> b.initialProperties(SharedProperties::stone)
				.properties(p -> p.sound(SoundType.COPPER).noOcclusion())
				.addLayer(() -> RenderType::cutoutMipped)
				.transform(axeOrPickaxe())
				.blockstate((c, p) -> p.simpleBlock(c.get(), p.models().cubeAll(c.getName(),
						ColorfulPipes.asResource("block/copper_glass_casing/" + color.getSerializedName()))))
				.onRegister(connectedTextures(() ->new ColorfulGlassCTBehaviour(color)))
				.onRegister(casingConnectivity((block, cc) -> cc.make(block, CCPSpriteShifts.COLORFUL_COPPER_GLASS_CASING.get(color))))
				.tag(AllTags.AllBlockTags.CASING.tag)
				.tag(CCPTags.ColorfulBlockTags.COLORFUL_COPPER_GLASS_CASINGS.tag)
				.item()
				.tag(AllTags.AllItemTags.CASING.tag)
				.tag(CCPTags.ColorfulItemTags.COLORFUL_COPPER_GLASS_CASINGS.tag)
				.tag(CCPTags.ColorfulItemTags.COPPER_GLASS_CASINGS.tag)
				.build();
	}

	public static <B extends CasingBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> copperglassCasing(Supplier<CTSpriteShiftEntry> ct) {
		return b -> b.initialProperties(SharedProperties::stone)
				.properties(p -> p.sound(SoundType.COPPER).noOcclusion())
				.addLayer(() -> RenderType::cutoutMipped)
				.transform(axeOrPickaxe())
				.blockstate((c, p) -> p.simpleBlock(c.get(), p.models().cubeAll(c.getName(),
						ColorfulPipes.asResource("block/copper_glass_casing/copper_glass_casing"))))
				.onRegister(connectedTextures(() -> new EncasedCTBehaviour(ct.get())))
				.onRegister(casingConnectivity((block, cc) -> cc.make(block, ct.get())))
				.tag(AllTags.AllBlockTags.CASING.tag)
				.item()
				.tag(CCPTags.ColorfulItemTags.COPPER_GLASS_CASINGS.tag)
				.tag(AllTags.AllItemTags.CASING.tag)
				.build();
	}

	public static <B extends CasingBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> colorfultintedglassCasing(DyeColor color) {
		return b -> b.initialProperties(SharedProperties::stone)
				.properties(p -> p.sound(SoundType.COPPER).noOcclusion())
				.addLayer(() -> RenderType::translucent)
				//.addLayer(() -> RenderType::cutoutMipped)
				.transform(axeOrPickaxe())
				.blockstate((c, p) -> p.simpleBlock(c.get(), p.models().cubeAll(c.getName(),
						ColorfulPipes.asResource("block/copper_tinted_glass_casing/" + color.getSerializedName()))))
				.onRegister(connectedTextures(() ->new ColorfulTintedGlassCTBehaviour(color)))
				.onRegister(casingConnectivity((block, cc) -> cc.make(block, CCPSpriteShifts.COLORFUL_COPPER_TINTED_GLASS_CASING.get(color))))
				.tag(AllTags.AllBlockTags.CASING.tag)
				.tag(CCPTags.ColorfulBlockTags.COLORFUL_COPPER_TINTED_GLASS_CASINGS.tag)
				.item()
				.tag(AllTags.AllItemTags.CASING.tag)
				.tag(CCPTags.ColorfulItemTags.COLORFUL_COPPER_TINTED_GLASS_CASINGS.tag)
				.tag(CCPTags.ColorfulItemTags.COPPER_TINTED_GLASS_CASINGS.tag)
				.build();
	}

	public static <B extends CasingBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> coppertintedglassCasing(Supplier<CTSpriteShiftEntry> ct) {
		return b -> b.initialProperties(SharedProperties::stone)
				.properties(p -> p.sound(SoundType.COPPER).noOcclusion())
				.addLayer(() -> RenderType::translucent)
				//.addLayer(() -> RenderType::cutoutMipped)
				.transform(axeOrPickaxe())
				.blockstate((c, p) -> p.simpleBlock(c.get(), p.models().cubeAll(c.getName(),
						ColorfulPipes.asResource("block/copper_tinted_glass_casing/copper_tinted_glass_casing"))))
				.onRegister(connectedTextures(() -> new EncasedCTBehaviour(ct.get())))
				.onRegister(casingConnectivity((block, cc) -> cc.make(block, ct.get())))
				.tag(AllTags.AllBlockTags.CASING.tag)
				.item()
				.tag(CCPTags.ColorfulItemTags.COPPER_TINTED_GLASS_CASINGS.tag)
				.tag(AllTags.AllItemTags.CASING.tag)
				.build();
	}

	public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfultableCloth(DyeColor color,
																						   NonNullSupplier<? extends Block> initialProps, boolean dyed) {
		return b -> {
			TagKey<Block> soundTag = dyed ? BlockTags.COMBINATION_STEP_SOUND_BLOCKS : BlockTags.INSIDE_STEP_SOUND_BLOCKS;

			ItemBuilder<TableClothBlockItem, BlockBuilder<B, P>> item = b.initialProperties(initialProps)
					.addLayer(() -> RenderType::cutoutMipped)
					.blockstate((c, p) -> p.simpleBlock(c.get(), p.models()
							.withExistingParent(c.getName(), Create.asResource("block/table_cloth/block"))
							.texture("0", ColorfulPipes.asResource("block/table_cloth/" + color.getSerializedName()))))
					.onRegister(CreateRegistrate.blockModel(() -> TableClothModel::new))
					.tag(AllTags.AllBlockTags.TABLE_CLOTHS.tag, soundTag)
					.tag(ColorfulBlockTags.COLORFUL_TABLE_CLOTHS.tag)
					.onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.table_cloth"))
					.item(TableClothBlockItem::new);

			return item.model((c, p) -> p.withExistingParent(c.getName(), Create.asResource("block/table_cloth/item"))
					.texture("0", ColorfulPipes.asResource("block/table_cloth/" + color.getSerializedName())))
					.tag(AllTags.AllItemTags.TABLE_CLOTHS.tag)
					.tag(ColorfulItemTags.COLORFUL_TABLE_CLOTHS.tag)
					.tag(ColorfulItemTags.COPPER_TABLE_CLOTHS.tag)
					.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
							.requires(c.get())
							.unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
							.save(p, ColorfulPipes.asResource("copper_table_cloth/" + c.getName() + "_clear")))
					.build();
		};
	}

	public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfulladder(DyeColor color) {
		return b -> b.initialProperties(() -> Blocks.LADDER)
				.addLayer(() -> RenderType::cutout)
				.properties(p -> p.mapColor(color))
				.blockstate((c, p) -> p.horizontalBlock(c.get(), p.models()
						.withExistingParent(c.getName(), Create.asResource("block/ladder"))
						.texture("0", ColorfulPipes.asResource("block/ladder_hoop/" + color ))
						.texture("1", ColorfulPipes.asResource("block/ladder/" + color))
						.texture("particle",ColorfulPipes.asResource("block/ladder/" + color))))
				.properties(p -> p.sound(SoundType.COPPER))
				.transform(pickaxeOnly())
				.tag(BlockTags.CLIMBABLE)
				.tag(ColorfulBlockTags.COLORFUL_COPPER_LADDERS.tag)
				.item()
				.tag(ColorfulItemTags.COLORFUL_COPPER_LADDERS.tag)
				.tag(ColorfulItemTags.COPPER_LADDERS.tag)
				.model((c, p) -> p.blockSprite(c::get, p.modLoc("block/ladder/" + color)))
				.build();
	}

	public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfulscaffold(CTSpriteShiftEntry scaffoldShift, CTSpriteShiftEntry scaffoldInsideShift, CTSpriteShiftEntry casingShift) {
		return b -> b.initialProperties(() -> Blocks.SCAFFOLDING)
				.properties(p -> p.sound(SoundType.COPPER))
				.addLayer(() -> RenderType::cutout)
				.onRegister(connectedTextures(() -> new MetalScaffoldingCTBehaviour(scaffoldShift, scaffoldInsideShift, casingShift)))
				.transform(pickaxeOnly())
				.tag(BlockTags.CLIMBABLE)
				.item(MetalScaffoldingBlockItem::new)
				.tag(ColorfulItemTags.COLORFUL_COPPER_SCAFFOLDS.tag)
				.tag(ColorfulItemTags.COPPER_SCAFFOLDS.tag)
				.build();
	}


	public static <B extends ColorfulSlidingDoorBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfulslidingDoor(String type) {
		return b -> b.initialProperties(() -> Blocks.IRON_DOOR)
				.properties(p -> p.requiresCorrectToolForDrops().strength(3.0F, 6.0F))
				.blockstate((c, p) -> {
					ModelFile bottom = AssetLookup.partialBaseModel(c, p, "bottom");
					ModelFile top = AssetLookup.partialBaseModel(c, p, "top");
					p.doorBlock(c.get(), bottom, bottom, bottom, bottom, top, top, top, top);
				})
				.addLayer(() -> RenderType::cutoutMipped)
				.transform(pickaxeOnly())
				.onRegister(interactionBehaviour(new DoorMovingInteraction()))
				.onRegister(movementBehaviour(new SlidingDoorMovementBehaviour()))
				.tag(BlockTags.DOORS)
				.tag(BlockTags.WOODEN_DOORS) // for villager AI
				.tag(AllTags.AllBlockTags.NON_DOUBLE_DOOR.tag)
				.tag(ColorfulBlockTags.COLORFUL_COPPER_DOORS.tag)
				.loot((lr, block) -> lr.add(block, lr.createDoorTable(block)))
				.item()
				.tag(ItemTags.DOORS)
				.tag(ColorfulItemTags.COLORFUL_COPPER_DOORS.tag)
				.tag(ColorfulItemTags.COPPER_DOORS.tag)
				.tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
				.model((c, p) -> p.blockSprite(c, ColorfulPipes.asResource("item/" + type + "_door")))
				.build();
	}

	public static <B extends EncasedShaftBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> encasedShaft(Supplier<CTSpriteShiftEntry> casingShift) {
		return builder -> encasedBase(builder, AllBlocks.SHAFT::get)
				.onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(casingShift.get())))
				.onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, casingShift.get(),
						(s, f) -> f.getAxis() != s.getValue(EncasedShaftBlock.AXIS))))
				.blockstate((c, p) -> axisBlock(c, p, blockState -> p.models()
						.withExistingParent(c.getName(), Create.asResource("block/encased_shaft/block"))
						.texture("casing", Create.asResource("block/copper_casing"))
						.texture("opening", ColorfulPipes.asResource("block/copper_gearbox/copper_gearbox")), true));
	}

	public static <B extends EncasedShaftBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfulencasedShaft(DyeColor color) {
		return builder -> encasedBase(builder, AllBlocks.SHAFT::get)
				.onRegister(connectedTextures(() ->new ColorfulEncasedCTBehaviour(color)))
				.onRegister(casingConnectivity((block, cc) -> cc.make(block, CCPSpriteShifts.COLORFUL_COPPER_CASING.get(color),
						(s, f) -> f.getAxis() != s.getValue(EncasedShaftBlock.AXIS))))
				.blockstate((c, p) -> axisBlock(c, p, blockState -> p.models()
						.withExistingParent(c.getName(), Create.asResource("block/encased_shaft/block"))
						.texture("casing", ColorfulPipes.asResource("block/copper_casing/" + color.getSerializedName()))
						.texture("opening", ColorfulPipes.asResource("block/copper_gearbox/" + color.getSerializedName())), true));
	}

	public static <B extends EncasedShaftBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> glassencasedShaft(Supplier<CTSpriteShiftEntry> casingShift) {
		return builder -> encasedBase(builder, AllBlocks.SHAFT::get)
				.addLayer(() -> RenderType::cutoutMipped)
				.onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(casingShift.get())))
				.onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, casingShift.get(),
						(s, f) -> f.getAxis() != s.getValue(EncasedShaftBlock.AXIS))))
				.blockstate((c, p) -> axisBlock(c, p, blockState -> p.models()
						.withExistingParent(c.getName(), ColorfulPipes.asResource("block/encased_shaft/block"))
						.texture("casing", ColorfulPipes.asResource("block/copper_glass_casing/copper_glass_casing"))
						.texture("opening", ColorfulPipes.asResource("block/copper_gearbox/copper_gearbox")), true));
	}

	public static <B extends EncasedShaftBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfulglassencasedShaft(DyeColor color) {
		return builder -> encasedBase(builder, AllBlocks.SHAFT::get)
				.addLayer(() -> RenderType::cutoutMipped)
				.onRegister(connectedTextures(() ->new ColorfulGlassCTBehaviour(color)))
				.onRegister(casingConnectivity((block, cc) -> cc.make(block, CCPSpriteShifts.COLORFUL_COPPER_GLASS_CASING.get(color),
						(s, f) -> f.getAxis() != s.getValue(EncasedShaftBlock.AXIS))))
				.blockstate((c, p) -> axisBlock(c, p, blockState -> p.models()
						.withExistingParent(c.getName(), ColorfulPipes.asResource("block/encased_shaft/block"))
						.texture("casing", ColorfulPipes.asResource("block/copper_glass_casing/" + color.getSerializedName()))
						.texture("opening", ColorfulPipes.asResource("block/copper_gearbox/" + color.getSerializedName())), true));
	}

	public static <B extends EncasedShaftBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> tintedglassencasedShaft(Supplier<CTSpriteShiftEntry> casingShift) {
		return builder -> encasedBase(builder, AllBlocks.SHAFT::get)
				.addLayer(() -> RenderType::translucent)
				.onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(casingShift.get())))
				.onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, casingShift.get(),
						(s, f) -> f.getAxis() != s.getValue(EncasedShaftBlock.AXIS))))
				.blockstate((c, p) -> axisBlock(c, p, blockState -> p.models()
						.withExistingParent(c.getName(), ColorfulPipes.asResource("block/encased_shaft/block"))
						.texture("casing", ColorfulPipes.asResource("block/copper_tinted_glass_casing/copper_tinted_glass_casing"))
						.texture("opening", ColorfulPipes.asResource("block/copper_gearbox/copper_gearbox")), true));
	}

	public static <B extends EncasedShaftBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfultintedglassencasedShaft(DyeColor color) {
		return builder -> encasedBase(builder, AllBlocks.SHAFT::get)
				.addLayer(() -> RenderType::translucent)
				.onRegister(connectedTextures(() ->new ColorfulTintedGlassCTBehaviour(color)))
				.onRegister(casingConnectivity((block, cc) -> cc.make(block, CCPSpriteShifts.COLORFUL_COPPER_TINTED_GLASS_CASING.get(color),
						(s, f) -> f.getAxis() != s.getValue(EncasedShaftBlock.AXIS))))
				.blockstate((c, p) -> axisBlock(c, p, blockState -> p.models()
						.withExistingParent(c.getName(), ColorfulPipes.asResource("block/encased_shaft/block"))
						.texture("casing", ColorfulPipes.asResource("block/copper_tinted_glass_casing/" + color.getSerializedName()))
						.texture("opening", ColorfulPipes.asResource("block/copper_gearbox/" + color.getSerializedName())), true));
	}

	public static <B extends EncasedCogwheelBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> encasedCogwheel(Supplier<CTSpriteShiftEntry> casingShift) {
		return b -> encasedCogwheelBase(b, casingShift, AllBlocks.COGWHEEL::get, false);
	}

	public static <B extends EncasedCogwheelBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> encasedLargeCogwheel(Supplier<CTSpriteShiftEntry> casingShift) {
		return b -> encasedCogwheelBase(b, casingShift, AllBlocks.LARGE_COGWHEEL::get, true)
				.onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(casingShift.get())));
	}

	public static <B extends EncasedCogwheelBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfulencasedCogwheel(Supplier<CTSpriteShiftEntry> casingShift, DyeColor color) {
		return b -> colorfulencasedCogwheelBase(b, casingShift, AllBlocks.COGWHEEL::get, false, color);
	}

	public static <B extends EncasedCogwheelBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfulencasedLargeCogwheel(Supplier<CTSpriteShiftEntry> casingShift, DyeColor color) {
		return b -> colorfulencasedCogwheelBase(b, casingShift, AllBlocks.LARGE_COGWHEEL::get, true, color)
				.onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(casingShift.get())));
	}

	private static <B extends EncasedCogwheelBlock, P> BlockBuilder<B, P> encasedCogwheelBase(BlockBuilder<B, P> b, Supplier<CTSpriteShiftEntry> casingShift,
																							  Supplier<ItemLike> drop, boolean large) {
		String encasedSuffix = "_encased_cogwheel_side" + (large ? "_connected" : "");
		String blockFolder = large ? "encased_large_cogwheel" : "encased_cogwheel";
		return encasedBase(b, drop)
				.addLayer(() -> RenderType::cutoutMipped)
				.onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, casingShift.get(),
						(s, f) -> f.getAxis() == s.getValue(EncasedCogwheelBlock.AXIS)
								&& !s.getValue(f.getAxisDirection() == Direction.AxisDirection.POSITIVE ? EncasedCogwheelBlock.TOP_SHAFT
								: EncasedCogwheelBlock.BOTTOM_SHAFT))))
				.blockstate((c, p) -> axisBlock(c, p, blockState -> {
					String suffix = (blockState.getValue(EncasedCogwheelBlock.TOP_SHAFT) ? "_top" : "")
							+ (blockState.getValue(EncasedCogwheelBlock.BOTTOM_SHAFT) ? "_bottom" : "");
					String modelName = c.getName() + suffix;
					return p.models()
							.withExistingParent(modelName, Create.asResource("block/" + blockFolder + "/block" + suffix))
							.texture("casing", Create.asResource("block/copper_casing"))
							.texture("particle", Create.asResource("block/copper_casing"))
							.texture("4", ColorfulPipes.asResource("block/copper_gearbox/copper_gearbox"))
							.texture("1",  ColorfulPipes.asResource("block/copper_shaft"))
							.texture("side", ColorfulPipes.asResource("block/copper" + encasedSuffix + "/copper" + encasedSuffix));
				}, false))
				.item()
				.model((c, p) -> p.withExistingParent(c.getName(), Create.asResource("block/" + blockFolder + "/item"))
						.texture("casing", Create.asResource("block/copper_casing"))
						.texture("particle", Create.asResource("block/copper_casing"))
						.texture("1",  ColorfulPipes.asResource("block/copper_shaft"))
						.texture("side", ColorfulPipes.asResource("block/copper" + encasedSuffix + "/copper" + encasedSuffix)))
				.build();
	}

	private static <B extends EncasedCogwheelBlock, P> BlockBuilder<B, P> colorfulencasedCogwheelBase(BlockBuilder<B, P> b, Supplier<CTSpriteShiftEntry> casingShift,
																							  Supplier<ItemLike> drop, boolean large, DyeColor color) {
		String encasedSuffix = "_encased_cogwheel_side" + (large ? "_connected" : "");
		String blockFolder = large ? "encased_large_cogwheel" : "encased_cogwheel";
		return encasedBase(b, drop)
				.addLayer(() -> RenderType::cutoutMipped)
				.onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, casingShift.get(),
						(s, f) -> f.getAxis() == s.getValue(EncasedCogwheelBlock.AXIS)
								&& !s.getValue(f.getAxisDirection() == Direction.AxisDirection.POSITIVE ? EncasedCogwheelBlock.TOP_SHAFT
								: EncasedCogwheelBlock.BOTTOM_SHAFT))))
				.blockstate((c, p) -> axisBlock(c, p, blockState -> {
					String suffix = (blockState.getValue(EncasedCogwheelBlock.TOP_SHAFT) ? "_top" : "")
							+ (blockState.getValue(EncasedCogwheelBlock.BOTTOM_SHAFT) ? "_bottom" : "");
					String modelName = c.getName() + suffix;
					return p.models()
							.withExistingParent(modelName, Create.asResource("block/" + blockFolder + "/block" + suffix))
							.texture("casing", ColorfulPipes.asResource("block/copper_casing/" + color.getSerializedName()))
							.texture("particle", ColorfulPipes.asResource("block/copper_casing/" + color.getSerializedName()))
							.texture("4", ColorfulPipes.asResource("block/copper_gearbox/" + color.getSerializedName()))
							.texture("1",  ColorfulPipes.asResource("block/copper_shaft"))
							.texture("side", ColorfulPipes.asResource("block/copper" + encasedSuffix + "/" + color.getSerializedName()));
				}, false));
//				.item()
//				.model((c, p) -> p.withExistingParent(c.getName(), Create.asResource("block/" + blockFolder + "/item"))
//						.texture("casing", ColorfulPipes.asResource("block/copper_casing/" + color.getSerializedName()))
//						.texture("particle", ColorfulPipes.asResource("block/copper_casing/" + color.getSerializedName()))
//						.texture("1",  ColorfulPipes.asResource("block/copper_shaft"))
//						.texture("side", ColorfulPipes.asResource("block/copper" + encasedSuffix + "/" + color.getSerializedName())))
//				.build();
	}

	private static <B extends RotatedPillarKineticBlock, P> BlockBuilder<B, P> encasedBase(BlockBuilder<B, P> b,
																						   Supplier<ItemLike> drop) {
		return b.initialProperties(SharedProperties::stone)
				.properties(BlockBehaviour.Properties::noOcclusion)
				.onRegister((block) -> BlockStressValues.IMPACTS.register(block, () -> 0))
				.loot((p, lb) -> p.dropOther(lb, drop.get()));
	}



}
