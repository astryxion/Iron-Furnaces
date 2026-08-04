/*    */ package ironfurnaces.util.gui;
/*    */ 
/*    */ import ironfurnaces.util.StringHelper;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FurnaceGuiEnergy
/*    */ {
/*    */   private int left;
/*    */   private int top;
/*    */   public int x;
/*    */   public int y;
/*    */   public int width;
/*    */   public int height;
/*    */   public int u;
/*    */   public int v;
/*    */   
/*    */   public FurnaceGuiEnergy(int left, int top, int x, int y, int width, int height, int u, int v) {
/* 24 */     this.left = left;
/* 25 */     this.top = top;
/* 26 */     this.x = x;
/* 27 */     this.y = y;
/* 28 */     this.width = width;
/* 29 */     this.height = height;
/* 30 */     this.u = u;
/* 31 */     this.v = v;
/*    */   }
/*    */ 
/*    */   
/*    */   public void changePos(int newX, int newY, boolean condition) {
/* 36 */     if (condition) {
/* 37 */       this.x = newX;
/* 38 */       this.y = newY;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void changeUV(int newU, int newV, boolean condition) {
/* 44 */     if (condition) {
/* 45 */       this.u = newU;
/* 46 */       this.v = newV;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(ResourceLocation location, GuiGraphics matrix, int scaled) {
/* 53 */     matrix.blit(location, this.left + this.x, this.top + this.y + 42 - scaled, this.u, this.v + this.height - scaled, this.width, scaled);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hovering(double mouseX, double mouseY) {
/* 58 */     return (mouseX >= this.x && mouseX <= (this.x + this.width) && mouseY >= this.y && mouseY <= (this.y + this.height));
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderTooltip(Font font, GuiGraphics matrix, int mouseX, int mouseY, int energy, int capacity, boolean condition) {
/* 63 */     if (condition && 
/* 64 */       hovering(mouseX, mouseY))
/* 65 */       matrix.renderTooltip(font, (Component)Component.literal(StringHelper.displayEnergy(energy, capacity).get(0)), mouseX, mouseY); 
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnace\\util\gui\FurnaceGuiEnergy.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */