package org.dantesys.nexus.efeitos;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.Vec3;
import org.dantesys.nexus.Nexus;
import org.jetbrains.annotations.NotNull;

public class EnderEffect extends MobEffect {
    protected EnderEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, @NotNull LivingEntity entity, int amplifier) {
        if(level.dimension() == ServerLevel.END){
            entity.heal(amplifier+1);
        }
        entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,220,0,false,false,false));
        if(entity.getHealth()/entity.getMaxHealth()<=0.5){
            this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(Nexus.MODID, "effect.speed"), 0.5*(amplifier+1), AttributeModifier.Operation.ADD_VALUE);
        }
        return true;
    }
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onMobHurt(ServerLevel level, LivingEntity entity, int amplifier, DamageSource damageSource, float amount) {
        amount=amount*(entity.getHealth()/(entity.getMaxHealth()*(amplifier+1)));
        if (entity.level().random.nextFloat() < 0.5f) {
            if(damageSource.getEntity() instanceof Projectile projectile){
                Entity target = projectile.getOwner();
                ProjectileDeflection reflectToAttacker = (proj, deflector, random) -> {
                    if (deflector != null && target != null) {
                        Vec3 direction = target.position().subtract(deflector.position()).normalize().scale(1.5);
                        proj.setDeltaMovement(direction);
                        proj.setPos(deflector.getX(), deflector.getEyeY(), deflector.getZ());
                        proj.hasImpulse = true;
                    }
                };
                projectile.deflect(reflectToAttacker,entity,entity, true);
                amount=0;
            }
        }
        super.onMobHurt(level, entity, amplifier, damageSource, amount);
    }
}
