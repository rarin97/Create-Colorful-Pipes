package net.rarin.colorfulpipes.content.valve;

import java.util.function.Consumer;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.ShaftVisual;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.rarin.colorfulpipes.CCPPartialModels;

public class ColorfulFluidValveVisual extends ShaftVisual<ColorfulFluidValveBlockEntity> implements SimpleDynamicVisual {

	protected TransformedInstance pointer;
	protected boolean settled;

	protected final double xRot;
	protected final double yRot;
	protected final int pointerRotationOffset;

	public ColorfulFluidValveVisual(VisualizationContext dispatcher, ColorfulFluidValveBlockEntity blockEntity, float partialTick) {
		super(dispatcher, blockEntity, partialTick);

		Direction facing = blockState.getValue(ColorfulFluidValveBlock.FACING);

		yRot = AngleHelper.horizontalAngle(facing);
		xRot = facing == Direction.UP ? 0 : facing == Direction.DOWN ? 180 : 90;

		Direction.Axis pipeAxis = ColorfulFluidValveBlock.getPipeAxis(blockState);
		Direction.Axis shaftAxis = KineticBlockEntityRenderer.getRotationAxisOf(blockEntity);

		boolean twist = pipeAxis.isHorizontal() && shaftAxis == Direction.Axis.X || pipeAxis.isVertical();
		pointerRotationOffset = twist ? 90 : 0;
		settled = false;
		DyeColor color = ((ColorfulFluidValveBlock) blockState.getBlock()).color;
		pointer = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(CCPPartialModels.COLORFUL_FLUID_VALVE_POINTER.get(color))).createInstance();

		transformPointer(partialTick);
	}

	@Override
	public void beginFrame(DynamicVisual.Context ctx) {
		if (blockEntity.pointer.settled() && settled)
			return;

		transformPointer(ctx.partialTick());
	}

	private void transformPointer(float partialTick) {
		float value = blockEntity.pointer.getValue(partialTick);
		float pointerRotation = Mth.lerp(value, 0, -90);
		settled = (value == 0 || value == 1) && blockEntity.pointer.settled();

		pointer.setIdentityTransform()
				.translate(getVisualPosition())
				.center()
				.rotateYDegrees((float) yRot)
				.rotateXDegrees((float) xRot)
				.rotateYDegrees(pointerRotationOffset + pointerRotation)
				.uncenter()
				.setChanged();
	}

	@Override
	public void updateLight(float partialTick) {
		super.updateLight(partialTick);
		relight(pointer);
	}

	@Override
	protected void _delete() {
		super._delete();
		pointer.delete();
	}

	@Override
	public void collectCrumblingInstances(Consumer<Instance> consumer) {
		super.collectCrumblingInstances(consumer);
		consumer.accept(pointer);
	}
}
