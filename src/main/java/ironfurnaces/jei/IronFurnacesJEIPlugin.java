/*
 * Copyright 2025 Astryxion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ironfurnaces.jei;

import com.google.common.collect.Lists;
import ironfurnaces.IronFurnaces;
import ironfurnaces.init.Registration;
import ironfurnaces.recipes.GeneratorRecipe;
import ironfurnaces.recipes.SimpleGeneratorRecipe;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IAdvancedRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

@JeiPlugin
public class IronFurnacesJEIPlugin implements IModPlugin {

    public static final class RecipeTypesJei {
        public static final IRecipeType GENERATOR_REGULAR = IRecipeType.create(IronFurnaces.MOD_ID, "generator_regular", SimpleGeneratorRecipe.class);
        public static final IRecipeType GENERATOR_BLASTING = IRecipeType.create(IronFurnaces.MOD_ID, "generator_blasting", GeneratorRecipe.class);
        public static final IRecipeType GENERATOR_SMOKING = IRecipeType.create(IronFurnaces.MOD_ID, "generator_smoking", SimpleGeneratorRecipe.class);
    }

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "plugin_" + IronFurnaces.MOD_ID);
    }

    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

        registration.addRecipeCategories(new RecipeCategoryGeneratorBlasting(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new RecipeCategoryGeneratorSmoking(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new RecipeCategoryGeneratorRegular(registration.getJeiHelpers().getGuiHelper()));


    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Level level = Minecraft.getInstance().level;
        boolean silverAvailable = level != null && Registration.isSilverContentAvailable(level.registryAccess());
        boolean atmAvailable = Registration.isAtmContentAvailable();

        List<ItemStack> hidden = Lists.newArrayList();
        if (!atmAvailable) {
            hidden.add(new ItemStack(Registration.ALLTHEMODIUM_FURNACE_ITEM));
            hidden.add(new ItemStack(Registration.VIBRANIUM_FURNACE_ITEM));
            hidden.add(new ItemStack(Registration.UNOBTAINIUM_FURNACE_ITEM));
            hidden.add(new ItemStack(Registration.ALLTHEMODIUM_UPGRADE));
            hidden.add(new ItemStack(Registration.VIBRANIUM_UPGRADE));
            hidden.add(new ItemStack(Registration.UNOBTAINIUM_UPGRADE));
        }
        if (!silverAvailable) {
            hidden.add(new ItemStack(Registration.SILVER_FURNACE_ITEM));
            hidden.add(new ItemStack(Registration.SILVER_UPGRADE));
            hidden.add(new ItemStack(Registration.SILVER2_UPGRADE));
            hidden.add(new ItemStack(Registration.GOLD2_UPGRADE));
        }
        if (!hidden.isEmpty()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, hidden);
        }

        List<SimpleGeneratorRecipe> recipes = Lists.newArrayList();
        if (level != null) {
            var fuelValues = level.fuelValues();
            for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
                ItemStack stack = new ItemStack(item);
                int burnTime = fuelValues.burnDuration(stack);
                if (burnTime > 0) {
                    recipes.add(new SimpleGeneratorRecipe(burnTime * 20, stack));
                }
            }
        }
        registration.addRecipes(RecipeTypesJei.GENERATOR_REGULAR, recipes);

        List<GeneratorRecipe> recipes1 = Lists.newArrayList();
        Minecraft mc = Minecraft.getInstance();
        if (mc.hasSingleplayerServer()) {
            RecipeManager recipeManager = mc.getSingleplayerServer().overworld().recipeAccess();
            for (RecipeHolder<?> holder : recipeManager.getRecipes()) {
                if (holder.value().getType() == Registration.GENERATOR_RECIPE_TYPE && holder.value() instanceof GeneratorRecipe gr) {
                    recipes1.add(gr);
                }
            }
        }
        registration.addRecipes(RecipeTypesJei.GENERATOR_BLASTING, recipes1);

        List<SimpleGeneratorRecipe> recipes2 = Lists.newArrayList();
        for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
            ItemStack stack = new ItemStack(item);
            if (stack.get(DataComponents.FOOD) != null && stack.get(DataComponents.FOOD).nutrition() > 0) {
                recipes2.add(new SimpleGeneratorRecipe(BlockIronFurnaceTileBase.getSmokingBurn(stack) * 40, stack));
            }
        }
        registration.addRecipes(RecipeTypesJei.GENERATOR_SMOKING, recipes2);


    }


    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addCraftingStation(RecipeTypes.BLASTING, new ItemStack(Registration.BLASTING_AUGMENT));
        registry.addCraftingStation(RecipeTypes.SMOKING, new ItemStack(Registration.SMOKING_AUGMENT));

        registry.addCraftingStation(RecipeTypesJei.GENERATOR_REGULAR, new ItemStack(Registration.GENERATOR_AUGMENT));
        registry.addCraftingStation(RecipeTypesJei.GENERATOR_BLASTING, new ItemStack(Registration.GENERATOR_AUGMENT));
        registry.addCraftingStation(RecipeTypesJei.GENERATOR_SMOKING, new ItemStack(Registration.GENERATOR_AUGMENT));

        registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.FACTORY_AUGMENT));

        registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.IRON_FURNACE));
        registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.GOLD_FURNACE));
        registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.DIAMOND_FURNACE));
        registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.EMERALD_FURNACE));
        registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.OBSIDIAN_FURNACE));
        registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.CRYSTAL_FURNACE));
        registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.NETHERITE_FURNACE));
        registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.COPPER_FURNACE));
        Level level = Minecraft.getInstance().level;
        if (level != null && Registration.isSilverContentAvailable(level.registryAccess())) {
            registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.SILVER_FURNACE));
        }


        registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.MILLION_FURNACE));

        registry.addCraftingStation(RecipeTypesJei.GENERATOR_BLASTING, new ItemStack(Registration.BLASTING_AUGMENT));
        registry.addCraftingStation(RecipeTypesJei.GENERATOR_SMOKING, new ItemStack(Registration.SMOKING_AUGMENT));



        if (Registration.isAtmContentAvailable())
        {
            registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.ALLTHEMODIUM_FURNACE));
            registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.VIBRANIUM_FURNACE));
            registry.addCraftingStation(RecipeTypes.SMELTING, new ItemStack(Registration.UNOBTAINIUM_FURNACE));
        }
    }
}