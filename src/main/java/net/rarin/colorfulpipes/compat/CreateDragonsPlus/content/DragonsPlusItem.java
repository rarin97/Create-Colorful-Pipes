package net.rarin.colorfulpipes.compat.CreateDragonsPlus.content;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class DragonsPlusItem extends BlockItem {
	public DragonsPlusItem(Block block, Properties properties) {
		super(block, properties);
	}
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.literal("Create: Dragons Plus")
				.withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));

		super.appendHoverText(stack, context, tooltip, flag);
	}
}
