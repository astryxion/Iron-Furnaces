/*    */ package ironfurnaces.container.slots;
/*    */ 
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class SlotIronFurnaceAugmentRed
/*    */   extends Slot {
/*    */   private BlockIronFurnaceTileBase te;
/*    */   
/*    */   public SlotIronFurnaceAugmentRed(BlockIronFurnaceTileBase te, int slotIndex, int xPosition, int yPosition) {
/* 13 */     super((Container)te, slotIndex, xPosition, yPosition);
/* 14 */     this.te = te;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack stack) {
/* 19 */     return stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentRed;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxStackSize() {
/* 24 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setChanged() {
/* 29 */     this.te.onUpdateSent();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActive() {
/* 34 */     return (this.te.getAugmentGUI() == 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\slots\SlotIronFurnaceAugmentRed.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */