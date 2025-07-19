package org.dantesys.nexus.efeitos;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.dantesys.nexus.Nexus;
import org.jetbrains.annotations.NotNull;

public class SolarEffect extends MobEffect {
    public SolarEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    public boolean applyEffectTick(ServerLevel level, @NotNull LivingEntity entity, int amplifier) {
        if(level.dimension() == ServerLevel.OVERWORLD){
            if(level.dayTime() >= 6000 && level.dayTime() <= 18000){
                entity.heal(amplifier+1);
                this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(Nexus.MODID, "effect.strength"), 2.0, AttributeModifier.Operation.ADD_VALUE);
            }
        }
        entity.setGlowingTag(true);
        return true;
    }
}
