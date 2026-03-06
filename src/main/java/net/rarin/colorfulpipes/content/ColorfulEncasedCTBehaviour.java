package net.rarin.colorfulpipes.content;

import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.rarin.colorfulpipes.CCPSpriteShifts;
import net.rarin.colorfulpipes.content.encasedPipe.ColorfulEncasedPipeBlock;
import org.jetbrains.annotations.Nullable;

public class ColorfulEncasedCTBehaviour extends EncasedCTBehaviour {

	protected final DyeColor color;

	public ColorfulEncasedCTBehaviour(DyeColor color) {
		super(null);
		this.color = color;
	}

	@Override
	public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
		if (state.getBlock() instanceof ColorfulEncasedPipeBlock block) {
			return CCPSpriteShifts.COLORFUL_COPPER_CASING.get(color);
		}
		return null;
	}
}
