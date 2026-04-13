/*    */ package ironfurnaces.items;
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
/*    */ 
/*    */ public class ItemXmas
/*    */   extends Item
/*    */ {
/*    */   public ItemXmas(Item.Properties properties) {
/* 21 */     super(properties);
/*    */   }
/*    */ 
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
/* 27 */     tooltip.add(Component.translatable("tooltip.ironfurnaces.xmas_right_click").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 28 */     tooltip.add(Component.translatable("tooltip.ironfurnaces.xmas1").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 29 */     tooltip.add(Component.translatable("tooltip.ironfurnaces.xmas2").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\items\ItemXmas.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */