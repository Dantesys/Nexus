package org.dantesys.nexus.telas;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.dantesys.nexus.Nexus;

public class ModMenuType {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Nexus.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<InfusorMenu>> INFUSOR_MENU =
            registerMenuType("infusor_menu", InfusorMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<ColetorMenu>> COLETOR_MENU =
            registerMenuType("coletor_menu", ColetorMenu::new);

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name,
                                                                                                               IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
