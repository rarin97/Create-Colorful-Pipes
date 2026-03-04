package net.rarin.colorfulpipes.content.steamEngine;

import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;

import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlockEntity;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.rarin.colorfulpipes.CCPBlockEntityTypes;

public class ColorfulSteamEngineBlock extends SteamEngineBlock {

	protected final DyeColor color;

	public ColorfulSteamEngineBlock(Properties properties, DyeColor color) {
		super(properties);
		this.color = color;
	}

	public BlockEntityType<? extends SteamEngineBlockEntity> getBlockEntityType() {
		return CCPBlockEntityTypes.COLORFUL_STEAM_ENGINES.get();
	}
}
