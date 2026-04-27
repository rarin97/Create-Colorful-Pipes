package net.rarin.colorfulpipes.content.portableFluidInterface;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.jetbrains.annotations.Nullable;

import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceMovement;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ActorVisual;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;

import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.minecraft.client.renderer.MultiBufferSource;

public class ColorfulPortableFluidInterfaceMovement extends PortableStorageInterfaceMovement {

	@Nullable
	@Override
	public ActorVisual createVisual(VisualizationContext visualizationContext, VirtualRenderWorld simulationWorld,
									MovementContext movementContext) {

		return new ColorfulPSIActorVisual(visualizationContext, simulationWorld, movementContext);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
									ContraptionMatrices matrices, MultiBufferSource buffer) {

		if (!VisualizationManager.supportsVisualization(context.world))
			ColorfulPortableFluidInterfaceRenderer.renderInContraption(context, renderWorld, matrices, buffer);
	}
}
