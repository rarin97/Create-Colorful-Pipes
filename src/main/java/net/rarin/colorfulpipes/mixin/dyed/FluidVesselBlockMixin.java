package net.rarin.colorfulpipes.mixin.dyed;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlockEntity;
import com.hlysine.create_connected.registries.CCBlockEntityTypes;
import com.simibubi.create.api.connectivity.ConnectivityHandler;

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
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.compat.Create_Connected.CCBlocks;

import net.rarin.colorfulpipes.compat.ModMixin;

import net.rarin.colorfulpipes.config.CCPConfigs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import static com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock.AXIS;

@ModMixin(mods = {"create_connected"})
@Mixin(FluidVesselBlock.class)
public class FluidVesselBlockMixin {

	@Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
	private void useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
						  BlockHitResult hitResult, CallbackInfoReturnable<ItemInteractionResult> cir) {

		if (!CCPConfigs.server().recipes.enableVesselItemApplication.get())
			return;

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				FluidVesselBlockEntity controller = ConnectivityHandler.partAt(CCBlockEntityTypes.FLUID_VESSEL.get(), level, pos);

				Set<BlockPos> tanks = new HashSet<>();
				Queue<BlockPos> queue = new ArrayDeque<>();

				queue.add(pos);
				tanks.add(pos);

				while (!queue.isEmpty()) {
					BlockPos current = queue.poll();

					for (Direction dir : Direction.values()) {

						if (tanks.contains(current.relative(dir)))
							continue;

						if (!(level.getBlockEntity(current.relative(dir)) instanceof FluidVesselBlockEntity tank))
							continue;

						if (!tank.getController().equals(controller.getController()))
							continue;

						tanks.add(current.relative(dir));
						queue.add(current.relative(dir));
					}
				}

				for (BlockPos p : tanks) {
					level.setBlock(p, CCBlocks.COLORFUL_FLUID_VESSELS.get(dyeColor).getDefaultState()
							.setValue(AXIS, state.getValue(AXIS)), 3);
				}

				if (!player.isCreative())
					stack.shrink(1);
			}
			cir.setReturnValue(ItemInteractionResult.SUCCESS);
		}
	}
}
