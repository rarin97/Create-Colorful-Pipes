package net.rarin.colorfulpipes.content.hosePulley;

import com.simibubi.create.content.fluids.pipes.FluidPipeBlock;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPBlocks;

public class ColorfulHosePulleyBlock  extends HorizontalKineticBlock implements IBE<ColorfulHosePulleyBlockEntity> {

	protected final DyeColor color;

	public ColorfulHosePulleyBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	public DyeColor getColor() {
		return color;
	}

	@Override
	public 	ItemInteractionResult useItemOn(ItemStack stack,BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color)
				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				CompoundTag oldData = null;

				if (level.getBlockEntity(pos) instanceof ColorfulHosePulleyBlockEntity oldPulley) {
					oldData = new CompoundTag();
					oldPulley.saveAdditional(oldData, level.registryAccess());
				}

				level.setBlock(pos,CCPBlocks.COLORFUL_HOSE_PULLEYS.get(dyeColor).getDefaultState()
								.setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)), 3);

				if (oldData != null &&
						level.getBlockEntity(pos) instanceof ColorfulHosePulleyBlockEntity newPulley) {
					newPulley.loadWithComponents(oldData, level.registryAccess());
				}
			}

			return ItemInteractionResult.SUCCESS;
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	@Override
	public Direction.Axis getRotationAxis(BlockState state) {
		return state.getValue(HORIZONTAL_FACING)
				.getClockWise()
				.getAxis();
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction preferredHorizontalFacing = getPreferredHorizontalFacing(context);
		return this.defaultBlockState()
				.setValue(HORIZONTAL_FACING,
						preferredHorizontalFacing != null ? preferredHorizontalFacing.getCounterClockWise()
								: context.getHorizontalDirection()
								.getOpposite());
	}

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return state.getValue(HORIZONTAL_FACING)
				.getClockWise() == face;
	}

	public static boolean hasPipeTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return state.getValue(HORIZONTAL_FACING)
				.getCounterClockWise() == face;
	}

	@Override
	public Direction getPreferredHorizontalFacing(BlockPlaceContext context) {
		Direction fromParent = super.getPreferredHorizontalFacing(context);
		if (fromParent != null)
			return fromParent;

		Direction prefferedSide = null;
		for (Direction facing : Iterate.horizontalDirections) {
			BlockPos pos = context.getClickedPos()
					.relative(facing);
			BlockState blockState = context.getLevel()
					.getBlockState(pos);
			if (FluidPipeBlock.canConnectTo(context.getLevel(), pos, blockState, facing))
				if (prefferedSide != null && prefferedSide.getAxis() != facing.getAxis()) {
					prefferedSide = null;
					break;
				} else
					prefferedSide = facing;
		}
		return prefferedSide == null ? null : prefferedSide.getOpposite();
	}

	@Override
	public Class<ColorfulHosePulleyBlockEntity> getBlockEntityClass() {
		return ColorfulHosePulleyBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends ColorfulHosePulleyBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_HOSE_PULLEYS.get();
	}

}
