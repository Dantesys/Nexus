package org.dantesys.nexus.eventos;


import com.mojang.blaze3d.platform.InputConstants;
import cpw.mods.util.Lazy;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.utilidade.ModModelProperty;

import static org.dantesys.nexus.utilidade.KeyMappings.HAB_MAPPING;
import static org.dantesys.nexus.utilidade.KeyMappings.SWAP_MAPPING;

@EventBusSubscriber(modid = Nexus.MODID,value = Dist.CLIENT)
public class ModEventsClient {
    @SubscribeEvent
    public static void registerSelectProperties(RegisterSelectItemModelPropertyEvent event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath(Nexus.MODID, "tipo_nexus"),
                ModModelProperty.TYPE
        );
    }
    @SubscribeEvent
    public static void registerKeyMapping(RegisterKeyMappingsEvent event){
        event.register(HAB_MAPPING.get());
        event.register(SWAP_MAPPING.get());
    }
}
