/*     */ package ironfurnaces.jei;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.InputConstants;
/*     */ import ironfurnaces.init.Registration;
/*     */ import ironfurnaces.recipes.SimpleGeneratorRecipe;
/*     */ import ironfurnaces.util.StringHelper;
/*     */ import java.util.List;
/*     */ import mezz.jei.api.constants.VanillaTypes;
/*     */ import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
/*     */ import mezz.jei.api.gui.drawable.IDrawable;
/*     */ import mezz.jei.api.gui.drawable.IDrawableAnimated;
/*     */ import mezz.jei.api.gui.drawable.IDrawableStatic;
/*     */ import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
/*     */ import mezz.jei.api.helpers.IGuiHelper;
/*     */ import mezz.jei.api.ingredients.IIngredientType;
/*     */ import mezz.jei.api.recipe.IFocusGroup;
/*     */ import mezz.jei.api.recipe.RecipeIngredientRole;
/*     */ import mezz.jei.api.recipe.category.IRecipeCategory;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import com.google.common.collect.Lists;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ public class RecipeCategoryGeneratorRegular
/*     */   implements IRecipeCategory<SimpleGeneratorRecipe>
/*     */ {
/*  33 */   public static final ResourceLocation UID = new ResourceLocation("ironfurnaces", "category_generator_regular");
/*     */   public static final mezz.jei.api.recipe.RecipeType<SimpleGeneratorRecipe> JEI_RECIPE_TYPE =
/*     */       mezz.jei.api.recipe.RecipeType.create(UID.getNamespace(), UID.getPath(), SimpleGeneratorRecipe.class);
/*     */   
/*     */   public IGuiHelper guiHelper;
/*     */   
/*     */   protected final IDrawableStatic staticFlame;
/*     */   
/*     */   protected final IDrawableAnimated animatedFlame;
/*     */   protected final IDrawableStatic staticEnergy;
/*     */   protected final IDrawableAnimated animatedEnergy;
/*     */   
/*     */   public RecipeCategoryGeneratorRegular(IGuiHelper guiHelper) {
/*  44 */     this.guiHelper = guiHelper;
/*  45 */     this.staticFlame = guiHelper.createDrawable(new ResourceLocation("ironfurnaces", "textures/gui/jei.png"), 68, 0, 14, 14);
/*  46 */     this.animatedFlame = guiHelper.createAnimatedDrawable(this.staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
/*     */     
/*  48 */     this.staticEnergy = guiHelper.createDrawable(new ResourceLocation("ironfurnaces", "textures/gui/jei.png"), 82, 0, 14, 42);
/*  49 */     this.animatedEnergy = guiHelper.createAnimatedDrawable(this.staticEnergy, 300, IDrawableAnimated.StartDirection.BOTTOM, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public mezz.jei.api.recipe.RecipeType<SimpleGeneratorRecipe> getRecipeType() {
/*  55 */     return JEI_RECIPE_TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getTitle() {
/*  60 */     return (Component)Component.translatable("ironfurnaces.jei_category_regular");
/*     */   }
/*     */ 
/*     */   
/*     */   public IDrawable getBackground() {
/*  65 */     return (IDrawable)this.guiHelper.createDrawable(new ResourceLocation("ironfurnaces", "textures/gui/jei.png"), 0, 0, 68, 42);
/*     */   }
/*     */ 
/*     */   
/*     */   public IDrawable getIcon() {
/*  70 */     return this.guiHelper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, new ItemStack((ItemLike)Registration.GENERATOR_AUGMENT.get()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRecipe(IRecipeLayoutBuilder builder, SimpleGeneratorRecipe recipe, IFocusGroup focuses) {
/*  75 */     builder.addSlot(RecipeIngredientRole.INPUT, 1, 18)
/*  76 */       .addIngredients(Ingredient.of(new ItemLike[] { (ItemLike)recipe.getIngredient().getItem() }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(SimpleGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics stack, double mouseX, double mouseY) {
/*  81 */     this.animatedFlame.draw(stack, 1, 1);
/*  82 */     this.animatedEnergy.draw(stack, 54, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Component> getTooltipStrings(SimpleGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
/*  87 */     List<Component> list = Lists.newArrayList();
/*  88 */     if (mouseX >= 55.0D && mouseX <= 68.0D && mouseY >= 1.0D && mouseY <= 42.0D)
/*     */     {
/*  90 */       list.add(Component.literal(StringHelper.displayEnergy(recipe.getEnergy()).get(0)));
/*     */     }
/*  92 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleInput(SimpleGeneratorRecipe recipe, double mouseX, double mouseY, InputConstants.Key input) {
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHandled(SimpleGeneratorRecipe recipe) {
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getRegistryName(SimpleGeneratorRecipe recipe) {
/* 107 */     return recipe.getId();
/*     */   }
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\jei\RecipeCategoryGeneratorRegular.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */