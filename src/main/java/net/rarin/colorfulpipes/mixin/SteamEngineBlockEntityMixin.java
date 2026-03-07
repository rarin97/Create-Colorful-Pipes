package net.rarin.colorfulpipes.mixin;

import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.rarin.colorfulpipes.CCPBlocks;

import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SteamEngineBlockEntity.class, remap = false)
public abstract class SteamEngineBlockEntityMixin {

	@Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
	private void isValid(CallbackInfoReturnable<Boolean> cir) {
		SteamEngineBlockEntity be = (SteamEngineBlockEntity) (Object) this;
		Level level = be.getLevel();
		if (level == null) {
			cir.setReturnValue(false);
			return;
		}

		Direction dir = SteamEngineBlock.getConnectedDirection(be.getBlockState()).getOpposite();
		var targetBlock = level.getBlockState(be.getBlockPos().relative(dir)).getBlock();

		if (targetBlock instanceof ColorfulFluidTankBlock) {
			cir.setReturnValue(true);
		}
	}
}
