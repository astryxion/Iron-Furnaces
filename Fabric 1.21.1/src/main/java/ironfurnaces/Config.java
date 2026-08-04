/*
 * Copyright 2025 pizzaatime and XenoMustache
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

package ironfurnaces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Mth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_FURNACE = "furnaces";
    public static final String CATEGORY_MODDED_FURNACE = "modded_furnaces";
    public static final String CATEGORY_MISC = "misc";

    public static IntValue ironFurnaceSpeed;
    public static IntValue goldFurnaceSpeed;
    public static IntValue diamondFurnaceSpeed;
    public static IntValue emeraldFurnaceSpeed;
    public static IntValue obsidianFurnaceSpeed;
    public static IntValue crystalFurnaceSpeed;
    public static IntValue netheriteFurnaceSpeed;
    public static IntValue copperFurnaceSpeed;
    public static IntValue silverFurnaceSpeed;
    public static IntValue millionFurnaceSpeed;
    public static IntValue millionFurnacePowerToGenerate;

    public static IntValue ironFurnaceGeneration;
    public static IntValue goldFurnaceGeneration;
    public static IntValue diamondFurnaceGeneration;
    public static IntValue emeraldFurnaceGeneration;
    public static IntValue obsidianFurnaceGeneration;
    public static IntValue crystalFurnaceGeneration;
    public static IntValue netheriteFurnaceGeneration;
    public static IntValue copperFurnaceGeneration;
    public static IntValue silverFurnaceGeneration;
    public static IntValue millionFurnaceGeneration;

    public static IntValue furnaceEnergyCapacityTier0;
    public static IntValue furnaceEnergyCapacityTier1;
    public static IntValue furnaceEnergyCapacityTier2;

    public static IntValue ironFurnaceTier;
    public static IntValue goldFurnaceTier;
    public static IntValue diamondFurnaceTier;
    public static IntValue emeraldFurnaceTier;
    public static IntValue obsidianFurnaceTier;
    public static IntValue crystalFurnaceTier;
    public static IntValue netheriteFurnaceTier;
    public static IntValue copperFurnaceTier;
    public static IntValue silverFurnaceTier;
    public static IntValue millionFurnaceTier;

    public static IntValue recipeMaxXPLevel;

    public static BooleanValue showErrors;
    public static BooleanValue disableLightupdates;

    public static IntValue vibraniumFurnaceSpeed;
    public static IntValue unobtainiumFurnaceSpeed;
    public static IntValue allthemodiumFurnaceSpeed;
    public static IntValue vibraniumFurnaceSmeltMult;
    public static IntValue unobtainiumFurnaceSmeltMult;
    public static IntValue allthemodiumFurnaceSmeltMult;

    public static IntValue allthemodiumGeneration;
    public static IntValue vibraniumGeneration;
    public static IntValue unobtainiumGeneration;

    public static IntValue allthemodiumFurnaceTier;
    public static IntValue vibraniumFurnaceTier;
    public static IntValue unobtainiumFurnaceTier;

    private static final List<IntValue> INT_VALUES = new ArrayList<>();
    private static final List<BooleanValue> BOOL_VALUES = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final class IntValue {
        final String key;
        private final int min;
        private final int max;
        private final int defaultValue;
        private int value;

        public IntValue(String key, int defaultValue, int min, int max) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.min = min;
            this.max = max;
            this.value = defaultValue;
            INT_VALUES.add(this);
        }

        public int get() {
            return value;
        }

        public void applyLoaded(int v) {
            value = Mth.clamp(v, min, max);
        }
    }

    public static final class BooleanValue {
        final String key;
        private final boolean defaultValue;
        private boolean value;

        public BooleanValue(String key, boolean defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.value = defaultValue;
            BOOL_VALUES.add(this);
        }

        public boolean get() {
            return value;
        }

        public void applyLoaded(boolean v) {
            value = v;
        }
    }

    static {
        ironFurnaceGeneration = new IntValue("furnaces.iron_furnace.generation", 40, 1, 100000);
        goldFurnaceGeneration = new IntValue("furnaces.gold_furnace.generation", 160, 1, 100000);
        diamondFurnaceGeneration = new IntValue("furnaces.diamond_furnace.generation", 240, 1, 100000);
        emeraldFurnaceGeneration = new IntValue("furnaces.emerald_furnace.generation", 320, 1, 100000);
        obsidianFurnaceGeneration = new IntValue("furnaces.obsidian_furnace.generation", 500, 1, 100000);
        crystalFurnaceGeneration = new IntValue("furnaces.crystal_furnace.generation", 360, 1, 100000);
        netheriteFurnaceGeneration = new IntValue("furnaces.netherite_furnace.generation", 1000, 1, 100000);
        millionFurnaceGeneration = new IntValue("furnaces.rainbow_furnace.generation", 2000, 1, 100000);
        copperFurnaceGeneration = new IntValue("furnaces.copper_furnace.generation", 40, 1, 100000);
        silverFurnaceGeneration = new IntValue("furnaces.silver_furnace.generation", 100, 1, 100000);

        furnaceEnergyCapacityTier0 = new IntValue("furnaces.energy.tier_0", 80000, 4000, Integer.MAX_VALUE);
        furnaceEnergyCapacityTier1 = new IntValue("furnaces.energy.tier_1", 200000, 4000, Integer.MAX_VALUE);
        furnaceEnergyCapacityTier2 = new IntValue("furnaces.energy.tier_2", 1000000, 4000, Integer.MAX_VALUE);

        ironFurnaceTier = new IntValue("furnaces.iron_furnace.tier", 0, 0, 2);
        copperFurnaceTier = new IntValue("furnaces.copper_furnace.tier", 0, 0, 2);
        goldFurnaceTier = new IntValue("furnaces.gold_furnace.tier", 1, 0, 2);
        diamondFurnaceTier = new IntValue("furnaces.diamond_furnace.tier", 2, 0, 2);
        emeraldFurnaceTier = new IntValue("furnaces.emerald_furnace.tier", 2, 0, 2);
        silverFurnaceTier = new IntValue("furnaces.silver_furnace.tier", 1, 0, 2);
        crystalFurnaceTier = new IntValue("furnaces.crystal_furnace.tier", 2, 0, 2);
        obsidianFurnaceTier = new IntValue("furnaces.obsidian_furnace.tier", 2, 0, 2);
        netheriteFurnaceTier = new IntValue("furnaces.netherite_furnace.tier", 2, 0, 2);
        millionFurnaceTier = new IntValue("furnaces.million_furnace.tier", 2, 0, 2);

        ironFurnaceSpeed = new IntValue("furnaces.iron_furnace.speed", 160, 2, 72000);
        goldFurnaceSpeed = new IntValue("furnaces.gold_furnace.speed", 120, 2, 72000);
        diamondFurnaceSpeed = new IntValue("furnaces.diamond_furnace.speed", 80, 2, 72000);
        emeraldFurnaceSpeed = new IntValue("furnaces.emerald_furnace.speed", 40, 2, 72000);
        obsidianFurnaceSpeed = new IntValue("furnaces.obsidian_furnace.speed", 20, 2, 72000);
        crystalFurnaceSpeed = new IntValue("furnaces.crystal_furnace.speed", 40, 2, 72000);
        netheriteFurnaceSpeed = new IntValue("furnaces.netherite_furnace.speed", 5, 2, 72000);
        copperFurnaceSpeed = new IntValue("furnaces.copper_furnace.speed", 180, 2, 72000);
        silverFurnaceSpeed = new IntValue("furnaces.silver_furnace.speed", 140, 2, 72000);
        millionFurnaceSpeed = new IntValue("furnaces.rainbow_furnace.speed", 20, 2, 72000);
        millionFurnacePowerToGenerate = new IntValue("furnaces.rainbow_furnace.rainbow_generation", 50000, 1, 100000000);
        recipeMaxXPLevel = new IntValue("furnaces.recipeMaxXPLevel.level", 100, 1, 1000);

        allthemodiumFurnaceSpeed = new IntValue("modded_furnaces.allthemodium_furnace.speed", 5, 1, 72000);
        vibraniumFurnaceSpeed = new IntValue("modded_furnaces.vibranium_furnace.speed", 3, 1, 72000);
        unobtainiumFurnaceSpeed = new IntValue("modded_furnaces.unobtainium_furnace.speed", 1, 1, 72000);
        allthemodiumFurnaceSmeltMult = new IntValue("modded_furnaces.allthemodium_furnace.mult", 16, 1, 64);
        vibraniumFurnaceSmeltMult = new IntValue("modded_furnaces.vibranium_furnace.mult", 32, 1, 64);
        unobtainiumFurnaceSmeltMult = new IntValue("modded_furnaces.unobtainium_furnace.mult", 64, 1, 64);
        allthemodiumGeneration = new IntValue("modded_furnaces.allthemodium_furnace.generation", 2000, 1, 100000);
        vibraniumGeneration = new IntValue("modded_furnaces.vibranium_furnace.generation", 3000, 1, 100000);
        unobtainiumGeneration = new IntValue("modded_furnaces.unobtainium_furnace.generation", 5000, 1, 100000);
        allthemodiumFurnaceTier = new IntValue("modded_furnaces.allthemodium_furnace.tier", 2, 0, 2);
        vibraniumFurnaceTier = new IntValue("modded_furnaces.vibranium_furnace.tier", 2, 0, 2);
        unobtainiumFurnaceTier = new IntValue("modded_furnaces.unobtainium_furnace.tier", 2, 0, 2);

        disableLightupdates = new BooleanValue("misc.lightupdates", false);
        showErrors = new BooleanValue("misc.show_errors", false);
    }

    public static void load() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("ironfurnaces.json");
        if (!Files.isRegularFile(path)) {
            save();
            return;
        }
        try {
            String text = Files.readString(path, StandardCharsets.UTF_8);
            JsonObject root = GSON.fromJson(text, JsonObject.class);
            if (root == null) {
                return;
            }
            for (IntValue v : INT_VALUES) {
                if (root.has(v.key)) {
                    v.applyLoaded(root.get(v.key).getAsInt());
                }
            }
            for (BooleanValue v : BOOL_VALUES) {
                if (root.has(v.key)) {
                    v.applyLoaded(root.get(v.key).getAsBoolean());
                }
            }
        } catch (IOException e) {
            IronFurnaces.LOGGER.error("Failed to load config {}", path, e);
        }
    }

    public static void save() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("ironfurnaces.json");
        JsonObject root = new JsonObject();
        for (IntValue v : INT_VALUES) {
            root.addProperty(v.key, v.get());
        }
        for (BooleanValue v : BOOL_VALUES) {
            root.addProperty(v.key, v.get());
        }
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, GSON.toJson(root), StandardCharsets.UTF_8);
        } catch (IOException e) {
            IronFurnaces.LOGGER.error("Failed to save config {}", path, e);
        }
    }
}
