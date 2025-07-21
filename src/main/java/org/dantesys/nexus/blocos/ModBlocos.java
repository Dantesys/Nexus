package org.dantesys.nexus.blocos;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.dantesys.nexus.Nexus;

public class ModBlocos {
    public static final DeferredRegister.Blocks BLOCOS = DeferredRegister.createBlocks(Nexus.MODID);

    public static final DeferredBlock<Infusor> INFUSOR = BLOCOS.registerBlock("infusor",(properties ->  new Infusor(properties.strength(2,10).dynamicShape().noOcclusion())));

    public static void register(IEventBus event){
        BLOCOS.register(event);
    }
}
