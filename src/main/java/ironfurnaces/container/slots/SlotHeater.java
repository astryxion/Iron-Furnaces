/*    */ package ironfurnaces.container.slots;
/*    */ 
/*    */ import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class SlotHeater
/*    */   extends Slot {
/*    */   private BlockWirelessEnergyHeaterTile te;
/*    */   
/*    */   public SlotHeater(BlockWirelessEnergyHeaterTile te, int slotIndex, int xPosition, int yPosition) {
/* 13 */     super((Container)te, slotIndex, xPosition, yPosition);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack stack) {
/* 18 */     return stack.getItem() instanceof ironfurnaces.items.ItemHeater;
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\slots\SlotHeater.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */