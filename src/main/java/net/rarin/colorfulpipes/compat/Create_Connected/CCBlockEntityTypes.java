package net.rarin.colorfulpipes.compat.Create_Connected;

import com.simibubi.create.foundation.data.CreateRegistrate;

import com.tterrag.registrate.util.entry.BlockEntityEntry;

import net.minecraft.world.item.DyeColor;
import net.rarin.colorfulpipes.ColorfulPipes;
import net.rarin.colorfulpipes.compat.Create_Connected.content.ColorfulFluidVesselBlockEntity;
import net.rarin.colorfulpipes.compat.Create_Connected.content.ColorfulFluidVesselRenderer;

import java.util.EnumMap;
import java.util.Map;

public class CCBlockEntityTypes {
	private static final CreateRegistrate REGISTRATE = ColorfulPipes.REGISTRATE;

	public static final Map<DyeColor, BlockEntityEntry<ColorfulFluidVesselBlockEntity>> COLORFUL_FLUID_VESSELS = new EnumMap<>(DyeColor.class);

	static {
		for (DyeColor color : DyeColor.values()) {
			COLORFUL_FLUID_VESSELS.put(color, REGISTRATE
					.blockEntity(color.getSerializedName() + "_fluid_vessel", ColorfulFluidVesselBlockEntity::new)
					.validBlocks(CCBlocks.COLORFUL_FLUID_VESSELS.toArray())
					.renderer(() -> ColorfulFluidVesselRenderer::new)
					.register());
		}
	}

//	public static final BlockEntityEntry<ColorfulFluidVesselBlockEntity> COLORFUL_FLUID_VESSELS = REGISTRATE
//			.blockEntity("colorful_fluid_vessel", ColorfulFluidVesselBlockEntity::new)
//			.validBlocks(CCBlocks.COLORFUL_FLUID_VESSELS.toArray())
//			.renderer(() -> ColorfulFluidVesselRenderer::new)
//			.register();

	public static void register() {
	}
}
