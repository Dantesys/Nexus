package org.dantesys.nexus.utilidade;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.dantesys.nexus.Nexus;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> ESSENCED = createTag("essenced");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Nexus.MODID, name));
        }
    }
}
