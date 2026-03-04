package net.rarin.colorfulpipes.content.smartPipe;

import com.simibubi.create.content.fluids.pipes.SmartFluidPipeBlock;
import com.simibubi.create.content.fluids.pipes.SmartFluidPipeBlockEntity;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulSmartFluidPipeBlock extends SmartFluidPipeBlock {

	protected final DyeColor color;

	public ColorfulSmartFluidPipeBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	public BlockEntityType<? extends SmartFluidPipeBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_SMART_FLUID_PIPES.get();
	}
}
