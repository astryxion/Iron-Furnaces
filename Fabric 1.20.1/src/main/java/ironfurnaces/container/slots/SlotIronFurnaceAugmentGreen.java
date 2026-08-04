/*    */ package ironfurnaces.container.slots;
/*    */ 
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class SlotIronFurnaceAugmentGreen
/*    */   extends Slot
/*    */ {
/*    */   private BlockIronFurnaceTileBase te;
/*    */   
/*    */   public SlotIronFurnaceAugmentGreen(BlockIronFurnaceTileBase te, int slotIndex, int xPosition, int yPosition) {
/* 14 */     super((Container)te, slotIndex, xPosition, yPosition);
/* 15 */     this.te = te;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack stack) {
/* 20 */     return stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentGreen;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxStackSize() {
/* 25 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setChanged() {
/* 30 */     this.te.onUpdateSent();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActive() {
/* 35 */     return (this.te.getAugmentGUI() == 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\slots\SlotIronFurnaceAugmentGreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */