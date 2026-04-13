/*    */ package ironfurnaces.items;
/*    */ 
/*    */ import ironfurnaces.gui.furnaces.BlockIronFurnaceScreenBase;
/*    */ import ironfurnaces.util.StringHelper;
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
/*    */ 
/*    */ 
/*    */ public class ItemHeater
/*    */   extends Item
/*    */ {
/*    */   public ItemHeater(Item.Properties properties) {
/* 22 */     super(properties);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
/* 30 */     if (BlockIronFurnaceScreenBase.isShiftKeyDown()) {
/*    */       
/* 32 */       if (stack.hasTag()) {
/* 33 */         tooltip.add(Component.translatable("tooltip.ironfurnaces.heater").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 34 */         tooltip.add(Component.translatable("tooltip.ironfurnaces.heaterX").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)).append((Component)Component.literal("" + stack.getTag().getInt("X")).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY))));
/* 35 */         tooltip.add(Component.translatable("tooltip.ironfurnaces.heaterY").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)).append((Component)Component.literal("" + stack.getTag().getInt("Y")).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY))));
/* 36 */         tooltip.add(Component.translatable("tooltip.ironfurnaces.heaterZ").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)).append((Component)Component.literal("" + stack.getTag().getInt("Z")).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY))));
/*    */       } else {
/* 38 */         tooltip.add(Component.translatable("tooltip.ironfurnaces.heater_not_bound").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 39 */         tooltip.add(Component.translatable("tooltip.ironfurnaces.heater_tip").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 40 */         tooltip.add(Component.translatable("tooltip.ironfurnaces.heater_tip1").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 45 */       tooltip.add(StringHelper.getShiftInfoText());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\items\ItemHeater.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */