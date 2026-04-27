package net.rarin.colorfulpipes.content.portableFluidInterface;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ActorVisual;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;

import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.animation.LerpedFloat;

public class ColorfulPSIActorVisual extends ActorVisual{

	private final ColorfulPIInstance instance;

	public ColorfulPSIActorVisual(VisualizationContext context, VirtualRenderWorld world, MovementContext movementContext) {
		super(context, world, movementContext);

		instance = new ColorfulPIInstance(context.instancerProvider(), movementContext.state, movementContext.localPos, false);

		instance.middle.light(localBlockLight(), 0);
		instance.top.light(localBlockLight(), 0);
	}

	@Override
	public void beginFrame() {
		LerpedFloat lf = ColorfulPortableFluidInterfaceMovement.getAnimation(context);
		instance.tick(lf.settled());
		instance.beginFrame(lf.getValue(AnimationTickHolder.getPartialTicks()));
	}

	@Override
	protected void _delete() {
		instance.remove();
	}
}
