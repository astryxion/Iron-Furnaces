/*     */ package ironfurnaces.jei;
/*     */ import com.google.common.collect.Lists;
/*     */ import ironfurnaces.Config;
/*     */ import ironfurnaces.init.Registration;
/*     */ import ironfurnaces.recipes.GeneratorRecipe;
/*     */ import ironfurnaces.recipes.SimpleGeneratorRecipe;
/*     */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*     */ import java.util.List;
/*     */ import mezz.jei.api.constants.VanillaTypes;
/*     */ import mezz.jei.api.IModPlugin;
/*     */ import mezz.jei.api.JeiPlugin;
/*     */ import mezz.jei.api.constants.RecipeTypes;
/*     */ import mezz.jei.api.recipe.category.IRecipeCategory;
/*     */ import mezz.jei.api.registration.IAdvancedRegistration;
/*     */ import mezz.jei.api.registration.IRecipeCatalystRegistration;
/*     */ import mezz.jei.api.registration.IRecipeCategoryRegistration;
/*     */ import mezz.jei.api.registration.IRecipeRegistration;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ 
/*     */ @JeiPlugin
/*     */ public class IronFurnacesJEIPlugin implements IModPlugin {
/*     */   public ResourceLocation getPluginUid() {
/*  30 */     return new ResourceLocation("ironfurnaces", "plugin_ironfurnaces");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAdvanced(IAdvancedRegistration registration) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerCategories(IRecipeCategoryRegistration registration) {
/*  40 */     if (((Boolean)Config.enableJeiPlugin.get()).booleanValue()) {
/*     */       
/*  42 */       registration.addRecipeCategories(new IRecipeCategory[] { new RecipeCategoryGeneratorBlasting(registration.getJeiHelpers().getGuiHelper()) });
/*  43 */       registration.addRecipeCategories(new IRecipeCategory[] { new RecipeCategoryGeneratorSmoking(registration.getJeiHelpers().getGuiHelper()) });
/*  44 */       registration.addRecipeCategories(new IRecipeCategory[] { new RecipeCategoryGeneratorRegular(registration.getJeiHelpers().getGuiHelper()) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerRecipes(IRecipeRegistration registration) {
/*  51 */     if (((Boolean)Config.enableJeiPlugin.get()).booleanValue()) {
/*     */       List<ItemStack> hidden = Lists.newArrayList();
/*     */       if (!Registration.isAtmContentAvailable()) {
/*     */         hidden.add(new ItemStack((ItemLike) Registration.ALLTHEMODIUM_FURNACE_ITEM.get()));
/*     */         hidden.add(new ItemStack((ItemLike) Registration.VIBRANIUM_FURNACE_ITEM.get()));
/*     */         hidden.add(new ItemStack((ItemLike) Registration.UNOBTAINIUM_FURNACE_ITEM.get()));
/*     */         hidden.add(new ItemStack((ItemLike) Registration.ALLTHEMODIUM_UPGRADE.get()));
/*     */         hidden.add(new ItemStack((ItemLike) Registration.VIBRANIUM_UPGRADE.get()));
/*     */         hidden.add(new ItemStack((ItemLike) Registration.UNOBTAINIUM_UPGRADE.get()));
/*     */       }
/*     */       if (!Registration.isSilverContentAvailable()) {
/*     */         hidden.add(new ItemStack((ItemLike) Registration.SILVER_FURNACE_ITEM.get()));
/*     */         hidden.add(new ItemStack((ItemLike) Registration.SILVER_UPGRADE.get()));
/*     */         hidden.add(new ItemStack((ItemLike) Registration.SILVER2_UPGRADE.get()));
/*     */         hidden.add(new ItemStack((ItemLike) Registration.GOLD2_UPGRADE.get()));
/*     */       }
/*     */       if (!hidden.isEmpty()) {
/*     */         registration
/*     */             .getIngredientManager()
/*     */             .removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, hidden);
/*     */       }
/*     */ 
/*     */       
/*  54 */       List<SimpleGeneratorRecipe> recipes = Lists.newArrayList();
/*  55 */       for (Item item : BuiltInRegistries.ITEM) {
/*     */         
/*  57 */         if (ForgeHooks.getBurnTime(new ItemStack((ItemLike)item), RecipeType.SMELTING) > 0) {
/*     */           
/*  59 */           ItemStack stack = new ItemStack((ItemLike)item);
/*  60 */           recipes.add(
/*     */               new SimpleGeneratorRecipe(
/*     */                   Registration.SIMPLE_GENERATOR_REGULAR_TYPE.get(),
/*     */                   ForgeHooks.getBurnTime(new ItemStack((ItemLike)item), RecipeType.SMELTING) * 20,
/*     */                   stack));
/*     */         } 
/*     */       } 
/*  63 */       registration.addRecipes(RecipeCategoryGeneratorRegular.JEI_RECIPE_TYPE, recipes);
/*     */       
/*  65 */       List<GeneratorRecipe> recipes1 = Lists.newArrayList();
/*  66 */       List<GeneratorRecipe> list =
/*     */           Minecraft.getInstance()
/*     */               .level
/*     */               .getRecipeManager()
/*     */               .getAllRecipesFor(Registration.GENERATOR_RECIPE_TYPE.get());
/*  67 */       for (GeneratorRecipe item : list)
/*     */       {
/*  69 */         recipes1.add(item);
/*     */       }
/*  71 */       registration.addRecipes(RecipeCategoryGeneratorBlasting.JEI_RECIPE_TYPE, recipes1);
/*     */       
/*  73 */       List<SimpleGeneratorRecipe> recipes2 = Lists.newArrayList();
/*  74 */       for (Item item : BuiltInRegistries.ITEM) {
/*     */         
/*  76 */         if (item.getFoodProperties() != null)
/*     */         {
/*  78 */           if (item.getFoodProperties().getNutrition() > 0) {
/*     */             
/*  80 */             ItemStack stack = new ItemStack((ItemLike)item);
/*  81 */             recipes2.add(
/*     */                 new SimpleGeneratorRecipe(
/*     */                     Registration.SIMPLE_GENERATOR_SMOKING_TYPE.get(),
/*     */                     BlockIronFurnaceTileBase.getSmokingBurn(stack) * 40,
/*     */                     stack));
/*     */           } 
/*     */         }
/*     */       } 
/*  85 */       registration.addRecipes(RecipeCategoryGeneratorSmoking.JEI_RECIPE_TYPE, recipes2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
/*  94 */     if (((Boolean)Config.enableJeiPlugin.get()).booleanValue() && ((Boolean)Config.enableJeiCatalysts.get()).booleanValue()) {
/*  95 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.BLASTING_AUGMENT.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.BLASTING });
/*  96 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.SMOKING_AUGMENT.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.SMOKING });
/*     */       
/*  98 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.GENERATOR_AUGMENT.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeCategoryGeneratorRegular.JEI_RECIPE_TYPE });
/*  99 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.GENERATOR_AUGMENT.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeCategoryGeneratorBlasting.JEI_RECIPE_TYPE });
/* 100 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.GENERATOR_AUGMENT.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeCategoryGeneratorSmoking.JEI_RECIPE_TYPE });
/*     */       
/* 102 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.FACTORY_AUGMENT.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.SMELTING });
/*     */       
/* 104 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.IRON_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.SMELTING });
/* 105 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.GOLD_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.SMELTING });
/* 106 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.DIAMOND_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.SMELTING });
/* 107 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.EMERALD_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.SMELTING });
/* 108 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.OBSIDIAN_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.SMELTING });
/* 109 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.CRYSTAL_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.SMELTING });
/* 110 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.NETHERITE_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.SMELTING });
/* 111 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.COPPER_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.SMELTING });
/*     */       if (Registration.isSilverContentAvailable()) {
/* 112 */         registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.SILVER_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.SMELTING });
/*     */       }
/*     */       
/* 114 */       if (((Boolean)Config.enableRainbowContent.get()).booleanValue()) {
/* 115 */         registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.MILLION_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.SMELTING });
/*     */       }
/*     */       
/* 118 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.IRON_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.FUELING });
/* 119 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.GOLD_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.FUELING });
/* 120 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.DIAMOND_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.FUELING });
/* 121 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.EMERALD_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.FUELING });
/* 122 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.OBSIDIAN_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.FUELING });
/* 123 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.CRYSTAL_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.FUELING });
/* 124 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.NETHERITE_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.FUELING });
/* 125 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.COPPER_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.FUELING });
/*     */       if (Registration.isSilverContentAvailable()) {
/* 126 */         registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.SILVER_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.FUELING });
/*     */       }
/*     */       
/* 128 */       if (((Boolean)Config.enableRainbowContent.get()).booleanValue()) {
/* 129 */         registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.MILLION_FURNACE.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeTypes.FUELING });
/*     */       }
/*     */       
/* 132 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.BLASTING_AUGMENT.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeCategoryGeneratorBlasting.JEI_RECIPE_TYPE });
/* 133 */       registry.addRecipeCatalyst(new ItemStack((ItemLike)Registration.SMOKING_AUGMENT.get()), new mezz.jei.api.recipe.RecipeType[] { RecipeCategoryGeneratorSmoking.JEI_RECIPE_TYPE });
/*     */     } 
/*     */   }
/*     */   
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\jei\IronFurnacesJEIPlugin.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */