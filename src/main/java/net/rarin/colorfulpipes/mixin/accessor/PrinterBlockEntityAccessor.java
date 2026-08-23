package net.rarin.colorfulpipes.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import plus.dragons.createenchantmentindustry.common.fluids.printer.PrinterBlockEntity;

@Mixin(value = PrinterBlockEntity.class,remap = false)
public interface PrinterBlockEntityAccessor {

    @Accessor("tank")
    SmartFluidTankBehaviour getTank();

}
