package net.rarin.colorfulpipes;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CCPCreativeTabs {
	private static final DeferredRegister<CreativeModeTab> REGISTER =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ColorfulPipes.ID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = REGISTER.register("base",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.colorfulpipes.main"))
					.withTabsBefore(com.simibubi.create.AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
					.icon(() -> CCPBlocks.COLORFUL_SMART_FLUID_PIPES.get(DyeColor.ORANGE).asStack())
					.displayItems(new ItemDisplay())
					.build());


	public static void register(IEventBus modEventBus) {
		REGISTER.register(modEventBus);
	}

	public static class ItemDisplay implements CreativeModeTab.DisplayItemsGenerator {

		@Override
		public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
			ColorfulPipes.REGISTRATE.getAll(Registries.ITEM)
					.forEach(entry -> output.accept(entry.get()));
		}
	}

}
