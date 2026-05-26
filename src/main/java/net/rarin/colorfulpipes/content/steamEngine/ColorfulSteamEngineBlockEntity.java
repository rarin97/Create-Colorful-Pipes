package net.rarin.colorfulpipes.content.steamEngine;

import com.hlysine.create_connected.registries.CCBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlockEntity;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineValueBox;
import com.simibubi.create.content.kinetics.steamEngine.SteamJetParticleData;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import java.lang.ref.WeakReference;
import java.util.List;
import net.rarin.colorfulpipes.compat.Create_Connected.content.ColorfulFluidVesselBlock;
import net.rarin.colorfulpipes.compat.Mods;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankBlock;

public class ColorfulSteamEngineBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

	protected ScrollOptionBehaviour<WindmillBearingBlockEntity.RotationDirection> movementDirection;

	public WeakReference<PoweredShaftBlockEntity> target;
	public WeakReference<FluidTankBlockEntity> source;

	float prevAngle = 0;

	public ColorfulSteamEngineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		source = new WeakReference<>(null);
		target = new WeakReference<>(null);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		movementDirection = new ScrollOptionBehaviour<>(WindmillBearingBlockEntity.RotationDirection.class,
				CreateLang.translateDirect("contraptions.windmill.rotation_direction"), this, new SteamEngineValueBox());
		movementDirection.onlyActiveWhen(() -> {
			PoweredShaftBlockEntity shaft = getShaft();
			return shaft == null || !shaft.hasSource();
		});
		movementDirection.withCallback($ -> onDirectionChanged());
		behaviours.add(movementDirection);

		registerAwardables(behaviours, AllAdvancements.STEAM_ENGINE);
	}

	private void onDirectionChanged() {
	}

	@Override
	public void tick() {
		super.tick();
		FluidTankBlockEntity tank = getTank();
		PoweredShaftBlockEntity shaft = getShaft();

		if (tank == null || shaft == null || !isValid()) {
			if (level.isClientSide())
				return;
			if (shaft == null)
				return;
			if (!shaft.getBlockPos()
					.subtract(worldPosition)
					.equals(shaft.enginePos))
				return;
			if (shaft.engineEfficiency == 0)
				return;
			Direction facing = ColorfulSteamEngineBlock.getFacing(getBlockState());
			if (level.isLoaded(worldPosition.relative(facing.getOpposite())))
				shaft.update(worldPosition, 0, 0);
			return;
		}

		boolean verticalTarget = false;
		BlockState shaftState = shaft.getBlockState();
		Direction.Axis targetAxis = Direction.Axis.X;
		if (shaftState.getBlock() instanceof IRotate ir)
			targetAxis = ir.getRotationAxis(shaftState);
		verticalTarget = targetAxis == Direction.Axis.Y;

		BlockState blockState = getBlockState();
		if (!(blockState.getBlock() instanceof ColorfulSteamEngineBlock))
			return;
		Direction facing = ColorfulSteamEngineBlock.getFacing(blockState);
		if (facing.getAxis() == Direction.Axis.Y)
			facing = blockState.getValue(ColorfulSteamEngineBlock.FACING);

		float efficiency = Mth.clamp(tank.boiler.getEngineEfficiency(tank.getTotalTankSize()), 0, 1);
		if (efficiency > 0)

			award(AllAdvancements.STEAM_ENGINE);

		int conveyedSpeedLevel =
				efficiency == 0 ? 1 : verticalTarget ? 1 : (int) GeneratingKineticBlockEntity.convertToDirection(1, facing);
		if (targetAxis == Direction.Axis.Z)
			conveyedSpeedLevel *= -1;
		if (movementDirection.get() == WindmillBearingBlockEntity.RotationDirection.COUNTER_CLOCKWISE)
			conveyedSpeedLevel *= -1;

		float shaftSpeed = shaft.getTheoreticalSpeed();
		if (shaft.hasSource() && shaftSpeed != 0 && conveyedSpeedLevel != 0
				&& (shaftSpeed > 0) != (conveyedSpeedLevel > 0)) {
			movementDirection.setValue(1 - movementDirection.get()
					.ordinal());
			conveyedSpeedLevel *= -1;
		}

		shaft.update(worldPosition, conveyedSpeedLevel, efficiency);

		if (!level.isClientSide)
			return;

		CatnipServices.PLATFORM.executeOnClientOnly(() -> this::spawnParticles);
	}

	@Override
	public void remove() {
		PoweredShaftBlockEntity shaft = getShaft();
		if (shaft != null)
			shaft.remove(worldPosition);
		super.remove();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	protected AABB createRenderBoundingBox() {
		return super.createRenderBoundingBox().inflate(2);
	}

	public PoweredShaftBlockEntity getShaft() {
		PoweredShaftBlockEntity shaft = target.get();
		if (shaft == null || shaft.isRemoved() || !shaft.canBePoweredBy(worldPosition)) {
			if (shaft != null)
				target = new WeakReference<>(null);
			Direction facing = ColorfulSteamEngineBlock.getFacing(getBlockState());
			BlockEntity anyShaftAt = level.getBlockEntity(worldPosition.relative(facing, 2));
			if (anyShaftAt instanceof PoweredShaftBlockEntity ps && ps.canBePoweredBy(worldPosition))
				target = new WeakReference<>(shaft = ps);
		}
		return shaft;
	}

	public FluidTankBlockEntity getTank() {
		FluidTankBlockEntity tank = source.get();
		if (tank == null || tank.isRemoved()) {
			if (tank != null)
				source = new WeakReference<>(null);
			Direction facing = ColorfulSteamEngineBlock.getFacing(getBlockState());
			BlockEntity be = level.getBlockEntity(worldPosition.relative(facing.getOpposite()));
			if (be instanceof FluidTankBlockEntity tankBe)
				source = new WeakReference<>(tank = tankBe);
		}
		if (tank == null)
			return null;
		return tank.getControllerBE();
	}

	public boolean isValid() {
		Direction dir = ColorfulSteamEngineBlock.getConnectedDirection(getBlockState()).getOpposite();

		Level level = getLevel();
		if (level == null)
			return false;

		if (level.getBlockState(getBlockPos().relative(dir)).is(AllBlocks.FLUID_TANK.get())
				|| level.getBlockState(getBlockPos().relative(dir)).getBlock() instanceof ColorfulFluidTankBlock)
			return true;
		return Mods.CREATE_CONNECTED.isLoaded() && (level.getBlockState(getBlockPos().relative(dir)).is(CCBlocks.FLUID_VESSEL.get())
				 ||level.getBlockState(getBlockPos().relative(dir)).getBlock() instanceof ColorfulFluidVesselBlock);
	}

	@OnlyIn(Dist.CLIENT)
	private void spawnParticles() {
		Float targetAngle = getTargetAngle();
		PoweredShaftBlockEntity ste = target.get();
		if (ste == null)
			return;
		if (!ste.isPoweredBy(worldPosition) || ste.engineEfficiency == 0)
			return;
		if (targetAngle == null)
			return;

		float angle = AngleHelper.deg(targetAngle);
		angle += (angle < 0) ? -180 + 75 : 360 - 75;
		angle %= 360;

		PoweredShaftBlockEntity shaft = getShaft();
		if (shaft == null || shaft.getSpeed() == 0)
			return;

		if (angle >= 0 && !(prevAngle > 180 && angle < 180)) {
			prevAngle = angle;
			return;
		}
		if (angle < 0 && !(prevAngle < -180 && angle > -180)) {
			prevAngle = angle;
			return;
		}

		FluidTankBlockEntity sourceBE = source.get();
		if (sourceBE != null) {
			FluidTankBlockEntity controller = sourceBE.getControllerBE();
			if (controller != null && controller.boiler != null) {
				controller.boiler.queueSoundOnSide(worldPosition, ColorfulSteamEngineBlock.getFacing(getBlockState()));
			}
		}

		Direction facing = ColorfulSteamEngineBlock.getFacing(getBlockState());

		Vec3 offset = VecHelper.rotate(new Vec3(0, 0, 1).add(VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1)
				.multiply(1, 1, 0)
				.normalize()
				.scale(.5f)), AngleHelper.verticalAngle(facing), Direction.Axis.X);
		offset = VecHelper.rotate(offset, AngleHelper.horizontalAngle(facing), Direction.Axis.Y);
		Vec3 v = offset.scale(.5f)
				.add(Vec3.atCenterOf(worldPosition));
		Vec3 m = offset.subtract(Vec3.atLowerCornerOf(facing.getNormal())
				.scale(.75f));
		level.addParticle(new SteamJetParticleData(1), v.x, v.y, v.z, m.x, m.y, m.z);

		prevAngle = angle;
	}

	@Nullable
	@OnlyIn(Dist.CLIENT)
	public Float getTargetAngle() {
		float angle = 0;
		BlockState blockState = getBlockState();
		if (!(blockState.getBlock() instanceof ColorfulSteamEngineBlock))
			return null;

		Direction facing = ColorfulSteamEngineBlock.getFacing(blockState);
		PoweredShaftBlockEntity shaft = getShaft();
		Direction.Axis facingAxis = facing.getAxis();
		Direction.Axis axis = Direction.Axis.Y;

		if (shaft == null)
			return null;

		axis = KineticBlockEntityRenderer.getRotationAxisOf(shaft);
		angle = KineticBlockEntityRenderer.getAngleForBe(shaft, shaft.getBlockPos(), axis);

		if (axis == facingAxis)
			return null;
		if (axis.isHorizontal() && (facingAxis == Direction.Axis.X ^ facing.getAxisDirection() == Direction.AxisDirection.POSITIVE))
			angle *= -1;
		if (axis == Direction.Axis.X && facing == Direction.DOWN)
			angle *= -1;
		return angle;
	}

	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		PoweredShaftBlockEntity shaft = getShaft();
		return shaft == null ? false : shaft.addToEngineTooltip(tooltip, isPlayerSneaking);
	}

}
