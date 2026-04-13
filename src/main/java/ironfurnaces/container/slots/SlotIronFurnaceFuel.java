/*    */ package ironfurnaces.container.slots;
/*    */ 
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ 
/*    */ public class SlotIronFurnaceFuel
/*    */   extends Slot {
/*    */   public SlotIronFurnaceFuel(BlockIronFurnaceTileBase te, int index, int x, int y) {
/* 13 */     super((Container)te, index, x, y);
/* 14 */     this.te = te;
/*    */   }
/*    */ 
/*    */   
/*    */   BlockIronFurnaceTileBase te;
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack stack) {
/* 22 */     return (BlockIronFurnaceTileBase.isItemFuel(stack, RecipeType.SMELTING) || isBucket(stack));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxStackSize(ItemStack stack) {
/* 27 */     return isBucket(stack) ? 1 : super.getMaxStackSize(stack);
/*    */   }
/*    */   
/*    */   public static boolean isBucket(ItemStack stack) {
/* 31 */     return (stack.getItem() == Items.BUCKET);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActive() {
/* 36 */     return (this.te.getAugmentGUI() == 0 && this.te.isFurnace());
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\slots\SlotIronFurnaceFuel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */