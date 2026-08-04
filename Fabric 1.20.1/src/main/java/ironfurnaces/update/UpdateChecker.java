/*    */ package ironfurnaces.update;
/*    */ 
/*    */ import ironfurnaces.Config;
/*    */ import ironfurnaces.IronFurnaces;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.client.resources.language.I18n;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.event.TickEvent;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber
/*    */ public class UpdateChecker
/*    */ {
/*    */   public static final String DOWNLOAD_LINK = "https://www.curseforge.com/minecraft/mc-mods/iron-furnaces";
/*    */   public static final String CHANGELOG_LINK = "https://raw.githubusercontent.com/Qelifern/IronFurnaces/1.20.1/ifchangelog.txt";
/*    */   public static boolean checkFailed;
/*    */   public static boolean needsUpdateNotify;
/*    */   public static int updateVersionInt;
/*    */   public static String updateVersionString;
/*    */   public static boolean threadFinished = false;
/*    */   
/*    */   public UpdateChecker() {
/* 35 */     IronFurnaces.LOGGER.info("Initializing Update Checker...");
/* 36 */     if (!((Boolean)Config.disableWebContent.get()).booleanValue()) {
/*    */       
/* 38 */       new ThreadUpdateChecker();
/* 39 */       MinecraftForge.EVENT_BUS.register(this);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   @SubscribeEvent(receiveCanceled = true)
/*    */   public void onTick(TickEvent.ClientTickEvent event) {
/* 48 */     if (!((Boolean)Config.disableWebContent.get()).booleanValue())
/*    */     {
/* 50 */       if ((Minecraft.getInstance()).player != null) {
/* 51 */         LocalPlayer localPlayer = (Minecraft.getInstance()).player;
/* 52 */         int id = 0;
/* 53 */         if (checkFailed) {
/* 54 */           localPlayer.sendSystemMessage((Component)Component.Serializer.fromJson(I18n.get("ironfurnaces.update.failed", new Object[0])));
/* 55 */         } else if (needsUpdateNotify) {
/* 56 */           localPlayer.sendSystemMessage((Component)Component.Serializer.fromJson(I18n.get("ironfurnaces.update.speech", new Object[0])));
/* 57 */           localPlayer.sendSystemMessage((Component)Component.Serializer.fromJson(I18n.get("ironfurnaces.update.version", new Object[] { "1.20.1-beta418", updateVersionString })));
/* 58 */           localPlayer.sendSystemMessage((Component)Component.Serializer.fromJson(I18n.get("ironfurnaces.update.buttons", new Object[] { "https://raw.githubusercontent.com/Qelifern/IronFurnaces/1.20.1/ifchangelog.txt", "https://www.curseforge.com/minecraft/mc-mods/iron-furnaces" })));
/*    */         } 
/* 60 */         if (threadFinished) MinecraftForge.EVENT_BUS.unregister(this); 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnace\\update\UpdateChecker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */