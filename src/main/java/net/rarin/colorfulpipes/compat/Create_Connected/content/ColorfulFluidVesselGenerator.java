package net.rarin.colorfulpipes.compat.Create_Connected.content;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;

import static com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock.AXIS;
import static com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock.NEGATIVE;
import static com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock.POSITIVE;
import static com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock.SHAPE;

public class ColorfulFluidVesselGenerator extends SpecialBlockStateGen {
	private final String prefix;
	private final DyeColor color;

	public ColorfulFluidVesselGenerator(DyeColor color) {
		this("", color);
	}

	public ColorfulFluidVesselGenerator(String prefix, DyeColor color) {
		this.prefix = prefix;
		this.color = color;
	}

	@Override
	protected int getXRotation(BlockState state) {
		return 0;
	}

	@Override
	protected int getYRotation(BlockState state) {
		return 0;
	}

	@Override
	public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
		Boolean positive = state.getValue(POSITIVE);
		Boolean negative = state.getValue(NEGATIVE);
		FluidVesselBlock.Shape shape = state.getValue(SHAPE);
		Direction.Axis axis = state.getValue(AXIS);

		if (positive && negative)
			shape = shape.nonSingleVariant();

		String shapeName = "middle";
		if (positive && negative)
			shapeName = "single";
		else if (positive)
			shapeName = "positive";
		else if (negative)
			shapeName = "negative";

		String modelName = (axis == Direction.Axis.X ? "x" : "z") +
				"_" + shapeName +
				(shape == FluidVesselBlock.Shape.PLAIN ? "" : "_" + shape.getSerializedName());

		if (prefix.isEmpty())
			return prov.models()
					.withExistingParent( "block/" + color + "_fluid_vessel/block_" + prefix + modelName,
							Create.asResource("block/fluid_vessel/block_" + modelName))
					.texture("0", prov.modLoc("block/" + prefix + "fluid_tank_top/" + color))
					.texture("1", prov.modLoc("block/" + prefix + "fluid_tank/" + color))
					.texture("3", prov.modLoc("block/" + prefix + "fluid_tank_window/" + color))
					.texture("4", prov.modLoc("block/" + prefix + "fluid_tank_inner/" + color))
					.texture("5", prov.modLoc("block/" + prefix + "fluid_tank_window_single/" + color))
					.texture("6", prov.modLoc("block/" + prefix + "fluid_container_window/" + color))
					.texture("7", prov.modLoc("block/" + prefix + "fluid_container_window_single/" + color))
					.texture("particle", prov.modLoc("block/" + prefix + "fluid_tank/" + color));

		return AssetLookup.partialBaseModel(ctx, prov, modelName);
	}

}
