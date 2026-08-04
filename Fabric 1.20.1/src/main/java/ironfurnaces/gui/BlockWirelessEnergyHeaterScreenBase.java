/*    */ package ironfurnaces.gui;
/*    */ 
/*    */ import ironfurnaces.container.BlockWirelessEnergyHeaterContainer;
/*    */ import ironfurnaces.util.StringHelper;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BlockWirelessEnergyHeaterScreenBase<T extends BlockWirelessEnergyHeaterContainer>
/*    */   extends AbstractContainerScreen<T>
/*    */ {
/* 18 */   public ResourceLocation GUI = new ResourceLocation("ironfurnaces:textures/gui/heater.png");
/*    */   Inventory playerInv;
/*    */   Component name;
/*    */   
/*    */   public BlockWirelessEnergyHeaterScreenBase(T t, Inventory inv, Component name) {
/* 23 */     super(t, inv, name);
/* 24 */     this.playerInv = inv;
/* 25 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics matrix, int mouseX, int mouseY, float partialTicks) {
/* 30 */     renderBackground(matrix);
/* 31 */     super.render(matrix, mouseX, mouseY, partialTicks);
/* 32 */     renderTooltip(matrix, mouseX, mouseY);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderLabels(GuiGraphics matrix, int mouseX, int mouseY) {
/* 37 */     matrix.drawString(this.font, this.playerInv.getDisplayName(), 7, imageHeight - 93, 4210752, false);
/* 38 */     matrix.drawString(this.font, this.name, imageWidth / 2 - this.font.width(this.name) / 2, 6, 4210752, false);
/*    */     
/* 40 */     int actualMouseX = mouseX - (this.width - imageWidth) / 2;
/* 41 */     int actualMouseY = mouseY - (this.height - imageHeight) / 2;
/* 42 */     if (actualMouseX >= 68 && actualMouseX <= 108 && actualMouseY >= 64 && actualMouseY <= 76) {
/* 43 */       int energy = ((BlockWirelessEnergyHeaterContainer)getMenu()).getEnergy();
/* 44 */       int capacity = ((BlockWirelessEnergyHeaterContainer)getMenu()).getMaxEnergy();
/* 45 */       matrix.renderTooltip(this.font, (Component)Component.literal(StringHelper.displayEnergy(energy, capacity).get(0)), actualMouseX, actualMouseY);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void renderBg(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY) {
/* 53 */     int relX = (this.width - imageWidth) / 2;
/* 54 */     int relY = (this.height - imageHeight) / 2;
/* 55 */     matrix.blit(this.GUI, relX, relY, 0, 0, imageWidth, imageHeight);
/*    */ 
/*    */     
/* 58 */     int i = ((BlockWirelessEnergyHeaterContainer)getMenu()).getEnergyScaled(42);
/* 59 */     matrix.blit(this.GUI, leftPos + 67, topPos + 63, 176, 0, i + 1, 14);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\gui\BlockWirelessEnergyHeaterScreenBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */