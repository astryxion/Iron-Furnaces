/*    */ package ironfurnaces.network;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraftforge.network.NetworkRegistry;
/*    */ import net.minecraftforge.network.simple.SimpleChannel;
/*    */ 
/*    */ 
/*    */ public class Messages
/*    */ {
/*    */   public static SimpleChannel INSTANCE;
/* 12 */   private static int ID = 0;
/*    */   private static int nextID() {
/* 14 */     return ID++;
/*    */   }
/*    */   
/*    */   public static void registerMessages(String channelName) {
/* 18 */     INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("ironfurnaces", channelName), () -> "1.0", s -> true, s -> true);
/*    */ 
/*    */ 
/*    */     
/* 22 */     INSTANCE.registerMessage(nextID(), PacketSettingsButton.class, PacketSettingsButton::toBytes, PacketSettingsButton::new, PacketSettingsButton::handle);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 27 */     INSTANCE.registerMessage(nextID(), PacketShowConfigButton.class, PacketShowConfigButton::toBytes, PacketShowConfigButton::new, PacketShowConfigButton::handle);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\network\Messages.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */