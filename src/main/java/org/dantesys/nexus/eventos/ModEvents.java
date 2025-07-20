package org.dantesys.nexus.eventos;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.utilidade.Essenced;

@EventBusSubscriber(modid = Nexus.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void tickPlayer(PlayerTickEvent.Pre event){
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        if(stack.getItem() instanceof Essenced essenced){
            essenced.passiva(player,stack);
        }else{
            stack = player.getOffhandItem();
            if(stack.getItem() instanceof Essenced essenced){
                essenced.passiva(player,stack);
            }
        }
    }
}
