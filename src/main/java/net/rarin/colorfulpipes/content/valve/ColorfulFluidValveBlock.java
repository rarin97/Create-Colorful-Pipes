package net.rarin.colorfulpipes.content.valve;

import com.simibubi.create.content.fluids.pipes.valve.FluidValveBlock;
import com.simibubi.create.content.fluids.pipes.valve.FluidValveBlockEntity;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulFluidValveBlock extends FluidValveBlock {

	protected final DyeColor color;

	public ColorfulFluidValveBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public BlockEntityType<? extends FluidValveBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_FLUID_VALVES.get();
	}
}
