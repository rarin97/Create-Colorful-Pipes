package net.rarin.colorfulpipes;

import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.source.BoilerDisplaySource;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;

import java.util.function.Supplier;

public class CCPDisplaySources {
	private static final CreateRegistrate REGISTRATE = ColorfulPipes.REGISTRATE;

	public static final RegistryEntry<DisplaySource, BoilerDisplaySource> BOILER = simple("boiler", BoilerDisplaySource::new);

	private static <T extends DisplaySource> RegistryEntry<DisplaySource, T> simple(String name, Supplier<T> supplier) {
		return REGISTRATE.displaySource(name, supplier).register();
	}

	public static void register() {
	}
}
