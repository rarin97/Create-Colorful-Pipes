package net.rarin.colorfulpipes.content.tank;

import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulFluidTankBlock extends FluidTankBlock {

	protected final DyeColor color;

	public ColorfulFluidTankBlock(Properties properties, DyeColor color, boolean creative) {
		super(properties, creative);
		this.color = color;
	}

	public BlockEntityType<? extends FluidTankBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_FLUID_TANKS.get();
	}
}
