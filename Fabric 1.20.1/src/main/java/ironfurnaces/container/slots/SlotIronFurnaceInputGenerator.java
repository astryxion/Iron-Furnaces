/*    */ package ironfurnaces.container.slots;
/*    */ 
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ 
/*    */ public class SlotIronFurnaceInputGenerator
/*    */   extends Slot
/*    */ {
/*    */   BlockIronFurnaceTileBase te;
/*    */   
/*    */   public SlotIronFurnaceInputGenerator(BlockIronFurnaceTileBase te, int index, int x, int y) {
/* 15 */     super((Container)te, index, x, y);
/* 16 */     this.te = te;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack stack) {
/* 24 */     if (!this.te.getItem(3).isEmpty()) {
/*    */       
/* 26 */       if (this.te.getItem(3).getItem() instanceof ironfurnaces.items.augments.ItemAugmentBlasting)
/*    */       {
/* 28 */         return this.te.hasGeneratorBlastingRecipe(stack);
/*    */       }
/* 30 */       if (this.te.getItem(3).getItem() instanceof ironfurnaces.items.augments.ItemAugmentSmoking)
/*    */       {
/* 32 */         return (BlockIronFurnaceTileBase.getSmokingBurn(stack) > 0);
/*    */       }
/*    */     } 
/* 35 */     if (stack.getItem() instanceof ironfurnaces.items.ItemHeater)
/*    */     {
/* 37 */       return false;
/*    */     }
/* 39 */     return BlockIronFurnaceTileBase.isItemFuel(stack, RecipeType.SMELTING);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActive() {
/* 44 */     return (this.te.isGenerator() && this.te.getAugmentGUI() == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\slots\SlotIronFurnaceInputGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */