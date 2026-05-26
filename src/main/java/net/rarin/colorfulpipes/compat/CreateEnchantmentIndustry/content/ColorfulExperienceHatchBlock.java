package net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.CEIBlockEntityTypes;
import plus.dragons.createenchantmentindustry.common.fluids.experience.ExperienceHatchBlock;
import plus.dragons.createenchantmentindustry.common.fluids.experience.ExperienceHatchBlockEntity;


public class ColorfulExperienceHatchBlock extends ExperienceHatchBlock {

	protected final DyeColor color;

	public ColorfulExperienceHatchBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public BlockEntityType<? extends ExperienceHatchBlockEntity> getBlockEntityType() {
		return CEIBlockEntityTypes.COLORFUL_EXPERIENCE_HATCHES.get();
	}
}
