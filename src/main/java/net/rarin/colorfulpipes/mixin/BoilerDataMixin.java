package net.rarin.colorfulpipes.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.tank.BoilerData;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.content.steamEngine.ColorfulSteamEngineBlock;
import net.rarin.colorfulpipes.content.steamWhistle.ColorfulWhistleBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoilerData.class)
public class BoilerDataMixin {

	@WrapOperation(method = "evaluate", at = @At(value = "INVOKE",
			target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z")
	)
	private boolean colorfulpipes$acceptColorfulBlocks(BlockEntry<?> entry, BlockState state, Operation<Boolean> original) {
		if (entry == AllBlocks.STEAM_ENGINE && state.getBlock() instanceof ColorfulSteamEngineBlock)
			return true;

		if (entry == AllBlocks.STEAM_WHISTLE && state.getBlock() instanceof ColorfulWhistleBlock)
			return true;

		return original.call(entry, state);
	}
}
