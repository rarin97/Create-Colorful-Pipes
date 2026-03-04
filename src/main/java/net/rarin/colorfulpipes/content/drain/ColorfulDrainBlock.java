package net.rarin.colorfulpipes.content.drain;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.drain.ItemDrainBlock;
import com.simibubi.create.content.fluids.drain.ItemDrainBlockEntity;
import com.simibubi.create.content.kinetics.crank.ValveHandleBlockEntity;
import com.simibubi.create.foundation.utility.BlockHelper;

import io.github.fabricators_of_create.porting_lib.util.TagUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPBlocks;

public class ColorfulDrainBlock extends ItemDrainBlock {

	protected final DyeColor color;

	public ColorfulDrainBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	public boolean clicked(Level level, BlockPos pos, BlockState blockState, Player player, InteractionHand hand) {
		ItemStack heldItem = player.getItemInHand(hand);
		DyeColor color = TagUtil.getColorFromStack(heldItem);
		if (color != null && color != this.color) {
			if (!level.isClientSide)
				level.setBlockAndUpdate(pos,
						BlockHelper.copyProperties(blockState, CCPBlocks.COLORFUL_DRAINS.get(color).getDefaultState()));
			return true;
		}
		return false;
	}

	public BlockEntityType<? extends ItemDrainBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_DRAINS.get();
	}
}

