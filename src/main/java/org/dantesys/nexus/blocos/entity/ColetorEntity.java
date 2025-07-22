package org.dantesys.nexus.blocos.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.blocos.Coletor;
import org.dantesys.nexus.recipe.InfusorInput;
import org.dantesys.nexus.recipe.InfusorRecipe;
import org.dantesys.nexus.recipe.ModRecipes;
import org.dantesys.nexus.telas.InfusorMenu;

import javax.annotation.Nullable;
import java.util.Optional;

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
                }
            }

            @Override
            public int getCount() {
                return 4;
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
        output.putInt("tipo_nexus",tipo);
        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        input.getInt("coletor.progress");
        input.getInt("coletor.essencia");
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
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level, blockPos, blockState);

            if(hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void craftItem() {
        Optional<RecipeHolder<InfusorRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().saida();

        itemHandler.extractItem(0, 1, false);
        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private void resetProgress() {
        progress = 0;
        maxProgress = 78;
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<InfusorRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().saida();
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private Optional<RecipeHolder<InfusorRecipe>> getCurrentRecipe() {
        return ((ServerLevel) this.level).recipeAccess()
                .getRecipeFor(ModRecipes.INFUSOR_TYPE.get(), new InfusorInput(itemHandler.getStackInSlot(0),itemHandler.getStackInSlot(1)), level);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container."+ Nexus.MODID+".infusor");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new InfusorMenu(i, inventory, this, this.data);
    }
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}