/*    */ package ironfurnaces.container.slots;
/*    */ 
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class SlotIronFurnaceInputFactory extends Slot {
/*    */   BlockIronFurnaceTileBase te;
/*    */   
/*    */   public SlotIronFurnaceInputFactory(int index, BlockIronFurnaceTileBase te, int slotIndex, int x, int y) {
/* 12 */     super((Container)te, slotIndex, x, y);
/* 13 */     this.te = te;
/* 14 */     this.index = index;
/*    */   }
/*    */ 
/*    */   
/*    */   public int index;
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack stack) {
/* 22 */     return this.te.hasRecipe(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActive() {
/* 27 */     if (this.index == 0 || this.index == 5) {
/*    */       
/* 29 */       if (this.te.getTier() > 1)
/*    */       {
/* 31 */         return (this.te.isFactory() && this.te.getAugmentGUI() == 0);
/*    */       }
/*    */ 
/*    */       
/* 35 */       return false;
/*    */     } 
/*    */     
/* 38 */     if (this.index == 1 || this.index == 4) {
/*    */       
/* 40 */       if (this.te.getTier() > 0)
/*    */       {
/* 42 */         return (this.te.isFactory() && this.te.getAugmentGUI() == 0);
/*    */       }
/*    */ 
/*    */       
/* 46 */       return false;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 51 */     return (this.te.isFactory() && this.te.getAugmentGUI() == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\slots\SlotIronFurnaceInputFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */