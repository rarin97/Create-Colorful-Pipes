package net.rarin.colorfulpipes.content.pump;

import com.simibubi.create.content.fluids.pump.PumpBlock;
import com.simibubi.create.content.fluids.pump.PumpBlockEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulPumpBlock extends PumpBlock {

	protected final DyeColor color;

	public ColorfulPumpBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public BlockEntityType<? extends PumpBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_PUMPS.get();
	}
}
