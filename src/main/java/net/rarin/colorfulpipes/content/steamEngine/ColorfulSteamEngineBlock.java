package net.rarin.colorfulpipes.content.steamEngine;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlockEntity;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;
import java.util.function.Predicate;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class ColorfulSteamEngineBlock extends SteamEngineBlock {

	protected final DyeColor color;
	private static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());

	public ColorfulSteamEngineBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		IPlacementHelper placementHelper = PlacementHelpers.get(placementHelperId);
		if (placementHelper.matchesItem(stack))
			return placementHelper.getOffset(player, level, state, pos, hitResult)
					.placeInWorld(level, (BlockItem) stack.getItem(), player, hand, hitResult);

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color)
				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));
				level.setBlockAndUpdate(pos, CCPBlocks.COLORFUL_STEAM_ENGINES.get(dyeColor).getDefaultState()
						.setValue(FACING, state.getValue(FACING)).setValue(WATERLOGGED, state.getValue(WATERLOGGED))
						.setValue(FACE, state.getValue(FACE)));
			}
			return ItemInteractionResult.SUCCESS;
		}

		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public Class<SteamEngineBlockEntity> getBlockEntityClass() {
		return SteamEngineBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends SteamEngineBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_STEAM_ENGINES.get();
	}

	@MethodsReturnNonnullByDefault
	private static class PlacementHelper implements IPlacementHelper {
		@Override
		public Predicate<ItemStack> getItemPredicate() {
			return AllBlocks.SHAFT::isIn;
		}

		@Override
		public Predicate<BlockState> getStatePredicate() {
			return s -> s.getBlock() instanceof SteamEngineBlock;
		}

		@Override
		public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
										 BlockHitResult ray) {
			BlockPos shaftPos = SteamEngineBlock.getShaftPos(state, pos);
			BlockState shaft = AllBlocks.SHAFT.getDefaultState();
			for (Direction direction : Direction.orderedByNearest(player)) {
				shaft = shaft.setValue(ShaftBlock.AXIS, direction.getAxis());
				if (isShaftValid(state, shaft))
					break;
			}

			BlockState newState = world.getBlockState(shaftPos);
			if (!newState.canBeReplaced())
				return PlacementOffset.fail();

			Direction.Axis axis = shaft.getValue(ShaftBlock.AXIS);
			return PlacementOffset.success(shaftPos,
					s -> BlockHelper
							.copyProperties(s,
									(world.isClientSide ? AllBlocks.SHAFT : AllBlocks.POWERED_SHAFT).getDefaultState())
							.setValue(PoweredShaftBlock.AXIS, axis));
		}
	}
}
