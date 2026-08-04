/*    */ package ironfurnaces.capability;
/*    */ 
/*    */ public class PlayerShowConfig
/*    */   implements IPlayerShowConfig
/*    */ {
/*    */   public int value;
/*    */   
/*    */   public PlayerShowConfig(int value) {
/*  9 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int get() {
/* 14 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int set(int value) {
/* 19 */     return this.value = value;
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\capability\PlayerShowConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */