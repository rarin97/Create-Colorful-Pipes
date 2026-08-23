package net.rarin.colorfulpipes.config;

import net.createmod.catnip.config.ConfigBase;

public class CCPFeatures extends ConfigBase {

	private final ConfigGroup Fluid = group(1, "Fluid", Comments.fluid);
	public final ConfigBool Pipe = b(true, "Fluid Pipes", Comments.fluid);
	public final ConfigBool SmartPipe = b(true, "Smart Fluid Pipes", Comments.fluid);
	public final ConfigBool Pump = b(true, "Smart Fluid Pipes", Comments.fluid);
	public final ConfigBool Valve = b(true, "Fluid Valves", Comments.fluid);
	public final ConfigBool Tank = b(true, "Fluid Tanks", Comments.fluid);
	public final ConfigBool Spout = b(true, "Spouts", Comments.fluid);
	public final ConfigBool Drain = b(true, "Item Drains", Comments.fluid);
	public final ConfigBool FluidInterface = b(true, "Fluid Interfaces", Comments.fluid);
	public final ConfigBool HosePulley = b(true, "Hose Pulleys", Comments.fluid);
	public final ConfigBool Engine = b(true, "Steam Engines", Comments.fluid);

	private final ConfigGroup Palette = group(1, "Palette", Comments.fluid);
	public final ConfigBool Whistle = b(true, "Steam Whistle", Comments.fluid);
	public final ConfigBool TableCloth = b(true, "Table Cloths", Comments.fluid);
	public final ConfigBool Casing = b(true, "Copper Casings", Comments.fluid);
	public final ConfigBool GlassCasing = b(true, "Copper Glass Casings", Comments.fluid);
	public final ConfigBool TintedGlassCasing = b(true, "Copper Tinted Glass Casings", Comments.fluid);
	public final ConfigBool Ladder = b(true, "Copper Ladders", Comments.fluid);
	public final ConfigBool Scaffold = b(true, "Copper Scaffolds", Comments.fluid);
	public final ConfigBool Bar = b(true, "Copper Bars", Comments.fluid);
	public final ConfigBool Door = b(true, "Copper Doors", Comments.fluid);

	private final ConfigGroup Compats = group(1, "Compats", Comments.fluid);
	public final ConfigBool Vessel = b(true, "Fluid Vessels", Comments.fluid);
	public final ConfigBool FluidHatch = b(true, "Fluid Hatches", Comments.fluid);
	public final ConfigBool XPHatch = b(true, "Experience Hatches", Comments.fluid);
	public final ConfigBool XPLantern = b(true, "Experience Lanterns", Comments.fluid);
	public final ConfigBool Printer = b(true, "Printers", Comments.fluid);
	public final ConfigBool ElectricPump = b(true, "Electric Pump", Comments.fluid);

	@Override
	public String getName() {
		return "feature flags";
	}

	static class Comments {
		static final String fluid = "show/hide blocks from JEI/Creative tab";

	}
}
