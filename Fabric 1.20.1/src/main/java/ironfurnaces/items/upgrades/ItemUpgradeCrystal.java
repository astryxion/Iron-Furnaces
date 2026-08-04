/*   */ package ironfurnaces.items.upgrades;
/*   */ 
/*   */ import ironfurnaces.init.Registration;
/*   */ import net.minecraft.world.item.Item;
/*   */ import net.minecraft.world.level.block.Block;
/*   */ 
/*   */ public class ItemUpgradeCrystal extends ItemUpgrade {
/*   */   public ItemUpgradeCrystal(Item.Properties properties) {
/* 9 */     super(properties, (Block)Registration.DIAMOND_FURNACE.get(), (Block)Registration.CRYSTAL_FURNACE.get());
/*   */   }
/*   */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\item\\upgrades\ItemUpgradeCrystal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */