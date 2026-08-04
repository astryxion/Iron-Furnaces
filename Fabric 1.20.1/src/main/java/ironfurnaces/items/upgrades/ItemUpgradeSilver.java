/*   */ package ironfurnaces.items.upgrades;
/*   */ 
/*   */ import ironfurnaces.init.Registration;
/*   */ import net.minecraft.world.item.Item;
/*   */ import net.minecraft.world.level.block.Block;
/*   */ 
/*   */ public class ItemUpgradeSilver extends ItemUpgrade {
/*   */   public ItemUpgradeSilver(Item.Properties properties) {
/* 9 */     super(properties, (Block)Registration.COPPER_FURNACE.get(), (Block)Registration.SILVER_FURNACE.get());
/*   */   }
/*   */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\item\\upgrades\ItemUpgradeSilver.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */