package net.rarin.colorfulpipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.createmod.ponder.foundation.PonderIndex;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.rarin.colorfulpipes.Datagen.CCPRecipeProvider;
import net.rarin.colorfulpipes.Datagen.CCPTagGen;

import java.util.Map;
import java.util.function.BiConsumer;

import static net.rarin.colorfulpipes.ColorfulPipes.REGISTRATE;

public class ColorfulPipesDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator datagen) {
		ExistingFileHelper helper = ExistingFileHelper.withResourcesFromArg();
		FabricDataGenerator.Pack pack = datagen.createPack();
		REGISTRATE.setupDatagen(pack, helper);
		gatherData(pack, helper);
	}

	public static void gatherData(FabricDataGenerator.Pack pack, ExistingFileHelper existingFileHelper) {
		addExtraRegistrateData();

		ColorfulPipes.REGISTRATE.addLang("itemGroup", ColorfulPipes.asResource("main"), "Colorful Pipes");

		pack.addProvider(CCPRecipeProvider::registerAllProcessing);
	}

	private static void addExtraRegistrateData() {
		CCPTagGen.addGenerators();

		REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
			BiConsumer<String, String> langConsumer = provider::add;

			provideDefaultLang("interface", langConsumer);
			providePonderLang(langConsumer);
		});
	}

	private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
		String path = "assets/colorfulpipes/lang/" + fileName + ".json";
		JsonElement jsonElement = FilesHelper.loadJsonResource(path);
		if (jsonElement == null) {
			throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
		}
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue().getAsString();
			consumer.accept(key, value);
		}
	}

	private static void providePonderLang(BiConsumer<String, String> consumer) {
		PonderIndex.addPlugin(new CCPPonderPlugin());

		PonderIndex.getLangAccess().provideLang(ColorfulPipes.ID, consumer);
	}
}
