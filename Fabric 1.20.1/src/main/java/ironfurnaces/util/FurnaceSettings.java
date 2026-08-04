/*     */ package ironfurnaces.util;
/*     */ 
/*     */ import ironfurnaces.Config;
/*     */ import ironfurnaces.IronFurnaces;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FurnaceSettings
/*     */ {
/*  15 */   public int[] settings = new int[] { 0, 0, 0, 0, 0, 0 };
/*  16 */   public int[] autoIO = new int[] { 0, 0 };
/*     */   
/*  18 */   public int[] redstoneSettings = new int[] { 0, 0 };
/*  19 */   public int augmentGUI = 0;
/*  20 */   public int autoSplit = 0;
/*     */ 
/*     */   
/*     */   public int get(int index) {
/*     */     try {
/*  25 */       switch (index) {
/*     */         case 0:
/*  27 */           return this.settings[0];
/*     */         case 1:
/*  29 */           return this.settings[1];
/*     */         case 2:
/*  31 */           return this.settings[2];
/*     */         case 3:
/*  33 */           return this.settings[3];
/*     */         case 4:
/*  35 */           return this.settings[4];
/*     */         case 5:
/*  37 */           return this.settings[5];
/*     */         case 6:
/*  39 */           return this.autoIO[0];
/*     */         case 7:
/*  41 */           return this.autoIO[1];
/*     */         case 8:
/*  43 */           return this.redstoneSettings[0];
/*     */         case 9:
/*  45 */           return this.redstoneSettings[1];
/*     */         case 10:
/*  47 */           return this.augmentGUI;
/*     */         case 11:
/*  49 */           return this.autoSplit;
/*     */       } 
/*  51 */       return 0;
/*     */     }
/*  53 */     catch (ArrayIndexOutOfBoundsException e) {
/*  54 */       if (((Boolean)Config.showErrors.get()).booleanValue()) {
/*  55 */         IronFurnaces.LOGGER.error("Something went wrong.");
/*  56 */         for (int i = 0; i < (e.getStackTrace()).length; i++) {
/*  57 */           IronFurnaces.LOGGER.error(e.getStackTrace()[i].toString());
/*     */         }
/*     */       } 
/*     */       
/*  61 */       return 0;
/*     */     } 
/*     */   }
/*     */   public void set(int index, int value) {
/*     */     try {
/*  66 */       switch (index) {
/*     */         case 0:
/*  68 */           this.settings[0] = value;
/*     */           break;
/*     */         case 1:
/*  71 */           this.settings[1] = value;
/*     */           break;
/*     */         case 2:
/*  74 */           this.settings[2] = value;
/*     */           break;
/*     */         case 3:
/*  77 */           this.settings[3] = value;
/*     */           break;
/*     */         case 4:
/*  80 */           this.settings[4] = value;
/*     */           break;
/*     */         case 5:
/*  83 */           this.settings[5] = value;
/*     */           break;
/*     */         case 6:
/*  86 */           this.autoIO[0] = value;
/*     */           break;
/*     */         case 7:
/*  89 */           this.autoIO[1] = value;
/*     */           break;
/*     */         case 8:
/*  92 */           this.redstoneSettings[0] = value;
/*     */           break;
/*     */         case 9:
/*  95 */           this.redstoneSettings[1] = value;
/*     */           break;
/*     */         case 10:
/*  98 */           this.augmentGUI = value;
/*     */           break;
/*     */         case 11:
/* 101 */           this.autoSplit = value;
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 106 */       onChanged();
/* 107 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 108 */       if (((Boolean)Config.showErrors.get()).booleanValue()) {
/* 109 */         IronFurnaces.LOGGER.error("Something went wrong.");
/* 110 */         for (int i = 0; i < (e.getStackTrace()).length; i++) {
/* 111 */           IronFurnaces.LOGGER.error(e.getStackTrace()[i].toString());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int size() {
/* 118 */     return this.settings.length + this.autoIO.length + this.redstoneSettings.length + 2;
/*     */   }
/*     */   
/*     */   public void read(CompoundTag tag) {
/* 122 */     this.settings = tag.getIntArray("Settings");
/* 123 */     this.autoIO = tag.getIntArray("AutoIO");
/* 124 */     this.redstoneSettings = tag.getIntArray("Redstone");
/* 125 */     this.augmentGUI = tag.getInt("AugmentGUI");
/* 126 */     this.autoSplit = tag.getInt("AutoSplit");
/* 127 */     onChanged();
/*     */   }
/*     */   
/*     */   public void write(CompoundTag tag) {
/* 131 */     tag.putIntArray("Settings", this.settings);
/* 132 */     tag.putIntArray("AutoIO", this.autoIO);
/* 133 */     tag.putIntArray("Redstone", this.redstoneSettings);
/* 134 */     tag.putInt("AugmentGUI", this.augmentGUI);
/* 135 */     tag.putInt("AutoSplit", this.autoSplit);
/*     */   }
/*     */   
/*     */   public void onChanged() {}
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnace\\util\FurnaceSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */