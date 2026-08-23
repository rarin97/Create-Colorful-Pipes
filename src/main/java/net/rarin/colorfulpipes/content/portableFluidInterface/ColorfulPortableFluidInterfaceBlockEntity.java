package net.rarin.colorfulpipes.content.portableFluidInterface;

import com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorage;
import com.simibubi.create.content.contraptions.actors.psi.PortableFluidInterfaceBlockEntity;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import com.simibubi.create.content.contraptions.Contraption;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.config.CCPConfigs;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankBlock;

import java.util.ArrayList;
import java.util.List;

public class ColorfulPortableFluidInterfaceBlockEntity extends PortableFluidInterfaceBlockEntity {
	protected IFluidHandler capability;

	public ColorfulPortableFluidInterfaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		capability = createEmptyHandler();
	}

	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(
				Capabilities.FluidHandler.BLOCK,
				CCPBlockEntityTypes.COLORFUL_PORTABLE_FLUID_INTERFACE.get(),
				(be, context) -> be.capability
		);
	}

	@Override
	public void startTransferringTo(Contraption contraption, float distance) {
		List<MountedFluidStorage> tanks = new ArrayList<>();
		DyeColor color = ((ColorfulPortableFluidInterfaceBlock) getBlockState().getBlock()).getColor();
		contraption.getStorage().getFluids().storages.forEach((pos, storage) -> {

			if (contraption.getBlocks().get(pos).state().getBlock() instanceof ColorfulFluidTankBlock tank) {
				if (CCPConfigs.server().fluids.MixedPSIConnections.get() && tank.getColor() != color)
					return;

				tanks.add(storage);
			}
			if(CCPConfigs.server().fluids.PSIConnections.get()) {
				tanks.add(storage);
			}
		});

		capability = tanks.isEmpty() ? createEmptyHandler() : new InterfaceFluidHandler(tanks);

		invalidateCapability();
		super.startTransferringTo(contraption, distance);
	}

	@Override
	protected void invalidateCapability() {
		invalidateCapabilities();
	}

	@Override
	protected void stopTransferring() {
		capability = createEmptyHandler();
		invalidateCapability();
		super.stopTransferring();
	}

	boolean isConnected() {
		int timeUnit = getTransferTimeout();
		return transferTimer >= ANIMATION && transferTimer <= timeUnit + ANIMATION;
	}

	float getExtensionDistance(float partialTicks) {
		return (float) (Math.pow(connectionAnimation.getValue(partialTicks), 2) * distance / 2);
	}

	private IFluidHandler createEmptyHandler() {
		return new FluidTank(0);
	}

	public class InterfaceFluidHandler implements IFluidHandler {

		private final List<MountedFluidStorage> handlers;

		public InterfaceFluidHandler(List<MountedFluidStorage> handlers) {
			this.handlers = handlers;
		}

		@Override
		public int getTanks() {
			return handlers.size();
		}

		@Override
		public FluidStack getFluidInTank(int tank) {
			return handlers.get(tank).getFluidInTank(0);
		}

		@Override
		public int getTankCapacity(int tank) {
			return handlers.get(tank).getTankCapacity(0);
		}

		@Override
		public boolean isFluidValid(int tank, FluidStack stack) {
			return handlers.get(tank).isFluidValid(0, stack);
		}

		@Override
		public int fill(FluidStack resource, FluidAction action) {
			if (!isConnected())
				return 0;
			int fill = 0;
			for (IFluidHandler handler : handlers) {
				int amount = handler.fill(resource, action);
				fill += amount;
				if (amount > 0 && action.execute())
					onContentTransferred();
				if (fill >= resource.getAmount())
					break;
			}
			return fill;
		}

		@Override
		public FluidStack drain(FluidStack resource, FluidAction action) {
			if (!canTransfer())
				return FluidStack.EMPTY;

			for (IFluidHandler handler : handlers) {
				FluidStack drained = handler.drain(resource, action);
				if (!drained.isEmpty()) {
					if (action.execute())
						onContentTransferred();
					return drained;
				}
			}
			return FluidStack.EMPTY;
		}

		@Override
		public FluidStack drain(int maxDrain, FluidAction action) {
			if (!canTransfer())
				return FluidStack.EMPTY;

			for (IFluidHandler handler : handlers) {
				FluidStack drained = handler.drain(maxDrain, action);
				if (!drained.isEmpty()) {
					if (action.execute())
						onContentTransferred();
					return drained;
				}
			}
			return FluidStack.EMPTY;
		}
	}
}
