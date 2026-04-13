/*    */ package ironfurnaces.util;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class LRUCache<K, V>
/*    */   extends LinkedHashMap<K, V>
/*    */ {
/*    */   private int size;
/*    */   
/*    */   private LRUCache(int size) {
/* 12 */     super(size, 0.75F, true);
/* 13 */     this.size = size;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
/* 18 */     return (size() > this.size);
/*    */   }
/*    */ 
/*    */   
/*    */   public static <K, V> LRUCache<K, V> newInstance(int size) {
/* 23 */     return new LRUCache<>(size);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnace\\util\LRUCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */