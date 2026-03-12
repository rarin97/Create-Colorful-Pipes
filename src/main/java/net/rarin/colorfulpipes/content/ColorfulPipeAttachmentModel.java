package net.rarin.colorfulpipes.content;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.decoration.bracket.BracketedBlockEntityBehaviour;
import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.model.BakedModelWrapperWithData;
import net.createmod.catnip.data.Iterate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import net.rarin.colorfulpipes.CCPPartialModels;
import net.rarin.colorfulpipes.content.pipe.ColorfulFluidPipeBlock;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ColorfulPipeAttachmentModel extends BakedModelWrapperWithData {
	private static final ModelProperty<ColorfulPipeModelData> PIPE_PROPERTY = new ModelProperty<>();
	protected final DyeColor color;
	private final boolean ao;

	public static ColorfulPipeAttachmentModel withAO(BakedModel template, DyeColor color){
		return new ColorfulPipeAttachmentModel(template, color, true);
	}

	public static ColorfulPipeAttachmentModel withoutAO(BakedModel template, DyeColor color){
		return new ColorfulPipeAttachmentModel(template, color, false);
	}

	public ColorfulPipeAttachmentModel(BakedModel template, DyeColor color, boolean ao) {
		super(template);
		this.color = color;
        this.ao = ao;
    }

	@Override
	protected ModelData.Builder gatherModelData(ModelData.Builder builder, BlockAndTintGetter world, BlockPos pos, BlockState state, ModelData blockEntityData) {
		ColorfulPipeModelData data = new ColorfulPipeModelData();
		FluidTransportBehaviour transport = BlockEntityBehaviour.get(world, pos, FluidTransportBehaviour.TYPE);
		BracketedBlockEntityBehaviour bracket = BlockEntityBehaviour.get(world, pos, BracketedBlockEntityBehaviour.TYPE);

		if(transport != null){
			for (Direction d : Iterate.directions){
				data.putAttachment(d, transport.getRenderedRimAttachment(world, pos, state, d));
			}
		}
		if(bracket != null){
			data.putBracket(bracket.getBracket());
		}

		data.setEncased(ColorfulFluidPipeBlock.shouldDrawCasing(world, pos, state));
		return builder.with(PIPE_PROPERTY, data);
	}

	@Override
	public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
		List<ChunkRenderTypeSet> set = new ArrayList<>();

		set.add(super.getRenderTypes(state, rand, data));
		set.add(AllPartialModels.FLUID_PIPE_CASING.get().getRenderTypes(state, rand, data));

		if (data.has(PIPE_PROPERTY)) {
			ColorfulPipeModelData pipeData = data.get(PIPE_PROPERTY);
			for (Direction d : Iterate.directions) {
				FluidTransportBehaviour.AttachmentTypes type = pipeData.getAttachment(d);
				for (FluidTransportBehaviour.AttachmentTypes.ComponentPartials partial : type.partials) {
					ChunkRenderTypeSet attachmentRenderTypeSet = AllPartialModels.PIPE_ATTACHMENTS.get(partial).get(d)
							.get().getRenderTypes(state, rand, data);
					set.add(attachmentRenderTypeSet);
				}
			}
		}

		return ChunkRenderTypeSet.union(set);
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData data, RenderType renderType) {
		List<BakedQuad> quads = super.getQuads(state, side, rand, data, renderType);
		if (data.has(PIPE_PROPERTY)) {
			ColorfulPipeModelData pipeData = data.get(PIPE_PROPERTY);
			quads = new ArrayList<>(quads);
			addQuads(quads, state, side, rand, data, renderType, pipeData);
		}
		return quads;
	}

	@Override
	public boolean useAmbientOcclusion() {
		return ao;
	}

	private void addQuads(List<BakedQuad> quads, BlockState state, Direction side, RandomSource rand, ModelData data, RenderType renderType,
						  ColorfulPipeModelData Data) {
		BakedModel bracket = Data.getBracket();
		if (bracket != null)
			quads.addAll(bracket.getQuads(state, side, rand, data, renderType));
		for (Direction d : Iterate.directions) {
			FluidTransportBehaviour.AttachmentTypes type = Data.getAttachment(d);
			for (FluidTransportBehaviour.AttachmentTypes.ComponentPartials partial : type.partials) {
				quads.addAll(CCPPartialModels.COLORFUL_PIPE_ATTACHMENTS
						.get(partial)
						.get(color)
						.get(d)
						.get()
						.getQuads(state, side, rand, data, renderType));
			}
		}
		if (Data.isEncased())
			quads.addAll(CCPPartialModels.COLORFUL_FLUID_PIPE_CASINGS.get(color).get()
					.getQuads(state, side, rand, data, renderType));
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
