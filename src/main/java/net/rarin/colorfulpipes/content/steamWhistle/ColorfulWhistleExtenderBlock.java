package net.rarin.colorfulpipes.content.steamWhistle;

import com.simibubi.create.content.decoration.steamWhistle.WhistleExtenderBlock;
import net.minecraft.world.item.DyeColor;

public class ColorfulWhistleExtenderBlock extends WhistleExtenderBlock {

	protected final DyeColor color;

	public ColorfulWhistleExtenderBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}
}
