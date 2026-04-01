package net.rarin.colorfulpipes.Datagen;

import com.google.common.base.Supplier;
import com.simibubi.create.Create;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.rarin.colorfulpipes.ColorfulPipes;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class CCPStandardRecipeGen extends BaseRecipeProvider {
	final List<GeneratedRecipe> all = new ArrayList<>();

	private Marker Pipes = enterFolder("fluid_pipes");


	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_PIPES = new EnumMap<>(DyeColor.class);

//	{
//		for (DyeColor color : DyeColor.values()) {
//			COLORFUL_FLUID_PIPES.put(color, create(CCPBlocks.COLORFUL_FLUID_PIPES.get(color))
//					.unlockedBy(AllBlocks.FLUID_PIPE::get)
//					.viaShapeless(b -> b.requires(ColorfulItemTags.FLUID_PIPES.tag)
//							.requires(color.getTag())));
//
//		}
//	}

	public CCPStandardRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, ColorfulPipes.ID);
	}

	static class Marker {
	}

	String currentFolder = "";

	Marker enterFolder(String folder) {
		currentFolder = folder;
		return new Marker();
	}

	GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
		return new GeneratedRecipeBuilder(currentFolder, result);
	}

	GeneratedRecipeBuilder create(ResourceLocation result) {
		return new GeneratedRecipeBuilder(currentFolder, result);
	}

	GeneratedRecipeBuilder create(ItemProviderEntry<? extends ItemLike, ? extends ItemLike> result) {
		return create(result::get);
	}

	GeneratedRecipe createSpecial(Function<CraftingBookCategory, Recipe<?>> builder, String recipeType,
								  String path) {
		ResourceLocation location = Create.asResource(recipeType + "/" + currentFolder + "/" + path);
		return register(consumer -> {
			SpecialRecipeBuilder b = SpecialRecipeBuilder.special(builder);
			b.save(consumer, location.toString());
		});
	}


	@Override
	public void buildRecipes(RecipeOutput output) {
		all.forEach(c -> c.register(output));
		Create.LOGGER.info("{} registered {} recipe{}", getName(), all.size(), all.size() == 1 ? "" : "s");
	}

	protected GeneratedRecipe register(GeneratedRecipe recipe) {
		all.add(recipe);
		return recipe;
	}

	class GeneratedRecipeBuilder {

		private String path;
		private String suffix;
		private Supplier<? extends ItemLike> result;
		private ResourceLocation compatDatagenOutput;
		List<ICondition> recipeConditions;

		private Supplier<ItemPredicate> unlockedBy;
		private int amount;

		private GeneratedRecipeBuilder(String path) {
			this.path = path;
			this.recipeConditions = new ArrayList<>();
			this.suffix = "";
			this.amount = 1;
		}

		public GeneratedRecipeBuilder(String path, Supplier<? extends ItemLike> result) {
			this(path);
			this.result = result;
		}

		public GeneratedRecipeBuilder(String path, ResourceLocation result) {
			this(path);
			this.compatDatagenOutput = result;
		}

		GeneratedRecipeBuilder returns(int amount) {
			this.amount = amount;
			return this;
		}

		GeneratedRecipeBuilder unlockedBy(Supplier<? extends ItemLike> item) {
			this.unlockedBy = () -> ItemPredicate.Builder.item()
					.of(item.get())
					.build();
			return this;
		}

		GeneratedRecipeBuilder unlockedByTag(Supplier<TagKey<Item>> tag) {
			this.unlockedBy = () -> ItemPredicate.Builder.item()
					.of(tag.get())
					.build();
			return this;
		}

		GeneratedRecipeBuilder whenModLoaded(String modid) {
			return withCondition(new ModLoadedCondition(modid));
		}

		GeneratedRecipeBuilder whenModMissing(String modid) {
			return withCondition(new NotCondition(new ModLoadedCondition(modid)));
		}

		GeneratedRecipeBuilder withCondition(ICondition condition) {
			recipeConditions.add(condition);
			return this;
		}

		GeneratedRecipeBuilder withSuffix(String suffix) {
			this.suffix = suffix;
			return this;
		}

		// FIXME 5.1 refactor - recipe categories as markers instead of sections?
		GeneratedRecipe viaShaped(UnaryOperator<ShapedRecipeBuilder> builder) {
			return register(consumer -> {
				ShapedRecipeBuilder b =
						builder.apply(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result.get(), amount));
				if (unlockedBy != null)
					b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
				b.save(consumer, createLocation("crafting"));
			});
		}

		GeneratedRecipe viaShapeless(UnaryOperator<ShapelessRecipeBuilder> builder) {
			return register(recipeOutput -> {
				ShapelessRecipeBuilder b =
						builder.apply(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), amount));
				if (unlockedBy != null)
					b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

				RecipeOutput conditionalOutput = recipeOutput.withConditions(recipeConditions.toArray(new ICondition[0]));

				b.save(conditionalOutput, createLocation("crafting"));
			});
		}

		GeneratedRecipe viaNetheriteSmithing(Supplier<? extends Item> base, Supplier<Ingredient> upgradeMaterial) {
			return register(consumer -> {
				SmithingTransformRecipeBuilder b =
						SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.of(base.get()), upgradeMaterial.get(), RecipeCategory.COMBAT, result.get()
										.asItem());
				b.unlocks("has_item", inventoryTrigger(ItemPredicate.Builder.item()
						.of(base.get())
						.build()));
				b.save(consumer, createLocation("crafting"));
			});
		}

		private ResourceLocation createSimpleLocation(String recipeType) {
			return Create.asResource(recipeType + "/" + getRegistryName().getPath() + suffix);
		}

		private ResourceLocation createLocation(String recipeType) {
			return Create.asResource(recipeType + "/" + path + "/" + getRegistryName().getPath() + suffix);
		}

		private ResourceLocation getRegistryName() {
			return compatDatagenOutput == null ? RegisteredObjectsHelper.getKeyOrThrow(result.get()
					.asItem()) : compatDatagenOutput;
		}
	}
}
