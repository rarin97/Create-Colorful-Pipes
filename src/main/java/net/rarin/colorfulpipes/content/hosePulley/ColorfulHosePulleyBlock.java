package net.rarin.colorfulpipes.content.hosePulley;

import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlock;
import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlockEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulHosePulleyBlock extends HosePulleyBlock {

	protected final DyeColor color;

	public ColorfulHosePulleyBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public BlockEntityType<? extends HosePulleyBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_HOSE_PULLEYS.get();
	}
}
