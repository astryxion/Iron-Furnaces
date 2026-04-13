/*    */ package ironfurnaces.container.slots;
/*    */ 
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class SlotIronFurnaceInput extends Slot {
/*    */   private BlockIronFurnaceTileBase te;
/*    */   
/*    */   public SlotIronFurnaceInput(BlockIronFurnaceTileBase te, int slotIndex, int xPosition, int yPosition) {
/* 12 */     super((Container)te, slotIndex, xPosition, yPosition);
/* 13 */     this.te = te;
/*    */   }
/*    */   
/*    */   public boolean mayPlace(ItemStack stack) {
/* 17 */     return this.te.hasRecipe(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActive() {
/* 22 */     return (this.te.getAugmentGUI() == 0 && this.te.isFurnace());
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\slots\SlotIronFurnaceInput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */