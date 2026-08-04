/*    */ package ironfurnaces.util;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.text.DecimalFormat;
/*    */ import java.text.NumberFormat;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringHelper
/*    */ {
/*    */   public static List<String> displayEnergy(int energy, int capacity) {
/* 18 */     List<String> text = new ArrayList<>();
/* 19 */     NumberFormat format = DecimalFormat.getNumberInstance();
/* 20 */     String i = format.format(energy);
/* 21 */     String j = format.format(capacity);
/* 22 */     i = i.replaceAll(" ", ",");
/* 23 */     j = j.replaceAll(" ", ",");
/* 24 */     text.add(i + " / " + i + " RF");
/* 25 */     return text;
/*    */   }
/*    */   
/*    */   public static List<String> displayEnergy(int energy) {
/* 29 */     List<String> text = new ArrayList<>();
/* 30 */     NumberFormat format = DecimalFormat.getNumberInstance();
/* 31 */     String i = format.format(energy);
/* 32 */     i = i.replaceAll(" ", ",");
/* 33 */     text.add(i + " RF");
/* 34 */     return text;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<Component> getShiftInfoGui() {
/* 39 */     List<Component> list = Lists.newArrayList();
/* 40 */     list.add(Component.translatable("tooltip.ironfurnaces.gui_close"));
/* 41 */     MutableComponent tooltip1 = Component.translatable("tooltip.ironfurnaces.gui_hold_shift");
/* 42 */     MutableComponent shift = Component.literal("[Shift]");
/* 43 */     MutableComponent tooltip2 = Component.translatable("tooltip.ironfurnaces.gui_shift_more_options");
/* 44 */     tooltip1.withStyle(ChatFormatting.GRAY);
/* 45 */     shift.withStyle(new ChatFormatting[] { ChatFormatting.GOLD, ChatFormatting.ITALIC });
/* 46 */     tooltip2.withStyle(ChatFormatting.GRAY);
/* 47 */     list.add(tooltip1.append((Component)shift).append((Component)tooltip2));
/* 48 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Component getShiftInfoText() {
/* 53 */     MutableComponent tooltip1 = Component.translatable("tooltip.ironfurnaces.hold");
/* 54 */     MutableComponent shift = Component.literal("[Shift]");
/* 55 */     MutableComponent tooltip2 = Component.translatable("tooltip.ironfurnaces.for_details");
/* 56 */     tooltip1.withStyle(ChatFormatting.GRAY);
/* 57 */     shift.withStyle(new ChatFormatting[] { ChatFormatting.GOLD, ChatFormatting.ITALIC });
/* 58 */     tooltip2.withStyle(ChatFormatting.GRAY);
/* 59 */     return (Component)tooltip1.append((Component)shift).append((Component)tooltip2);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnace\\util\StringHelper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */