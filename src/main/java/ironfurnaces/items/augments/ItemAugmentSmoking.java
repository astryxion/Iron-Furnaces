/*    */ package ironfurnaces.items.augments;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.TooltipFlag;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ 
/*    */ public class ItemAugmentSmoking
/*    */   extends ItemAugmentRed
/*    */ {
/*    */   public ItemAugmentSmoking(Item.Properties properties) {
/* 18 */     super(properties);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
/* 25 */     super.appendHoverText(stack, worldIn, tooltip, flagIn);
/* 26 */     tooltip.add(Component.translatable("tooltip.ironfurnaces.augment_smoking").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\items\augments\ItemAugmentSmoking.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */