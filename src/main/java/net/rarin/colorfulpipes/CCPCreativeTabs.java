package net.rarin.colorfulpipes;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraft.world.item.CreativeModeTab.DisplayItemsGenerator;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.rarin.colorfulpipes.compat.CreateDragonsPlus.CDPBlocks;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.CEIBlocks;
import net.rarin.colorfulpipes.compat.Create_Connected.CCBlocks;
import net.rarin.colorfulpipes.compat.Mods;
import net.rarin.colorfulpipes.config.CCPConfigs;

import org.apache.commons.lang3.mutable.MutableObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static net.minecraft.world.item.CreativeModeTab.builder;

public class CCPCreativeTabs {
	private static final DeferredRegister<CreativeModeTab> REGISTER =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ColorfulPipes.ID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = REGISTER.register("base",
			() -> builder()
					.title(Component.translatable("itemGroup.colorfulpipes.main"))
					.withTabsBefore(com.simibubi.create.AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
					.icon(() -> CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.ORANGE).asStack())
					.displayItems(new ItemDisplay(true, CCPCreativeTabs.MAIN))
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PALETTES = REGISTER.register("palettes",
			() -> builder()
					.title(Component.translatable("itemGroup.colorfulpipes.palettes"))
					.withTabsBefore(com.simibubi.create.AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
					.icon(() -> CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(DyeColor.RED).asStack())
					.displayItems(new ItemDisplay(false, CCPCreativeTabs.PALETTES))
					.build());


	public static void register(IEventBus modEventBus) {
		REGISTER.register(modEventBus);
	}

	public static class ItemDisplay implements DisplayItemsGenerator {
		private static final Predicate<Item> IS_ITEM_3D_PREDICATE;

		static {
			MutableObject<Predicate<Item>> isItem3d = new MutableObject<>(item -> false);
			if (CatnipServices.PLATFORM.getEnv().isClient())
				isItem3d.setValue(item -> {
					ItemRenderer itemRenderer = Minecraft.getInstance()
							.getItemRenderer();
					BakedModel model = itemRenderer.getModel(new ItemStack(item), null, null, 0);
					return model.isGui3d();
				});
			IS_ITEM_3D_PREDICATE = isItem3d.getValue();
		}

		private final boolean addItems;
		private final DeferredHolder<CreativeModeTab, CreativeModeTab> tabFilter;

		public ItemDisplay(boolean addItems, DeferredHolder<CreativeModeTab, CreativeModeTab> tabFilter) {
			this.addItems = addItems;
			this.tabFilter = tabFilter;
		}

		private static Predicate<Item> makeExclusionPredicate() {
			Set<Item> exclusions = new ReferenceOpenHashSet<>();

			for (DyeColor color : DyeColor.values()) {
				if (!CCPConfigs.common().toggle.Pipe.get())
					exclusions.add(CCPBlocks.COLORFUL_FLUID_PIPES.get(color).asItem());
				if (!CCPConfigs.common().toggle.SmartPipe.get())
					exclusions.add(CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(color).asItem());
				if (!CCPConfigs.common().toggle.Pump.get())
					exclusions.add(CCPBlocks.COLORFUL_PUMPS.get(color).asItem());
				if (!CCPConfigs.common().toggle.Valve.get())
					exclusions.add(CCPBlocks.COLORFUL_FLUID_VALVES.get(color).asItem());
				if (!CCPConfigs.common().toggle.Tank.get())
					exclusions.add(CCPBlocks.COLORFUL_FLUID_TANKS.get(color).asItem());
				if (!CCPConfigs.common().toggle.Spout.get())
					exclusions.add(CCPBlocks.COLORFUL_SPOUTS.get(color).asItem());
				if (!CCPConfigs.common().toggle.Drain.get())
					exclusions.add(CCPBlocks.COLORFUL_DRAINS.get(color).asItem());
				if (!CCPConfigs.common().toggle.FluidInterface.get())
					exclusions.add(CCPBlocks.COLORFUL_FLUID_INTERFACES.get(color).asItem());
				if (!CCPConfigs.common().toggle.HosePulley.get())
					exclusions.add(CCPBlocks.COLORFUL_HOSE_PULLEYS.get(color).asItem());
				if (!CCPConfigs.common().toggle.Engine.get())
					exclusions.add(CCPBlocks.COLORFUL_STEAM_ENGINES.get(color).asItem());
				if (!CCPConfigs.common().toggle.Whistle.get())
					exclusions.add(CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(color).asItem());
				if (!CCPConfigs.common().toggle.TableCloth.get())
					exclusions.add(CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.get(color).asItem());
				if (!CCPConfigs.common().toggle.Casing.get())
					exclusions.add(CCPPaletteBlocks.COLORFUL_COPPER_CASING.get(color).asItem());
				if (!CCPConfigs.common().toggle.GlassCasing.get()) {
					exclusions.add(CCPPaletteBlocks.COLORFUL_COPPER_GLASS_CASING.get(color).asItem());
					exclusions.add(CCPPaletteBlocks.COPPER_GLASS_CASING.asItem());
				}
				if (!CCPConfigs.common().toggle.TintedGlassCasing.get()) {
					exclusions.add(CCPPaletteBlocks.COLORFUL_COPPER_TINTED_GLASS_CASING.get(color).asItem());
					exclusions.add(CCPPaletteBlocks.COPPER_TINTED_GLASS_CASING.asItem());
				}
				if (!CCPConfigs.common().toggle.Ladder.get())
					exclusions.add(CCPPaletteBlocks.COLORFUL_COPPER_LADDER.get(color).asItem());
				if (!CCPConfigs.common().toggle.Scaffold.get())
					exclusions.add(CCPPaletteBlocks.COLORFUL_COPPER_SCAFFOLD.get(color).asItem());
				if (!CCPConfigs.common().toggle.Bar.get())
					exclusions.add(CCPPaletteBlocks.COLORFUL_COPPER_BARS.get(color).asItem());
				if (!CCPConfigs.common().toggle.Door.get())
					exclusions.add(CCPPaletteBlocks.COLORFUL_COPPER_DOOR.get(color).asItem());
				if (!CCPConfigs.common().toggle.Vessel.get() && Mods.CREATE_CONNECTED.isLoaded())
					exclusions.add(CCBlocks.COLORFUL_FLUID_VESSELS.get(color).asItem());
				if (!CCPConfigs.common().toggle.FluidHatch.get() && Mods.CREATE_DRAGONS_PLUS.isLoaded())
					exclusions.add(CDPBlocks.COLORFUL_FLUID_HATCHES.get(color).asItem());
				if (!CCPConfigs.common().toggle.XPHatch.get() && Mods.CREATE_ENCHANTMENT_INDUSTRY.isLoaded())
					exclusions.add(CEIBlocks.COLORFUL_EXPERIENCE_HATCHES.get(color).asItem());
				if (!CCPConfigs.common().toggle.XPLantern.get() && Mods.CREATE_ENCHANTMENT_INDUSTRY.isLoaded())
					exclusions.add(CEIBlocks.COLORFUL_EXPERIENCE_LANTERNS.get(color).asItem());
				if (!CCPConfigs.common().toggle.Printer.get() && Mods.CREATE_ENCHANTMENT_INDUSTRY.isLoaded())
					exclusions.add(CEIBlocks.COLORFUL_PRINTERS.get(color).asItem());
			}
				exclusions.add(CCPPaletteBlocks.COPPER_ENCASED_COGWHEEL.asItem());
				exclusions.add(CCPPaletteBlocks.COPPER_ENCASED_LARGE_COGWHEEL.asItem());

			return exclusions::contains;
		}

		private static List<ItemOrdering> makeOrderings() {
			List<ItemOrdering> orderings = new ReferenceArrayList<>();

			Map<ItemProviderEntry<?, ?>, ItemProviderEntry<?, ?>> simpleBeforeOrderings = Map.of(
			);

			Map<ItemProviderEntry<?, ?>, ItemProviderEntry<?, ?>> simpleAfterOrderings = Map.of(
			);

			simpleBeforeOrderings.forEach((entry, otherEntry) -> {
				orderings.add(ItemOrdering.before(entry.asItem(), otherEntry.asItem()));
			});

			simpleAfterOrderings.forEach((entry, otherEntry) -> {
				orderings.add(ItemOrdering.after(entry.asItem(), otherEntry.asItem()));
			});

			return orderings;
		}

		private static Function<Item, ItemStack> makeStackFunc() {
			Map<Item, Function<Item, ItemStack>> factories = new Reference2ReferenceOpenHashMap<>();

			Map<ItemProviderEntry<?, ?>, Function<Item, ItemStack>> simpleFactories = Map.of(
			);

			simpleFactories.forEach((entry, factory) -> {
				factories.put(entry.asItem(), factory);
			});

			return item -> {
				Function<Item, ItemStack> factory = factories.get(item);
				if (factory != null) {
					return factory.apply(item);
				}
				return new ItemStack(item);
			};
		}

		private static Function<Item, TabVisibility> makeVisibilityFunc() {
			Map<Item, TabVisibility> visibilities = new Reference2ObjectOpenHashMap<>();

			Map<ItemProviderEntry<?, ?>, TabVisibility> simpleVisibilities = Map.of(
			);

			simpleVisibilities.forEach((entry, factory) -> {
				visibilities.put(entry.asItem(), factory);
			});

			return item -> {
				TabVisibility visibility = visibilities.get(item);
				if (visibility != null) {
					return visibility;
				}
				return TabVisibility.PARENT_AND_SEARCH_TABS;
			};
		}

		@Override
		public void accept(ItemDisplayParameters parameters, Output output) {
			Predicate<Item> exclusionPredicate = makeExclusionPredicate();
			List<ItemOrdering> orderings = makeOrderings();
			Function<Item, ItemStack> stackFunc = makeStackFunc();
			Function<Item, TabVisibility> visibilityFunc = makeVisibilityFunc();

			List<Item> items = new LinkedList<>();
			if (addItems) {
				items.addAll(collectItems(exclusionPredicate.or(IS_ITEM_3D_PREDICATE.negate())));
			}
			items.addAll(collectBlocks(exclusionPredicate));
			if (addItems) {
				items.addAll(collectItems(exclusionPredicate.or(IS_ITEM_3D_PREDICATE)));
			}

			applyOrderings(items, orderings);
			outputAll(output, items, stackFunc, visibilityFunc);
		}

		private List<Item> collectBlocks(Predicate<Item> exclusionPredicate) {
			List<Item> items = new ReferenceArrayList<>();
			for (RegistryEntry<Block, Block> entry : ColorfulPipes.registrate().getAll(Registries.BLOCK)) {
				if (!CreateRegistrate.isInCreativeTab(entry, tabFilter))
					continue;
				Item item = entry.get()
						.asItem();
				if (item == Items.AIR)
					continue;
				if (!exclusionPredicate.test(item))
					items.add(item);
			}
			items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
			return items;
		}

		private List<Item> collectItems(Predicate<Item> exclusionPredicate) {
			List<Item> items = new ReferenceArrayList<>();
			for (RegistryEntry<Item, Item> entry : ColorfulPipes.registrate().getAll(Registries.ITEM)) {
				if (!CreateRegistrate.isInCreativeTab(entry, tabFilter))
					continue;
				Item item = entry.get();
				if (item instanceof BlockItem)
					continue;
				if (!exclusionPredicate.test(item))
					items.add(item);
			}
			return items;
		}

		private static void applyOrderings(List<Item> items, List<ItemOrdering> orderings) {
			for (ItemOrdering ordering : orderings) {
				int anchorIndex = items.indexOf(ordering.anchor());
				if (anchorIndex != -1) {
					Item item = ordering.item();
					int itemIndex = items.indexOf(item);
					if (itemIndex != -1) {
						items.remove(itemIndex);
						if (itemIndex < anchorIndex) {
							anchorIndex--;
						}
					}
					if (ordering.type() == ItemOrdering.Type.AFTER) {
						items.add(anchorIndex + 1, item);
					} else {
						items.add(anchorIndex, item);
					}
				}
			}
		}

		private static void outputAll(Output output, List<Item> items, Function<Item, ItemStack> stackFunc, Function<Item, TabVisibility> visibilityFunc) {
			for (Item item : items) {
				output.accept(stackFunc.apply(item), visibilityFunc.apply(item));
			}
		}

		private record ItemOrdering(Item item, Item anchor, Type type) {
			public static ItemOrdering before(Item item, Item anchor) {
				return new ItemOrdering(item, anchor, Type.BEFORE);
			}

			public static ItemOrdering after(Item item, Item anchor) {
				return new ItemOrdering(item, anchor, Type.AFTER);
			}

			public enum Type {
				BEFORE,
				AFTER;
			}
		}
	}
}
