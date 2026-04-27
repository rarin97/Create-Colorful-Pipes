package net.rarin.colorfulpipes;

import com.simibubi.create.Create;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import static net.rarin.colorfulpipes.CCPTags.NameSpace.CREATE;

public class CCPTags {
	public enum NameSpace {

		MOD(ColorfulPipes.ID),
		COMMON("c"),
		CREATE(Create.ID);

		public final String id;

		NameSpace(String id) {
			this.id = id;
		}

		public ResourceLocation id(String path) {
			return ResourceLocation.fromNamespaceAndPath(this.id, path);
		}

		public ResourceLocation id(Enum<?> entry, @Nullable String pathOverride) {
			return this.id(pathOverride != null ? pathOverride : Lang.asId(entry.name()));
		}
	}

	public enum ColorfulItemTags {
		COLORFUL_COPPER_CASINGS,
		COLORFUL_COPPER_GLASS_CASINGS,
		COLORFUL_COPPER_TINTED_GLASS_CASINGS,
		COLORFUL_PIPES,
		COLORFUL_SMART_PIPES,
		COLORFUL_FLUID_VALVES,
		COLORFUL_PUMPS,
		COLORFUL_FLUID_TANKS,
		COLORFUL_HOSE_PULLEYS,
		COLORFUL_DRAINS,
		COLORFUL_SPOUTS,
		COLORFUL_PORTABLE_FLUID_INTERFACES,
		COLORFUL_STEAM_ENGINES,
		COLORFUL_STEAM_WHISTLES,
		COLORFUL_TABLE_CLOTHS,
		COLORFUL_COPPER_SCAFFOLDS,
		COLORFUL_COPPER_LADDERS,
		COLORFUL_COPPER_BARS,
		COLORFUL_COPPER_DOORS,

		FLUID_PIPES(CREATE),
		COPPER_CASINGS(CREATE),
		COPPER_GLASS_CASINGS,
		COPPER_TINTED_GLASS_CASINGS,
		MECHANICAL_PUMPS(CREATE),
		SMART_FLUID_PIPES(CREATE),
		FLUID_VALVES(CREATE),
		FLUID_TANKS(CREATE),
		ITEM_DRAINS(CREATE),
		SPOUTS(CREATE),
		PORTABLE_FLUID_INTERFACES(CREATE),
		HOSE_PULLEYS(CREATE),
		STEAM_WHISTLES(CREATE),
		COPPER_SCAFFOLDS(CREATE),
		COPPER_TABLE_CLOTHS(CREATE),
		COPPER_LADDERS(CREATE),
		COPPER_BARS(CREATE),
		COPPER_DOORS(CREATE);

		public final TagKey<Item> tag;

		ColorfulItemTags() {
			this(NameSpace.MOD, null);
		}

		ColorfulItemTags(NameSpace namespace) {
			this(namespace, null);
		}

		ColorfulItemTags(NameSpace namespace, @Nullable String pathOverride) {
			this.tag = TagKey.create(Registries.ITEM, namespace.id(this, pathOverride));
		}
	}

		public enum ColorfulBlockTags {
			COLORFUL_COPPER_CASINGS,
			COLORFUL_COPPER_GLASS_CASINGS,
			COLORFUL_COPPER_TINTED_GLASS_CASINGS,
			COLORFUL_PIPES,
			COLORFUL_SMART_PIPES,
			COLORFUL_FLUID_VALVES,
			COLORFUL_PUMPS,
			COLORFUL_FLUID_TANKS,
			COLORFUL_HOSE_PULLEYS,
			COLORFUL_DRAINS,
			COLORFUL_SPOUTS,
			COLORFUL_PORTABLE_FLUID_INTERFACES,
			COLORFUL_STEAM_ENGINES,
			COLORFUL_STEAM_WHISTLES,
			COLORFUL_TABLE_CLOTHS,
			COLORFUL_COPPER_SCAFFOLDS,
			COLORFUL_COPPER_LADDERS,
			COLORFUL_COPPER_BARS,
			COLORFUL_COPPER_DOORS;

			public final TagKey<Block> tag;

			ColorfulBlockTags() {
				this(NameSpace.MOD, null);
			}

			ColorfulBlockTags(NameSpace namespace) {
				this(namespace, null);
			}

			ColorfulBlockTags(NameSpace namespace, @Nullable String pathOverride) {
				this.tag = TagKey.create(Registries.BLOCK, namespace.id(this, pathOverride));
			}
		}

}
