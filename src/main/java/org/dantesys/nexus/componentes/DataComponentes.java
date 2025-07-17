package org.dantesys.nexus.componentes;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.dantesys.nexus.Nexus;

import java.util.function.UnaryOperator;

public class DataComponentes {
    public static final DeferredRegister<DataComponentType<?>> DATACOMPONENT =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE,Nexus.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ESSENCIA_TIPO = register("tipo_nexus",
            builder -> builder.persistent(Codec.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CAJADO_MODO = register("cajado_modo",
            builder -> builder.persistent(Codec.INT));



    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATACOMPONENT.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATACOMPONENT.register(eventBus);
    }
}
