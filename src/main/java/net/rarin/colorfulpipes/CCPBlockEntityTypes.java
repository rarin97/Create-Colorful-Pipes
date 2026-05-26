package net.rarin.colorfulpipes;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.decoration.steamWhistle.WhistleRenderer;
import com.simibubi.create.content.fluids.drain.ItemDrainRenderer;
import com.simibubi.create.content.fluids.pipes.GlassPipeVisual;
import com.simibubi.create.content.fluids.pipes.TransparentStraightPipeRenderer;
import com.simibubi.create.content.fluids.pump.PumpRenderer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogVisual;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlockEntity;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineRenderer;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineVisual;
import com.simibubi.create.content.logistics.tableCloth.TableClothRenderer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import net.minecraft.world.item.DyeColor;
import net.rarin.colorfulpipes.content.drain.ColorfulDrainBlockEntity;
import net.rarin.colorfulpipes.content.glassPipe.ColorfulGlassFluidPipeBlockEntity;
import net.rarin.colorfulpipes.content.hosePulley.ColorfulHosePulleyBlockEntity;
import net.rarin.colorfulpipes.content.hosePulley.ColorfulHosePulleyRenderer;
import net.rarin.colorfulpipes.content.hosePulley.ColorfulHosePulleyVisual;
import net.rarin.colorfulpipes.content.pipe.ColorfulFluidPipeBlockEntity;
import net.rarin.colorfulpipes.content.portableFluidInterface.ColorfulPSIVisual;
import net.rarin.colorfulpipes.content.portableFluidInterface.ColorfulPortableFluidInterfaceBlockEntity;
import net.rarin.colorfulpipes.content.portableFluidInterface.ColorfulPortableFluidInterfaceRenderer;
import net.rarin.colorfulpipes.content.pump.ColorfulPumpBlockEntity;
import net.rarin.colorfulpipes.content.slidingDoor.ColorfulSlidingDoorBlockEntity;
import net.rarin.colorfulpipes.content.slidingDoor.ColorfulSlidingDoorRenderer;
import net.rarin.colorfulpipes.content.smartPipe.ColorfulSmartFluidPipeBlockEntity;
import net.rarin.colorfulpipes.content.spout.ColorfulSpoutBlockEntity;
import net.rarin.colorfulpipes.content.spout.ColorfulSpoutRenderer;
import net.rarin.colorfulpipes.content.steamEngine.ColorfulSteamEngineBlockEntity;
import net.rarin.colorfulpipes.content.steamEngine.ColorfulSteamEngineRenderer;
import net.rarin.colorfulpipes.content.steamEngine.ColorfulSteamEngineVisual;
import net.rarin.colorfulpipes.content.steamWhistle.ColorfulWhistleBlockEntity;
import net.rarin.colorfulpipes.content.table_cloth.ColorfulTableClothBlockEntity;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankBlockEntity;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankRenderer;
import net.rarin.colorfulpipes.content.valve.ColorfulFluidValveBlockEntity;
import net.rarin.colorfulpipes.content.valve.ColorfulFluidValveRenderer;
import net.rarin.colorfulpipes.content.valve.ColorfulFluidValveVisual;

import java.util.EnumMap;
import java.util.Map;

public class CCPBlockEntityTypes {
	private static final CreateRegistrate REGISTRATE = ColorfulPipes.REGISTRATE;

	public static final BlockEntityEntry<ColorfulDrainBlockEntity> COLORFUL_DRAINS = REGISTRATE
			.blockEntity("colorful_item_drain", ColorfulDrainBlockEntity::new)
			.validBlocks(CCPBlocks.COLORFUL_DRAINS.toArray())
			.renderer(() -> ItemDrainRenderer::new)
			.register();

	public static final BlockEntityEntry<ColorfulPumpBlockEntity> COLORFUL_PUMPS = REGISTRATE
			.blockEntity("colorful_mechanical_pump", ColorfulPumpBlockEntity::new)
			.visual(() -> SingleAxisRotatingVisual.ofZ(AllPartialModels.MECHANICAL_PUMP_COG))
			.validBlocks(CCPBlocks.COLORFUL_PUMPS.toArray())
			.renderer(() -> PumpRenderer::new)
			.register();

	public static final BlockEntityEntry<ColorfulSpoutBlockEntity> COLORFUL_SPOUTS = REGISTRATE
			.blockEntity("colorful_spout", ColorfulSpoutBlockEntity::new)
			.validBlocks(CCPBlocks.COLORFUL_SPOUTS.toArray())
			.renderer(() -> ColorfulSpoutRenderer::new)
			.register();

	public static final BlockEntityEntry<ColorfulSmartFluidPipeBlockEntity> COLORFUL_SMART_FLUID_PIPES = REGISTRATE
			.blockEntity("colorful_smart_fluid_pipe", ColorfulSmartFluidPipeBlockEntity::new)
			.validBlocks(CCPBlocks.COLORFUL_SMART_FLUID_PIPES.toArray())
			.renderer(() -> SmartBlockEntityRenderer::new)
			.register();

	public static final BlockEntityEntry<ColorfulFluidPipeBlockEntity> COLORFUL_FLUID_PIPES = REGISTRATE
			.blockEntity("colorful_fluid_pipe", ColorfulFluidPipeBlockEntity::new)
			.validBlocks(CCPBlocks.COLORFUL_FLUID_PIPES.toArray())
			.register();

	public static final BlockEntityEntry<ColorfulFluidPipeBlockEntity> COLORFUL_ENCASED_FLUID_PIPES = REGISTRATE
			.blockEntity("colorful_encased_fluid_pipe", ColorfulFluidPipeBlockEntity::new)
			.validBlocks(CCPBlocks.COLORFUL_ENCASED_FLUID_PIPES.toArray())
			.register();

	public static final BlockEntityEntry<ColorfulGlassFluidPipeBlockEntity> COLORFUL_GLASS_FLUID_PIPES = REGISTRATE
			.blockEntity("colorful_glass_fluid_pipe", ColorfulGlassFluidPipeBlockEntity::new)
			.visual(() -> GlassPipeVisual::new, false)
			.validBlocks(CCPBlocks.COLORFUL_GLASS_FLUID_PIPES.toArray())
			.renderer(() -> TransparentStraightPipeRenderer::new)
			.register();

	public static final BlockEntityEntry<ColorfulFluidValveBlockEntity> COLORFUL_FLUID_VALVES = REGISTRATE
			.blockEntity("colorful_fluid_valve", ColorfulFluidValveBlockEntity::new)
			.visual(() -> ColorfulFluidValveVisual::new)
			.validBlocks(CCPBlocks.COLORFUL_FLUID_VALVES.toArray())
			.renderer(() -> ColorfulFluidValveRenderer::new)
			.register();


	public static final Map<DyeColor, BlockEntityEntry<ColorfulFluidTankBlockEntity>> COLORFUL_FLUID_TANKS = new EnumMap<>(DyeColor.class);

	static {
		for (DyeColor color : DyeColor.values()) {
			COLORFUL_FLUID_TANKS.put(color, REGISTRATE
					.blockEntity(color.getSerializedName() + "_fluid_tank", ColorfulFluidTankBlockEntity::new)
					.validBlocks(CCPBlocks.COLORFUL_FLUID_TANKS.toArray())
					.renderer(() -> ColorfulFluidTankRenderer::new)
					.register());
		}
	}

	public static final BlockEntityEntry<ColorfulHosePulleyBlockEntity> COLORFUL_HOSE_PULLEYS = REGISTRATE
			.blockEntity("colorful_hose_pulley", ColorfulHosePulleyBlockEntity::new)
			.visual(() -> ColorfulHosePulleyVisual::new)
			.validBlocks(CCPBlocks.COLORFUL_HOSE_PULLEYS.toArray())
			.renderer(() -> ColorfulHosePulleyRenderer::new)
			.register();

	public static final BlockEntityEntry<ColorfulPortableFluidInterfaceBlockEntity> COLORFUL_PORTABLE_FLUID_INTERFACE = REGISTRATE
			.blockEntity("portable_fluid_interface", ColorfulPortableFluidInterfaceBlockEntity::new)
			.visual(() -> ColorfulPSIVisual::new)
			.validBlocks(CCPBlocks.COLORFUL_FLUID_INTERFACES.toArray())
			.renderer(() -> ColorfulPortableFluidInterfaceRenderer::new)
			.register();

	public static final BlockEntityEntry<ColorfulSteamEngineBlockEntity> COLORFUL_STEAM_ENGINES = REGISTRATE
			.blockEntity("colorful_steam_engine", ColorfulSteamEngineBlockEntity::new)
			.visual(() -> ColorfulSteamEngineVisual::new)
			.validBlocks(CCPBlocks.COLORFUL_STEAM_ENGINES.toArray())
			.renderer(() -> ColorfulSteamEngineRenderer::new)
			.register();

	public static final BlockEntityEntry<ColorfulWhistleBlockEntity> COLORFUL_STEAM_WHISTLES = REGISTRATE
			.blockEntity("colorful_steam_whistle", ColorfulWhistleBlockEntity::new)
			.validBlocks(CCPPaletteBlocks.COLORFUL_STEAM_WHISTLES.toArray())
			.renderer(() -> WhistleRenderer::new)
			.register();

	public static final BlockEntityEntry<ColorfulTableClothBlockEntity> COLORFUL_TABLE_CLOTH = REGISTRATE
			.blockEntity("colorful_table_cloth", ColorfulTableClothBlockEntity::new)
			.validBlocks(CCPPaletteBlocks.COLORFUL_TABLE_CLOTHS.toArray())
			.renderer(() -> TableClothRenderer::new)
			.register();

	public static final BlockEntityEntry<ColorfulSlidingDoorBlockEntity> COLORFUL_SLIDING_DOOR = REGISTRATE
			.blockEntity("colorful_sliding_door", ColorfulSlidingDoorBlockEntity::new)
			.renderer(() -> ColorfulSlidingDoorRenderer::new)
			.validBlocks(CCPPaletteBlocks.COLORFUL_COPPER_DOOR.toArray())
			.register();

	public static final BlockEntityEntry<KineticBlockEntity> ENCASED_SHAFT = REGISTRATE
			.blockEntity("encased_shaft", KineticBlockEntity::new)
			.visual(() -> SingleAxisRotatingVisual::shaft, false)
			.validBlocks(CCPPaletteBlocks.COPPER_ENCASED_SHAFT, CCPPaletteBlocks.COPPER_GLASS_ENCASED_SHAFT, CCPPaletteBlocks.COPPER_TINTED_GLASS_ENCASED_SHAFT)
			.validBlocks(CCPPaletteBlocks.COLORFUL_COPPER_ENCASED_SHAFT.toArray())
			.validBlocks(CCPPaletteBlocks.COLORFUL_COPPER_GLASS_ENCASED_SHAFT.toArray())
			.validBlocks(CCPPaletteBlocks.COLORFUL_TINTED_COPPER_GLASS_ENCASED_SHAFT.toArray())
			.renderer(() -> ShaftRenderer::new)
			.register();

	public static final BlockEntityEntry<SimpleKineticBlockEntity> ENCASED_COGWHEEL = REGISTRATE
			.blockEntity("encased_cogwheel", SimpleKineticBlockEntity::new)
			.visual(() -> EncasedCogVisual::small, false)
			.validBlocks(CCPPaletteBlocks.COPPER_ENCASED_COGWHEEL)
			.validBlocks(CCPPaletteBlocks.COLORFUL_COPPER_ENCASED_COGWHEEL.toArray())
			.renderer(() -> EncasedCogRenderer::small)
			.register();

	public static final BlockEntityEntry<SimpleKineticBlockEntity> ENCASED_LARGE_COGWHEEL = REGISTRATE
			.blockEntity("encased_large_cogwheel", SimpleKineticBlockEntity::new)
			.visual(() -> EncasedCogVisual::large, false)
			.validBlocks(CCPPaletteBlocks.COPPER_ENCASED_LARGE_COGWHEEL)
			.validBlocks(CCPPaletteBlocks.COLORFUL_COPPER_ENCASED_LARGE_COGWHEEL.toArray())
			.renderer(() -> EncasedCogRenderer::large)
			.register();

	public static void register() {
	}
}
