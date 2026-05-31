package net.rarin.colorfulpipes.content.tank;

import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public class ColorfulFluidTankGenerator extends SpecialBlockStateGen {
	private final String prefix;
	protected final DyeColor color;

	public ColorfulFluidTankGenerator(DyeColor color) {
		this("", color);
	}

	public ColorfulFluidTankGenerator(String prefix, DyeColor color) {
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
	public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
												BlockState state) {
		Boolean top = state.getValue(FluidTankBlock.TOP);
		Boolean bottom = state.getValue(FluidTankBlock.BOTTOM);
		FluidTankBlock.Shape shape = state.getValue(FluidTankBlock.SHAPE);

		String shapeName = "middle";
		if (top && bottom)
			shapeName = "single";
		else if (top)
			shapeName = "top";
		else if (bottom)
			shapeName = "bottom";

		String modelName = shapeName + (shape == FluidTankBlock.Shape.PLAIN ? "" : "_" + shape.getSerializedName());

		if (prefix.isEmpty())
			return prov.models()
					.withExistingParent("block/" + color + "_fluid_tank/block_" + prefix + modelName,
							Create.asResource("block/fluid_tank/block_" + modelName))
					.texture("0", prov.modLoc("block/" + prefix + "fluid_tank_top/" + color))
					.texture("1", prov.modLoc("block/" + prefix + "fluid_tank/" + color))
					.texture("3", prov.modLoc("block/" + prefix + "fluid_tank_window/" + color))
					.texture("4", prov.modLoc("block/" + prefix + "fluid_tank_inner/" + color))
					.texture("5", prov.modLoc("block/" + prefix + "fluid_tank_window_single/" + color))
					.texture("particle", prov.modLoc("block/" + prefix + "fluid_tank/" + color));

		return AssetLookup.partialBaseModel(ctx, prov, modelName);
	}

}
