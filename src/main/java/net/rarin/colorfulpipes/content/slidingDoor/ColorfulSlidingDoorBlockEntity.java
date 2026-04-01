package net.rarin.colorfulpipes.content.slidingDoor;

import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ColorfulSlidingDoorBlockEntity extends SlidingDoorBlockEntity {
	public ColorfulSlidingDoorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
}
