package net.rarin.colorfulpipes.config;

import net.createmod.catnip.config.ConfigBase;

public class CCPRecipes extends ConfigBase {

	private final ConfigGroup ItemApplication = group(1, "Item Application", Comments.ItemApplication);
	public final ConfigBool enablePipeItemApplication = b(true, "enablePipeItemApplication", Comments.Pipe);
	public final ConfigBool enableTankItemApplication = b(true, "enableTankItemApplication", Comments.Tank);
	public final ConfigBool enableVesselItemApplication = b(true, "enableVesselItemApplication", Comments.Vessel);
	public final ConfigBool enableEngineItemApplication = b(true, "enableSteamEngineItemApplication", Comments.Engine);
	public final ConfigBool enableWhistleItemApplication = b(true, "enableSteamWhistleItemApplication", Comments.Whistle);

	@Override
	public String getName() {
		return "recipes";
	}

	static class Comments {
		static String ItemApplication = ".";
		static String Pipe = "Allow Create fluid pipe to be colored with dyes.";
		static String Tank = "Allow Create fluid Tank to be colored with dyes.";
		static String Vessel = "Allow Create fluid Vessel to be colored with dyes.[Create:Connected]";
		static String Engine = "Allow Create steam engine to be colored with dyes.";
		static String Whistle = "Allow Create steam whistle to be colored with dyes.";
	}
}
