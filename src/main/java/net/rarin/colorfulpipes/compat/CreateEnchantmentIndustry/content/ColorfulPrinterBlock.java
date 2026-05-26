package net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.CEIBlockEntityTypes;
import plus.dragons.createenchantmentindustry.common.fluids.printer.PrinterBlock;
import plus.dragons.createenchantmentindustry.common.fluids.printer.PrinterBlockEntity;

public class ColorfulPrinterBlock extends PrinterBlock {

	protected final DyeColor color;

	public ColorfulPrinterBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	public DyeColor getColor() {
		return color;
	}

	@Override
	public BlockEntityType<? extends PrinterBlockEntity> getBlockEntityType() {
		return CEIBlockEntityTypes.COLORFUL_PRINTER.get();
	}
}
