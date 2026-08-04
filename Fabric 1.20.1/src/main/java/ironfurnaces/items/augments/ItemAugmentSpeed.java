/*    */ package ironfurnaces.items.augments;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ 
/*    */ public class ItemAugmentSpeed
/*    */   extends ItemAugmentGreen
/*    */ {
/*    */   public ItemAugmentSpeed(Item.Properties properties) {
/* 20 */     super(properties);
/*    */   }
/*    */ 
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
/* 26 */     super.appendHoverText(stack, worldIn, tooltip, flagIn);
/* 27 */     tooltip.add(Component.translatable("tooltip.ironfurnaces.augment_speed_pro").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN)));
/* 28 */     tooltip.add(Component.translatable("tooltip.ironfurnaces.augment_speed_con").setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_RED)));
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\items\augments\ItemAugmentSpeed.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */