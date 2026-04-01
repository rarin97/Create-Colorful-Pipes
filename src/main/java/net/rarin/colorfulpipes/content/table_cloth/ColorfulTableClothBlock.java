package net.rarin.colorfulpipes.content.table_cloth;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlock;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulTableClothBlock extends TableClothBlock {

	protected final DyeColor color;

	public ColorfulTableClothBlock(Properties properties, String type, DyeColor color) {
		super(properties, type);
		this.color = color;
	}

	public DyeColor getColor() {
		return color;
	}

	@Override
	public BlockEntityType<? extends TableClothBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_TABLE_CLOTH.get();
	}
}
