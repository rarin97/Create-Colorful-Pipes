package net.rarin.colorfulpipes.content.valve;

import com.simibubi.create.content.fluids.pipes.valve.FluidValveBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ColorfulFluidValveBlockEntity extends FluidValveBlockEntity {
	public ColorfulFluidValveBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}
}
