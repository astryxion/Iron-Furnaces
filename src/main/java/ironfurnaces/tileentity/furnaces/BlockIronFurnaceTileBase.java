/*      */ package ironfurnaces.tileentity.furnaces;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import harmonised.pmmo.api.events.FurnaceBurnEvent;
/*      */ import harmonised.pmmo.events.impl.FurnaceHandler;
/*      */ import ironfurnaces.Config;
/*      */ import ironfurnaces.blocks.furnaces.BlockIronFurnaceBase;
/*      */ import ironfurnaces.blocks.furnaces.BlockMillionFurnace;
/*      */ import ironfurnaces.capability.CapabilityPlayerFurnacesList;
/*      */ import ironfurnaces.capability.IPlayerFurnacesList;
/*      */ import ironfurnaces.energy.FEnergyStorage;
/*      */ import ironfurnaces.init.ModSetup;
/*      */ import ironfurnaces.init.Registration;
/*      */ import ironfurnaces.recipes.GeneratorRecipe;
/*      */ import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
/*      */ import ironfurnaces.tileentity.TileEntityInventory;
/*      */ import ironfurnaces.util.DirectionUtil;
/*      */ import ironfurnaces.util.FurnaceSettings;
/*      */ import ironfurnaces.util.LRUCache;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Optional;
/*      */ import java.util.UUID;
/*      */ import org.jetbrains.annotations.Nullable;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.RegistryAccess;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.server.level.ServerPlayer;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.world.Container;
/*      */ import net.minecraft.world.Containers;
/*      */ import net.minecraft.world.SimpleContainer;
/*      */ import net.minecraft.world.WorldlyContainer;
/*      */ import net.minecraft.world.entity.ExperienceOrb;
/*      */ import net.minecraft.world.entity.player.StackedContents;
/*      */ import net.minecraft.world.inventory.RecipeHolder;
/*      */ import net.minecraft.world.inventory.StackedContentsCompatible;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.crafting.AbstractCookingRecipe;
/*      */ import net.minecraft.world.item.crafting.Recipe;
/*      */ import net.minecraft.world.item.crafting.RecipeType;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.entity.BlockEntity;
/*      */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraftforge.common.ForgeConfigSpec;
/*      */ import net.minecraftforge.common.ForgeHooks;
/*      */ import net.minecraftforge.common.capabilities.Capability;
/*      */ import net.minecraftforge.common.capabilities.CapabilityHooks;
/*      */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*      */ import net.minecraftforge.common.util.LazyOptional;
/*      */ import net.minecraftforge.common.world.LevelForgeHooks;
/*      */ import net.minecraftforge.energy.IEnergyStorage;
/*      */ import net.minecraftforge.fml.ModList;
/*      */ import net.minecraftforge.items.IItemHandler;
/*      */ import net.minecraftforge.items.ItemHandlerHelper;
/*      */ import net.minecraftforge.items.wrapper.SidedInvWrapper;
/*      */ import net.minecraftforge.registries.ForgeRegistries;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ 
/*      */ public abstract class BlockIronFurnaceTileBase
/*      */   extends TileEntityInventory implements RecipeHolder, StackedContentsCompatible {
/*      */   public static final int INPUT = 0;
/*      */   public static final int FUEL = 1;
/*      */   public static final int OUTPUT = 2;
/*      */   public static final int AUGMENT_RED = 3;
/*      */   public static final int AUGMENT_GREEN = 4;
/*      */   public static final int AUGMENT_BLUE = 5;
/*      */   public static final int GENERATOR_FUEL = 6;
/*   89 */   public static final int[] FACTORY_INPUT = new int[] { 7, 8, 9, 10, 11, 12 };
/*      */ 
/*      */   
/*   92 */   public final int[] provides = new int[(Direction.values()).length];
/*   93 */   protected final int[] lastProvides = new int[this.provides.length];
/*      */   public int jovial;
/*   95 */   public int[] currentAugment = new int[3];
/*   96 */   public int[] factoryCookTime = new int[6];
/*   97 */   public int[] factoryTotalCookTime = new int[6];
/*   98 */   public double[] usedRF = new double[6];
/*      */   
/*      */   public double generatorBurn;
/*      */   
/*      */   public int generatorRecentRecipeRF;
/*      */   
/*      */   public double gottenRF;
/*      */   
/*      */   public int furnaceBurnTime;
/*      */   public int cookTime;
/*      */   public int totalCookTime;
/*      */   public int recipesUsed;
/*      */   public long lastGameTickEnergyUpdated;
/*      */   public UUID owner;
/*      */   public boolean rainbowGenerating;
/*  113 */   public final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap();
/*      */   public RecipeType<? extends AbstractCookingRecipe> recipeType;
/*      */   public FurnaceSettings furnaceSettings;
/*  116 */   public LRUCache<Item, Optional<AbstractCookingRecipe>> cache = LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue());
/*  117 */   public LRUCache<Item, Optional<AbstractCookingRecipe>> blasting_cache = LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue());
/*  118 */   public LRUCache<Item, Optional<AbstractCookingRecipe>> smoking_cache = LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue());
/*  119 */   public LRUCache<Item, Optional<GeneratorRecipe>> generator_cache = LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue());
/*  120 */   public List<LRUCache<Item, Optional<AbstractCookingRecipe>>> factory_cache =
/*      */       Lists.newArrayList(
/*  121 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  122 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  123 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  124 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  125 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  126 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()));
/*  128 */   public List<LRUCache<Item, Optional<AbstractCookingRecipe>>> factory_blasting_cache =
/*      */       Lists.newArrayList(
/*  129 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  130 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  131 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  132 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  133 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  134 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()));
/*  136 */   public List<LRUCache<Item, Optional<AbstractCookingRecipe>>> factory_smoking_cache =
/*      */       Lists.newArrayList(
/*  137 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  138 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  139 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  140 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  141 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()),
/*  142 */           LRUCache.newInstance(((Integer)Config.cache_capacity.get()).intValue()));
/*      */ 
/*      */ 
/*      */   
/*  147 */   public FEnergyStorage energyStorage = new FEnergyStorage(((Integer)Config.furnaceEnergyCapacityTier2.get()).intValue())
/*      */     {
/*      */       protected void onEnergyChanged() {
/*  150 */         if (BlockIronFurnaceTileBase.this.level != null && BlockIronFurnaceTileBase.this.level.getBlockEntity(BlockIronFurnaceTileBase.this.getBlockPos()) != null)
/*      */         {
/*  152 */           if (BlockIronFurnaceTileBase.this.lastGameTickEnergyUpdated <= 0L) {
/*      */             
/*  154 */             BlockIronFurnaceTileBase.this.setChanged();
/*  155 */             BlockIronFurnaceTileBase.this.lastGameTickEnergyUpdated = BlockIronFurnaceTileBase.this.level.getGameTime();
/*      */           }
/*  157 */           else if (BlockIronFurnaceTileBase.this.level.getGameTime() - BlockIronFurnaceTileBase.this.lastGameTickEnergyUpdated >= 20L) {
/*      */             
/*  159 */             BlockIronFurnaceTileBase.this.setChanged();
/*  160 */             BlockIronFurnaceTileBase.this.lastGameTickEnergyUpdated = BlockIronFurnaceTileBase.this.level.getGameTime();
/*      */           } 
/*      */         }
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*  168 */   public LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> this.energyStorage);
/*      */   LazyOptional<? extends IItemHandler>[] invHandlers;
/*      */   public int getEnergy() { return this.energyStorage.getEnergy(); }
/*  171 */   public int getCapacity() { return this.energyStorage.getCapacity(); } public void setEnergy(int energy) { this.energyStorage.setEnergy(energy); } public void setMaxEnergy(int energy) { this.energyStorage.setCapacity(energy); } public void removeEnergy(int energy) { this.energyStorage.setEnergy(this.energyStorage.getEnergy() - energy); } public boolean hasRecipe(ItemStack stack) { Item item = stack.getItem(); if (this.recipeType == RecipeType.SMOKING) return ((Boolean)ModSetup.HAS_RECIPE_SMOKING.computeIfAbsent(ForgeRegistries.ITEMS.getDelegateOrThrow(item), value -> Boolean.valueOf(this.level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>)this.recipeType, (Container)new SimpleContainer(new ItemStack[] { stack }), this.level).isPresent()))).booleanValue();  if (this.recipeType == RecipeType.BLASTING) return ((Boolean)ModSetup.HAS_RECIPE_BLASTING.computeIfAbsent(ForgeRegistries.ITEMS.getDelegateOrThrow(item), value -> Boolean.valueOf(this.level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>)this.recipeType, (Container)new SimpleContainer(new ItemStack[] { stack }), this.level).isPresent()))).booleanValue();  return ((Boolean)ModSetup.HAS_RECIPE.computeIfAbsent(ForgeRegistries.ITEMS.getDelegateOrThrow(item), value -> Boolean.valueOf(this.level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>)this.recipeType, (Container)new SimpleContainer(new ItemStack[] { stack }), this.level).isPresent()))).booleanValue(); } public boolean hasGeneratorBlastingRecipe(ItemStack stack) { return getRecipeGeneratorBlasting(stack).isPresent(); } protected Optional<AbstractCookingRecipe> getRecipe(ItemStack stack) { Optional<AbstractCookingRecipe> recipe = (Optional<AbstractCookingRecipe>)getCache().computeIfAbsent(stack.getItem(), item -> (stack.getItem() instanceof net.minecraft.world.item.AirItem) ? Optional.<AbstractCookingRecipe>empty() : this.level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>)this.recipeType, (Container)new SimpleContainer(new ItemStack[] { stack }), this.level)); return recipe; } protected Optional<AbstractCookingRecipe> getRecipeFactory(int slot, ItemStack stack) { Optional<AbstractCookingRecipe> recipe = (Optional<AbstractCookingRecipe>)((LRUCache)getFactoryCache().get(slot - FACTORY_INPUT[0])).computeIfAbsent(stack.getItem(), item -> (stack.getItem() instanceof net.minecraft.world.item.AirItem) ? Optional.<AbstractCookingRecipe>empty() : this.level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>)this.recipeType, (Container)new SimpleContainer(new ItemStack[] { stack }), this.level)); return recipe; } protected Optional<AbstractCookingRecipe> getRecipeNonCached(ItemStack stack) { return (stack.getItem() instanceof net.minecraft.world.item.AirItem) ? Optional.<AbstractCookingRecipe>empty() : this.level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>)this.recipeType, (Container)new SimpleContainer(new ItemStack[] { stack }), this.level); } protected Optional<GeneratorRecipe> getRecipeGeneratorBlasting(ItemStack item) { return (item.getItem() instanceof net.minecraft.world.item.AirItem) ? Optional.<GeneratorRecipe>empty() : this.level.getRecipeManager().getRecipeFor(Registration.GENERATOR_RECIPE_TYPE.get(), (Container)new SimpleContainer(new ItemStack[] { item }), this.level); } protected void checkRecipeType() { ItemStack stack = getItem(3); if (stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentBlasting && this.recipeType != RecipeType.BLASTING) this.recipeType = RecipeType.BLASTING;  if (stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentSmoking && this.recipeType != RecipeType.SMOKING) this.recipeType = RecipeType.SMOKING;  if (!(stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentSmoking) && !(stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentBlasting) && this.recipeType != RecipeType.SMELTING) this.recipeType = RecipeType.SMELTING;  } protected LRUCache<Item, Optional<AbstractCookingRecipe>> getCache() { checkRecipeType(); if (this.recipeType == RecipeType.BLASTING) return this.blasting_cache;  if (this.recipeType == RecipeType.SMOKING) return this.smoking_cache;  return this.cache; } protected List<LRUCache<Item, Optional<AbstractCookingRecipe>>> getFactoryCache() { checkRecipeType(); if (this.recipeType == RecipeType.BLASTING) return this.factory_blasting_cache;  if (this.recipeType == RecipeType.SMOKING) return this.factory_smoking_cache;  return this.factory_cache; } public int getCookTime() { ItemStack stack = getItem(4); if (getItem(0).getItem() == Items.AIR) return this.totalCookTime;  int speed = getSpeed(); if (!stack.isEmpty()) { if (stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentSpeed) speed = Math.max(1, speed / 2);  if (stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentFuel) speed = Math.max(1, (int)Math.ceil(speed * 1.25D));  }  return Math.max(1, speed); } protected int getSpeed() { int regular = ((Integer)getCookTimeConfig().get()).intValue(); int recipe = ((Integer)getCache().computeIfAbsent(getItem(0).getItem(), item -> getRecipeNonCached(new ItemStack((ItemLike)item))).map(AbstractCookingRecipe::getCookingTime).orElse(Integer.valueOf(0))).intValue(); double div = 200.0D / recipe; double i = regular / div; return (int)Math.max(1.0D, i); } protected int getFactoryCookTime(int slot) { ItemStack stack = getItem(4); if (getItem(slot).getItem() == Items.AIR) return this.factoryTotalCookTime[slot - FACTORY_INPUT[0]];  int speed = getFactorySpeed(slot); if (!stack.isEmpty()) { if (stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentSpeed) speed = Math.max(1, speed / 2);  if (stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentFuel) speed = Math.max(1, (int)Math.ceil(speed * 1.25D));  }  return Math.max(1, speed); } protected int getFactorySpeed(int slot) { int regular = ((Integer)getCookTimeConfig().get()).intValue(); int recipe = ((Integer)getFactoryCache().get(slot - FACTORY_INPUT[0]).computeIfAbsent(getItem(slot).getItem(), item -> getRecipeNonCached(new ItemStack((ItemLike)item))).map(AbstractCookingRecipe::getCookingTime).orElse(Integer.valueOf(0))).intValue(); double div = 200.0D / recipe; double i = regular / div; return (int)Math.max(1.0D, i); } public ForgeConfigSpec.IntValue getCookTimeConfig() { return null; } public BlockIronFurnaceTileBase(BlockEntityType<?> tileentitytypeIn, BlockPos pos, BlockState state) { super(tileentitytypeIn, pos, state, 19);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1934 */     this
/* 1935 */       .invHandlers = (LazyOptional<? extends IItemHandler>[])SidedInvWrapper.create((WorldlyContainer)this, new Direction[] { Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST }); this.recipeType = RecipeType.SMELTING; this.furnaceSettings = new FurnaceSettings() {
/*      */         public void onChanged() { BlockIronFurnaceTileBase.this.setChanged(); }
/*      */       }; }
/*      */   protected int getAugment(ItemStack stack) { if (stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentBlasting) return 1;  if (stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentSmoking) return 2;  if (stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentSpeed) return 1;  if (stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentFuel) return 2;  if (stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentFactory) return 1;  if (stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentGenerator) return 2;  return 0; }
/*      */   public void forceUpdateAllStates() { BlockState state = this.level.getBlockState(this.worldPosition); if (((Boolean)state.getValue((Property)BlockStateProperties.LIT)).booleanValue() != isBurning()) this.level.setBlock(this.worldPosition, (BlockState)state.setValue((Property)BlockStateProperties.LIT, Boolean.valueOf(isBurning())), 3);  if (((Integer)state.getValue((Property)BlockIronFurnaceBase.TYPE)).intValue() != getStateType()) this.level.setBlock(this.worldPosition, (BlockState)state.setValue((Property)BlockIronFurnaceBase.TYPE, Integer.valueOf(getStateType())), 3);  if (((Integer)state.getValue((Property)BlockIronFurnaceBase.JOVIAL)).intValue() != this.jovial) this.level.setBlock(this.worldPosition, (BlockState)state.setValue((Property)BlockIronFurnaceBase.JOVIAL, Integer.valueOf(this.jovial)), 3);  } public void dropContents() { for (int i = 0; i <= 18; i++) { if (i < 3 || i > 5) { ItemStack stack = getItem(i); Containers.dropItemStack(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), stack); }  }  } public int getGeneration() { int rf = 0; if (this instanceof BlockCopperFurnaceTile) { rf = ((Integer)Config.copperFurnaceGeneration.get()).intValue(); } else if (this instanceof BlockIronFurnaceTile) { rf = ((Integer)Config.ironFurnaceGeneration.get()).intValue(); } else if (this instanceof BlockSilverFurnaceTile) { rf = ((Integer)Config.silverFurnaceGeneration.get()).intValue(); } else if (this instanceof BlockGoldFurnaceTile) { rf = ((Integer)Config.goldFurnaceGeneration.get()).intValue(); } else if (this instanceof BlockDiamondFurnaceTile) { rf = ((Integer)Config.diamondFurnaceGeneration.get()).intValue(); } else if (this instanceof BlockEmeraldFurnaceTile) { rf = ((Integer)Config.emeraldFurnaceGeneration.get()).intValue(); } else if (this instanceof BlockCrystalFurnaceTile) { rf = ((Integer)Config.crystalFurnaceGeneration.get()).intValue(); } else if (this instanceof BlockObsidianFurnaceTile) { rf = ((Integer)Config.obsidianFurnaceGeneration.get()).intValue(); } else if (this instanceof BlockNetheriteFurnaceTile) { rf = ((Integer)Config.netheriteFurnaceGeneration.get()).intValue(); } else if (this instanceof BlockMillionFurnaceTile) { rf = ((Integer)Config.millionFurnaceGeneration.get()).intValue(); } else if (this instanceof ironfurnaces.tileentity.furnaces.other.BlockAllthemodiumFurnaceTile) { rf = ((Integer)Config.allthemodiumGeneration.get()).intValue(); } else if (this instanceof ironfurnaces.tileentity.furnaces.other.BlockVibraniumFurnaceTile) { rf = ((Integer)Config.vibraniumGeneration.get()).intValue(); } else if (this instanceof ironfurnaces.tileentity.furnaces.other.BlockUnobtainiumFurnaceTile) { rf = ((Integer)Config.unobtainiumGeneration.get()).intValue(); }  return (getItem(4).getItem() instanceof ironfurnaces.items.augments.ItemAugmentSpeed) ? (rf * 2) : ((getItem(4).getItem() instanceof ironfurnaces.items.augments.ItemAugmentFuel) ? (int)(rf * 0.75D) : rf); } public static int getSmokingBurn(ItemStack stack) { if (stack.isEmpty()) return 0;  Item item = stack.getItem(); return ((Integer)ModSetup.SMOKING_BURNS.getOrDefault(ForgeRegistries.ITEMS.getDelegateOrThrow(item), Integer.valueOf(addSmokingBurn(stack)))).intValue(); } public static int addSmokingBurn(ItemStack stack) { int burnTime = getSmokingBurnTime(stack); Item item = stack.getItem(); ModSetup.SMOKING_BURNS.put(ForgeRegistries.ITEMS.getDelegateOrThrow(item), Integer.valueOf(burnTime)); return 0; } public static int getSmokingBurnTime(ItemStack stack) { if (!stack.isEmpty() && stack.getItem().getFoodProperties() != null && stack.getItem().getFoodProperties().getNutrition() > 0) return stack.getItem().getFoodProperties().getNutrition() * 800;  return 0; } public int getGeneratorBurn() { int burn = 0; if (getItem(3).getItem() instanceof ironfurnaces.items.augments.ItemAugmentSmoking) { burn = getSmokingBurn(getItem(6)); } else if (getItem(3).getItem() instanceof ironfurnaces.items.augments.ItemAugmentBlasting) { if (!getItem(6).isEmpty()) { int energy = ((Integer)this.generator_cache.computeIfAbsent(getItem(6).getItem(), item -> getRecipeGeneratorBlasting(new ItemStack((ItemLike)item))).map(GeneratorRecipe::getEnergy).orElse(Integer.valueOf(0))).intValue(); burn = energy / 20; }  } else { burn = getBurnTime(getItem(6), RecipeType.SMELTING); }  if (getItem(4).getItem() instanceof ironfurnaces.items.augments.ItemAugmentSpeed) { burn /= 2; } else if (getItem(4).getItem() instanceof ironfurnaces.items.augments.ItemAugmentFuel) { burn *= 2; }  return burn; } public boolean isFactoryCooking() { for (int i = 0; i < this.factoryCookTime.length; i++) { if (this.factoryCookTime[i] > 0) return true;  }  return false; } public Map<Integer, Integer> getSplitCounts(int[] slot, int[] input) { if (slot.length != input.length) return null;  Map<Integer, Integer> output = Maps.newHashMap(); double sum = 0.0D; for (int i = 0; i < input.length; i++) sum += input[i];  double splitted = sum / input.length; if (sum % input.length != 0.0D) { if (Math.floor(splitted) < splitted) { double lowest = Math.floor(sum / input.length) * input.length; int itemsLeftOver = (int)sum - (int)lowest; for (int k = 0; k < input.length; k++) { if (itemsLeftOver > 0) { input[k] = (int)Math.ceil(splitted); itemsLeftOver--; } else { input[k] = (int)splitted; }  }  }  } else { for (int k = 0; k < input.length; k++) input[k] = (int)splitted;  }  for (int j = 0; j < input.length; j++) output.put(Integer.valueOf(slot[j]), Integer.valueOf(input[j]));  return output; } public void fillEmptySlots(int start, int size) { int amount = 0; for (int i = start; i < size; i++) { if (getItem(FACTORY_INPUT[i]).isEmpty()) amount++;  }  if (amount == 0) return;  ItemStack stack = ItemStack.EMPTY; for (int j = start; j < size; j++) { if (!getItem(FACTORY_INPUT[j]).isEmpty() && getItem(FACTORY_INPUT[j]).getCount() > 1 && amount > 0) { if (amount >= getItem(FACTORY_INPUT[j]).getCount()) amount = getItem(FACTORY_INPUT[j]).getCount() - 1;  CompoundTag stackTag = getItem(FACTORY_INPUT[j]).getTag(); stack = new ItemStack((ItemLike)getItem(FACTORY_INPUT[j]).getItem()); stack.setTag(stackTag); getItem(FACTORY_INPUT[j]).shrink(amount); for (int k = start; k < size; k++) { if (getItem(FACTORY_INPUT[k]).isEmpty() && amount > 0) { setItem(FACTORY_INPUT[k], stack.copy()); amount--; setChanged(); }  }  setChanged(); break; }  }  } public void split(boolean fullCheck, int start, int size) { ItemStack itemToCheck = ItemStack.EMPTY; int fullCheckCount = 0; if (!fullCheck) { for (int m = start; m < size; m++) { if (getItem(FACTORY_INPUT[m]).isEmpty()) fullCheckCount++;  }  if (fullCheckCount == 0) return;  }  for (int i = start; i < size; i++) { if (!getItem(FACTORY_INPUT[i]).isEmpty()) itemToCheck = getItem(FACTORY_INPUT[i]).copy();  }  if (!itemToCheck.isEmpty()) { fillEmptySlots(start, size); } else { return; }  Map<Integer, Integer> items = Maps.newHashMap(); Map<Integer, Integer> setCounts = Maps.newHashMap(); for (int k = start; k < size; k++) { if (!getItem(FACTORY_INPUT[k]).isEmpty() && getItem(FACTORY_INPUT[k]).getItem() == itemToCheck.getItem()) items.put(Integer.valueOf(FACTORY_INPUT[k]), Integer.valueOf(getItem(FACTORY_INPUT[k]).getCount()));  }  if (items.isEmpty()) return;  int[] slot = new int[items.size()]; int[] input = new int[items.size()]; int j = 0; for (Map.Entry<Integer, Integer> itemEntry : items.entrySet()) { slot[j] = ((Integer)itemEntry.getKey()).intValue(); input[j] = ((Integer)itemEntry.getValue()).intValue(); j++; }  setCounts = getSplitCounts(slot, input); int check = 0; for (Map.Entry<Integer, Integer> countsEntry : setCounts.entrySet()) { int count = getItem(((Integer)countsEntry.getKey()).intValue()).getCount(); if (count == ((Integer)countsEntry.getValue()).intValue()) check++;  }  if (check == setCounts.size()) return;  for (Map.Entry<Integer, Integer> countsEntry : setCounts.entrySet()) { CompoundTag newTag = getItem(((Integer)countsEntry.getKey()).intValue()).getTag(); ItemStack newStack = new ItemStack((ItemLike)getItem(((Integer)countsEntry.getKey()).intValue()).getItem(), ((Integer)countsEntry.getValue()).intValue()); newStack.setTag(newTag); setItem(((Integer)countsEntry.getKey()).intValue(), newStack); setChanged(); }  } boolean rainbowCheckFurnaceTiers(List<BlockIronFurnaceTileBase> list) { if (list.isEmpty()) return false;  int check = 0; for (BlockIronFurnaceTileBase furnace : list) { if (furnace.generatorBurn > 0.0D && furnace.getEnergy() < furnace.getCapacity()) check++;  }  if (check == 0) return false;  return true; } public static void tick(Level level, BlockPos worldPosition, BlockState blockState, final BlockIronFurnaceTileBase e) { if (!e.level.isClientSide && e.isGenerator()) { boolean flag3 = false; List<BlockIronFurnaceTileBase> iron = new ArrayList<>(); List<BlockIronFurnaceTileBase> gold = new ArrayList<>(); List<BlockIronFurnaceTileBase> diamond = new ArrayList<>(); List<BlockIronFurnaceTileBase> emerald = new ArrayList<>(); List<BlockIronFurnaceTileBase> obsidian = new ArrayList<>(); List<BlockIronFurnaceTileBase> crystal = new ArrayList<>(); List<BlockIronFurnaceTileBase> netherite = new ArrayList<>(); List<BlockIronFurnaceTileBase> copper = new ArrayList<>(); List<BlockIronFurnaceTileBase> silver = new ArrayList<>(); List<BlockIronFurnaceTileBase> rainbow = new ArrayList<>(); if (e instanceof BlockMillionFurnaceTile) { BlockMillionFurnaceTile furnaceTile = (BlockMillionFurnaceTile)e; if (furnaceTile.owner != null) { flag3 = true; if (level.getPlayerByUUID(furnaceTile.owner) != null) { List<BlockPos> furnacesBlockPos = CapabilityHooks.get(level.getPlayerByUUID(furnaceTile.owner), CapabilityPlayerFurnacesList.FURNACES_LIST).map(h -> h.get()).orElse(new ArrayList<>()); if (!furnacesBlockPos.isEmpty()) for (int j = 0; j < furnacesBlockPos.size(); j++) { level.getChunkAt(furnacesBlockPos.get(j)).setLoaded(true); BlockEntity be = level.getBlockEntity(furnacesBlockPos.get(j)); if (be != null) if (be instanceof BlockIronFurnaceTileBase) { BlockIronFurnaceTileBase te = (BlockIronFurnaceTileBase)be; if (te instanceof BlockIronFurnaceTile) iron.add(te);  if (te instanceof BlockGoldFurnaceTile) gold.add(te);  if (te instanceof BlockDiamondFurnaceTile) diamond.add(te);  if (te instanceof BlockEmeraldFurnaceTile) emerald.add(te);  if (te instanceof BlockObsidianFurnaceTile) obsidian.add(te);  if (te instanceof BlockCrystalFurnaceTile) crystal.add(te);  if (te instanceof BlockNetheriteFurnaceTile) netherite.add(te);  if (te instanceof BlockCopperFurnaceTile) copper.add(te);  if (te instanceof BlockSilverFurnaceTile) silver.add(te);  if (te instanceof BlockMillionFurnaceTile) rainbow.add(te);  }   }   }  }  if (rainbow.size() > 1) { int rainbowGens = 0; for (int j = 0; j < rainbow.size(); j++) { if (((BlockIronFurnaceTileBase)rainbow.get(j)).isGenerator()) rainbowGens++;  }  if (rainbowGens > 1) flag3 = false;  }  if (flag3 && e.rainbowCheckFurnaceTiers(iron) && e.rainbowCheckFurnaceTiers(gold) && e.rainbowCheckFurnaceTiers(diamond) && e.rainbowCheckFurnaceTiers(emerald) && e.rainbowCheckFurnaceTiers(obsidian) && e.rainbowCheckFurnaceTiers(crystal) && e.rainbowCheckFurnaceTiers(netherite) && e.rainbowCheckFurnaceTiers(copper) && e.rainbowCheckFurnaceTiers(silver)) { e.rainbowGenerating = flag3; BlockState state = level.getBlockState(worldPosition); if (((Boolean)state.getValue((Property)BlockMillionFurnace.RAINBOW_GENERATING)).booleanValue() != e.rainbowGenerating) level.setBlock(worldPosition, (BlockState)state.setValue((Property)BlockMillionFurnace.RAINBOW_GENERATING, Boolean.valueOf(e.rainbowGenerating)), 3);  e.rainbowEnergyOut(); } else { e.rainbowGenerating = false; BlockState state = level.getBlockState(worldPosition); if (((Boolean)state.getValue((Property)BlockMillionFurnace.RAINBOW_GENERATING)).booleanValue() != e.rainbowGenerating) level.setBlock(worldPosition, (BlockState)state.setValue((Property)BlockMillionFurnace.RAINBOW_GENERATING, Boolean.valueOf(e.rainbowGenerating)), 3);  }  }  }  boolean flag1 = false; boolean wasBurning = e.isBurning(); if (e.furnaceSettings.size() <= 0) e.furnaceSettings = new FurnaceSettings() {
/*      */           public void onChanged() { e.setChanged(); }
/* 1941 */         };  for (int i = 3; i <= 5; i++) { if (e.currentAugment[i - 3] != e.getAugment(e.getItem(i))) { e.currentAugment[i - 3] = e.getAugment(e.getItem(i)); e.furnaceBurnTime = 0; e.generatorBurn = 0.0D; if (i - 3 == 2 || (e.isGenerator() && i - 3 == 0)) e.dropContents();  }  }  if (!e.level.isClientSide) { if (e.getCapacity() != e.getCapacityFromTier()) e.setMaxEnergy(e.getCapacityFromTier());  if (e.totalCookTime != e.getCookTime()) e.totalCookTime = e.getCookTime();  int mode = e.getRedstoneSetting(); if (mode != 0) { if (mode == 2) { int k = 0; for (Direction side : Direction.values()) { if (level.getSignal(worldPosition.offset(side.getNormal()), side) > 0) k++;  }  if (k != 0) { e.cookTime = 0; e.furnaceBurnTime = 0; e.forceUpdateAllStates(); return; }  }  if (mode == 1) { boolean flag = false; for (Direction side : Direction.values()) { if (level.getSignal(worldPosition.offset(side.getNormal()), side) > 0) flag = true;  }  if (!flag) { e.cookTime = 0; e.furnaceBurnTime = 0; e.forceUpdateAllStates(); return; }  }  for (int j = 0; j < (Direction.values()).length; j++) e.provides[j] = e.getBlockState().getDirectSignal((BlockGetter)e.level, worldPosition, DirectionUtil.fromId(j));  } else { for (int j = 0; j < (Direction.values()).length; j++) e.provides[j] = 0;  }  if (e.doesNeedUpdateSend()) e.onUpdateSent();  }  if (e.isFactory()) { if (!e.level.isClientSide) { e.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> { if (!h.canReceive()) ((FEnergyStorage)h).setMaxReceive(h.getMaxEnergyStored());  if (h.canExtract()) ((FEnergyStorage)h).setMaxExtract(0);  }); e.checkRecipeType(); int start = (e.getTier() == 0) ? 2 : ((e.getTier() == 1) ? 1 : 0); int size = (e.getTier() == 0) ? 4 : ((e.getTier() == 1) ? 5 : 6); if (e.isAutoSplit()) e.split(false, start, size);  for (int j = start; j < size; j++) { int slot = FACTORY_INPUT[j]; if (e.factoryTotalCookTime[j] != e.getFactoryCookTime(slot)) e.factoryTotalCookTime[j] = e.getFactoryCookTime(slot);  if (!e.getItem(slot).isEmpty()) { Optional<AbstractCookingRecipe> irecipe = e.getRecipeFactory(slot, e.getItem(slot)); boolean valid = e.canFactorySmelt((Recipe)irecipe.orElse(null), slot); if (valid) { int energyRecipe = ((AbstractCookingRecipe)irecipe.get()).getCookingTime() * 20; int energy = (e.getItem(4).getItem() instanceof ironfurnaces.items.augments.ItemAugmentSpeed) ? (energyRecipe * 2) : ((e.getItem(4).getItem() instanceof ironfurnaces.items.augments.ItemAugmentFuel) ? (energyRecipe / 2) : energyRecipe); if (e.getEnergy() >= energy || e.factoryCookTime[j] > 0) { e.factoryCookTime[j] = e.factoryCookTime[j] + 1; e.usedRF[j] = e.usedRF[j] + (energy / e.factoryTotalCookTime[j]); e.setEnergy((int)(e.getEnergy() - (energy / e.factoryTotalCookTime[j]))); if (((Boolean)level.getBlockState(e.getBlockPos()).getValue((Property)BlockStateProperties.LIT)).booleanValue() != e.isFactoryCooking()) level.setBlock(worldPosition, (BlockState)level.getBlockState(worldPosition).setValue((Property)BlockStateProperties.LIT, Boolean.valueOf(e.isFactoryCooking())), 3);  if (e.factoryCookTime[j] >= e.factoryTotalCookTime[j]) { e.factoryCookTime[j] = 0; if (e.usedRF[j] < energy) { double diff = energy - e.usedRF[j]; e.setEnergy((int)(e.getEnergy() - diff)); }  e.usedRF[j] = 0.0D; e.factoryTotalCookTime[j] = e.getFactoryCookTime(slot); if (e.isAutoSplit()) e.split(true, start, size);  e.factorySmelt((Recipe)irecipe.orElse(null), slot); e.setChanged(); }  }  }  } else { e.factoryCookTime[j] = 0; if (((Boolean)level.getBlockState(e.getBlockPos()).getValue((Property)BlockStateProperties.LIT)).booleanValue() != e.isFactoryCooking()) level.setBlock(worldPosition, (BlockState)level.getBlockState(worldPosition).setValue((Property)BlockStateProperties.LIT, Boolean.valueOf(e.isFactoryCooking())), 3);  }  }  if (e.level.getGameTime() % 24L == 0L) { BlockState state = level.getBlockState(worldPosition); if (((Integer)state.getValue((Property)BlockIronFurnaceBase.TYPE)).intValue() != e.getStateType()) level.setBlock(worldPosition, (BlockState)state.setValue((Property)BlockIronFurnaceBase.TYPE, Integer.valueOf(e.getStateType())), 3);  if (((Integer)state.getValue((Property)BlockIronFurnaceBase.JOVIAL)).intValue() != e.jovial) level.setBlock(worldPosition, (BlockState)state.setValue((Property)BlockIronFurnaceBase.JOVIAL, Integer.valueOf(e.jovial)), 3);  for (int k = 0; k < e.factoryCookTime.length; k++) { if (e.factoryCookTime[k] <= 0) { int m; for (m = 0; m < FACTORY_INPUT.length; m++) { if (e.getItem(FACTORY_INPUT[m]).isEmpty()) { e.autoFactoryIO(); e.setChanged(); } else if (e.getItem(FACTORY_INPUT[m]).getCount() < e.getItem(FACTORY_INPUT[m]).getMaxStackSize()) { e.autoFactoryIO(); e.setChanged(); }  }  for (m = 0; m < FACTORY_INPUT.length; m++) { int outputSlot = FACTORY_INPUT[m] + 6; if (!e.getItem(outputSlot).isEmpty() && e.getItem(outputSlot).getCount() >= 64) e.autoFactoryIO();  }  }  }  }  }  } else if (e.isGenerator()) { if (!level.isClientSide) { e.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> { if (h.canReceive()) ((FEnergyStorage)h).setMaxReceive(0);  if (!h.canExtract()) ((FEnergyStorage)h).setMaxExtract(h.getMaxEnergyStored());  }); if (e.getEnergy() < e.getCapacity()) { if (!e.getItem(6).isEmpty() && e.generatorBurn <= 0.0D) { e.generatorBurn = e.getGeneratorBurn(); e.generatorRecentRecipeRF = (int)e.generatorBurn; if (e.getItem(6).getItem().hasCraftingRemainingItem()) { e.setItem(6, ForgeHooks.getCraftingRemainder(e.getItem(6))); } else if (!e.getItem(6).isEmpty()) { e.getItem(6).shrink(1); if (e.getItem(6).isEmpty()) e.setItem(6, ForgeHooks.getCraftingRemainder(e.getItem(6)));  }  e.setChanged(); }  if (e.isGenerator() && ((Boolean)level.getBlockState(e.getBlockPos()).getValue((Property)BlockStateProperties.LIT)).booleanValue() != ((e.generatorBurn > 0.0D))) level.setBlock(worldPosition, (BlockState)level.getBlockState(worldPosition).setValue((Property)BlockStateProperties.LIT, Boolean.valueOf((e.generatorBurn > 0.0D))), 3);  if (e.generatorBurn > 0.0D) { double max = (e.generatorRecentRecipeRF * 20); e.gottenRF += e.getGeneration(); e.setEnergy(e.getEnergy() + e.getGeneration()); if (e.generatorBurn - e.getGeneration() / 20.0D <= 0.0D) { if (e.gottenRF + e.getGeneration() > max && e.gottenRF + e.getGeneration() < e.getCapacity()) { int diff = (int)(e.gottenRF + e.getGeneration() - max); e.setEnergy(e.getEnergy() + e.getGeneration()); e.removeEnergy(diff); }  if (e.gottenRF + e.getGeneration() < max) { int diff = (int)(max - e.gottenRF + e.getGeneration()); e.setEnergy(e.getEnergy() + e.getGeneration()); e.setEnergy(e.getEnergy() + diff); }  e.gottenRF = 0.0D; }  e.generatorBurn -= e.getGeneration() / 20.0D; if (e.generatorBurn <= 0.0D) { e.autoIOGenerator(); e.generatorBurn = 0.0D; }  }  }  if (e.generatorBurn <= 0.0D) e.generatorBurn = 0.0D;  e.energyOut(); if (e.level.getGameTime() % 24L == 0L) if (e.generatorBurn <= 0.0D) if (e.getItem(6).isEmpty()) { e.autoIOGenerator(); e.setChanged(); } else if (e.getItem(6).getCount() < e.getItem(6).getMaxStackSize()) { e.autoIOGenerator(); e.setChanged(); }    }  if (e.level.getGameTime() % 24L == 0L) { BlockState state = level.getBlockState(worldPosition); if (((Integer)state.getValue((Property)BlockIronFurnaceBase.TYPE)).intValue() != e.getStateType()) level.setBlock(worldPosition, (BlockState)state.setValue((Property)BlockIronFurnaceBase.TYPE, Integer.valueOf(e.getStateType())), 3);  if (((Integer)state.getValue((Property)BlockIronFurnaceBase.JOVIAL)).intValue() != e.jovial) level.setBlock(worldPosition, (BlockState)state.setValue((Property)BlockIronFurnaceBase.JOVIAL, Integer.valueOf(e.jovial)), 3);  }  } else if (e.isFurnace()) { e.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> { if (h.canReceive()) ((FEnergyStorage)h).setMaxReceive(0);  if (h.canExtract()) ((FEnergyStorage)h).setMaxExtract(0);  }); if (!e.level.isClientSide) { if (e.isBurning()) e.furnaceBurnTime--;  e.checkRecipeType(); ItemStack itemstack = e.getItem(1); if (e.isBurning() || (!itemstack.isEmpty() && !e.getItem(0).isEmpty())) { Optional<AbstractCookingRecipe> irecipe = Optional.empty(); if (!e.getItem(0).isEmpty()) irecipe = e.getRecipe(e.getItem(0));  boolean valid = e.canSmelt((Recipe)irecipe.orElse(null)); if (!e.isBurning() && valid) { if (itemstack.getItem() instanceof ironfurnaces.items.ItemHeater) { if (itemstack.hasTag()) { int x = itemstack.getTag().getInt("X"); int y = itemstack.getTag().getInt("Y"); int z = itemstack.getTag().getInt("Z"); BlockEntity te = level.getBlockEntity(new BlockPos(x, y, z)); if (te instanceof BlockWirelessEnergyHeaterTile) { int energy = ((BlockWirelessEnergyHeaterTile)te).getEnergy(); if (energy >= 2000) { if (!e.getItem(4).isEmpty() && e.getItem(4).getItem() instanceof ironfurnaces.items.augments.ItemAugmentFuel) { e.furnaceBurnTime = 400 * e.getCookTime() / 200; } else if (!e.getItem(4).isEmpty() && e.getItem(4).getItem() instanceof ironfurnaces.items.augments.ItemAugmentSpeed) { if (energy >= 4000) e.furnaceBurnTime = 100 * e.getCookTime() / 200;  } else { e.furnaceBurnTime = 200 * e.getCookTime() / 200; }  if (e.furnaceBurnTime > 0) ((BlockWirelessEnergyHeaterTile)te).removeEnergy(2000);  e.recipesUsed = e.furnaceBurnTime; }  }  }  } else { if (!e.getItem(4).isEmpty()) { if (e.getItem(4).getItem() instanceof ironfurnaces.items.augments.ItemAugmentFuel) { e.furnaceBurnTime = getBurnTime(itemstack, e.recipeType) * e.getCookTime() / 200 * 2; } else if (e.getItem(4).getItem() instanceof ironfurnaces.items.augments.ItemAugmentSpeed) { e.furnaceBurnTime = getBurnTime(itemstack, e.recipeType) * e.getCookTime() / 200 / 2; }  } else { e.furnaceBurnTime = getBurnTime(itemstack, e.recipeType) * e.getCookTime() / 200; }  e.recipesUsed = e.furnaceBurnTime; }  if (e.isBurning()) { flag1 = true; if (!(itemstack.getItem() instanceof ironfurnaces.items.ItemHeater)) if (itemstack.getItem().hasCraftingRemainingItem()) { e.setItem(1, ForgeHooks.getCraftingRemainder(itemstack)); } else if (!itemstack.isEmpty()) { itemstack.shrink(1); if (itemstack.isEmpty()) e.setItem(1, ForgeHooks.getCraftingRemainder(itemstack));  }   }  }  if (e.isBurning() && valid) { e.cookTime++; if (e.cookTime >= e.totalCookTime) { e.cookTime = 0; e.totalCookTime = e.getCookTime(); e.smelt((Recipe)irecipe.orElse(null)); e.autoIO(); flag1 = true; }  } else { e.cookTime = 0; }  } else if (!e.isBurning() && e.cookTime > 0) { e.cookTime = clamp(e.cookTime - 2, 0, e.totalCookTime); }  if (e.level.getGameTime() % 24L == 0L) if (e.cookTime <= 0) { if (e.getItem(0).isEmpty()) { e.autoIO(); flag1 = true; } else if (e.getItem(0).getCount() < e.getItem(0).getMaxStackSize()) { e.autoIO(); flag1 = true; }  if (e.getItem(1).isEmpty()) { e.autoIO(); flag1 = true; } else if (e.getItem(1).getCount() < e.getItem(1).getMaxStackSize()) { e.autoIO(); flag1 = true; }  if (!e.getItem(2).isEmpty() && e.getItem(2).getCount() >= 64) e.autoIO();  }   }  if (wasBurning != e.isBurning()) level.setBlock(worldPosition, (BlockState)level.getBlockState(e.worldPosition).setValue((Property)BlockStateProperties.LIT, Boolean.valueOf(e.isBurning())), 3);  if (e.level.getGameTime() % 24L == 0L) { BlockState state = level.getBlockState(worldPosition); if (((Integer)state.getValue((Property)BlockIronFurnaceBase.TYPE)).intValue() != e.getStateType()) level.setBlock(worldPosition, (BlockState)state.setValue((Property)BlockIronFurnaceBase.TYPE, Integer.valueOf(e.getStateType())), 3);  if (((Integer)state.getValue((Property)BlockIronFurnaceBase.JOVIAL)).intValue() != e.jovial) level.setBlock(worldPosition, (BlockState)state.setValue((Property)BlockIronFurnaceBase.JOVIAL, Integer.valueOf(e.jovial)), 3);  }  if (flag1) e.setChanged();  }  } public static int clamp(int p_76125_0_, int p_76125_1_, int p_76125_2_) { if (p_76125_0_ < p_76125_1_) return p_76125_1_;  return (p_76125_0_ > p_76125_2_) ? p_76125_2_ : p_76125_0_; } protected int getCapacityFromTier() { switch (getTier()) { case 1: case 2:  }  return ((Integer)Config.furnaceEnergyCapacityTier0.get()).intValue(); } protected void rainbowEnergyOut() { Map<BlockEntity, Direction> tiles = Maps.newHashMap(); for (Direction dir : Direction.values()) { BlockEntity tile = this.level.getBlockEntity(this.worldPosition.offset(dir.getNormal())); if (tile != null) if (this.furnaceSettings.get(dir.ordinal()) == 2 || this.furnaceSettings.get(dir.ordinal()) == 3) { IEnergyStorage other = CapabilityHooks.get(tile,ForgeCapabilities.ENERGY, dir.getOpposite()).map(other1 -> other1).orElse(null); if (other != null) if (other.canReceive() && other.getEnergyStored() < other.getMaxEnergyStored()) tiles.put(tile, dir.getOpposite());   }   }  for (Map.Entry<BlockEntity, Direction> entry : tiles.entrySet()) { int energy = ((Integer)Config.millionFurnacePowerToGenerate.get()).intValue() / tiles.size(); CapabilityHooks.get((BlockEntity)entry.getKey(),ForgeCapabilities.ENERGY, entry.getValue()).ifPresent(h -> h.receiveEnergy(energy, false)); }  } protected void energyOut() { Map<BlockEntity, Direction> tiles = Maps.newHashMap(); for (Direction dir : Direction.values()) { BlockEntity tile = this.level.getBlockEntity(this.worldPosition.offset(dir.getNormal())); if (tile != null) if (this.furnaceSettings.get(dir.ordinal()) == 2 || this.furnaceSettings.get(dir.ordinal()) == 3) { IEnergyStorage other = CapabilityHooks.get(tile,ForgeCapabilities.ENERGY, dir.getOpposite()).map(other1 -> other1).orElse(null); if (other != null) if (other.canReceive() && other.getEnergyStored() < other.getMaxEnergyStored()) tiles.put(tile, dir.getOpposite());   }   }  for (Iterator<Map.Entry<BlockEntity, Direction>> iterator = tiles.entrySet().iterator(); iterator.hasNext(); ) { Map.Entry<BlockEntity, Direction> entry = iterator.next(); int energy = Math.min(((Integer)getCapability(ForgeCapabilities.ENERGY).map(h -> Integer.valueOf(((FEnergyStorage)h).getMaxExtract())).orElse(Integer.valueOf(0))).intValue(), getEnergy()) / tiles.size(); CapabilityHooks.get((BlockEntity)entry.getKey(),ForgeCapabilities.ENERGY, entry.getValue()).ifPresent(h -> removeEnergy(h.receiveEnergy(energy, false))); }  } protected void autoIO() { for (Direction dir : Direction.values()) { BlockEntity tile = this.level.getBlockEntity(this.worldPosition.offset(dir.getNormal())); if (tile == null) continue;  if ((this.furnaceSettings.get(dir.ordinal()) == 1 || this.furnaceSettings.get(dir.ordinal()) == 2 || this.furnaceSettings.get(dir.ordinal()) == 3 || this.furnaceSettings.get(dir.ordinal()) == 4) && tile != null) { IItemHandler other = CapabilityHooks.get(tile,ForgeCapabilities.ITEM_HANDLER, dir.getOpposite()).map(other1 -> other1).orElse(null); if (other == null) continue;  if (other != null && (getAutoInput() != 0 || getAutoOutput() != 0)) { if (getAutoInput() == 1) { if (this.furnaceSettings.get(dir.ordinal()) == 1 || this.furnaceSettings.get(dir.ordinal()) == 3) { if (getItem(0).getCount() >= getItem(0).getMaxStackSize()) continue;  for (int i = 0; i < other.getSlots(); i++) { if (!other.getStackInSlot(i).isEmpty()) { ItemStack stack = other.extractItem(i, other.getStackInSlot(i).getMaxStackSize(), true); if ((hasRecipe(stack) && getItem(0).isEmpty()) || ItemHandlerHelper.canItemStacksStack(getItem(0), stack)) insertItemInternal(0, other.extractItem(i, other.getStackInSlot(i).getMaxStackSize() - getItem(0).getCount(), false), false);  }  }  }  if (this.furnaceSettings.get(dir.ordinal()) == 4) { if (getItem(1).getCount() >= getItem(1).getMaxStackSize()) continue;  for (int i = 0; i < other.getSlots(); i++) { if (!other.getStackInSlot(i).isEmpty()) if (isItemFuel(other.getStackInSlot(i), this.recipeType)) { ItemStack stack = other.extractItem(i, other.getStackInSlot(i).getMaxStackSize(), true); if ((isItemFuel(stack, this.recipeType) && getItem(1).isEmpty()) || ItemHandlerHelper.canItemStacksStack(getItem(1), stack)) insertItemInternal(1, other.extractItem(i, other.getStackInSlot(i).getMaxStackSize() - getItem(1).getCount(), false), false);  }   }  }  }  if (getAutoOutput() == 1) { if (this.furnaceSettings.get(dir.ordinal()) == 4) { if (getItem(1).isEmpty()) continue;  if (isItemFuel(getItem(1), this.recipeType)) continue;  for (int i = 0; i < other.getSlots(); i++) { ItemStack stack = extractItemInternal(1, other.getSlotLimit(i) - other.getStackInSlot(i).getCount(), true); if (other.isItemValid(i, stack) && (other.getStackInSlot(i).isEmpty() || (ItemHandlerHelper.canItemStacksStack(other.getStackInSlot(i), stack) && other.getStackInSlot(i).getCount() + stack.getCount() <= other.getSlotLimit(i)))) { boolean check = other.insertItem(i, extractItemInternal(1, stack.getCount(), true), true).isEmpty(); if (check) other.insertItem(i, extractItemInternal(1, stack.getCount(), false), false);  }  }  }  if ((this.furnaceSettings.get(dir.ordinal()) == 2 || this.furnaceSettings.get(dir.ordinal()) == 3) && !getItem(2).isEmpty()) for (int i = 0; i < other.getSlots(); i++) { ItemStack stack = extractItemInternal(2, other.getSlotLimit(i) - other.getStackInSlot(i).getCount(), true); if (other.isItemValid(i, stack) && (other.getStackInSlot(i).isEmpty() || (ItemHandlerHelper.canItemStacksStack(other.getStackInSlot(i), stack) && other.getStackInSlot(i).getCount() + stack.getCount() <= other.getSlotLimit(i)))) { boolean check = other.insertItem(i, extractItemInternal(2, stack.getCount(), true), true).isEmpty(); if (check) other.insertItem(i, extractItemInternal(2, stack.getCount(), false), false);  }  }   }  }  }  continue; }  } @NotNull public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) { if (!isRemoved() && facing != null && capability == ForgeCapabilities.ITEM_HANDLER) {
/* 1942 */       if (facing == Direction.DOWN)
/* 1943 */         return this.invHandlers[0].cast(); 
/* 1944 */       if (facing == Direction.UP)
/* 1945 */         return this.invHandlers[1].cast(); 
/* 1946 */       if (facing == Direction.NORTH)
/* 1947 */         return this.invHandlers[2].cast(); 
/* 1948 */       if (facing == Direction.SOUTH)
/* 1949 */         return this.invHandlers[3].cast(); 
/* 1950 */       if (facing == Direction.WEST) {
/* 1951 */         return this.invHandlers[4].cast();
/*      */       }
/* 1953 */       return this.invHandlers[5].cast();
/*      */     } 
/* 1955 */     if (!isRemoved() && capability == ForgeCapabilities.ENERGY && (isGenerator() || isFactory())) {
/* 1956 */       return this.energy.cast();
/*      */     }
/* 1958 */     return super.getCapability(capability, facing); } protected void autoIOGenerator() { for (Direction dir : Direction.values()) { BlockEntity tile = this.level.getBlockEntity(this.worldPosition.offset(dir.getNormal())); if (tile == null) continue;  if (this.furnaceSettings.get(dir.ordinal()) == 4 && tile != null) { IItemHandler other = CapabilityHooks.get(tile,ForgeCapabilities.ITEM_HANDLER, dir.getOpposite()).map(other1 -> other1).orElse(null); if (other == null) continue;  if (other != null) { if (getAutoInput() != 0 && this.furnaceSettings.get(dir.ordinal()) == 4) { if (getItem(6).getCount() >= getItem(6).getMaxStackSize()) continue;  for (int i = 0; i < other.getSlots(); i++) { if (!other.getStackInSlot(i).isEmpty()) if (other.getStackInSlot(i).getItem() != Items.BUCKET) { ItemStack stack = other.extractItem(i, other.getStackInSlot(i).getMaxStackSize(), true); if (!(stack.getItem() instanceof ironfurnaces.items.ItemHeater)) if ((isItemFuel(stack, this.recipeType) && getItem(6).isEmpty()) || ItemHandlerHelper.canItemStacksStack(getItem(6), stack)) insertItemInternal(6, other.extractItem(i, other.getStackInSlot(i).getMaxStackSize() - getItem(6).getCount(), false), false);   }   }  }  if (getAutoOutput() != 0) if (this.furnaceSettings.get(dir.ordinal()) == 4) if (!getItem(6).isEmpty()) if (!isItemFuel(getItem(6), this.recipeType)) for (int i = 0; i < other.getSlots(); i++) { ItemStack stack = extractItemInternal(6, getItem(6).getMaxStackSize() - other.getStackInSlot(i).getCount(), true); if (other.isItemValid(i, stack) && (other.getStackInSlot(i).isEmpty() || (ItemHandlerHelper.canItemStacksStack(other.getStackInSlot(i), stack) && other.getStackInSlot(i).getCount() + stack.getCount() <= other.getSlotLimit(i)))) { boolean check = other.insertItem(i, extractItemInternal(6, stack.getCount(), true), true).isEmpty(); if (check) other.insertItem(i, extractItemInternal(6, stack.getCount(), false), false);  }  }      }  }  continue; }  } protected void autoFactoryIO() { for (Direction dir : Direction.values()) { BlockEntity tile = this.level.getBlockEntity(this.worldPosition.offset(dir.getNormal())); if (tile != null) if ((this.furnaceSettings.get(dir.ordinal()) == 1 || this.furnaceSettings.get(dir.ordinal()) == 2 || this.furnaceSettings.get(dir.ordinal()) == 3) && tile != null) { IItemHandler other = CapabilityHooks.get(tile,ForgeCapabilities.ITEM_HANDLER, dir.getOpposite()).map(other1 -> other1).orElse(null); if (other != null) if (other != null && (getAutoInput() != 0 || getAutoOutput() != 0)) { if (getAutoInput() == 1 && (this.furnaceSettings.get(dir.ordinal()) == 1 || this.furnaceSettings.get(dir.ordinal()) == 3)) { int start = (getTier() == 0) ? 2 : ((getTier() == 1) ? 1 : 0); int size = (getTier() == 0) ? 4 : ((getTier() == 1) ? 5 : 6); for (int j = start; j < size; j++) { if (getItem(FACTORY_INPUT[j]).getCount() < getItem(FACTORY_INPUT[j]).getMaxStackSize()) for (int i = 0; i < other.getSlots(); i++) { if (!other.getStackInSlot(i).isEmpty()) { ItemStack stack = other.extractItem(i, other.getStackInSlot(i).getMaxStackSize(), true); if ((hasRecipe(stack) && getItem(FACTORY_INPUT[j]).isEmpty()) || canItemStacksStack(getItem(FACTORY_INPUT[j]), stack)) insertItemInternal(FACTORY_INPUT[j], other.extractItem(i, other.getStackInSlot(i).getMaxStackSize() - getItem(FACTORY_INPUT[j]).getCount(), false), false);  }  }   }  }  if (getAutoOutput() == 1) if (this.furnaceSettings.get(dir.ordinal()) == 2 || this.furnaceSettings.get(dir.ordinal()) == 3) { int start = (getTier() == 0) ? 2 : ((getTier() == 1) ? 1 : 0); int size = (getTier() == 0) ? 4 : ((getTier() == 1) ? 5 : 6); for (int j = start; j < size; j++) { if (!getItem(FACTORY_INPUT[j] + 6).isEmpty()) for (int i = 0; i < other.getSlots(); i++) { ItemStack stack = extractItemInternal(FACTORY_INPUT[j] + 6, other.getSlotLimit(i) - other.getStackInSlot(i).getCount(), true); if (other.isItemValid(i, stack) && (other.getStackInSlot(i).isEmpty() || (canItemStacksStack(other.getStackInSlot(i), stack) && other.getStackInSlot(i).getCount() + stack.getCount() <= other.getSlotLimit(i)))) { boolean check = other.insertItem(i, extractItemInternal(FACTORY_INPUT[j] + 6, stack.getCount(), true), true).isEmpty(); if (check) other.insertItem(i, extractItemInternal(FACTORY_INPUT[j] + 6, stack.getCount(), false), false);  }  }   }  }   }   }   }  } public static boolean canItemStacksStack(@NotNull ItemStack a, @NotNull ItemStack b) { return ItemHandlerHelper.canItemStacksStack(a, b); } @NotNull public ItemStack insertItemInternal(int slot, @NotNull ItemStack stack, boolean simulate) { if (stack.isEmpty()) return ItemStack.EMPTY;  if (!canPlaceItemThroughFace(slot, stack, null)) return stack;  ItemStack existing = getItem(slot); int limit = stack.getMaxStackSize(); if (!existing.isEmpty()) { if (!ItemHandlerHelper.canItemStacksStack(stack, existing)) return stack;  limit -= existing.getCount(); }  if (limit <= 0) return stack;  boolean reachedLimit = (stack.getCount() > limit); if (!simulate) { if (existing.isEmpty()) { setItem(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack); } else { existing.grow(reachedLimit ? limit : stack.getCount()); }  setChanged(); }  return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY; } @NotNull private ItemStack extractItemInternal(int slot, int amount, boolean simulate) { if (amount == 0) return ItemStack.EMPTY;  ItemStack existing = getItem(slot); if (existing.isEmpty()) return ItemStack.EMPTY;  int toExtract = Math.min(amount, existing.getMaxStackSize()); if (existing.getCount() <= toExtract) { if (!simulate) { setItem(slot, ItemStack.EMPTY); setChanged(); return existing; }  return existing.copy(); }  if (!simulate) { setItem(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract)); setChanged(); }  return ItemHandlerHelper.copyStackWithSize(existing, toExtract); } public boolean isAutoSplit() { return (this.furnaceSettings.autoSplit == 1); } public int getSettingBottom() { return this.furnaceSettings.get(0); } public int getSettingTop() { return this.furnaceSettings.get(1); } public int getSettingFront() { int i = DirectionUtil.getId((Direction)getBlockState().getValue((Property)BlockStateProperties.HORIZONTAL_FACING)); return this.furnaceSettings.get(i); } public int getSettingBack() { int i = DirectionUtil.getId(((Direction)getBlockState().getValue((Property)BlockStateProperties.HORIZONTAL_FACING)).getOpposite()); return this.furnaceSettings.get(i); } public int getSettingLeft() { Direction facing = (Direction)getBlockState().getValue((Property)BlockStateProperties.HORIZONTAL_FACING); if (facing == Direction.NORTH) return this.furnaceSettings.get(DirectionUtil.getId(Direction.EAST));  if (facing == Direction.WEST) return this.furnaceSettings.get(DirectionUtil.getId(Direction.NORTH));  if (facing == Direction.SOUTH) return this.furnaceSettings.get(DirectionUtil.getId(Direction.WEST));  return this.furnaceSettings.get(DirectionUtil.getId(Direction.SOUTH)); } public int getSettingRight() { Direction facing = (Direction)getBlockState().getValue((Property)BlockStateProperties.HORIZONTAL_FACING); if (facing == Direction.NORTH) return this.furnaceSettings.get(DirectionUtil.getId(Direction.WEST));  if (facing == Direction.WEST) return this.furnaceSettings.get(DirectionUtil.getId(Direction.SOUTH));  if (facing == Direction.SOUTH) return this.furnaceSettings.get(DirectionUtil.getId(Direction.EAST));  return this.furnaceSettings.get(DirectionUtil.getId(Direction.NORTH)); } public int getIndexFront() { int i = ((Direction)getBlockState().getValue((Property)BlockStateProperties.HORIZONTAL_FACING)).ordinal(); return i; } public int getIndexBack() { int i = ((Direction)getBlockState().getValue((Property)BlockStateProperties.HORIZONTAL_FACING)).getOpposite().ordinal(); return i; } public int getIndexLeft() { Direction facing = (Direction)getBlockState().getValue((Property)BlockStateProperties.HORIZONTAL_FACING); if (facing == Direction.NORTH) return Direction.EAST.ordinal();  if (facing == Direction.WEST) return Direction.NORTH.ordinal();  if (facing == Direction.SOUTH) return Direction.WEST.ordinal();  return Direction.SOUTH.ordinal(); } public int getIndexRight() { Direction facing = (Direction)getBlockState().getValue((Property)BlockStateProperties.HORIZONTAL_FACING); if (facing == Direction.NORTH) return Direction.WEST.ordinal();  if (facing == Direction.WEST) return Direction.SOUTH.ordinal();  if (facing == Direction.SOUTH) return Direction.EAST.ordinal();  return Direction.NORTH.ordinal(); } public int getAutoInput() { return this.furnaceSettings.get(6); } public int getAugmentGUI() { return this.furnaceSettings.get(10); } public int getAutoOutput() { return this.furnaceSettings.get(7); } public int getRedstoneSetting() { return this.furnaceSettings.get(8); } public int getRedstoneComSub() { return this.furnaceSettings.get(9); } protected int getStateType() { if (getItem(3).getItem() == Registration.SMOKING_AUGMENT.get()) return 1;  if (getItem(3).getItem() == Registration.BLASTING_AUGMENT.get()) return 2;  return 0; } public boolean isBurning() { return (this.furnaceBurnTime > 0); } public boolean isRainbowFurnace() { return this instanceof BlockMillionFurnaceTile; } protected void smelt(@Nullable Recipe<?> recipe) { if (this instanceof BlockMillionFurnaceTile) { smeltItemMult(recipe, 64); } else if (this instanceof ironfurnaces.tileentity.furnaces.other.BlockAllthemodiumFurnaceTile) { smeltItemMult(recipe, ((Integer)Config.allthemodiumFurnaceSmeltMult.get()).intValue()); } else if (this instanceof ironfurnaces.tileentity.furnaces.other.BlockVibraniumFurnaceTile) { smeltItemMult(recipe, ((Integer)Config.vibraniumFurnaceSmeltMult.get()).intValue()); } else if (this instanceof ironfurnaces.tileentity.furnaces.other.BlockUnobtainiumFurnaceTile) { smeltItemMult(recipe, ((Integer)Config.unobtainiumFurnaceSmeltMult.get()).intValue()); } else { smeltItem(recipe); }  } protected void factorySmelt(@Nullable Recipe<?> recipe, int slot) { if (this instanceof BlockMillionFurnaceTile) { smeltFactoryItemMult(recipe, slot, 64); } else if (this instanceof ironfurnaces.tileentity.furnaces.other.BlockAllthemodiumFurnaceTile) { smeltFactoryItemMult(recipe, slot, ((Integer)Config.allthemodiumFurnaceSmeltMult.get()).intValue()); } else if (this instanceof ironfurnaces.tileentity.furnaces.other.BlockVibraniumFurnaceTile) { smeltFactoryItemMult(recipe, slot, ((Integer)Config.vibraniumFurnaceSmeltMult.get()).intValue()); } else if (this instanceof ironfurnaces.tileentity.furnaces.other.BlockUnobtainiumFurnaceTile) { smeltFactoryItemMult(recipe, slot, ((Integer)Config.unobtainiumFurnaceSmeltMult.get()).intValue()); } else { smeltFactoryItem(recipe, slot); }  } protected boolean canSmelt(@Nullable Recipe<?> recipe) { if (!getItem(0).isEmpty() && recipe != null) { ItemStack recipeOutput = recipe.getResultItem((RegistryAccess)RegistryAccess.EMPTY); if (!recipeOutput.isEmpty()) { ItemStack output = getItem(2); if (output.isEmpty()) return true;  if (!ItemStack.isSameItemSameTags(output, recipeOutput)) return false;  return (output.getCount() + recipeOutput.getCount() <= Math.min(output.getMaxStackSize(), 64)); }  }  return false; } protected void smeltItem(@Nullable Recipe<?> recipe) { if (recipe != null && canSmelt(recipe)) { ItemStack itemstack = getItem(0); ItemStack itemstack1 = recipe.getResultItem((RegistryAccess)RegistryAccess.EMPTY); ItemStack itemstack2 = getItem(2); if (itemstack2.isEmpty()) { setItem(2, itemstack1.copy()); } else if (itemstack2.getItem() == itemstack1.getItem()) { itemstack2.grow(itemstack1.getCount()); }  if (!this.level.isClientSide) setRecipeUsed(recipe);  if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !getItem(1).isEmpty() && getItem(1).getItem() == Items.BUCKET) setItem(1, new ItemStack((ItemLike)Items.WATER_BUCKET));  if (ModList.get().isLoaded("pmmo")) handleSmeltedPMMO(itemstack, this.level, this.worldPosition);  itemstack.shrink(1); }  } protected boolean canFactorySmelt(@Nullable Recipe<?> recipe, int slot) { int outputSlot = slot + 6; if (!getItem(slot).isEmpty() && recipe != null) { ItemStack recipeOutput = recipe.getResultItem((RegistryAccess)RegistryAccess.EMPTY); if (!recipeOutput.isEmpty()) { ItemStack output = getItem(outputSlot); if (output.isEmpty()) return true;  if (!ItemStack.isSameItemSameTags(output, recipeOutput)) return false;  return (output.getCount() + recipeOutput.getCount() <= output.getMaxStackSize()); }  }  return false; } protected void smeltFactoryItem(@Nullable Recipe<?> recipe, int slot) { int outputSlot = slot + 6; if (recipe != null && canFactorySmelt(recipe, slot)) { ItemStack itemstack = getItem(slot); ItemStack itemstack1 = recipe.getResultItem((RegistryAccess)RegistryAccess.EMPTY); ItemStack itemstack2 = getItem(outputSlot); if (itemstack2.isEmpty()) { setItem(outputSlot, itemstack1.copy()); } else if (itemstack2.getItem() == itemstack1.getItem()) { itemstack2.grow(itemstack1.getCount()); }  if (!this.level.isClientSide) setRecipeUsed(recipe);  if (ModList.get().isLoaded("pmmo")) handleSmeltedPMMO(itemstack, this.level, this.worldPosition);  itemstack.shrink(1); }  } protected void smeltItemMult(@Nullable Recipe<?> recipe, int div) { if (recipe != null && canSmelt(recipe)) { ItemStack itemstack = getItem(0); ItemStack itemstack1 = recipe.getResultItem((RegistryAccess)RegistryAccess.EMPTY); ItemStack itemstack2 = getItem(2); int maxCanSmelt = (64 - itemstack2.getCount()) / itemstack1.getCount(); int wantToSmeltCount = Math.min(Math.min(div, maxCanSmelt), itemstack.getCount()); int whenSmelted = itemstack1.getCount() * wantToSmeltCount; int decrement = whenSmelted / itemstack1.getCount(); if (itemstack2.isEmpty()) { setItem(2, new ItemStack((ItemLike)itemstack1.copy().getItem(), whenSmelted)); } else if (itemstack2.getItem() == itemstack1.getItem()) { itemstack2.grow(whenSmelted); }  if (!this.level.isClientSide) for (int i = 0; i < decrement; i++) setRecipeUsed(recipe);   if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !getItem(1).isEmpty() && getItem(1).getItem() == Items.BUCKET) setItem(1, new ItemStack((ItemLike)Items.WATER_BUCKET));  if (ModList.get().isLoaded("pmmo")) handleSmeltedPMMO(itemstack, this.level, this.worldPosition);  itemstack.shrink(decrement); }  } protected void smeltFactoryItemMult(@Nullable Recipe<?> recipe, int slot, int div) { int outputSlot = slot + 6; if (recipe != null && canFactorySmelt(recipe, slot)) { ItemStack itemstack = getItem(slot); ItemStack itemstack1 = recipe.getResultItem((RegistryAccess)RegistryAccess.EMPTY); ItemStack itemstack2 = getItem(outputSlot); int maxCanSmelt = (64 - itemstack2.getCount()) / itemstack1.getCount(); int wantToSmeltCount = Math.min(Math.min(div, maxCanSmelt), itemstack.getCount()); int whenSmelted = itemstack1.getCount() * wantToSmeltCount; int decrement = whenSmelted / itemstack1.getCount(); if (itemstack2.isEmpty()) { setItem(outputSlot, new ItemStack((ItemLike)itemstack1.copy().getItem(), whenSmelted)); } else if (itemstack2.getItem() == itemstack1.getItem()) { itemstack2.grow(whenSmelted); }  if (!this.level.isClientSide) for (int i = 0; i < decrement; i++) setRecipeUsed(recipe);   if (ModList.get().isLoaded("pmmo")) handleSmeltedPMMO(itemstack, this.level, this.worldPosition);  itemstack.shrink(decrement); }  } private void handleSmeltedPMMO(ItemStack stack, Level level, BlockPos pos) { FurnaceHandler.handle(new FurnaceBurnEvent(stack, level, pos)); }
/*      */   public void load(CompoundTag tag) { if (tag.get("Owner") != null) this.owner = tag.getUUID("Owner");  tag.getBoolean("RainbowGen"); int i; for (i = 0; i < this.factoryCookTime.length; i++) { int[] tagArr = tag.getIntArray("FactoryCookTime"); if (tagArr.length == this.factoryCookTime.length) this.factoryCookTime[i] = tagArr[i];  }  for (i = 0; i < this.factoryTotalCookTime.length; i++) { int[] tagArr = tag.getIntArray("FactoryTotalCookTime"); if (tagArr.length == this.factoryTotalCookTime.length) this.factoryTotalCookTime[i] = tagArr[i];  }  for (i = 0; i < this.usedRF.length; i++) { double tagRF = tag.getDouble("UsedRF" + i); this.usedRF[i] = tagRF; }  this.generatorBurn = tag.getDouble("GeneratorBurn"); this.generatorRecentRecipeRF = tag.getInt("GeneratorRecent"); this.gottenRF = tag.getDouble("GottenRF"); this.furnaceBurnTime = tag.getInt("BurnTime"); this.cookTime = tag.getInt("CookTime"); this.totalCookTime = tag.getInt("CookTimeTotal"); this.currentAugment = tag.getIntArray("Augment"); this.jovial = tag.getInt("Jovial"); this.recipesUsed = getBurnTime(getItem(1), this.recipeType); CompoundTag compoundnbt = tag.getCompound("RecipesUsed"); for (String s : compoundnbt.getAllKeys()) this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));  this.furnaceSettings.read(tag); setEnergy(tag.getInt("Energy")); this.lastGameTickEnergyUpdated = 0L; super.load(tag); }
/*      */   protected void saveAdditional(CompoundTag tag) { super.saveAdditional(tag); if (this.owner != null) tag.putUUID("Owner", this.owner);  tag.putBoolean("RainbowGen", this.rainbowGenerating); tag.putIntArray("FactoryCookTime", this.factoryCookTime); tag.putIntArray("FactoryTotalCookTime", this.factoryTotalCookTime); for (int i = 0; i < this.usedRF.length; i++) tag.putDouble("UsedRF" + i, this.usedRF[i]);  tag.putDouble("GeneratorBurn", this.generatorBurn); tag.putInt("GeneratorRecent", this.generatorRecentRecipeRF); tag.putDouble("GottenRF", this.gottenRF); tag.putInt("BurnTime", this.furnaceBurnTime); tag.putInt("CookTime", this.cookTime); tag.putInt("CookTimeTotal", this.totalCookTime); tag.putIntArray("Augment", this.currentAugment); tag.putInt("Jovial", this.jovial); this.furnaceSettings.write(tag); tag.putInt("Energy", getEnergy()); CompoundTag compoundnbt = new CompoundTag(); this.recipes.forEach((recipeId, craftedAmount) -> compoundnbt.putInt(recipeId.toString(), craftedAmount.intValue())); tag.put("RecipesUsed", (Tag)compoundnbt); }
/*      */   public static int getBurnTime(ItemStack stack, RecipeType recipeType) { return ForgeHooks.getBurnTime(stack, recipeType); }
/*      */   public static boolean isItemFuel(ItemStack stack, RecipeType recipeType) { return (getBurnTime(stack, recipeType) > 0 || stack.getItem() instanceof ironfurnaces.items.ItemHeater); }
/*      */   public static boolean isItemAugment(ItemStack stack, int type) { if (type == 0) return stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentRed;  if (type == 1) return stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentGreen;  if (type == 2) return stack.getItem() instanceof ironfurnaces.items.augments.ItemAugmentBlue;  return stack.getItem() instanceof ironfurnaces.items.augments.ItemAugment; }
/* 1964 */   public int[] IgetSlotsForFace(Direction side) { if (isFurnace()) {
/* 1965 */       if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 0)
/* 1966 */         return new int[0]; 
/* 1967 */       if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 1)
/* 1968 */         return new int[] { 0, 1 }; 
/* 1969 */       if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 2)
/* 1970 */         return new int[] { 2 }; 
/* 1971 */       if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 3)
/* 1972 */         return new int[] { 0, 1, 2 }; 
/* 1973 */       if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 4) {
/* 1974 */         return new int[] { 1 };
/*      */       }
/* 1976 */     } else if (isGenerator()) {
/* 1977 */       if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 4) {
/* 1978 */         return new int[] { 6 };
/*      */       }
/* 1980 */     } else if (isFactory()) {
/* 1981 */       if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 0)
/* 1982 */         return new int[0]; 
/* 1983 */       if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 1)
/* 1984 */         return FACTORY_INPUT; 
/* 1985 */       if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 2)
/* 1986 */         return new int[] { 13, 14, 15, 16, 17, 18 }; 
/* 1987 */       if (this.furnaceSettings.get(DirectionUtil.getId(side)) == 3) {
/* 1988 */         return new int[] { 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
/*      */       }
/*      */     } 
/*      */     
/* 1992 */     return new int[0]; }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean IcanExtractItem(int index, ItemStack stack, Direction direction) {
/* 1997 */     if (isFurnace()) {
/* 1998 */       if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 0)
/* 1999 */         return false; 
/* 2000 */       if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 1)
/* 2001 */         return false; 
/* 2002 */       if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 2)
/* 2003 */         return (index == 2); 
/* 2004 */       if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 3)
/* 2005 */         return (index == 2); 
/* 2006 */       if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 4 && stack.getItem() != Items.BUCKET)
/* 2007 */         return false; 
/* 2008 */       if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 4 && stack.getItem() == Items.BUCKET) {
/* 2009 */         return true;
/*      */       }
/* 2011 */     } else if (isGenerator()) {
/* 2012 */       if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 4 && stack.getItem() == Items.BUCKET) {
/* 2013 */         return true;
/*      */       }
/* 2015 */     } else if (isFactory()) {
/* 2016 */       if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 2)
/* 2017 */         return (index >= 13 && index <= 18); 
/* 2018 */       if (this.furnaceSettings.get(DirectionUtil.getId(direction)) == 3) {
/* 2019 */         return (index >= 13 && index <= 18);
/*      */       }
/*      */     } 
/* 2022 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean IisItemValidForSlot(int index, ItemStack stack) {
/* 2027 */     if (isFurnace()) {
/* 2028 */       if (index == 2 || index == 3 || index == 4 || index == 5) {
/* 2029 */         return false;
/*      */       }
/* 2031 */       if (index == 0) {
/* 2032 */         if (stack.isEmpty()) {
/* 2033 */           return false;
/*      */         }
/*      */         
/* 2036 */         return hasRecipe(stack);
/*      */       } 
/* 2038 */       if (index == 1) {
/* 2039 */         ItemStack itemstack = getItem(1);
/* 2040 */         return (getBurnTime(stack, this.recipeType) > 0 || (stack.getItem() == Items.BUCKET && itemstack.getItem() != Items.BUCKET) || stack.getItem() instanceof ironfurnaces.items.ItemHeater);
/*      */       } 
/* 2042 */     } else if (isGenerator()) {
/* 2043 */       if (index == 6) {
/* 2044 */         if (getItem(3).getItem() instanceof ironfurnaces.items.augments.ItemAugmentSmoking && getSmokingBurn(stack) > 0) {
/* 2045 */           return true;
/*      */         }
/* 2047 */         if (getItem(3).getItem() instanceof ironfurnaces.items.augments.ItemAugmentBlasting && hasGeneratorBlastingRecipe(stack)) {
/* 2048 */           return true;
/*      */         }
/* 2050 */         if (getItem(3).isEmpty() && getBurnTime(stack, this.recipeType) > 0) {
/* 2051 */           return true;
/*      */         }
/* 2053 */         if (stack.getItem() instanceof ironfurnaces.items.ItemHeater)
/*      */         {
/* 2055 */           return false;
/*      */         }
/*      */       } 
/* 2058 */     } else if (isFactory()) {
/* 2059 */       if ((index >= 13 && index <= 18) || index == 3 || index == 4 || index == 5) {
/* 2060 */         return false;
/*      */       }
/* 2062 */       if (index >= 7 && index <= 12) {
/* 2063 */         if (stack.isEmpty()) {
/* 2064 */           return false;
/*      */         }
/* 2066 */         if (getTier() == 0) {
/*      */           
/* 2068 */           if (index >= 9 && index <= 10)
/*      */           {
/* 2070 */             return hasRecipe(stack);
/*      */           }
/*      */ 
/*      */           
/* 2074 */           return false;
/*      */         } 
/*      */         
/* 2077 */         if (getTier() == 1) {
/*      */           
/* 2079 */           if (index >= 8 && index <= 11)
/*      */           {
/* 2081 */             return hasRecipe(stack);
/*      */           }
/*      */ 
/*      */           
/* 2085 */           return false;
/*      */         } 
/*      */         
/* 2088 */         return hasRecipe(stack);
/*      */       } 
/*      */     } 
/* 2091 */     return false;
/*      */   }
/*      */   
/*      */   public void setJovial(int value) {
/* 2095 */     this.jovial = value;
/*      */   }
/*      */   
/*      */   public int getXpNeededForNextLevel(int experienceLevel) {
/* 2099 */     if (experienceLevel >= 30) {
/* 2100 */       return 112 + (experienceLevel - 30) * 9;
/*      */     }
/* 2102 */     return (experienceLevel >= 15) ? (37 + (experienceLevel - 15) * 5) : (7 + experienceLevel * 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getXpNeededForLevel(int level) {
/* 2109 */     int xp = 0;
/* 2110 */     for (int i = 0; i < level; i++)
/*      */     {
/* 2112 */       xp += getXpNeededForNextLevel(i);
/*      */     }
/* 2114 */     return xp + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRecipeUsed(@Nullable Recipe<?> recipe) {
/* 2120 */     if (recipe != null) {
/* 2121 */       ResourceLocation resourcelocation = recipe.getId();
/* 2122 */       float xpRecipe = ((AbstractCookingRecipe)recipe).getExperience();
/* 2123 */       if ((this.recipes.getInt(resourcelocation) + 1) * xpRecipe <= (getXpNeededForLevel(((Integer)Config.recipeMaxXPLevel.get()).intValue()) + 1))
/*      */       {
/* 2125 */         this.recipes.addTo(resourcelocation, 1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Recipe<?> getRecipeUsed() {
/* 2134 */     return null;
/*      */   }
/*      */   
/*      */   public void unlockRecipes(ServerPlayer player) {
/* 2138 */     List<Recipe<?>> list = grantStoredRecipeExperience(player.serverLevel(), player.position());
/* 2139 */     player.awardRecipes(list);
/* 2140 */     this.recipes.clear();
/*      */   }
/*      */   
/*      */   public List<Recipe<?>> grantStoredRecipeExperience(ServerLevel level, Vec3 worldPosition) {
/* 2144 */     List<Recipe<?>> list = Lists.newArrayList();
/*      */     
/* 2146 */     for (ObjectIterator<Object2IntMap.Entry<ResourceLocation>> objectIterator = this.recipes.object2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Object2IntMap.Entry<ResourceLocation> entry = objectIterator.next();
/* 2147 */       level.getRecipeManager().byKey((ResourceLocation)entry.getKey()).ifPresent(h -> {
/*      */             list.add(h);
/*      */             
/*      */             splitAndSpawnExperience(level, worldPosition, entry.getIntValue(), ((AbstractCookingRecipe)h).getExperience());
/*      */           }); }
/*      */ 
/*      */     
/* 2154 */     return list;
/*      */   }
/*      */   
/*      */   private static void splitAndSpawnExperience(ServerLevel level, Vec3 worldPosition, int craftedAmount, float experience) {
/* 2158 */     int i = Mth.floor(craftedAmount * experience);
/* 2159 */     float f = Mth.frac(craftedAmount * experience);
/* 2160 */     if (f != 0.0F && Math.random() < f) {
/* 2161 */       i++;
/*      */     }
/* 2163 */     ExperienceOrb.award(level, worldPosition, i);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillStackedContents(StackedContents helper) {
/* 2169 */     for (ItemStack itemstack : this.inventory) {
/* 2170 */       helper.accountStack(itemstack);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean doesNeedUpdateSend() {
/* 2176 */     return !Arrays.equals(this.provides, this.lastProvides);
/*      */   }
/*      */   
/*      */   public void onUpdateSent() {
/* 2180 */     System.arraycopy(this.provides, 0, this.lastProvides, 0, this.provides.length);
/* 2181 */     this.level.updateNeighborsAt(this.worldPosition, getBlockState().getBlock());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void placeConfig() {
/* 2187 */     if (this.furnaceSettings != null) {
/* 2188 */       this.furnaceSettings.set(0, 2);
/* 2189 */       this.furnaceSettings.set(1, 1);
/* 2190 */       for (Direction dir : Direction.values()) {
/* 2191 */         if (dir != Direction.DOWN && dir != Direction.UP) {
/* 2192 */           this.furnaceSettings.set(dir.ordinal(), 4);
/*      */         }
/*      */       } 
/* 2195 */       LevelForgeHooks.markAndNotifyBlock(this.level,this.worldPosition, this.level.getChunkAt(this.worldPosition), this.level.getBlockState(this.worldPosition).getBlock().defaultBlockState(), this.level.getBlockState(this.worldPosition), 3, 3);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isGenerator() {
/* 2201 */     return (this.currentAugment[2] == 2);
/*      */   }
/*      */   
/*      */   public boolean isFactory() {
/* 2205 */     return (this.currentAugment[2] == 1);
/*      */   }
/*      */   
/*      */   public boolean isFurnace() {
/* 2209 */     return (this.currentAugment[2] == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRemoved() {
/* 2214 */     this.energy.invalidate();
/* 2215 */     super.setRemoved();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTier() {
/* 2220 */     return 0;
/*      */   }
/*      */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\tileentity\furnaces\BlockIronFurnaceTileBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */