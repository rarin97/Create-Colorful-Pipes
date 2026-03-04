package net.rarin.colorfulpipes.content.spout;

import com.simibubi.create.content.fluids.spout.SpoutBlock;
import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulSpoutBlock extends SpoutBlock {

	protected final DyeColor color;

	public ColorfulSpoutBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	public BlockEntityType<? extends SpoutBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_SPOUTS.get();
	}
}
