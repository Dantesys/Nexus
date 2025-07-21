package org.dantesys.nexus.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import static org.dantesys.nexus.recipe.ModRecipes.INFUSOR_TYPE;
import static org.dantesys.nexus.recipe.ModRecipes.INFUSOR_CATEGORY;

public record InfusorRecipe(Ingredient entrada, Ingredient entrada2, ItemStack saida) implements Recipe<InfusorInput> {

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(entrada);
        list.add(entrada2);
        return list;
    }

    @Override
    public boolean matches(InfusorInput itemStacks, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return entrada.test(itemStacks.getItem(0)) && entrada.test(itemStacks.getItem(1));
    }

    @Override
    public ItemStack assemble(InfusorInput itemStacks, HolderLookup.Provider provider) {
        return saida.copy();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeSerializer<? extends Recipe<InfusorInput>> getSerializer() {
        return ModRecipes.INFUSOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<InfusorInput>> getType() {
        return INFUSOR_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(getIngredients());
    }
    @Override
    public RecipeBookCategory recipeBookCategory() {
        return INFUSOR_CATEGORY.get();
    }
    public static class Serializer implements RecipeSerializer<InfusorRecipe> {
        public static final MapCodec<InfusorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(InfusorRecipe::entrada),
                Ingredient.CODEC.fieldOf("ingredient2").forGetter(InfusorRecipe::entrada2),
                ItemStack.CODEC.fieldOf("result").forGetter(InfusorRecipe::saida)
        ).apply(inst, InfusorRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, InfusorRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, InfusorRecipe::entrada,
                        Ingredient.CONTENTS_STREAM_CODEC, InfusorRecipe::entrada2,
                        ItemStack.STREAM_CODEC, InfusorRecipe::saida,
                        InfusorRecipe::new);

        @Override
        public MapCodec<InfusorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, InfusorRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
