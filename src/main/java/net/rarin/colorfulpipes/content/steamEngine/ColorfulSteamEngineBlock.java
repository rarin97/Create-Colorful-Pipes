package net.rarin.colorfulpipes.content.steamEngine;

import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ColorfulSteamEngineBlock extends SteamEngineBlock {

	protected final DyeColor color;

	public ColorfulSteamEngineBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		if (stack.getItem() instanceof DyeItem dye) {
			DyeColor dyeColor = dye.getDyeColor();

			if (dyeColor == this.color)
				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

			if (!level.isClientSide) {
				level.levelEvent(2001, pos, Block.getId(state));
				//level.setBlockAndUpdate(pos, CCPBlocks.COLORFUL_STEAM_ENGINES.get(dyeColor).getDefaultState());
			}
			return ItemInteractionResult.SUCCESS;
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

//	public BlockEntityType<? extends SteamEngineBlockEntity> getBlockEntityType() {
//		return CCPBlockEntityTypes.COLORFUL_STEAM_ENGINES.get();
//	}
}
