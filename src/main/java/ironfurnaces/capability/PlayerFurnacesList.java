/*    */ package ironfurnaces.capability;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerFurnacesList
/*    */   implements IPlayerFurnacesList
/*    */ {
/* 18 */   public List<BlockPos> listFurances = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<BlockPos> get() {
/* 24 */     return this.listFurances;
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(BlockPos pos) {
/* 29 */     int check = 0;
/* 30 */     for (int i = 0; i < this.listFurances.size(); i++) {
/*    */       
/* 32 */       if (((BlockPos)this.listFurances.get(i)).getX() == pos.getX() && ((BlockPos)this.listFurances.get(i)).getY() == pos.getY() && ((BlockPos)this.listFurances.get(i)).getZ() == pos.getZ())
/*    */       {
/* 34 */         check++;
/*    */       }
/*    */     } 
/* 37 */     if (check == 0)
/*    */     {
/* 39 */       this.listFurances.add(pos);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove(BlockPos pos) {
/* 45 */     for (int i = 0; i < this.listFurances.size(); i++) {
/*    */       
/* 47 */       if (((BlockPos)this.listFurances.get(i)).getX() == pos.getX() && ((BlockPos)this.listFurances.get(i)).getY() == pos.getY() && ((BlockPos)this.listFurances.get(i)).getZ() == pos.getZ())
/*    */       {
/* 49 */         this.listFurances.remove(i);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\capability\PlayerFurnacesList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */