package net.rarin.colorfulpipes.content.portableFluidInterface;

import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulPortableFluidInterfaceBlock extends WrenchableDirectionalBlock implements IBE<ColorfulPortableFluidInterfaceBlockEntity> {

	protected final DyeColor color;

	public ColorfulPortableFluidInterfaceBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	public DyeColor getColor() {
		return color;
	}

	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos blockPos,
								boolean isMoving) {
		withBlockEntityDo(world, pos, ColorfulPortableFluidInterfaceBlockEntity::neighbourChanged);
	}

	@Override
	public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
		AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getNearestLookingDirection();
		if (context.getPlayer() != null && context.getPlayer()
				.isSteppingCarefully())
			direction = direction.getOpposite();
		return defaultBlockState().setValue(FACING, direction.getOpposite());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return AllShapes.PORTABLE_STORAGE_INTERFACE.get(state.getValue(FACING));
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
		return getBlockEntityOptional(worldIn, pos).map(be -> be.isConnected() ? 15 : 0)
				.orElse(0);
	}

	@Override
	public Class<ColorfulPortableFluidInterfaceBlockEntity> getBlockEntityClass() {
		return ColorfulPortableFluidInterfaceBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends ColorfulPortableFluidInterfaceBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_PORTABLE_FLUID_INTERFACE.get();
	}
}
