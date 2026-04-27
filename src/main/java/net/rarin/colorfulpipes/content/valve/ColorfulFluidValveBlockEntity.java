package net.rarin.colorfulpipes.content.valve;

import com.simibubi.create.content.fluids.FluidPropagator;
import com.simibubi.create.content.fluids.PipeAttachmentBlockEntity;
import com.simibubi.create.content.fluids.pipes.StraightPipeBlockEntity;
import com.simibubi.create.content.fluids.pipes.valve.FluidValveBlock;
import com.simibubi.create.content.fluids.pipes.valve.FluidValveBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ColorfulFluidValveBlockEntity extends FluidValveBlockEntity {

	LerpedFloat pointer;

	public ColorfulFluidValveBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
		pointer = LerpedFloat.linear()
				.startWithValue(0)
				.chase(0, 0, LerpedFloat.Chaser.LINEAR);
	}

	@Override
	public void onSpeedChanged(float previousSpeed) {
		super.onSpeedChanged(previousSpeed);
		float speed = getSpeed();
		pointer.chase(speed > 0 ? 1 : 0, getChaseSpeed(), LerpedFloat.Chaser.LINEAR);
		sendData();
	}

	@Override
	public void tick() {
		super.tick();
		pointer.tickChaser();

		if (level.isClientSide)
			return;

		BlockState blockState = getBlockState();
		if (!(blockState.getBlock() instanceof FluidValveBlock))
			return;
		boolean stateOpen = blockState.getValue(FluidValveBlock.ENABLED);

		if (stateOpen && pointer.getValue() == 0) {
			switchToBlockState(level, worldPosition, blockState.setValue(FluidValveBlock.ENABLED, false));
			return;
		}
		if (!stateOpen && pointer.getValue() == 1) {
			switchToBlockState(level, worldPosition, blockState.setValue(FluidValveBlock.ENABLED, true));
			return;
		}
	}

	private float getChaseSpeed() {
		return Mth.clamp(Math.abs(getSpeed()) / 16 / 20, 0, 1);
	}

	@Override
	protected void write(CompoundTag compound, boolean clientPacket) {
		super.write(compound, clientPacket);
		compound.put("Pointer", pointer.writeNBT());
	}

	@Override
	protected void read(CompoundTag compound, boolean clientPacket) {
		super.read(compound, clientPacket);
		pointer.readNBT(compound.getCompound("Pointer"), clientPacket);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		behaviours.add(new ColorfulFluidValveBlockEntity.ValvePipeBehaviour(this));
		registerAwardables(behaviours, FluidPropagator.getSharedTriggers());
	}

	@Override
	@Nullable
	public Object getRenderData() {
		return PipeAttachmentBlockEntity.getAttachments(this);
	}

	class ValvePipeBehaviour extends StraightPipeBlockEntity.StraightPipeFluidTransportBehaviour {

		public ValvePipeBehaviour(SmartBlockEntity be) {
			super(be);
		}

		@Override
		public boolean canHaveFlowToward(BlockState state, Direction direction) {
			return FluidValveBlock.getPipeAxis(state) == direction.getAxis();
		}

		@Override
		public boolean canPullFluidFrom(FluidStack fluid, BlockState state, Direction direction) {
			if (state.hasProperty(FluidValveBlock.ENABLED) && state.getValue(FluidValveBlock.ENABLED))
				return super.canPullFluidFrom(fluid, state, direction);
			return false;
		}

	}

}
