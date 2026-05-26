package net.rarin.colorfulpipes.compat.jade;

import net.rarin.colorfulpipes.compat.CreateDragonsPlus.content.ColorfulFluidHatchBlock;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content.ColorfulExperienceHatchBlock;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content.ColorfulExperienceLanternBlock;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content.ColorfulGrindstoneDrainBlock;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content.ColorfulPrinterBlock;
import net.rarin.colorfulpipes.compat.Create_Connected.content.ColorfulFluidVesselBlock;
import net.rarin.colorfulpipes.compat.Mods;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		if (Mods.CREATE_CONNECTED.isLoaded()) {
			registration.registerBlockComponent(CreateConnectedComponentProvider.INSTANCE, ColorfulFluidVesselBlock.class);
		}
		if (Mods.CREATE_DRAGONS_PLUS.isLoaded()) {
			registration.registerBlockComponent(DragonsPlusComponentProvider.INSTANCE, ColorfulFluidHatchBlock.class);
		}
		if (Mods.CREATE_ENCHANTMENT_INDUSTRY.isLoaded()) {
			registration.registerBlockComponent(EnchantmentIndustryComponentProvider.INSTANCE, ColorfulExperienceHatchBlock.class);
			registration.registerBlockComponent(EnchantmentIndustryComponentProvider.INSTANCE, ColorfulExperienceLanternBlock.class);
			registration.registerBlockComponent(EnchantmentIndustryComponentProvider.INSTANCE, ColorfulPrinterBlock.class);
			registration.registerBlockComponent(EnchantmentIndustryComponentProvider.INSTANCE, ColorfulGrindstoneDrainBlock.class);
		}
	}
}
