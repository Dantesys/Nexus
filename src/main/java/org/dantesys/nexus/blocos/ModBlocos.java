package org.dantesys.nexus.blocos;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.dantesys.nexus.Nexus;

public class ModBlocos {
    public static final DeferredRegister.Blocks BLOCOS = DeferredRegister.createBlocks(Nexus.MODID);



    public static void register(IEventBus event){
        BLOCOS.register(event);
    }
}
