package org.dantesys.nexus.blocos.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.blocos.Coletor;
import org.dantesys.nexus.telas.ColetorMenu;
import org.dantesys.nexus.utilidade.Essenced;
import org.dantesys.nexus.utilidade.NexusTipos;

import javax.annotation.Nullable;

import static org.dantesys.nexus.blocos.entity.ModBlocoEntidade.COLETOR_ENTITY;

public class ColetorEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(2){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
            }
        }
    };
    private int tipo;
    private int essencia = 0;
    private int maxEssencia = 1000;
    private int tickCont = 0;
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    private final int OUTPUT_SLOT = 1;
    public ColetorEntity(BlockPos pos, BlockState blockState) {
        super(COLETOR_ENTITY.get(), pos, blockState);
        this.tipo = blockState.getValue(Coletor.TIPO);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> ColetorEntity.this.progress;
                    case 1 -> ColetorEntity.this.maxProgress;
                    case 2 -> ColetorEntity.this.essencia;
                    case 3 -> ColetorEntity.this.maxEssencia;
                    case 4 -> ColetorEntity.this.tipo;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> ColetorEntity.this.progress = pValue;
                    case 1 -> ColetorEntity.this.maxProgress = pValue;
                    case 2 -> ColetorEntity.this.essencia = pValue;
                    case 3 -> ColetorEntity.this.maxEssencia = pValue;
                    case 4 -> ColetorEntity.this.setTipo(pValue);
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }
    public void setTipo(int tipo){
        this.tipo=tipo;
        if(level!=null && !level.isClientSide){
            BlockState state = getBlockState();
            if(state.getBlock() instanceof Coletor){
                level.setBlock(worldPosition,state.setValue(Coletor.TIPO,tipo),3);
                setChanged();
            }
        }
    }
    public int getTipo(){
        return this.tipo;
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        drops();
        super.preRemoveSideEffects(pos, state);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        itemHandler.serialize(output);
        output.putInt("coletor.progress", progress);
        output.putInt("coletor.essencia",essencia);
        output.putInt("coletor.tickCont",tickCont);
        output.putInt("tipo_nexus",tipo);
        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        input.getInt("coletor.progress");
        input.getInt("coletor.essencia");
        input.getInt("coletor.tickCont");
        tipo = input.getInt("tipo_nexus").get();
        if(level!=null && !level.isClientSide){
            BlockState state = getBlockState();
            if(state.getBlock() instanceof Coletor){
                level.setBlock(worldPosition,state.setValue(Coletor.TIPO,tipo),3);
                setChanged();
            }
        }
        super.loadAdditional(input);
    }
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if(!fullCheck()){
            if(loadEssence()){
                SimpleParticleType particula = ParticleTypes.SMOKE;
                SoundEvent som = SoundEvents.BEACON_AMBIENT;
                switch (tipo){
                    case 0 ->{
                        particula = ParticleTypes.END_ROD;
                        som = SoundEvents.ANVIL_USE;
                    }
                    case 1 -> {
                        particula = ParticleTypes.FLAME;
                        som = SoundEvents.FIRE_AMBIENT;
                    }
                    case 2 -> {
                        particula = ParticleTypes.GLOW;
                        som = SoundEvents.AMETHYST_BLOCK_CHIME;
                    }
                    case 3 -> {
                        particula = ParticleTypes.SOUL_FIRE_FLAME;
                        som = SoundEvents.LAVA_POP;
                    }
                    case 4 -> {
                        particula = ParticleTypes.DRAGON_BREATH;
                        som = SoundEvents.ENDER_DRAGON_GROWL;
                    }
                }
                if(level instanceof ServerLevel sv){
                    sv.sendParticles(particula,blockPos.getX()+0.5f,blockPos.getY()+0.5f,blockPos.getZ()+0.5f,5,0,0,0,0);
                    sv.playSound(null,blockPos,som, SoundSource.BLOCKS,0.6f,1f);
                }
            }
        }
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level, blockPos, blockState);
            if(hasCraftingFinished()) {
                craftItem();
                if(level instanceof ServerLevel sv){
                    sv.sendParticles(ParticleTypes.HAPPY_VILLAGER,blockPos.getX(),blockPos.getY()+1f,blockPos.getZ(),5,0.25,0.25,0.25,0.01);
                    sv.playSound(null,blockPos,SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS,0.6f,1f);
                }
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }
    private boolean fullCheck(){
        return essencia==maxEssencia;
    }
    private boolean loadEssence(){
        tickCont++;
        if (tickCont % 20 == 0) {
            tickCont=0;
            int qtd;
            if(level.dimension() == ServerLevel.OVERWORLD){
                if(level.dayTime() >= 6000 && level.dayTime() < 18000){
                    qtd=26;
                    if(tipo==1){
                        qtd*=2;
                    }else if(tipo!=0){
                        qtd/=2;
                    }
                }else{
                    qtd=20;
                    if(tipo==2){
                        qtd*=2;
                    }else if(tipo!=0){
                        qtd/=2;
                    }
                }
            }else if(level.dimension() == ServerLevel.NETHER){
                qtd=16;
                if(tipo==3){
                    qtd*=2;
                }else if(tipo!=0){
                    qtd/=2;
                }
            }else if(level.dimension() == ServerLevel.END){
                qtd=10;
                if(tipo==4){
                    qtd*=2;
                }else if(tipo!=0){
                    qtd/=2;
                }
            }else{
                qtd=6;
                if(tipo!=0){
                    qtd/=2;
                }
            }
            if(essencia+qtd>maxEssencia){
                essencia=maxEssencia;
                return false;
            }else{
                essencia+=qtd;
            }
            return true;
        }
        return false;
    }

    private void craftItem() {
        essencia-=32;
        ItemStack itemStack = itemHandler.extractItem(0, 1, false);
        if(itemStack.getItem() instanceof Essenced essenced){
            itemStack.setDamageValue(0);
            essenced.setTipo(itemStack,findTipo());
        }
        itemHandler.setStackInSlot(OUTPUT_SLOT, itemStack);
    }
    private int findTipo(){
        if(tipo==0){
            if(level.dimension() == ServerLevel.OVERWORLD){
                if(level.dayTime() >= 6000 && level.dayTime() < 18000){
                    return 1;
                }else{
                    return 2;
                }
            }else if(level.dimension() == ServerLevel.NETHER){
                return 3;
            }else if(level.dimension() == ServerLevel.END){
                return 4;
            }else{
                return 1;
            }
        }
        return tipo;
    }

    private void resetProgress() {
        progress = 0;
        maxProgress = 78;
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress && essencia>=32;
    }

    private void increaseCraftingProgress() {
        if(essencia>=6 && progress<maxProgress){
            essencia-=6;
            progress++;
        }
    }

    private boolean hasRecipe() {
        return itemHandler.getStackInSlot(0).getItem() instanceof Essenced;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container."+ Nexus.MODID+".coletor"+ NexusTipos.getNomeTipo(tipo));
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ColetorMenu(i, inventory, this, this.data);
    }
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}