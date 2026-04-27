package net.rarin.colorfulpipes.content;

import org.jetbrains.annotations.Nullable;

import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.CCPSpriteShifts;

public class ColorfulGlassCTBehaviour extends EncasedCTBehaviour {

	protected final DyeColor color;

	public ColorfulGlassCTBehaviour(DyeColor color) {
		super(null);
		this.color = color;
	}

	@Override
	public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
		return CCPSpriteShifts.COLORFUL_COPPER_GLASS_CASING.get(color);
	}
}
