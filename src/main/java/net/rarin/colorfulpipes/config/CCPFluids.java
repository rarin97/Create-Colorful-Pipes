package net.rarin.colorfulpipes.config;

import net.createmod.catnip.config.ConfigBase;

public class CCPFluids extends ConfigBase {

	public final ConfigBool MixedPipeConnections = b(false, "allowPipesConnections", Comments.Pipe);
	public final ConfigBool MixedTankConnections = b(true, "allowPipesTankConnections", Comments.Tank);
	public final ConfigBool MixedVesselConnections = b(true, "allowPipesVesselConnections", Comments.Vessel);

	@Override
	public String getName() {
		return "Fluids";
	}

	static class Comments {
		static String Pipe = "Pipes can only connect to pipes of the same color. If true, all pipes can connect freely.";
		static String Tank = "Pipes can only connect to tanks of the same color. If true, all pipes can connect freely to tanks.";
		static String Vessel = "Pipes can only connect to vessels of the same color. If true, all pipes can connect freely to vessels. [Create: Connected]";
	}
}
