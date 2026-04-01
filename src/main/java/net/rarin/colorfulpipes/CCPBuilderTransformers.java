package net.rarin.colorfulpipes;

import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.behaviour.DoorMovingInteraction;
import com.simibubi.create.content.decoration.MetalScaffoldingBlockItem;
import com.simibubi.create.content.decoration.MetalScaffoldingCTBehaviour;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorBlock;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorMovementBehaviour;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockItem;
import com.simibubi.create.content.logistics.tableCloth.TableClothModel;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.rarin.colorfulpipes.content.ColorfulEncasedCTBehaviour;
import net.rarin.colorfulpipes.CCPTags.ColorfulItemTags;
import net.rarin.colorfulpipes.CCPTags.ColorfulBlockTags;

import static com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour.interactionBehaviour;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class CCPBuilderTransformers {

	public static <B extends CasingBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> colorfulCasing(DyeColor color) {
		return b -> b.initialProperties(SharedProperties::stone)
				.properties(p -> p.sound(SoundType.COPPER))
				.transform(axeOrPickaxe())
				.blockstate((c, p) -> p.simpleBlock(c.get(), p.models().cubeAll(c.getName(),
								ColorfulPipes.asResource("block/copper_casing/" + color.getSerializedName()))))
				.onRegister(connectedTextures(() ->new ColorfulEncasedCTBehaviour(color)))
				.onRegister(casingConnectivity((block, cc) -> cc.make(block, CCPSpriteShifts.COLORFUL_COPPER_CASING.get(color))))
				.tag(AllTags.AllBlockTags.CASING.tag)
				.item()
				.tag(AllTags.AllItemTags.CASING.tag)
				.build();
	}

	public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfultableCloth(DyeColor color,
																						   NonNullSupplier<? extends Block> initialProps, boolean dyed) {
		return b -> {
			TagKey<Block> soundTag = dyed ? BlockTags.COMBINATION_STEP_SOUND_BLOCKS : BlockTags.INSIDE_STEP_SOUND_BLOCKS;

			ItemBuilder<TableClothBlockItem, BlockBuilder<B, P>> item = b.initialProperties(initialProps)
					.addLayer(() -> RenderType::cutoutMipped)
					.blockstate((c, p) -> p.simpleBlock(c.get(), p.models()
							.withExistingParent(c.getName(), Create.asResource("block/table_cloth/block"))
							.texture("0", ColorfulPipes.asResource("block/table_cloth/" + color.getSerializedName()))))
					.onRegister(CreateRegistrate.blockModel(() -> TableClothModel::new))
					.tag(AllTags.AllBlockTags.TABLE_CLOTHS.tag, soundTag)
					.tag(ColorfulBlockTags.COLORFUL_TABLE_CLOTHS.tag)
					.onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.table_cloth"))
					.item(TableClothBlockItem::new);

			return item.model((c, p) -> p.withExistingParent(c.getName(), Create.asResource("block/table_cloth/item"))
					.texture("0", ColorfulPipes.asResource("block/table_cloth/" + color.getSerializedName())))
					.tag(AllTags.AllItemTags.TABLE_CLOTHS.tag)
					.tag(ColorfulItemTags.COLORFUL_TABLE_CLOTHS.tag)
					.tag(ColorfulItemTags.COPPER_TABLE_CLOTHS.tag)
					.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
							.requires(c.get())
							.unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
							.save(p, ColorfulPipes.asResource("copper_table_cloth/" + c.getName() + "_clear")))
					.build();
		};
	}

	public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfulladder(DyeColor color) {
		return b -> b.initialProperties(() -> Blocks.LADDER)
				.addLayer(() -> RenderType::cutout)
				.properties(p -> p.mapColor(color))
				.blockstate((c, p) -> p.horizontalBlock(c.get(), p.models()
						.withExistingParent(c.getName(), Create.asResource("block/ladder"))
						.texture("0", ColorfulPipes.asResource("block/ladder_hoop/" + color ))
						.texture("1", ColorfulPipes.asResource("block/ladder/" + color))
						.texture("particle",ColorfulPipes.asResource("block/ladder/" + color))))
				.properties(p -> p.sound(SoundType.COPPER))
				.transform(pickaxeOnly())
				.tag(BlockTags.CLIMBABLE)
				.tag(ColorfulBlockTags.COLORFUL_COPPER_LADDERS.tag)
				.item()
				.tag(ColorfulItemTags.COLORFUL_COPPER_LADDERS.tag)
				.tag(ColorfulItemTags.COPPER_LADDERS.tag)
				.model((c, p) -> p.blockSprite(c::get, p.modLoc("block/ladder/" + color)))
				.build();
	}

	public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfulscaffold(CTSpriteShiftEntry scaffoldShift, CTSpriteShiftEntry scaffoldInsideShift, CTSpriteShiftEntry casingShift) {
		return b -> b.initialProperties(() -> Blocks.SCAFFOLDING)
				.properties(p -> p.sound(SoundType.COPPER))
				.addLayer(() -> RenderType::cutout)
				.onRegister(connectedTextures(() -> new MetalScaffoldingCTBehaviour(scaffoldShift, scaffoldInsideShift, casingShift)))
				.transform(pickaxeOnly())
				.tag(BlockTags.CLIMBABLE)
				.item(MetalScaffoldingBlockItem::new)
				.tag(ColorfulItemTags.COLORFUL_COPPER_SCAFFOLDS.tag)
				.tag(ColorfulItemTags.COPPER_SCAFFOLDS.tag)
				.build();
	}


	public static <B extends SlidingDoorBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> colorfulslidingDoor(String type) {
		return b -> b.initialProperties(() -> Blocks.IRON_DOOR)
				.properties(p -> p.requiresCorrectToolForDrops().strength(3.0F, 6.0F))
				.blockstate((c, p) -> {
					ModelFile bottom = AssetLookup.partialBaseModel(c, p, "bottom");
					ModelFile top = AssetLookup.partialBaseModel(c, p, "top");
					p.doorBlock(c.get(), bottom, bottom, bottom, bottom, top, top, top, top);
				})
				.addLayer(() -> RenderType::cutoutMipped)
				.transform(pickaxeOnly())
				.onRegister(interactionBehaviour(new DoorMovingInteraction()))
				.onRegister(movementBehaviour(new SlidingDoorMovementBehaviour()))
				.tag(BlockTags.DOORS)
				.tag(BlockTags.WOODEN_DOORS) // for villager AI
				.tag(AllTags.AllBlockTags.NON_DOUBLE_DOOR.tag)
				.loot((lr, block) -> lr.add(block, lr.createDoorTable(block)))
				.item()
				.tag(ItemTags.DOORS)
				.tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
				.model((c, p) -> p.blockSprite(c, Create.asResource("item/" + type + "_door")))
				.build();
	}



}
