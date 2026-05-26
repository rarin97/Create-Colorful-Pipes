package net.rarin.colorfulpipes.Datagen;

import com.simibubi.create.content.fluids.pipes.EncasedPipeBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import net.createmod.catnip.data.Iterate;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class CCPBlockStateGen extends BlockStateGen {

	public static <P extends EncasedPipeBlock> NonNullBiConsumer<DataGenContext<Block, P>, RegistrateBlockstateProvider> encasedPipe() {
		return (c, p) -> {

			ModelFile open = AssetLookup.partialBaseModel(c, p, "open");
			ModelFile flat = AssetLookup.partialBaseModel(c, p, "flat");

			ModelFile coreX = p.models().getExistingFile(p.modLoc("block/encased_pipe/core_x"));
			ModelFile coreY = p.models().getExistingFile(p.modLoc("block/encased_pipe/core_y"));
			ModelFile coreZ = p.models().getExistingFile(p.modLoc("block/encased_pipe/core_z"));

			MultiPartBlockStateBuilder builder = p.getMultipartBuilder(c.get());

			// INNER PIPE
			builder.part()
					.modelFile(coreX)
					.addModel()
					.condition(EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(Direction.EAST), true)
					.condition(EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(Direction.WEST), true)
					.end();

			builder.part()
					.modelFile(coreY)
					.addModel()
					.condition(EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(Direction.UP), true)
					.condition(EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(Direction.DOWN), true)
					.end();

			builder.part()
					.modelFile(coreZ)
					.addModel()
					.condition(EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(Direction.NORTH), true)
					.condition(EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(Direction.SOUTH), true)
					.end();

			// CASING SIDES
			for (boolean flatPass : Iterate.trueAndFalse)
				for (Direction d : Iterate.directions) {

					int verticalAngle =
							d == Direction.UP ? 90 :
									d == Direction.DOWN ? -90 : 0;

					builder.part()
							.modelFile(flatPass ? flat : open)
							.rotationX(verticalAngle)
							.rotationY((int) (d.toYRot()
									+ (d.getAxis().isVertical() ? 90 : 0)) % 360)
							.addModel()
							.condition(
									EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(d),
									!flatPass)
							.end();
				}
		};
	}
}
