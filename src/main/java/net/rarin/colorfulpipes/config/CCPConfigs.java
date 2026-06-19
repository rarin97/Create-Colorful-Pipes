package net.rarin.colorfulpipes.config;

import com.simibubi.create.api.stress.BlockStressValues;
import net.createmod.catnip.config.ConfigBase;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.rarin.colorfulpipes.ColorfulPipes;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber(modid = ColorfulPipes.ID)
public class CCPConfigs {

	private static final Map<ModConfig.Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

	//private static CCPClient client;
	private static CCPCommon common;
	private static CCPServer server;

//	public static CCPClient client() {
//		return client;
//	}

	public static CCPCommon common() {
		return common;
	}

	public static CCPServer server() {
		return server;
	}

	public static ConfigBase byType(ModConfig.Type type) {
		return CONFIGS.get(type);
	}

	private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
		Pair<T, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(builder -> {
			T config = factory.get();
			config.registerAll(builder);
			return config;
		});

		T config = specPair.getLeft();
		config.specification = specPair.getRight();
		CONFIGS.put(side, config);
		return config;
	}

	public static void register(ModContainer container) {
		//client = register(CClient::new, ModConfig.Type.CLIENT);
		common = register(CCPCommon::new, ModConfig.Type.COMMON);
		server = register(CCPServer::new, ModConfig.Type.SERVER);

		for (Map.Entry<ModConfig.Type, ConfigBase> pair : CONFIGS.entrySet())
			container.registerConfig(pair.getKey(), pair.getValue().specification);

		CCPStress stress = server().stressValues;
		BlockStressValues.IMPACTS.registerProvider(stress::getImpact);
		BlockStressValues.CAPACITIES.registerProvider(stress::getCapacity);
	}

	@SubscribeEvent
	public static void onLoad(ModConfigEvent.Loading event) {
		for (ConfigBase config : CONFIGS.values())
			if (config.specification == event.getConfig()
					.getSpec())
				config.onLoad();
	}

	@SubscribeEvent
	public static void onReload(ModConfigEvent.Reloading event) {
		for (ConfigBase config : CONFIGS.values())
			if (config.specification == event.getConfig()
					.getSpec())
				config.onReload();
	}
}
