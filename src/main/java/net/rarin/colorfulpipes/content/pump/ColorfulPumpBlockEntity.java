package net.rarin.colorfulpipes.content.pump;

import com.simibubi.create.content.fluids.FluidPropagator;
import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.content.fluids.PipeAttachmentBlockEntity;
import com.simibubi.create.content.fluids.PipeConnection;
import com.simibubi.create.content.fluids.pump.PumpBlockEntity;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.createmod.catnip.data.Couple;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.List;
import java.util.Map;

public class ColorfulPumpBlockEntity extends PumpBlockEntity implements PipeAttachmentBlockEntity {
	public ColorfulPumpBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		super.addBehaviours(behaviours);
		behaviours.add(new ColorfulPumpFluidTransferBehaviour(this));
		registerAwardables(behaviours, FluidPropagator.getSharedTriggers());
		registerAwardables(behaviours, AllAdvancements.PUMP);
	}

	class ColorfulPumpFluidTransferBehaviour extends FluidTransportBehaviour {
		public ColorfulPumpFluidTransferBehaviour(SmartBlockEntity be) {
			super(be);
		}

		@Override
		public boolean canHaveFlowToward(BlockState state, Direction direction) {
			return isSideAccessible(direction);
		}

		@Override
		public void tick() {
			super.tick();
			for (Map.Entry<Direction, PipeConnection> entry : interfaces.entrySet()) {
				boolean pull = isPullingOnSide(isFront(entry.getKey()));
				Couple<Float> pressure = entry.getValue().getPressure();
				pressure.set(pull, Math.abs(getSpeed()));
				pressure.set(!pull, 0f);
			}
		}

		@Override
		public AttachmentTypes getRenderedRimAttachment(BlockAndTintGetter world, BlockPos pos, BlockState state,
														Direction direction) {
			AttachmentTypes attachment = super.getRenderedRimAttachment(world, pos, state, direction);
			if (attachment == AttachmentTypes.RIM)
				return AttachmentTypes.NONE;
			return attachment;
		}
	}


}
