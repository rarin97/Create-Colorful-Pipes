package net.rarin.colorfulpipes.compat.CreateDragonsPlus;

import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import plus.dragons.createdragonsplus.common.fluids.hatch.FluidHatchBlockEntity;

import static net.rarin.colorfulpipes.ColorfulPipes.REGISTRATE;

public class CDPBlockEntityTypes {

	public static final BlockEntityEntry<FluidHatchBlockEntity> COLORFUL_FLUID_HATCHES = REGISTRATE
			.blockEntity("fluid_hatch", FluidHatchBlockEntity::new)
			.validBlocks(CDPBlocks.COLORFUL_FLUID_HATCHES.toArray())
			.renderer(() -> SmartBlockEntityRenderer::new)
			.register();

	public static void register() {
	}
}
