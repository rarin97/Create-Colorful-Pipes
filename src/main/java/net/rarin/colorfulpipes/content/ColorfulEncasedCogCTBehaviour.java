package net.rarin.colorfulpipes.content;

import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;

import net.createmod.catnip.data.Couple;
import net.minecraft.world.item.DyeColor;

public class ColorfulEncasedCogCTBehaviour extends EncasedCogCTBehaviour {

	protected final DyeColor color;
	private Couple<CTSpriteShiftEntry> sideShifts;
	private boolean large;

	public ColorfulEncasedCogCTBehaviour(CTSpriteShiftEntry shift, DyeColor color) {
		super(shift,null);
		this.color = color;
	}

	public ColorfulEncasedCogCTBehaviour(CTSpriteShiftEntry shift, Couple<CTSpriteShiftEntry> sideShifts, DyeColor color) {
		super(shift);
		large = sideShifts == null;
		this.sideShifts = sideShifts;
		this.color = color;
	}
}
