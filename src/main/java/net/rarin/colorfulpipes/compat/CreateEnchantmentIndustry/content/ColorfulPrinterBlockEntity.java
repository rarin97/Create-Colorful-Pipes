package net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content;

import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import plus.dragons.createenchantmentindustry.common.fluids.printer.PrinterBlockEntity;

public class ColorfulPrinterBlockEntity extends PrinterBlockEntity {
	protected SmartFluidTankBehaviour tank;

	public ColorfulPrinterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public SmartFluidTankBehaviour getTank() {
		return getBehaviour(SmartFluidTankBehaviour.TYPE);
	}
}
