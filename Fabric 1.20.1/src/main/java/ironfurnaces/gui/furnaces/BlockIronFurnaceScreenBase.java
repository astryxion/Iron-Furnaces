/*     */ package ironfurnaces.gui.furnaces;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.platform.InputConstants;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import ironfurnaces.capability.ClientShowConfig;
/*     */ import ironfurnaces.container.furnaces.BlockIronFurnaceContainerBase;
/*     */ import ironfurnaces.items.ItemMillionFurnace;
/*     */ import ironfurnaces.network.Messages;
/*     */ import ironfurnaces.network.PacketSettingsButton;
/*     */ import ironfurnaces.network.PacketShowConfigButton;
/*     */ import ironfurnaces.util.StringHelper;
/*     */ import ironfurnaces.util.gui.FurnaceGuiButton;
/*     */ import ironfurnaces.util.gui.FurnaceGuiEnergy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BlockIronFurnaceScreenBase<T extends BlockIronFurnaceContainerBase>
/*     */   extends AbstractContainerScreen<T>
/*     */ {
/*  34 */   public ResourceLocation GUI = new ResourceLocation("ironfurnaces:textures/gui/furnace.png");
/*  35 */   public static final ResourceLocation GUI_NETHERITE = new ResourceLocation("ironfurnaces:textures/gui/furnace_netherite.png");
/*  36 */   public static final ResourceLocation GUI_ATM = new ResourceLocation("ironfurnaces:textures/gui/furnace_allthemodium.png");
/*  37 */   public static final ResourceLocation GUI_VIB = new ResourceLocation("ironfurnaces:textures/gui/furnace_vibranium.png");
/*  38 */   public static final ResourceLocation GUI_UNOB = new ResourceLocation("ironfurnaces:textures/gui/furnace_unobtainium.png");
/*  39 */   public static final ResourceLocation GUI_FACTORY = new ResourceLocation("ironfurnaces:textures/gui/furnace_factory.png");
/*  40 */   public static final ResourceLocation GUI_GENERATOR = new ResourceLocation("ironfurnaces:textures/gui/furnace_generator.png");
/*  41 */   public static final ResourceLocation GUI_GENERATOR_NETHERITE = new ResourceLocation("ironfurnaces:textures/gui/furnace_generator_netherite.png");
/*  42 */   public static final ResourceLocation GUI_GENERATOR_ALLTHEMODIUM = new ResourceLocation("ironfurnaces:textures/gui/furnace_generator_allthemodium.png");
/*  43 */   public static final ResourceLocation GUI_GENERATOR_VIBRANIUM = new ResourceLocation("ironfurnaces:textures/gui/furnace_generator_vibranium.png");
/*  44 */   public static final ResourceLocation GUI_GENERATOR_UNOBTAINIUM = new ResourceLocation("ironfurnaces:textures/gui/furnace_generator_unobtainium.png");
/*  45 */   public static final ResourceLocation GUI_AUGMENTS = new ResourceLocation("ironfurnaces:textures/gui/augment.png");
/*  46 */   public static final ResourceLocation WIDGETS = new ResourceLocation("ironfurnaces:textures/gui/widgets.png");
/*     */   
/*     */   Inventory playerInv;
/*     */   Component name;
/*  50 */   public List<FurnaceGuiButton> sideButtons = Lists.newArrayList();
/*     */   
/*     */   public FurnaceGuiButton autoSplitButton;
/*     */   
/*     */   public FurnaceGuiButton augmentButton;
/*     */   public FurnaceGuiButton autoInputButton;
/*     */   public FurnaceGuiButton autoOutputButton;
/*     */   public FurnaceGuiButton topButton;
/*     */   public FurnaceGuiButton leftButton;
/*     */   public FurnaceGuiButton frontButton;
/*     */   public FurnaceGuiButton rightButton;
/*     */   public FurnaceGuiButton bottomButton;
/*     */   public FurnaceGuiButton backButton;
/*     */   public FurnaceGuiButton redstoneIgnoredButton;
/*     */   public FurnaceGuiButton redstoneLowButton;
/*     */   public FurnaceGuiButton redstoneHighButton;
/*     */   public FurnaceGuiButton comparatorButton;
/*     */   public FurnaceGuiButton comparatorSubButton;
/*     */   public FurnaceGuiButton addButton;
/*     */   public FurnaceGuiButton subButton;
/*     */   public FurnaceGuiEnergy energyBar;
/*     */   private int timer;
/*  72 */   private Random rand = new Random();
/*     */   
/*     */   public BlockIronFurnaceScreenBase(T t, Inventory inv, Component name) {
/*  75 */     super(t, inv, name);
/*  76 */     this.playerInv = inv;
/*  77 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics matrix, int mouseX, int mouseY, float partialTicks) {
/*  82 */     renderBackground(matrix);
/*  83 */     super.render(matrix, mouseX, mouseY, partialTicks);
/*  84 */     renderTooltip(matrix, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  89 */     super.init();
/*  90 */     int left = leftPos;
/*  91 */     int top = topPos;
/*  92 */     this.energyBar = new FurnaceGuiEnergy(left, top, 109, 22, 14, 42, 176, 14);
/*  93 */     this.autoSplitButton = new FurnaceGuiButton(left, top, 9, 56, 14, 14, 112, 189);
/*  94 */     this.augmentButton = new FurnaceGuiButton(left, top, 161, 4, 11, 11);
/*  95 */     this.autoInputButton = new FurnaceGuiButton(left, top, -47, 12, 14, 14, 0, 189);
/*  96 */     this.autoOutputButton = new FurnaceGuiButton(left, top, -29, 12, 14, 14, 14, 189);
/*  97 */     this.redstoneIgnoredButton = new FurnaceGuiButton(left, top, -47, 70, 14, 14, 28, 189);
/*  98 */     this.redstoneLowButton = new FurnaceGuiButton(left, top, -31, 70, 14, 14, 84, 189, 98, 189, 98, 189);
/*  99 */     this.redstoneHighButton = new FurnaceGuiButton(left, top, -31, 70, 14, 14, 42, 189);
/* 100 */     this.comparatorButton = new FurnaceGuiButton(left, top, -15, 70, 14, 14, 56, 189);
/* 101 */     this.comparatorSubButton = new FurnaceGuiButton(left, top, -47, 86, 14, 14, 70, 189);
/* 102 */     this.addButton = new FurnaceGuiButton(left, top, -31, 86, 14, 14, 0, 14, 14, 14, 28, 14);
/* 103 */     this.subButton = new FurnaceGuiButton(left, top, -31, 86, 14, 14, 0, 0, 14, 0, 28, 0);
/* 104 */     this.sideButtons.add(this.bottomButton = new FurnaceGuiButton(left, top, -32, 55, 10, 10));
/* 105 */     this.sideButtons.add(this.topButton = new FurnaceGuiButton(left, top, -32, 31, 10, 10));
/* 106 */     this.sideButtons.add(this.frontButton = new FurnaceGuiButton(left, top, -32, 43, 10, 10));
/* 107 */     this.sideButtons.add(this.backButton = new FurnaceGuiButton(left, top, -20, 55, 10, 10));
/* 108 */     this.sideButtons.add(this.leftButton = new FurnaceGuiButton(left, top, -44, 43, 10, 10));
/* 109 */     this.sideButtons.add(this.rightButton = new FurnaceGuiButton(left, top, -20, 43, 10, 10));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean showInventoryButtons() {
/* 114 */     return (getShowConfig() == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setShowConfig(int value) {
/* 119 */     ClientShowConfig.set(value);
/* 120 */     Messages.INSTANCE.sendToServer(new PacketShowConfigButton(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getShowConfig() {
/* 125 */     return ClientShowConfig.getShowConfig();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderLabels(GuiGraphics matrix, int mouseX, int mouseY) {
/* 130 */     int actualMouseX = mouseX - (this.width - imageWidth) / 2;
/* 131 */     int actualMouseY = mouseY - (this.height - imageHeight) / 2;
/* 132 */     if (((BlockIronFurnaceContainerBase)getMenu()).isRainbowFurnace()) {
/*     */       
/* 134 */       this.timer++;
/* 135 */       if (this.timer % 20 == 0) {
/* 136 */         this.timer = 0;
/* 137 */         String name = this.name.getString();
/* 138 */         ArrayList<Component> names = Lists.newArrayList();
/* 139 */         for (int i = 0; i < name.length(); i++) {
/* 140 */           names.add(Component.literal("" + name.charAt(i)).withStyle(ChatFormatting.getById(ItemMillionFurnace.getIDRandom(this.rand.nextInt(6)))));
/*     */         }
/* 142 */         MutableComponent component = Component.literal("");
/* 143 */         for (int j = 0; j < names.size(); j++) {
/* 144 */           component.append(names.get(j));
/*     */         }
/* 146 */         this.name = (Component)component;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 151 */     if (((BlockIronFurnaceContainerBase)getMenu()).getIsFactory()) {
/* 152 */       matrix.drawString(this.font, this.name, imageWidth / 2 - this.font.width(this.name) / 2, -10, 16777215, false);
/*     */     } else {
/* 154 */       matrix.drawString(this.font, this.name, ((BlockIronFurnaceContainerBase)getMenu()).getIsFurnace() ? (7 + imageWidth / 2 - this.font.width(this.name) / 2) : (imageWidth / 2 - this.font.width(this.name) / 2), 6, 4210752, false);
/*     */     } 
/* 156 */     matrix.drawString(this.font, this.playerInv.getDisplayName(), 7, imageHeight - 93, 4210752, false);
/*     */     
/* 158 */     if (showInventoryButtons() && ((BlockIronFurnaceContainerBase)getMenu()).getRedstoneMode() == 4) {
/* 159 */       int comSub = ((BlockIronFurnaceContainerBase)getMenu()).getComSub();
/* 160 */       int i = (comSub > 9) ? 28 : 31;
/* 161 */       matrix.drawString(this.font, (Component)Component.literal("" + comSub), i - 42, 90, 4210752, false);
/*     */     } 
/*     */ 
/*     */     
/* 165 */     addTooltips(matrix, actualMouseX, actualMouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addTooltips(GuiGraphics matrix, int mouseX, int mouseY) {
/* 170 */     this.augmentButton.renderTooltip(this.font, matrix, (Component)Component.translatable("tooltip.ironfurnaces.gui_open_augments"), mouseX, mouseY, !((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI());
/* 171 */     this.augmentButton.renderTooltip(this.font, matrix, (Component)Component.translatable("tooltip.ironfurnaces.gui_open_furnace"), mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI());
/* 172 */     this.energyBar.changePos(109, 22, (((BlockIronFurnaceContainerBase)getMenu()).getIsGenerator() && !((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI()));
/* 173 */     this.energyBar.changePos(9, 7, (((BlockIronFurnaceContainerBase)getMenu()).getIsFactory() && !((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI()));
/* 174 */     this.energyBar.renderTooltip(this.font, matrix, mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getEnergy(), ((BlockIronFurnaceContainerBase)getMenu()).getMaxEnergy(), (((BlockIronFurnaceContainerBase)getMenu()).getIsGenerator() && !((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI()));
/* 175 */     this.energyBar.renderTooltip(this.font, matrix, mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getEnergy(), ((BlockIronFurnaceContainerBase)getMenu()).getMaxEnergy(), (((BlockIronFurnaceContainerBase)getMenu()).getIsFactory() && !((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI()));
/* 176 */     List<Component> tl = Lists.newArrayList(Component.literal("Auto Split"), Component.literal("ON"));
/* 177 */     this.autoSplitButton.renderComponentTooltip(this.font, matrix, tl, mouseX, mouseY, (((BlockIronFurnaceContainerBase)getMenu()).isAutoSplit() && ((BlockIronFurnaceContainerBase)getMenu()).getIsFactory() && !((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI()));
/* 178 */     tl = Lists.newArrayList(Component.literal("Auto Split"), Component.literal("OFF"));
/* 179 */     this.autoSplitButton.renderComponentTooltip(this.font, matrix, tl, mouseX, mouseY, (!((BlockIronFurnaceContainerBase)getMenu()).isAutoSplit() && ((BlockIronFurnaceContainerBase)getMenu()).getIsFactory() && !((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI()));
/*     */ 
/*     */     
/* 182 */     if (!showInventoryButtons()) {
/* 183 */       if (mouseX >= -20 && mouseX <= 0 && mouseY >= 4 && mouseY <= 26) {
/* 184 */         matrix.renderTooltip(this.font, (Component)Component.translatable("tooltip.ironfurnaces.gui_open"), mouseX, mouseY);
/*     */       }
/*     */     } else {
/* 187 */       if (mouseX >= -13 && mouseX <= 0 && mouseY >= 4 && mouseY <= 26) {
/* 188 */         matrix.renderComponentTooltip(this.font, StringHelper.getShiftInfoGui(), mouseX, mouseY);
/*     */       }
/* 190 */       List<Component> list = Lists.newArrayList();
/* 191 */       list.add(Component.translatable("tooltip.ironfurnaces.gui_auto_input"));
/* 192 */       list.add(Component.literal(((BlockIronFurnaceContainerBase)getMenu()).getAutoInput() ? "ON" : "OFF"));
/* 193 */       this.autoInputButton.renderComponentTooltip(this.font, matrix, list, mouseX, mouseY, true);
/* 194 */       list = Lists.newArrayList();
/* 195 */       list.add(Component.translatable("tooltip.ironfurnaces.gui_auto_output"));
/* 196 */       list.add(Component.literal(((BlockIronFurnaceContainerBase)getMenu()).getAutoOutput() ? "ON" : "OFF"));
/* 197 */       this.autoOutputButton.renderComponentTooltip(this.font, matrix, list, mouseX, mouseY, true);
/* 198 */       list = Lists.newArrayList();
/* 199 */       list.add(Component.translatable("tooltip.ironfurnaces.gui_top"));
/* 200 */       list.add(((BlockIronFurnaceContainerBase)getMenu()).getTooltip(Direction.UP.ordinal()));
/* 201 */       this.topButton.renderComponentTooltip(this.font, matrix, list, mouseX, mouseY, true);
/* 202 */       list = Lists.newArrayList();
/* 203 */       list.add(Component.translatable("tooltip.ironfurnaces.gui_bottom"));
/* 204 */       list.add(((BlockIronFurnaceContainerBase)getMenu()).getTooltip(Direction.DOWN.ordinal()));
/* 205 */       this.bottomButton.renderComponentTooltip(this.font, matrix, list, mouseX, mouseY, true);
/* 206 */       list = Lists.newArrayList();
/* 207 */       if (isShiftKeyDown()) {
/* 208 */         list.add(Component.translatable("tooltip.ironfurnaces.gui_reset"));
/*     */       } else {
/* 210 */         list.add(Component.translatable("tooltip.ironfurnaces.gui_front"));
/* 211 */         list.add(((BlockIronFurnaceContainerBase)getMenu()).getTooltip(((BlockIronFurnaceContainerBase)getMenu()).getIndexFront()));
/*     */       } 
/* 213 */       this.frontButton.renderComponentTooltip(this.font, matrix, list, mouseX, mouseY, true);
/* 214 */       list = Lists.newArrayList();
/* 215 */       list.add(Component.translatable("tooltip.ironfurnaces.gui_back"));
/* 216 */       list.add(((BlockIronFurnaceContainerBase)getMenu()).getTooltip(((BlockIronFurnaceContainerBase)getMenu()).getIndexBack()));
/* 217 */       this.backButton.renderComponentTooltip(this.font, matrix, list, mouseX, mouseY, true);
/* 218 */       list = Lists.newArrayList();
/* 219 */       list.add(Component.translatable("tooltip.ironfurnaces.gui_left"));
/* 220 */       list.add(((BlockIronFurnaceContainerBase)getMenu()).getTooltip(((BlockIronFurnaceContainerBase)getMenu()).getIndexLeft()));
/* 221 */       this.leftButton.renderComponentTooltip(this.font, matrix, list, mouseX, mouseY, true);
/* 222 */       list = Lists.newArrayList();
/* 223 */       list.add(Component.translatable("tooltip.ironfurnaces.gui_right"));
/* 224 */       list.add(((BlockIronFurnaceContainerBase)getMenu()).getTooltip(((BlockIronFurnaceContainerBase)getMenu()).getIndexRight()));
/* 225 */       this.rightButton.renderComponentTooltip(this.font, matrix, list, mouseX, mouseY, true);
/* 226 */       this.redstoneIgnoredButton.renderTooltip(this.font, matrix, (Component)Component.translatable("tooltip.ironfurnaces.gui_redstone_ignored"), mouseX, mouseY, true);
/* 227 */       this.redstoneLowButton.renderTooltip(this.font, matrix, (Component)Component.translatable("tooltip.ironfurnaces.gui_redstone_low"), mouseX, mouseY, isShiftKeyDown());
/* 228 */       this.redstoneHighButton.renderTooltip(this.font, matrix, (Component)Component.translatable("tooltip.ironfurnaces.gui_redstone_high"), mouseX, mouseY, !isShiftKeyDown());
/* 229 */       this.comparatorButton.renderTooltip(this.font, matrix, (Component)Component.translatable("tooltip.ironfurnaces.gui_redstone_comparator"), mouseX, mouseY, true);
/* 230 */       this.comparatorSubButton.renderTooltip(this.font, matrix, (Component)Component.translatable("tooltip.ironfurnaces.gui_redstone_comparator_sub"), mouseX, mouseY, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void bg(GuiGraphics matrix, int relX, int relY) {
/* 236 */     if (!((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI()) {
/*     */       
/* 238 */       if (((BlockIronFurnaceContainerBase)getMenu()).getIsFactory())
/*     */       {
/* 240 */         matrix.blit(GUI_FACTORY, relX, relY, 0, 0, imageWidth, imageHeight);
/*     */       }
/*     */       
/* 243 */       if (((BlockIronFurnaceContainerBase)getMenu()).getIsGenerator())
/*     */       {
/* 245 */         matrix.blit(GUI_GENERATOR, relX, relY, 0, 0, imageWidth, imageHeight);
/*     */       }
/*     */       
/* 248 */       if (!((BlockIronFurnaceContainerBase)getMenu()).getIsGenerator() && !((BlockIronFurnaceContainerBase)getMenu()).getIsFactory())
/*     */       {
/* 250 */         matrix.blit(this.GUI, relX, relY, 0, 0, imageWidth, imageHeight);
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 256 */       matrix.blit(GUI_AUGMENTS, relX, relY, 0, 0, imageWidth, imageHeight);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderFurnaceBg(GuiGraphics matrix) {
/* 263 */     if (((BlockIronFurnaceContainerBase)getMenu()).getIsFurnace() && !((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI()) {
/*     */ 
/*     */ 
/*     */       
/* 267 */       if (((BlockIronFurnaceContainerBase)getMenu()).isBurning()) {
/* 268 */         int j = ((BlockIronFurnaceContainerBase)getMenu()).getBurnLeftScaled(13);
/* 269 */         matrix.blit(this.GUI, leftPos + 56, topPos + 36 + 12 - j, 176, 12 - j, 14, j + 1);
/*     */       } 
/*     */       
/* 272 */       int i = ((BlockIronFurnaceContainerBase)getMenu()).getCookScaled(24);
/* 273 */       matrix.blit(this.GUI, leftPos + 79, topPos + 34, 176, 14, i + 1, 16);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderGeneratorBg(GuiGraphics matrix) {
/* 279 */     if (((BlockIronFurnaceContainerBase)getMenu()).getIsGenerator() && !((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI()) {
/*     */ 
/*     */       
/* 282 */       if (((BlockIronFurnaceContainerBase)getMenu()).isGeneratorBurning()) {
/* 283 */         int i = ((BlockIronFurnaceContainerBase)getMenu()).getGeneratorBurnScaled(13);
/* 284 */         matrix.blit(GUI_GENERATOR, leftPos + 56, topPos + 23 + 12 - i, 176, 12 - i, 14, i + 1);
/*     */       } 
/* 286 */       this.energyBar.render(GUI_GENERATOR, matrix, ((BlockIronFurnaceContainerBase)getMenu()).getEnergyScaled(42));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderFactoryBg(GuiGraphics matrix) {
/* 292 */     if (((BlockIronFurnaceContainerBase)getMenu()).getIsFactory() && !((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI()) {
/*     */       
/* 294 */       addSlots(matrix, ((BlockIronFurnaceContainerBase)getMenu()).getTier());
/* 295 */       this.energyBar.changePos(9, 7, true);
/* 296 */       this.energyBar.changeUV(176, 22, true);
/* 297 */       this.energyBar.render(GUI_FACTORY, matrix, ((BlockIronFurnaceContainerBase)getMenu()).getEnergyScaled(42));
/*     */ 
/*     */       
/* 300 */       for (int j = 0; j < ((BlockIronFurnaceContainerBase)getMenu()).getFactoryCooktimeSize(); j++) {
/*     */         
/* 302 */         int i = ((BlockIronFurnaceContainerBase)getMenu()).getFactoryCookScaled(j, 22);
/* 303 */         matrix.blit(GUI_FACTORY, leftPos + 29 + 21 * j, topPos + 27, 176, 0, 15, i + 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBg(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY) {
/* 310 */     int relX = (this.width - imageWidth) / 2;
/* 311 */     int relY = (this.height - imageHeight) / 2;
/* 312 */     bg(matrix, relX, relY);
/* 313 */     renderFurnaceBg(matrix);
/* 314 */     renderGeneratorBg(matrix);
/* 315 */     renderFactoryBg(matrix);
/* 316 */     RenderSystem.setShaderTexture(0, WIDGETS);
/* 317 */     int actualMouseX = mouseX - (this.width - imageWidth) / 2;
/* 318 */     int actualMouseY = mouseY - (this.height - imageHeight) / 2;
/* 319 */     addFactoryButtons(matrix, actualMouseX, actualMouseY);
/* 320 */     addInventoryButtons(matrix, actualMouseX, actualMouseY);
/* 321 */     addRedstoneButtons(matrix, actualMouseX, actualMouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addSlots(GuiGraphics matrix, int amount) {
/* 326 */     if (((BlockIronFurnaceContainerBase)getMenu()).getIsFactory())
/*     */     {
/* 328 */       if (amount > 0) {
/*     */         
/* 330 */         matrix.blit(GUI_FACTORY, leftPos + 48, topPos + 5, 176, 64, 18, 67);
/* 331 */         matrix.blit(GUI_FACTORY, leftPos + 111, topPos + 5, 176, 64, 18, 67);
/* 332 */         if (amount == 2) {
/*     */           
/* 334 */           matrix.blit(GUI_FACTORY, leftPos + 27, topPos + 5, 176, 64, 18, 67);
/* 335 */           matrix.blit(GUI_FACTORY, leftPos + 132, topPos + 5, 176, 64, 18, 67);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addFactoryButtons(GuiGraphics matrix, int mouseX, int mouseY) {
/* 346 */     if (((BlockIronFurnaceContainerBase)getMenu()).getIsFactory() && !((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI())
/*     */     {
/* 348 */       this.autoSplitButton.render(WIDGETS, matrix, mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).isAutoSplit());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void addRedstoneButtons(GuiGraphics matrix, int mouseX, int mouseY) {
/* 354 */     if (showInventoryButtons()) {
/* 355 */       boolean flag = isShiftKeyDown();
/* 356 */       int setting = ((BlockIronFurnaceContainerBase)getMenu()).getRedstoneMode();
/* 357 */       if (setting == 0) this.redstoneIgnoredButton.render(WIDGETS, matrix, mouseX, mouseY, true); 
/* 358 */       if (flag) this.redstoneLowButton.render(WIDGETS, matrix, mouseX, mouseY, (setting == 2)); 
/* 359 */       if (!flag) this.redstoneHighButton.render(WIDGETS, matrix, mouseX, mouseY, (setting == 1)); 
/* 360 */       if (setting == 3) this.comparatorButton.render(WIDGETS, matrix, mouseX, mouseY, true); 
/* 361 */       if (setting == 4) {
/* 362 */         this.comparatorSubButton.render(WIDGETS, matrix, mouseX, mouseY, true);
/* 363 */         int comSub = ((BlockIronFurnaceContainerBase)getMenu()).getComSub();
/* 364 */         this.addButton.render(WIDGETS, matrix, mouseX, mouseY, (comSub == 15));
/* 365 */         if (flag)
/* 366 */           this.subButton.render(WIDGETS, matrix, mouseX, mouseY, (comSub == 0)); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addInventoryButtons(GuiGraphics matrix, int mouseX, int mouseY) {
/* 372 */     if (!showInventoryButtons()) {
/* 373 */       matrix.blit(WIDGETS, leftPos - 20, topPos + 4, 0, 28, 23, 26);
/* 374 */     } else if (showInventoryButtons()) {
/* 375 */       matrix.blit(WIDGETS, leftPos - 56, topPos + 4, 0, 54, 59, 107);
/* 376 */       this.autoInputButton.render(WIDGETS, matrix, mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getAutoInput());
/* 377 */       this.autoOutputButton.render(WIDGETS, matrix, mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getAutoOutput());
/* 378 */       blitIO(matrix, mouseX, mouseY);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void blitIO(GuiGraphics matrix, int mouseX, int mouseY) {
/* 391 */     int[] settings = { ((BlockIronFurnaceContainerBase)getMenu()).getSettingBottom(), ((BlockIronFurnaceContainerBase)getMenu()).getSettingTop(), ((BlockIronFurnaceContainerBase)getMenu()).getSettingFront(), ((BlockIronFurnaceContainerBase)getMenu()).getSettingBack(), ((BlockIronFurnaceContainerBase)getMenu()).getSettingLeft(), ((BlockIronFurnaceContainerBase)getMenu()).getSettingRight() };
/*     */     
/* 393 */     for (int i = 0; i < settings.length; i++) {
/*     */       
/* 395 */       if (settings.length != this.sideButtons.size()) {
/*     */         break;
/*     */       }
/* 398 */       if (settings[i] != 0) {
/*     */ 
/*     */         
/* 401 */         FurnaceGuiButton button = this.sideButtons.get(i);
/* 402 */         button.changeEnabledUV(10 * settings[i] - 10, 161);
/* 403 */         button.render(WIDGETS, matrix, mouseX, mouseY, true);
/*     */       } 
/*     */     } 
/* 406 */     if (((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI()) {
/*     */       return;
/*     */     }
/* 409 */     boolean input = false;
/* 410 */     boolean output = false;
/* 411 */     boolean both = false;
/* 412 */     boolean fuel = false;
/* 413 */     for (int set : settings) {
/* 414 */       if (set == 1) {
/* 415 */         input = true;
/* 416 */       } else if (set == 2) {
/* 417 */         output = true;
/* 418 */       } else if (set == 3) {
/* 419 */         both = true;
/* 420 */       } else if (set == 4) {
/* 421 */         fuel = true;
/*     */       } 
/*     */     } 
/* 424 */     if (input || both) {
/* 425 */       if (((BlockIronFurnaceContainerBase)getMenu()).getIsFurnace())
/*     */       {
/* 427 */         matrix.blit(WIDGETS, leftPos + 55, topPos + 16, 0, 171, 18, 18);
/*     */       }
/* 429 */       if (((BlockIronFurnaceContainerBase)getMenu()).getIsFactory()) {
/*     */         
/* 431 */         matrix.blit(WIDGETS, leftPos + 69, topPos + 5, 0, 171, 18, 18);
/* 432 */         matrix.blit(WIDGETS, leftPos + 90, topPos + 5, 0, 171, 18, 18);
/* 433 */         if (((BlockIronFurnaceContainerBase)getMenu()).getTier() > 0) {
/*     */           
/* 435 */           matrix.blit(WIDGETS, leftPos + 48, topPos + 5, 0, 171, 18, 18);
/* 436 */           matrix.blit(WIDGETS, leftPos + 111, topPos + 5, 0, 171, 18, 18);
/* 437 */           if (((BlockIronFurnaceContainerBase)getMenu()).getTier() > 1) {
/*     */             
/* 439 */             matrix.blit(WIDGETS, leftPos + 27, topPos + 5, 0, 171, 18, 18);
/* 440 */             matrix.blit(WIDGETS, leftPos + 132, topPos + 5, 0, 171, 18, 18);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 447 */     if (output || both) {
/* 448 */       if (((BlockIronFurnaceContainerBase)getMenu()).getIsFurnace())
/*     */       {
/* 450 */         matrix.blit(WIDGETS, leftPos + 111, topPos + 30, 0, 203, 26, 26);
/*     */       }
/* 452 */       if (((BlockIronFurnaceContainerBase)getMenu()).getIsFactory()) {
/*     */         
/* 454 */         matrix.blit(WIDGETS, leftPos + 69, topPos + 54, 36, 171, 18, 18);
/* 455 */         matrix.blit(WIDGETS, leftPos + 90, topPos + 54, 36, 171, 18, 18);
/* 456 */         if (((BlockIronFurnaceContainerBase)getMenu()).getTier() > 0) {
/*     */           
/* 458 */           matrix.blit(WIDGETS, leftPos + 48, topPos + 54, 36, 171, 18, 18);
/* 459 */           matrix.blit(WIDGETS, leftPos + 111, topPos + 54, 36, 171, 18, 18);
/* 460 */           if (((BlockIronFurnaceContainerBase)getMenu()).getTier() > 1) {
/*     */             
/* 462 */             matrix.blit(WIDGETS, leftPos + 27, topPos + 54, 36, 171, 18, 18);
/* 463 */             matrix.blit(WIDGETS, leftPos + 132, topPos + 54, 36, 171, 18, 18);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 468 */     if (fuel) {
/* 469 */       if (((BlockIronFurnaceContainerBase)getMenu()).getIsFurnace())
/*     */       {
/* 471 */         matrix.blit(WIDGETS, leftPos + 55, topPos + 52, 18, 171, 18, 18);
/*     */       }
/* 473 */       if (((BlockIronFurnaceContainerBase)getMenu()).getIsGenerator())
/*     */       {
/* 475 */         matrix.blit(WIDGETS, leftPos + 55, topPos + 39, 18, 171, 18, 18);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 482 */     double actualMouseX = mouseX - (this.width - imageWidth) / 2.0D;
/* 483 */     double actualMouseY = mouseY - (this.height - imageHeight) / 2.0D;
/* 484 */     mouseClickedRedstoneButtons(actualMouseX, actualMouseY);
/* 485 */     mouseClickedInventoryButtons(button, actualMouseX, actualMouseY);
/* 486 */     mouseClickedAugmentButton(actualMouseX, actualMouseY);
/* 487 */     mouseClickedAutoSplitButton(actualMouseX, actualMouseY);
/* 488 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClickedAutoSplitButton(double mouseX, double mouseY) {
/* 493 */     if (!((BlockIronFurnaceContainerBase)getMenu()).isAutoSplit()) {
/* 494 */       this.autoSplitButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 11, 1, ((BlockIronFurnaceContainerBase)getMenu()).getIsFactory());
/*     */     } else {
/* 496 */       this.autoSplitButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 11, 0, ((BlockIronFurnaceContainerBase)getMenu()).getIsFactory());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseClickedAugmentButton(double mouseX, double mouseY) {
/* 501 */     if (!((BlockIronFurnaceContainerBase)getMenu()).getAugmentGUI()) {
/* 502 */       this.augmentButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 10, 1, true);
/*     */     } else {
/* 504 */       this.augmentButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 10, 0, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseClickedInventoryButtons(int button, double mouseX, double mouseY) {
/* 509 */     if (!showInventoryButtons()) {
/* 510 */       if (mouseX >= -20.0D && mouseX <= 0.0D && mouseY >= 4.0D && mouseY <= 26.0D) {
/* 511 */         setShowConfig(1);
/*     */       }
/*     */     } else {
/* 514 */       if (mouseX >= -13.0D && mouseX <= 0.0D && mouseY >= 4.0D && mouseY <= 26.0D) {
/* 515 */         setShowConfig(0);
/*     */       }
/* 517 */       if (!((BlockIronFurnaceContainerBase)getMenu()).getAutoInput()) {
/*     */         
/* 519 */         this.autoInputButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 6, 1, true);
/*     */       }
/* 521 */       else if (((BlockIronFurnaceContainerBase)getMenu()).getAutoInput()) {
/*     */         
/* 523 */         this.autoInputButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 6, 0, true);
/*     */       } 
/* 525 */       if (!((BlockIronFurnaceContainerBase)getMenu()).getAutoOutput()) {
/*     */         
/* 527 */         this.autoOutputButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 7, 1, true);
/*     */       }
/* 529 */       else if (((BlockIronFurnaceContainerBase)getMenu()).getAutoOutput()) {
/*     */         
/* 531 */         this.autoOutputButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 7, 0, true);
/*     */       } 
/* 533 */       clickInvButton(mouseX, mouseY, this.topButton, button, ((BlockIronFurnaceContainerBase)getMenu()).getSettingTop(), Direction.UP.ordinal());
/* 534 */       clickInvButton(mouseX, mouseY, this.bottomButton, button, ((BlockIronFurnaceContainerBase)getMenu()).getSettingBottom(), Direction.DOWN.ordinal());
/* 535 */       clickInvButton(mouseX, mouseY, this.frontButton, button, ((BlockIronFurnaceContainerBase)getMenu()).getSettingFront(), ((BlockIronFurnaceContainerBase)getMenu()).getIndexFront(), isShiftKeyDown());
/* 536 */       clickInvButton(mouseX, mouseY, this.backButton, button, ((BlockIronFurnaceContainerBase)getMenu()).getSettingBack(), ((BlockIronFurnaceContainerBase)getMenu()).getIndexBack());
/* 537 */       clickInvButton(mouseX, mouseY, this.leftButton, button, ((BlockIronFurnaceContainerBase)getMenu()).getSettingLeft(), ((BlockIronFurnaceContainerBase)getMenu()).getIndexLeft());
/* 538 */       clickInvButton(mouseX, mouseY, this.rightButton, button, ((BlockIronFurnaceContainerBase)getMenu()).getSettingRight(), ((BlockIronFurnaceContainerBase)getMenu()).getIndexRight());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clickInvButton(double mouseX, double mouseY, FurnaceGuiButton button, int buttonid, int setting, int index) {
/* 544 */     clickInvButton(mouseX, mouseY, button, buttonid, setting, index, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clickInvButton(double mouseX, double mouseY, FurnaceGuiButton button, int buttonid, int setting, int index, boolean shift) {
/* 549 */     int set = (setting == 4) ? 0 : (setting + 1);
/* 550 */     button.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), index, set, (buttonid == 0));
/* 551 */     set = (setting == 0) ? 4 : (setting - 1);
/* 552 */     button.onRightClick(mouseX, mouseY, buttonid, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), index, set, true);
/*     */     
/* 554 */     if (shift && this.frontButton.hovering(mouseX, mouseY))
/*     */     {
/* 556 */       for (int i = 0; i < this.sideButtons.size(); i++)
/*     */       {
/* 558 */         Messages.INSTANCE.sendToServer(new PacketSettingsButton(((BlockIronFurnaceContainerBase)getMenu()).getPos(), i, 0));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClickedRedstoneButtons(double mouseX, double mouseY) {
/* 568 */     if (showInventoryButtons()) {
/* 569 */       boolean shift = isShiftKeyDown();
/* 570 */       this.addButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 9, ((BlockIronFurnaceContainerBase)getMenu()).getComSub() + 1, (!shift && ((BlockIronFurnaceContainerBase)getMenu()).getComSub() < 15));
/* 571 */       this.subButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 9, ((BlockIronFurnaceContainerBase)getMenu()).getComSub() - 1, (shift && ((BlockIronFurnaceContainerBase)getMenu()).getComSub() > 0));
/* 572 */       this.redstoneIgnoredButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 8, 0, (((BlockIronFurnaceContainerBase)getMenu()).getRedstoneMode() != 0));
/* 573 */       this.redstoneLowButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 8, 2, (((BlockIronFurnaceContainerBase)getMenu()).getRedstoneMode() != 2 && shift));
/* 574 */       this.redstoneHighButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 8, 1, (((BlockIronFurnaceContainerBase)getMenu()).getRedstoneMode() != 1 && !shift));
/* 575 */       this.comparatorButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 8, 3, (((BlockIronFurnaceContainerBase)getMenu()).getRedstoneMode() != 3));
/* 576 */       this.comparatorSubButton.onClick(mouseX, mouseY, ((BlockIronFurnaceContainerBase)getMenu()).getPos(), 8, 4, (((BlockIronFurnaceContainerBase)getMenu()).getRedstoneMode() != 4));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isShiftKeyDown() {
/* 581 */     return (isKeyDown(340) || isKeyDown(344));
/*     */   }
/*     */   
/*     */   public static boolean isKeyDown(int glfw) {
/* 585 */     InputConstants.Key key = InputConstants.Type.KEYSYM.getOrCreate(glfw);
/* 586 */     int keyCode = key.getValue();
/* 587 */     if (keyCode != InputConstants.UNKNOWN.getValue()) {
/* 588 */       long windowHandle = Minecraft.getInstance().getWindow().getWindow();
/*     */       try {
/* 590 */         if (key.getType() == InputConstants.Type.KEYSYM) {
/* 591 */           return InputConstants.isKeyDown(windowHandle, keyCode);
/*     */         
/*     */         }
/*     */       }
/* 595 */       catch (Exception exception) {}
/*     */     } 
/*     */     
/* 598 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\gui\furnaces\BlockIronFurnaceScreenBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */