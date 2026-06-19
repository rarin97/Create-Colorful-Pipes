package net.rarin.colorfulpipes.Datagen;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import net.minecraft.tags.ItemTags;
import net.rarin.colorfulpipes.compat.CreateDragonsPlus.CDPBlocks;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.CEIBlocks;
import net.rarin.colorfulpipes.compat.Mods;
import com.simibubi.create.foundation.mixin.accessor.MappedRegistryAccessor;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.rarin.colorfulpipes.compat.Create_Connected.CCBlocks;
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.CCPPaletteBlocks;
import net.rarin.colorfulpipes.CCPTags.ColorfulItemTags;
import net.rarin.colorfulpipes.ColorfulPipes;

import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static net.rarin.colorfulpipes.CCPTags.ColorfulItemTags.EXPERIENCE_LANTERNS;

public class CCPStandardRecipeGen extends BaseRecipeProvider {
	final List<GeneratedRecipe> all = new ArrayList<>();

	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_PIPES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_SMART_FLUID_PIPES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_PUMPS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_VALVES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_HOSE_PULLEY = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_SPOUTS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_DRAINS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_INTERFACES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_VESSELS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_GLASS_CASING = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_TINTED_GLASS_CASING = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_COPPER_DOOR = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_FLUID_HATCHES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_EXPERIENCE_HATCHES = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_EXPERIENCE_LANTERNS = new EnumMap<>(DyeColor.class);
	final EnumMap<DyeColor, GeneratedRecipe> COLORFUL_PRINTERS = new EnumMap<>(DyeColor.class);

	private Marker KINETICS = enterFolder("kinetics");

	{
		for (DyeColor color : DyeColor.values()) {
			COLORFUL_FLUID_PIPES.put(color, create(CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(color))
					.unlockedBy(AllBlocks.COPPER_BARS::get)
					.viaShapeless(b -> b.requires(ColorfulItemTags.COPPER_BARS.tag)
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

			COLORFUL_DRAINS.put(color, create(CCPBlocks.COLORFUL_DRAINS.get(color))
					.unlockedBy(AllBlocks.ITEM_DRAIN::get)
					.viaShaped(b -> b.define('P', Blocks.IRON_BARS)
							.define('S', CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))
							.pattern("P")
							.pattern("S")));

			COLORFUL_SPOUTS.put(color, create(CCPBlocks.COLORFUL_SPOUTS.get(color))
					.unlockedBy(AllBlocks.SPOUT::get)
					.viaShaped(b -> b.define('T', CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))
							.define('P', Items.DRIED_KELP)
							.pattern("T")
							.pattern("P")));

			COLORFUL_FLUID_INTERFACES.put(color, create(CCPBlocks.COLORFUL_FLUID_INTERFACES.get(color))
					.unlockedBy(AllBlocks.COPPER_CASING::get)
					.viaShapeless(b -> b.requires(CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))
							.requires(AllBlocks.CHUTE.get())));

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

	private final Marker PALETTES = enterFolder("palettes");

	{
		for (DyeColor color : DyeColor.values()) {

			COLORFUL_COPPER_GLASS_CASING.put(color, create(CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(color))
					.unlockedBy(AllBlocks.COPPER_CASING::get)
					.viaShapeless(b -> b.requires(Tags.Items.GLASS_BLOCKS_COLORLESS)
							.requires(CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))));

			COLORFUL_COPPER_TINTED_GLASS_CASING.put(color, create(CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(color)).returns(2)
					.unlockedBy(Items.AMETHYST_SHARD::asItem)
					.viaShaped(b -> b.define('A', Items.AMETHYST_SHARD)
							.define('G', CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(color))
							.pattern(" A ")
							.pattern("AGA")
							.pattern(" A ")));

			COLORFUL_COPPER_DOOR.put(color, create(CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(color))
					.unlockedBy(AllBlocks.COPPER_DOOR::get)
					.viaShapeless(b -> b.requires(ItemTags.WOODEN_DOORS)
							.requires(CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))));

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

	private final Marker COMPATS = enterFolder("compats");

	{
		for (DyeColor color : DyeColor.values()) {

			COLORFUL_FLUID_VESSELS.put(color, create(CCBlocks.COLORFUL_FLUID_VESSELS.get(color))
					.unlockedBy(AllBlocks.FLUID_TANK::get)
					.whenModLoaded(Mods.CREATE_CONNECTED.id())
					.viaShapeless(b -> b.requires(ColorfulItemTags.FLUID_VESSELS.tag)
							.requires(color.getTag())));

			COLORFUL_FLUID_VESSELS.put(color,
					conversionCycle(ImmutableList.of(CCBlocks.COLORFUL_FLUID_VESSELS.get(color), CCPBlocks.COLORFUL_FLUID_TANKS.get(color)),
							Mods.CREATE_CONNECTED.id()));

			COLORFUL_FLUID_HATCHES.put(color, create(CDPBlocks.COLORFUL_FLUID_HATCHES.get(color))
					.unlockedBy(AllBlocks.ITEM_DRAIN::get)
					.whenModLoaded(Mods.CREATE_DRAGONS_PLUS.id())
					.viaShapeless(b -> b.requires(ColorfulItemTags.FLUID_HATCHES.tag)
							.requires(color.getTag())));

			COLORFUL_FLUID_HATCHES.put(color, create(CDPBlocks.COLORFUL_FLUID_HATCHES.get(color))
					.withSuffix("_from_drain")
					.unlockedBy(AllBlocks.ITEM_DRAIN::get)
					.whenModLoaded(Mods.CREATE_DRAGONS_PLUS.id())
					.viaShapeless(b -> b.requires(Items.COPPER_INGOT)
							.requires(CCPBlocks.COLORFUL_DRAINS.get(color))));

			COLORFUL_EXPERIENCE_HATCHES.put(color, create(CEIBlocks.COLORFUL_EXPERIENCE_HATCHES.get(color))
					.unlockedBy(plus.dragons.createenchantmentindustry.common.registry.CEIBlocks.EXPERIENCE_HATCH::get)
					.whenModLoaded(Mods.CREATE_ENCHANTMENT_INDUSTRY.id())
					.viaShapeless(b -> b.requires(ColorfulItemTags.EXPERIENCE_HATCHES.tag)
							.requires(color.getTag())));

			COLORFUL_EXPERIENCE_LANTERNS.put(color, create(CEIBlocks.COLORFUL_EXPERIENCE_LANTERNS.get(color))
					.withSuffix("_from_dyes")
					.unlockedBy(plus.dragons.createenchantmentindustry.common.registry.CEIBlocks.EXPERIENCE_LANTERN::get)
					.whenModLoaded(Mods.CREATE_ENCHANTMENT_INDUSTRY.id())
					.viaShapeless(b -> b.requires(EXPERIENCE_LANTERNS.tag)
							.requires(color.getTag())));

			COLORFUL_EXPERIENCE_LANTERNS.put(color, create(CEIBlocks.COLORFUL_EXPERIENCE_LANTERNS.get(color))
					.unlockedBy(CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color)::get)
					.whenModLoaded(Mods.CREATE_ENCHANTMENT_INDUSTRY.id())
					.viaShaped(b -> b.define('a', AllBlocks.EXPERIENCE_BLOCK)
							.define('s', Items.SPONGE).define('c', CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color))
							.pattern("a")
							.pattern("s")
							.pattern("c")));

			COLORFUL_PRINTERS.put(color, create(CEIBlocks.COLORFUL_PRINTERS.get(color))
					.withSuffix("_from_dyes")
					.unlockedBy(plus.dragons.createenchantmentindustry.common.registry.CEIBlocks.PRINTER::get)
					.whenModLoaded(Mods.CREATE_ENCHANTMENT_INDUSTRY.id())
					.viaShapeless(b -> b.requires(ColorfulItemTags.PRINTERS.tag)
							.requires(color.getTag())));

			COLORFUL_PRINTERS.put(color, create(CEIBlocks.COLORFUL_PRINTERS.get(color))
					.unlockedBy(AllItems.BRASS_SHEET::get)
					.whenModLoaded(Mods.CREATE_ENCHANTMENT_INDUSTRY.id())
					.viaShaped(b -> b.define('-', CommonMetal.BRASS.plates)
							.define('o', CCPBlocks.COLORFUL_SPOUTS.get(color)).define('=', Blocks.IRON_BLOCK)
							.pattern("-")
							.pattern("o")
							.pattern("=")));

		}
	}

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
		ResourceLocation location = ColorfulPipes.asResource(recipeType + "/" + currentFolder + "/" + path);
		return register(consumer -> {
			SpecialRecipeBuilder b = SpecialRecipeBuilder.special(builder);
			b.save(consumer, location.toString());
		});
	}

	GeneratedRecipe blastCrushedMetal(Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> ingredient) {
		return create(result::get).withSuffix("_from_crushed")
				.viaCooking(ingredient)
				.rewardXP(.1f)
				.inBlastFurnace();
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

	GeneratedRecipe metalCompacting(List<ItemProviderEntry<? extends ItemLike, ? extends ItemLike>> variants,
									List<Supplier<TagKey<Item>>> ingredients) {
		GeneratedRecipe result = null;
		for (int i = 0; i + 1 < variants.size(); i++) {
			ItemProviderEntry<? extends ItemLike, ? extends ItemLike> currentEntry = variants.get(i);
			ItemProviderEntry<? extends ItemLike, ? extends ItemLike> nextEntry = variants.get(i + 1);
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

	GeneratedRecipe conversionCycle(List<ItemProviderEntry<? extends ItemLike, ? extends ItemLike>> cycle, String modid) {
		GeneratedRecipe result = null;
		for (int i = 0; i < cycle.size(); i++) {
			ItemProviderEntry<? extends ItemLike, ? extends ItemLike> currentEntry = cycle.get(i);
			ItemProviderEntry<? extends ItemLike, ? extends ItemLike> nextEntry = cycle.get((i + 1) % cycle.size());
			result = create(nextEntry).withSuffix("_from_conversion")
					.unlockedBy(currentEntry::get)
					.whenModLoaded(modid)
					.viaShapeless(b -> b.requires(currentEntry.get()));
		}
		return result;
	}

	GeneratedRecipe clearData(ItemProviderEntry<? extends ItemLike, ? extends ItemLike> item) {
		return create(item).withSuffix("_clear")
				.unlockedBy(item::get)
				.viaShapeless(b -> b.requires(item.get()));
	}

	@Override
	public void buildRecipes(RecipeOutput output) {
		all.forEach(c -> c.register(output));
		ColorfulPipes.LOGGER.info("{} registered {} recipe{}", getName(), all.size(), all.size() == 1 ? "" : "s");
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
			return ColorfulPipes.asResource(recipeType + "/" + getRegistryName().getPath() + suffix);
		}

		private ResourceLocation createLocation(String recipeType) {
			return ColorfulPipes.asResource(recipeType + "/" + path + "/" + getRegistryName().getPath() + suffix);
		}

		private ResourceLocation getRegistryName() {
			return compatDatagenOutput == null ? RegisteredObjectsHelper.getKeyOrThrow(result.get()
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
				return create(RecipeSerializer.SMELTING_RECIPE, builder, SmeltingRecipe::new, 1);
			}

			GeneratedRecipe inSmoker() {
				return inSmoker(b -> b);
			}

			GeneratedRecipe inSmoker(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
				create(RecipeSerializer.SMELTING_RECIPE, builder, SmeltingRecipe::new, 1);
				create(RecipeSerializer.CAMPFIRE_COOKING_RECIPE, builder, CampfireCookingRecipe::new, 3);
				return create(RecipeSerializer.SMOKING_RECIPE, builder, SmokingRecipe::new, .5f);
			}

			GeneratedRecipe inBlastFurnace() {
				return inBlastFurnace(b -> b);
			}

			GeneratedRecipe inBlastFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
				create(RecipeSerializer.SMELTING_RECIPE, builder, SmeltingRecipe::new, 1);
				return create(RecipeSerializer.BLASTING_RECIPE, builder, BlastingRecipe::new, .5f);
			}

			private <T extends AbstractCookingRecipe> GeneratedRecipe create(RecipeSerializer<T> serializer,
																			 UnaryOperator<SimpleCookingRecipeBuilder> builder, AbstractCookingRecipe.Factory<T> factory, float cookingTimeModifier) {
				return register(recipeOutput -> {
					boolean isOtherMod = compatDatagenOutput != null;

					SimpleCookingRecipeBuilder b = builder.apply(SimpleCookingRecipeBuilder.generic(ingredient.get(),
							RecipeCategory.MISC, isOtherMod ? Items.DIRT : result.get(), exp,
							(int) (cookingTime * cookingTimeModifier), serializer, factory));
					if (unlockedBy != null)
						b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

					RecipeOutput conditionalOutput = recipeOutput.withConditions(recipeConditions.toArray(new ICondition[0]));

					b.save(
							isOtherMod ? new ModdedCookingRecipeOutput(conditionalOutput, compatDatagenOutput) : conditionalOutput,
							createSimpleLocation(RegisteredObjectsHelper.getKeyOrThrow(serializer).getPath())
					);
				});
			}
		}
	}

	@ParametersAreNonnullByDefault
	@MethodsReturnNonnullByDefault
	private static class ModdedCookingRecipeOutputShim implements Recipe<RecipeInput> {

		private static final Map<RecipeType<?>, ModdedCookingRecipeOutputShim.Serializer> serializers = new ConcurrentHashMap<>();

		private final Recipe<?> wrapped;
		private final ResourceLocation overrideID;

		private ModdedCookingRecipeOutputShim(Recipe<?> wrapped, ResourceLocation overrideID) {
			this.wrapped = wrapped;
			this.overrideID = overrideID;
		}

		@Override
		public boolean matches(RecipeInput recipeInput, Level level) {
			throw new AssertionError("Only for datagen output");
		}

		@Override
		public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
			throw new AssertionError("Only for datagen output");
		}

		@Override
		public boolean canCraftInDimensions(int pWidth, int pHeight) {
			throw new AssertionError("Only for datagen output");
		}

		@Override
		public ItemStack getResultItem(HolderLookup.Provider registries) {
			throw new AssertionError("Only for datagen output");
		}

		@Override
		public RecipeSerializer<?> getSerializer() {
			return serializers.computeIfAbsent(
					getType(),
					t -> ModdedCookingRecipeOutputShim.Serializer.create(wrapped)
			);
		}

		@Override
		public RecipeType<?> getType() {
			return wrapped.getType();
		}

		private record Serializer(
				MapCodec<Recipe<?>> wrappedCodec) implements RecipeSerializer<ModdedCookingRecipeOutputShim> {
			private static ModdedCookingRecipeOutputShim.Serializer create(Recipe<?> wrapped) {
				RecipeSerializer<?> wrappedSerializer = wrapped.getSerializer();
				@SuppressWarnings("unchecked")
				ModdedCookingRecipeOutputShim.Serializer serializer = new ModdedCookingRecipeOutputShim.Serializer((MapCodec<Recipe<?>>) wrappedSerializer.codec());

				// Need to do some registry injection to get the Recipe/Registry#byNameCodec to encode the right type for this
				// getResourceKey and getId
				// byValue and toId
				// Holder.Reference: key
				if (BuiltInRegistries.RECIPE_SERIALIZER instanceof MappedRegistryAccessor<?> mra) {
					@SuppressWarnings("unchecked")
					MappedRegistryAccessor<RecipeSerializer<?>> mra$ = (MappedRegistryAccessor<RecipeSerializer<?>>) mra;

					int wrappedId = mra$.getToId().getOrDefault(wrappedSerializer, -1);
					ResourceKey<RecipeSerializer<?>> wrappedKey = mra$.getByValue().get(wrappedSerializer).key();

					mra$.getToId().put(serializer, wrappedId);
					//noinspection DataFlowIssue - it is ok to pass null as the owner, because this is only being used for serialization
					mra$.getByValue().put(serializer, Holder.Reference.createStandAlone(null, wrappedKey));
				} else {
					throw new AssertionError("ModdedCookingRecipeOutputShim will not be able to" +
							" serialize without injecting into a registry. Expected" +
							" BuiltInRegistries.RECIPE_SERIALIZER to be of class MappedRegistry, is of class " +
							BuiltInRegistries.RECIPE_SERIALIZER.getClass()
					);
				}
				return serializer;
			}

			@Override
			public MapCodec<ModdedCookingRecipeOutputShim> codec() {
				return RecordCodecBuilder.mapCodec(instance -> instance.group(
						wrappedCodec.forGetter(i -> i.wrapped),
						ModdedCookingRecipeOutputShim.FakeItemStack.CODEC.fieldOf("result").forGetter(i -> new ModdedCookingRecipeOutputShim.FakeItemStack(i.overrideID))
				).apply(instance, (wrappedRecipe, fakeItemStack) -> {
					throw new AssertionError("Only for datagen output");
				}));
			}

			@Override
			public StreamCodec<RegistryFriendlyByteBuf, ModdedCookingRecipeOutputShim> streamCodec() {
				throw new AssertionError("Only for datagen output");
			}
		}

		private record FakeItemStack(ResourceLocation id) {
			public static Codec<ModdedCookingRecipeOutputShim.FakeItemStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
					ResourceLocation.CODEC.fieldOf("id").forGetter(ModdedCookingRecipeOutputShim.FakeItemStack::id)
			).apply(instance, ModdedCookingRecipeOutputShim.FakeItemStack::new));
		}
	}

	@ParametersAreNonnullByDefault
	@MethodsReturnNonnullByDefault
	private record ModdedCookingRecipeOutput(RecipeOutput wrapped,
											 ResourceLocation outputOverride) implements RecipeOutput {

		@Override
		public Advancement.Builder advancement() {
			return wrapped.advancement();
		}

		@Override
		public void accept(ResourceLocation id, Recipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition... conditions) {
			wrapped.accept(id, new ModdedCookingRecipeOutputShim(recipe, outputOverride), advancement, conditions);
		}
	}
}
