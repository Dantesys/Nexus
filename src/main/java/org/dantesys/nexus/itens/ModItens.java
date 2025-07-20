package org.dantesys.nexus.itens;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.dantesys.nexus.Nexus;

public class ModItens {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Nexus.MODID);

    public static final DeferredItem<Item> ORB = ITEMS.registerSimpleItem("orb",new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> GEMA = ITEMS.registerItem("gema",GemaNexus::new,new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON));

    public static void register(IEventBus bus){
        ITEMS.register(bus);
    }
}
