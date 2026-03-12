package net.rarin.colorfulpipes.content.spout;

import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulSpoutBlockEntity extends SpoutBlockEntity {
	private SmartFluidTankBehaviour tank;

	public ColorfulSpoutBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public SmartFluidTankBehaviour getTank() {
		return getBehaviour(SmartFluidTankBehaviour.TYPE);
	}

	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(
				Capabilities.FluidHandler.BLOCK,
				CCPBlockEntityTypes.COLORFUL_SPOUTS.get(),
				(be, context) -> {
					if (context != Direction.DOWN)
						return be.getTank().getCapability();
					return null;
				}
		);
	}
}
