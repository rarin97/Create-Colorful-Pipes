package net.rarin.colorfulpipes;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.Map;

public class CCPSpriteShifts {
	public static final Map<DyeColor, CTSpriteShiftEntry>
			COLORFUL_FLUID_TANK = new EnumMap<>(DyeColor.class),
			COLORFUL_FLUID_TANK_TOP = new EnumMap<>(DyeColor.class),
			COLORFUL_FLUID_TANK_INNER = new EnumMap<>(DyeColor.class),
			COLORFUL_COPPER_CASING = new EnumMap<>(DyeColor.class);


	static {
		for (DyeColor color : DyeColor.values()) {
			COLORFUL_FLUID_TANK.put(color, getColorfulCT(AllCTTypes.RECTANGLE, "fluid_tank", color));
			COLORFUL_FLUID_TANK_TOP.put(color, getColorfulCT(AllCTTypes.RECTANGLE, "fluid_tank_top", color));
			COLORFUL_FLUID_TANK_INNER.put(color, getColorfulCT(AllCTTypes.RECTANGLE, "fluid_tank_inner", color));
			COLORFUL_COPPER_CASING.put(color,getColorfulCT(AllCTTypes.OMNIDIRECTIONAL, "copper_casing", color));
		}
	}

	private static CTSpriteShiftEntry getColorfulCT(CTType type, String blockTextureName, DyeColor color) {
		return CTSpriteShifter.getCT(type, ColorfulPipes.asResource("block/" + blockTextureName + "/" + color.getName()),
				ColorfulPipes.asResource("block/" + blockTextureName + "_connected/" + color.getName()));
	}

}
