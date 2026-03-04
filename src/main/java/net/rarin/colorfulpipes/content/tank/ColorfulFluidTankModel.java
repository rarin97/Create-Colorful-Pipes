package net.rarin.colorfulpipes.content.tank;

import java.util.Arrays;
import java.util.function.Supplier;

import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.fluids.tank.FluidTankCTBehaviour;
import com.simibubi.create.foundation.block.connected.CTModel;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;

import net.createmod.catnip.data.Iterate;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.CCPSpriteShifts;

public class ColorfulFluidTankModel extends CTModel {

	public static ColorfulFluidTankModel standard(BakedModel originalModel, DyeColor color) {
		return new ColorfulFluidTankModel(originalModel,
				 CCPSpriteShifts.COLORFUL_FLUID_TANK.get(color),
				 CCPSpriteShifts.COLORFUL_FLUID_TANK_TOP.get(color),
				 CCPSpriteShifts.COLORFUL_FLUID_TANK_INNER.get(color)
		);
	}

	private ColorfulFluidTankModel(BakedModel originalModel, CTSpriteShiftEntry side, CTSpriteShiftEntry top,
						   CTSpriteShiftEntry inner) {
		super(originalModel, new FluidTankCTBehaviour(side, top, inner));
	}

	@Override
	public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
		CullData cullData = new CullData();
		for (Direction d : Iterate.horizontalDirections)
			cullData.setCulled(d, ConnectivityHandler.isConnected(blockView, pos, pos.relative(d)));

		context.pushTransform(quad -> {
			Direction cullFace = quad.cullFace();
			if (cullFace != null && cullData.isCulled(cullFace)) {
				return false;
			}
			quad.cullFace(null);
			return true;
		});
		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
		context.popTransform();
	}

	private static class CullData {
		boolean[] culledFaces;

		public CullData() {
			culledFaces = new boolean[4];
			Arrays.fill(culledFaces, false);
		}

		void setCulled(Direction face, boolean cull) {
			if (face.getAxis()
					.isVertical())
				return;
			culledFaces[face.get2DDataValue()] = cull;
		}

		boolean isCulled(Direction face) {
			if (face.getAxis()
					.isVertical())
				return false;
			return culledFaces[face.get2DDataValue()];
		}
	}

}
