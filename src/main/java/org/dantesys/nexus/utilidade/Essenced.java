package org.dantesys.nexus.utilidade;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.dantesys.nexus.componentes.DataComponentes;

public interface Essenced {
    default void setTipo(ItemStack stack, int tipo){
        if(stack.is(ModTags.Items.ESSENCED)){
            stack.set(DataComponentes.ESSENCIA_TIPO,tipo);
        }
    }
    default int getTipo(ItemStack stack) {
        if(stack.is(ModTags.Items.ESSENCED)){
            return stack.get(DataComponentes.ESSENCIA_TIPO);
        }
        return -1;
    }
    default void habilidade(ServerPlayer player, ItemStack stack){}
    default void passiva(ServerPlayer player, ItemStack stack){}
}
