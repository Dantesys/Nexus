package org.dantesys.nexus.itens;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.utilidade.Essenced;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class GemaNexus extends Item implements Essenced {
    public GemaNexus(Properties properties) {
        super(properties);
    }

    @Override
    @Deprecated
    public void appendHoverText(@NotNull ItemStack stack,@NotNull TooltipContext context,@NotNull TooltipDisplay tooltipDisplay,@NotNull Consumer<Component> tooltipAdder,@NotNull TooltipFlag flag) {
        int tipo = getTipo(stack);
        String tipoNome = switch (tipo) {
            case 0 -> "solar";
            case 1 -> "lunar";
            case 2 -> "nether";
            case 3 -> "ender";
            default -> "nexus";
        };
        tooltipAdder.accept(Component.translatable("tooltip."+Nexus.MODID+".gema."+tipoNome));
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }
    @Override
    public int getDamage(@NotNull ItemStack stack) {
        int tipo = getTipo(stack);
        if(tipo==-1){
            return 0;
        }
        return super.getDamage(stack);
    }
    @NotNull
    public Component getName(@NotNull ItemStack stack) {
        int tipo = getTipo(stack);
        String essenceId = switch (tipo) {
            case 0 -> "solar";
            case 1 -> "lunar";
            case 2 -> "nether";
            case 3 -> "ender";
            default -> "nexus";
        };
        return Component.translatable("item."+Nexus.MODID+".gema." + essenceId);
    }
}
