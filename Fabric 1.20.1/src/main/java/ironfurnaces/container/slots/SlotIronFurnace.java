/*    */ package ironfurnaces.container.slots;
/*    */ 
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraftforge.event.ForgeEventFactory;
/*    */ 
/*    */ public class SlotIronFurnace
/*    */   extends Slot {
/*    */   private final Player player;
/*    */   
/*    */   public SlotIronFurnace(Player player, BlockIronFurnaceTileBase te, int slotIndex, int xPosition, int yPosition) {
/* 16 */     super((Container)te, slotIndex, xPosition, yPosition);
/* 17 */     this.player = player;
/* 18 */     this.te = te;
/*    */   }
/*    */   private int removeCount; private BlockIronFurnaceTileBase te;
/*    */   
/*    */   public boolean isActive() {
/* 23 */     return (this.te.getAugmentGUI() == 0 && this.te.isFurnace());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack p_40231_) {
/* 31 */     return false;
/*    */   }
/*    */   
/*    */   public void onTake(Player thePlayer, ItemStack stack) {
/* 35 */     checkTakeAchievements(stack);
/* 36 */     super.onTake(thePlayer, stack);
/*    */   }
/*    */   
/*    */   public ItemStack remove(int p_39548_) {
/* 40 */     if (hasItem()) {
/* 41 */       this.removeCount += Math.min(p_39548_, getItem().getCount());
/*    */     }
/*    */     
/* 44 */     return super.remove(p_39548_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onQuickCraft(ItemStack stack, int p_75210_2_) {
/* 49 */     stack.onCraftedBy(this.player.level(), this.player, this.removeCount);
/* 50 */     if (!(this.player.level()).isClientSide && this.te instanceof BlockIronFurnaceTileBase) {
/* 51 */       this.te.unlockRecipes((ServerPlayer)this.player);
/*    */     }
/*    */     
/* 54 */     this.removeCount = 0;
/*    */   }
/*    */   
/*    */   protected void checkTakeAchievements(ItemStack p_39558_) {
/* 58 */     p_39558_.onCraftedBy(this.player.level(), this.player, this.removeCount);
/* 59 */     if (this.player instanceof ServerPlayer && this.container instanceof BlockIronFurnaceTileBase) {
/* 60 */       ((BlockIronFurnaceTileBase)this.container).unlockRecipes((ServerPlayer)this.player);
/*    */     }
/*    */     
/* 63 */     this.removeCount = 0;
/* 64 */     ForgeEventFactory.firePlayerSmeltedEvent(this.player, p_39558_);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\slots\SlotIronFurnace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */