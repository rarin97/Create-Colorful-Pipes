package net.rarin.colorfulpipes.config;

import net.createmod.catnip.config.ConfigBase;

public class CCPServer extends ConfigBase {

	public final CCPStress stressValues = nested(2, CCPStress::new, Comments.stress);
	public final CCPRecipes recipes = nested(1, CCPRecipes::new, Comments.recipes);
	public final CCPFluids fluids = nested(1, CCPFluids::new, Comments.fluids);

	private static class Comments {
		static String stress = "Fine tune the kinetic stats of individual components";
		static String recipes = "Packmakers' control panel for internal recipe compat";
		static String fluids = "Configure behavior of fluid components";
	}

	@Override
	public String getName() {
		return "server";
	}

}
