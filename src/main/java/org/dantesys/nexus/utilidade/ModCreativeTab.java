package org.dantesys.nexus.utilidade;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.itens.ModItens;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Nexus.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NEXUSTAB = CREATIVE_MODE_TABS.register("nexus_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.nexus"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItens.GEMA.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItens.ORB.get());
                output.accept(ModItens.GEMA.get());
                output.accept(ModItens.INFUSOR.get());
            }).build());

    public static void register(IEventBus event){
        CREATIVE_MODE_TABS.register(event);
    }
}
