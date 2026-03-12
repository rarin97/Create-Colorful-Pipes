package net.rarin.colorfulpipes;

import com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType;
import com.simibubi.create.content.fluids.tank.storage.FluidTankMountedStorageType;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;

import java.util.function.Supplier;

public class CCPMountedStorageTypes {
	private static final CreateRegistrate REGISTRATE = ColorfulPipes.REGISTRATE;

	public static final RegistryEntry<MountedFluidStorageType<?>, FluidTankMountedStorageType> FLUID_TANK = simpleFluid("fluid_tank", FluidTankMountedStorageType::new);

	private static <T extends MountedFluidStorageType<?>> RegistryEntry<MountedFluidStorageType<?>, T> simpleFluid(String name, Supplier<T> supplier) {
		return REGISTRATE.mountedFluidStorage(name, supplier).register();
	}

	public static void register() {
	}
}
