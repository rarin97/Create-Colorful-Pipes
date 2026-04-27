package net.rarin.colorfulpipes.content.portableFluidInterface;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visual.TickableVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import dev.engine_room.flywheel.lib.visual.SimpleTickableVisual;

import java.util.function.Consumer;

public class ColorfulPSIVisual extends AbstractBlockEntityVisual<ColorfulPortableFluidInterfaceBlockEntity> implements SimpleDynamicVisual, SimpleTickableVisual {

	private final ColorfulPIInstance instance;

	public ColorfulPSIVisual(VisualizationContext visualizationContext, ColorfulPortableFluidInterfaceBlockEntity blockEntity, float partialTick) {
		super(visualizationContext, blockEntity, partialTick);

		instance = new ColorfulPIInstance(visualizationContext.instancerProvider(), blockState, getVisualPosition(), isLit());
		instance.beginFrame(blockEntity.getExtensionDistance(partialTick));
	}

	@Override
	public void tick(TickableVisual.Context ctx) {
		instance.tick(isLit());
	}

	@Override
	public void beginFrame(DynamicVisual.Context ctx) {
		instance.beginFrame(blockEntity.getExtensionDistance(ctx.partialTick()));
	}

	@Override
	public void updateLight(float partialTick) {
		relight(instance.middle, instance.top);
	}

	@Override
	protected void _delete() {
		instance.remove();
	}

	private boolean isLit() {
		return blockEntity.isConnected();
	}

	@Override
	public void collectCrumblingInstances(Consumer<Instance> consumer) {
		instance.collectCrumblingInstances(consumer);
	}
}
