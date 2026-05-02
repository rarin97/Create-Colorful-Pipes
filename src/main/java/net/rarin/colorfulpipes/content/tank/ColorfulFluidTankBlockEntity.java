package net.rarin.colorfulpipes.content.tank;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulFluidTankBlockEntity extends FluidTankBlockEntity {
	public ColorfulFluidTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		for (DyeColor color : DyeColor.values()) {
			event.registerBlockEntity(
					Capabilities.FluidHandler.BLOCK,
					CCPBlockEntityTypes.COLORFUL_FLUID_TANKS.get(color).get(),
					(be, context) -> {
						if (be.fluidCapability == null)
							be.refreshCapability();
						return be.fluidCapability;
					}
			);
		}
	}

	void refreshCapability() {
		invalidateCapabilities();
		fluidCapability = handlerForCapability();
	}

	private IFluidHandler handlerForCapability() {
		if(isController()){
			return boiler.isActive() ? boiler.createHandler() : tankInventory;
		}else{
			return getControllerBE() != null ? ((ColorfulFluidTankBlockEntity)getControllerBE()).handlerForCapability() : new FluidTank(0);
		}
	}

	@Override
	protected void updateConnectivity() {
		updateConnectivity = false;
		if (level.isClientSide)
			return;
		if (!isController())
			return;
		ConnectivityHandler.formMulti(this);
	}
}
