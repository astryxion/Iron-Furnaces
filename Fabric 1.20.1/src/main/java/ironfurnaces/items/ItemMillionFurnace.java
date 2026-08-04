/*    */ package ironfurnaces.items;
/*    */ import com.google.common.collect.Lists;
/*    */ import ironfurnaces.Config;
/*    */ import ironfurnaces.gui.furnaces.BlockIronFurnaceScreenBase;
/*    */ import ironfurnaces.util.StringHelper;
/*    */ import java.text.DecimalFormat;
/*    */ import java.text.Format;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.TooltipFlag;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ 
/*    */ public class ItemMillionFurnace extends BlockItem {
/*    */   private Random rand;
/*    */   
/*    */   public ItemMillionFurnace(Block blockIn, Item.Properties builder) {
/* 27 */     super(blockIn, builder);
/*    */ 
/*    */     
/* 30 */     this.rand = new Random();
/* 31 */     this.timer = 0;
/*    */   }
/*    */   private int timer;
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
/* 36 */     tooltip.add(Component.literal("Cooktime: " + String.valueOf(Config.millionFurnaceSpeed.get())).withStyle(ChatFormatting.GRAY));
/* 37 */     this.timer++;
/* 38 */     if (this.timer % 20 == 0) {
/* 39 */       this.timer = 0;
/* 40 */       String name = Component.translatable("block.ironfurnaces.million_furnace").getString();
/* 41 */       ArrayList<Component> names = Lists.newArrayList();
/* 42 */       for (int i = 0; i < name.length(); i++) {
/* 43 */         names.add(Component.literal("" + name.charAt(i)).withStyle(ChatFormatting.getById(getIDRandom(this.rand.nextInt(6)))));
/*    */       }
/* 45 */       MutableComponent component = Component.literal("");
/* 46 */       for (int j = 0; j < names.size(); j++) {
/* 47 */         component.append(names.get(j));
/*    */       }
/* 49 */       stack.setHoverName((Component)component);
/*    */     } 
/*    */ 
/*    */     
/* 53 */     Format decimal = new DecimalFormat();
/* 54 */     String part1 = Component.translatable("tooltip.ironfurnaces.rainbow_gen1").getString();
/* 55 */     String part2 = Component.translatable("tooltip.ironfurnaces.rainbow_gen2").getString();
/* 56 */     tooltip.add(Component.literal(part1 + " " + part1 + " " + decimal.format(Config.millionFurnacePowerToGenerate.get()).toString().replaceAll(" ", ",")).withStyle(ChatFormatting.GRAY));
/*    */ 
/*    */ 
/*    */     
/* 60 */     if (BlockIronFurnaceScreenBase.isShiftKeyDown()) {
/*    */       
/* 62 */       tooltip.add(Component.translatable("tooltip.ironfurnaces.rainbow_gen3").withStyle(ChatFormatting.GRAY));
/* 63 */       tooltip.add(Component.translatable("tooltip.ironfurnaces.rainbow_gen4").withStyle(ChatFormatting.GRAY));
/* 64 */       tooltip.add(Component.translatable("tooltip.ironfurnaces.rainbow_gen5").withStyle(ChatFormatting.GRAY));
/*    */     }
/*    */     else {
/*    */       
/* 68 */       tooltip.add(StringHelper.getShiftInfoText());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getIDRandom(int id) {
/* 75 */     switch (id) {
/*    */       
/*    */       case 0:
/* 78 */         return 12;
/*    */       case 1:
/* 80 */         return 14;
/*    */       case 2:
/* 82 */         return 10;
/*    */       case 3:
/* 84 */         return 11;
/*    */       case 4:
/* 86 */         return 9;
/*    */       case 5:
/* 88 */         return 13;
/*    */       case 6:
/* 90 */         return 5;
/*    */     } 
/* 92 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\items\ItemMillionFurnace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */