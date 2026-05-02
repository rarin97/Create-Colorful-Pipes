package net.rarin.colorfulpipes.compat.Create_Connected;

import com.hlysine.create_connected.config.FeatureCategory;
import com.hlysine.create_connected.config.FeatureToggle;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselGenerator;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.api.contraption.BlockMovementChecks;
import com.simibubi.create.content.fluids.tank.FluidTankMovementBehavior;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.rarin.colorfulpipes.*;
import net.rarin.colorfulpipes.compat.Create_Connected.content.ColorfulFluidVesselBlock;
import net.rarin.colorfulpipes.compat.Create_Connected.content.ColorfulFluidVesselItem;
import net.rarin.colorfulpipes.compat.Create_Connected.content.ColorfulFluidVesselModel;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType.mountedFluidStorage;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class CCBlocks {
	private static final CreateRegistrate REGISTRATE = ColorfulPipes.REGISTRATE;

	static {
		REGISTRATE.setCreativeTab(CCPCreativeTabs.MAIN);
	}

	public static final DyedBlockList<ColorfulFluidVesselBlock> COLORFUL_FLUID_VESSELS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_fluid_vessel", p -> new ColorfulFluidVesselBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.noOcclusion().isRedstoneConductor((p1, p2, p3) -> true))
				.transform(pickaxeOnly())
				.transform(FeatureToggle.register(FeatureCategory.LOGISTICS))
				.blockstate(new FluidVesselGenerator()::generate)
				.onRegister(CCPRegistrate.ColorfulblockModel(() -> ColorfulFluidVesselModel::standard, color))
				.onRegister(b -> BlockMovementChecks.registerAttachedCheck((state, world, pos, direction) -> {
					if (state.getBlock() instanceof FluidVesselBlock)
						return BlockMovementChecks.CheckResult.of(ConnectivityHandler.isConnected(world, pos, pos.relative(direction)));
					return BlockMovementChecks.CheckResult.PASS;
				}))
				.transform(displaySource(CCPDisplaySources.BOILER))
				.transform(mountedFluidStorage(CCMountedStorageTypes.FLUID_VESSEL))
				.onRegister(movementBehaviour(new FluidTankMovementBehavior()))
				.addLayer(() -> RenderType::cutoutMipped)
				.item(ColorfulFluidVesselItem::new)
				.tag(CCPTags.ColorfulItemTags.FLUID_VESSELS.tag)
				.tag(CCPTags.ColorfulItemTags.COLORFUL_FLUID_VESSELS.tag)
				.model(AssetLookup.customBlockItemModel("_", "block_x_single_window"))
				.build()
				.register();
	});

	public static void register() {
	}
}
