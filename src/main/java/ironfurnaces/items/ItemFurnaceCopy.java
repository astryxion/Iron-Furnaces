/*    */ package ironfurnaces.items;
/*    */ 
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.TooltipFlag;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraftforge.common.world.LevelForgeHooks;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ 
/*    */ 
/*    */ public class ItemFurnaceCopy
/*    */   extends Item
/*    */ {
/*    */   public ItemFurnaceCopy(Item.Properties properties) {
/* 25 */     super(properties);
/*    */   }
/*    */ 
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
/* 31 */     if (stack.hasTag() && (
/* 32 */       stack.getTag().getIntArray("settings")).length >= 10) {
/*    */       
/* 34 */       tooltip.add(Component.literal("Down: " + stack.getTag().getIntArray("settings")[0]).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 35 */       tooltip.add(Component.literal("Up: " + stack.getTag().getIntArray("settings")[1]).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 36 */       tooltip.add(Component.literal("North: " + stack.getTag().getIntArray("settings")[2]).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 37 */       tooltip.add(Component.literal("South: " + stack.getTag().getIntArray("settings")[3]).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 38 */       tooltip.add(Component.literal("West: " + stack.getTag().getIntArray("settings")[4]).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 39 */       tooltip.add(Component.literal("East: " + stack.getTag().getIntArray("settings")[5]).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 40 */       tooltip.add(Component.literal("Auto Input: " + stack.getTag().getIntArray("settings")[6]).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 41 */       tooltip.add(Component.literal("Auto Output: " + stack.getTag().getIntArray("settings")[7]).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 42 */       tooltip.add(Component.literal("Redstone Mode: " + stack.getTag().getIntArray("settings")[8]).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/* 43 */       tooltip.add(Component.literal("Redstone Value: " + stack.getTag().getIntArray("settings")[9]).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/*    */     } 
/*    */     
/* 46 */     tooltip.add(Component.literal("Right-click to copy settings").withStyle(ChatFormatting.GRAY));
/* 47 */     tooltip.add(Component.literal("Sneak & right-click to apply settings").withStyle(ChatFormatting.GRAY));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext ctx) {
/* 56 */     Level world = ctx.getLevel();
/* 57 */     BlockPos pos = ctx.getClickedPos();
/* 58 */     if (!ctx.getPlayer().isCrouching())
/*    */     {
/* 60 */       return super.useOn(ctx);
/*    */     }
/* 62 */     if (!world.isClientSide) {
/* 63 */       BlockEntity te = world.getBlockEntity(pos);
/*    */       
/* 65 */       if (!(te instanceof BlockIronFurnaceTileBase)) {
/* 66 */         return super.useOn(ctx);
/*    */       }
/*    */       
/* 69 */       ItemStack stack = ctx.getItemInHand();
/* 70 */       if (stack.hasTag())
/*    */       {
/* 72 */         if (stack.getTag().getIntArray("settings") != null && (stack.getTag().getIntArray("settings")).length > 0) {
/*    */           
/* 74 */           int[] settings = stack.getTag().getIntArray("settings");
/* 75 */           for (int i = 0; i < settings.length; i++)
/*    */           {
/* 77 */             ((BlockIronFurnaceTileBase)te).furnaceSettings.set(i, settings[i]);
/*    */           }
/*    */         } 
/*    */       }
/* 81 */       LevelForgeHooks.markAndNotifyBlock(world,pos, world.getChunkAt(pos), world.getBlockState(pos).getBlock().defaultBlockState(), world.getBlockState(pos), 3, 3);
/* 82 */       ctx.getPlayer().sendSystemMessage((Component)Component.literal("Settings applied"));
/*    */     } 
/*    */     
/* 85 */     return super.useOn(ctx);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\items\ItemFurnaceCopy.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */