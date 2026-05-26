package net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.CEIBlockEntityTypes;
import plus.dragons.createenchantmentindustry.common.kinetics.grindstone.GrindstoneDrainBlock;
import plus.dragons.createenchantmentindustry.common.kinetics.grindstone.GrindstoneDrainBlockEntity;
import plus.dragons.createenchantmentindustry.common.kinetics.grindstone.MechanicalGrindstoneBlock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static plus.dragons.createenchantmentindustry.common.registry.CEIBlocks.MECHANICAL_GRINDSTONE;

public class ColorfulGrindstoneDrainBlock extends GrindstoneDrainBlock {
	private static final Method MECHANICAL_GRINDSTONE_USE_ITEM_ON = MechanicalGrindstoneUseItemOn();
	protected final DyeColor color;

	public ColorfulGrindstoneDrainBlock(Properties properties, DyeColor color) {
		super(null, properties);
		this.color = color;
	}

	protected MechanicalGrindstoneBlock getMechanicalGrindstone() {
		return MECHANICAL_GRINDSTONE.get();
	}

	private static Method MechanicalGrindstoneUseItemOn() {
		try {
			Method method = MechanicalGrindstoneBlock.class.getDeclaredMethod("useItemOn", ItemStack.class,
					BlockState.class, Level.class, BlockPos.class, Player.class, InteractionHand.class, BlockHitResult.class);
			method.setAccessible(true);
			return method;
		} catch (ReflectiveOperationException exception) {
			throw new RuntimeException("Failed to access MechanicalGrindstoneBlockuseItemOn", exception);
		}
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
											  InteractionHand hand, BlockHitResult hitResult) {
		if (hitResult.getDirection() == Direction.UP) {
			try {
				return (ItemInteractionResult) MECHANICAL_GRINDSTONE_USE_ITEM_ON.invoke(getMechanicalGrindstone(), stack,
						state, level, pos, player, hand, hitResult);
			} catch (IllegalAccessException | InvocationTargetException exception) {
				throw new RuntimeException("Failed to forward interaction to CEI mechanical grindstone", exception);
			}
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	public ItemRequirement getRequiredItems(BlockState state, BlockEntity blockEntity) {
		return new ItemRequirement(List.of(
				new ItemRequirement.StackRequirement(new ItemStack(getMechanicalGrindstone()), ItemRequirement.ItemUseType.CONSUME),
				new ItemRequirement.StackRequirement(AllBlocks.ITEM_DRAIN.asStack(), ItemRequirement.ItemUseType.CONSUME)));
	}

	@Override
	public BlockEntityType<? extends GrindstoneDrainBlockEntity> getBlockEntityType() {
		return CEIBlockEntityTypes.COLORFUL_GRINDSTONE_DRAIN.get();
	}
}
