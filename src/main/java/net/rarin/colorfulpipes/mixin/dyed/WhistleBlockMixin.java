package net.rarin.colorfulpipes.mixin.dyed;

import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;

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

import net.rarin.colorfulpipes.CCPPaletteBlocks;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.simibubi.create.content.decoration.steamWhistle.WhistleBlock.FACING;
import static com.simibubi.create.content.decoration.steamWhistle.WhistleBlock.POWERED;
import static com.simibubi.create.content.decoration.steamWhistle.WhistleBlock.SIZE;
import static com.simibubi.create.content.decoration.steamWhistle.WhistleBlock.WALL;

@Mixin(WhistleBlock.class)
public class WhistleBlockMixin {

	@Unique
	private DyeColor color;

	public WhistleBlockMixin(DyeColor color) {
		this.color = color;
	}

	@Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
	private void useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<ItemInteractionResult> cir) {
		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color) {
				cir.setReturnValue(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
				return;
			}

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));
				level.setBlock(pos, CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.get(dyeColor).getDefaultState()
						.setValue(FACING, state.getValue(FACING)).setValue(SIZE, state.getValue(SIZE))
						.setValue(POWERED, state.getValue(POWERED)).setValue(WALL, state.getValue(WALL)), 3);

				if (!player.isCreative())
					stack.shrink(1);
			}
			cir.setReturnValue(ItemInteractionResult.SUCCESS);
		}
	}
}
