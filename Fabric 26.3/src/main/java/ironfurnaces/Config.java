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

package ironfurnaces;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Common config aligned with NeoForge {@code ironfurnaces-common.toml} semantics.
 * Stored as {@code config/ironfurnaces.json} (no third-party config library).
 */
public final class Config {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_FURNACE = "furnaces";
    public static final String CATEGORY_MODDED_FURNACE = "modded_furnaces";
    public static final String CATEGORY_MISC = "misc";

    public static final IntValue ironFurnaceSpeed = new IntValue(160, 2, 72000);
    public static final IntValue goldFurnaceSpeed = new IntValue(120, 2, 72000);
    public static final IntValue diamondFurnaceSpeed = new IntValue(80, 2, 72000);
    public static final IntValue emeraldFurnaceSpeed = new IntValue(40, 2, 72000);
    public static final IntValue obsidianFurnaceSpeed = new IntValue(20, 2, 72000);
    public static final IntValue crystalFurnaceSpeed = new IntValue(40, 2, 72000);
    public static final IntValue netheriteFurnaceSpeed = new IntValue(5, 2, 72000);
    public static final IntValue copperFurnaceSpeed = new IntValue(180, 2, 72000);
    public static final IntValue silverFurnaceSpeed = new IntValue(140, 2, 72000);
    public static final IntValue millionFurnaceSpeed = new IntValue(20, 2, 72000);
    public static final IntValue millionFurnacePowerToGenerate = new IntValue(50000, 1, 100000000);

    public static final IntValue ironFurnaceGeneration = new IntValue(40, 1, 100000);
    public static final IntValue goldFurnaceGeneration = new IntValue(160, 1, 100000);
    public static final IntValue diamondFurnaceGeneration = new IntValue(240, 1, 100000);
    public static final IntValue emeraldFurnaceGeneration = new IntValue(320, 1, 100000);
    public static final IntValue obsidianFurnaceGeneration = new IntValue(500, 1, 100000);
    public static final IntValue crystalFurnaceGeneration = new IntValue(360, 1, 100000);
    public static final IntValue netheriteFurnaceGeneration = new IntValue(1000, 1, 100000);
    public static final IntValue copperFurnaceGeneration = new IntValue(40, 1, 100000);
    public static final IntValue silverFurnaceGeneration = new IntValue(100, 1, 100000);
    public static final IntValue millionFurnaceGeneration = new IntValue(2000, 1, 100000);

    public static final IntValue furnaceEnergyCapacityTier0 = new IntValue(80000, 4000, Integer.MAX_VALUE);
    public static final IntValue furnaceEnergyCapacityTier1 = new IntValue(200000, 4000, Integer.MAX_VALUE);
    public static final IntValue furnaceEnergyCapacityTier2 = new IntValue(1000000, 4000, Integer.MAX_VALUE);

    public static final IntValue ironFurnaceTier = new IntValue(0, 0, 2);
    public static final IntValue goldFurnaceTier = new IntValue(1, 0, 2);
    public static final IntValue diamondFurnaceTier = new IntValue(2, 0, 2);
    public static final IntValue emeraldFurnaceTier = new IntValue(2, 0, 2);
    public static final IntValue obsidianFurnaceTier = new IntValue(2, 0, 2);
    public static final IntValue crystalFurnaceTier = new IntValue(2, 0, 2);
    public static final IntValue netheriteFurnaceTier = new IntValue(2, 0, 2);
    public static final IntValue copperFurnaceTier = new IntValue(0, 0, 2);
    public static final IntValue silverFurnaceTier = new IntValue(1, 0, 2);
    public static final IntValue millionFurnaceTier = new IntValue(2, 0, 2);

    public static final IntValue recipeMaxXPLevel = new IntValue(100, 1, 1000);

    public static final BoolValue showErrors = new BoolValue(true);
    public static final BoolValue disableLightupdates = new BoolValue(false);

    public static final IntValue vibraniumFurnaceSpeed = new IntValue(3, 1, 72000);
    public static final IntValue unobtainiumFurnaceSpeed = new IntValue(1, 1, 72000);
    public static final IntValue allthemodiumFurnaceSpeed = new IntValue(5, 1, 72000);
    public static final IntValue vibraniumFurnaceSmeltMult = new IntValue(32, 1, 64);
    public static final IntValue unobtainiumFurnaceSmeltMult = new IntValue(64, 1, 64);
    public static final IntValue allthemodiumFurnaceSmeltMult = new IntValue(16, 1, 64);

    public static final IntValue allthemodiumGeneration = new IntValue(2000, 1, 100000);
    public static final IntValue vibraniumGeneration = new IntValue(3000, 1, 100000);
    public static final IntValue unobtainiumGeneration = new IntValue(5000, 1, 100000);

    public static final IntValue allthemodiumFurnaceTier = new IntValue(2, 0, 2);
    public static final IntValue vibraniumFurnaceTier = new IntValue(2, 0, 2);
    public static final IntValue unobtainiumFurnaceTier = new IntValue(2, 0, 2);

    private Config() {
    }

    public static void load() {
        Path dir = FabricLoader.getInstance().getConfigDir();
        Path path = dir.resolve("ironfurnaces.json");
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(dir);
                saveDefaults(path);
            } catch (IOException e) {
                LOGGER.error("Failed to write default ironfurnaces.json", e);
            }
            return;
        }
        try (Reader r = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JsonObject root = JsonParser.parseReader(r).getAsJsonObject();
            loadFromRoot(root);
        } catch (Exception e) {
            LOGGER.error("Failed to load ironfurnaces.json; using defaults", e);
        }
    }

    private static void loadFromRoot(JsonObject root) {
        JsonObject general = obj(root, CATEGORY_GENERAL);
        applyBool(general, "show_errors", showErrors);

        JsonObject misc = obj(root, CATEGORY_MISC);
        applyBool(misc, "lightupdates", disableLightupdates);

        JsonObject furnaces = obj(root, CATEGORY_FURNACE);
        JsonObject energy = obj(furnaces, "energy");
        applyInt(energy, "tier_0", furnaceEnergyCapacityTier0);
        applyInt(energy, "tier_1", furnaceEnergyCapacityTier1);
        applyInt(energy, "tier_2", furnaceEnergyCapacityTier2);

        applyFurnaceGroup(furnaces, "iron_furnace", ironFurnaceTier, ironFurnaceSpeed, ironFurnaceGeneration);
        applyFurnaceGroup(furnaces, "copper_furnace", copperFurnaceTier, copperFurnaceSpeed, copperFurnaceGeneration);
        applyFurnaceGroup(furnaces, "gold_furnace", goldFurnaceTier, goldFurnaceSpeed, goldFurnaceGeneration);
        applyFurnaceGroup(furnaces, "diamond_furnace", diamondFurnaceTier, diamondFurnaceSpeed, diamondFurnaceGeneration);
        applyFurnaceGroup(furnaces, "emerald_furnace", emeraldFurnaceTier, emeraldFurnaceSpeed, emeraldFurnaceGeneration);
        applyFurnaceGroup(furnaces, "silver_furnace", silverFurnaceTier, silverFurnaceSpeed, silverFurnaceGeneration);
        applyFurnaceGroup(furnaces, "crystal_furnace", crystalFurnaceTier, crystalFurnaceSpeed, crystalFurnaceGeneration);
        applyFurnaceGroup(furnaces, "obsidian_furnace", obsidianFurnaceTier, obsidianFurnaceSpeed, obsidianFurnaceGeneration);
        applyFurnaceGroup(furnaces, "netherite_furnace", netheriteFurnaceTier, netheriteFurnaceSpeed, netheriteFurnaceGeneration);

        JsonObject rainbow = obj(furnaces, "rainbow_furnace");
        applyInt(rainbow, "speed", millionFurnaceSpeed);
        applyInt(rainbow, "generation", millionFurnaceGeneration);
        applyInt(rainbow, "rainbow_generation", millionFurnacePowerToGenerate);

        JsonObject million = obj(furnaces, "million_furnace");
        applyInt(million, "tier", millionFurnaceTier);

        applyInt(furnaces, "recipeMaxXPLevel", recipeMaxXPLevel);

        JsonObject modded = obj(root, CATEGORY_MODDED_FURNACE);
        applyModded(modded, "allthemodium_furnace", allthemodiumFurnaceSpeed, allthemodiumFurnaceSmeltMult,
                allthemodiumGeneration, allthemodiumFurnaceTier);
        applyModded(modded, "vibranium_furnace", vibraniumFurnaceSpeed, vibraniumFurnaceSmeltMult,
                vibraniumGeneration, vibraniumFurnaceTier);
        applyModded(modded, "unobtainium_furnace", unobtainiumFurnaceSpeed, unobtainiumFurnaceSmeltMult,
                unobtainiumGeneration, unobtainiumFurnaceTier);
    }

    private static void applyFurnaceGroup(JsonObject furnaces, String name, IntValue tier, IntValue speed, IntValue generation) {
        JsonObject o = obj(furnaces, name);
        applyInt(o, "tier", tier);
        applyInt(o, "speed", speed);
        applyInt(o, "generation", generation);
    }

    private static void applyModded(JsonObject modded, String name, IntValue speed, IntValue mult, IntValue gen, IntValue tier) {
        JsonObject o = obj(modded, name);
        applyInt(o, "speed", speed);
        applyInt(o, "mult", mult);
        applyInt(o, "generation", gen);
        applyInt(o, "tier", tier);
    }

    private static void saveDefaults(Path path) throws IOException {
        JsonObject root = new JsonObject();

        JsonObject general = new JsonObject();
        general.addProperty("show_errors", showErrors.get());
        root.add(CATEGORY_GENERAL, general);

        JsonObject misc = new JsonObject();
        misc.addProperty("lightupdates", disableLightupdates.get());
        root.add(CATEGORY_MISC, misc);

        JsonObject furnaces = new JsonObject();
        JsonObject energy = new JsonObject();
        energy.addProperty("tier_0", furnaceEnergyCapacityTier0.get());
        energy.addProperty("tier_1", furnaceEnergyCapacityTier1.get());
        energy.addProperty("tier_2", furnaceEnergyCapacityTier2.get());
        furnaces.add("energy", energy);

        putFurnaceGroup(furnaces, "iron_furnace", ironFurnaceTier, ironFurnaceSpeed, ironFurnaceGeneration);
        putFurnaceGroup(furnaces, "copper_furnace", copperFurnaceTier, copperFurnaceSpeed, copperFurnaceGeneration);
        putFurnaceGroup(furnaces, "gold_furnace", goldFurnaceTier, goldFurnaceSpeed, goldFurnaceGeneration);
        putFurnaceGroup(furnaces, "diamond_furnace", diamondFurnaceTier, diamondFurnaceSpeed, diamondFurnaceGeneration);
        putFurnaceGroup(furnaces, "emerald_furnace", emeraldFurnaceTier, emeraldFurnaceSpeed, emeraldFurnaceGeneration);
        putFurnaceGroup(furnaces, "silver_furnace", silverFurnaceTier, silverFurnaceSpeed, silverFurnaceGeneration);
        putFurnaceGroup(furnaces, "crystal_furnace", crystalFurnaceTier, crystalFurnaceSpeed, crystalFurnaceGeneration);
        putFurnaceGroup(furnaces, "obsidian_furnace", obsidianFurnaceTier, obsidianFurnaceSpeed, obsidianFurnaceGeneration);
        putFurnaceGroup(furnaces, "netherite_furnace", netheriteFurnaceTier, netheriteFurnaceSpeed, netheriteFurnaceGeneration);

        JsonObject rainbow = new JsonObject();
        rainbow.addProperty("speed", millionFurnaceSpeed.get());
        rainbow.addProperty("generation", millionFurnaceGeneration.get());
        rainbow.addProperty("rainbow_generation", millionFurnacePowerToGenerate.get());
        furnaces.add("rainbow_furnace", rainbow);

        JsonObject million = new JsonObject();
        million.addProperty("tier", millionFurnaceTier.get());
        furnaces.add("million_furnace", million);

        furnaces.addProperty("recipeMaxXPLevel", recipeMaxXPLevel.get());

        JsonObject modded = new JsonObject();
        putModded(modded, "allthemodium_furnace", allthemodiumFurnaceSpeed, allthemodiumFurnaceSmeltMult,
                allthemodiumGeneration, allthemodiumFurnaceTier);
        putModded(modded, "vibranium_furnace", vibraniumFurnaceSpeed, vibraniumFurnaceSmeltMult,
                vibraniumGeneration, vibraniumFurnaceTier);
        putModded(modded, "unobtainium_furnace", unobtainiumFurnaceSpeed, unobtainiumFurnaceSmeltMult,
                unobtainiumGeneration, unobtainiumFurnaceTier);

        root.add(CATEGORY_FURNACE, furnaces);
        root.add(CATEGORY_MODDED_FURNACE, modded);

        try (BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            w.write(new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(root));
            w.write('\n');
        }
    }

    private static void putFurnaceGroup(JsonObject furnaces, String name, IntValue tier, IntValue speed, IntValue generation) {
        JsonObject o = new JsonObject();
        o.addProperty("tier", tier.get());
        o.addProperty("speed", speed.get());
        o.addProperty("generation", generation.get());
        furnaces.add(name, o);
    }

    private static void putModded(JsonObject modded, String name, IntValue speed, IntValue mult, IntValue gen, IntValue tier) {
        JsonObject o = new JsonObject();
        o.addProperty("speed", speed.get());
        o.addProperty("mult", mult.get());
        o.addProperty("generation", gen.get());
        o.addProperty("tier", tier.get());
        modded.add(name, o);
    }

    private static JsonObject obj(@Nullable JsonObject parent, String key) {
        if (parent == null || !parent.has(key)) {
            return new JsonObject();
        }
        JsonElement el = parent.get(key);
        return el.isJsonObject() ? el.getAsJsonObject() : new JsonObject();
    }

    private static void applyInt(@Nullable JsonObject parent, String key, IntValue target) {
        if (parent == null || !parent.has(key)) {
            return;
        }
        try {
            target.set(parent.get(key).getAsInt());
        } catch (Exception ignored) {
        }
    }

    private static void applyBool(@Nullable JsonObject parent, String key, BoolValue target) {
        if (parent == null || !parent.has(key)) {
            return;
        }
        try {
            JsonElement el = parent.get(key);
            if (el.isJsonPrimitive()) {
                target.set(el.getAsBoolean());
            }
        } catch (Exception ignored) {
        }
    }

    public static final class IntValue {
        private final int min;
        private final int max;
        private int value;

        public IntValue(int defaultValue, int min, int max) {
            this.min = min;
            this.max = max;
            this.value = Mth.clamp(defaultValue, min, max);
        }

        public int get() {
            return value;
        }

        public void set(int v) {
            this.value = Mth.clamp(v, min, max);
        }
    }

    public static final class BoolValue {
        private boolean value;

        public BoolValue(boolean defaultValue) {
            this.value = defaultValue;
        }

        public boolean get() {
            return value;
        }

        public void set(boolean v) {
            this.value = v;
        }
    }
}
