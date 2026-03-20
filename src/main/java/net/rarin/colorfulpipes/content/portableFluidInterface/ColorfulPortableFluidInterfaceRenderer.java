package net.rarin.colorfulpipes.content.portableFluidInterface;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceBlock;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceBlockEntity;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceMovement;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceRenderer;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;

import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;

import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.CCPPartialModels;

import java.util.function.Consumer;

public class ColorfulPortableFluidInterfaceRenderer extends PortableStorageInterfaceRenderer {

	public ColorfulPortableFluidInterfaceRenderer(BlockEntityRendererProvider.Context ctx) {
		super(ctx);
	}

	@Override
	protected void renderSafe(PortableStorageInterfaceBlockEntity be, float partialTicks, PoseStack ms,
							  MultiBufferSource buffer, int light, int overlay) {
		if (VisualizationManager.supportsVisualization(be.getLevel()))
			return;

		BlockState blockState = be.getBlockState();
		float progress = 0f;
		VertexConsumer vb = buffer.getBuffer(RenderType.solid());
		render(blockState, false, progress, null, sbb -> sbb.light(light)
				.renderInto(ms, vb));
	}

	public static void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
										   ContraptionMatrices matrices, MultiBufferSource buffer) {
		BlockState blockState = context.state;
		VertexConsumer vb = buffer.getBuffer(RenderType.solid());
		float renderPartialTicks = AnimationTickHolder.getPartialTicks();

		LerpedFloat animation = PortableStorageInterfaceMovement.getAnimation(context);
		float progress = animation.getValue(renderPartialTicks);
		boolean lit = animation.settled();
		render(blockState, lit, progress, matrices.getModel(),
				sbb -> sbb.light(LevelRenderer.getLightColor(renderWorld, context.localPos))
						.useLevelLight(context.world, matrices.getWorld())
						.renderInto(matrices.getViewProjection(), vb));
	}

	private static void render(BlockState blockState, boolean lit, float progress, PoseStack local,
							   Consumer<SuperByteBuffer> drawCallback) {
		SuperByteBuffer middle = CachedBuffers.partial(getMiddleForState(blockState, lit), blockState);
		SuperByteBuffer top = CachedBuffers.partial(getTopForState(blockState), blockState);

		if (local != null) {
			middle.transform(local);
			top.transform(local);
		}
		Direction facing = blockState.getValue(PortableStorageInterfaceBlock.FACING);
		rotateToFacing(middle, facing);
		rotateToFacing(top, facing);
		middle.translate(0, progress * 0.5f + 0.375f, 0);
		top.translate(0, progress, 0);

		drawCallback.accept(middle);
		drawCallback.accept(top);
	}

	private static void rotateToFacing(SuperByteBuffer buffer, Direction facing) {
		buffer.center()
				.rotateYDegrees(AngleHelper.horizontalAngle(facing))
				.rotateXDegrees(facing == Direction.UP ? 0 : facing == Direction.DOWN ? 180 : 90)
				.uncenter();
	}

	static PartialModel getMiddleForState(BlockState state, boolean lit) {
		if (AllBlocks.PORTABLE_FLUID_INTERFACE.has(state))
			return lit ? AllPartialModels.PORTABLE_FLUID_INTERFACE_MIDDLE_POWERED
					: AllPartialModels.PORTABLE_FLUID_INTERFACE_MIDDLE;
		return lit ? AllPartialModels.PORTABLE_FLUID_INTERFACE_MIDDLE_POWERED
				: AllPartialModels.PORTABLE_FLUID_INTERFACE_MIDDLE;
	}

	static PartialModel getTopForState(BlockState state) {
		if (AllBlocks.PORTABLE_FLUID_INTERFACE.has(state))
			return AllPartialModels.PORTABLE_FLUID_INTERFACE_TOP;
		return AllPartialModels.PORTABLE_FLUID_INTERFACE_TOP;
	}

	private PartialModel getTopForState(PortableStorageInterfaceBlockEntity be) {
		if (be instanceof ColorfulPortableFluidInterfaceBlockEntity colorful) {
			DyeColor color = ((ColorfulPortableFluidInterfaceBlock)
					colorful.getBlockState().getBlock()).getColor();

			return CCPPartialModels.COLORFUL_PORTABLE_FLUID_INTERFACE_TOP
					.getOrDefault(color, AllPartialModels.PORTABLE_FLUID_INTERFACE_TOP);
		}

		return AllPartialModels.PORTABLE_FLUID_INTERFACE_TOP;
	}
}

