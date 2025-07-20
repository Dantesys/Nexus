package org.dantesys.nexus.eventos;


import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.utilidade.ModModelProperty;

@EventBusSubscriber(modid = Nexus.MODID,value = Dist.CLIENT)
public class ModEventsClient {
    @SubscribeEvent
    public static void registerSelectProperties(RegisterSelectItemModelPropertyEvent event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath(Nexus.MODID, "tipo_nexus"),
                ModModelProperty.TYPE
        );
    }
}
