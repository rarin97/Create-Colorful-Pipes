package net.rarin.colorfulpipes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.fluids.drain.ItemDrainBlock;
import com.simibubi.create.content.fluids.pipes.*;
import com.simibubi.create.content.fluids.pipes.valve.FluidValveBlock;
import com.simibubi.create.content.fluids.pump.PumpBlock;
import com.simibubi.create.content.fluids.spout.SpoutBlock;
import com.simibubi.create.content.fluids.tank.FluidTankMovementBehavior;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.rarin.colorfulpipes.Datagen.CCPBlockStateGen;
import net.rarin.colorfulpipes.content.ColorfulEncasedCTBehaviour;
import net.rarin.colorfulpipes.content.ColorfulPipeAttachmentModel;
import net.rarin.colorfulpipes.content.drain.ColorfulDrainBlock;
import net.rarin.colorfulpipes.content.encasedPipe.ColorfulEncasedPipeBlock;
import net.rarin.colorfulpipes.content.glassPipe.ColorfulGlassFluidPipeBlock;
import net.rarin.colorfulpipes.content.hosePulley.ColorfulHosePulleyBlock;
import net.rarin.colorfulpipes.content.pipe.ColorfulFluidPipeBlock;
import net.rarin.colorfulpipes.content.portableFluidInterface.ColorfulPortableFluidInterfaceBlock;
import net.rarin.colorfulpipes.content.portableFluidInterface.ColorfulPortableFluidInterfaceMovement;
import net.rarin.colorfulpipes.content.pump.ColorfulPumpBlock;
import net.rarin.colorfulpipes.content.smartPipe.ColorfulSmartFluidPipeBlock;
import net.rarin.colorfulpipes.content.smartPipe.ColorfulSmartFluidPipeGenerator;
import net.rarin.colorfulpipes.content.spout.ColorfulSpoutBlock;
import net.rarin.colorfulpipes.content.steamEngine.ColorfulSteamEngineBlock;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankBlock;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankGenerator;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankItem;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankModel;
import net.rarin.colorfulpipes.content.valve.ColorfulFluidValveBlock;
import net.rarin.colorfulpipes.CCPTags.ColorfulItemTags;
import net.rarin.colorfulpipes.CCPTags.ColorfulBlockTags;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType.mountedFluidStorage;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class CCPBlocks {
	private static final CreateRegistrate REGISTRATE = ColorfulPipes.REGISTRATE;

	static {
		REGISTRATE.setCreativeTab(CCPCreativeTabs.MAIN);
	}

	public static final DyedBlockList<FluidPipeBlock> COLORFUL_FLUID_PIPES = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_fluid_pipe", p -> new ColorfulFluidPipeBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly())
				.blockstate(BlockStateGen.pipe())
				.onRegister(CCPRegistrate.ColorfulblockModel(() -> ColorfulPipeAttachmentModel::withAO, color))
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(ColorfulItemTags.FLUID_PIPES.tag)
						.unlockedBy("has_fluid_pipe", RegistrateRecipeProvider.has(AllBlocks.FLUID_PIPE.asItem()))
						.save(p, ColorfulPipes.asResource("fluid_pipe/" + c.getName()))
				)
				.tag(ColorfulBlockTags.COLORFUL_PIPES.tag)
				.item()
				.tag(ColorfulItemTags.COLORFUL_PIPES.tag)
				.tag(ColorfulItemTags.FLUID_PIPES.tag)
				.transform(customItemModel())
				.register();
	});

	public static final DyedBlockList<EncasedPipeBlock> COLORFUL_ENCASED_FLUID_PIPES = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_encased_fluid_pipe", p -> new ColorfulEncasedPipeBlock(p, color, () -> CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color).get()))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.noOcclusion().mapColor(color.getMapColor()))
				.transform(axeOrPickaxe())
				.blockstate(CCPBlockStateGen.encasedPipe(color))
				.onRegister(CreateRegistrate.connectedTextures(() -> new ColorfulEncasedCTBehaviour(color)))
				.onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, CCPSpriteShifts.COLORFUL_COPPER_CASING.get(color),
						(s, f) -> !s.getValue(EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(f)))))
				.onRegister(CCPRegistrate.ColorfulblockModel(() -> ColorfulPipeAttachmentModel::withAO, color))
				.loot((p, b) -> p.dropOther(b, COLORFUL_FLUID_PIPES.get(color).get()))
				.transform(EncasingRegistry.addVariantTo(COLORFUL_FLUID_PIPES.get(color)))
				.register();
	});

	public static final DyedBlockList<GlassFluidPipeBlock> COLORFUL_GLASS_FLUID_PIPES = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_glass_fluid_pipe", p -> new ColorfulGlassFluidPipeBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.noOcclusion().mapColor(color.getMapColor()))
				.transform(pickaxeOnly())
				.addLayer(() -> RenderType::cutoutMipped)
				.blockstate((c, p) -> {
					p.getVariantBuilder(c.getEntry()).forAllStatesExcept(state -> {
						Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
						return ConfiguredModel.builder()
								.modelFile(p.models()
										.withExistingParent("block/" + colorName + "_fluid_pipe/window", Create.asResource("block/fluid_pipe/window"))
										.texture("0", "block/glass_fluid_pipe/" + colorName)
										.texture("particle", "block/copper_underside/" + colorName))
								.uvLock(false)
								.rotationX(axis == Direction.Axis.Y ? 0 : 90)
								.rotationY(axis == Direction.Axis.X ? 90 : 0)
								.build();
					}, BlockStateProperties.WATERLOGGED);
				})
				.onRegister(CCPRegistrate.ColorfulblockModel(() -> ColorfulPipeAttachmentModel::withAO, color))
				.loot((p, b) -> p.dropOther(b, COLORFUL_FLUID_PIPES.get(color).get()))
				.register();
	});

	public static final DyedBlockList<SmartFluidPipeBlock> COLORFUL_SMART_FLUID_PIPES = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_smart_fluid_pipe", p -> new ColorfulSmartFluidPipeBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly())
				.blockstate(new ColorfulSmartFluidPipeGenerator(color)::generate)
				.onRegister(CCPRegistrate.ColorfulblockModel(() -> ColorfulPipeAttachmentModel::withAO, color))
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(ColorfulItemTags.SMART_FLUID_PIPES.tag)
						.unlockedBy("has_smart_fluid_pipe", RegistrateRecipeProvider.has(AllBlocks.SMART_FLUID_PIPE.asItem()))
						.save(p, ColorfulPipes.asResource("smart_fluid_pipe/" + c.getName()))
				)
				.tag(ColorfulBlockTags.COLORFUL_SMART_PIPES.tag)
				.item()
				.tag(ColorfulItemTags.COLORFUL_SMART_PIPES.tag)
				.tag(ColorfulItemTags.SMART_FLUID_PIPES.tag)
				.transform(customItemModel(colorName + "_smart_fluid_pipe"))
				.register();
	});

	public static final DyedBlockList<PumpBlock> COLORFUL_PUMPS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_mechanical_pump", p -> new ColorfulPumpBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly())
				.onRegister((block) -> BlockStressValues.IMPACTS.register(block, () -> 4.0))
				.onRegister(CCPRegistrate.ColorfulblockModel(() -> ColorfulPipeAttachmentModel::withAO, color))
				.blockstate((c, p) -> {
					p.directionalBlock(c.get(),p.models().withExistingParent(c.getName(), Create.asResource("block/mechanical_pump/block"))
							.texture("4", ColorfulPipes.asResource("block/pump/" + colorName))
							.texture("particle", ColorfulPipes.asResource("block/pump/" + colorName)));
				})
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(ColorfulItemTags.MECHANICAL_PUMPS.tag)
						.unlockedBy("has_pump", RegistrateRecipeProvider.has(AllBlocks.MECHANICAL_PUMP.asItem()))
						.save(p, ColorfulPipes.asResource("mechanical_pump/" + c.getName()))
				)
				.tag(ColorfulBlockTags.COLORFUL_PUMPS.tag)
				.item()
				.tag(ColorfulItemTags.COLORFUL_PUMPS.tag)
				.tag(ColorfulItemTags.MECHANICAL_PUMPS.tag)
				.model((c, p) ->
						p.withExistingParent(c.getName(), Create.asResource("block/mechanical_pump/item"))
								.texture("4", ColorfulPipes.asResource("block/pump/" + colorName))
				)
				.build()
				.register();
	});

	public static final DyedBlockList<FluidValveBlock> COLORFUL_FLUID_VALVES = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_fluid_valve", p -> new ColorfulFluidValveBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly())
				.addLayer(() -> RenderType::cutoutMipped)
				.blockstate((c, p) -> BlockStateGen.directionalAxisBlock(c, p,
						(state, vertical) -> AssetLookup.partialBaseModel(c, p, vertical ? "vertical" : "horizontal",
								state.getValue(FluidValveBlock.ENABLED) ? "open" : "closed")))
				.onRegister(CCPRegistrate.ColorfulblockModel(() -> ColorfulPipeAttachmentModel::withAO, color))
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(ColorfulItemTags.FLUID_VALVES.tag)
						.unlockedBy("has_fluid_valve", RegistrateRecipeProvider.has(AllBlocks.FLUID_VALVE.asItem()))
						.save(p, ColorfulPipes.asResource("fluid_valve/" + c.getName()))
				)
				.tag(ColorfulBlockTags.COLORFUL_FLUID_VALVES.tag)
				.item()
				.tag(ColorfulItemTags.COLORFUL_FLUID_VALVES.tag)
				.tag(ColorfulItemTags.FLUID_VALVES.tag)
				.transform(customItemModel())
				.register();
	});

	public static final DyedBlockList<ColorfulFluidTankBlock> COLORFUL_FLUID_TANKS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_fluid_tank", p -> new ColorfulFluidTankBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.noOcclusion().isRedstoneConductor((p1, p2, p3) -> true).mapColor(color.getMapColor()))
				.transform(pickaxeOnly())
				.addLayer(() -> RenderType::cutoutMipped)
				.blockstate(new ColorfulFluidTankGenerator(color)::generate)
				.onRegister(CCPRegistrate.ColorfulblockModel(() -> ColorfulFluidTankModel::standard, color))
				.transform(displaySource(CCPDisplaySources.BOILER))
				.transform(mountedFluidStorage(CCPMountedStorageTypes.FLUID_TANK))
				.onRegister(movementBehaviour(new FluidTankMovementBehavior()))
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(ColorfulItemTags.FLUID_TANKS.tag)
						.unlockedBy("has_fluid_tank", RegistrateRecipeProvider.has(AllBlocks.FLUID_TANK.asItem()))
						.save(p, ColorfulPipes.asResource("fluid_tank/" + c.getName()))
				)
				.tag(ColorfulBlockTags.COLORFUL_FLUID_TANKS.tag)
				.item(ColorfulFluidTankItem::new)
				.tag(ColorfulItemTags.COLORFUL_FLUID_TANKS.tag)
				.tag(ColorfulItemTags.FLUID_TANKS.tag)
				.model(AssetLookup.customBlockItemModel("_", "block_single_window"))
				.build()
				.register();
	});

	public static final DyedBlockList<SpoutBlock> COLORFUL_SPOUTS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_spout", p -> new ColorfulSpoutBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly())
				.addLayer(() -> RenderType::cutoutMipped)
				.blockstate((c, p) -> {
					p.simpleBlock(c.get(), p.models().withExistingParent(c.getName(), Create.asResource("block/spout/block"))
							.texture("0", ColorfulPipes.asResource("block/spout/" + colorName))
							.texture("3", ColorfulPipes.asResource("block/encased_pipe/" + colorName))
							.texture("particle", ColorfulPipes.asResource("block/copper_underside/" + colorName)));
				})
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(ColorfulItemTags.SPOUTS.tag)
						.unlockedBy("has_spout", RegistrateRecipeProvider.has(AllBlocks.SPOUT.asItem()))
						.save(p, ColorfulPipes.asResource("spout/" + c.getName()))
				)
				.tag(ColorfulBlockTags.COLORFUL_SPOUTS.tag)
				.item()
				.tag(ColorfulItemTags.COLORFUL_SPOUTS.tag)
				.tag(ColorfulItemTags.SPOUTS.tag)
				.model((c, p) ->
						p.withExistingParent(c.getName(), Create.asResource("block/spout/item"))
								.texture("0", ColorfulPipes.asResource("block/spout/" + colorName))
								.texture("3", ColorfulPipes.asResource("block/spout_nozzle/" + colorName))
								.texture("4", ColorfulPipes.asResource("block/encased_pipe/" + colorName))
				)
				.build()
				.register();
	});

	public static final DyedBlockList<ItemDrainBlock> COLORFUL_DRAINS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_item_drain", p -> new ColorfulDrainBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly())
				.addLayer(() -> RenderType::cutoutMipped)
				.blockstate((c, p) -> {
					p.simpleBlock(c.get(), p.models().withExistingParent(c.getName(), Create.asResource("block/item_drain"))
							.texture("0", ColorfulPipes.asResource("block/item_drain/" + colorName))
							.texture("3", ColorfulPipes.asResource("block/pump/" + colorName))
							.texture("4", ColorfulPipes.asResource("block/copper_underside/" + colorName))
							.texture("particle", ColorfulPipes.asResource("block/item_drain/" + colorName)));
				})
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(ColorfulItemTags.ITEM_DRAINS.tag)
						.unlockedBy("has_item_drain", RegistrateRecipeProvider.has(AllBlocks.ITEM_DRAIN.asItem()))
						.save(p, ColorfulPipes.asResource("item_drain/" + c.getName()))
				)
				.tag(ColorfulBlockTags.COLORFUL_DRAINS.tag)
				.simpleItem()
				.item()
				.tag(ColorfulItemTags.COLORFUL_DRAINS.tag)
				.tag(ColorfulItemTags.ITEM_DRAINS.tag)
				.build()
				.register();
	});

	public static final DyedBlockList<ColorfulPortableFluidInterfaceBlock> COLORFUL_FLUID_INTERFACES = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_portable_fluid_interface", p -> new ColorfulPortableFluidInterfaceBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(axeOrPickaxe())
				.blockstate((c, p) ->
					p.directionalBlock(c.get(), p.models().withExistingParent(c.getName(), Create.asResource("block/portable_fluid_interface/block"))
							.texture("0", ColorfulPipes.asResource("block/portable_fluid_interface/" + colorName))
							.texture("2", ColorfulPipes.asResource("block/copper_underside/" + colorName))
							.texture("particle", ColorfulPipes.asResource("block/copper_underside/" + colorName))))
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(ColorfulItemTags.PORTABLE_FLUID_INTERFACES.tag)
						.unlockedBy("has_portable_fluid_interface", RegistrateRecipeProvider.has(AllBlocks.PORTABLE_FLUID_INTERFACE.asItem()))
						.save(p, ColorfulPipes.asResource("portable_fluid_interface/" + c.getName()))
				)
				.tag(ColorfulBlockTags.COLORFUL_PORTABLE_FLUID_INTERFACES.tag)
				.onRegister(movementBehaviour(new ColorfulPortableFluidInterfaceMovement()))
				.item()
				.tag(ColorfulItemTags.COLORFUL_PORTABLE_FLUID_INTERFACES.tag)
				.tag(ColorfulItemTags.PORTABLE_FLUID_INTERFACES.tag)
				.tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
				.model((c, p) ->
						p.withExistingParent(c.getName(), Create.asResource("block/portable_fluid_interface/item"))
								.texture("0", ColorfulPipes.asResource("block/portable_fluid_interface/" + colorName))
								.texture("2", ColorfulPipes.asResource("block/copper_underside/" + colorName))
								.texture( "particle", ColorfulPipes.asResource("block/copper_underside/" + colorName)))
				.build()
				.register();
	});

	public static final DyedBlockList<ColorfulHosePulleyBlock> COLORFUL_HOSE_PULLEYS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_hose_pulley", p -> new ColorfulHosePulleyBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly())
				.addLayer(() -> RenderType::cutoutMipped)
				.properties(BlockBehaviour.Properties::noOcclusion)
				.blockstate((c, p) ->
						p.horizontalBlock(c.get(), p.models().withExistingParent(c.getName(), Create.asResource("block/hose_pulley/block"))
								.texture("1", ColorfulPipes.asResource("block/hose_pulley/" + colorName))
								.texture("3", ColorfulPipes.asResource("block/pump/" + colorName))
								.texture("particle", ColorfulPipes.asResource("block/copper_underside/" + colorName))))
				.onRegister((block) -> BlockStressValues.IMPACTS.register(block, () -> 4.0))
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(ColorfulItemTags.HOSE_PULLEYS.tag)
						.unlockedBy("has_hose_pulley", RegistrateRecipeProvider.has(AllBlocks.HOSE_PULLEY.asItem()))
						.save(p, ColorfulPipes.asResource("hose_pulley/" + c.getName()))
				)
				.tag(ColorfulBlockTags.COLORFUL_HOSE_PULLEYS.tag)
				.item()
				.tag(ColorfulItemTags.COLORFUL_HOSE_PULLEYS.tag)
				.tag(ColorfulItemTags.HOSE_PULLEYS.tag)
				.model((c, p) ->
						p.withExistingParent(c.getName(), Create.asResource("block/hose_pulley/item"))
								.texture("1", ColorfulPipes.asResource("block/hose_pulley/" + colorName))
								.texture("3", ColorfulPipes.asResource("block/pump/" + colorName))
								.texture( "particle", ColorfulPipes.asResource("block/copper_underside/" + colorName)))
				.build()
				.register();
	});

	public static final DyedBlockList<ColorfulSteamEngineBlock> COLORFUL_STEAM_ENGINES = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_steam_engine", p -> new ColorfulSteamEngineBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly())
				.blockstate((c, p) ->
						p.horizontalFaceBlock(c.get(), p.models().withExistingParent(c.getName(), Create.asResource("block/steam_engine/block"))
						.texture("1", ColorfulPipes.asResource("block/engine/" + colorName))
						.texture("particle", ColorfulPipes.asResource("block/copper_underside/" + colorName))))
				.onRegister((block) -> BlockStressValues.CAPACITIES.register(block, () -> 1024.0))
				.onRegister(BlockStressValues.setGeneratorSpeed(64, true))
				.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
						.requires(color.getTag())
						.requires(ColorfulItemTags.STEAM_ENGINES.tag)
						.unlockedBy("has_steam_engine", RegistrateRecipeProvider.has(AllBlocks.STEAM_ENGINE.asItem()))
						.save(p, ColorfulPipes.asResource("steam_engine/" + c.getName()))
				)
				.tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
				.tag(ColorfulBlockTags.COLORFUL_STEAM_ENGINES.tag)
				.item()
				.tag(ColorfulItemTags.COLORFUL_STEAM_ENGINES.tag)
				.tag(ColorfulItemTags.STEAM_ENGINES.tag)
				.model((c, p) ->
						p.withExistingParent(c.getName(), Create.asResource("block/steam_engine/item"))
								.texture("1", ColorfulPipes.asResource("block/engine/" + colorName))
				)
				.build()
				.register();
	});

	public static void register() {
	}
}
