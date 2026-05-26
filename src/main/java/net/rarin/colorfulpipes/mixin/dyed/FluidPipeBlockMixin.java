package net.rarin.colorfulpipes.mixin.dyed;

import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlock;

import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlocks;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock.FACE;
import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

@Mixin(FluidPipeBlock.class)
public class FluidPipeBlockMixin {

	@Unique
	private DyeColor color;

	public FluidPipeBlockMixin(DyeColor color) {
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

				BlockState newState  = CCPBlocks.COLORFUL_FLUID_PIPES.get(dyeColor).getDefaultState()
						.setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED));

				for (Direction dir : Iterate.directions)
					newState = newState.setValue(FluidPipeBlock.PROPERTY_BY_DIRECTION.get(dir),
							state.getValue(FluidPipeBlock.PROPERTY_BY_DIRECTION.get(dir)));

				Direction firstFound = Direction.UP;
				for (Direction d : Iterate.directions)
					if (state.getValue(FluidPipeBlock.PROPERTY_BY_DIRECTION.get(d))) {
						firstFound = d;
						break;
					}

				FluidTransportBehaviour.cacheFlows(level, pos);

				level.setBlockAndUpdate(pos, CCPBlocks.COLORFUL_FLUID_PIPES.get(dyeColor).get()
						.updateBlockState(newState , firstFound, null, level, pos));

				FluidTransportBehaviour.loadFlows(level, pos);

				if (!player.isCreative())
					stack.shrink(1);
			}
			cir.setReturnValue(ItemInteractionResult.SUCCESS);
		}
	}
}
