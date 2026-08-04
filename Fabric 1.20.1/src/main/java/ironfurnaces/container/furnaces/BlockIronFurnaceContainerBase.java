/*     */ package ironfurnaces.container.furnaces;
/*     */ 
/*     */ import ironfurnaces.container.slots.SlotIronFurnace;
/*     */ import ironfurnaces.container.slots.SlotIronFurnaceAugmentBlue;
/*     */ import ironfurnaces.container.slots.SlotIronFurnaceAugmentGreen;
/*     */ import ironfurnaces.container.slots.SlotIronFurnaceAugmentRed;
/*     */ import ironfurnaces.container.slots.SlotIronFurnaceFuel;
/*     */ import ironfurnaces.container.slots.SlotIronFurnaceInput;
/*     */ import ironfurnaces.container.slots.SlotIronFurnaceInputFactory;
/*     */ import ironfurnaces.container.slots.SlotIronFurnaceInputGenerator;
/*     */ import ironfurnaces.container.slots.SlotIronFurnaceOutputFactory;
/*     */ import ironfurnaces.energy.FEnergyStorage;
/*     */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*     */ import ironfurnaces.util.container.FactoryDataSlot;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.DataSlot;
/*     */ import net.minecraft.world.inventory.MenuType;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*     */ import net.minecraftforge.energy.IEnergyStorage;
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ import net.minecraftforge.items.SlotItemHandler;
/*     */ import net.minecraftforge.items.wrapper.InvWrapper;
/*     */ 
/*     */ public abstract class BlockIronFurnaceContainerBase extends AbstractContainerMenu {
/*     */   protected BlockIronFurnaceTileBase te;
/*     */   protected Player playerEntity;
/*     */   
/*     */   public BlockIronFurnaceContainerBase(MenuType<?> containerType, int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
/*  38 */     super(containerType, windowId);
/*  39 */     this.te = (BlockIronFurnaceTileBase)world.getBlockEntity(pos);
/*  40 */     this.playerEntity = player;
/*  41 */     this.playerInventory = (IItemHandler)new InvWrapper((Container)playerInventory);
/*  42 */     this.world = playerInventory.player.level();
/*     */ 
/*     */     
/*  45 */     addSlot((Slot)new SlotIronFurnaceInput(this.te, 0, 56, 17));
/*  46 */     addSlot((Slot)new SlotIronFurnaceFuel(this.te, 1, 56, 53));
/*  47 */     addSlot((Slot)new SlotIronFurnace(this.playerEntity, this.te, 2, 116, 35));
/*  48 */     addSlot((Slot)new SlotIronFurnaceAugmentRed(this.te, 3, 26, 35));
/*  49 */     addSlot((Slot)new SlotIronFurnaceAugmentGreen(this.te, 4, 80, 35));
/*  50 */     addSlot((Slot)new SlotIronFurnaceAugmentBlue(this.te, 5, 134, 35));
/*     */ 
/*     */     
/*  53 */     addSlot((Slot)new SlotIronFurnaceInputGenerator(this.te, 6, 56, 40));
/*     */ 
/*     */     
/*  56 */     addSlot((Slot)new SlotIronFurnaceInputFactory(0, this.te, 7, 28, 6));
/*  57 */     addSlot((Slot)new SlotIronFurnaceInputFactory(1, this.te, 8, 49, 6));
/*  58 */     addSlot((Slot)new SlotIronFurnaceInputFactory(2, this.te, 9, 70, 6));
/*  59 */     addSlot((Slot)new SlotIronFurnaceInputFactory(3, this.te, 10, 91, 6));
/*  60 */     addSlot((Slot)new SlotIronFurnaceInputFactory(4, this.te, 11, 112, 6));
/*  61 */     addSlot((Slot)new SlotIronFurnaceInputFactory(5, this.te, 12, 133, 6));
/*  62 */     addSlot((Slot)new SlotIronFurnaceOutputFactory(0, this.playerEntity, this.te, 13, 28, 55));
/*  63 */     addSlot((Slot)new SlotIronFurnaceOutputFactory(1, this.playerEntity, this.te, 14, 49, 55));
/*  64 */     addSlot((Slot)new SlotIronFurnaceOutputFactory(2, this.playerEntity, this.te, 15, 70, 55));
/*  65 */     addSlot((Slot)new SlotIronFurnaceOutputFactory(3, this.playerEntity, this.te, 16, 91, 55));
/*  66 */     addSlot((Slot)new SlotIronFurnaceOutputFactory(4, this.playerEntity, this.te, 17, 112, 55));
/*  67 */     addSlot((Slot)new SlotIronFurnaceOutputFactory(5, this.playerEntity, this.te, 18, 133, 55));
/*  68 */     layoutPlayerInventorySlots(8, 84);
/*  69 */     checkContainerSize((Container)this.te, 19);
/*  70 */     addDataSlots();
/*     */   }
/*     */   protected IItemHandler playerInventory; protected final Level world;
/*     */   
/*     */   public void addDataSlots() {
/*  75 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/*  78 */             return BlockIronFurnaceContainerBase.this.getAugmentGUI() ? 1 : 0;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/*  83 */             BlockIronFurnaceContainerBase.this.te.furnaceSettings.set(10, value);
/*     */           }
/*     */         });
/*  86 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/*  89 */             return BlockIronFurnaceContainerBase.this.getIsFurnace() ? 1 : 0;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/*  94 */             if (value == 1)
/*     */             {
/*  96 */               BlockIronFurnaceContainerBase.this.te.currentAugment[2] = 0;
/*     */             }
/*     */           }
/*     */         });
/* 100 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 103 */             return BlockIronFurnaceContainerBase.this.getIsGenerator() ? 1 : 0;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 108 */             if (value == 1)
/*     */             {
/* 110 */               BlockIronFurnaceContainerBase.this.te.currentAugment[2] = 2;
/*     */             }
/*     */           }
/*     */         });
/* 114 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 117 */             return BlockIronFurnaceContainerBase.this.getIsFactory() ? 1 : 0;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 122 */             if (value == 1)
/*     */             {
/* 124 */               BlockIronFurnaceContainerBase.this.te.currentAugment[2] = 1;
/*     */             }
/*     */           }
/*     */         });
/* 128 */     addEnergyData();
/* 129 */     addFurnaceData();
/* 130 */     addGeneratorData();
/* 131 */     addFactoryData();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFurnaceData() {
/* 137 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 140 */             return BlockIronFurnaceContainerBase.this.te.furnaceBurnTime & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 145 */             int add = BlockIronFurnaceContainerBase.this.te.furnaceBurnTime & 0xFFFF0000;
/* 146 */             BlockIronFurnaceContainerBase.this.te.furnaceBurnTime = add + (value & 0xFFFF);
/*     */           }
/*     */         });
/* 149 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 152 */             return BlockIronFurnaceContainerBase.this.te.furnaceBurnTime >> 16 & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 157 */             int add = BlockIronFurnaceContainerBase.this.te.furnaceBurnTime & 0xFFFF;
/* 158 */             BlockIronFurnaceContainerBase.this.te.furnaceBurnTime = add | value << 16;
/*     */           }
/*     */         });
/*     */     
/* 162 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 165 */             return BlockIronFurnaceContainerBase.this.te.recipesUsed & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 170 */             int add = BlockIronFurnaceContainerBase.this.te.recipesUsed & 0xFFFF0000;
/* 171 */             BlockIronFurnaceContainerBase.this.te.recipesUsed = add + (value & 0xFFFF);
/*     */           }
/*     */         });
/* 174 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 177 */             return BlockIronFurnaceContainerBase.this.te.recipesUsed >> 16 & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 182 */             int add = BlockIronFurnaceContainerBase.this.te.recipesUsed & 0xFFFF;
/* 183 */             BlockIronFurnaceContainerBase.this.te.recipesUsed = add | value << 16;
/*     */           }
/*     */         });
/*     */     
/* 187 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 190 */             return BlockIronFurnaceContainerBase.this.te.cookTime & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 195 */             int add = BlockIronFurnaceContainerBase.this.te.cookTime & 0xFFFF0000;
/* 196 */             BlockIronFurnaceContainerBase.this.te.cookTime = add + (value & 0xFFFF);
/*     */           }
/*     */         });
/* 199 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 202 */             return BlockIronFurnaceContainerBase.this.te.cookTime >> 16 & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 207 */             int add = BlockIronFurnaceContainerBase.this.te.cookTime & 0xFFFF;
/* 208 */             BlockIronFurnaceContainerBase.this.te.cookTime = add | value << 16;
/*     */           }
/*     */         });
/*     */     
/* 212 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 215 */             return BlockIronFurnaceContainerBase.this.te.totalCookTime & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 220 */             int add = BlockIronFurnaceContainerBase.this.te.totalCookTime & 0xFFFF0000;
/* 221 */             BlockIronFurnaceContainerBase.this.te.totalCookTime = add + (value & 0xFFFF);
/*     */           }
/*     */         });
/* 224 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 227 */             return BlockIronFurnaceContainerBase.this.te.totalCookTime >> 16 & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 232 */             int add = BlockIronFurnaceContainerBase.this.te.totalCookTime & 0xFFFF;
/* 233 */             BlockIronFurnaceContainerBase.this.te.totalCookTime = add | value << 16;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void addGeneratorData() {
/* 240 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 243 */             return (int)BlockIronFurnaceContainerBase.this.te.generatorBurn & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 248 */             int add = (int)BlockIronFurnaceContainerBase.this.te.generatorBurn & 0xFFFF0000;
/* 249 */             BlockIronFurnaceContainerBase.this.te.generatorBurn = (add + (value & 0xFFFF));
/*     */           }
/*     */         });
/* 252 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 255 */             return (int)BlockIronFurnaceContainerBase.this.te.generatorBurn >> 16 & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 260 */             int add = (int)BlockIronFurnaceContainerBase.this.te.generatorBurn & 0xFFFF;
/* 261 */             BlockIronFurnaceContainerBase.this.te.generatorBurn = (add | value << 16);
/*     */           }
/*     */         });
/* 264 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 267 */             return BlockIronFurnaceContainerBase.this.te.generatorRecentRecipeRF & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 272 */             int add = BlockIronFurnaceContainerBase.this.te.generatorRecentRecipeRF & 0xFFFF0000;
/* 273 */             BlockIronFurnaceContainerBase.this.te.generatorRecentRecipeRF = add + (value & 0xFFFF);
/*     */           }
/*     */         });
/* 276 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 279 */             return BlockIronFurnaceContainerBase.this.te.generatorRecentRecipeRF >> 16 & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 284 */             int add = BlockIronFurnaceContainerBase.this.te.generatorRecentRecipeRF & 0xFFFF;
/* 285 */             BlockIronFurnaceContainerBase.this.te.generatorRecentRecipeRF = add | value << 16;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void addFactoryData() {
/*     */     int i;
/* 292 */     for (i = 0; i < this.te.factoryCookTime.length; i++) {
/*     */       
/* 294 */       addDataSlot((DataSlot)new FactoryDataSlot(i)
/*     */           {
/*     */             public int get() {
/* 297 */               return BlockIronFurnaceContainerBase.this.te.factoryCookTime[this.index] & 0xFFFF;
/*     */             }
/*     */ 
/*     */             
/*     */             public void set(int value) {
/* 302 */               int add = BlockIronFurnaceContainerBase.this.te.factoryCookTime[this.index] & 0xFFFF0000;
/* 303 */               BlockIronFurnaceContainerBase.this.te.factoryCookTime[this.index] = add + (value & 0xFFFF);
/*     */             }
/*     */           });
/* 306 */       addDataSlot((DataSlot)new FactoryDataSlot(i)
/*     */           {
/*     */             public int get() {
/* 309 */               return BlockIronFurnaceContainerBase.this.te.factoryCookTime[this.index] >> 16 & 0xFFFF;
/*     */             }
/*     */ 
/*     */             
/*     */             public void set(int value) {
/* 314 */               int add = BlockIronFurnaceContainerBase.this.te.factoryCookTime[this.index] & 0xFFFF;
/* 315 */               BlockIronFurnaceContainerBase.this.te.factoryCookTime[this.index] = add | value << 16;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 320 */     for (i = 0; i < this.te.factoryTotalCookTime.length; i++) {
/*     */       
/* 322 */       addDataSlot((DataSlot)new FactoryDataSlot(i)
/*     */           {
/*     */             public int get() {
/* 325 */               return BlockIronFurnaceContainerBase.this.te.factoryTotalCookTime[this.index] & 0xFFFF;
/*     */             }
/*     */ 
/*     */             
/*     */             public void set(int value) {
/* 330 */               int add = BlockIronFurnaceContainerBase.this.te.factoryTotalCookTime[this.index] & 0xFFFF0000;
/* 331 */               BlockIronFurnaceContainerBase.this.te.factoryTotalCookTime[this.index] = add + (value & 0xFFFF);
/*     */             }
/*     */           });
/* 334 */       addDataSlot((DataSlot)new FactoryDataSlot(i)
/*     */           {
/*     */             public int get() {
/* 337 */               return BlockIronFurnaceContainerBase.this.te.factoryTotalCookTime[this.index] >> 16 & 0xFFFF;
/*     */             }
/*     */ 
/*     */             
/*     */             public void set(int value) {
/* 342 */               int add = BlockIronFurnaceContainerBase.this.te.factoryTotalCookTime[this.index] & 0xFFFF;
/* 343 */               BlockIronFurnaceContainerBase.this.te.factoryTotalCookTime[this.index] = add | value << 16;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getEnergy() {
/* 350 */     return ((Integer)this.te.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(Integer.valueOf(0))).intValue();
/*     */   }
/*     */   
/*     */   public int getMaxEnergy() {
/* 354 */     return ((Integer)this.te.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(Integer.valueOf(0))).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEnergyData() {
/* 362 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 365 */             return BlockIronFurnaceContainerBase.this.getMaxEnergy() & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 370 */             BlockIronFurnaceContainerBase.this.te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
/*     */                   int capacity = h.getMaxEnergyStored() & 0xFFFF0000;
/*     */                   ((FEnergyStorage)h).setCapacity(capacity + (value & 0xFFFF));
/*     */                 });
/*     */           }
/*     */         });
/* 376 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 379 */             return BlockIronFurnaceContainerBase.this.getMaxEnergy() >> 16 & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 384 */             BlockIronFurnaceContainerBase.this.te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
/*     */                   int capacity = h.getMaxEnergyStored() & 0xFFFF;
/*     */                   
/*     */                   ((FEnergyStorage)h).setCapacity(capacity | value << 16);
/*     */                 });
/*     */           }
/*     */         });
/* 391 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 394 */             return BlockIronFurnaceContainerBase.this.getEnergy() & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 399 */             BlockIronFurnaceContainerBase.this.te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
/*     */                   int energyStored = h.getEnergyStored() & 0xFFFF0000;
/*     */                   ((FEnergyStorage)h).setEnergy(energyStored + (value & 0xFFFF));
/*     */                 });
/*     */           }
/*     */         });
/* 405 */     addDataSlot(new DataSlot()
/*     */         {
/*     */           public int get() {
/* 408 */             return BlockIronFurnaceContainerBase.this.getEnergy() >> 16 & 0xFFFF;
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(int value) {
/* 413 */             BlockIronFurnaceContainerBase.this.te.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
/*     */                   int energyStored = h.getEnergyStored() & 0xFFFF;
/*     */                   ((FEnergyStorage)h).setEnergy(energyStored | value << 16);
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public boolean stillValid(Player player) {
/* 422 */     return this.te.stillValid(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTier() {
/* 427 */     return this.te.getTier();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoSplit() {
/* 434 */     return this.te.isAutoSplit();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRedstoneMode() {
/* 440 */     return this.te.getRedstoneSetting();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComSub() {
/* 445 */     return this.te.getRedstoneComSub();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getAutoInput() {
/* 450 */     return (this.te.getAutoInput() == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getAugmentGUI() {
/* 455 */     return (this.te.getAugmentGUI() == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIsFactory() {
/* 460 */     return this.te.isFactory();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIsFurnace() {
/* 465 */     return this.te.isFurnace();
/*     */   }
/*     */   
/*     */   public boolean getIsGenerator() {
/* 469 */     return this.te.isGenerator();
/*     */   }
/*     */   
/*     */   public boolean getAutoOutput() {
/* 473 */     return (this.te.getAutoOutput() == 1);
/*     */   }
/*     */   
/*     */   public Component getTooltip(int index) {
/* 477 */     switch (this.te.furnaceSettings.get(index)) {
/*     */       
/*     */       case 1:
/* 480 */         return (Component)Component.translatable("tooltip.ironfurnaces.gui_input");
/*     */       case 2:
/* 482 */         return (Component)Component.translatable("tooltip.ironfurnaces.gui_output");
/*     */       case 3:
/* 484 */         return (Component)Component.translatable("tooltip.ironfurnaces.gui_input_output");
/*     */       case 4:
/* 486 */         return (Component)Component.translatable("tooltip.ironfurnaces.gui_fuel");
/*     */     } 
/* 488 */     return (Component)Component.translatable("tooltip.ironfurnaces.gui_none");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSettingTop() {
/* 495 */     return this.te.getSettingTop();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSettingBottom() {
/* 501 */     return this.te.getSettingBottom();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSettingFront() {
/* 507 */     return this.te.getSettingFront();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSettingBack() {
/* 513 */     return this.te.getSettingBack();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSettingLeft() {
/* 519 */     return this.te.getSettingLeft();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSettingRight() {
/* 525 */     return this.te.getSettingRight();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndexFront() {
/* 531 */     return this.te.getIndexFront();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndexBack() {
/* 537 */     return this.te.getIndexBack();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndexLeft() {
/* 543 */     return this.te.getIndexLeft();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndexRight() {
/* 549 */     return this.te.getIndexRight();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPos() {
/* 554 */     return this.te.getBlockPos();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 559 */     return this.te.isBurning();
/*     */   }
/*     */   
/*     */   public boolean isRainbowFurnace() {
/* 563 */     return this.te.isRainbowFurnace();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCookScaled(int pixels) {
/* 568 */     int i = this.te.cookTime;
/* 569 */     int j = this.te.totalCookTime;
/* 570 */     return (j != 0 && i != 0) ? (i * pixels / j) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFactoryCookScaled(int index, int pixels) {
/* 575 */     int i = this.te.factoryCookTime[index];
/* 576 */     int j = this.te.factoryTotalCookTime[index];
/* 577 */     return (j != 0 && i != 0) ? (i * pixels / j) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFactoryCooktimeSize() {
/* 583 */     return this.te.factoryCookTime.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBurnLeftScaled(int pixels) {
/* 588 */     int i = this.te.recipesUsed;
/* 589 */     if (i == 0) {
/* 590 */       i = 200;
/*     */     }
/*     */     
/* 593 */     return this.te.furnaceBurnTime * pixels / i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGeneratorBurnScaled(int pixels) {
/* 598 */     int i = this.te.generatorRecentRecipeRF;
/* 599 */     if (i == 0) {
/* 600 */       i = 200;
/*     */     }
/* 602 */     return (int)this.te.generatorBurn * pixels / i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGeneratorBurning() {
/* 608 */     return (this.te.generatorBurn > 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyScaled(int pixels) {
/* 614 */     int i = this.te.getEnergy();
/* 615 */     int j = this.te.getCapacity();
/* 616 */     return (j != 0 && i != 0) ? (i * pixels / j) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player playerIn, int index) {
/* 621 */     ItemStack itemstack = ItemStack.EMPTY;
/* 622 */     Slot slot = (Slot)this.slots.get(index);
/*     */     
/* 624 */     if (slot != null && slot.hasItem()) {
/* 625 */       ItemStack itemstack1 = slot.getItem();
/* 626 */       itemstack = itemstack1.copy();
/* 627 */       if (this.te.isGenerator())
/*     */       {
/* 629 */         if (index != 6 && index != 3 && index != 4 && index != 5) {
/*     */           
/* 631 */           if (this.te.getItem(3).getItem() instanceof ironfurnaces.items.augments.ItemAugmentSmoking) {
/*     */             
/* 633 */             if (BlockIronFurnaceTileBase.getSmokingBurn(itemstack1) > 0 && 
/* 634 */               !moveItemStackTo(itemstack1, 6, 7, false)) {
/* 635 */               return ItemStack.EMPTY;
/*     */             
/*     */             }
/*     */           }
/* 639 */           else if (this.te.getItem(3).getItem() instanceof ironfurnaces.items.augments.ItemAugmentBlasting) {
/*     */             
/* 641 */             if (this.te.hasGeneratorBlastingRecipe(itemstack1) && 
/* 642 */               !moveItemStackTo(itemstack1, 6, 7, false)) {
/* 643 */               return ItemStack.EMPTY;
/*     */ 
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 649 */           else if (BlockIronFurnaceTileBase.isItemFuel(itemstack1, RecipeType.SMELTING) && !(itemstack1.getItem() instanceof ironfurnaces.items.ItemHeater) && 
/* 650 */             !moveItemStackTo(itemstack1, 6, 7, false)) {
/* 651 */             return ItemStack.EMPTY;
/*     */           } 
/*     */ 
/*     */           
/* 655 */           if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 0)) {
/* 656 */             if (!moveItemStackTo(itemstack1, 3, 4, false)) {
/* 657 */               return ItemStack.EMPTY;
/*     */             }
/* 659 */           } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 1)) {
/* 660 */             if (!moveItemStackTo(itemstack1, 4, 5, false)) {
/* 661 */               return ItemStack.EMPTY;
/*     */             }
/* 663 */           } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 2)) {
/* 664 */             if (!moveItemStackTo(itemstack1, 5, 6, false)) {
/* 665 */               return ItemStack.EMPTY;
/*     */             }
/* 667 */           } else if (index >= 19 && index < 45) {
/* 668 */             if (!moveItemStackTo(itemstack1, 45, 54, false)) {
/* 669 */               return ItemStack.EMPTY;
/*     */             }
/* 671 */           } else if (index >= 45 && index < 54 && !moveItemStackTo(itemstack1, 19, 45, false)) {
/* 672 */             return ItemStack.EMPTY;
/*     */           } 
/* 674 */         } else if (!moveItemStackTo(itemstack1, 19, 54, false)) {
/* 675 */           return ItemStack.EMPTY;
/*     */         } 
/*     */       }
/*     */       
/* 679 */       if (this.te.isFactory()) {
/* 680 */         if (index >= 12 && index <= 18) {
/* 681 */           if (!moveItemStackTo(itemstack1, 19, 54, true)) {
/* 682 */             return ItemStack.EMPTY;
/*     */           }
/*     */           
/* 685 */           slot.onQuickCraft(itemstack1, itemstack);
/* 686 */         } else if (index >= 19) {
/* 687 */           if (this.te.hasRecipe(itemstack1)) {
/* 688 */             if (getTier() == 2) {
/*     */               
/* 690 */               if (!moveItemStackTo(itemstack1, 7, 13, false)) {
/* 691 */                 return ItemStack.EMPTY;
/*     */               }
/*     */             }
/* 694 */             else if (getTier() == 1) {
/*     */               
/* 696 */               if (!moveItemStackTo(itemstack1, 8, 12, false)) {
/* 697 */                 return ItemStack.EMPTY;
/*     */               
/*     */               }
/*     */             
/*     */             }
/* 702 */             else if (!moveItemStackTo(itemstack1, 9, 11, false)) {
/* 703 */               return ItemStack.EMPTY;
/*     */             }
/*     */           
/*     */           }
/* 707 */           else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 0)) {
/* 708 */             if (!moveItemStackTo(itemstack1, 3, 4, false)) {
/* 709 */               return ItemStack.EMPTY;
/*     */             }
/* 711 */           } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 1)) {
/* 712 */             if (!moveItemStackTo(itemstack1, 4, 5, false)) {
/* 713 */               return ItemStack.EMPTY;
/*     */             }
/* 715 */           } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 2)) {
/* 716 */             if (!moveItemStackTo(itemstack1, 5, 6, false)) {
/* 717 */               return ItemStack.EMPTY;
/*     */             }
/* 719 */           } else if (index >= 19 && index < 45) {
/* 720 */             if (!moveItemStackTo(itemstack1, 45, 54, false)) {
/* 721 */               return ItemStack.EMPTY;
/*     */             }
/* 723 */           } else if (index >= 45 && index < 54 && !moveItemStackTo(itemstack1, 19, 45, false)) {
/* 724 */             return ItemStack.EMPTY;
/*     */           } 
/* 726 */         } else if (!moveItemStackTo(itemstack1, 19, 54, false)) {
/* 727 */           return ItemStack.EMPTY;
/*     */         } 
/*     */       }
/* 730 */       if (this.te.isFurnace())
/*     */       {
/*     */         
/* 733 */         if (index == 2) {
/* 734 */           if (!moveItemStackTo(itemstack1, 19, 54, true)) {
/* 735 */             return ItemStack.EMPTY;
/*     */           }
/*     */           
/* 738 */           slot.onQuickCraft(itemstack1, itemstack);
/* 739 */         } else if (index != 1 && index != 0 && index != 3 && index != 4 && index != 5) {
/* 740 */           if (this.te.hasRecipe(itemstack1)) {
/* 741 */             if (!moveItemStackTo(itemstack1, 0, 1, false)) {
/* 742 */               return ItemStack.EMPTY;
/*     */             }
/* 744 */           } else if (BlockIronFurnaceTileBase.isItemFuel(itemstack1, RecipeType.SMELTING)) {
/* 745 */             if (!moveItemStackTo(itemstack1, 1, 2, false)) {
/* 746 */               return ItemStack.EMPTY;
/*     */             }
/* 748 */           } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 0)) {
/* 749 */             if (!moveItemStackTo(itemstack1, 3, 4, false)) {
/* 750 */               return ItemStack.EMPTY;
/*     */             }
/* 752 */           } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 1)) {
/* 753 */             if (!moveItemStackTo(itemstack1, 4, 5, false)) {
/* 754 */               return ItemStack.EMPTY;
/*     */             }
/* 756 */           } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack1, 2)) {
/* 757 */             if (!moveItemStackTo(itemstack1, 5, 6, false)) {
/* 758 */               return ItemStack.EMPTY;
/*     */             }
/* 760 */           } else if (index >= 19 && index < 45) {
/* 761 */             if (!moveItemStackTo(itemstack1, 45, 54, false)) {
/* 762 */               return ItemStack.EMPTY;
/*     */             }
/* 764 */           } else if (index >= 45 && index < 54 && !moveItemStackTo(itemstack1, 19, 45, false)) {
/* 765 */             return ItemStack.EMPTY;
/*     */           } 
/* 767 */         } else if (!moveItemStackTo(itemstack1, 19, 54, false)) {
/* 768 */           return ItemStack.EMPTY;
/*     */         } 
/*     */       }
/* 771 */       if (itemstack1.isEmpty()) {
/* 772 */         slot.set(ItemStack.EMPTY);
/*     */       } else {
/* 774 */         slot.setChanged();
/*     */       } 
/*     */       
/* 777 */       if (itemstack1.getCount() == itemstack.getCount()) {
/* 778 */         return ItemStack.EMPTY;
/*     */       }
/*     */       
/* 781 */       slot.onTake(playerIn, itemstack1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 786 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
/* 792 */     for (int i = 0; i < amount; i++) {
/* 793 */       addSlot((Slot)new SlotItemHandler(handler, index, x, y));
/* 794 */       x += dx;
/* 795 */       index++;
/*     */     } 
/* 797 */     return index;
/*     */   }
/*     */   
/*     */   private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
/* 801 */     for (int j = 0; j < verAmount; j++) {
/* 802 */       index = addSlotRange(handler, index, x, y, horAmount, dx);
/* 803 */       y += dy;
/*     */     } 
/* 805 */     return index;
/*     */   }
/*     */ 
/*     */   
/*     */   private void layoutPlayerInventorySlots(int leftCol, int topRow) {
/* 810 */     addSlotBox(this.playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
/*     */ 
/*     */     
/* 813 */     topRow += 58;
/* 814 */     addSlotRange(this.playerInventory, 0, leftCol, topRow, 9, 18);
/*     */   }
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\furnaces\BlockIronFurnaceContainerBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */