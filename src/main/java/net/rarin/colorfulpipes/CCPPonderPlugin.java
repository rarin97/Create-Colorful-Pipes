package net.rarin.colorfulpipes;

import com.simibubi.create.Create;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.rarin.colorfulpipes.Ponder.CCPPonderScenes;

public class CCPPonderPlugin implements PonderPlugin {
	@Override
	public String getModId() {
		return Create.ID;
	}

	@Override
	public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
		CCPPonderScenes.register(helper);
	}
}
