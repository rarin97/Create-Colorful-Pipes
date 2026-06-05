package net.rarin.colorfulpipes.compat.CreateDragonsPlus;

import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import net.rarin.colorfulpipes.CCPCreativeTabs;
import net.rarin.colorfulpipes.CCPTags.ColorfulBlockTags;
import net.rarin.colorfulpipes.ColorfulPipes;
import net.rarin.colorfulpipes.compat.CreateDragonsPlus.content.ColorfulFluidHatchBlock;
import net.rarin.colorfulpipes.CCPTags.ColorfulItemTags;
import net.rarin.colorfulpipes.compat.CreateDragonsPlus.content.DragonsPlusItem;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class CDPBlocks {
	private static final CreateRegistrate REGISTRATE = ColorfulPipes.REGISTRATE;

	static {
		REGISTRATE.setCreativeTab(CCPCreativeTabs.MAIN);
	}

	public static final DyedBlockList<ColorfulFluidHatchBlock> COLORFUL_FLUID_HATCHES = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_fluid_hatch", p -> new ColorfulFluidHatchBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly()).asOptional()
				.blockstate((c, p) ->
						p.horizontalBlock(c.get(), p.models().withExistingParent(c.getName(), ColorfulPipes.asResource("block/fluid_hatch"))
								.texture("base", ColorfulPipes.asResource("block/fluid_hatch/" + colorName))
								.texture("particle", ColorfulPipes.asResource("block/item_drain/" + colorName)))
				)
				.tag(ColorfulBlockTags.COLORFUL_FLUID_HATCHES.tag)
				.simpleItem()
				.item(DragonsPlusItem::new)
				.tag(ColorfulItemTags.FLUID_HATCHES.tag).asOptional()
				.tag(ColorfulItemTags.COLORFUL_FLUID_HATCHES.tag).asOptional()
				.build()
				.register();
	});

	public static void register() {
	}
}
