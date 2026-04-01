package net.rarin.colorfulpipes;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.contraptions.actors.psi.PSIVisual;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceRenderer;
import com.simibubi.create.content.decoration.steamWhistle.WhistleRenderer;
import com.simibubi.create.content.fluids.drain.ItemDrainRenderer;
import com.simibubi.create.content.fluids.pipes.GlassPipeVisual;
import com.simibubi.create.content.fluids.pipes.TransparentStraightPipeRenderer;
import com.simibubi.create.content.fluids.pipes.valve.FluidValveRenderer;
import com.simibubi.create.content.fluids.pipes.valve.FluidValveVisual;
import com.simibubi.create.content.fluids.pump.PumpRenderer;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.content.logistics.tableCloth.TableClothRenderer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.rarin.colorfulpipes.content.drain.ColorfulDrainBlockEntity;
import net.rarin.colorfulpipes.content.glassPipe.ColorfulGlassFluidPipeBlockEntity;
import net.rarin.colorfulpipes.content.pipe.ColorfulFluidPipeBlockEntity;
import net.rarin.colorfulpipes.content.portableFluidInterface.ColorfulPortableFluidInterfaceBlockEntity;
import net.rarin.colorfulpipes.content.pump.ColorfulPumpBlockEntity;
import net.rarin.colorfulpipes.content.smartPipe.ColorfulSmartFluidPipeBlockEntity;
import net.rarin.colorfulpipes.content.spout.ColorfulSpoutBlockEntity;
import net.rarin.colorfulpipes.content.spout.ColorfulSpoutRenderer;
import net.rarin.colorfulpipes.content.steamWhistle.ColorfulWhistleBlockEntity;
import net.rarin.colorfulpipes.content.table_cloth.ColorfulTableClothBlockEntity;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankBlockEntity;
import net.rarin.colorfulpipes.content.tank.ColorfulFluidTankRenderer;
import net.rarin.colorfulpipes.content.valve.ColorfulFluidValveBlockEntity;

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
			.visual(() -> FluidValveVisual::new)
			.validBlocks(CCPBlocks.COLORFUL_FLUID_VALVES.toArray())
			.renderer(() -> FluidValveRenderer::new)
			.register();

	public static final BlockEntityEntry<ColorfulFluidTankBlockEntity> COLORFUL_FLUID_TANKS = REGISTRATE
			.blockEntity("colorful_fluid_tank", ColorfulFluidTankBlockEntity::new)
			.validBlocks(CCPBlocks.COLORFUL_FLUID_TANKS.toArray())
			.renderer(() -> ColorfulFluidTankRenderer::new)
			.register();

//	public static final BlockEntityEntry<ColorfulHosePulleyBlockEntity> COLORFUL_HOSE_PULLEYS = REGISTRATE
//			.blockEntity("colorful_hose_pulley", ColorfulHosePulleyBlockEntity::new)
//			.visual(() -> ColorfulHosePulleyVisual::new)
//			.validBlocks(CCPBlocks.COLORFUL_HOSE_PULLEYS.toArray())
//			.renderer(() -> ColorfulHosePulleyRenderer::new)
//			.register();

	public static final BlockEntityEntry<ColorfulPortableFluidInterfaceBlockEntity> COLORFUL_PORTABLE_FLUID_INTERFACE = REGISTRATE
			.blockEntity("portable_fluid_interface", ColorfulPortableFluidInterfaceBlockEntity::new)
			.visual(() -> PSIVisual::new)
			.validBlocks(CCPBlocks.COLORFUL_FLUID_INTERFACES.toArray())
			.renderer(() -> PortableStorageInterfaceRenderer::new)
			.register();

//	public static final BlockEntityEntry<ColorfulSteamEngineBlockEntity> COLORFUL_STEAM_ENGINES = REGISTRATE
//			.blockEntity("colorful_steam_engine", ColorfulSteamEngineBlockEntity::new)
//			.visual(() -> SteamEngineVisual::new)
//			.validBlocks(CCPBlocks.COLORFUL_STEAM_ENGINES.toArray())
//			.renderer(() -> SteamEngineRenderer::new)
//			.register();

	public static final BlockEntityEntry<ColorfulWhistleBlockEntity> COLORFUL_STEAM_WHISTLES = REGISTRATE
			.blockEntity("colorful_steam_whistle", ColorfulWhistleBlockEntity::new)
			.validBlocks(CCPBlocks.COLORFUL_STEAM_WHISTLES.toArray())
			.renderer(() -> WhistleRenderer::new)
			.register();

	public static final BlockEntityEntry<ColorfulTableClothBlockEntity> COLORFUL_TABLE_CLOTH = REGISTRATE
			.blockEntity("colorful_table_cloth", ColorfulTableClothBlockEntity::new)
			.validBlocks(CCPBlocks.COLORFUL_TABLE_CLOTHS.toArray())
			.renderer(() -> TableClothRenderer::new)
			.register();

//	public static final BlockEntityEntry<ColorfulSlidingDoorBlockEntity> COLORFUL_SLIDING_DOOR = REGISTRATE
//			.blockEntity("colorful_sliding_door", ColorfulSlidingDoorBlockEntity::new)
//			.renderer(() -> SlidingDoorRenderer::new)
//			.validBlocks(CCPBlocks.COLORFUL_COPPER_DOOR.toArray())
//			.register();

	public static void register() {
	}
}
