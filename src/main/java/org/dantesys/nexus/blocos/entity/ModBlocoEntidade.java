package org.dantesys.nexus.blocos.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.blocos.ModBlocos;

import java.util.function.Supplier;

public class ModBlocoEntidade {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Nexus.MODID);

    public static final Supplier<BlockEntityType<InfusorEntity>> INSUFOR_ENTITY = BLOCK_ENTITY_TYPES.register("infusor",
            () -> new BlockEntityType<>(InfusorEntity::new,false, ModBlocos.INFUSOR.get()));

    public static void register(IEventBus event){
        BLOCK_ENTITY_TYPES.register(event);
    }
}
