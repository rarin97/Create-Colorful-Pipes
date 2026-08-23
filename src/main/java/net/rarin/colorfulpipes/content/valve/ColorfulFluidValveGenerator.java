package net.rarin.colorfulpipes.content.valve;


import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.pipes.valve.FluidValveBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.rarin.colorfulpipes.ColorfulPipes;

public class ColorfulFluidValveGenerator extends SpecialBlockStateGen {

	protected final DyeColor color;

	public ColorfulFluidValveGenerator(DyeColor color) {
		this.color = color;
	}

	@Override
	protected int getXRotation(BlockState state) {
		Direction facing = state.getValue(FluidValveBlock.FACING);
		return facing.getAxis()
				.isVertical() ? facing == Direction.DOWN ? 180 : 0 : 90;
	}

	@Override
	protected int getYRotation(BlockState state) {
		Direction facing = state.getValue(FluidValveBlock.FACING);
		return facing.getAxis()
				.isVertical() ? 0 : horizontalAngle(facing) + 180;
	}

	@Override
	public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
												BlockState state) {
		String open = state.getValue(FluidValveBlock.ENABLED) ? "open" : "closed";
		String facing = state.getValue(FluidValveBlock.FACING)
				.getAxis()
				.isVertical() ? "vertical" : "horizontal";
		ModelFile model = AssetLookup.partialBaseModel(ctx, prov, facing, open);

		ResourceLocation parentLocation = model.getLocation();

		prov.models()
				.withExistingParent("block/" + color + "_fluid_valve" , Create.asResource("block/fluid_valve/block_vertical_" + open))
				.texture("2", ColorfulPipes.asResource("block/fluid_valve/" + color))
				.texture("3", ColorfulPipes.asResource("block/valve_closed/" + color))
				.texture("particle", ColorfulPipes.asResource("block/copper_underside/" + color));


		return prov.models()
				.withExistingParent("block/" + color + "_fluid_valve" , Create.asResource("block/fluid_valve/block_horizontal_" + open))
				.texture("2", ColorfulPipes.asResource("block/fluid_valve/" + color))
				.texture("3", ColorfulPipes.asResource("block/valve_closed/" + color))
				.texture("particle", ColorfulPipes.asResource("block/copper_underside/" + color));
	}

}


