package org.dantesys.nexus.itens;

import net.minecraft.world.item.ToolMaterial;
import org.dantesys.nexus.utilidade.ModTags;

public class ModTiers {
    public static final ToolMaterial NEXUS_TIER = new ToolMaterial(
            ToolMaterial.NETHERITE.incorrectBlocksForDrops(),
            3072,
            10.5f,
            5.0f,
            25,
            ModTags.Items.ESSENCED
    );
}
