package net.rarin.colorfulpipes.content.smartPipe;

import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.pipes.SmartFluidPipeBlock;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public class ColorfulSmartFluidPipeGenerator extends SpecialBlockStateGen {
	protected final DyeColor color;

	public ColorfulSmartFluidPipeGenerator(DyeColor color) {
		this.color = color;
	}

	@Override
	protected int getXRotation(BlockState state) {
		AttachFace attachFace = state.getValue(SmartFluidPipeBlock.FACE);
		return attachFace == AttachFace.CEILING ? 180 : attachFace == AttachFace.FLOOR ? 0 : 270;
	}

	@Override
	protected int getYRotation(BlockState state) {
		AttachFace attachFace = state.getValue(SmartFluidPipeBlock.FACE);
		int angle = horizontalAngle(state.getValue(SmartFluidPipeBlock.FACING));
		return angle + (attachFace == AttachFace.CEILING ? 180 : 0);
	}

	@Override
	public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
												BlockState state) {

		prov.models()
				.withExistingParent("item/" + color + "_smart_fluid_pipe", Create.asResource("block/smart_fluid_pipe/item"))
				.texture("1", prov.modLoc("block/fluid_pipe/" + color))
				.texture("2", prov.modLoc("block/smart_pipe_1/" + color))
				.texture("3", prov.modLoc("block/smart_pipe_2/" + color))
				.texture("4", Create.asResource("block/smart_pipe_3"));

		return prov.models()
				.withExistingParent("block/" + color + "_smart_fluid_pipe", Create.asResource("block/smart_fluid_pipe/block"))
				.texture("2", prov.modLoc("block/smart_pipe_1/" + color))
				.texture("3", prov.modLoc("block/smart_pipe_2/" + color))
				.texture("4", prov.modLoc("block/fluid_pipe/" + color))
				.texture("5", Create.asResource("block/smart_pipe_3"))
				.texture("particle", prov.modLoc("block/copper_underside/" + color));

	}

}
