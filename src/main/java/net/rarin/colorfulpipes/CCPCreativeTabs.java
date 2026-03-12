package net.rarin.colorfulpipes;

import com.simibubi.create.AllCreativeModeTabs.TabInfo;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;

import java.util.function.Supplier;

public class CCPCreativeTabs {


	public static final TabInfo MAIN = register("main", () -> FabricItemGroup.builder()
			.title(Component.translatable("itemGroup.colorfulpipes.main"))
			.icon(() -> CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.ORANGE).asStack())
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
