package org.dantesys.nexus.itens;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.phys.AABB;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.utilidade.Essenced;
import org.dantesys.nexus.utilidade.NexusTipos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class EspadaNexus extends Item implements Essenced {
    public EspadaNexus(Properties properties) {
        super(properties);
    }
    @Override
    @Deprecated
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder, @NotNull TooltipFlag flag) {
        int tipo = getTipo(stack);
        tooltipAdder.accept(Component.translatable("tooltip."+ Nexus.MODID+".espada."+ NexusTipos.getNomeTipo(tipo)));
        tooltipAdder.accept(Component.translatable("tooltip."+ Nexus.MODID+"."+ NexusTipos.getNomeTipo(tipo)));
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }
    @Override
    public int getDamage(@NotNull ItemStack stack) {
        int tipo = getTipo(stack);
        if(tipo<=0){
            return 0;
        }
        return super.getDamage(stack);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, @NotNull Consumer<Item> onBroken) {
        int dano = stack.getDamageValue();
        if(dano+amount>=stack.getMaxDamage()){
            setTipo(stack,0);
            amount=0;
            stack.setDamageValue(0);
        }
        return super.damageItem(stack, amount, entity, onBroken);
    }

    @Override
    public void habilidade(ServerPlayer player, ItemStack stack) {
        int tipo = getTipo(stack);
        ServerLevel level = player.level();
        switch (tipo){
            case 1 -> {
                AABB area = player.getBoundingBox().expandTowards(player.getLookAngle().scale(4)).inflate(2);
                List<LivingEntity> alvos = level.getEntitiesOfClass(LivingEntity.class,area,e -> e!=player && player.hasLineOfSight(e));
                for(LivingEntity alvo: alvos){
                    try{
                        alvo.hurtServer(level,player.damageSources().playerAttack(player),9f);
                        alvo.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,60));
                    }catch (Exception e){}
                }
                level.playSound(null,player.blockPosition(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS,1f,1.2f);
                level.sendParticles(ParticleTypes.FLAME,player.getX(),player.getY()+1.5,player.getZ(),20,0.5,0.5,0.5,0.01);
            }
        }
    }

    @NotNull
    public Component getName(@NotNull ItemStack stack) {
        int tipo = getTipo(stack);
        return Component.translatable("item."+Nexus.MODID+".espada." + NexusTipos.getNomeTipo(tipo));
    }
}
