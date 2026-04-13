/*    */ package ironfurnaces.energy;
/*    */ 
/*    */ import net.minecraftforge.energy.EnergyStorage;
/*    */ 
/*    */ public class FEnergyStorage
/*    */   extends EnergyStorage {
/*    */   public FEnergyStorage(int capacity) {
/*  8 */     super(capacity);
/*    */   }
/*    */   
/*    */   public FEnergyStorage(int capacity, int maxTransfer) {
/* 12 */     super(capacity, maxTransfer);
/*    */   }
/*    */   
/*    */   public FEnergyStorage(int capacity, int maxReceive, int maxExtract) {
/* 16 */     super(capacity, maxReceive, maxExtract);
/*    */   }
/*    */   
/*    */   public FEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
/* 20 */     super(capacity, maxReceive, maxExtract, energy);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onEnergyChanged() {}
/*    */ 
/*    */   
/*    */   public int getEnergy() {
/* 28 */     return getEnergyStored();
/*    */   }
/*    */   
/*    */   public int getCapacity() {
/* 32 */     return getMaxEnergyStored();
/*    */   }
/*    */ 
/*    */   
/*    */   public EnergyStorage setCapacity(int capacity) {
/* 37 */     this.capacity = capacity;
/*    */     
/* 39 */     if (this.energy > capacity) {
/* 40 */       this.energy = capacity;
/*    */     }
/* 42 */     onEnergyChanged();
/* 43 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnergyStorage setMaxTransfer(int maxTransfer) {
/* 48 */     setMaxReceive(maxTransfer);
/* 49 */     setMaxExtract(maxTransfer);
/* 50 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnergyStorage setMaxReceive(int maxReceive) {
/* 55 */     this.maxReceive = maxReceive;
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnergyStorage setMaxExtract(int maxExtract) {
/* 61 */     this.maxExtract = maxExtract;
/* 62 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxReceive() {
/* 67 */     return this.maxReceive;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxExtract() {
/* 72 */     return this.maxExtract;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEnergy(int energy) {
/* 77 */     this.energy = energy;
/*    */     
/* 79 */     if (this.energy > this.capacity) {
/* 80 */       this.energy = this.capacity;
/* 81 */     } else if (this.energy < 0) {
/* 82 */       this.energy = 0;
/*    */     } 
/* 84 */     onEnergyChanged();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCapacityDirectly(int capacity) {
/* 89 */     this.capacity = capacity;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEnergyDirectly(int energy) {
/* 94 */     this.energy = energy;
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\energy\FEnergyStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */