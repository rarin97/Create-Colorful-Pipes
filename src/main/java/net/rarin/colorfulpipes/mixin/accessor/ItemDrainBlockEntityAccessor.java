package net.rarin.colorfulpipes.mixin.accessor;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.simibubi.create.content.fluids.drain.ItemDrainBlockEntity;
import com.simibubi.create.content.fluids.drain.ItemDrainItemHandler;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;

import net.minecraft.core.Direction;

@Mixin(value = ItemDrainBlockEntity.class,remap = false)
public interface ItemDrainBlockEntityAccessor {

    @Accessor("itemHandlers")
    Map<Direction, ItemDrainItemHandler> getItemHandlers();

    @Accessor("internalTank")
    SmartFluidTankBehaviour getInternalTank();

}
