/*     */ package ironfurnaces.util.gui;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.InputConstants;
/*     */ import ironfurnaces.network.Messages;
/*     */ import ironfurnaces.network.PacketSettingsButton;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*     */ import net.minecraft.client.resources.sounds.SoundInstance;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ 
/*     */ 
/*     */ public class FurnaceGuiButton
/*     */ {
/*     */   public int left;
/*     */   public int top;
/*     */   public int x;
/*     */   public int y;
/*     */   public int width;
/*     */   public int height;
/*     */   public int u;
/*     */   public int v;
/*     */   public int u_hover;
/*     */   public int v_hover;
/*     */   public int u_enabled;
/*     */   public int v_enabled;
/*     */   
/*     */   public FurnaceGuiButton(int left, int top, int x, int y, int width, int height, int u, int v, int u_hover, int v_hover, int u_enabled, int v_enabled) {
/*  35 */     this.left = left;
/*  36 */     this.top = top;
/*  37 */     this.x = x;
/*  38 */     this.y = y;
/*  39 */     this.width = width;
/*  40 */     this.height = height;
/*  41 */     this.u = u;
/*  42 */     this.v = v;
/*  43 */     this.u_hover = u_hover;
/*  44 */     this.v_hover = v_hover;
/*  45 */     this.u_enabled = u_enabled;
/*  46 */     this.v_enabled = v_enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public FurnaceGuiButton(int left, int top, int x, int y, int width, int height) {
/*  51 */     this(left, top, x, y, width, height, -1, -1, -1, -1, -1, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public FurnaceGuiButton(int left, int top, int x, int y, int width, int height, int u_hover, int v_hover) {
/*  56 */     this(left, top, x, y, width, height, -1, -1, u_hover, v_hover, u_hover, v_hover);
/*     */   }
/*     */ 
/*     */   
/*     */   public void changeEnabledUV(int u, int v) {
/*  61 */     this.u_enabled = u;
/*  62 */     this.v_enabled = v;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClick(double mouseX, double mouseY, BlockPos pos, int index, int set, boolean condition) {
/*  67 */     if (condition)
/*     */     {
/*  69 */       if (hovering(mouseX, mouseY)) {
/*     */         
/*  71 */         Messages.INSTANCE.sendToServer(new PacketSettingsButton(pos, index, set));
/*  72 */         Minecraft.getInstance().getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((SoundEvent)SoundEvents.UI_BUTTON_CLICK.value(), 0.6F, 0.3F));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRightClick(double mouseX, double mouseY, int button, BlockPos pos, int index, int set, boolean condition) {
/*  79 */     if (button == 1)
/*     */     {
/*  81 */       if (condition)
/*     */       {
/*  83 */         if (hovering(mouseX, mouseY)) {
/*     */           
/*  85 */           Messages.INSTANCE.sendToServer(new PacketSettingsButton(pos, index, set));
/*  86 */           Minecraft.getInstance().getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((SoundEvent)SoundEvents.UI_BUTTON_CLICK.value(), 0.3F, 0.3F));
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(ResourceLocation location, GuiGraphics matrix, int mouseX, int mouseY, boolean enabled) {
/*  95 */     if (!hovering(mouseX, mouseY) && hasUV()) {
/*  96 */       matrix.blit(location, this.left + this.x, this.top + this.y, this.u, this.v, this.width, this.height);
/*     */     }
/*  98 */     if (hovering(mouseX, mouseY) && hasUVHover()) {
/*  99 */       matrix.blit(location, this.left + this.x, this.top + this.y, this.u_hover, this.v_hover, this.width, this.height);
/*     */     }
/* 101 */     if (enabled && hasUVEnabled()) {
/* 102 */       matrix.blit(location, this.left + this.x, this.top + this.y, this.u_enabled, this.v_enabled, this.width, this.height);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hovering(double mouseX, double mouseY) {
/* 110 */     return (mouseX >= this.x && mouseX <= (this.x + this.width) && mouseY >= this.y && mouseY <= (this.y + this.height));
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderTooltip(Font font, GuiGraphics matrix, Component text, int mouseX, int mouseY, boolean condition) {
/* 115 */     if (condition && 
/* 116 */       hovering(mouseX, mouseY)) {
/* 117 */       matrix.renderTooltip(font, text, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */   
/*     */   public void renderComponentTooltip(Font font, GuiGraphics matrix, List<Component> text, int mouseX, int mouseY, boolean condition) {
/* 122 */     if (condition && 
/* 123 */       hovering(mouseX, mouseY)) {
/* 124 */       matrix.renderComponentTooltip(font, text, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasUV() {
/* 129 */     return (this.u >= 0 && this.v >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasUVHover() {
/* 134 */     return (this.u_hover >= 0 && this.v_hover >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasUVEnabled() {
/* 139 */     return (this.u_enabled >= 0 && this.v_enabled >= 0);
/*     */   }
/*     */   
/*     */   public static boolean isShiftKeyDown() {
/* 143 */     return (isKeyDown(340) || isKeyDown(344));
/*     */   }
/*     */   
/*     */   public static boolean isKeyDown(int glfw) {
/* 147 */     InputConstants.Key key = InputConstants.Type.KEYSYM.getOrCreate(glfw);
/* 148 */     int keyCode = key.getValue();
/* 149 */     if (keyCode != InputConstants.UNKNOWN.getValue()) {
/* 150 */       long windowHandle = Minecraft.getInstance().getWindow().getWindow();
/*     */       try {
/* 152 */         if (key.getType() == InputConstants.Type.KEYSYM) {
/* 153 */           return InputConstants.isKeyDown(windowHandle, keyCode);
/*     */         
/*     */         }
/*     */       }
/* 157 */       catch (Exception exception) {}
/*     */     } 
/*     */     
/* 160 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnace\\util\gui\FurnaceGuiButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */