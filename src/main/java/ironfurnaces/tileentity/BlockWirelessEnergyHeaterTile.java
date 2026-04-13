/*     */ package ironfurnaces.tileentity;

/*     */ import ironfurnaces.container.BlockWirelessEnergyHeaterContainer;
/*     */ import ironfurnaces.energy.FEnergyStorage;
/*     */ import ironfurnaces.init.Registration;
/*     */ import ironfurnaces.items.ItemHeater;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*     */ import net.minecraftforge.common.util.LazyOptional;
/*     */ import net.minecraftforge.energy.IEnergyStorage;
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ import net.minecraftforge.items.wrapper.SidedInvWrapper;
/*     */ import org.jetbrains.annotations.Nullable;

/*     */ public class BlockWirelessEnergyHeaterTile extends TileEntityInventory {
/*     */   private final LazyOptional<IEnergyStorage> energy;
/*     */   private final LazyOptional<? extends IItemHandler>[] handlers;

/*     */   private FEnergyStorage createEnergy() {
/*     */     return new FEnergyStorage(1000000, 1000000, 0) {
/*     */           @Override
/*     */           protected void onEnergyChanged() {
/*     */             BlockWirelessEnergyHeaterTile.this.setChanged();
/*     */           }
/*     */         };
/*     */   }

/*     */   public static void tick(
/*     */       Level level, BlockPos worldPosition, BlockState blockState, BlockWirelessEnergyHeaterTile e) {
/*     */     ItemStack stack = e.getItem(0);
/*     */     if (!stack.isEmpty()) {
/*     */       CompoundTag nbt = new CompoundTag();
/*     */       stack.setTag(nbt);
/*     */       nbt.putInt("X", e.worldPosition.getX());
/*     */       nbt.putInt("Y", e.worldPosition.getY());
/*     */       nbt.putInt("Z", e.worldPosition.getZ());
/*     */     }
/*     */   }

/*     */   public int getEnergy() {
/*     */     return getCapability(ForgeCapabilities.ENERGY, null)
/*     */         .map(IEnergyStorage::getEnergyStored)
/*     */         .orElse(0);
/*     */   }

/*     */   public int getCapacity() {
/*     */     return getCapability(ForgeCapabilities.ENERGY, null)
/*     */         .map(IEnergyStorage::getMaxEnergyStored)
/*     */         .orElse(0);
/*     */   }

/*     */   @SuppressWarnings("unchecked")
/*     */   public BlockWirelessEnergyHeaterTile(BlockPos pos, BlockState state) {
/*     */     super((BlockEntityType<?>) Registration.HEATER_TILE.get(), pos, state, 1);
/*     */     this.energy = LazyOptional.of(() -> this.createEnergy());
/*     */     this.handlers =
/*     */         (LazyOptional<? extends IItemHandler>[])
/*     */             SidedInvWrapper.create(
/*     */                 this, new Direction[] {Direction.UP, Direction.DOWN, Direction.NORTH});
/*     */   }

/*     */   public void setEnergy(int energy) {
/*     */     this.energy.ifPresent(h -> ((FEnergyStorage) h).setEnergy(energy));
/*     */   }

/*     */   public void setMaxEnergy(int energy) {
/*     */     this.energy.ifPresent(h -> ((FEnergyStorage) h).setCapacity(energy));
/*     */   }

/*     */   public void removeEnergy(int energy) {
/*     */     this.energy.ifPresent(h -> ((FEnergyStorage) h).setEnergy(h.getEnergyStored() - energy));
/*     */   }

/*     */   @Override
/*     */   public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
/*     */     if (!isRemoved() && facing != null && capability == ForgeCapabilities.ITEM_HANDLER) {
/*     */       if (facing == Direction.UP) {
/*     */         return this.handlers[0].cast();
/*     */       }
/*     */       if (facing == Direction.DOWN) {
/*     */         return this.handlers[1].cast();
/*     */       }
/*     */       return this.handlers[2].cast();
/*     */     }
/*     */     if (!isRemoved() && capability == ForgeCapabilities.ENERGY) {
/*     */       return this.energy.cast();
/*     */     }
/*     */     return super.getCapability(capability, facing);
/*     */   }

/*     */   @Override
/*     */   public void load(CompoundTag nbt) {
/*     */     super.load(nbt);
/*     */     this.energy.ifPresent(h -> ((FEnergyStorage) h).setEnergy(nbt.getInt("Energy")));
/*     */   }

/*     */   @Override
/*     */   protected void saveAdditional(CompoundTag nbt) {
/*     */     super.saveAdditional(nbt);
/*     */     nbt.putInt("Energy", getEnergy());
/*     */   }

/*     */   @Override
/*     */   public int[] IgetSlotsForFace(Direction side) {
/*     */     return new int[0];
/*     */   }

/*     */   @Override
/*     */   public boolean IcanExtractItem(int index, ItemStack stack, Direction direction) {
/*     */     return true;
/*     */   }

/*     */   @Override
/*     */   public String IgetName() {
/*     */     return "container.ironfurnaces.wireless_energy_heater";
/*     */   }

/*     */   @Override
/*     */   public boolean IisItemValidForSlot(int index, ItemStack stack) {
/*     */     return stack.getItem() instanceof ItemHeater;
/*     */   }

/*     */   @Override
/*     */   public AbstractContainerMenu IcreateMenu(
/*     */       int i, Inventory playerInventory, Player playerEntity) {
/*     */     return new BlockWirelessEnergyHeaterContainer(
/*     */         i, this.level, this.worldPosition, playerInventory, playerEntity);
/*     */   }

/*     */   @Override
/*     */   public void setRemoved() {
/*     */     this.energy.invalidate();
/*     */     super.setRemoved();
/*     */   }
/*     */ }
