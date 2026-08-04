package ironfurnaces.init;

import ironfurnaces.Config;
import ironfurnaces.blocks.BlockWirelessEnergyHeater;
import ironfurnaces.blocks.furnaces.BlockCopperFurnace;
import ironfurnaces.blocks.furnaces.BlockCrystalFurnace;
import ironfurnaces.blocks.furnaces.BlockDiamondFurnace;
import ironfurnaces.blocks.furnaces.BlockEmeraldFurnace;
import ironfurnaces.blocks.furnaces.BlockGoldFurnace;
import ironfurnaces.blocks.furnaces.BlockIronFurnace;
import ironfurnaces.blocks.furnaces.BlockMillionFurnace;
import ironfurnaces.blocks.furnaces.BlockNetheriteFurnace;
import ironfurnaces.blocks.furnaces.BlockObsidianFurnace;
import ironfurnaces.blocks.furnaces.BlockSilverFurnace;
import ironfurnaces.blocks.furnaces.other.BlockAllthemodiumFurnace;
import ironfurnaces.blocks.furnaces.other.BlockUnobtainiumFurnace;
import ironfurnaces.blocks.furnaces.other.BlockVibraniumFurnace;
import ironfurnaces.container.BlockWirelessEnergyHeaterContainer;
import ironfurnaces.blocks.furnaces.BlockItemHeater;
import ironfurnaces.container.furnaces.BlockCopperFurnaceContainer;
import ironfurnaces.container.furnaces.BlockCrystalFurnaceContainer;
import ironfurnaces.container.furnaces.BlockDiamondFurnaceContainer;
import ironfurnaces.container.furnaces.BlockEmeraldFurnaceContainer;
import ironfurnaces.container.furnaces.BlockGoldFurnaceContainer;
import ironfurnaces.container.furnaces.BlockIronFurnaceContainer;
import ironfurnaces.container.furnaces.BlockMillionFurnaceContainer;
import ironfurnaces.container.furnaces.BlockNetheriteFurnaceContainer;
import ironfurnaces.container.furnaces.BlockObsidianFurnaceContainer;
import ironfurnaces.container.furnaces.BlockSilverFurnaceContainer;
import ironfurnaces.container.furnaces.other.BlockAllthemodiumFurnaceContainer;
import ironfurnaces.container.furnaces.other.BlockUnobtainiumFurnaceContainer;
import ironfurnaces.container.furnaces.other.BlockVibraniumFurnaceContainer;
import ironfurnaces.items.ItemFurnace;
import ironfurnaces.items.ItemFurnaceCopy;
import ironfurnaces.items.ItemHeater;
import ironfurnaces.items.ItemMillionFurnace;
import ironfurnaces.items.ItemRainbowCoal;
import ironfurnaces.items.ItemSpooky;
import ironfurnaces.items.ItemXmas;
import ironfurnaces.items.augments.ItemAugmentBlasting;
import ironfurnaces.items.augments.ItemAugmentFactory;
import ironfurnaces.items.augments.ItemAugmentFuel;
import ironfurnaces.items.augments.ItemAugmentGenerator;
import ironfurnaces.items.augments.ItemAugmentSmoking;
import ironfurnaces.items.augments.ItemAugmentSpeed;
import ironfurnaces.items.upgrades.ItemUpgradeAllthemodium;
import ironfurnaces.items.upgrades.ItemUpgradeCopper;
import ironfurnaces.items.upgrades.ItemUpgradeCrystal;
import ironfurnaces.items.upgrades.ItemUpgradeDiamond;
import ironfurnaces.items.upgrades.ItemUpgradeEmerald;
import ironfurnaces.items.upgrades.ItemUpgradeGold;
import ironfurnaces.items.upgrades.ItemUpgradeGold2;
import ironfurnaces.items.upgrades.ItemUpgradeIron;
import ironfurnaces.items.upgrades.ItemUpgradeIron2;
import ironfurnaces.items.upgrades.ItemUpgradeNetherite;
import ironfurnaces.items.upgrades.ItemUpgradeObsidian;
import ironfurnaces.items.upgrades.ItemUpgradeObsidian2;
import ironfurnaces.items.upgrades.ItemUpgradeSilver;
import ironfurnaces.items.upgrades.ItemUpgradeSilver2;
import ironfurnaces.items.upgrades.ItemUpgradeUnobtainium;
import ironfurnaces.items.upgrades.ItemUpgradeVibranium;
import ironfurnaces.recipes.GeneratorRecipe;
import ironfurnaces.recipes.SimpleGeneratorRecipe;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import ironfurnaces.tileentity.furnaces.other.BlockAllthemodiumFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockCopperFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockCrystalFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockDiamondFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockEmeraldFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockGoldFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockMillionFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockNetheriteFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockObsidianFurnaceTile;
import ironfurnaces.tileentity.furnaces.BlockSilverFurnaceTile;
import ironfurnaces.tileentity.furnaces.other.BlockUnobtainiumFurnaceTile;
import ironfurnaces.tileentity.furnaces.other.BlockVibraniumFurnaceTile;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraftforge.fml.ModList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Optional;
import java.util.Set;

public class Registration {

  public static final class RegistryObject<T> {
    private T value;

    public T get() {
      return this.value;
    }

    private void bind(T v) {
      this.value = v;
    }
  }

  private static ResourceLocation id(String path) {
    return new ResourceLocation("ironfurnaces", path);
  }

  /** ATM content only appears when Allthemodium is loaded (Forge uses forge:mod_loaded). */
  private static final Set<ResourceLocation> ATM_CONTENT =
      Set.of(
          id("allthemodium_furnace"),
          id("vibranium_furnace"),
          id("unobtainium_furnace"),
          id("upgrade_allthemodium"),
          id("upgrade_vibranium"),
          id("upgrade_unobtainium"));

  /** Silver content only appears when a mod provides silver ingots (same idea as Forge tag_empty gating). */
  private static final Set<ResourceLocation> SILVER_CONTENT =
      Set.of(
          id("silver_furnace"),
          id("upgrade_silver"),
          id("upgrade_silver2"),
          id("upgrade_gold2"));

  public static final TagKey<Item> SILVER_INGOTS =
      TagKey.create(Registries.ITEM, new ResourceLocation("c", "silver_ingots"));

  public static boolean isSilverContentAvailable() {
    Optional<HolderSet.Named<Item>> tag = BuiltInRegistries.ITEM.getTag(SILVER_INGOTS);
    return tag.isPresent() && tag.get().size() > 0;
  }

  public static boolean isAtmContentAvailable() {
    return ModList.get().isLoaded("allthemodium");
  }

  private static <T extends Block> RegistryObject<T> bindBlock(String name, T block) {
    RegistryObject<T> ro = new RegistryObject<>();
    ro.bind(Registry.register(BuiltInRegistries.BLOCK, id(name), block));
    return ro;
  }

  private static <T extends Item> RegistryObject<T> bindItem(String name, T item) {
    RegistryObject<T> ro = new RegistryObject<>();
    ro.bind(Registry.register(BuiltInRegistries.ITEM, id(name), item));
    return ro;
  }

  private static <T extends BlockEntityType<?>> RegistryObject<T> bindBlockEntityType(String name, T type) {
    RegistryObject<T> ro = new RegistryObject<>();
    ro.bind(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id(name), type));
    return ro;
  }

  private static <T extends MenuType<?>> RegistryObject<T> bindMenu(String name, T menuType) {
    RegistryObject<T> ro = new RegistryObject<>();
    ro.bind(Registry.register(BuiltInRegistries.MENU, id(name), menuType));
    return ro;
  }

  private static <T extends RecipeSerializer<?>> RegistryObject<T> bindRecipeSerializer(String name, T serializer) {
    RegistryObject<T> ro = new RegistryObject<>();
    ro.bind(Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id(name), serializer));
    return ro;
  }

  private static <T extends RecipeType<?>> RegistryObject<T> bindRecipeType(String name, T recipeType) {
    RegistryObject<T> ro = new RegistryObject<>();
    ro.bind(Registry.register(BuiltInRegistries.RECIPE_TYPE, id(name), recipeType));
    return ro;
  }

  private static RegistryObject<CreativeModeTab> bindCreativeTab(String name, CreativeModeTab tab) {
    RegistryObject<CreativeModeTab> ro = new RegistryObject<>();
    ro.bind(Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id(name), tab));
    return ro;
  }

  private static <T extends AbstractContainerMenu> MenuType<T> extendedMenu(
      ExtendedScreenHandlerType.ExtendedFactory<T> factory) {
    return new ExtendedScreenHandlerType<>(factory);
  }

  public static final String GENERATOR_ID = "generator_blasting";

  public static void init() {}

  public static final RegistryObject<RecipeType<SimpleGeneratorRecipe>> SIMPLE_GENERATOR_REGULAR_TYPE =
      bindRecipeType(
          "generator_regular",
          new RecipeType<SimpleGeneratorRecipe>() {
            @Override
            public String toString() {
              return "ironfurnaces:generator_regular";
            }
          });

  public static final RegistryObject<RecipeType<SimpleGeneratorRecipe>> SIMPLE_GENERATOR_SMOKING_TYPE =
      bindRecipeType(
          "generator_smoking",
          new RecipeType<SimpleGeneratorRecipe>() {
            @Override
            public String toString() {
              return "ironfurnaces:generator_smoking";
            }
          });

  public static final RegistryObject<RecipeType<GeneratorRecipe>> GENERATOR_RECIPE_TYPE =
      bindRecipeType(
          "generator_blasting",
          new RecipeType<GeneratorRecipe>() {
            @Override
            public String toString() {
              return "generator_blasting";
            }
          });

  public static final RegistryObject<RecipeSerializer<GeneratorRecipe>> GENERATOR_RECIPE_SERIALIZER =
      bindRecipeSerializer("generator_blasting", new GeneratorRecipe.Serializer());

  public static final RegistryObject<BlockIronFurnace> IRON_FURNACE =
      bindBlock(
          "iron_furnace",
          new BlockIronFurnace(BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.IRON_BLOCK)));

  public static final RegistryObject<Item> IRON_FURNACE_ITEM =
      bindItem(
          "iron_furnace",
          new ItemFurnace(
              (Block) IRON_FURNACE.get(),
              new Item.Properties(),
              ((Integer) Config.ironFurnaceSpeed.get()).intValue()));

  public static final RegistryObject<BlockEntityType<BlockIronFurnaceTile>> IRON_FURNACE_TILE =
      bindBlockEntityType(
          "iron_furnace",
          BlockEntityType.Builder.of(BlockIronFurnaceTile::new, new Block[] {(Block) IRON_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockIronFurnaceContainer>> IRON_FURNACE_CONTAINER =
      bindMenu(
          "iron_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockIronFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<BlockGoldFurnace> GOLD_FURNACE =
      bindBlock(
          "gold_furnace",
          new BlockGoldFurnace(BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.GOLD_BLOCK)));

  public static final RegistryObject<Item> GOLD_FURNACE_ITEM =
      bindItem(
          "gold_furnace",
          new ItemFurnace(
              (Block) GOLD_FURNACE.get(),
              new Item.Properties(),
              ((Integer) Config.goldFurnaceSpeed.get()).intValue()));

  public static final RegistryObject<BlockEntityType<BlockGoldFurnaceTile>> GOLD_FURNACE_TILE =
      bindBlockEntityType(
          "gold_furnace",
          BlockEntityType.Builder.of(BlockGoldFurnaceTile::new, new Block[] {(Block) GOLD_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockGoldFurnaceContainer>> GOLD_FURNACE_CONTAINER =
      bindMenu(
          "gold_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockGoldFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<BlockDiamondFurnace> DIAMOND_FURNACE =
      bindBlock(
          "diamond_furnace",
          new BlockDiamondFurnace(BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.DIAMOND_BLOCK)));

  public static final RegistryObject<Item> DIAMOND_FURNACE_ITEM =
      bindItem(
          "diamond_furnace",
          new ItemFurnace(
              (Block) DIAMOND_FURNACE.get(),
              new Item.Properties(),
              ((Integer) Config.diamondFurnaceSpeed.get()).intValue()));

  public static final RegistryObject<BlockEntityType<BlockDiamondFurnaceTile>> DIAMOND_FURNACE_TILE =
      bindBlockEntityType(
          "diamond_furnace",
          BlockEntityType.Builder.of(BlockDiamondFurnaceTile::new, new Block[] {(Block) DIAMOND_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockDiamondFurnaceContainer>> DIAMOND_FURNACE_CONTAINER =
      bindMenu(
          "diamond_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockDiamondFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<BlockEmeraldFurnace> EMERALD_FURNACE =
      bindBlock(
          "emerald_furnace",
          new BlockEmeraldFurnace(BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.EMERALD_BLOCK)));

  public static final RegistryObject<Item> EMERALD_FURNACE_ITEM =
      bindItem(
          "emerald_furnace",
          new ItemFurnace(
              (Block) EMERALD_FURNACE.get(),
              new Item.Properties(),
              ((Integer) Config.emeraldFurnaceSpeed.get()).intValue()));

  public static final RegistryObject<BlockEntityType<BlockEmeraldFurnaceTile>> EMERALD_FURNACE_TILE =
      bindBlockEntityType(
          "emerald_furnace",
          BlockEntityType.Builder.of(BlockEmeraldFurnaceTile::new, new Block[] {(Block) EMERALD_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockEmeraldFurnaceContainer>> EMERALD_FURNACE_CONTAINER =
      bindMenu(
          "emerald_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockEmeraldFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<BlockObsidianFurnace> OBSIDIAN_FURNACE =
      bindBlock(
          "obsidian_furnace",
          new BlockObsidianFurnace(
              BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.OBSIDIAN).strength(40.0F, 6000.0F)));

  public static final RegistryObject<Item> OBSIDIAN_FURNACE_ITEM =
      bindItem(
          "obsidian_furnace",
          new ItemFurnace(
              (Block) OBSIDIAN_FURNACE.get(),
              new Item.Properties(),
              ((Integer) Config.obsidianFurnaceSpeed.get()).intValue()));

  public static final RegistryObject<BlockEntityType<BlockObsidianFurnaceTile>> OBSIDIAN_FURNACE_TILE =
      bindBlockEntityType(
          "obsidian_furnace",
          BlockEntityType.Builder.of(
                  BlockObsidianFurnaceTile::new, new Block[] {(Block) OBSIDIAN_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockObsidianFurnaceContainer>> OBSIDIAN_FURNACE_CONTAINER =
      bindMenu(
          "obsidian_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockObsidianFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<BlockCrystalFurnace> CRYSTAL_FURNACE =
      bindBlock(
          "crystal_furnace",
          new BlockCrystalFurnace(
              BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.PRISMARINE)
                  .noOcclusion()
                  .isValidSpawn(Registration::isntSolid)
                  .isSuffocating(Registration::isntSolid)
                  .isViewBlocking(Registration::isntSolid)));

  private static Boolean isntSolid(
      BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
    return Boolean.valueOf(false);
  }

  public static final RegistryObject<Item> CRYSTAL_FURNACE_ITEM =
      bindItem(
          "crystal_furnace",
          new ItemFurnace(
              (Block) CRYSTAL_FURNACE.get(),
              new Item.Properties(),
              ((Integer) Config.crystalFurnaceSpeed.get()).intValue()));

  public static final RegistryObject<BlockEntityType<BlockCrystalFurnaceTile>> CRYSTAL_FURNACE_TILE =
      bindBlockEntityType(
          "crystal_furnace",
          BlockEntityType.Builder.of(BlockCrystalFurnaceTile::new, new Block[] {(Block) CRYSTAL_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockCrystalFurnaceContainer>> CRYSTAL_FURNACE_CONTAINER =
      bindMenu(
          "crystal_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockCrystalFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  private static boolean isntSolid(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
    return false;
  }

  public static final RegistryObject<BlockNetheriteFurnace> NETHERITE_FURNACE =
      bindBlock(
          "netherite_furnace",
          new BlockNetheriteFurnace(
              BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.NETHERITE_BLOCK).strength(40.0F, 6000.0F)));

  public static final RegistryObject<Item> NETHERITE_FURNACE_ITEM =
      bindItem(
          "netherite_furnace",
          new ItemFurnace(
              (Block) NETHERITE_FURNACE.get(),
              new Item.Properties(),
              ((Integer) Config.netheriteFurnaceSpeed.get()).intValue()));

  public static final RegistryObject<BlockEntityType<BlockNetheriteFurnaceTile>> NETHERITE_FURNACE_TILE =
      bindBlockEntityType(
          "netherite_furnace",
          BlockEntityType.Builder.of(
                  BlockNetheriteFurnaceTile::new, new Block[] {(Block) NETHERITE_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockNetheriteFurnaceContainer>> NETHERITE_FURNACE_CONTAINER =
      bindMenu(
          "netherite_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockNetheriteFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<BlockCopperFurnace> COPPER_FURNACE =
      bindBlock(
          "copper_furnace",
          new BlockCopperFurnace(BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.GOLD_BLOCK)));

  public static final RegistryObject<Item> COPPER_FURNACE_ITEM =
      bindItem(
          "copper_furnace",
          new ItemFurnace(
              (Block) COPPER_FURNACE.get(),
              new Item.Properties(),
              ((Integer) Config.copperFurnaceSpeed.get()).intValue()));

  public static final RegistryObject<BlockEntityType<BlockCopperFurnaceTile>> COPPER_FURNACE_TILE =
      bindBlockEntityType(
          "copper_furnace",
          BlockEntityType.Builder.of(BlockCopperFurnaceTile::new, new Block[] {(Block) COPPER_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockCopperFurnaceContainer>> COPPER_FURNACE_CONTAINER =
      bindMenu(
          "copper_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockCopperFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<BlockSilverFurnace> SILVER_FURNACE =
      bindBlock(
          "silver_furnace",
          new BlockSilverFurnace(BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.IRON_BLOCK)));

  public static final RegistryObject<Item> SILVER_FURNACE_ITEM =
      bindItem(
          "silver_furnace",
          new ItemFurnace(
              (Block) SILVER_FURNACE.get(),
              new Item.Properties(),
              ((Integer) Config.silverFurnaceSpeed.get()).intValue()));

  public static final RegistryObject<BlockEntityType<BlockSilverFurnaceTile>> SILVER_FURNACE_TILE =
      bindBlockEntityType(
          "silver_furnace",
          BlockEntityType.Builder.of(BlockSilverFurnaceTile::new, new Block[] {(Block) SILVER_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockSilverFurnaceContainer>> SILVER_FURNACE_CONTAINER =
      bindMenu(
          "silver_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockSilverFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<ItemUpgradeIron> IRON_UPGRADE =
      bindItem("upgrade_iron", new ItemUpgradeIron(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeGold> GOLD_UPGRADE =
      bindItem("upgrade_gold", new ItemUpgradeGold(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeDiamond> DIAMOND_UPGRADE =
      bindItem("upgrade_diamond", new ItemUpgradeDiamond(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeEmerald> EMERALD_UPGRADE =
      bindItem("upgrade_emerald", new ItemUpgradeEmerald(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeObsidian> OBSIDIAN_UPGRADE =
      bindItem("upgrade_obsidian", new ItemUpgradeObsidian(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeCrystal> CRYSTAL_UPGRADE =
      bindItem("upgrade_crystal", new ItemUpgradeCrystal(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeNetherite> NETHERITE_UPGRADE =
      bindItem("upgrade_netherite", new ItemUpgradeNetherite(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeCopper> COPPER_UPGRADE =
      bindItem("upgrade_copper", new ItemUpgradeCopper(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeSilver> SILVER_UPGRADE =
      bindItem("upgrade_silver", new ItemUpgradeSilver(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeObsidian2> OBSIDIAN2_UPGRADE =
      bindItem("upgrade_obsidian2", new ItemUpgradeObsidian2(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeIron2> IRON2_UPGRADE =
      bindItem("upgrade_iron2", new ItemUpgradeIron2(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeGold2> GOLD2_UPGRADE =
      bindItem("upgrade_gold2", new ItemUpgradeGold2(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeSilver2> SILVER2_UPGRADE =
      bindItem("upgrade_silver2", new ItemUpgradeSilver2(new Item.Properties()));

  public static final RegistryObject<BlockAllthemodiumFurnace> ALLTHEMODIUM_FURNACE =
      bindBlock(
          "allthemodium_furnace",
          new BlockAllthemodiumFurnace(BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.GOLD_BLOCK)));

  public static final RegistryObject<Item> ALLTHEMODIUM_FURNACE_ITEM =
      bindItem(
          "allthemodium_furnace",
          new ItemFurnace(
              (Block) ALLTHEMODIUM_FURNACE.get(),
              new Item.Properties(),
              ((Integer) Config.allthemodiumFurnaceSpeed.get()).intValue()));

  public static final RegistryObject<BlockEntityType<BlockAllthemodiumFurnaceTile>> ALLTHEMODIUM_FURNACE_TILE =
      bindBlockEntityType(
          "allthemodium_furnace",
          BlockEntityType.Builder.of(
                  BlockAllthemodiumFurnaceTile::new, new Block[] {(Block) ALLTHEMODIUM_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockAllthemodiumFurnaceContainer>> ALLTHEMODIUM_FURNACE_CONTAINER =
      bindMenu(
          "allthemodium_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockAllthemodiumFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<BlockVibraniumFurnace> VIBRANIUM_FURNACE =
      bindBlock(
          "vibranium_furnace",
          new BlockVibraniumFurnace(BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.DIAMOND_BLOCK)));

  public static final RegistryObject<Item> VIBRANIUM_FURNACE_ITEM =
      bindItem(
          "vibranium_furnace",
          new ItemFurnace(
              (Block) VIBRANIUM_FURNACE.get(),
              new Item.Properties(),
              ((Integer) Config.vibraniumFurnaceSpeed.get()).intValue()));

  public static final RegistryObject<BlockEntityType<BlockVibraniumFurnaceTile>> VIBRANIUM_FURNACE_TILE =
      bindBlockEntityType(
          "vibranium_furnace",
          BlockEntityType.Builder.of(
                  BlockVibraniumFurnaceTile::new, new Block[] {(Block) VIBRANIUM_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockVibraniumFurnaceContainer>> VIBRANIUM_FURNACE_CONTAINER =
      bindMenu(
          "vibranium_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockVibraniumFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<BlockUnobtainiumFurnace> UNOBTAINIUM_FURNACE =
      bindBlock(
          "unobtainium_furnace",
          new BlockUnobtainiumFurnace(BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.NETHERITE_BLOCK)));

  public static final RegistryObject<Item> UNOBTAINIUM_FURNACE_ITEM =
      bindItem(
          "unobtainium_furnace",
          new ItemFurnace(
              (Block) UNOBTAINIUM_FURNACE.get(),
              new Item.Properties(),
              ((Integer) Config.unobtainiumFurnaceSpeed.get()).intValue()));

  public static final RegistryObject<BlockEntityType<BlockUnobtainiumFurnaceTile>> UNOBTAINIUM_FURNACE_TILE =
      bindBlockEntityType(
          "unobtainium_furnace",
          BlockEntityType.Builder.of(
                  BlockUnobtainiumFurnaceTile::new, new Block[] {(Block) UNOBTAINIUM_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockUnobtainiumFurnaceContainer>> UNOBTAINIUM_FURNACE_CONTAINER =
      bindMenu(
          "unobtainium_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockUnobtainiumFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<ItemUpgradeAllthemodium> ALLTHEMODIUM_UPGRADE =
      bindItem("upgrade_allthemodium", new ItemUpgradeAllthemodium(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeVibranium> VIBRANIUM_UPGRADE =
      bindItem("upgrade_vibranium", new ItemUpgradeVibranium(new Item.Properties()));

  public static final RegistryObject<ItemUpgradeUnobtainium> UNOBTAINIUM_UPGRADE =
      bindItem("upgrade_unobtainium", new ItemUpgradeUnobtainium(new Item.Properties()));

  public static final RegistryObject<BlockWirelessEnergyHeater> HEATER =
      bindBlock(
          "heater",
          new BlockWirelessEnergyHeater(BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.IRON_BLOCK)));

  public static final RegistryObject<Item> HEATER_ITEM =
      bindItem("heater", new BlockItemHeater((Block) HEATER.get(), new Item.Properties()));

  public static final RegistryObject<BlockEntityType<BlockWirelessEnergyHeaterTile>> HEATER_TILE =
      bindBlockEntityType(
          "heater",
          BlockEntityType.Builder.of(BlockWirelessEnergyHeaterTile::new, new Block[] {(Block) HEATER.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockWirelessEnergyHeaterContainer>> HEATER_CONTAINER =
      bindMenu(
          "heater",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockWirelessEnergyHeaterContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<ItemHeater> ITEM_HEATER =
      bindItem("item_heater", new ItemHeater((new Item.Properties()).stacksTo(1)));

  public static final RegistryObject<ItemAugmentBlasting> BLASTING_AUGMENT =
      bindItem("augment_blasting", new ItemAugmentBlasting(new Item.Properties()));

  public static final RegistryObject<ItemAugmentSmoking> SMOKING_AUGMENT =
      bindItem("augment_smoking", new ItemAugmentSmoking(new Item.Properties()));

  public static final RegistryObject<ItemAugmentFactory> FACTORY_AUGMENT =
      bindItem("augment_factory", new ItemAugmentFactory(new Item.Properties()));

  public static final RegistryObject<ItemAugmentGenerator> GENERATOR_AUGMENT =
      bindItem("augment_generator", new ItemAugmentGenerator(new Item.Properties()));

  public static final RegistryObject<ItemAugmentSpeed> SPEED_AUGMENT =
      bindItem("augment_speed", new ItemAugmentSpeed(new Item.Properties()));

  public static final RegistryObject<ItemAugmentFuel> FUEL_AUGMENT =
      bindItem("augment_fuel", new ItemAugmentFuel(new Item.Properties()));

  public static final RegistryObject<ItemSpooky> ITEM_SPOOKY =
      bindItem("item_spooky", new ItemSpooky(new Item.Properties()));

  public static final RegistryObject<ItemXmas> ITEM_XMAS =
      bindItem("item_xmas", new ItemXmas(new Item.Properties()));

  public static final RegistryObject<ItemFurnaceCopy> ITEM_COPY =
      bindItem("item_copy", new ItemFurnaceCopy((new Item.Properties()).stacksTo(1)));

  public static final RegistryObject<Item> RAINBOW_CORE =
      bindItem("rainbow_core", new Item(new Item.Properties()));

  public static final RegistryObject<Item> RAINBOW_PLATING =
      bindItem("rainbow_plating", new Item(new Item.Properties()));

  public static final RegistryObject<ItemRainbowCoal> RAINBOW_COAL =
      bindItem("rainbow_coal", new ItemRainbowCoal(new Item.Properties()));

  public static final RegistryObject<BlockMillionFurnace> MILLION_FURNACE =
      bindBlock(
          "million_furnace",
          new BlockMillionFurnace(BlockBehaviour.Properties.copy((BlockBehaviour) Blocks.IRON_BLOCK)));

  public static final RegistryObject<Item> MILLION_FURNACE_ITEM =
      bindItem(
          "million_furnace",
          new ItemMillionFurnace((Block) MILLION_FURNACE.get(), new Item.Properties()));

  public static final RegistryObject<BlockEntityType<BlockMillionFurnaceTile>> MILLION_FURNACE_TILE =
      bindBlockEntityType(
          "million_furnace",
          BlockEntityType.Builder.of(BlockMillionFurnaceTile::new, new Block[] {(Block) MILLION_FURNACE.get()})
              .build(null));

  public static final RegistryObject<MenuType<BlockMillionFurnaceContainer>> MILLION_FURNACE_CONTAINER =
      bindMenu(
          "million_furnace",
          extendedMenu(
              (syncId, inv, buf) ->
                  new BlockMillionFurnaceContainer(
                      syncId,
                      inv.player.level(),
                      buf.readBlockPos(),
                      inv,
                      inv.player)));

  public static final RegistryObject<CreativeModeTab> tabIronFurnaces =
      bindCreativeTab(
          "ironfurnaces_tab",
          CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
              .title(Component.translatable("itemGroup.ironfurnaces"))
              .icon(() -> new ItemStack((ItemLike) IRON_FURNACE_ITEM.get()))
              .displayItems(
                  (params, output) -> {
                    boolean silverAvailable = isSilverContentAvailable();
                    boolean atmAvailable = isAtmContentAvailable();
                    for (Item item : BuiltInRegistries.ITEM) {
                      ResourceLocation rl = BuiltInRegistries.ITEM.getKey(item);
                      if (rl != null
                          && "ironfurnaces".equals(rl.getNamespace())
                          && (atmAvailable || !ATM_CONTENT.contains(rl))
                          && (silverAvailable || !SILVER_CONTENT.contains(rl))) {
                        output.accept(new ItemStack((ItemLike) item));
                      }
                    }
                  })
              .build());
}
