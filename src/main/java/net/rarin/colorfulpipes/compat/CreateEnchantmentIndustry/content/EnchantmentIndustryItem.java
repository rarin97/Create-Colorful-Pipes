package net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class EnchantmentIndustryItem extends BlockItem {
	public EnchantmentIndustryItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.literal("Create: Enchantment Industry")
				.withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));

		super.appendHoverText(stack, context, tooltip, flag);
	}
}
