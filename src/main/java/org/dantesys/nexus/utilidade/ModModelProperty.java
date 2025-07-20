package org.dantesys.nexus.utilidade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.dantesys.nexus.componentes.DataComponentes;
import org.jetbrains.annotations.Nullable;

public record ModModelProperty() implements SelectItemModelProperty<Integer> {
    public static final SelectItemModelProperty.Type<ModModelProperty, Integer> TYPE = SelectItemModelProperty.Type.create(
            MapCodec.unit(new ModModelProperty()),
            Codec.INT
    );
    @Nullable
    @Override
    public Integer get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        return stack.get(DataComponentes.ESSENCIA_TIPO);
    }

    @Override
    public Codec<Integer> valueCodec() {
        return null;
    }

    @Override
    public SelectItemModelProperty.Type<ModModelProperty, Integer> type() {
        return TYPE;
    }
}
