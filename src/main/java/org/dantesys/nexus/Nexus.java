package org.dantesys.nexus;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.dantesys.nexus.blocos.Coletor;
import org.dantesys.nexus.blocos.ModBlocos;
import org.dantesys.nexus.blocos.entity.ModBlocoEntidade;
import org.dantesys.nexus.componentes.DataComponentes;
import org.dantesys.nexus.efeitos.ModEfeitos;
import org.dantesys.nexus.itens.ModItens;
import org.dantesys.nexus.recipe.ModRecipes;
import org.dantesys.nexus.telas.ColetorScreen;
import org.dantesys.nexus.telas.InfusorScreen;
import org.dantesys.nexus.telas.ModMenuType;
import org.dantesys.nexus.utilidade.ModCreativeTab;
import org.slf4j.Logger;

import static org.dantesys.nexus.telas.ModMenuType.COLETOR_MENU;
import static org.dantesys.nexus.telas.ModMenuType.INFUSOR_MENU;
import static org.dantesys.nexus.utilidade.KeyMappings.HAB_MAPPING;
import static org.dantesys.nexus.utilidade.KeyMappings.SWAP_MAPPING;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Nexus.MODID)
public class Nexus {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "nexus";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Nexus(IEventBus modEventBus) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        ModCreativeTab.register(modEventBus);
        ModItens.register(modEventBus);
        ModBlocos.register(modEventBus);
        DataComponentes.register(modEventBus);
        ModEfeitos.register(modEventBus);
        ModBlocoEntidade.register(modEventBus);
        ModMenuType.register(modEventBus);
        ModRecipes.register(modEventBus);
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Nexus) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
        @SubscribeEvent // on the mod event bus only on the physical client
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(INFUSOR_MENU.get(), InfusorScreen::new);
            event.register(COLETOR_MENU.get(), ColetorScreen::new);
        }
        @SubscribeEvent
        public static void registerKeyMapping(RegisterKeyMappingsEvent event){
            event.register(HAB_MAPPING.get());
            event.register(SWAP_MAPPING.get());
        }
    }
}
