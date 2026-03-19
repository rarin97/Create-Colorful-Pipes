package net.rarin.colorfulpipes.content.steamWhistle;

import com.simibubi.create.content.decoration.steamWhistle.WhistleBlockEntity;
import com.simibubi.create.content.decoration.steamWhistle.WhistleExtenderBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ColorfulWhistleBlockEntity extends WhistleBlockEntity {
	public ColorfulWhistleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public void updatePitch() {
		BlockPos currentPos = worldPosition.above();
		int newPitch;
		for (newPitch = 0; newPitch <= 24; newPitch += 2) {
			assert level != null;
			BlockState blockState = level.getBlockState(currentPos);
			if (!(blockState.getBlock() instanceof WhistleExtenderBlock))
				break;
			if (blockState.getValue(WhistleExtenderBlock.SHAPE) == WhistleExtenderBlock.WhistleExtenderShape.SINGLE) {
				newPitch++;
				break;
			}
			currentPos = currentPos.above();
		}
		if (pitch == newPitch)
			return;
		pitch = newPitch;

		notifyUpdate();

		FluidTankBlockEntity tank = getTank();
		if (tank != null && tank.boiler != null)
			tank.boiler.checkPipeOrganAdvancement(tank);
	}

	@Override
	public void tick() {
		super.tick();
		updatePitch();
	}
}
