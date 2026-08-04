/*    */ package ironfurnaces.capability;
/*    */ 
/*    */ import net.minecraftforge.common.capabilities.Capability;
/*    */ import net.minecraftforge.common.capabilities.CapabilityManager;
/*    */ import net.minecraftforge.common.capabilities.CapabilityToken;
/*    */ import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
/*    */ 
/*    */ public class CapabilityPlayerFurnacesList
/*    */ {
/* 10 */   public static final Capability<IPlayerFurnacesList> FURNACES_LIST = CapabilityManager.get(new CapabilityToken<IPlayerFurnacesList>() {  }
/*    */     );
/*    */   
/*    */   public static void register(RegisterCapabilitiesEvent event) {
/* 14 */     event.register(IPlayerFurnacesList.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\capability\CapabilityPlayerFurnacesList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */