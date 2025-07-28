package org.dantesys.nexus.itens;

import net.minecraft.world.item.ToolMaterial;
import net.neoforged.neoforge.common.Tags;

public class ModTiers {
    public static final ToolMaterial NEXUS_TIER = new ToolMaterial(
            ToolMaterial.NETHERITE.incorrectBlocksForDrops(),
            3072,
            10.5f,
            5.0f,
            25,
            Tags.Items.INGOTS_NETHERITE
    );
}
