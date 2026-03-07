package net.rarin.colorfulpipes.Ponder;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.simibubi.create.infrastructure.ponder.scenes.SteamScenes;
import com.simibubi.create.infrastructure.ponder.scenes.fluid.DrainScenes;
import com.simibubi.create.infrastructure.ponder.scenes.fluid.FluidTankScenes;
import com.simibubi.create.infrastructure.ponder.scenes.fluid.HosePulleyScenes;
import com.simibubi.create.infrastructure.ponder.scenes.fluid.PipeScenes;
import com.simibubi.create.infrastructure.ponder.scenes.fluid.PumpScenes;
import com.simibubi.create.infrastructure.ponder.scenes.fluid.SpoutScenes;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;

import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.rarin.colorfulpipes.CCPBlocks;

public class CCPPonderScenes {

	public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
		PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

		HELPER.forComponents(CCPBlocks.COLORFUL_FLUID_PIPES)
				.addStoryBoard("fluid_pipe/flow", PipeScenes::flow, AllCreatePonderTags.FLUIDS)
				.addStoryBoard("fluid_pipe/interaction", PipeScenes::interaction)
				.addStoryBoard("fluid_pipe/encasing", PipeScenes::encasing);
//		HELPER.forComponents(AllBlocks.COPPER_CASING)
//				.addStoryBoard("fluid_pipe/encasing", PipeScenes::encasing);
		HELPER.forComponents(CCPBlocks.COLORFUL_PUMPS)
				.addStoryBoard("mechanical_pump/flow", PumpScenes::flow, AllCreatePonderTags.FLUIDS, AllCreatePonderTags.KINETIC_APPLIANCES)
				.addStoryBoard("mechanical_pump/speed", PumpScenes::speed);
		HELPER.forComponents(CCPBlocks.COLORFUL_FLUID_VALVES)
				.addStoryBoard("fluid_valve", PipeScenes::valve, AllCreatePonderTags.FLUIDS, AllCreatePonderTags.KINETIC_APPLIANCES);
		HELPER.forComponents(CCPBlocks.COLORFUL_SMART_FLUID_PIPES)
				.addStoryBoard("smart_pipe", PipeScenes::smart, AllCreatePonderTags.FLUIDS);
		HELPER.forComponents(CCPBlocks.COLORFUL_FLUID_TANKS)
				.addStoryBoard("fluid_tank/storage", FluidTankScenes::storage, AllCreatePonderTags.FLUIDS)
				.addStoryBoard("fluid_tank/sizes", FluidTankScenes::sizes);
		HELPER.forComponents(CCPBlocks.COLORFUL_HOSE_PULLEYS)
				.addStoryBoard("hose_pulley/intro", HosePulleyScenes::intro, AllCreatePonderTags.FLUIDS, AllCreatePonderTags.KINETIC_APPLIANCES)
				.addStoryBoard("hose_pulley/level", HosePulleyScenes::level)
				.addStoryBoard("hose_pulley/infinite", HosePulleyScenes::infinite);
		HELPER.forComponents(CCPBlocks.COLORFUL_SPOUTS)
				.addStoryBoard("spout", SpoutScenes::filling, AllCreatePonderTags.FLUIDS);
		HELPER.forComponents(CCPBlocks.COLORFUL_DRAINS.toArray())
				.addStoryBoard("item_drain", DrainScenes::emptying, AllCreatePonderTags.FLUIDS);
//		HELPER.forComponents(CCPBlocks.COLORFUL_STEAM_WHISTLES)
//				.addStoryBoard("steam_whistle", SteamScenes::whistle);
//		HELPER.forComponents(CCPBlocks.COLORFUL_STEAM_ENGINES)
//				.addStoryBoard("steam_engine", SteamScenes::engine);
//		HELPER.forComponents(AllBlocks.PORTABLE_FLUID_INTERFACE)
//				.addStoryBoard("portable_interface/transfer_fluid", FluidMovementActorScenes::transfer, AllCreatePonderTags.FLUIDS,


	}
}
