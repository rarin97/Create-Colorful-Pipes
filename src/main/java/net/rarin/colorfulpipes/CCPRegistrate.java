package net.rarin.colorfulpipes;

import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.createmod.catnip.platform.CatnipServices;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class CCPRegistrate extends CreateRegistrate {
	protected CCPRegistrate(String modid) {
		super(modid);
	}

	public static <T extends Block> NonNullConsumer<? super T> ColorfulblockModel(
			Supplier<NonNullBiFunction<BakedModel, DyeColor, ? extends BakedModel>> func, DyeColor color) {
		return entry ->  CatnipServices.PLATFORM.executeOnClientOnly(() -> () -> registerColorfulBlockModel(entry, func, color));
	}

	@OnlyIn(Dist.CLIENT)
	private static void registerColorfulBlockModel(Block entry,
												   Supplier<NonNullBiFunction<BakedModel, DyeColor, ? extends BakedModel>> func, DyeColor color) {
		CreateClient.MODEL_SWAPPER.getCustomBlockModels()
				.register(RegisteredObjectsHelper.getKeyOrThrow(entry), model -> func.get().apply(model, color));
	}
}
