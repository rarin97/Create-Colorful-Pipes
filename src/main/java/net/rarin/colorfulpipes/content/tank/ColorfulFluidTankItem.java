package net.rarin.colorfulpipes.content.tank;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.equipment.symmetryWand.SymmetryWandItem;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.fluids.tank.FluidTankItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulFluidTankItem extends FluidTankItem {
	public ColorfulFluidTankItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public InteractionResult place(BlockPlaceContext ctx) {
		IS_PLACING_NBT = FluidTankItem.checkPlacingNbt(ctx);
		InteractionResult initialResult = super.place(ctx);
		IS_PLACING_NBT = false;
		if (!initialResult.consumesAction())
			return initialResult;
		tryMultiPlace(ctx);
		return initialResult;
	}

	private void tryMultiPlace(BlockPlaceContext ctx) {
		Player player = ctx.getPlayer();
		if (player == null)
			return;
		if (player.isShiftKeyDown())
			return;
		Direction face = ctx.getClickedFace();
		if (!face.getAxis()
				.isVertical())
			return;
		ItemStack stack = ctx.getItemInHand();
		Level world = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		BlockPos placedOnPos = pos.relative(face.getOpposite());
		BlockState placedOnState = world.getBlockState(placedOnPos);

		if (!ColorfulFluidTankBlock.isTank(placedOnState))
			return;
		if (SymmetryWandItem.presentInHotbar(player))
			return;
		FluidTankBlockEntity tankAt = ConnectivityHandler.partAt(
				CCPBlockEntityTypes.COLORFUL_FLUID_TANKS.get(), world, placedOnPos
		);
		if (tankAt == null)
			return;
		FluidTankBlockEntity controllerBE = tankAt.getControllerBE();
		if (controllerBE == null)
			return;

		int width = controllerBE.getWidth();
		if (width == 1)
			return;

		int tanksToPlace = 0;
		BlockPos startPos = face == Direction.DOWN ? controllerBE.getBlockPos()
				.below()
				: controllerBE.getBlockPos()
				.above(controllerBE.getHeight());

		if (startPos.getY() != pos.getY())
			return;

		for (int xOffset = 0; xOffset < width; xOffset++) {
			for (int zOffset = 0; zOffset < width; zOffset++) {
				BlockPos offsetPos = startPos.offset(xOffset, 0, zOffset);
				BlockState blockState = world.getBlockState(offsetPos);
				if (ColorfulFluidTankBlock.isTank(blockState))
					continue;
				if (!blockState.canBeReplaced())
					return;
				tanksToPlace++;
			}
		}

		if (!player.isCreative() && stack.getCount() < tanksToPlace)
			return;

		for (int xOffset = 0; xOffset < width; xOffset++) {
			for (int zOffset = 0; zOffset < width; zOffset++) {
				BlockPos offsetPos = startPos.offset(xOffset, 0, zOffset);
				BlockState blockState = world.getBlockState(offsetPos);
				if (ColorfulFluidTankBlock.isTank(blockState))
					continue;
				BlockPlaceContext context = BlockPlaceContext.at(ctx, offsetPos, face);
				player.getCustomData()
						.putBoolean("SilenceTankSound", true);
				IS_PLACING_NBT = checkPlacingNbt(context);
				super.place(context);
				IS_PLACING_NBT = false;
				player.getCustomData()
						.remove("SilenceTankSound");
			}
		}
	}
}
