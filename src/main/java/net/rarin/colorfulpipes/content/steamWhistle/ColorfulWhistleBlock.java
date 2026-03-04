package net.rarin.colorfulpipes.content.steamWhistle;

import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlockEntity;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulWhistleBlock extends WhistleBlock {

	protected final DyeColor color;

	public ColorfulWhistleBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public BlockEntityType<? extends WhistleBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_STEAM_WHISTLES.get();
	}

	public DyeColor getColor() {
		return color;
	}
}
