package org.dantesys.nexus.efeitos;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.dantesys.nexus.Nexus;
import org.jetbrains.annotations.NotNull;

public class NetherEffect extends MobEffect {
    protected NetherEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    public boolean applyEffectTick(ServerLevel level, @NotNull LivingEntity entity, int amplifier) {
        if(level.dimension() == ServerLevel.NETHER){
            entity.heal(amplifier+1);
        }
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(Nexus.MODID, "effect.strength"), 2.0*(amplifier+1), AttributeModifier.Operation.ADD_VALUE);
        if(entity.isOnFire()){
            entity.heal(entity.getRemainingFireTicks());
        }
        return true;
    }
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onMobHurt(ServerLevel level, LivingEntity entity, int amplifier, DamageSource damageSource, float amount) {
        if(damageSource.getEntity() instanceof LivingEntity atacante){
            atacante.setRemainingFireTicks(600*amplifier);
        }
        if(entity.isOnFire()){
            amount = amount/2;
        }
        super.onMobHurt(level, entity, amplifier, damageSource, amount);
    }
}
