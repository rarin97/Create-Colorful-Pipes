package net.rarin.colorfulpipes.content.pipe;

import com.simibubi.create.content.fluids.FluidPropagator;
import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlock;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlockEntity;

import com.simibubi.create.content.fluids.pipes.GlassFluidPipeBlock;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.advancement.AllAdvancements;

import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPBlocks;

import javax.annotation.Nullable;

public class ColorfulFluidPipeBlock extends FluidPipeBlock {

	protected final DyeColor color;

	public ColorfulFluidPipeBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public InteractionResult onWrenched(BlockState state, UseOnContext context) {
		if (tryRemoveBracket(context))
			return InteractionResult.SUCCESS;

		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Direction clickedFace = context.getClickedFace();

		Direction.Axis axis = getAxis(world, pos, state);
		if (axis == null) {
			Vec3 clickLocation = context.getClickLocation()
					.subtract(pos.getX(), pos.getY(), pos.getZ());
			double closest = Float.MAX_VALUE;
			Direction argClosest = Direction.UP;
			for (Direction direction : Iterate.directions) {
				if (clickedFace.getAxis() == direction.getAxis())
					continue;
				Vec3 centerOf = Vec3.atCenterOf(direction.getNormal());
				double distance = centerOf.distanceToSqr(clickLocation);
				if (distance < closest) {
					closest = distance;
					argClosest = direction;
				}
			}
			axis = argClosest.getAxis();
		}

		if (clickedFace.getAxis() == axis)
			return InteractionResult.PASS;
		if (!world.isClientSide) {
			withBlockEntityDo(world, pos, fpte -> fpte.getBehaviour(FluidTransportBehaviour.TYPE).interfaces.values()
					.stream()
					.filter(pc -> pc != null && pc.hasFlow())
					.findAny()
					.ifPresent($ -> AllAdvancements.GLASS_PIPE.awardTo(context.getPlayer())));

			FluidTransportBehaviour.cacheFlows(world, pos);
			world.setBlockAndUpdate(pos, CCPBlocks.COLORFUL_GLASS_FLUID_PIPES.get(color).getDefaultState()
					.setValue(GlassFluidPipeBlock.AXIS, axis)
					.setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED)));
			FluidTransportBehaviour.loadFlows(world, pos);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
		AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
	}


	@Nullable
	private Direction.Axis getAxis(BlockGetter world, BlockPos pos, BlockState state) {
		return FluidPropagator.getStraightPipeAxis(state);
	}

	public static boolean isColorfulPipe(BlockState state) {
		return state.getBlock() instanceof ColorfulFluidPipeBlock;
	}

	public static boolean shouldDrawCasing(BlockAndTintGetter world, BlockPos pos, BlockState state) {
		if (!isColorfulPipe(state))
			return false;
		for (Direction.Axis axis : Iterate.axes) {
			int connections = 0;
			for (Direction direction : Iterate.directions)
				if (direction.getAxis() != axis && isOpenAt(state, direction))
					connections++;
			if (connections > 2)
				return true;
		}
		return false;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor != color) {
				level.setBlock(pos, CCPBlocks.COLORFUL_FLUID_PIPES.get(dyeColor).getDefaultState(), 3);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.use(state, level, pos, player, hand, hit);
	}

	public BlockEntityType<? extends FluidPipeBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_FLUID_PIPES.get();
	}
}
