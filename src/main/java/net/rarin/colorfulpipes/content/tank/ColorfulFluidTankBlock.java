package net.rarin.colorfulpipes.content.tank;

import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPBlocks;

public class ColorfulFluidTankBlock extends FluidTankBlock {

	protected final DyeColor color;

	public ColorfulFluidTankBlock(Properties properties, DyeColor color) {
		super(properties, false);
		this.color = color;
	}

	public DyeColor getColor() {
		return color;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color)
				return InteractionResult.PASS;

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				FluidTankBlockEntity oldTank = (FluidTankBlockEntity) level.getBlockEntity(pos);
				if (oldTank == null) return InteractionResult.SUCCESS;

				var oldFluid = oldTank.getTank(0).getFluid().copy();

				level.setBlock(pos, CCPBlocks.COLORFUL_FLUID_TANKS.get(dyeColor).getDefaultState(), 3);

				FluidTankBlockEntity newTank = (FluidTankBlockEntity) level.getBlockEntity(pos);
				if (newTank != null) {
					newTank.getTank(0).setFluid(oldFluid);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return super.use(state, level, pos, player, hand, hit);
	}

	public BlockEntityType<? extends FluidTankBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_FLUID_TANKS.get();
	}
}
