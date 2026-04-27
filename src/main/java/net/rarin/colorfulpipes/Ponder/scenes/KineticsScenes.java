package net.rarin.colorfulpipes.Ponder.scenes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.rarin.colorfulpipes.CCPPaletteBlocks;
import net.rarin.colorfulpipes.content.encasedcogwheel.ColorfulEncasedCogwheelBlock;
import net.rarin.colorfulpipes.content.encasedcogwheel.CopperEncasedCogwheelBlock;
import net.rarin.colorfulpipes.content.encasedshaft.ColorfulEncasedShaftBlock;
import net.rarin.colorfulpipes.content.encasedshaft.ColorfulGlassEncasedShaftBlock;
import net.rarin.colorfulpipes.content.encasedshaft.ColorfulTintedGlassEncasedShaftBlock;
import net.rarin.colorfulpipes.content.encasedshaft.CopperEncasedShaftBlock;
import net.rarin.colorfulpipes.content.encasedshaft.CopperGlassEncasedShaftBlock;
import net.rarin.colorfulpipes.content.encasedshaft.CopperTintedGlassEncasedShaftBlock;

public class KineticsScenes {

	public static void shaftsCanBeEncased(SceneBuilder builder, SceneBuildingUtil util) {
		CreateSceneBuilder scene = new CreateSceneBuilder(builder);
		scene.title("shaft_casing", "Encasing Shafts");
		scene.configureBasePlate(0, 0, 5);
		scene.world().showSection(util.select().layer(0), Direction.UP);

		Selection shaft = util.select().cuboid(new BlockPos(0, 1, 2), new Vec3i(5, 0, 2));
		Selection andesite = util.select().position(3, 1, 2);
		Selection brass = util.select().position(1, 1, 2);

		scene.world().showSection(shaft, Direction.DOWN);
		scene.idle(20);

		BlockEntry<CopperEncasedShaftBlock> copperEncased = CCPPaletteBlocks.COPPER_ENCASED_SHAFT;
		ItemStack copperCasingItem = AllBlocks.COPPER_CASING.asStack();

		scene.overlay().showControls(util.vector().topOf(3, 1, 2), Pointing.DOWN, 60).rightClick()
				.withItem(copperCasingItem);
		scene.idle(7);
		scene.world().setBlocks(andesite, copperEncased.getDefaultState()
				.setValue(EncasedShaftBlock.AXIS, Axis.X), true);
		scene.world().setKineticSpeed(shaft, 32);
		scene.idle(10);

		BlockEntry<CopperGlassEncasedShaftBlock> copperglassEncased = CCPPaletteBlocks.COPPER_GLASS_ENCASED_SHAFT;
		ItemStack copperglassCasingItem = CCPPaletteBlocks.COPPER_GLASS_CASING.asStack();

		scene.overlay().showControls(util.vector().topOf(1, 0, 2), Pointing.UP, 60).rightClick()
				.withItem(copperglassCasingItem);
		scene.idle(7);
		scene.world().setBlocks(brass, copperglassEncased.getDefaultState()
				.setValue(EncasedShaftBlock.AXIS, Axis.X), true);
		scene.world().setKineticSpeed(shaft, 32);

		scene.idle(10);
		scene.overlay().showText(100)
				.placeNearTarget()
				.text("copper or glass Casing can be used to decorate Shafts")
				.pointAt(util.vector().topOf(1, 1, 2));
		scene.idle(70);
	}

	public static void shaftsCanBecolorEncased(SceneBuilder builder, SceneBuildingUtil util) {
		CreateSceneBuilder scene = new CreateSceneBuilder(builder);
		scene.title("shaft_casing", "Encasing Shafts");
		scene.configureBasePlate(0, 0, 5);
		scene.world().showSection(util.select().layer(0), Direction.UP);

		Selection shaft = util.select().cuboid(new BlockPos(0, 1, 2), new Vec3i(5, 0, 2));
		Selection andesite = util.select().position(3, 1, 2);
		Selection brass = util.select().position(1, 1, 2);

		scene.world().showSection(shaft, Direction.DOWN);
		scene.idle(20);

		BlockEntry<ColorfulEncasedShaftBlock> copperEncased = CCPPaletteBlocks.COLORFUL_COPPER_ENCASED_SHAFT.get(DyeColor.LIGHT_BLUE);
		ItemStack copperCasingItem = CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.LIGHT_BLUE).asStack();

		scene.overlay().showControls(util.vector().topOf(3, 1, 2), Pointing.DOWN, 60).rightClick()
				.withItem(copperCasingItem);
		scene.idle(7);
		scene.world().setBlocks(andesite, copperEncased.getDefaultState()
				.setValue(EncasedShaftBlock.AXIS, Axis.X), true);
		scene.world().setKineticSpeed(shaft, 32);
		scene.idle(10);

		BlockEntry<ColorfulGlassEncasedShaftBlock> copperglassEncased = CCPPaletteBlocks.COLORFUL_COPPER_GLASS_ENCASED_SHAFT.get(DyeColor.GREEN);
		ItemStack copperglassCasingItem = CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(DyeColor.GREEN).asStack();

		scene.overlay().showControls(util.vector().topOf(1, 0, 2), Pointing.UP, 60).rightClick()
				.withItem(copperglassCasingItem);
		scene.idle(7);
		scene.world().setBlocks(brass, copperglassEncased.getDefaultState()
				.setValue(EncasedShaftBlock.AXIS, Axis.X), true);
		scene.world().setKineticSpeed(shaft, 32);

		scene.idle(10);
		scene.overlay().showText(100)
				.placeNearTarget()
				.text("copper or glass Casing can be used to decorate Shafts")
				.pointAt(util.vector().topOf(1, 1, 2));
		scene.idle(70);
	}

	public static void shaftsCanBetintedEncased(SceneBuilder builder, SceneBuildingUtil util) {
		CreateSceneBuilder scene = new CreateSceneBuilder(builder);
		scene.title("shaft_casing", "Encasing Shafts");
		scene.configureBasePlate(0, 0, 5);
		scene.world().showSection(util.select().layer(0), Direction.UP);

		Selection shaft = util.select().cuboid(new BlockPos(0, 1, 2), new Vec3i(5, 0, 2));
		Selection andesite = util.select().position(3, 1, 2);
		Selection brass = util.select().position(1, 1, 2);

		scene.world().showSection(shaft, Direction.DOWN);
		scene.idle(20);

		BlockEntry<CopperTintedGlassEncasedShaftBlock> copperEncased = CCPPaletteBlocks.COPPER_TINTED_GLASS_ENCASED_SHAFT;
		ItemStack copperCasingItem = CCPPaletteBlocks.COPPER_TINTED_GLASS_CASING.asStack();

		scene.overlay().showControls(util.vector().topOf(3, 1, 2), Pointing.DOWN, 60).rightClick()
				.withItem(copperCasingItem);
		scene.idle(7);
		scene.world().setBlocks(andesite, copperEncased.getDefaultState()
				.setValue(EncasedShaftBlock.AXIS, Axis.X), true);
		scene.world().setKineticSpeed(shaft, 32);
		scene.idle(10);

		BlockEntry<ColorfulTintedGlassEncasedShaftBlock> copperglassEncased = CCPPaletteBlocks.COLORFUL_TINTED_COPPER_GLASS_ENCASED_SHAFT.get(DyeColor.MAGENTA);
		ItemStack copperglassCasingItem = CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(DyeColor.MAGENTA).asStack();

		scene.overlay().showControls(util.vector().topOf(1, 0, 2), Pointing.UP, 60).rightClick()
				.withItem(copperglassCasingItem);
		scene.idle(7);
		scene.world().setBlocks(brass, copperglassEncased.getDefaultState()
				.setValue(EncasedShaftBlock.AXIS, Axis.X), true);
		scene.world().setKineticSpeed(shaft, 32);

		scene.idle(10);
		scene.overlay().showText(100)
				.placeNearTarget()
				.text("copper or glass Casing can be used to decorate Shafts")
				.pointAt(util.vector().topOf(1, 1, 2));
		scene.idle(70);
	}

	public static void cogwheelsCanBeEncased(SceneBuilder builder, SceneBuildingUtil util) {
		CreateSceneBuilder scene = new CreateSceneBuilder(builder);
		scene.title("cogwheel_casing", "Encasing Cogwheels");
		scene.configureBasePlate(0, 0, 5);
		scene.world().showSection(util.select().layer(0), Direction.UP);

		Selection large1 = util.select().position(4, 1, 3);
		Selection small1 = util.select().fromTo(3, 1, 2, 3, 2, 2);
		Selection small2 = util.select().position(2, 1, 2);
		Selection large2 = util.select().fromTo(1, 1, 3, 1, 1, 4);
		Selection shaft2 = util.select().position(2, 2, 2);

		scene.world().setKineticSpeed(shaft2, 0);
		scene.idle(10);

		scene.world().showSection(large1, Direction.DOWN);
		scene.idle(5);
		scene.world().showSection(small1, Direction.DOWN);
		scene.world().showSection(small2, Direction.DOWN);
		scene.idle(5);
		scene.world().showSection(large2, Direction.EAST);
		scene.idle(20);

		BlockEntry<CopperEncasedCogwheelBlock> andesiteEncased = CCPPaletteBlocks.COPPER_ENCASED_COGWHEEL;
		ItemStack andesiteCasingItem = AllBlocks.COPPER_CASING.asStack();

		scene.overlay().showControls(util.vector().topOf(3, 0, 2), Pointing.UP, 100).rightClick()
				.withItem(andesiteCasingItem);
		scene.idle(7);
		scene.world().setBlocks(util.select().position(3, 1, 2), andesiteEncased.getDefaultState()
				.setValue(EncasedCogwheelBlock.AXIS, Axis.Y)
				.setValue(EncasedCogwheelBlock.TOP_SHAFT, true), true);
		scene.world().setKineticSpeed(util.select().position(3, 1, 2), -32);
		scene.idle(15);

		scene.overlay().showControls(util.vector().topOf(2, 1, 2), Pointing.DOWN, 30).rightClick()
				.withItem(andesiteCasingItem);
		scene.idle(7);
		scene.world().setBlocks(small2, andesiteEncased.getDefaultState()
				.setValue(EncasedCogwheelBlock.AXIS, Axis.Y), true);
		scene.world().setKineticSpeed(small2, 32);
		scene.idle(15);

		BlockEntry<ColorfulEncasedCogwheelBlock> brassEncased = CCPPaletteBlocks.COLORFUL_COPPER_ENCASED_LARGE_COGWHEEL.get(DyeColor.WHITE);
		ItemStack brassCasingItem = CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.WHITE).asStack();

		scene.overlay().showControls(util.vector().topOf(1, 0, 3), Pointing.UP, 60).rightClick()
				.withItem(brassCasingItem);
		scene.idle(7);
		scene.world().setBlocks(util.select().position(1, 1, 3), brassEncased.getDefaultState()
				.setValue(EncasedCogwheelBlock.AXIS, Axis.Y), true);
		scene.world().setKineticSpeed(util.select().position(1, 1, 3), -16);

		scene.idle(10);
		scene.overlay().showText(70)
				.placeNearTarget()
				.attachKeyFrame()
				.text("Copper or Colored Casing can be used to decorate Cogwheels")
				.pointAt(util.vector().topOf(1, 1, 3));
		scene.idle(80);

		ElementLink<WorldSectionElement> shaftLink = scene.world().showIndependentSection(shaft2, Direction.DOWN);
		scene.idle(15);
		scene.overlay().showText(90)
				.placeNearTarget()
				.colored(PonderPalette.RED)
				.attachKeyFrame()
				.text("Components added after encasing will not connect to the shaft outputs")
				.pointAt(util.vector().centerOf(2, 2, 2));
		scene.idle(90);

		scene.world().moveSection(shaftLink, new Vec3(0, .5f, 0), 10);
		scene.idle(10);

		scene.addKeyframe();
		Vec3 wrenchHere = util.vector().topOf(2, 1, 2)
				.add(.25, 0, -.25);
		scene.overlay().showControls(wrenchHere, Pointing.RIGHT, 25).rightClick()
				.withItem(AllItems.WRENCH.asStack());
		scene.idle(7);
		scene.world().cycleBlockProperty(util.grid().at(2, 1, 2), EncasedCogwheelBlock.TOP_SHAFT);
		scene.idle(15);
		scene.world().moveSection(shaftLink, new Vec3(0, -.5f, 0), 10);
		scene.idle(10);
		scene.world().setKineticSpeed(shaft2, 32);
		scene.effects().rotationDirectionIndicator(util.grid().at(2, 2, 2));
		scene.idle(20);

		scene.overlay().showText(90)
				.placeNearTarget()
				.colored(PonderPalette.GREEN)
				.text("The Wrench can be used to toggle connections")
				.pointAt(wrenchHere.add(-.5, 0, .5));
		scene.idle(40);

		scene.overlay().showControls(wrenchHere, Pointing.RIGHT, 25).rightClick()
				.withItem(AllItems.WRENCH.asStack());
		scene.idle(7);
		scene.world().cycleBlockProperty(util.grid().at(2, 1, 2), EncasedCogwheelBlock.TOP_SHAFT);
		scene.world().setKineticSpeed(shaft2, 0);
	}
}
