package net.rarin.colorfulpipes.content.hosePulley;

import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlockEntity;
import com.simibubi.create.content.fluids.hosePulley.HosePulleyRenderer;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.DyeColor;
import net.rarin.colorfulpipes.CCPPartialModels;

public class ColorfulHosePulleyRenderer extends HosePulleyRenderer {
	public ColorfulHosePulleyRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected SuperByteBuffer renderMagnet(HosePulleyBlockEntity be) {
		ColorfulHosePulleyBlock block = (ColorfulHosePulleyBlock) be.getBlockState().getBlock();
		DyeColor color = block.getColor();
		return CachedBuffers.partial(CCPPartialModels.COLORFUL_HOSE_MAGNET.get(color), be.getBlockState());
	}

}
