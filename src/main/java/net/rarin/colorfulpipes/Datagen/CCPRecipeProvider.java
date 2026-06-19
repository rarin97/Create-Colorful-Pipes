package net.rarin.colorfulpipes.Datagen;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CCPRecipeProvider {
	public static void registerAllProcessing(DataGenerator gen, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		final List<ProcessingRecipeGen<?, ?, ?>> GENERATORS = new ArrayList<>();

		GENERATORS.add(new CCPItemApplicationRecipeGen(output, registries));
		GENERATORS.add(new CCPWashingRecipeGen(output, registries));

		gen.addProvider(true, new DataProvider() {

			@Override
			public String getName() {
				return "Create: Colorful Pipe's Processing Recipes";
			}

			@Override
			public CompletableFuture<?> run(CachedOutput dc) {
				return CompletableFuture.allOf(GENERATORS.stream()
						.map(gen -> gen.run(dc))
						.toArray(CompletableFuture[]::new));
			}
		});
	}
}
