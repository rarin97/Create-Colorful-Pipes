package net.rarin.colorfulpipes.compat.CreateElectroEnergetics;

import com.george_vi.electroenergetics.content.electric_pump.ElectricPumpBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static net.rarin.colorfulpipes.ColorfulPipes.REGISTRATE;

public class CEEBlockEntityTypes {

	public static final BlockEntityEntry<ElectricPumpBlockEntity> ELECTRIC_PUMP = REGISTRATE.blockEntity("electric_pump", ElectricPumpBlockEntity::new)
			.validBlocks(CEEBlocks.COLORFUL_ELECTRIC_PUMPS.toArray())
			.register();

	public static void register() {
	}
}
