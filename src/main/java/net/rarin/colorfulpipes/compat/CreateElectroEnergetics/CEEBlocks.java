package net.rarin.colorfulpipes.compat.CreateElectroEnergetics;

import com.george_vi.electroenergetics.CreateElectroEnergetics;
import com.george_vi.electroenergetics.content.electric_pump.ElectricPumpBlock;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.rarin.colorfulpipes.CCPCreativeTabs;
import net.rarin.colorfulpipes.CCPTags;
import net.rarin.colorfulpipes.ColorfulPipes;
import net.rarin.colorfulpipes.compat.CreateElectroEnergetics.content.ColorfulElectricPumpBlock;
import net.rarin.colorfulpipes.compat.CreateElectroEnergetics.content.ElectroEnergeticsItem;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class CEEBlocks {
	private static final CreateRegistrate REGISTRATE = ColorfulPipes.REGISTRATE;

	static {
		REGISTRATE.setCreativeTab(CCPCreativeTabs.MAIN);
	}

	public static final DyedBlockList<ColorfulElectricPumpBlock> COLORFUL_ELECTRIC_PUMPS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_electric_pump", p -> new ColorfulElectricPumpBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly()).asOptional()
				.blockstate((c, p) -> {
					var block = p.models().withExistingParent(c.getName(), CreateElectroEnergetics.rl("block/electric_pump/block"))
							.texture("4", ColorfulPipes.asResource("block/electrical_pump/electrical_pump_" + colorName))
							.texture("particle", ColorfulPipes.asResource("block/electrical_pump/electrical_pump_" + colorName));
					var roll = p.models()
							.withExistingParent(c.getName() + "_roll", CreateElectroEnergetics.rl("block/electric_pump/block_roll"))
							.texture("4", ColorfulPipes.asResource("block/electrical_pump/electrical_pump_" + colorName))
							.texture("particle", ColorfulPipes.asResource("block/electrical_pump/electrical_pump_" + colorName));
					p.getVariantBuilder(c.get())
							.forAllStatesExcept(state -> {
								var facing = state.getValue(ElectricPumpBlock.FACING);
								var rolled = state.getValue(ElectricPumpBlock.ROLL);

								var model = rolled ? roll : block;

								return ConfiguredModel.builder().modelFile(model)
										.rotationX(facing == Direction.DOWN ? 180 : facing.getAxis().isHorizontal() ? 90 : 0)
										.rotationY(switch (facing) {
											case EAST -> 90;
											case SOUTH -> 180;
											case WEST -> 270;
											default -> 0;
										})
										.build();
							});
				})
				.tag(AllTags.AllBlockTags.SAFE_NBT.tag).asOptional()
				.tag(CCPTags.ColorfulBlockTags.COLORFUL_ELECTRIC_PUMPS.tag).asOptional()
				.item(ElectroEnergeticsItem::new)
				.tag(CCPTags.ColorfulItemTags.ELECTRIC_PUMPS.tag).asOptional()
				.tag(CCPTags.ColorfulItemTags.COLORFUL_ELECTRIC_PUMPS.tag).asOptional()
				.build()
				.register();
	});

	public static void register() {
	}

//	public static final BlockEntry<ElectricPumpBlock> ELECTRIC_PUMP = REGISTRATE.block("electric_pump", ElectricPumpBlock::new)
//			.tag(AllTags.AllBlockTags.SAFE_NBT.tag)
//			.initialProperties(SharedProperties::copperMetal)
//			.properties(p -> p.mapColor(MapColor.COLOR_GRAY))
//			.blockstate((c, p) -> p.directionalBlock(c.get(), bs ->
//					bs.getValue(ElectricPumpBlock.ROLL) ? AssetLookup.partialBaseModel(c, p, "roll") : AssetLookup.partialBaseModel(c, p)))
//			.transform(pickaxeOnly())
//			.item()
//			.model((c, p) -> p.blockItem(c::getEntry, "/block"))
//			.build()
//			.register();
}
