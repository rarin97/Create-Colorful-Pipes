package net.rarin.colorfulpipes.compat.CreateDragonsPlus.content;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.compat.CreateDragonsPlus.CDPBlockEntityTypes;
import plus.dragons.createdragonsplus.common.fluids.hatch.FluidHatchBlock;
import plus.dragons.createdragonsplus.common.fluids.hatch.FluidHatchBlockEntity;

public class ColorfulFluidHatchBlock extends FluidHatchBlock {

	protected final DyeColor color;

	public ColorfulFluidHatchBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public BlockEntityType<? extends FluidHatchBlockEntity> getBlockEntityType() {
		return CDPBlockEntityTypes.COLORFUL_FLUID_HATCHES.get();
	}
}
