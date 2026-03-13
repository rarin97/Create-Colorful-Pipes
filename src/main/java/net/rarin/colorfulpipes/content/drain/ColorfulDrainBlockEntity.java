package net.rarin.colorfulpipes.content.drain;

import com.simibubi.create.content.fluids.drain.ItemDrainBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulDrainBlockEntity extends ItemDrainBlockEntity {
	public ColorfulDrainBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public SmartFluidTankBehaviour getTank() {
		return getBehaviour(SmartFluidTankBehaviour.TYPE);
	}

	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(
				Capabilities.ItemHandler.BLOCK,
				CCPBlockEntityTypes.COLORFUL_DRAINS.get(),
				(be, context) -> {
					if (context != null && context.getAxis().isHorizontal())
						return new ColorfulDrainItemHandler(be, context);
					return null;
				}
		);

		event.registerBlockEntity(
				Capabilities.FluidHandler.BLOCK,
				CCPBlockEntityTypes.COLORFUL_DRAINS.get(),
				(be, context) -> {
					if (context != Direction.UP)
						return be.getTank().getCapability();
					return null;
				}
		);
	}
}
