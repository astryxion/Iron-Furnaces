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

package ironfurnaces.init;

import ironfurnaces.IronFurnaces;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class AtmRecipeSupport {

    public static final Identifier LISTENER_ID = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "atm_recipes");

    private static final String PATTERN_TOP = "B#B";
    private static final String PATTERN_MID = "#X#";
    private static final String PATTERN_BOT = "B#B";

    private static MinecraftServer activeServer;

    private AtmRecipeSupport() {
    }

    public static void onServerStarted(ServerStartedEvent event) {
        activeServer = event.getServer();
        ensureAtmRecipes(event.getServer());
    }

    public static void onServerStopping(ServerStoppingEvent event) {
        activeServer = null;
    }

    public static void onAddServerReloadListeners(AddServerReloadListenersEvent event) {
        event.addListener(LISTENER_ID, new AtmRecipeReloadListener());
    }

    public static void ensureAtmRecipes(MinecraftServer server) {
        if (!Registration.isAtmContentAvailable(server.registryAccess())) {
            return;
        }

        RecipeManager recipeManager = server.getRecipeManager();
        HolderLookup.Provider registries = server.registryAccess();
        FeatureFlagSet enabledFeatures = server.getWorldData().enabledFeatures();
        List<RecipeHolder<?>> additions = new ArrayList<>();

        addIfMissing(registries, recipeManager, additions, "furnaces/allthemodium_furnace",
                Registration.ALLTHEMODIUM_FURNACE.get(),
                "c:ingots/allthemodium", "c:furnaces/netherite", "ironfurnaces:allthemodium_corners");
        addIfMissing(registries, recipeManager, additions, "furnaces/vibranium_furnace",
                Registration.VIBRANIUM_FURNACE.get(),
                "c:ingots/vibranium", "c:furnaces/allthemodium", "ironfurnaces:vibranium_corners");
        addIfMissing(registries, recipeManager, additions, "furnaces/unobtainium_furnace",
                Registration.UNOBTAINIUM_FURNACE.get(),
                "c:ingots/unobtainium", "c:furnaces/vibranium", "ironfurnaces:unobtainium_corners");
        addIfMissing(registries, recipeManager, additions, "upgrades/upgrade_allthemodium",
                Registration.ALLTHEMODIUM_UPGRADE.get(),
                "c:ingots/allthemodium", "ironfurnaces:netherite_upgrade_crafting", "ironfurnaces:allthemodium_corners");
        addIfMissing(registries, recipeManager, additions, "upgrades/upgrade_vibranium",
                Registration.VIBRANIUM_UPGRADE.get(),
                "c:ingots/vibranium", "c:ingots/allthemodium", "ironfurnaces:vibranium_corners");
        addIfMissing(registries, recipeManager, additions, "upgrades/upgrade_unobtainium",
                Registration.UNOBTAINIUM_UPGRADE.get(),
                "c:ingots/unobtainium", "c:ingots/vibranium", "ironfurnaces:unobtainium_corners");

        if (additions.isEmpty()) {
            return;
        }

        try {
            mergeRecipes(recipeManager, additions, enabledFeatures);
            for (RecipeHolder<?> holder : additions) {
                IronFurnaces.LOGGER.info("Injected missing ATM crafting recipe: {}", holder.id().identifier());
            }
        } catch (ReflectiveOperationException e) {
            IronFurnaces.LOGGER.error("Failed to inject ATM crafting recipes", e);
        }
    }

    private static void addIfMissing(HolderLookup.Provider registries, RecipeManager recipeManager,
                                     List<RecipeHolder<?>> additions, String path, ItemLike result,
                                     String ingotTag, String centerTag, String cornerTag) {
        ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE,
                Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, path));
        if (recipeManager.byKey(key).isPresent()) {
            return;
        }

        Optional<ShapedRecipe> recipe = buildRecipe(registries, result, ingotTag, centerTag, cornerTag);
        if (recipe.isEmpty()) {
            IronFurnaces.LOGGER.warn(
                    "ATM crafting recipe {} was not loaded and could not be injected (missing tag ingredients: {}, {}, {})",
                    key.identifier(), ingotTag, centerTag, cornerTag);
            return;
        }

        additions.add(new RecipeHolder<>(key, recipe.get()));
    }

    private static Optional<ShapedRecipe> buildRecipe(HolderLookup.Provider registries, ItemLike result,
                                                      String ingotTag, String centerTag, String cornerTag) {
        Optional<Ingredient> ingots = ingredientFromTag(registries, ingotTag);
        Optional<Ingredient> center = ingredientFromTag(registries, centerTag);
        Optional<Ingredient> corners = ingredientFromTag(registries, cornerTag);
        if (ingots.isEmpty() || center.isEmpty() || corners.isEmpty()) {
            return Optional.empty();
        }

        ShapedRecipePattern pattern = ShapedRecipePattern.of(
                Map.of(
                        '#', ingots.get(),
                        'X', center.get(),
                        'B', corners.get()
                ),
                PATTERN_TOP,
                PATTERN_MID,
                PATTERN_BOT
        );

        return Optional.of(new ShapedRecipe(
                new Recipe.CommonInfo(false),
                new CraftingRecipe.CraftingBookInfo(CraftingBookCategory.MISC, ""),
                pattern,
                new ItemStackTemplate(result.asItem())
        ));
    }

    private static Optional<Ingredient> ingredientFromTag(HolderLookup.Provider registries, String tagId) {
        TagKey<Item> tag = TagKey.create(Registries.ITEM, Identifier.parse(tagId));
        Optional<HolderSet.Named<Item>> holders = registries.lookupOrThrow(Registries.ITEM).get(tag);
        if (holders.isEmpty() || holders.get().size() == 0) {
            return Optional.empty();
        }
        return Optional.of(Ingredient.of(holders.get()));
    }

    private static void mergeRecipes(RecipeManager recipeManager, List<RecipeHolder<?>> additions,
                                     FeatureFlagSet enabledFeatures) throws ReflectiveOperationException {
        Field recipesField = RecipeManager.class.getDeclaredField("recipes");
        recipesField.setAccessible(true);

        List<RecipeHolder<?>> merged = new ArrayList<>(recipeManager.recipeMap().values());
        merged.addAll(additions);
        recipesField.set(recipeManager, RecipeMap.create(merged));
        recipeManager.finalizeRecipeLoading(enabledFeatures);
    }

    private static final class AtmRecipeReloadListener extends SimplePreparableReloadListener<Void> {
        @Override
        protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
            return null;
        }

        @Override
        protected void apply(Void object, ResourceManager resourceManager, ProfilerFiller profiler) {
            if (activeServer != null) {
                // Defer until after the full reload cycle (including recipes) has finished.
                activeServer.execute(() -> ensureAtmRecipes(activeServer));
            }
        }
    }
}
