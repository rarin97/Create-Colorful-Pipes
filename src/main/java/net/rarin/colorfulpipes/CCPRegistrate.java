package net.rarin.colorfulpipes;

import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullConsumer;

import net.createmod.catnip.platform.CatnipServices;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class CCPRegistrate extends CreateRegistrate {
	protected CCPRegistrate(String modid) {
		super(modid);
	}

	public static <T extends Block> NonNullConsumer<? super T> ColorfulblockModel(
			Supplier<NonNullBiFunction<BakedModel, DyeColor, ? extends BakedModel>> func, DyeColor color) {
		return entry -> onClient(() -> () -> registerColorfulBlockModel(entry, func, color));
	}

	@Environment(EnvType.CLIENT)
	private static void registerColorfulBlockModel(Block entry,
												   Supplier<NonNullBiFunction<BakedModel, DyeColor, ? extends BakedModel>> func, DyeColor color) {
		CreateClient.MODEL_SWAPPER.getCustomBlockModels()
				.register(CatnipServices.REGISTRIES.getKeyOrThrow(entry), model -> func.get().apply(model, color));
	}
}
