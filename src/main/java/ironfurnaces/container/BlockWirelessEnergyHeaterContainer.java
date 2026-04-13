/*     */ package ironfurnaces.container;
/*     */ 
/*     */ import ironfurnaces.container.slots.SlotHeater;
/*     */ import ironfurnaces.energy.FEnergyStorage;
/*     */ import ironfurnaces.init.Registration;
/*     */ import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.DataSlot;
/*     */ import net.minecraft.world.inventory.MenuType;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*     */ import net.minecraftforge.energy.IEnergyStorage;
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ import net.minecraftforge.items.SlotItemHandler;
/*     */ import net.minecraftforge.items.wrapper.InvWrapper;
/*     */ 
/*     */ 
/*     */ public class BlockWirelessEnergyHeaterContainer
/*     */   extends AbstractContainerMenu
/*     */ {
/*     */   protected BlockWirelessEnergyHeaterTile te;
/*     */   protected Player playerEntity;
/*     */   protected IItemHandler playerInventory;
/*     */   protected final Level world;
/*     */   
/*     */   public BlockWirelessEnergyHeaterContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
/*  35 */     super((MenuType)Registration.HEATER_CONTAINER.get(), windowId);
/*  36 */     this.te = (BlockWirelessEnergyHeaterTile)world.getBlockEntity(pos);
/*  37 */     this.playerEntity = player;
/*  38 */     this.playerInventory = (IItemHandler)new InvWrapper((Container)playerInventory);
/*  39 */     this.world = playerInventory.player.level();
/*  40 */     trackPower();
/*  41 */     addSlot((Slot)new SlotHeater(this.te, 0, 80, 37));
/*  42 */     layoutPlayerInventorySlots(8, 84);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergy() {
/*  47 */     return ((Integer)this.te.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(Integer.valueOf(0))).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergy() {
/*  52 */     return ((Integer)this.te.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(Integer.valueOf(0))).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void trackPower() {
/*  60 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/*  63 */             return BlockWirelessEnergyHeaterContainer.this.getMaxEnergy() & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/*  68 */             BlockWirelessEnergyHeaterContainer.this.te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
/*     */                   int capacity = h.getMaxEnergyStored() & 0xFFFF0000;
/*     */                   ((FEnergyStorage)h).setCapacity(capacity + (value & 0xFFFF));
/*     */                 });
/*     */           }
/*     */         });
/*  74 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/*  77 */             return BlockWirelessEnergyHeaterContainer.this.getMaxEnergy() >> 16 & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/*  82 */             BlockWirelessEnergyHeaterContainer.this.te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
/*     */                   int capacity = h.getMaxEnergyStored() & 0xFFFF;
/*     */                   
/*     */                   ((FEnergyStorage)h).setCapacity(capacity | value << 16);
/*     */                 });
/*     */           }
/*     */         });
/*  89 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/*  92 */             return BlockWirelessEnergyHeaterContainer.this.getEnergy() & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/*  97 */             BlockWirelessEnergyHeaterContainer.this.te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
/*     */                   int energyStored = h.getEnergyStored() & 0xFFFF0000;
/*     */                   ((FEnergyStorage)h).setEnergy(energyStored + (value & 0xFFFF));
/*     */                 });
/*     */           }
/*     */         });
/* 103 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 106 */             return BlockWirelessEnergyHeaterContainer.this.getEnergy() >> 16 & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 111 */             BlockWirelessEnergyHeaterContainer.this.te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
/*     */                   int energyStored = h.getEnergyStored() & 0xFFFF;
/*     */                   ((FEnergyStorage)h).setEnergy(energyStored | value << 16);
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public int getEnergyScaled(int pixels) {
/* 121 */     int i = getEnergy();
/* 122 */     int j = getMaxEnergy();
/* 123 */     return (j != 0 && i != 0) ? (i * pixels / j) : 0;
/*     */   }
/*     */   
/*     */   private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
/* 127 */     for (int i = 0; i < amount; i++) {
/* 128 */       addSlot((Slot)new SlotItemHandler(handler, index, x, y));
/* 129 */       x += dx;
/* 130 */       index++;
/*     */     } 
/* 132 */     return index;
/*     */   }
/*     */   
/*     */   private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
/* 136 */     for (int j = 0; j < verAmount; j++) {
/* 137 */       index = addSlotRange(handler, index, x, y, horAmount, dx);
/* 138 */       y += dy;
/*     */     } 
/* 140 */     return index;
/*     */   }
/*     */ 
/*     */   
/*     */   private void layoutPlayerInventorySlots(int leftCol, int topRow) {
/* 145 */     addSlotBox(this.playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
/*     */ 
/*     */     
/* 148 */     topRow += 58;
/* 149 */     addSlotRange(this.playerInventory, 0, leftCol, topRow, 9, 18);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player playerIn, int index) {
/* 154 */     ItemStack itemstack = ItemStack.EMPTY;
/* 155 */     Slot slot = (Slot)this.slots.get(index);
/* 156 */     if (slot != null && slot.hasItem()) {
/* 157 */       ItemStack itemstack1 = slot.getItem();
/* 158 */       itemstack = itemstack1.copy();
/* 159 */       if (!(itemstack.getItem() instanceof ironfurnaces.items.ItemHeater))
/*     */       {
/* 161 */         return ItemStack.EMPTY;
/*     */       }
/* 163 */       if (index < 1) {
/* 164 */         if (!moveItemStackTo(itemstack1, 1, this.slots.size(), true)) {
/* 165 */           return ItemStack.EMPTY;
/*     */         }
/* 167 */       } else if (!moveItemStackTo(itemstack1, 0, 1, false)) {
/* 168 */         return ItemStack.EMPTY;
/*     */       } 
/*     */       
/* 171 */       if (itemstack1.isEmpty()) {
/* 172 */         slot.set(ItemStack.EMPTY);
/*     */       } else {
/* 174 */         slot.setChanged();
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player p_38874_) {
/* 183 */     return this.te.stillValid(p_38874_);
/*     */   }
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\BlockWirelessEnergyHeaterContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */