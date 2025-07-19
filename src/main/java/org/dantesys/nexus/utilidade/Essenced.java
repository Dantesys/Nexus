package org.dantesys.nexus.utilidade;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import org.dantesys.nexus.componentes.DataComponentes;
import org.dantesys.nexus.efeitos.ModEfeitos;

public interface Essenced {
    default void setTipo(ItemStack stack, int tipo){
        if(stack.is(ModTags.Items.ESSENCED)){
            stack.set(DataComponentes.ESSENCIA_TIPO,tipo);
        }
    }
    default int getTipo(ItemStack stack) {
        if(stack.is(ModTags.Items.ESSENCED)){
            return stack.getOrDefault(DataComponentes.ESSENCIA_TIPO,0);
        }
        return 0;
    }
    default void habilidade(ServerPlayer player, ItemStack stack){}
    default void passiva(ServerPlayer player, ItemStack stack){
        int tipo = stack.getOrDefault(DataComponentes.ESSENCIA_TIPO,0);
        switch (tipo){
            case 1 -> player.addEffect(new MobEffectInstance(ModEfeitos.SOLAR_EFFECT,600));
        }
    }
}
