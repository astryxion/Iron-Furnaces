/*    */ package ironfurnaces.util;
/*    */ 
/*    */ import net.minecraft.core.Direction;
/*    */ 
/*    */ 
/*    */ public class DirectionUtil
/*    */ {
/*    */   public static int getId(Direction dir) {
/*  9 */     if (dir == Direction.DOWN)
/* 10 */       return 0; 
/* 11 */     if (dir == Direction.UP)
/* 12 */       return 1; 
/* 13 */     if (dir == Direction.NORTH)
/* 14 */       return 2; 
/* 15 */     if (dir == Direction.SOUTH)
/* 16 */       return 3; 
/* 17 */     if (dir == Direction.WEST)
/* 18 */       return 4; 
/* 19 */     if (dir == Direction.EAST) {
/* 20 */       return 5;
/*    */     }
/*    */     
/* 23 */     return 0;
/*    */   }
/*    */   
/*    */   public static Direction fromId(int id) {
/* 27 */     switch (id) {
/*    */       case 0:
/* 29 */         return Direction.DOWN;
/*    */       case 1:
/* 31 */         return Direction.UP;
/*    */       case 2:
/* 33 */         return Direction.NORTH;
/*    */       case 3:
/* 35 */         return Direction.SOUTH;
/*    */       case 4:
/* 37 */         return Direction.WEST;
/*    */       case 5:
/* 39 */         return Direction.EAST;
/*    */     } 
/* 41 */     return Direction.DOWN;
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnace\\util\DirectionUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */