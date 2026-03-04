package net.rarin.colorfulpipes.content.tank;

import com.simibubi.create.content.fluids.tank.FluidTankRenderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.DyeColor;

public class ColorfulFluidTankRenderer extends FluidTankRenderer {

	protected final DyeColor color;

	public ColorfulFluidTankRenderer(BlockEntityRendererProvider.Context context, DyeColor color) {
		super(context);
		this.color = color;
	}
}
