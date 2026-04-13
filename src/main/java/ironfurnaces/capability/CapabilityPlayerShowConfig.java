/*    */ package ironfurnaces.capability;
/*    */ 
/*    */ import net.minecraftforge.common.capabilities.Capability;
/*    */ import net.minecraftforge.common.capabilities.CapabilityManager;
/*    */ import net.minecraftforge.common.capabilities.CapabilityToken;
/*    */ import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
/*    */ 
/*    */ public class CapabilityPlayerShowConfig
/*    */ {
/* 10 */   public static final Capability<IPlayerShowConfig> CONFIG = CapabilityManager.get(new CapabilityToken<IPlayerShowConfig>() {  }
/*    */     );
/*    */   
/*    */   public static void register(RegisterCapabilitiesEvent event) {
/* 14 */     event.register(IPlayerShowConfig.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\capability\CapabilityPlayerShowConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */