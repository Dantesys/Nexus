package org.dantesys.nexus.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.dantesys.nexus.Nexus;

import java.util.function.Supplier;

public class ModRecipes {
    public static final ExtendedRecipeBookCategory INFUSOR_SEARCH_CATEGORY = new ExtendedRecipeBookCategory() {};
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, Nexus.MODID);

    public static final Supplier<RecipeBookCategory> INFUSOR_CATEGORY = RECIPE_BOOK_CATEGORIES.register(
            "infusor", RecipeBookCategory::new
    );

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Nexus.MODID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<InfusorRecipe>> INFUSOR_SERIALIZER =
            SERIALIZERS.register("infusor", InfusorRecipe.Serializer::new);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Nexus.MODID);

    public static final Supplier<RecipeType<InfusorRecipe>> INFUSOR_TYPE =
            RECIPE_TYPES.register("infusor", RecipeType::simple);

    public static void register(IEventBus bus){
        RECIPE_BOOK_CATEGORIES.register(bus);
        RECIPE_TYPES.register(bus);
        SERIALIZERS.register(bus);
    }
}
