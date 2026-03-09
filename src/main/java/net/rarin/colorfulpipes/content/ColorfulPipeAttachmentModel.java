package net.rarin.colorfulpipes.content;

import com.simibubi.create.content.decoration.bracket.BracketedBlockEntityBehaviour;
import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.createmod.catnip.data.Iterate;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.CCPPartialModels;
import net.rarin.colorfulpipes.content.pipe.ColorfulFluidPipeBlock;

import java.util.Arrays;
import java.util.function.Supplier;

public class ColorfulPipeAttachmentModel extends ForwardingBakedModel {

	protected final DyeColor color;
	private boolean ao;

	public static ColorfulPipeAttachmentModel withAO(BakedModel template, DyeColor color) {
		return new ColorfulPipeAttachmentModel(template, color, true);
	}

	public static ColorfulPipeAttachmentModel withoutAO(BakedModel template, DyeColor color) {
		return new ColorfulPipeAttachmentModel(template, color, false);
	}

	public ColorfulPipeAttachmentModel(BakedModel template, DyeColor color, boolean ao) {
		wrapped = template;
		this.color = color;
		this.ao = ao;
	}

	@Override
	public boolean isVanillaAdapter() {
		return false;
	}

	@Override
	public void emitBlockQuads(BlockAndTintGetter world, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
		ColorfulPipeModelData data = new ColorfulPipeModelData();
		BracketedBlockEntityBehaviour bracket = BlockEntityBehaviour.get(world, pos, BracketedBlockEntityBehaviour.TYPE);

		if (world.getBlockEntityRenderData(pos) instanceof FluidTransportBehaviour.AttachmentTypes[] attachments) {
			for (int i = 0; i < attachments.length; i++) {
				data.putAttachment(Iterate.directions[i], attachments[i]);
			}
		}

		if (bracket != null)
			data.putBracket(bracket.getBracket());

		data.setEncased(ColorfulFluidPipeBlock.shouldDrawCasing(world, pos, state));

		super.emitBlockQuads(world, state, pos, randomSupplier, context);

		addQuads(world, state, pos, randomSupplier, context, data);
	}

	private void addQuads(BlockAndTintGetter world, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context,
						  ColorfulPipeModelData Data) {
		BakedModel bracket = Data.getBracket();
		if (bracket != null)
			bracket.emitBlockQuads(world, state, pos, randomSupplier, context);
		for (Direction d : Iterate.directions) {
			FluidTransportBehaviour.AttachmentTypes type = Data.getAttachment(d);
			for (FluidTransportBehaviour.AttachmentTypes.ComponentPartials partial : type.partials) {
				CCPPartialModels.COLORFUL_PIPE_ATTACHMENTS
						.get(partial)
						.get(color)
						.get(d)
						.get()
						.emitBlockQuads(world, state, pos, randomSupplier, context);
			}
		}
		if (Data.isEncased())
			CCPPartialModels.COLORFUL_FLUID_PIPE_CASINGS.get(color).get()
					.emitBlockQuads(world, state, pos, randomSupplier, context);
	}

	@Override
	public boolean useAmbientOcclusion() {
		return ao;
	}

	private static class ColorfulPipeModelData {
		private FluidTransportBehaviour.AttachmentTypes[] attachments;
		private boolean encased;
		private BakedModel bracket;

		public ColorfulPipeModelData() {
			attachments = new FluidTransportBehaviour.AttachmentTypes[6];
			Arrays.fill(attachments, FluidTransportBehaviour.AttachmentTypes.NONE);
		}

		public void putBracket(BlockState state) {
			if (state != null) {
				this.bracket = Minecraft.getInstance()
						.getBlockRenderer()
						.getBlockModel(state);
			}
		}

		public BakedModel getBracket() {
			return bracket;
		}

		public void putAttachment(Direction face, FluidTransportBehaviour.AttachmentTypes rim) {
			attachments[face.get3DDataValue()] = rim;
		}

		public FluidTransportBehaviour.AttachmentTypes getAttachment(Direction face) {
			return attachments[face.get3DDataValue()];
		}

		public void setEncased(boolean encased) {
			this.encased = encased;
		}

		public boolean isEncased() {
			return encased;
		}
	}
}
