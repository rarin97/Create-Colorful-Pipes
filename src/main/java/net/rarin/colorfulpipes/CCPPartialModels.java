package net.rarin.colorfulpipes;

import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.rarin.colorfulpipes.compat.Mods;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class CCPPartialModels {

	public static final Map<DyeColor,PartialModel>  COLORFUL_BOILER_GAUGE = new EnumMap<>(DyeColor.class);
	public static final Map<DyeColor,PartialModel>  COLORFUL_BOILER_GAUGE_DIAL = new EnumMap<>(DyeColor.class);
	public static final Map<DyeColor,PartialModel>  COLORFUL_FLUID_VALVE_POINTER = new EnumMap<>(DyeColor.class);
	public static final Map<DyeColor,PartialModel>  COLORFUL_HOSE_MAGNET = new EnumMap<>(DyeColor.class);
	public static final Map<DyeColor,PartialModel>  COLORFUL_HOSE_HALF_MAGNET = new EnumMap<>(DyeColor.class);
	public static final Map<DyeColor,PartialModel> COLORFUL_SPOUT_NOZZLE = new EnumMap<>(DyeColor.class);
	public static final Map<DyeColor,PartialModel>  COLORFUL_FLUID_PIPE_CASINGS = new EnumMap<>(DyeColor.class);
	public static final Map<DyeColor,PartialModel>  COLORFUL_PORTABLE_FLUID_INTERFACE_TOP = new EnumMap<>(DyeColor.class);
	public static final Map<DyeColor, PartialModel> PRINTER_PISTON = new EnumMap<>(DyeColor.class);
	public static final Map<DyeColor, Couple<PartialModel>> COLORFUL_DOORS = new HashMap<>();
	public static final Map<FluidTransportBehaviour.AttachmentTypes.ComponentPartials, Map<DyeColor,Map<Direction, PartialModel>>> COLORFUL_PIPE_ATTACHMENTS =
			new EnumMap<>(FluidTransportBehaviour.AttachmentTypes.ComponentPartials.class);

	static {
		for (FluidTransportBehaviour.AttachmentTypes.ComponentPartials type : FluidTransportBehaviour.AttachmentTypes.ComponentPartials
				.values()) {
			Map<DyeColor, Map<Direction, PartialModel>> colorMap = new EnumMap<>(DyeColor.class);
			for (DyeColor color : DyeColor.values()) {
				Map<Direction, PartialModel> map = new HashMap<>();
				for (Direction d : Iterate.directions) {
					String asId = Lang.asId(type.name());
					map.put(d, block(color.getName() + "_fluid_pipe/" + asId + "/" + Lang.asId(d.getSerializedName())));
				}
				colorMap.put(color, map);
			}
			COLORFUL_PIPE_ATTACHMENTS.put(type, colorMap);
		}
	}

	static {
		for (DyeColor color : DyeColor.values()) {
			COLORFUL_BOILER_GAUGE.put(color, block(color.getName() + "_boiler_gauge"));
			COLORFUL_BOILER_GAUGE_DIAL.put(color, block(color.getName() + "_boiler_gauge_dial"));

			COLORFUL_FLUID_VALVE_POINTER.put(color, block(color.getName() + "_valve_pointer"));

			COLORFUL_HOSE_MAGNET.put(color, block(color.getName() + "_hose_magnet"));
			COLORFUL_HOSE_HALF_MAGNET.put(color, block(color.getName() + "_hose_half_magnet"));

			COLORFUL_SPOUT_NOZZLE.put(color, block(color.getName() + "_spout_nozzle"));

			COLORFUL_FLUID_PIPE_CASINGS.put(color, block(color.getName() + "_fluid_pipe/casing"));

			COLORFUL_PORTABLE_FLUID_INTERFACE_TOP.put(color, block(color.getName() + "_psi_top"));

			COLORFUL_DOORS.put(color, Couple.create(
					PartialModel.of(ColorfulPipes.asResource("block/" + color.getName() + "_copper_door/fold_left")),
					PartialModel.of(ColorfulPipes.asResource("block/" + color.getName() + "_copper_door/fold_right"))));

			if (Mods.CREATE_ENCHANTMENT_INDUSTRY.isLoaded()) {
				PRINTER_PISTON.put(color, block( "piston/" + color.getName()));
			}
		}
	}

	private static PartialModel block(String path) {
		return PartialModel.of(ColorfulPipes.asResource("block/" + path));
	}

	public static void register() {
	}
}
