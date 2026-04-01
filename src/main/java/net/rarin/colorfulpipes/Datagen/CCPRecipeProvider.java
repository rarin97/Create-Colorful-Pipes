package net.rarin.colorfulpipes.Datagen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.FinishedRecipe;

public class CCPRecipeProvider extends FabricRecipeProvider {
	static final List<ProcessingRecipeGen> GENERATORS = new ArrayList<>();

	public CCPRecipeProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void buildRecipes(Consumer<FinishedRecipe> consumer) {

	}

	public static DataProvider registerAllProcessing(FabricDataOutput output) {

		GENERATORS.add(new CCPItemApplicationRecipeGen(output));
		GENERATORS.add(new CCPWashingRecipeGen(output));

		return new DataProvider() {

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
		};
	}
}
