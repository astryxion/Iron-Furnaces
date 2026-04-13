/*    */ package ironfurnaces.util;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import ironfurnaces.Config;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraftforge.common.crafting.conditions.ICondition;
/*    */ import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
/*    */ 
/*    */ public class RainbowEnabledCondition
/*    */   implements ICondition {
/* 11 */   private static final ResourceLocation NAME = new ResourceLocation("ironfurnaces", "rainbow");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceLocation getID() {
/* 19 */     return NAME;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(ICondition.IContext context) {
/* 24 */     return ((Boolean)Config.enableRainbowContent.get()).booleanValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 30 */     return "enabled(\"" + String.valueOf(Config.enableRainbowContent.get()) + "\")";
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     implements IConditionSerializer<RainbowEnabledCondition> {
/* 35 */     public static final Serializer INSTANCE = new Serializer();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void write(JsonObject json, RainbowEnabledCondition value) {}
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public RainbowEnabledCondition read(JsonObject json) {
/* 46 */       return new RainbowEnabledCondition();
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public ResourceLocation getID() {
/* 52 */       return RainbowEnabledCondition.NAME;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnace\\util\RainbowEnabledCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */