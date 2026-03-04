package net.rarin.colorfulpipes;

import com.simibubi.create.AllCreativeModeTabs.TabInfo;
import com.simibubi.create.Create;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import org.apache.commons.lang3.mutable.MutableObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CCPCreativeTabs {


	public static final TabInfo MAIN = register("main", () -> FabricItemGroup.builder()
			.title(Component.translatable("itemGroup.colorfulpipes.main"))
			.icon(() -> CCPBlocks.COLORFUL_DRAINS.get(DyeColor.RED).asStack())
			.displayItems(new ItemDisplay())
			.build());

	private static TabInfo register(String name, Supplier<CreativeModeTab> supplier) {
		ResourceLocation id = ColorfulPipes.asResource(name);
		ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
		CreativeModeTab tab = supplier.get();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
		return new TabInfo(key, tab);
	}

	public static void register() {
	}

	public static class ItemDisplay implements CreativeModeTab.DisplayItemsGenerator {

		@Override
		public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
			ColorfulPipes.REGISTRATE.getAll(Registries.ITEM)
					.forEach(entry -> output.accept(entry.get()));
		}
	}

}
