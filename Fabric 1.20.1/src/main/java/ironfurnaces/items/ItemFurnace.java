/*    */ package ironfurnaces.items;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.TooltipFlag;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ 
/*    */ public class ItemFurnace
/*    */   extends BlockItem {
/*    */   private int cooktime;
/*    */   
/*    */   public ItemFurnace(Block block, Item.Properties properties, int cooktime) {
/* 21 */     super(block, properties);
/* 22 */     this.cooktime = cooktime;
/*    */   }
/*    */ 
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
/* 28 */     tooltip.add(Component.literal("Cooktime: " + this.cooktime).withStyle(ChatFormatting.GRAY));
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\items\ItemFurnace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */