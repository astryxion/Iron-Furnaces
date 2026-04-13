/*    */ package ironfurnaces.blocks.furnaces;
/*    */ 
/*    */ import ironfurnaces.gui.furnaces.BlockIronFurnaceScreenBase;
/*    */ import ironfurnaces.util.StringHelper;
/*    */ import java.util.List;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.TooltipFlag;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ public class BlockItemHeater
/*    */   extends BlockItem
/*    */ {
/*    */   public BlockItemHeater(Block block, Item.Properties properties) {
/* 24 */     super(block, properties);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public void appendHoverText(ItemStack stack, Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
/* 31 */     if (stack.hasTag()) {
/* 32 */       assert stack.getTag() != null;
/* 33 */       tooltip.add(Component.literal(StringHelper.displayEnergy(stack.getTag().getInt("Energy"), 1000000).get(0)).withStyle(ChatFormatting.GOLD));
/*    */     } 
/* 35 */     if (BlockIronFurnaceScreenBase.isShiftKeyDown()) {
/* 36 */       tooltip.add(Component.translatable("tooltip.ironfurnaces.heater_block").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 37 */       tooltip.add(Component.translatable("tooltip.ironfurnaces.heater_block1").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/*    */     } else {
/* 39 */       tooltip.add(StringHelper.getShiftInfoText());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBarVisible(ItemStack stack) {
/* 45 */     return stack.hasTag();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBarWidth(ItemStack stack) {
/* 50 */     if (stack.hasTag()) {
/*    */       
/* 52 */       assert stack.getTag() != null;
/* 53 */       int energy = stack.getTag().getInt("Energy");
/* 54 */       return (int)(13.0D * energy / 1000000.0D);
/*    */     } 
/* 56 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBarColor(@NotNull ItemStack p_150901_) {
/* 61 */     return -8387072;
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\blocks\furnaces\BlockItemHeater.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */