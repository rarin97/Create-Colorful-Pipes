package net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry;

import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.rarin.colorfulpipes.CCPBlocks;
import net.rarin.colorfulpipes.CCPCreativeTabs;
import net.rarin.colorfulpipes.ColorfulPipes;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content.ColorfulExperienceHatchBlock;
import net.rarin.colorfulpipes.CCPTags.ColorfulItemTags;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content.ColorfulExperienceLanternBlock;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content.ColorfulGrindstoneDrainBlock;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content.ColorfulPrinterBlock;
import net.rarin.colorfulpipes.compat.CreateEnchantmentIndustry.content.EnchantmentIndustryItem;
import plus.dragons.createenchantmentindustry.common.fluids.lantern.ExperienceLanternMovementBehaviour;
import plus.dragons.createenchantmentindustry.common.registry.CEIMountedStorageTypes;

import static com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType.mountedFluidStorage;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static plus.dragons.createenchantmentindustry.common.registry.CEIBlocks.MECHANICAL_GRINDSTONE;

public class CEIBlocks {
	private static final CreateRegistrate REGISTRATE = ColorfulPipes.REGISTRATE;

	static {
		REGISTRATE.setCreativeTab(CCPCreativeTabs.MAIN);
	}

	public static final DyedBlockList<ColorfulExperienceHatchBlock> COLORFUL_EXPERIENCE_HATCHES = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_experience_hatch", p -> new ColorfulExperienceHatchBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly()).asOptional()
				.blockstate((c, p) ->
						p.horizontalBlock(c.get(), p.models().withExistingParent(c.getName(), ColorfulPipes.asResource("block/experience_hatch"))
								.texture("base", ColorfulPipes.asResource("block/experience_hatch/" + colorName))
								.texture("particle", ColorfulPipes.asResource("block/item_drain/" + colorName)))
				)
				.simpleItem()
				.item(EnchantmentIndustryItem::new)
				.tag(ColorfulItemTags.EXPERIENCE_HATCHES.tag).asOptional()
				.tag(ColorfulItemTags.COLORFUL_EXPERIENCE_HATCHES.tag).asOptional()
				.build()
				.register();
	});

	public static final DyedBlockList<ColorfulExperienceLanternBlock> COLORFUL_EXPERIENCE_LANTERNS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_experience_lantern", p -> new ColorfulExperienceLanternBlock(p, color))
				.initialProperties(SharedProperties::softMetal)
				.properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GREEN))
				.transform(pickaxeOnly()).asOptional()
				.transform(mountedFluidStorage(CEIMountedStorageTypes.EXPERIENCE_LANTERN))
				.onRegister(block -> MovementBehaviour.REGISTRY.register(block, new ExperienceLanternMovementBehaviour()))
				.addLayer(() -> RenderType::cutoutMipped)
				.blockstate((c, p) ->
						p.directionalBlock(c.get(), p.models().withExistingParent(c.getName(), ColorfulPipes.asResource("block/experience_lantern"))
								.texture("1", ColorfulPipes.asResource("block/experience_lantern_bottom/" + colorName))
								.texture("2", ColorfulPipes.asResource("block/experience_lantern_side/" + colorName))
								.texture("particle", ColorfulPipes.asResource("block/experience_lantern_side/" + colorName)))
				)
				.simpleItem()
				.item(EnchantmentIndustryItem::new)
				.tag(ColorfulItemTags.EXPERIENCE_LANTERNS.tag).asOptional()
				.tag(ColorfulItemTags.COLORFUL_EXPERIENCE_LANTERNS.tag).asOptional()
				.build()
				.register();
	});

	public static final DyedBlockList<ColorfulPrinterBlock> COLORFUL_PRINTERS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_printer", p -> new ColorfulPrinterBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(color.getMapColor()))
				.transform(pickaxeOnly()).asOptional()
				.blockstate((c, p) ->
						p.horizontalBlock(c.get(), p.models().withExistingParent(c.getName(), ColorfulPipes.asResource("block/printer/block"))
								.texture("tank", ColorfulPipes.asResource("block/spout/" + colorName))
								.texture("tank_bottom", ColorfulPipes.asResource("block/encased_pipe/" + colorName))
								.texture("filter", ColorfulPipes.asResource("block/smart_pipe_2/" + colorName))
								.texture("particle", ColorfulPipes.asResource("block/encased_pipe/" + colorName)))
				)
				.item(EnchantmentIndustryItem::new)
				.tag(ColorfulItemTags.PRINTERS.tag).asOptional()
				.tag(ColorfulItemTags.COLORFUL_PRINTERS.tag).asOptional()
				.model((c, p) ->
						p.withExistingParent(c.getName(), ColorfulPipes.asResource("block/printer/item"))
								.texture("tank", ColorfulPipes.asResource("block/spout/" + colorName))
								.texture("tank_bottom", ColorfulPipes.asResource("block/encased_pipe/" + colorName))
								.texture("filter", ColorfulPipes.asResource("block/smart_pipe_2/" + colorName))
								.texture("nozzle", ColorfulPipes.asResource("block/spout_nozzle/" + colorName))
								.texture("hose", ColorfulPipes.asResource("block/hose/" + colorName)))
				.build()
				.register();
	});


	public static final DyedBlockList<ColorfulGrindstoneDrainBlock> COLORFUL_GRINDSTONE_DRAIN = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_grindstone_drain", p -> new ColorfulGrindstoneDrainBlock(p, color))
				.initialProperties(SharedProperties::copperMetal)
				.onRegister((block) -> BlockStressValues.IMPACTS.register(block, () -> 4.0))
				.transform(pickaxeOnly()).asOptional()
				.blockstate((c, p) ->
						p.horizontalBlock(c.get(), p.models().withExistingParent(c.getName(), ColorfulPipes.asResource("block/grindstone_drain/block"))
								.texture("front", ColorfulPipes.asResource("block/mechanical_grinder_front/" + colorName))
								.texture("back", ColorfulPipes.asResource("block/mechanical_grinder_back/" + colorName))
								.texture("side", ColorfulPipes.asResource("block/item_drain/" + colorName))
								.texture("bottom", ColorfulPipes.asResource("block/copper_underside/" + colorName))
								.texture("inner", ColorfulPipes.asResource("block/pump/" + colorName))
								.texture("particle", ColorfulPipes.asResource("block/item_drain/" + colorName)))
				)
				.loot((loots, block) -> loots.add(block, LootTable.lootTable()
						.withPool(loots.applyExplosionCondition(plus.dragons.createenchantmentindustry.common.registry.CEIBlocks.MECHANICAL_GRINDSTONE, LootPool.lootPool()
								.setRolls(ConstantValue.exactly(1.0F))
								.add(LootItem.lootTableItem(MECHANICAL_GRINDSTONE))))
						.withPool(loots.applyExplosionCondition(CCPBlocks.COLORFUL_DRAINS.get(color), LootPool.lootPool()
								.setRolls(ConstantValue.exactly(1.0F))
								.add(LootItem.lootTableItem(CCPBlocks.COLORFUL_DRAINS.get(color)))))))
//				.item()
//				.model((c, p) ->
//						p.withExistingParent(c.getName(), ColorfulPipes.asResource("block/grindstone_drain/item"))
//								.texture("front", ColorfulPipes.asResource("block/mechanical_grinder_front/" + colorName))
//								.texture("back", ColorfulPipes.asResource("block/mechanical_grinder_back/" + colorName))
//								.texture("side", ColorfulPipes.asResource("block/item_drain/" + colorName))
//								.texture("bottom", ColorfulPipes.asResource("block/copper_underside/" + colorName))
//								.texture("inner", ColorfulPipes.asResource("block/pump/" + colorName))
//								.texture("particle", ColorfulPipes.asResource("block/item_drain/" + colorName)))
//				.build()
				.register();
	});

	public static void register() {
	}
}
