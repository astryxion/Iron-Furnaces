package ironfurnaces.recipes;

import ironfurnaces.init.Registration;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class SimpleGeneratorRecipe implements Recipe<Container> {
  private final RecipeType<?> recipeType;
  private final ResourceLocation recipeId;
  private final int energy;
  private final ItemStack stack;

  public SimpleGeneratorRecipe(RecipeType<?> recipeType, int energy, ItemStack stack) {
    this.recipeType = recipeType;
    this.energy = energy;
    this.stack = stack;
    ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
    ResourceLocation typeKey = BuiltInRegistries.RECIPE_TYPE.getKey(recipeType);
    String typePart = typeKey != null ? typeKey.getNamespace() + "_" + typeKey.getPath() : "unknown";
    this.recipeId =
        new ResourceLocation(
            "ironfurnaces", "simple_gen/" + typePart + "/" + itemId.getNamespace() + "_" + itemId.getPath() + "/" + energy);
  }

  @Override
  public boolean isIncomplete() {
    return this.stack.isEmpty();
  }

  @Override
  public boolean matches(Container inv, Level level) {
    return GeneratorRecipe.getTotalCount(inv, this.stack) >= this.stack.getCount();
  }

  @Override
  public ItemStack assemble(Container inv, RegistryAccess registryAccess) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canCraftInDimensions(int width, int height) {
    return true;
  }

  @Override
  public ItemStack getResultItem(RegistryAccess registryAccess) {
    return this.stack;
  }

  @Override
  public boolean isSpecial() {
    return true;
  }

  @Override
  public ResourceLocation getId() {
    return this.recipeId;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return Registration.GENERATOR_RECIPE_SERIALIZER.get();
  }

  @Override
  public RecipeType<?> getType() {
    return this.recipeType;
  }

  public int getEnergy() {
    return this.energy;
  }

  public ItemStack getIngredient() {
    return this.stack;
  }
}
