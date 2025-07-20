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
    public static final Holder<MobEffect> LUNAR_EFFECT = EFEITOS.register("lunar_effect",() -> new LunarEffect(MobEffectCategory.BENEFICIAL,0x250063));
    public static final Holder<MobEffect> NETHER_EFFECT = EFEITOS.register("nether_effect",() -> new NetherEffect(MobEffectCategory.BENEFICIAL,0xff0000));
    public static final Holder<MobEffect> ENDER_EFFECT = EFEITOS.register("ender_effect",() -> new EnderEffect(MobEffectCategory.BENEFICIAL,0x8000ff));

    public static void register(IEventBus eventBus) {
        EFEITOS.register(eventBus);
    }
}
