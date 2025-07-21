package org.dantesys.nexus.eventos;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterRecipeBookSearchCategoriesEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.utilidade.Essenced;

import static org.dantesys.nexus.recipe.ModRecipes.INFUSOR_CATEGORY;
import static org.dantesys.nexus.recipe.ModRecipes.INFUSOR_SEARCH_CATEGORY;

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
    @SubscribeEvent // on the mod event bus
    public static void registerSearchCategories(RegisterRecipeBookSearchCategoriesEvent event) {
        event.register(
                // The search category
                INFUSOR_SEARCH_CATEGORY,
                // All recipe categories within the search category as varargs
                INFUSOR_CATEGORY.get()
        );
    }
}
