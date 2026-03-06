package net.rarin.colorfulpipes.content.encasedPipe;

import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.content.fluids.pipes.EncasedPipeBlock;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlockEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import net.rarin.colorfulpipes.CCPBlocks;

import java.util.function.Supplier;

public class ColorfulEncasedPipeBlock extends EncasedPipeBlock {

	protected final DyeColor color;

	public ColorfulEncasedPipeBlock(Properties properties, DyeColor color, Supplier<Block> casing) {
		super(properties, casing);
		this.color = color;
	}

	@Override
	public InteractionResult onWrenched(BlockState state, UseOnContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();

		if (world.isClientSide)
			return InteractionResult.SUCCESS;

		context.getLevel()
				.levelEvent(2001, context.getClickedPos(), Block.getId(state));
		BlockState equivalentPipe = transferSixWayProperties(state, CCPBlocks.COLORFUL_FLUID_PIPES.get(color).getDefaultState());

		Direction firstFound = Direction.UP;
		for (Direction d : Iterate.directions)
			if (state.getValue(FACING_TO_PROPERTY_MAP.get(d))) {
				firstFound = d;
				break;
			}

		FluidTransportBehaviour.cacheFlows(world, pos);
		world.setBlockAndUpdate(pos, CCPBlocks.COLORFUL_FLUID_PIPES.get(color).get()
				.updateBlockState(equivalentPipe, firstFound, null, world, pos));
		FluidTransportBehaviour.loadFlows(world, pos);
		return InteractionResult.SUCCESS;
	}

	@Override
	public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
		AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
	}

//	@Override
//	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//		ItemStack stack = player.getItemInHand(hand);
//
//		if (stack.getItem() instanceof DyeItem dye) {
//			DyeColor dyeColor = dye.getDyeColor();
//
//			if (dyeColor != color) {
//				level.setBlock(pos, CCPBlocks.COLORFUL_ENCASED_FLUID_PIPES.get(dyeColor).getDefaultState(), 3);
//			}
//			return InteractionResult.SUCCESS;
//		}
//		return super.use(state, level, pos, player, hand, hit);
//	}

	public BlockEntityType<? extends FluidPipeBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_ENCASED_FLUID_PIPES.get();
	}
}
