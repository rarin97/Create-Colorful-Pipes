package net.rarin.colorfulpipes.Ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.simibubi.create.infrastructure.ponder.scenes.MovementActorScenes;
import com.simibubi.create.infrastructure.ponder.scenes.SteamScenes;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.CCPPaletteBlocks;
import net.rarin.colorfulpipes.Ponder.scenes.DrainScenes;
import net.rarin.colorfulpipes.Ponder.scenes.FluidMovementActorScenes;
import net.rarin.colorfulpipes.Ponder.scenes.FluidTankScenes;
import net.rarin.colorfulpipes.Ponder.scenes.HosePulleyScenes;
import net.rarin.colorfulpipes.Ponder.scenes.KineticsScenes;
import net.rarin.colorfulpipes.Ponder.scenes.PipeScenes;
import net.rarin.colorfulpipes.Ponder.scenes.PumpScenes;
import net.rarin.colorfulpipes.Ponder.scenes.SpoutScenes;
import net.rarin.colorfulpipes.Ponder.scenes.TableClothScenes;

public class CCPPonderScenes {

	public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
		PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

		HELPER.forComponents(AllBlocks.SHAFT)
				.addStoryBoard("copper_encasing", KineticsScenes::shaftsCanBeEncased)
				.addStoryBoard("colorful_encasing", KineticsScenes::shaftsCanBecolorEncased)
				.addStoryBoard("tinted_encasing", KineticsScenes::shaftsCanBetintedEncased);

		HELPER.forComponents(AllBlocks.COGWHEEL)
				.addStoryBoard("cog/colorful_encasing", KineticsScenes::cogwheelsCanBeEncased);

		HELPER.forComponents(AllBlocks.LARGE_COGWHEEL)
				.addStoryBoard("cog/colorful_encasing", KineticsScenes::cogwheelsCanBeEncased);

		HELPER.forComponents(CCPBlocks.COLORFUL_FLUID_PIPES)
				.addStoryBoard("fluid_pipe/colorful_flow", PipeScenes::flow, AllCreatePonderTags.FLUIDS)
				.addStoryBoard("fluid_pipe/bowl_interaction", PipeScenes::interaction)
				.addStoryBoard("fluid_pipe/colorful_encasing", PipeScenes::encasing);

		HELPER.forComponents(CCPPaletteBlocks.COLORFUL_COPPER_CASING)
				.addStoryBoard("fluid_pipe/colorful_encasing", PipeScenes::encasing)
				.addStoryBoard("colorful_encasing", KineticsScenes::shaftsCanBecolorEncased)
				.addStoryBoard("cog/colorful_encasing", KineticsScenes::cogwheelsCanBeEncased);

		HELPER.forComponents(CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING)
				.addStoryBoard("colorful_encasing", KineticsScenes::shaftsCanBecolorEncased);

		HELPER.forComponents(CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING)
				.addStoryBoard("tinted_encasing", KineticsScenes::shaftsCanBetintedEncased);

		HELPER.forComponents(AllBlocks.COPPER_CASING, CCPPaletteBlocks.COPPER_GLASS_CASING)
				.addStoryBoard("copper_encasing", KineticsScenes::shaftsCanBeEncased)
				.addStoryBoard("cog/colorful_encasing", KineticsScenes::cogwheelsCanBeEncased);

		HELPER.forComponents(CCPPaletteBlocks.COPPER_TINTED_GLASS_CASING)
				.addStoryBoard("tinted_encasing", KineticsScenes::shaftsCanBetintedEncased);

		HELPER.forComponents(CCPBlocks.COLORFUL_PUMPS)
				.addStoryBoard("mechanical_pump/colorful_flow", PumpScenes::flow, AllCreatePonderTags.FLUIDS, AllCreatePonderTags.KINETIC_APPLIANCES)
				.addStoryBoard("mechanical_pump/colorful_speed", PumpScenes::speed);

		HELPER.forComponents(CCPBlocks.COLORFUL_FLUID_VALVES)
				.addStoryBoard("colorful_fluid_valve", PipeScenes::valve, AllCreatePonderTags.FLUIDS, AllCreatePonderTags.KINETIC_APPLIANCES);

		HELPER.forComponents(CCPBlocks.COLORFUL_SMART_FLUID_PIPES)
				.addStoryBoard("colorful_smart_pipe", PipeScenes::smart, AllCreatePonderTags.FLUIDS);

		HELPER.forComponents(CCPBlocks.COLORFUL_FLUID_TANKS)
				.addStoryBoard("fluid_tank/colorful_storage", FluidTankScenes::storage, AllCreatePonderTags.FLUIDS)
				.addStoryBoard("fluid_tank/colorful_sizes", FluidTankScenes::sizes);

		HELPER.forComponents(CCPBlocks.COLORFUL_HOSE_PULLEYS)
				.addStoryBoard("hose_pulley/colorful_intro", HosePulleyScenes::intro, AllCreatePonderTags.FLUIDS, AllCreatePonderTags.KINETIC_APPLIANCES)
				.addStoryBoard("hose_pulley/colorful_level", HosePulleyScenes::level)
				.addStoryBoard("hose_pulley/colorful_infinite", HosePulleyScenes::infinite);

		HELPER.forComponents(CCPBlocks.COLORFUL_SPOUTS)
				.addStoryBoard("colorful_spout", SpoutScenes::filling, AllCreatePonderTags.FLUIDS);

		HELPER.forComponents(CCPBlocks.COLORFUL_DRAINS)
				.addStoryBoard("colorful_item_drain", DrainScenes::emptying, AllCreatePonderTags.FLUIDS);

		HELPER.forComponents(CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES)
				.addStoryBoard("colorful_steam_whistle", SteamScenes::whistle);

//		HELPER.forComponents(CCPBlocks.COLORFUL_STEAM_ENGINES)
//				.addStoryBoard("steam_engine", SteamScenes::engine);

		HELPER.forComponents(CCPBlocks.COLORFUL_FLUID_INTERFACES)
				.addStoryBoard("portable_interface/colorful_transfer_fluid", FluidMovementActorScenes::transfer, AllCreatePonderTags.FLUIDS,
						AllCreatePonderTags.CONTRAPTION_ACTOR)
				.addStoryBoard("portable_interface/colorful_redstone_fluid", MovementActorScenes::psiRedstone);

		HELPER.forComponents(CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS)
				.addStoryBoard("high_logistics/table_cloth", TableClothScenes::tableCloth);
	}
}
