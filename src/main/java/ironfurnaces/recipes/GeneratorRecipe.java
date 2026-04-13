/*     */ package ironfurnaces.recipes;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import ironfurnaces.init.Registration;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ import net.minecraft.world.item.crafting.RecipeSerializer;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.item.crafting.ShapedRecipe;
/*     */ import net.minecraft.world.level.Level;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public class GeneratorRecipe
/*     */   implements Recipe<Container>
/*     */ {
/*     */   private final ResourceLocation recipeId;
/*     */   private int energy;
/*     */   private ItemStack stack;
/*     */   
/*     */   public GeneratorRecipe(ResourceLocation recipeId, int energy, ItemStack stack) {
/*  26 */     this.recipeId = recipeId;
/*  27 */     this.energy = energy;
/*  28 */     this.stack = stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIncomplete() {
/*  33 */     return this.stack.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getIngredient() {
/*  38 */     return this.stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergy() {
/*  43 */     return this.energy;
/*     */   }
/*     */   
/*     */   public static int getTotalCount(Container inventory, ItemStack input) {
/*  47 */     ItemStack stack = inventory.getItem(0);
/*  48 */     if (!stack.isEmpty() && stack.getItem() == input.getItem()) {
/*  49 */       return stack.getCount();
/*     */     }
/*  51 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(Container inv, Level level) {
/*  57 */     int required = this.stack.getCount();
/*  58 */     int found = getTotalCount(inv, this.stack);
/*  59 */     return (found >= required);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
/*  64 */     return ItemStack.EMPTY;
/*     */   }
/*     */   
/*     */   public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getResultItem(RegistryAccess p_267052_) {
/*  73 */     return this.stack;
/*     */   }
/*     */   
/*     */   public boolean isSpecial() {
/*  77 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getId() {
/*  82 */     return this.recipeId;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeSerializer<?> getSerializer() {
/*  87 */     return (RecipeSerializer)Registration.GENERATOR_RECIPE_SERIALIZER.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeType<?> getType() {
/*  92 */     return (RecipeType)Registration.GENERATOR_RECIPE_TYPE.get();
/*     */   }
/*     */   
/*     */   public static class Serializer
/*     */     implements RecipeSerializer<GeneratorRecipe> {
/*     */     public GeneratorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
/*  98 */       int energy = GsonHelper.getAsInt(json, "energy", 10000);
/*  99 */       ItemStack input = ShapedRecipe.itemStackFromJson(json);
/* 100 */       GeneratorRecipe recipe = new GeneratorRecipe(recipeId, energy, input);
/* 101 */       return recipe;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public GeneratorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
/* 107 */       GeneratorRecipe recipe = new GeneratorRecipe(recipeId, buffer.readVarInt(), buffer.readItem());
/* 108 */       return recipe;
/*     */     }
/*     */ 
/*     */     
/*     */     public void toNetwork(FriendlyByteBuf buffer, GeneratorRecipe recipe) {
/* 113 */       buffer.writeVarInt(recipe.energy);
/* 114 */       buffer.writeItem(recipe.stack);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\recipes\GeneratorRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */