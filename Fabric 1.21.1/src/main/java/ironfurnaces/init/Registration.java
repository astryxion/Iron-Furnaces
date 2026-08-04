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

package ironfurnaces.init;

import com.mojang.serialization.Codec;
import ironfurnaces.IronFurnaces;
import ironfurnaces.blocks.BlockWirelessEnergyHeater;
import ironfurnaces.blocks.furnaces.*;
import ironfurnaces.blocks.furnaces.other.BlockAllthemodiumFurnace;
import ironfurnaces.blocks.furnaces.other.BlockUnobtainiumFurnace;
import ironfurnaces.blocks.furnaces.other.BlockVibraniumFurnace;
import ironfurnaces.capability.PlayerFurnacesListProvider;
import ironfurnaces.capability.PlayerShowConfigProvider;
import ironfurnaces.container.BlockWirelessEnergyHeaterContainer;
import ironfurnaces.container.furnaces.*;
import ironfurnaces.container.furnaces.other.BlockAllthemodiumFurnaceContainer;
import ironfurnaces.container.furnaces.other.BlockUnobtainiumFurnaceContainer;
import ironfurnaces.container.furnaces.other.BlockVibraniumFurnaceContainer;
import ironfurnaces.items.*;
import ironfurnaces.items.augments.*;
import ironfurnaces.items.upgrades.*;
import ironfurnaces.recipes.GeneratorRecipe;
import ironfurnaces.recipes.SimpleGeneratorRecipe;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import ironfurnaces.tileentity.furnaces.*;
import ironfurnaces.tileentity.furnaces.other.BlockAllthemodiumFurnaceTile;
import ironfurnaces.tileentity.furnaces.other.BlockUnobtainiumFurnaceTile;
import ironfurnaces.tileentity.furnaces.other.BlockVibraniumFurnaceTile;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Registry;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static ironfurnaces.IronFurnaces.MOD_ID;

public class Registration {

    public static final TagKey<Item> SILVER_INGOTS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/silver"));

    public static boolean isSilverContentAvailable() {
        Optional<HolderSet.Named<Item>> tag = BuiltInRegistries.ITEM.getTag(SILVER_INGOTS);
        return tag.isPresent() && tag.get().size() > 0;
    }

    public static boolean isAtmContentAvailable() {
        return FabricLoader.getInstance().isModLoaded("allthemodium");
    }

    public static final class Holder<T> implements Supplier<T> {
        private T value;

        public void set(T v) {
            this.value = v;
        }

        @Override
        public T get() {
            return Objects.requireNonNull(value, "registry object not initialized");
        }
    }

    private static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static final Codec<PlayerFurnacesListProvider> PLAYER_FURNACES_CODEC = BlockPos.CODEC.listOf().xmap(
            list -> {
                PlayerFurnacesListProvider p = new PlayerFurnacesListProvider();
                p.furnacesList.listFurances.clear();
                p.furnacesList.listFurances.addAll(list);
                return p;
            },
            p -> List.copyOf(p.furnacesList.listFurances)
    );

    public static final Codec<PlayerShowConfigProvider> PLAYER_SHOW_CODEC = Codec.INT.xmap(
            v -> {
                PlayerShowConfigProvider p = new PlayerShowConfigProvider();
                p.config = v;
                return p;
            },
            p -> p.config
    );

    public static final AttachmentType<PlayerFurnacesListProvider> PLAYER_FURNACES_LIST =
            AttachmentRegistry.createPersistent(rl("furnaces_list"), PLAYER_FURNACES_CODEC);

    public static final AttachmentType<PlayerShowConfigProvider> PLAYER_SHOW_CONFIG =
            AttachmentRegistry.createPersistent(rl("show_config"), PLAYER_SHOW_CODEC);

    public static final Holder<DataComponentType<Integer>> ENERGY = new Holder<>();
    public static final Holder<DataComponentType<CustomData>> FURNACE_SETTINGS = new Holder<>();

    public static final String GENERATOR_ID = "generator_blasting";

    public static final class RecipeTypes {
        public static mezz.jei.api.recipe.RecipeType<GeneratorRecipe> GENERATOR_BLASTING =
                mezz.jei.api.recipe.RecipeType.create(IronFurnaces.MOD_ID, "generator_blasting", GeneratorRecipe.class);
        public static mezz.jei.api.recipe.RecipeType<SimpleGeneratorRecipe> GENERATOR_SMOKING =
                mezz.jei.api.recipe.RecipeType.create(IronFurnaces.MOD_ID, "generator_smoking", SimpleGeneratorRecipe.class);
        public static mezz.jei.api.recipe.RecipeType<SimpleGeneratorRecipe> GENERATOR_REGULAR =
                mezz.jei.api.recipe.RecipeType.create(IronFurnaces.MOD_ID, "generator_regular", SimpleGeneratorRecipe.class);
    }

    public static final Holder<RecipeType<GeneratorRecipe>> GENERATOR_RECIPE_TYPE = new Holder<>();
    public static final Holder<RecipeSerializer<GeneratorRecipe>> GENERATOR_RECIPE_SERIALIZER = new Holder<>();

    public static final Holder<BlockIronFurnace> IRON_FURNACE = new Holder<>();
    public static final Holder<ItemFurnace> IRON_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockIronFurnaceTile>> IRON_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockIronFurnaceContainer>> IRON_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<BlockGoldFurnace> GOLD_FURNACE = new Holder<>();
    public static final Holder<ItemFurnace> GOLD_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockGoldFurnaceTile>> GOLD_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockGoldFurnaceContainer>> GOLD_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<BlockDiamondFurnace> DIAMOND_FURNACE = new Holder<>();
    public static final Holder<ItemFurnace> DIAMOND_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockDiamondFurnaceTile>> DIAMOND_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockDiamondFurnaceContainer>> DIAMOND_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<BlockEmeraldFurnace> EMERALD_FURNACE = new Holder<>();
    public static final Holder<ItemFurnace> EMERALD_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockEmeraldFurnaceTile>> EMERALD_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockEmeraldFurnaceContainer>> EMERALD_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<BlockObsidianFurnace> OBSIDIAN_FURNACE = new Holder<>();
    public static final Holder<ItemFurnace> OBSIDIAN_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockObsidianFurnaceTile>> OBSIDIAN_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockObsidianFurnaceContainer>> OBSIDIAN_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<BlockCrystalFurnace> CRYSTAL_FURNACE = new Holder<>();
    public static final Holder<ItemFurnace> CRYSTAL_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockCrystalFurnaceTile>> CRYSTAL_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockCrystalFurnaceContainer>> CRYSTAL_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<BlockNetheriteFurnace> NETHERITE_FURNACE = new Holder<>();
    public static final Holder<ItemFurnace> NETHERITE_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockNetheriteFurnaceTile>> NETHERITE_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockNetheriteFurnaceContainer>> NETHERITE_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<BlockCopperFurnace> COPPER_FURNACE = new Holder<>();
    public static final Holder<ItemFurnace> COPPER_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockCopperFurnaceTile>> COPPER_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockCopperFurnaceContainer>> COPPER_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<BlockSilverFurnace> SILVER_FURNACE = new Holder<>();
    public static final Holder<ItemFurnace> SILVER_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockSilverFurnaceTile>> SILVER_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockSilverFurnaceContainer>> SILVER_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<ItemUpgradeIron> IRON_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeGold> GOLD_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeDiamond> DIAMOND_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeEmerald> EMERALD_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeObsidian> OBSIDIAN_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeCrystal> CRYSTAL_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeNetherite> NETHERITE_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeCopper> COPPER_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeSilver> SILVER_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeObsidian2> OBSIDIAN2_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeIron2> IRON2_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeGold2> GOLD2_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeSilver2> SILVER2_UPGRADE = new Holder<>();

    public static final Holder<BlockAllthemodiumFurnace> ALLTHEMODIUM_FURNACE = new Holder<>();
    public static final Holder<ItemAllthemodiumFurnace> ALLTHEMODIUM_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockAllthemodiumFurnaceTile>> ALLTHEMODIUM_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockAllthemodiumFurnaceContainer>> ALLTHEMODIUM_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<BlockVibraniumFurnace> VIBRANIUM_FURNACE = new Holder<>();
    public static final Holder<ItemVibraniumFurnace> VIBRANIUM_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockVibraniumFurnaceTile>> VIBRANIUM_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockVibraniumFurnaceContainer>> VIBRANIUM_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<BlockUnobtainiumFurnace> UNOBTAINIUM_FURNACE = new Holder<>();
    public static final Holder<ItemUnobtainiumFurnace> UNOBTAINIUM_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockUnobtainiumFurnaceTile>> UNOBTAINIUM_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockUnobtainiumFurnaceContainer>> UNOBTAINIUM_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<ItemUpgradeAllthemodium> ALLTHEMODIUM_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeVibranium> VIBRANIUM_UPGRADE = new Holder<>();
    public static final Holder<ItemUpgradeUnobtainium> UNOBTAINIUM_UPGRADE = new Holder<>();

    public static final Holder<BlockWirelessEnergyHeater> HEATER = new Holder<>();
    public static final Holder<BlockItemHeater> HEATER_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockWirelessEnergyHeaterTile>> HEATER_TILE = new Holder<>();
    public static final Holder<MenuType<BlockWirelessEnergyHeaterContainer>> HEATER_CONTAINER = new Holder<>();

    public static final Holder<ItemHeater> ITEM_HEATER = new Holder<>();
    public static final Holder<ItemAugmentBlasting> BLASTING_AUGMENT = new Holder<>();
    public static final Holder<ItemAugmentSmoking> SMOKING_AUGMENT = new Holder<>();
    public static final Holder<ItemAugmentFactory> FACTORY_AUGMENT = new Holder<>();
    public static final Holder<ItemAugmentGenerator> GENERATOR_AUGMENT = new Holder<>();
    public static final Holder<ItemAugmentSpeed> SPEED_AUGMENT = new Holder<>();
    public static final Holder<ItemAugmentFuel> FUEL_AUGMENT = new Holder<>();
    public static final Holder<ItemSpooky> ITEM_SPOOKY = new Holder<>();
    public static final Holder<ItemXmas> ITEM_XMAS = new Holder<>();
    public static final Holder<ItemFurnaceCopy> ITEM_COPY = new Holder<>();
    public static final Holder<Item> RAINBOW_CORE = new Holder<>();
    public static final Holder<Item> RAINBOW_PLATING = new Holder<>();
    public static final Holder<ItemRainbowCoal> RAINBOW_COAL = new Holder<>();

    public static final Holder<BlockMillionFurnace> MILLION_FURNACE = new Holder<>();
    public static final Holder<ItemMillionFurnace> MILLION_FURNACE_ITEM = new Holder<>();
    public static final Holder<BlockEntityType<BlockMillionFurnaceTile>> MILLION_FURNACE_TILE = new Holder<>();
    public static final Holder<MenuType<BlockMillionFurnaceContainer>> MILLION_FURNACE_CONTAINER = new Holder<>();

    public static final Holder<CreativeModeTab> tabIronFurnaces = new Holder<>();

    public static void bootstrap() {
        ENERGY.set(Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, rl("energy"),
                DataComponentType.<Integer>builder()
                        .persistent(Codec.INT)
                        .networkSynchronized(ByteBufCodecs.VAR_INT)
                        .build()));

        FURNACE_SETTINGS.set(Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, rl("furnace_settings"),
                DataComponentType.<CustomData>builder()
                        .persistent(CustomData.CODEC)
                        .networkSynchronized(CustomData.STREAM_CODEC)
                        .build()));

        GENERATOR_RECIPE_TYPE.set(Registry.register(BuiltInRegistries.RECIPE_TYPE, rl(GENERATOR_ID), new RecipeType<GeneratorRecipe>() {
            @Override
            public String toString() {
                return GENERATOR_ID;
            }
        }));

        GENERATOR_RECIPE_SERIALIZER.set(Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, rl(GENERATOR_ID), new GeneratorRecipe.Serializer()));

        IRON_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockIronFurnace.IRON_FURNACE), new BlockIronFurnace(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK))));
        IRON_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockIronFurnace.IRON_FURNACE), new ItemFurnace(IRON_FURNACE.get(), new Item.Properties())));
        IRON_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockIronFurnace.IRON_FURNACE),
                BlockEntityType.Builder.of(BlockIronFurnaceTile::new, IRON_FURNACE.get()).build(null)));
        IRON_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockIronFurnace.IRON_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockIronFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        GOLD_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockGoldFurnace.GOLD_FURNACE), new BlockGoldFurnace(Block.Properties.ofFullCopy(Blocks.GOLD_BLOCK))));
        GOLD_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockGoldFurnace.GOLD_FURNACE), new ItemFurnace(GOLD_FURNACE.get(), new Item.Properties())));
        GOLD_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockGoldFurnace.GOLD_FURNACE),
                BlockEntityType.Builder.of(BlockGoldFurnaceTile::new, GOLD_FURNACE.get()).build(null)));
        GOLD_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockGoldFurnace.GOLD_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockGoldFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        DIAMOND_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockDiamondFurnace.DIAMOND_FURNACE), new BlockDiamondFurnace(Block.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK))));
        DIAMOND_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockDiamondFurnace.DIAMOND_FURNACE), new ItemFurnace(DIAMOND_FURNACE.get(), new Item.Properties())));
        DIAMOND_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockDiamondFurnace.DIAMOND_FURNACE),
                BlockEntityType.Builder.of(BlockDiamondFurnaceTile::new, DIAMOND_FURNACE.get()).build(null)));
        DIAMOND_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockDiamondFurnace.DIAMOND_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockDiamondFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        EMERALD_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockEmeraldFurnace.EMERALD_FURNACE), new BlockEmeraldFurnace(Block.Properties.ofFullCopy(Blocks.EMERALD_BLOCK))));
        EMERALD_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockEmeraldFurnace.EMERALD_FURNACE), new ItemFurnace(EMERALD_FURNACE.get(), new Item.Properties())));
        EMERALD_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockEmeraldFurnace.EMERALD_FURNACE),
                BlockEntityType.Builder.of(BlockEmeraldFurnaceTile::new, EMERALD_FURNACE.get()).build(null)));
        EMERALD_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockEmeraldFurnace.EMERALD_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockEmeraldFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        OBSIDIAN_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockObsidianFurnace.OBSIDIAN_FURNACE), new BlockObsidianFurnace(Block.Properties.ofFullCopy(Blocks.OBSIDIAN))));
        OBSIDIAN_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockObsidianFurnace.OBSIDIAN_FURNACE), new ItemFurnace(OBSIDIAN_FURNACE.get(), new Item.Properties())));
        OBSIDIAN_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockObsidianFurnace.OBSIDIAN_FURNACE),
                BlockEntityType.Builder.of(BlockObsidianFurnaceTile::new, OBSIDIAN_FURNACE.get()).build(null)));
        OBSIDIAN_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockObsidianFurnace.OBSIDIAN_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockObsidianFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        CRYSTAL_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockCrystalFurnace.CRYSTAL_FURNACE), new BlockCrystalFurnace(Block.Properties.ofFullCopy(Blocks.GLASS))));
        CRYSTAL_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockCrystalFurnace.CRYSTAL_FURNACE), new ItemFurnace(CRYSTAL_FURNACE.get(), new Item.Properties())));
        CRYSTAL_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockCrystalFurnace.CRYSTAL_FURNACE),
                BlockEntityType.Builder.of(BlockCrystalFurnaceTile::new, CRYSTAL_FURNACE.get()).build(null)));
        CRYSTAL_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockCrystalFurnace.CRYSTAL_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockCrystalFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        NETHERITE_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockNetheriteFurnace.NETHERITE_FURNACE), new BlockNetheriteFurnace(Block.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK))));
        NETHERITE_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockNetheriteFurnace.NETHERITE_FURNACE), new ItemFurnace(NETHERITE_FURNACE.get(), new Item.Properties())));
        NETHERITE_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockNetheriteFurnace.NETHERITE_FURNACE),
                BlockEntityType.Builder.of(BlockNetheriteFurnaceTile::new, NETHERITE_FURNACE.get()).build(null)));
        NETHERITE_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockNetheriteFurnace.NETHERITE_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockNetheriteFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        COPPER_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockCopperFurnace.COPPER_FURNACE), new BlockCopperFurnace(Block.Properties.ofFullCopy(Blocks.COPPER_BLOCK))));
        COPPER_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockCopperFurnace.COPPER_FURNACE), new ItemFurnace(COPPER_FURNACE.get(), new Item.Properties())));
        COPPER_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockCopperFurnace.COPPER_FURNACE),
                BlockEntityType.Builder.of(BlockCopperFurnaceTile::new, COPPER_FURNACE.get()).build(null)));
        COPPER_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockCopperFurnace.COPPER_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockCopperFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        SILVER_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockSilverFurnace.SILVER_FURNACE), new BlockSilverFurnace(Block.Properties.ofFullCopy(Blocks.COPPER_BLOCK))));
        SILVER_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockSilverFurnace.SILVER_FURNACE), new ItemFurnace(SILVER_FURNACE.get(), new Item.Properties())));
        SILVER_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockSilverFurnace.SILVER_FURNACE),
                BlockEntityType.Builder.of(BlockSilverFurnaceTile::new, SILVER_FURNACE.get()).build(null)));
        SILVER_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockSilverFurnace.SILVER_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockSilverFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        IRON_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_iron"), new ItemUpgradeIron(new Item.Properties())));
        GOLD_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_gold"), new ItemUpgradeGold(new Item.Properties())));
        DIAMOND_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_diamond"), new ItemUpgradeDiamond(new Item.Properties())));
        EMERALD_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_emerald"), new ItemUpgradeEmerald(new Item.Properties())));
        OBSIDIAN_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_obsidian"), new ItemUpgradeObsidian(new Item.Properties())));
        CRYSTAL_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_crystal"), new ItemUpgradeCrystal(new Item.Properties())));
        NETHERITE_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_netherite"), new ItemUpgradeNetherite(new Item.Properties())));
        COPPER_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_copper"), new ItemUpgradeCopper(new Item.Properties())));
        SILVER_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_silver"), new ItemUpgradeSilver(new Item.Properties())));
        OBSIDIAN2_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_obsidian2"), new ItemUpgradeObsidian2(new Item.Properties())));
        IRON2_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_iron2"), new ItemUpgradeIron2(new Item.Properties())));
        GOLD2_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_gold2"), new ItemUpgradeGold2(new Item.Properties())));
        SILVER2_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_silver2"), new ItemUpgradeSilver2(new Item.Properties())));

        ALLTHEMODIUM_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE), new BlockAllthemodiumFurnace(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK))));
        ALLTHEMODIUM_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE), new ItemAllthemodiumFurnace(ALLTHEMODIUM_FURNACE.get(), new Item.Properties())));
        ALLTHEMODIUM_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE),
                BlockEntityType.Builder.of(BlockAllthemodiumFurnaceTile::new, ALLTHEMODIUM_FURNACE.get()).build(null)));
        ALLTHEMODIUM_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockAllthemodiumFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        VIBRANIUM_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockVibraniumFurnace.VIBRANIUM_FURNACE), new BlockVibraniumFurnace(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK))));
        VIBRANIUM_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockVibraniumFurnace.VIBRANIUM_FURNACE), new ItemVibraniumFurnace(VIBRANIUM_FURNACE.get(), new Item.Properties())));
        VIBRANIUM_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockVibraniumFurnace.VIBRANIUM_FURNACE),
                BlockEntityType.Builder.of(BlockVibraniumFurnaceTile::new, VIBRANIUM_FURNACE.get()).build(null)));
        VIBRANIUM_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockVibraniumFurnace.VIBRANIUM_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockVibraniumFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        UNOBTAINIUM_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE), new BlockUnobtainiumFurnace(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK))));
        UNOBTAINIUM_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE), new ItemUnobtainiumFurnace(UNOBTAINIUM_FURNACE.get(), new Item.Properties())));
        UNOBTAINIUM_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE),
                BlockEntityType.Builder.of(BlockUnobtainiumFurnaceTile::new, UNOBTAINIUM_FURNACE.get()).build(null)));
        UNOBTAINIUM_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockUnobtainiumFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        ALLTHEMODIUM_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_allthemodium"), new ItemUpgradeAllthemodium(new Item.Properties())));
        VIBRANIUM_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_vibranium"), new ItemUpgradeVibranium(new Item.Properties())));
        UNOBTAINIUM_UPGRADE.set(Registry.register(BuiltInRegistries.ITEM, rl("upgrade_unobtainium"), new ItemUpgradeUnobtainium(new Item.Properties())));

        HEATER.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockWirelessEnergyHeater.HEATER), new BlockWirelessEnergyHeater(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK))));
        HEATER_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockWirelessEnergyHeater.HEATER), new BlockItemHeater(HEATER.get(), new Item.Properties())));
        HEATER_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockWirelessEnergyHeater.HEATER),
                BlockEntityType.Builder.of(BlockWirelessEnergyHeaterTile::new, HEATER.get()).build(null)));
        HEATER_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockWirelessEnergyHeater.HEATER),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockWirelessEnergyHeaterContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        ITEM_HEATER.set(Registry.register(BuiltInRegistries.ITEM, rl("item_heater"), new ItemHeater(new Item.Properties().stacksTo(1))));
        BLASTING_AUGMENT.set(Registry.register(BuiltInRegistries.ITEM, rl("augment_blasting"), new ItemAugmentBlasting(new Item.Properties())));
        SMOKING_AUGMENT.set(Registry.register(BuiltInRegistries.ITEM, rl("augment_smoking"), new ItemAugmentSmoking(new Item.Properties())));
        FACTORY_AUGMENT.set(Registry.register(BuiltInRegistries.ITEM, rl("augment_factory"), new ItemAugmentFactory(new Item.Properties())));
        GENERATOR_AUGMENT.set(Registry.register(BuiltInRegistries.ITEM, rl("augment_generator"), new ItemAugmentGenerator(new Item.Properties())));
        SPEED_AUGMENT.set(Registry.register(BuiltInRegistries.ITEM, rl("augment_speed"), new ItemAugmentSpeed(new Item.Properties())));
        FUEL_AUGMENT.set(Registry.register(BuiltInRegistries.ITEM, rl("augment_fuel"), new ItemAugmentFuel(new Item.Properties())));
        ITEM_SPOOKY.set(Registry.register(BuiltInRegistries.ITEM, rl("item_spooky"), new ItemSpooky(new Item.Properties())));
        ITEM_XMAS.set(Registry.register(BuiltInRegistries.ITEM, rl("item_xmas"), new ItemXmas(new Item.Properties())));
        ITEM_COPY.set(Registry.register(BuiltInRegistries.ITEM, rl("item_copy"), new ItemFurnaceCopy(new Item.Properties().stacksTo(1))));
        RAINBOW_CORE.set(Registry.register(BuiltInRegistries.ITEM, rl("rainbow_core"), new Item(new Item.Properties())));
        RAINBOW_PLATING.set(Registry.register(BuiltInRegistries.ITEM, rl("rainbow_plating"), new Item(new Item.Properties())));
        RAINBOW_COAL.set(Registry.register(BuiltInRegistries.ITEM, rl("rainbow_coal"), new ItemRainbowCoal(new Item.Properties().stacksTo(1))));

        MILLION_FURNACE.set(Registry.register(BuiltInRegistries.BLOCK, rl(BlockMillionFurnace.MILLION_FURNACE), new BlockMillionFurnace(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK))));
        MILLION_FURNACE_ITEM.set(Registry.register(BuiltInRegistries.ITEM, rl(BlockMillionFurnace.MILLION_FURNACE), new ItemMillionFurnace(MILLION_FURNACE.get(), new Item.Properties())));
        MILLION_FURNACE_TILE.set(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, rl(BlockMillionFurnace.MILLION_FURNACE),
                BlockEntityType.Builder.of(BlockMillionFurnaceTile::new, MILLION_FURNACE.get()).build(null)));
        MILLION_FURNACE_CONTAINER.set(Registry.register(BuiltInRegistries.MENU, rl(BlockMillionFurnace.MILLION_FURNACE),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new BlockMillionFurnaceContainer(syncId, inv.player.level(), pos, inv, inv.player), BlockPos.STREAM_CODEC)));

        tabIronFurnaces.set(Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, rl("ironfurnaces_tab"),
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(IRON_FURNACE.get()))
                        .title(Component.translatable("itemGroup.ironfurnaces"))
                        .displayItems((parameters, output) -> {
                            boolean silverAvailable = isSilverContentAvailable();
                            boolean atmAvailable = isAtmContentAvailable();

                            output.accept(Registration.IRON_FURNACE_ITEM.get());
                            output.accept(Registration.GOLD_FURNACE_ITEM.get());
                            output.accept(Registration.DIAMOND_FURNACE_ITEM.get());
                            output.accept(Registration.EMERALD_FURNACE_ITEM.get());
                            output.accept(Registration.OBSIDIAN_FURNACE_ITEM.get());
                            output.accept(Registration.CRYSTAL_FURNACE_ITEM.get());
                            output.accept(Registration.NETHERITE_FURNACE_ITEM.get());
                            output.accept(Registration.COPPER_FURNACE_ITEM.get());
                            if (silverAvailable) {
                                output.accept(Registration.SILVER_FURNACE_ITEM.get());
                            }
                            if (atmAvailable) {
                                output.accept(Registration.ALLTHEMODIUM_FURNACE_ITEM.get());
                                output.accept(Registration.VIBRANIUM_FURNACE_ITEM.get());
                                output.accept(Registration.UNOBTAINIUM_FURNACE_ITEM.get());
                            }
                            output.accept(Registration.IRON_UPGRADE.get());
                            output.accept(Registration.GOLD_UPGRADE.get());
                            output.accept(Registration.DIAMOND_UPGRADE.get());
                            output.accept(Registration.EMERALD_UPGRADE.get());
                            output.accept(Registration.OBSIDIAN_UPGRADE.get());
                            output.accept(Registration.CRYSTAL_UPGRADE.get());
                            output.accept(Registration.NETHERITE_UPGRADE.get());
                            output.accept(Registration.COPPER_UPGRADE.get());
                            if (silverAvailable) {
                                output.accept(Registration.SILVER_UPGRADE.get());
                            }
                            output.accept(Registration.OBSIDIAN2_UPGRADE.get());
                            output.accept(Registration.IRON2_UPGRADE.get());
                            if (silverAvailable) {
                                output.accept(Registration.GOLD2_UPGRADE.get());
                                output.accept(Registration.SILVER2_UPGRADE.get());
                            }
                            if (atmAvailable) {
                                output.accept(Registration.ALLTHEMODIUM_UPGRADE.get());
                                output.accept(Registration.VIBRANIUM_UPGRADE.get());
                                output.accept(Registration.UNOBTAINIUM_UPGRADE.get());
                            }
                            output.accept(Registration.HEATER_ITEM.get());
                            output.accept(Registration.ITEM_HEATER.get());
                            output.accept(Registration.BLASTING_AUGMENT.get());
                            output.accept(Registration.SMOKING_AUGMENT.get());
                            output.accept(Registration.FACTORY_AUGMENT.get());
                            output.accept(Registration.GENERATOR_AUGMENT.get());
                            output.accept(Registration.SPEED_AUGMENT.get());
                            output.accept(Registration.FUEL_AUGMENT.get());
                            output.accept(Registration.ITEM_SPOOKY.get());
                            output.accept(Registration.ITEM_XMAS.get());
                            output.accept(Registration.ITEM_COPY.get());
                            output.accept(Registration.RAINBOW_CORE.get());
                            output.accept(Registration.RAINBOW_PLATING.get());
                            output.accept(Registration.MILLION_FURNACE_ITEM.get());
                            output.accept(Registration.RAINBOW_COAL.get());
                        }).build()));
    }
}
