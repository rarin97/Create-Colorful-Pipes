package net.rarin.colorfulpipes.content.encasedshaft;

import java.util.function.Supplier;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPPaletteBlocks;

public class ColorfulEncasedShaftBlock extends EncasedShaftBlock {

	protected final DyeColor color;

	public ColorfulEncasedShaftBlock(Properties properties, DyeColor color, Supplier<Block> casing) {
		super(properties, casing);
		this.color = color;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color)
				return InteractionResult.PASS;

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));

				level.setBlock(pos, CCPPaletteBlocks.COLORFUL_COPPER_ENCASED_SHAFT.get(dyeColor).getDefaultState()
						.setValue(AXIS, state.getValue(AXIS)), 3);
			}
			return InteractionResult.SUCCESS;
		}

		return super.use(state, level, pos, player, hand, hit);
	}

	@Override
	public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.ENCASED_SHAFT.get();
	}
}
