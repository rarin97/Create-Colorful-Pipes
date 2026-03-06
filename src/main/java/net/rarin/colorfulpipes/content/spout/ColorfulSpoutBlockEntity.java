package net.rarin.colorfulpipes.content.spout;

import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ColorfulSpoutBlockEntity extends SpoutBlockEntity {
	private SmartFluidTankBehaviour tank;

	public ColorfulSpoutBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public SmartFluidTankBehaviour Tank() {
		return this.tank;
	}
}
