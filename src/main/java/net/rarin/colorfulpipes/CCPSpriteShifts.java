package net.rarin.colorfulpipes;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTType;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.Map;

import static com.simibubi.create.foundation.block.connected.CTSpriteShifter.getCT;

public class CCPSpriteShifts {
	public static final Map<DyeColor, CTSpriteShiftEntry>
			COLORFUL_FLUID_TANK = new EnumMap<>(DyeColor.class),
			COLORFUL_FLUID_TANK_TOP = new EnumMap<>(DyeColor.class),
			COLORFUL_FLUID_TANK_INNER = new EnumMap<>(DyeColor.class),
			COLORFUL_COPPER_CASING = new EnumMap<>(DyeColor.class),
			COLORFUL_COPPER_GLASS_CASING = new EnumMap<>(DyeColor.class),
			COLORFUL_COPPER_TINTED_GLASS_CASING = new EnumMap<>(DyeColor.class),
	        COLORFUL_COPPER_SCAFFOLD = new EnumMap<>(DyeColor.class),
			COLORFUL_COPPER_SCAFFOLD_INSIDE = new EnumMap<>(DyeColor.class),
			COLORFUL_COPPER_ENCASED_COGWHEEL_SIDE = new EnumMap<>(DyeColor.class),
			COLORFUL_COPPER_ENCASED_COGWHEEL_OTHERSIDE = new EnumMap<>(DyeColor.class);


	public static final CTSpriteShiftEntry
			COPPER_GLASS_CASING = omni("copper_glass_casing"),
			COPPER_TINTED_GLASS_CASING = omni("copper_tinted_glass_casing"),
			COPPER_ENCASED_COGWHEEL_SIDE = vertical("copper_encased_cogwheel_side"),
			COPPER_ENCASED_COGWHEEL_OTHERSIDE = horizontal("copper_encased_cogwheel_side");

	static {
		for (DyeColor color : DyeColor.values()) {
			COLORFUL_FLUID_TANK.put(color, getColorfulCT(AllCTTypes.RECTANGLE, "fluid_tank", color));
			COLORFUL_FLUID_TANK_TOP.put(color, getColorfulCT(AllCTTypes.RECTANGLE, "fluid_tank_top", color));
			COLORFUL_FLUID_TANK_INNER.put(color, getColorfulCT(AllCTTypes.RECTANGLE, "fluid_tank_inner", color));
			COLORFUL_COPPER_CASING.put(color,getColorfulCT(AllCTTypes.OMNIDIRECTIONAL, "copper_casing", color));
			COLORFUL_COPPER_GLASS_CASING.put(color,getColorfulCT(AllCTTypes.OMNIDIRECTIONAL, "copper_glass_casing", color));
			COLORFUL_COPPER_TINTED_GLASS_CASING.put(color,getColorfulCT(AllCTTypes.OMNIDIRECTIONAL, "copper_tinted_glass_casing", color));
			COLORFUL_COPPER_SCAFFOLD.put(color,getColorfulCT(AllCTTypes.HORIZONTAL,"copper_scaffold", color));
			COLORFUL_COPPER_SCAFFOLD_INSIDE.put(color,getColorfulCT(AllCTTypes.HORIZONTAL,"copper_scaffold_inside", color));
			COLORFUL_COPPER_ENCASED_COGWHEEL_SIDE.put(color, getColorfulCT(AllCTTypes.VERTICAL, "copper_encased_cogwheel_side", color));
			COLORFUL_COPPER_ENCASED_COGWHEEL_OTHERSIDE.put(color, getColorfulCT(AllCTTypes.HORIZONTAL, "copper_encased_cogwheel_side", color));
		}
	}

	private static CTSpriteShiftEntry omni(String blockTextureName) {
		return getCT(AllCTTypes.OMNIDIRECTIONAL, ColorfulPipes.asResource("block/" + blockTextureName+ "/" + blockTextureName),
				ColorfulPipes.asResource("block/" + blockTextureName + "_connected/" + blockTextureName + "_connected"));
	}

	private static CTSpriteShiftEntry horizontal(String blockTextureName) {
		return getCT(AllCTTypes.HORIZONTAL, ColorfulPipes.asResource("block/" + blockTextureName+ "/" + blockTextureName),
				ColorfulPipes.asResource("block/" + blockTextureName + "_connected/" + blockTextureName + "_connected"));
	}

	private static CTSpriteShiftEntry vertical(String blockTextureName) {
		return getCT(AllCTTypes.VERTICAL, ColorfulPipes.asResource("block/" + blockTextureName+ "/" + blockTextureName),
				ColorfulPipes.asResource("block/" + blockTextureName + "_connected/" + blockTextureName + "_connected"));
	}

	private static CTSpriteShiftEntry getColorfulCT(CTType type, String blockTextureName, DyeColor color) {
		return getCT(type, ColorfulPipes.asResource("block/" + blockTextureName + "/" + color.getName()),
				ColorfulPipes.asResource("block/" + blockTextureName + "_connected/" + color.getName()));
	}

}
