package net.rarin.colorfulpipes;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SoundType;

import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class CCPBuilderTransformers {

	public static <B extends CasingBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> colorfulcasing(DyeColor color) {
		return b -> b.initialProperties(SharedProperties::stone)
				.properties(p -> p.sound(SoundType.WOOD))
				.transform(axeOrPickaxe())
				.transform(BuilderTransformers.casing(() -> CCPSpriteShifts.COLORFUL_COPPER_CASING.get(color)))
				.tag(AllTags.AllBlockTags.CASING.tag)
				.item()
				.tag(AllTags.AllItemTags.CASING.tag)
				.build();
	}
}
