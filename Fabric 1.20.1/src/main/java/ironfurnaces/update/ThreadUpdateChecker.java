/*    */ package ironfurnaces.update;
/*    */ 
/*    */ import ironfurnaces.IronFurnaces;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.util.Properties;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThreadUpdateChecker
/*    */   extends Thread
/*    */ {
/*    */   public ThreadUpdateChecker() {
/* 18 */     setName("Iron Furnaces Update Checker");
/* 19 */     setDaemon(true);
/* 20 */     start();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 26 */     IronFurnaces.LOGGER.info("Starting Update Check...");
/*    */     try {
/* 28 */       URL newestURL = new URL("https://raw.githubusercontent.com/Qelifern/IronFurnaces/1.20.1/update/updateVersions.properties");
/* 29 */       Properties updateProperties = new Properties();
/* 30 */       updateProperties.load(new InputStreamReader(newestURL.openStream()));
/*    */       
/* 32 */       String currentMcVersion = "1.20.1";
/* 33 */       String newestVersionProp = updateProperties.getProperty(currentMcVersion);
/*    */       
/* 35 */       UpdateChecker.updateVersionInt = Integer.parseInt(newestVersionProp);
/* 36 */       UpdateChecker.updateVersionString = currentMcVersion + "-release" + currentMcVersion;
/*    */       
/* 38 */       int clientVersion = Integer.parseInt("418");
/* 39 */       if (UpdateChecker.updateVersionInt > clientVersion) {
/* 40 */         UpdateChecker.needsUpdateNotify = true;
/*    */       }
/*    */       
/* 43 */       IronFurnaces.LOGGER.info("Update Check done!");
/* 44 */     } catch (Exception e) {
/* 45 */       IronFurnaces.LOGGER.error("Update Check failed!", e);
/* 46 */       UpdateChecker.checkFailed = true;
/*    */     } 
/*    */     
/* 49 */     if (!UpdateChecker.checkFailed) {
/* 50 */       if (UpdateChecker.needsUpdateNotify) {
/* 51 */         IronFurnaces.LOGGER.info("There is an Update for Iron Furnaces available!");
/* 52 */         IronFurnaces.LOGGER.info("Current Version: 1.20.1-418, newest Version: " + UpdateChecker.updateVersionString + "!");
/* 53 */         IronFurnaces.LOGGER.info("View the Changelog at https://raw.githubusercontent.com/Qelifern/IronFurnaces/1.20.1/ifchangelog.txt");
/* 54 */         IronFurnaces.LOGGER.info("Download at https://www.curseforge.com/minecraft/mc-mods/iron-furnaces");
/*    */       } else {
/* 56 */         IronFurnaces.LOGGER.info("Iron Furnaces is up to date!");
/*    */       } 
/*    */     }
/*    */     
/* 60 */     UpdateChecker.threadFinished = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnace\\update\ThreadUpdateChecker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */