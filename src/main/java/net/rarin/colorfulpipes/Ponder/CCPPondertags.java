package net.rarin.colorfulpipes.Ponder;

import com.tterrag.registrate.util.entry.RegistryEntry;

import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.rarin.colorfulpipes.CCPBlocks;

import static com.simibubi.create.infrastructure.ponder.AllCreatePonderTags.FLUIDS;

public class CCPPondertags {

	public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {

		PonderTagRegistrationHelper<RegistryEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

		HELPER.addToTag(FLUIDS)
				.add(CCPBlocks.COLORFUL_DRAINS.get(DyeColor.RED));
	}
}
