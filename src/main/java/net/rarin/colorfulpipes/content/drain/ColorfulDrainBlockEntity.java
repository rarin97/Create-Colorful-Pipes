package net.rarin.colorfulpipes.content.drain;

import com.simibubi.create.content.fluids.drain.ItemDrainBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ColorfulDrainBlockEntity extends ItemDrainBlockEntity {
	public ColorfulDrainBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
}
