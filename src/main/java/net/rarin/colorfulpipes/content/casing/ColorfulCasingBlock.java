package net.rarin.colorfulpipes.content.casing;

import com.simibubi.create.content.decoration.encasing.CasingBlock;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

public class ColorfulCasingBlock extends CasingBlock {

	protected final DyeColor color;

	public ColorfulCasingBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public InteractionResult onWrenched(BlockState state, UseOnContext context) {
		return InteractionResult.FAIL;
	}

}
