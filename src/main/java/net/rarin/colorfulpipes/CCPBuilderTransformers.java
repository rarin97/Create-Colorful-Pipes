package net.rarin.colorfulpipes;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SoundType;

import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class CCPBuilderTransformers {

	public static <B extends CasingBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> colorfulCasing(DyeColor color) {
		return b -> b.initialProperties(SharedProperties::stone)
				.properties(p -> p.sound(SoundType.COPPER))
				.transform(axeOrPickaxe())
				.blockstate((c, p) -> p.simpleBlock(c.get(), p.models().cubeAll(c.getName(),
						ColorfulPipes.asResource("block/copper_casing/" + color.getSerializedName()))))
				.onRegister(connectedTextures(() -> new SimpleCTBehaviour(CCPSpriteShifts.COLORFUL_COPPER_CASING.get(color))))
				.onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, CCPSpriteShifts.COLORFUL_COPPER_CASING.get(color))))
				.tag(AllTags.AllBlockTags.CASING.tag)
				.item()
				.tag(AllTags.AllItemTags.CASING.tag)
				.build();
	}
}
