package org.dantesys.nexus.utilidade;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.dantesys.nexus.componentes.DataComponentes;
import org.dantesys.nexus.efeitos.ModEfeitos;

public interface Essenced {
    default void setTipo(ItemStack stack, int tipo){
        if(stack.is(ModTags.Items.ESSENCED) || stack.getItem() instanceof Essenced){
            stack.set(DataComponentes.ESSENCIA_TIPO,tipo);
        }
    }
    default int getTipo(ItemStack stack) {
        return stack.getOrDefault(DataComponentes.ESSENCIA_TIPO,0);
    }
    default void habilidade(Player player, ItemStack stack){}
    default void passiva(Player player, ItemStack stack){
        int tipo = stack.getOrDefault(DataComponentes.ESSENCIA_TIPO,0);
        switch (tipo){
            case 1 -> player.addEffect(new MobEffectInstance(ModEfeitos.SOLAR_EFFECT,600));
            case 2 -> player.addEffect(new MobEffectInstance(ModEfeitos.LUNAR_EFFECT,600));
            case 3 -> player.addEffect(new MobEffectInstance(ModEfeitos.NETHER_EFFECT,600));
            case 4 -> player.addEffect(new MobEffectInstance(ModEfeitos.ENDER_EFFECT,600));
        }
    }
}
