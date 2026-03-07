package net.rarin.colorfulpipes.content.hosePulley;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.contraptions.pulley.HosePulleyVisual;
import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlockEntity;
import dev.engine_room.flywheel.api.instance.Instancer;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.world.item.DyeColor;
import net.rarin.colorfulpipes.CCPPartialModels;

public class ColorfulHosePulleyVisual extends HosePulleyVisual {
	public ColorfulHosePulleyVisual(VisualizationContext dispatcher, HosePulleyBlockEntity blockEntity, float partialTick) {
		super(dispatcher, blockEntity, partialTick);
	}

	@Override
	protected Instancer<TransformedInstance> getMagnetModel() {

		if (!(blockEntity.getBlockState().getBlock() instanceof ColorfulHosePulleyBlock block))
			return super.getMagnetModel();

		DyeColor color = block.getColor();

		return instancerProvider().instancer(InstanceTypes.TRANSFORMED,
				Models.partial(CCPPartialModels.COLORFUL_HOSE_MAGNET.get(color)));
	}

	@Override
	protected Instancer<TransformedInstance> getHalfMagnetModel() {

		if (!(blockEntity.getBlockState().getBlock() instanceof ColorfulHosePulleyBlock block))
			return super.getHalfMagnetModel();

		DyeColor color = block.getColor();

		return instancerProvider().instancer(InstanceTypes.TRANSFORMED,
				Models.partial(CCPPartialModels.COLORFUL_HOSE_HALF_MAGNET.get(color)));
	}

}
