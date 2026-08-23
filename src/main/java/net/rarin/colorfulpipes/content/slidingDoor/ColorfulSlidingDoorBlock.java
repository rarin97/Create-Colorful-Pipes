package net.rarin.colorfulpipes.content.slidingDoor;

import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorBlock;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorBlockEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulSlidingDoorBlock extends SlidingDoorBlock {

	protected final DyeColor color;

	public static ColorfulSlidingDoorBlock stone(Properties properties, boolean folds, DyeColor color) {
		return new ColorfulSlidingDoorBlock(properties, STONE_SET_TYPE.get(), folds, color);
	}

	public ColorfulSlidingDoorBlock(Properties properties, BlockSetType type, boolean folds, DyeColor color) {
		super(properties, type, folds);
		this.color = color;
	}

	public DyeColor getColor() {
		return color;
	}

	@Override
	public BlockEntityType<? extends SlidingDoorBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_SLIDING_DOOR.get();
	}
}

