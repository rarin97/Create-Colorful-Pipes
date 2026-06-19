package net.rarin.colorfulpipes.mixin.dyed;

import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.config.CCPConfigs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock.FACE;
import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

@Mixin(SteamEngineBlock.class)
public class SteamEngineBlockMixin {

	@Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
	private void useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
						   BlockHitResult hitResult, CallbackInfoReturnable<ItemInteractionResult> cir) {

		if (!CCPConfigs.server().recipes.enableEngineItemApplication.get())
			return;

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));
				level.setBlockAndUpdate(pos, CCPBlocks.COLORFUL_STEAM_ENGINES.get(dyeColor).getDefaultState()
						.setValue(FACING, state.getValue(FACING)).setValue(WATERLOGGED, state.getValue(WATERLOGGED))
						.setValue(FACE, state.getValue(FACE)));

				if (!player.isCreative())
					stack.shrink(1);
			}
			cir.setReturnValue(ItemInteractionResult.SUCCESS);
		}
	}
}
