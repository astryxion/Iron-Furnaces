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

import com.mojang.serialization.Codec;
import ironfurnaces.Config;
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
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import ironfurnaces.tileentity.furnaces.*;
import ironfurnaces.tileentity.furnaces.other.BlockAllthemodiumFurnaceTile;
import ironfurnaces.tileentity.furnaces.other.BlockUnobtainiumFurnaceTile;
import ironfurnaces.tileentity.furnaces.other.BlockVibraniumFurnaceTile;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static ironfurnaces.IronFurnaces.MOD_ID;

public class Registration {

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    private static ResourceKey<Block> blockKey(String path) {
        return ResourceKey.create(Registries.BLOCK, id(path));
    }

    private static ResourceKey<Item> itemKey(String path) {
        return ResourceKey.create(Registries.ITEM, id(path));
    }

    private static Block.Properties furnaceBlockProperties(Block referenceBlock, ResourceKey<Block> blockKey) {
        return Block.Properties.ofFullCopy(referenceBlock)
                .setId(blockKey)
                .lightLevel(state -> {
                    if (Config.disableLightupdates.get()) {
                        return 0;
                    }
                    return state.getValue(BlockStateProperties.LIT) ? 14 : 0;
                });
    }

    private static boolean registered;

    public static final String GENERATOR_ID = "generator_blasting";

    public static AttachmentType<PlayerFurnacesListProvider> PLAYER_FURNACES_LIST;
    public static AttachmentType<PlayerShowConfigProvider> PLAYER_SHOW_CONFIG;

    public static DataComponentType<Integer> ENERGY;
    public static DataComponentType<CustomData> FURNACE_SETTINGS;

    public static RecipeType<GeneratorRecipe> GENERATOR_RECIPE_TYPE;
    public static RecipeSerializer<GeneratorRecipe> GENERATOR_RECIPE_SERIALIZER;

    public static BlockIronFurnace IRON_FURNACE;
    public static ItemFurnace IRON_FURNACE_ITEM;
    public static BlockEntityType<BlockIronFurnaceTile> IRON_FURNACE_TILE;
    public static MenuType<BlockIronFurnaceContainer> IRON_FURNACE_CONTAINER;

    public static BlockGoldFurnace GOLD_FURNACE;
    public static ItemFurnace GOLD_FURNACE_ITEM;
    public static BlockEntityType<BlockGoldFurnaceTile> GOLD_FURNACE_TILE;
    public static MenuType<BlockGoldFurnaceContainer> GOLD_FURNACE_CONTAINER;

    public static BlockDiamondFurnace DIAMOND_FURNACE;
    public static ItemFurnace DIAMOND_FURNACE_ITEM;
    public static BlockEntityType<BlockDiamondFurnaceTile> DIAMOND_FURNACE_TILE;
    public static MenuType<BlockDiamondFurnaceContainer> DIAMOND_FURNACE_CONTAINER;

    public static BlockEmeraldFurnace EMERALD_FURNACE;
    public static ItemFurnace EMERALD_FURNACE_ITEM;
    public static BlockEntityType<BlockEmeraldFurnaceTile> EMERALD_FURNACE_TILE;
    public static MenuType<BlockEmeraldFurnaceContainer> EMERALD_FURNACE_CONTAINER;

    public static BlockObsidianFurnace OBSIDIAN_FURNACE;
    public static ItemFurnace OBSIDIAN_FURNACE_ITEM;
    public static BlockEntityType<BlockObsidianFurnaceTile> OBSIDIAN_FURNACE_TILE;
    public static MenuType<BlockObsidianFurnaceContainer> OBSIDIAN_FURNACE_CONTAINER;

    public static BlockCrystalFurnace CRYSTAL_FURNACE;
    public static ItemFurnace CRYSTAL_FURNACE_ITEM;
    public static BlockEntityType<BlockCrystalFurnaceTile> CRYSTAL_FURNACE_TILE;
    public static MenuType<BlockCrystalFurnaceContainer> CRYSTAL_FURNACE_CONTAINER;

    public static BlockNetheriteFurnace NETHERITE_FURNACE;
    public static ItemFurnace NETHERITE_FURNACE_ITEM;
    public static BlockEntityType<BlockNetheriteFurnaceTile> NETHERITE_FURNACE_TILE;
    public static MenuType<BlockNetheriteFurnaceContainer> NETHERITE_FURNACE_CONTAINER;

    public static BlockCopperFurnace COPPER_FURNACE;
    public static ItemFurnace COPPER_FURNACE_ITEM;
    public static BlockEntityType<BlockCopperFurnaceTile> COPPER_FURNACE_TILE;
    public static MenuType<BlockCopperFurnaceContainer> COPPER_FURNACE_CONTAINER;

    public static BlockSilverFurnace SILVER_FURNACE;
    public static ItemFurnace SILVER_FURNACE_ITEM;
    public static BlockEntityType<BlockSilverFurnaceTile> SILVER_FURNACE_TILE;
    public static MenuType<BlockSilverFurnaceContainer> SILVER_FURNACE_CONTAINER;

    public static ItemUpgradeIron IRON_UPGRADE;
    public static ItemUpgradeGold GOLD_UPGRADE;
    public static ItemUpgradeDiamond DIAMOND_UPGRADE;
    public static ItemUpgradeEmerald EMERALD_UPGRADE;
    public static ItemUpgradeObsidian OBSIDIAN_UPGRADE;
    public static ItemUpgradeCrystal CRYSTAL_UPGRADE;
    public static ItemUpgradeNetherite NETHERITE_UPGRADE;

    public static ItemUpgradeCopper COPPER_UPGRADE;
    public static ItemUpgradeSilver SILVER_UPGRADE;

    public static ItemUpgradeObsidian2 OBSIDIAN2_UPGRADE;
    public static ItemUpgradeIron2 IRON2_UPGRADE;
    public static ItemUpgradeGold2 GOLD2_UPGRADE;
    public static ItemUpgradeSilver2 SILVER2_UPGRADE;

    public static BlockAllthemodiumFurnace ALLTHEMODIUM_FURNACE;
    public static ItemAllthemodiumFurnace ALLTHEMODIUM_FURNACE_ITEM;
    public static BlockEntityType<BlockAllthemodiumFurnaceTile> ALLTHEMODIUM_FURNACE_TILE;
    public static MenuType<BlockAllthemodiumFurnaceContainer> ALLTHEMODIUM_FURNACE_CONTAINER;

    public static BlockVibraniumFurnace VIBRANIUM_FURNACE;
    public static ItemVibraniumFurnace VIBRANIUM_FURNACE_ITEM;
    public static BlockEntityType<BlockVibraniumFurnaceTile> VIBRANIUM_FURNACE_TILE;
    public static MenuType<BlockVibraniumFurnaceContainer> VIBRANIUM_FURNACE_CONTAINER;

    public static BlockUnobtainiumFurnace UNOBTAINIUM_FURNACE;
    public static ItemUnobtainiumFurnace UNOBTAINIUM_FURNACE_ITEM;
    public static BlockEntityType<BlockUnobtainiumFurnaceTile> UNOBTAINIUM_FURNACE_TILE;
    public static MenuType<BlockUnobtainiumFurnaceContainer> UNOBTAINIUM_FURNACE_CONTAINER;

    public static ItemUpgradeAllthemodium ALLTHEMODIUM_UPGRADE;
    public static ItemUpgradeVibranium VIBRANIUM_UPGRADE;
    public static ItemUpgradeUnobtainium UNOBTAINIUM_UPGRADE;

    public static BlockWirelessEnergyHeater HEATER;
    public static BlockItemHeater HEATER_ITEM;
    public static BlockEntityType<BlockWirelessEnergyHeaterTile> HEATER_TILE;
    public static MenuType<BlockWirelessEnergyHeaterContainer> HEATER_CONTAINER;

    public static ItemHeater ITEM_HEATER;

    public static ItemAugmentBlasting BLASTING_AUGMENT;
    public static ItemAugmentSmoking SMOKING_AUGMENT;
    public static ItemAugmentFactory FACTORY_AUGMENT;
    public static ItemAugmentGenerator GENERATOR_AUGMENT;
    public static ItemAugmentSpeed SPEED_AUGMENT;
    public static ItemAugmentFuel FUEL_AUGMENT;

    public static ItemSpooky ITEM_SPOOKY;
    public static ItemXmas ITEM_XMAS;

    public static ItemFurnaceCopy ITEM_COPY;
    public static Item RAINBOW_CORE;
    public static Item RAINBOW_PLATING;
    public static ItemRainbowCoal RAINBOW_COAL;

    public static BlockMillionFurnace MILLION_FURNACE;
    public static ItemMillionFurnace MILLION_FURNACE_ITEM;
    public static BlockEntityType<BlockMillionFurnaceTile> MILLION_FURNACE_TILE;
    public static MenuType<BlockMillionFurnaceContainer> MILLION_FURNACE_CONTAINER;

    public static CreativeModeTab tabIronFurnaces;

    public static void register() {
        synchronized (Registration.class) {
            if (registered) {
                return;
            }
            registered = true;

            PLAYER_FURNACES_LIST = AttachmentRegistry.createPersistent(id("furnaces_list"), PlayerFurnacesListProvider.CODEC);
            PLAYER_SHOW_CONFIG = AttachmentRegistry.createPersistent(id("show_config"), PlayerShowConfigProvider.CODEC);

            ENERGY = Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    id("energy"),
                    DataComponentType.<Integer>builder()
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.VAR_INT)
                            .build()
            );
            FURNACE_SETTINGS = Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    id("furnace_settings"),
                    DataComponentType.<CustomData>builder()
                            .persistent(CustomData.CODEC)
                            .networkSynchronized(CustomData.STREAM_CODEC)
                            .build()
            );

            GENERATOR_RECIPE_TYPE = Registry.register(
                    BuiltInRegistries.RECIPE_TYPE,
                    id(GENERATOR_ID),
                    new RecipeType<GeneratorRecipe>() {
                        @Override
                        public String toString() {
                            return GENERATOR_ID;
                        }
                    }
            );
            GENERATOR_RECIPE_SERIALIZER = Registry.register(
                    BuiltInRegistries.RECIPE_SERIALIZER,
                    id(GENERATOR_ID),
                    GeneratorRecipe.SERIALIZER
            );

            IRON_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockIronFurnace.IRON_FURNACE),
                    new BlockIronFurnace(furnaceBlockProperties(Blocks.IRON_BLOCK, blockKey(BlockIronFurnace.IRON_FURNACE)))
            );
            IRON_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockIronFurnace.IRON_FURNACE),
                    new ItemFurnace(IRON_FURNACE, new Item.Properties().setId(itemKey(BlockIronFurnace.IRON_FURNACE)))
            );
            IRON_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockIronFurnace.IRON_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockIronFurnaceTile::new, IRON_FURNACE).build()
            );
            IRON_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockIronFurnace.IRON_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockIronFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            GOLD_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockGoldFurnace.GOLD_FURNACE),
                    new BlockGoldFurnace(furnaceBlockProperties(Blocks.GOLD_BLOCK, blockKey(BlockGoldFurnace.GOLD_FURNACE)))
            );
            GOLD_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockGoldFurnace.GOLD_FURNACE),
                    new ItemFurnace(GOLD_FURNACE, new Item.Properties().setId(itemKey(BlockGoldFurnace.GOLD_FURNACE)))
            );
            GOLD_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockGoldFurnace.GOLD_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockGoldFurnaceTile::new, GOLD_FURNACE).build()
            );
            GOLD_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockGoldFurnace.GOLD_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockGoldFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            DIAMOND_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockDiamondFurnace.DIAMOND_FURNACE),
                    new BlockDiamondFurnace(furnaceBlockProperties(Blocks.DIAMOND_BLOCK, blockKey(BlockDiamondFurnace.DIAMOND_FURNACE)))
            );
            DIAMOND_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockDiamondFurnace.DIAMOND_FURNACE),
                    new ItemFurnace(DIAMOND_FURNACE, new Item.Properties().setId(itemKey(BlockDiamondFurnace.DIAMOND_FURNACE)))
            );
            DIAMOND_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockDiamondFurnace.DIAMOND_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockDiamondFurnaceTile::new, DIAMOND_FURNACE).build()
            );
            DIAMOND_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockDiamondFurnace.DIAMOND_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockDiamondFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            EMERALD_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockEmeraldFurnace.EMERALD_FURNACE),
                    new BlockEmeraldFurnace(furnaceBlockProperties(Blocks.EMERALD_BLOCK, blockKey(BlockEmeraldFurnace.EMERALD_FURNACE)))
            );
            EMERALD_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockEmeraldFurnace.EMERALD_FURNACE),
                    new ItemFurnace(EMERALD_FURNACE, new Item.Properties().setId(itemKey(BlockEmeraldFurnace.EMERALD_FURNACE)))
            );
            EMERALD_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockEmeraldFurnace.EMERALD_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockEmeraldFurnaceTile::new, EMERALD_FURNACE).build()
            );
            EMERALD_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockEmeraldFurnace.EMERALD_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockEmeraldFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            OBSIDIAN_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockObsidianFurnace.OBSIDIAN_FURNACE),
                    new BlockObsidianFurnace(furnaceBlockProperties(Blocks.OBSIDIAN, blockKey(BlockObsidianFurnace.OBSIDIAN_FURNACE)))
            );
            OBSIDIAN_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockObsidianFurnace.OBSIDIAN_FURNACE),
                    new ItemFurnace(OBSIDIAN_FURNACE, new Item.Properties().setId(itemKey(BlockObsidianFurnace.OBSIDIAN_FURNACE)))
            );
            OBSIDIAN_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockObsidianFurnace.OBSIDIAN_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockObsidianFurnaceTile::new, OBSIDIAN_FURNACE).build()
            );
            OBSIDIAN_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockObsidianFurnace.OBSIDIAN_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockObsidianFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            CRYSTAL_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockCrystalFurnace.CRYSTAL_FURNACE),
                    new BlockCrystalFurnace(furnaceBlockProperties(Blocks.GLASS, blockKey(BlockCrystalFurnace.CRYSTAL_FURNACE)))
            );
            CRYSTAL_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockCrystalFurnace.CRYSTAL_FURNACE),
                    new ItemFurnace(CRYSTAL_FURNACE, new Item.Properties().setId(itemKey(BlockCrystalFurnace.CRYSTAL_FURNACE)))
            );
            CRYSTAL_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockCrystalFurnace.CRYSTAL_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockCrystalFurnaceTile::new, CRYSTAL_FURNACE).build()
            );
            CRYSTAL_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockCrystalFurnace.CRYSTAL_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockCrystalFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            NETHERITE_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockNetheriteFurnace.NETHERITE_FURNACE),
                    new BlockNetheriteFurnace(furnaceBlockProperties(Blocks.NETHERITE_BLOCK, blockKey(BlockNetheriteFurnace.NETHERITE_FURNACE)))
            );
            NETHERITE_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockNetheriteFurnace.NETHERITE_FURNACE),
                    new ItemFurnace(NETHERITE_FURNACE, new Item.Properties().setId(itemKey(BlockNetheriteFurnace.NETHERITE_FURNACE)))
            );
            NETHERITE_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockNetheriteFurnace.NETHERITE_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockNetheriteFurnaceTile::new, NETHERITE_FURNACE).build()
            );
            NETHERITE_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockNetheriteFurnace.NETHERITE_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockNetheriteFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            COPPER_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockCopperFurnace.COPPER_FURNACE),
                    new BlockCopperFurnace(furnaceBlockProperties(Blocks.COPPER_BLOCK, blockKey(BlockCopperFurnace.COPPER_FURNACE)))
            );
            COPPER_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockCopperFurnace.COPPER_FURNACE),
                    new ItemFurnace(COPPER_FURNACE, new Item.Properties().setId(itemKey(BlockCopperFurnace.COPPER_FURNACE)))
            );
            COPPER_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockCopperFurnace.COPPER_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockCopperFurnaceTile::new, COPPER_FURNACE).build()
            );
            COPPER_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockCopperFurnace.COPPER_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockCopperFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            SILVER_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockSilverFurnace.SILVER_FURNACE),
                    new BlockSilverFurnace(furnaceBlockProperties(Blocks.COPPER_BLOCK, blockKey(BlockSilverFurnace.SILVER_FURNACE)))
            );
            SILVER_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockSilverFurnace.SILVER_FURNACE),
                    new ItemFurnace(SILVER_FURNACE, new Item.Properties().setId(itemKey(BlockSilverFurnace.SILVER_FURNACE)))
            );
            SILVER_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockSilverFurnace.SILVER_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockSilverFurnaceTile::new, SILVER_FURNACE).build()
            );
            SILVER_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockSilverFurnace.SILVER_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockSilverFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            IRON_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_iron"), new ItemUpgradeIron(new Item.Properties().setId(itemKey("upgrade_iron"))));
            GOLD_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_gold"), new ItemUpgradeGold(new Item.Properties().setId(itemKey("upgrade_gold"))));
            DIAMOND_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_diamond"), new ItemUpgradeDiamond(new Item.Properties().setId(itemKey("upgrade_diamond"))));
            EMERALD_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_emerald"), new ItemUpgradeEmerald(new Item.Properties().setId(itemKey("upgrade_emerald"))));
            OBSIDIAN_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_obsidian"), new ItemUpgradeObsidian(new Item.Properties().setId(itemKey("upgrade_obsidian"))));
            CRYSTAL_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_crystal"), new ItemUpgradeCrystal(new Item.Properties().setId(itemKey("upgrade_crystal"))));
            NETHERITE_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_netherite"), new ItemUpgradeNetherite(new Item.Properties().setId(itemKey("upgrade_netherite"))));

            COPPER_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_copper"), new ItemUpgradeCopper(new Item.Properties().setId(itemKey("upgrade_copper"))));
            SILVER_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_silver"), new ItemUpgradeSilver(new Item.Properties().setId(itemKey("upgrade_silver"))));

            OBSIDIAN2_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_obsidian2"), new ItemUpgradeObsidian2(new Item.Properties().setId(itemKey("upgrade_obsidian2"))));
            IRON2_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_iron2"), new ItemUpgradeIron2(new Item.Properties().setId(itemKey("upgrade_iron2"))));
            GOLD2_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_gold2"), new ItemUpgradeGold2(new Item.Properties().setId(itemKey("upgrade_gold2"))));
            SILVER2_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_silver2"), new ItemUpgradeSilver2(new Item.Properties().setId(itemKey("upgrade_silver2"))));

            ALLTHEMODIUM_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE),
                    new BlockAllthemodiumFurnace(furnaceBlockProperties(Blocks.IRON_BLOCK, blockKey(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE)))
            );
            ALLTHEMODIUM_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE),
                    new ItemAllthemodiumFurnace(ALLTHEMODIUM_FURNACE, new Item.Properties().setId(itemKey(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE)))
            );
            ALLTHEMODIUM_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockAllthemodiumFurnaceTile::new, ALLTHEMODIUM_FURNACE).build()
            );
            ALLTHEMODIUM_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockAllthemodiumFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            VIBRANIUM_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockVibraniumFurnace.VIBRANIUM_FURNACE),
                    new BlockVibraniumFurnace(furnaceBlockProperties(Blocks.IRON_BLOCK, blockKey(BlockVibraniumFurnace.VIBRANIUM_FURNACE)))
            );
            VIBRANIUM_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockVibraniumFurnace.VIBRANIUM_FURNACE),
                    new ItemVibraniumFurnace(VIBRANIUM_FURNACE, new Item.Properties().setId(itemKey(BlockVibraniumFurnace.VIBRANIUM_FURNACE)))
            );
            VIBRANIUM_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockVibraniumFurnace.VIBRANIUM_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockVibraniumFurnaceTile::new, VIBRANIUM_FURNACE).build()
            );
            VIBRANIUM_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockVibraniumFurnace.VIBRANIUM_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockVibraniumFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            UNOBTAINIUM_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE),
                    new BlockUnobtainiumFurnace(furnaceBlockProperties(Blocks.IRON_BLOCK, blockKey(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE)))
            );
            UNOBTAINIUM_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE),
                    new ItemUnobtainiumFurnace(UNOBTAINIUM_FURNACE, new Item.Properties().setId(itemKey(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE)))
            );
            UNOBTAINIUM_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockUnobtainiumFurnaceTile::new, UNOBTAINIUM_FURNACE).build()
            );
            UNOBTAINIUM_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockUnobtainiumFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            ALLTHEMODIUM_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_allthemodium"), new ItemUpgradeAllthemodium(new Item.Properties().setId(itemKey("upgrade_allthemodium"))));
            VIBRANIUM_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_vibranium"), new ItemUpgradeVibranium(new Item.Properties().setId(itemKey("upgrade_vibranium"))));
            UNOBTAINIUM_UPGRADE = Registry.register(BuiltInRegistries.ITEM, id("upgrade_unobtainium"), new ItemUpgradeUnobtainium(new Item.Properties().setId(itemKey("upgrade_unobtainium"))));

            HEATER = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockWirelessEnergyHeater.HEATER),
                    new BlockWirelessEnergyHeater(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK).setId(blockKey(BlockWirelessEnergyHeater.HEATER)))
            );
            HEATER_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockWirelessEnergyHeater.HEATER),
                    new BlockItemHeater(HEATER, new Item.Properties().setId(itemKey(BlockWirelessEnergyHeater.HEATER)))
            );
            HEATER_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockWirelessEnergyHeater.HEATER),
                    FabricBlockEntityTypeBuilder.create(BlockWirelessEnergyHeaterTile::new, HEATER).build()
            );
            HEATER_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockWirelessEnergyHeater.HEATER),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockWirelessEnergyHeaterContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            ITEM_HEATER = Registry.register(BuiltInRegistries.ITEM, id("item_heater"), new ItemHeater(new Item.Properties().setId(itemKey("item_heater")).stacksTo(1)));

            BLASTING_AUGMENT = Registry.register(BuiltInRegistries.ITEM, id("augment_blasting"), new ItemAugmentBlasting(new Item.Properties().setId(itemKey("augment_blasting"))));
            SMOKING_AUGMENT = Registry.register(BuiltInRegistries.ITEM, id("augment_smoking"), new ItemAugmentSmoking(new Item.Properties().setId(itemKey("augment_smoking"))));
            FACTORY_AUGMENT = Registry.register(BuiltInRegistries.ITEM, id("augment_factory"), new ItemAugmentFactory(new Item.Properties().setId(itemKey("augment_factory"))));
            GENERATOR_AUGMENT = Registry.register(BuiltInRegistries.ITEM, id("augment_generator"), new ItemAugmentGenerator(new Item.Properties().setId(itemKey("augment_generator"))));
            SPEED_AUGMENT = Registry.register(BuiltInRegistries.ITEM, id("augment_speed"), new ItemAugmentSpeed(new Item.Properties().setId(itemKey("augment_speed"))));
            FUEL_AUGMENT = Registry.register(BuiltInRegistries.ITEM, id("augment_fuel"), new ItemAugmentFuel(new Item.Properties().setId(itemKey("augment_fuel"))));

            ITEM_SPOOKY = Registry.register(BuiltInRegistries.ITEM, id("item_spooky"), new ItemSpooky(new Item.Properties().setId(itemKey("item_spooky"))));
            ITEM_XMAS = Registry.register(BuiltInRegistries.ITEM, id("item_xmas"), new ItemXmas(new Item.Properties().setId(itemKey("item_xmas"))));

            ITEM_COPY = Registry.register(BuiltInRegistries.ITEM, id("item_copy"), new ItemFurnaceCopy(new Item.Properties().setId(itemKey("item_copy")).stacksTo(1)));
            RAINBOW_CORE = Registry.register(BuiltInRegistries.ITEM, id("rainbow_core"), new Item(new Item.Properties().setId(itemKey("rainbow_core"))));
            RAINBOW_PLATING = Registry.register(BuiltInRegistries.ITEM, id("rainbow_plating"), new Item(new Item.Properties().setId(itemKey("rainbow_plating"))));
            RAINBOW_COAL = Registry.register(BuiltInRegistries.ITEM, id("rainbow_coal"), new ItemRainbowCoal(new Item.Properties().setId(itemKey("rainbow_coal")).stacksTo(1).durability(5120)));

            MILLION_FURNACE = Registry.register(
                    BuiltInRegistries.BLOCK,
                    id(BlockMillionFurnace.MILLION_FURNACE),
                    new BlockMillionFurnace(furnaceBlockProperties(Blocks.IRON_BLOCK, blockKey(BlockMillionFurnace.MILLION_FURNACE)))
            );
            MILLION_FURNACE_ITEM = Registry.register(
                    BuiltInRegistries.ITEM,
                    id(BlockMillionFurnace.MILLION_FURNACE),
                    new ItemMillionFurnace(MILLION_FURNACE, new Item.Properties().setId(itemKey(BlockMillionFurnace.MILLION_FURNACE)))
            );
            MILLION_FURNACE_TILE = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    id(BlockMillionFurnace.MILLION_FURNACE),
                    FabricBlockEntityTypeBuilder.create(BlockMillionFurnaceTile::new, MILLION_FURNACE).build()
            );
            MILLION_FURNACE_CONTAINER = Registry.register(
                    BuiltInRegistries.MENU,
                    id(BlockMillionFurnace.MILLION_FURNACE),
                    new MenuType<>(
                            (int syncId, Inventory inv) -> new BlockMillionFurnaceContainer(syncId, inv),
                            FeatureFlags.VANILLA_SET)
            );

            tabIronFurnaces = Registry.register(
                    BuiltInRegistries.CREATIVE_MODE_TAB,
                    id("ironfurnaces_tab"),
                    FabricCreativeModeTab.builder()
                            .icon(() -> IRON_FURNACE.asItem().getDefaultInstance())
                            .title(Component.translatable("itemGroup.ironfurnaces"))
                            .displayItems((parameters, output) -> {
                                output.accept(IRON_FURNACE_ITEM);
                                output.accept(GOLD_FURNACE_ITEM);
                                output.accept(DIAMOND_FURNACE_ITEM);
                                output.accept(EMERALD_FURNACE_ITEM);
                                output.accept(OBSIDIAN_FURNACE_ITEM);
                                output.accept(CRYSTAL_FURNACE_ITEM);
                                output.accept(NETHERITE_FURNACE_ITEM);
                                output.accept(COPPER_FURNACE_ITEM);
                                output.accept(SILVER_FURNACE_ITEM);

                                output.accept(IRON_UPGRADE);
                                output.accept(GOLD_UPGRADE);
                                output.accept(DIAMOND_UPGRADE);
                                output.accept(EMERALD_UPGRADE);
                                output.accept(OBSIDIAN_UPGRADE);
                                output.accept(CRYSTAL_UPGRADE);
                                output.accept(NETHERITE_UPGRADE);
                                output.accept(COPPER_UPGRADE);
                                output.accept(SILVER_UPGRADE);

                                output.accept(OBSIDIAN2_UPGRADE);
                                output.accept(IRON2_UPGRADE);
                                output.accept(GOLD2_UPGRADE);
                                output.accept(SILVER2_UPGRADE);
                                output.accept(HEATER_ITEM);
                                output.accept(ITEM_HEATER);
                                output.accept(BLASTING_AUGMENT);
                                output.accept(SMOKING_AUGMENT);
                                output.accept(FACTORY_AUGMENT);

                                output.accept(GENERATOR_AUGMENT);
                                output.accept(SPEED_AUGMENT);
                                output.accept(FUEL_AUGMENT);
                                output.accept(ITEM_SPOOKY);
                                output.accept(ITEM_XMAS);
                                output.accept(ITEM_COPY);
                                output.accept(RAINBOW_CORE);
                                output.accept(RAINBOW_PLATING);

                                output.accept(MILLION_FURNACE_ITEM);
                                output.accept(RAINBOW_COAL);
                            })
                            .build()
            );
        }
    }

    static {
        register();
    }
}
