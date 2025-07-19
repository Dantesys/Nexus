package org.dantesys.nexus.efeitos;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.dantesys.nexus.Nexus;

public class ModEfeitos {
    public static final DeferredRegister<MobEffect> EFEITOS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Nexus.MODID);

    public static final Holder<MobEffect> SOLAR_EFFECT = EFEITOS.register("solar_effect",() -> new SolarEffect(MobEffectCategory.BENEFICIAL,0xfcff00));

    public static void register(IEventBus eventBus) {
        EFEITOS.register(eventBus);
    }
}
