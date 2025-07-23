package org.dantesys.nexus.itens;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.blocos.Coletor;
import org.dantesys.nexus.blocos.entity.ColetorEntity;
import org.dantesys.nexus.utilidade.Essenced;
import org.dantesys.nexus.utilidade.NexusTipos;
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
        tooltipAdder.accept(Component.translatable("tooltip."+Nexus.MODID+".gema."+NexusTipos.getNomeTipo(tipo)));
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
    @NotNull
    public Component getName(@NotNull ItemStack stack) {
        int tipo = getTipo(stack);
        return Component.translatable("item."+Nexus.MODID+".gema." + NexusTipos.getNomeTipo(tipo));
    }

    @NotNull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        Block clickedBlock = blockState.getBlock();
        if(clickedBlock instanceof Coletor){
            if(!level.isClientSide()) {
                ItemStack stack = context.getItemInHand();
                if(stack.getItem() instanceof Essenced item){
                    BlockEntity entity = level.getBlockEntity(pos);
                    if(entity instanceof ColetorEntity coletor){
                        int tipo = item.getTipo(stack);
                        int novotipo=0;
                        if(tipo==0){
                            novotipo = coletor.getTipo();
                        }
                        blockState.setValue(Coletor.TIPO,tipo);
                        coletor.setTipo(tipo);
                        item.setTipo(stack,novotipo);
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}
