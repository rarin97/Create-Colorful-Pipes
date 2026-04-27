package net.rarin.colorfulpipes.Datagen;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import com.google.common.base.Supplier;
import com.google.gson.JsonObject;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import com.simibubi.create.foundation.data.recipe.Mods;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;

import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.createmod.catnip.platform.CatnipServices;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.CCPPaletteBlocks;
import net.rarin.colorfulpipes.CCPTags;
import net.rarin.colorfulpipes.ColorfulPipes;

import org.jetbrains.annotations.NotNull;

public class CCPStandardRecipeGen extends BaseRecipeProvider {
	final List<GeneratedRecipe> all = new ArrayList<>();

	private Marker Pipes = enterFolder("colorfulpipes");


	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_PIPES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_SMART_FLUID_PIPES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_PUMPS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_VALVES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_HOSE_PULLEY = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_SPOUTS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_DRAINS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_INTERFACES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_STEAM_WHISTLES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_CASING = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_GLASS_CASING = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_TINTED_GLASS_CASING = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_SCAFFOLD = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_LADDER = new EnumMap<>(DyeColor.class);

	{
		for (DyeColor color : DyeColor.values()) {
			COLORFUL_FLUID_PIPES.put(color, create(CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(color))
					.unlockedBy(AllBlocks.COPPER_BARS::get)
					.viaShapeless(b -> b.requires(CCPTags.ColorfulItemTags.COPPER_BARS.tag)
							.requires(color.getTag())));

			COLORFUL_PUMPS.put(color, create(CCPBlocks.COLORFUL_PUMPS.get(color))
					.unlockedBy(AllBlocks.FLUID_PIPE::get)
					.viaShapeless(b -> b.requires(CCPBlocks.COLORFUL_FLUID_PIPES.get(color))
							.requires(AllBlocks.COGWHEEL)));

			COLORFUL_SMART_FLUID_PIPES.put(color, create(CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(color))
					.unlockedBy(AllBlocks.FLUID_PIPE::get)
					.viaShaped(b -> b.define('F', CCPBlocks.COLORFUL_FLUID_PIPES.get(color))
							.define('E', AllItems.ELECTRON_TUBE)
							.define('B', AllItems.BRASS_SHEET)
							.pattern("E")
							.pattern("F")
							.pattern("B")));

			COLORFUL_FLUID_VALVES.put(color, create(CCPBlocks.COLORFUL_FLUID_VALVES.get(color))
					.unlockedBy(AllBlocks.FLUID_PIPE::get)
					.viaShapeless(b -> b.requires(AllItems.IRON_SHEET)
							.requires(CCPBlocks.COLORFUL_FLUID_PIPES.get(color))));

			COLORFUL_COPPER_GLASS_CASING.put(color, create(CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(color))
					.unlockedBy(AllBlocks.COPPER_CASING::get)
					.viaShapeless(b -> b.requires(Tags.Items.GLASS_COLORLESS)
							.requires(CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))));

			COLORFUL_DRAINS.put(color, create(CCPBlocks.COLORFUL_DRAINS.get(color))
					.unlockedBy(AllBlocks.ITEM_DRAIN::get)
					.viaShaped(b -> b.define('P', Blocks.IRON_BARS)
							.define('S', CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))
							.pattern("P")
							.pattern("S")));

			COLORFUL_SPOUTS.put(color, create(CCPBlocks.COLORFUL_SPOUTS.get(color))
							.unlockedBy(AllBlocks.SPOUT::get)
							.viaShaped(b -> b.define('T',CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))
							.define('P', Items.DRIED_KELP)
							.pattern("T")
							.pattern("P")));

			COLORFUL_FLUID_INTERFACES.put(color, create(CCPBlocks.COLORFUL_FLUID_INTERFACES.get(color))
					.unlockedBy(AllBlocks.COPPER_CASING::get)
					.viaShapeless(b -> b.requires(CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))
							.requires(AllBlocks.CHUTE.get())));

			COLORFUL_COPPER_TINTED_GLASS_CASING.put(color, create(CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(color)).returns(2)
					.unlockedBy(Items.AMETHYST_SHARD::asItem)
					.viaShaped(b -> b.define('A', Items.AMETHYST_SHARD)
							.define('G', CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(color))
							.pattern(" A ")
							.pattern("AGA")
							.pattern(" A ")));

			COLORFUL_HOSE_PULLEY.put(color, create(CCPBlocks.COLORFUL_HOSE_PULLEYS.get(color))
					.unlockedBy(AllBlocks.HOSE_PULLEY::get)
					.viaShaped(b -> b.define('B', CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))
							.define('C', Items.DRIED_KELP_BLOCK)
							.define('I', AllItems.COPPER_SHEET)
							.pattern("B")
							.pattern("C")
							.pattern("I")));

		}
	}

	GeneratedRecipe

			COPPER_TINTED_GLASS_CASING = create(CCPPaletteBlocks.COPPER_TINTED_GLASS_CASING).returns(2)
			.unlockedBy(Items.AMETHYST_SHARD::asItem)
			.viaShaped(b -> b.define('A', Items.AMETHYST_SHARD)
					.define('G', CCPPaletteBlocks.COPPER_GLASS_CASING.get())
							.pattern(" A ")
							.pattern("AGA")
							.pattern(" A "));


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

	GeneratedRecipeBuilder create(ItemProviderEntry<? extends ItemLike> result) {
		return create(result::get);
	}

	GeneratedRecipe createSpecial(Supplier<? extends SimpleCraftingRecipeSerializer<?>> serializer, String recipeType,
								  String path) {
		ResourceLocation location = Create.asResource(recipeType + "/" + currentFolder + "/" + path);
		return register(consumer -> {
			SpecialRecipeBuilder b = SpecialRecipeBuilder.special(serializer.get());
			b.save(consumer, location.toString());
		});
	}

	GeneratedRecipe blastCrushedMetal(Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> ingredient) {
		return create(result::get).withSuffix("_from_crushed")
				.viaCooking(ingredient::get)
				.rewardXP(.1f)
				.inBlastFurnace();
	}

	GeneratedRecipe blastModdedCrushedMetal(ItemEntry<? extends Item> ingredient, CommonMetal metal) {
		for (Mods mod : metal.mods) {
			String metalName = metal.getName(mod);
			ResourceLocation ingot = mod.ingotOf(metalName);
			String modId = mod.getId();
			create(ingot).withSuffix("_compat_" + modId)
					.whenModLoaded(modId)
					.viaCooking(ingredient::get)
					.rewardXP(.1f)
					.inBlastFurnace();
		}
		return null;
	}

	GeneratedRecipe recycleGlass(BlockEntry<? extends Block> ingredient) {
		return create(() -> Blocks.GLASS).withSuffix("_from_" + ingredient.getId()
						.getPath())
				.viaCooking(ingredient::get)
				.forDuration(50)
				.inFurnace();
	}

	GeneratedRecipe recycleGlassPane(BlockEntry<? extends Block> ingredient) {
		return create(() -> Blocks.GLASS_PANE).withSuffix("_from_" + ingredient.getId()
						.getPath())
				.viaCooking(ingredient::get)
				.forDuration(50)
				.inFurnace();
	}

	GeneratedRecipe metalCompacting(List<ItemProviderEntry<? extends ItemLike>> variants,
									List<Supplier<TagKey<Item>>> ingredients) {
		GeneratedRecipe result = null;
		for (int i = 0; i + 1 < variants.size(); i++) {
			ItemProviderEntry<? extends ItemLike> currentEntry = variants.get(i);
			ItemProviderEntry<? extends ItemLike> nextEntry = variants.get(i + 1);
			Supplier<TagKey<Item>> currentIngredient = ingredients.get(i);
			Supplier<TagKey<Item>> nextIngredient = ingredients.get(i + 1);

			result = create(nextEntry).withSuffix("_from_compacting")
					.unlockedBy(currentEntry::get)
					.viaShaped(b -> b.pattern("###")
							.pattern("###")
							.pattern("###")
							.define('#', currentIngredient.get()));

			result = create(currentEntry).returns(9)
					.withSuffix("_from_decompacting")
					.unlockedBy(nextEntry::get)
					.viaShapeless(b -> b.requires(nextIngredient.get()));
		}
		return result;
	}

	GeneratedRecipe conversionCycle(List<ItemProviderEntry<? extends ItemLike>> cycle) {
		GeneratedRecipe result = null;
		for (int i = 0; i < cycle.size(); i++) {
			ItemProviderEntry<? extends ItemLike> currentEntry = cycle.get(i);
			ItemProviderEntry<? extends ItemLike> nextEntry = cycle.get((i + 1) % cycle.size());
			result = create(nextEntry).withSuffix("_from_conversion")
					.unlockedBy(currentEntry::get)
					.viaShapeless(b -> b.requires(currentEntry.get()));
		}
		return result;
	}

	GeneratedRecipe clearData(ItemProviderEntry<? extends ItemLike> item) {
		return create(item).withSuffix("_clear")
				.unlockedBy(item::get)
				.viaShapeless(b -> b.requires(item.get()));
	}

	@Override
	public void buildRecipes(Consumer<FinishedRecipe> p_200404_1_) {
		all.forEach(c -> c.register(p_200404_1_));
		Create.LOGGER.info(getName() + " registered " + all.size() + " recipe" + (all.size() == 1 ? "" : "s"));
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
		List<ConditionJsonProvider> recipeConditions;

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
			return withCondition(DefaultResourceConditions.allModsLoaded(modid));
		}

		GeneratedRecipeBuilder whenModMissing(String modid) {
			return withCondition(DefaultResourceConditions.not(DefaultResourceConditions.allModsLoaded(modid)));
		}

		GeneratedRecipeBuilder withCondition(ConditionJsonProvider condition) {
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
			return register(consumer -> {
				ShapelessRecipeBuilder b =
						builder.apply(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), amount));
				if (unlockedBy != null)
					b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

				b.save(result -> {
					consumer.accept(!recipeConditions.isEmpty()
							? new ConditionSupportingShapelessRecipeResult(result, recipeConditions)
							: result);
				}, createLocation("crafting"));
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
			return compatDatagenOutput == null ? CatnipServices.REGISTRIES.getKeyOrThrow(result.get()
					.asItem()) : compatDatagenOutput;
		}

		GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCooking(Supplier<? extends ItemLike> item) {
			return unlockedBy(item).viaCookingIngredient(() -> Ingredient.of(item.get()));
		}

		GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCookingTag(Supplier<TagKey<Item>> tag) {
			return unlockedByTag(tag).viaCookingIngredient(() -> Ingredient.of(tag.get()));
		}

		GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCookingIngredient(Supplier<Ingredient> ingredient) {
			return new GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder(ingredient);
		}

		class GeneratedCookingRecipeBuilder {

			private Supplier<Ingredient> ingredient;
			private float exp;
			private int cookingTime;

			private final RecipeSerializer<? extends AbstractCookingRecipe> FURNACE = RecipeSerializer.SMELTING_RECIPE,
					SMOKER = RecipeSerializer.SMOKING_RECIPE, BLAST = RecipeSerializer.BLASTING_RECIPE,
					CAMPFIRE = RecipeSerializer.CAMPFIRE_COOKING_RECIPE;

			GeneratedCookingRecipeBuilder(Supplier<Ingredient> ingredient) {
				this.ingredient = ingredient;
				cookingTime = 200;
				exp = 0;
			}

			GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder forDuration(int duration) {
				cookingTime = duration;
				return this;
			}

			GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder rewardXP(float xp) {
				exp = xp;
				return this;
			}

			GeneratedRecipe inFurnace() {
				return inFurnace(b -> b);
			}

			GeneratedRecipe inFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
				return create(FURNACE, builder, 1);
			}

			GeneratedRecipe inSmoker() {
				return inSmoker(b -> b);
			}

			GeneratedRecipe inSmoker(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
				create(FURNACE, builder, 1);
				create(CAMPFIRE, builder, 3);
				return create(SMOKER, builder, .5f);
			}

			GeneratedRecipe inBlastFurnace() {
				return inBlastFurnace(b -> b);
			}

			GeneratedRecipe inBlastFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
				create(FURNACE, builder, 1);
				return create(BLAST, builder, .5f);
			}

			private GeneratedRecipe create(RecipeSerializer<? extends AbstractCookingRecipe> serializer,
										   UnaryOperator<SimpleCookingRecipeBuilder> builder, float cookingTimeModifier) {
				return register(consumer -> {
					boolean isOtherMod = compatDatagenOutput != null;

					SimpleCookingRecipeBuilder b = builder.apply(SimpleCookingRecipeBuilder.generic(ingredient.get(),
							RecipeCategory.MISC, isOtherMod ? Items.DIRT : result.get(), exp,
							(int) (cookingTime * cookingTimeModifier), serializer));

					if (unlockedBy != null)
						b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

					b.save(result -> {
						consumer.accept(
								isOtherMod ? new ModdedCookingRecipeResult(result, compatDatagenOutput, recipeConditions)
										: result);
					}, createSimpleLocation(CatnipServices.REGISTRIES.getKeyOrThrow(serializer)
							.getPath()));
				});
			}
		}
	}

	@Override
	public String getName() {
		return "Create's Standard Recipes";
	}

	public CCPStandardRecipeGen(FabricDataOutput output) {
		super(output, ColorfulPipes.ID);
	}

	private record ModdedCookingRecipeResult(FinishedRecipe wrapped, ResourceLocation outputOverride,
											 List<ConditionJsonProvider> conditions) implements FinishedRecipe {
		@Override
		public ResourceLocation getId() {
			return wrapped.getId();
		}

		@Override
		public RecipeSerializer<?> getType() {
			return wrapped.getType();
		}

		@Override
		public JsonObject serializeAdvancement() {
			return wrapped.serializeAdvancement();
		}

		@Override
		public ResourceLocation getAdvancementId() {
			return wrapped.getAdvancementId();
		}

		@Override
		public void serializeRecipeData(JsonObject object) {
			wrapped.serializeRecipeData(object);
			object.addProperty("result", outputOverride.toString());

			ConditionJsonProvider.write(object, conditions.toArray(new ConditionJsonProvider[0]));
		}
	}

	private record ConditionSupportingShapelessRecipeResult(FinishedRecipe wrapped, List<ConditionJsonProvider> conditions)
			implements FinishedRecipe {
		@Override
		public ResourceLocation getId() {
			return wrapped.getId();
		}

		@Override
		public RecipeSerializer<?> getType() {
			return wrapped.getType();
		}

		@Override
		public JsonObject serializeAdvancement() {
			return wrapped.serializeAdvancement();
		}

		@Override
		public ResourceLocation getAdvancementId() {
			return wrapped.getAdvancementId();
		}

		@Override
		public void serializeRecipeData(@NotNull JsonObject pJson) {
			wrapped.serializeRecipeData(pJson);

			ConditionJsonProvider.write(pJson, conditions.toArray(new ConditionJsonProvider[0]));
		}
	}
}
