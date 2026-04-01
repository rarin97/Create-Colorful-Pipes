package net.rarin.colorfulpipes.content.hosePulley;

import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ColorfulHosePulleyBlockEntity extends HosePulleyBlockEntity {

	public ColorfulHosePulleyBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}

	public SmartFluidTankBehaviour getTank() {
		return getBehaviour(SmartFluidTankBehaviour.TYPE);
	}

//	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
//		event.registerBlockEntity(
//				Capabilities.FluidHandler.BLOCK,
//				CCPBlockEntityTypes.COLORFUL_HOSE_PULLEYS.get(),
//				(be, context) -> {
//					if (be.level == null)
//						return null;
//
//					if (context == null || HosePulleyBlock.hasPipeTowards(be.level, be.worldPosition, be.getBlockState(), context))
//						return be.getCapability();
//
//					return null;
//				}
//		);
//	}
}
