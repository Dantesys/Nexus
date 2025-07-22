package org.dantesys.nexus.blocos;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.dantesys.nexus.blocos.entity.ColetorEntity;
import org.dantesys.nexus.componentes.DataComponentes;
import org.dantesys.nexus.itens.GemaNexus;
import org.dantesys.nexus.utilidade.Essenced;
import org.jetbrains.annotations.Nullable;

public class Coletor extends BaseEntityBlock implements Essenced {
    public static final IntegerProperty TIPO = IntegerProperty.create("tipo_nexus",0,4);
    public static final MapCodec<Coletor> CODEC = simpleCodec(Coletor::new);
    public Coletor(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TIPO,0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIPO);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(TIPO,context.getItemInHand().getOrDefault(DataComponentes.ESSENCIA_TIPO,0));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ColetorEntity(blockPos,blockState);
    }
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
