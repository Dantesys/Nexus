package org.dantesys.nexus.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record InfusorInput(ItemStack input, ItemStack input2) implements RecipeInput {

    @Override
    public ItemStack getItem(int slot) {
        return slot==0 ? this.input: this.input2;
    }

    @Override
    public int size() {
        return 2;
    }
}
