package net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.CEIBlockEntityTypes;
import plus.dragons.createenchantmentindustry.common.fluids.lantern.ExperienceLanternBlock;
import plus.dragons.createenchantmentindustry.common.fluids.lantern.ExperienceLanternBlockEntity;

public class ColorfulExperienceLanternBlock extends ExperienceLanternBlock {

	protected final DyeColor color;

	public ColorfulExperienceLanternBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public BlockEntityType<? extends ExperienceLanternBlockEntity> getBlockEntityType() {
		return CEIBlockEntityTypes.COLORFUL_EXPERIENCE_LANTERN.get();
	}
}
