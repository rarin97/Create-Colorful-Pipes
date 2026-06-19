package net.rarin.colorfulpipes.config;

import net.createmod.catnip.config.ConfigBase;

public class CCPCommon extends ConfigBase {

	public final CCPFeatures toggle = nested(0, CCPFeatures::new);

	@Override
	public String getName() {
		return "common";
	}

}
