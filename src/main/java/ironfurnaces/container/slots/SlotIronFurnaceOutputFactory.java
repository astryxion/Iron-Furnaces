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
/*    */ public class SlotIronFurnaceOutputFactory extends Slot {
/*    */   private final Player player;
/*    */   private int removeCount;
/*    */   private BlockIronFurnaceTileBase te;
/*    */   public int index;
/*    */   
/*    */   public SlotIronFurnaceOutputFactory(int index, Player player, BlockIronFurnaceTileBase te, int slotIndex, int xPosition, int yPosition) {
/* 18 */     super((Container)te, slotIndex, xPosition, yPosition);
/* 19 */     this.player = player;
/* 20 */     this.te = te;
/* 21 */     this.index = index;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActive() {
/* 26 */     if (this.index == 0 || this.index == 5) {
/*    */       
/* 28 */       if (this.te.getTier() > 1)
/*    */       {
/* 30 */         return (this.te.isFactory() && this.te.getAugmentGUI() == 0);
/*    */       }
/*    */     }
/* 33 */     else if (this.index == 1 || this.index == 4) {
/*    */       
/* 35 */       if (this.te.getTier() > 0)
/*    */       {
/* 37 */         return (this.te.isFactory() && this.te.getAugmentGUI() == 0);
/*    */       }
/*    */     }
/*    */     else {
/*    */       
/* 42 */       return (this.te.isFactory() && this.te.getAugmentGUI() == 0);
/*    */     } 
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack p_40231_) {
/* 52 */     return false;
/*    */   }
/*    */   
/*    */   public void onTake(Player thePlayer, ItemStack stack) {
/* 56 */     checkTakeAchievements(stack);
/* 57 */     super.onTake(thePlayer, stack);
/*    */   }
/*    */   
/*    */   public ItemStack remove(int p_39548_) {
/* 61 */     if (hasItem()) {
/* 62 */       this.removeCount += Math.min(p_39548_, getItem().getCount());
/*    */     }
/*    */     
/* 65 */     return super.remove(p_39548_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onQuickCraft(ItemStack stack, int p_75210_2_) {
/* 70 */     stack.onCraftedBy(this.player.level(), this.player, this.removeCount);
/* 71 */     if (!(this.player.level()).isClientSide && this.te instanceof BlockIronFurnaceTileBase) {
/* 72 */       this.te.unlockRecipes((ServerPlayer)this.player);
/*    */     }
/*    */     
/* 75 */     this.removeCount = 0;
/*    */   }
/*    */   
/*    */   protected void checkTakeAchievements(ItemStack p_39558_) {
/* 79 */     p_39558_.onCraftedBy(this.player.level(), this.player, this.removeCount);
/* 80 */     if (this.player instanceof ServerPlayer && this.container instanceof BlockIronFurnaceTileBase) {
/* 81 */       ((BlockIronFurnaceTileBase)this.container).unlockRecipes((ServerPlayer)this.player);
/*    */     }
/*    */     
/* 84 */     this.removeCount = 0;
/* 85 */     ForgeEventFactory.firePlayerSmeltedEvent(this.player, p_39558_);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\slots\SlotIronFurnaceOutputFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */