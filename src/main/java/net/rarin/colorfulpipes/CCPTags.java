package net.rarin.colorfulpipes;

import net.createmod.catnip.lang.Lang;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import net.minecraft.world.item.Item;

import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.Nullable;

public class CCPTags {
	public static <T> TagKey<T> optionalTag(Registry<T> registry,
											ResourceLocation id) {
		return TagKey.create(registry.key(), id);
	}

	public enum NameSpace {

		MOD(ColorfulPipes.ID);

		public final String id;

		NameSpace(String id) {
			this.id = id;
		}

		public ResourceLocation id(String path) {
			return new ResourceLocation(this.id, path);
		}

		public ResourceLocation id(Enum<?> entry, @Nullable String pathOverride) {
			return this.id(pathOverride != null ? pathOverride : Lang.asId(entry.name()));
		}
	}

	public enum ColorfulItemTags {
		COLORFUL_PIPES,
		COLORFUL_SMART_PIPES,
		COLORFUL_FLUID_VALVES,
		COLORFUL_PUMPS,
		COLORFUL_FLUID_TANKS,
		COLORFUL_HOSE_PULLEYS,
		COLORFUL_DRAINS,
		COLORFUL_SPOUTS,
		COLORFUL_STEAM_ENGINES,
		COLORFUL_STEAM_WHISTLES;

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

			COLORFUL_PIPES,
			COLORFUL_SMART_PIPES,
			COLORFUL_FLUID_VALVES,
			COLORFUL_PUMPS,
			COLORFUL_FLUID_TANKS,
			COLORFUL_HOSE_PULLEYS,
			COLORFUL_DRAINS,
			COLORFUL_SPOUTS,
			COLORFUL_STEAM_ENGINES,
			COLORFUL_STEAM_WHISTLES;

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
