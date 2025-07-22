package org.dantesys.nexus.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.dantesys.nexus.Nexus;

import java.util.function.Supplier;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Nexus.MODID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<InfusorRecipe>> INFUSOR_SERIALIZER =
            SERIALIZERS.register("infusor", InfusorRecipe.Serializer::new);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Nexus.MODID);

    public static final Supplier<RecipeType<InfusorRecipe>> INFUSOR_TYPE =
            RECIPE_TYPES.register("infusor", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "infusor";
                }
            });

    public static void register(IEventBus bus){
        SERIALIZERS.register(bus);
        RECIPE_TYPES.register(bus);
    }
}
