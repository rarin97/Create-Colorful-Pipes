package net.rarin.colorfulpipes.mixin;


import com.hlysine.create_connected.content.fluidvessel.BoilerData;

import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.content.steamEngine.ColorfulSteamEngineBlock;
import net.rarin.colorfulpipes.content.steamWhistle.ColorfulWhistleBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoilerData.class)
public class CCBoilerDataMixin {

	@Redirect(method = "evaluate", at = @At(value = "INVOKE",
			target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
	private boolean acceptColorfulBlocks(BlockEntry<?> entry, BlockState state) {

		if (entry == AllBlocks.STEAM_ENGINE) {
			if (state.getBlock() instanceof ColorfulSteamEngineBlock)
				return true;
		}
		if (entry == AllBlocks.STEAM_WHISTLE) {
			if (state.getBlock() instanceof ColorfulWhistleBlock)
				return true;
		}
		return entry.has(state);
	}
}
