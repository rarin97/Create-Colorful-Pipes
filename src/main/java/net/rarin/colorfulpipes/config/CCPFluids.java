package net.rarin.colorfulpipes.config;

import net.createmod.catnip.config.ConfigBase;

public class CCPFluids extends ConfigBase {

	public final ConfigBool MixedPipeConnections = b(false, "PipesConnections", Comments.Pipe);
	public final ConfigBool MixedTankConnections = b(true, "PipesTankConnections", Comments.Tank);
	public final ConfigBool MixedVesselConnections = b(true, "PipesVesselConnections", Comments.Vessel);
	public final ConfigBool MixedPSIConnections = b(false, "FluidInterfaceTankConnections", Comments.PSI);
	public final ConfigBool PSIConnections = b(true, "FluidInterfaceConnections", Comments.PSI2);

	@Override
	public String getName() {
		return "Fluids";
	}

	static class Comments {
		static String Pipe = "Pipes can only connect to pipes of the same color. If true, all pipes can connect freely.";
		static String Tank = "Pipes can only connect to tanks of the same color. If true, all pipes can connect freely to tanks.";
		static String Vessel = "Pipes can only connect to vessels of the same color. If true, all pipes can connect freely to vessels. [Create: Connected]";
		static String PSI = "Fluid interfaces can only fill/drain tanks of the same color.";
		static String PSI2 = "Allow colored Fluid interfaces to fill/drain Create fluid tanks.";
	}
}
