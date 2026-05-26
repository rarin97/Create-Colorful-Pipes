package net.rarin.colorfulpipes.compat.jade;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum CreateConnectedComponentProvider implements IBlockComponentProvider {
	INSTANCE;

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
		tooltip.add(Component.literal("Create: Connected")
				.withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));
	}

	@Override
	public ResourceLocation getUid() {
		return ResourceLocation.fromNamespaceAndPath("create_connected", "create_connected_tooltip");
	}
}
