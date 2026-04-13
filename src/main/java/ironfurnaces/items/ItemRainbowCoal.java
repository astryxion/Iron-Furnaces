/*    */ package ironfurnaces.items;
/*    */ 
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ import net.minecraft.world.item.enchantment.Enchantment;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ 
/*    */ public class ItemRainbowCoal
/*    */   extends Item
/*    */ {
/*    */   public ItemRainbowCoal(Item.Properties properties) {
/* 15 */     super(properties);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBarVisible(ItemStack p_150899_) {
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBarWidth(ItemStack stack) {
/* 25 */     return (int)(13.0D * (1.0D - stack.getDamageValue() / 5120.0D));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBarColor(ItemStack p_150901_) {
/* 30 */     float f = Math.max(0.0F, (5120.0F - p_150901_.getDamageValue()) / 5120.0F);
/* 31 */     return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
/* 36 */     return 200;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasCraftingRemainingItem(ItemStack stack) {
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
/* 48 */     ItemStack stack = new ItemStack((ItemLike)this);
/* 49 */     stack.setDamageValue(itemStack.getDamageValue() + 1);
/* 50 */     if (stack.getDamageValue() >= 5120)
/*    */     {
/* 52 */       stack = ItemStack.EMPTY;
/*    */     }
/* 54 */     return stack;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnchantable(ItemStack p_77616_1_) {
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\items\ItemRainbowCoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */