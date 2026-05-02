package net.rarin.colorfulpipes.compat.Create_Connected;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselMountedStorageType;
import com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;

import net.rarin.colorfulpipes.ColorfulPipes;

import java.util.function.Supplier;

public class CCMountedStorageTypes {
	private static final CreateRegistrate REGISTRATE = ColorfulPipes.REGISTRATE;

	public static final RegistryEntry<MountedFluidStorageType<?>, FluidVesselMountedStorageType> FLUID_VESSEL = simpleFluid("fluid_vessel", FluidVesselMountedStorageType::new);

	private static <T extends MountedFluidStorageType<?>> RegistryEntry<MountedFluidStorageType<?>, T> simpleFluid(String name, Supplier<T> supplier) {
		return REGISTRATE.mountedFluidStorage(name, supplier).register();
	}

	public static void register() {
	}
}
