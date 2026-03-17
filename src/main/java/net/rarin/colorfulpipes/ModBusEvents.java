package net.rarin.colorfulpipes;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.rarin.colorfulpipes.content.drain.ColorfulDrainBlockEntity;
import net.rarin.colorfulpipes.content.hosePulley.ColorfulHosePulleyBlockEntity;
import net.rarin.colorfulpipes.content.portableFluidInterface.ColorfulPortableFluidInterfaceBlockEntity;
import net.rarin.colorfulpipes.content.spout.ColorfulSpoutBlockEntity;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankBlockEntity;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModBusEvents {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        ColorfulFluidTankBlockEntity.registerCapabilities(event);
        ColorfulDrainBlockEntity.registerCapabilities(event);
        ColorfulHosePulleyBlockEntity.registerCapabilities(event);
        ColorfulSpoutBlockEntity.registerCapabilities(event);
		ColorfulPortableFluidInterfaceBlockEntity.registerCapabilities(event);
    }
}
