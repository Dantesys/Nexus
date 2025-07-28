package org.dantesys.nexus.eventos;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.utilidade.Essenced;

import static org.dantesys.nexus.utilidade.KeyMappings.HAB_MAPPING;

@EventBusSubscriber(modid = Nexus.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void tickPlayerPre(PlayerTickEvent.Pre event){
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
    @SubscribeEvent
    public static void tickPlayerPost(PlayerTickEvent.Post event){
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        if(stack.getItem() instanceof Essenced essenced){
            if(HAB_MAPPING.get().consumeClick() && !player.getCooldowns().isOnCooldown(stack)){
                essenced.habilidade(player,stack);
            }
        }

    }
}
