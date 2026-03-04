package net.rarin.colorfulpipes.content.drain;

import com.simibubi.create.content.fluids.drain.ItemDrainBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ColorfulDrainBlockEntity extends ItemDrainBlockEntity {

//	@Override
//	public void onShortInteract(Player player, InteractionHand hand, Direction side, BlockHitResult hitResult) {
//		if (getWorld().isClientSide)
//			return;
//		BlockState blockState = blockEntity.getBlockState();
//		if (blockState.getBlock() instanceof ColorfulDrainBlock cdb)
//			cdb.clicked(getWorld(), getPos(), blockState, player, hand);
//	}

	public ColorfulDrainBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
}
