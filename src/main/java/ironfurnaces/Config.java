/*     */ package ironfurnaces;
/*     */ 
/*     */ import com.electronwill.nightconfig.core.CommentedConfig;
/*     */ import com.electronwill.nightconfig.core.file.CommentedFileConfig;
/*     */ import com.electronwill.nightconfig.core.io.WritingMode;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import ironfurnaces.init.Registration;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Path;
/*     */ import java.util.UUID;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraftforge.common.ForgeConfigSpec;
/*     */ import net.minecraftforge.event.TickEvent;
/*     */ import net.minecraftforge.event.level.LevelEvent;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ import net.minecraftforge.fml.event.config.ModConfigEvent;
/*     */ import net.minecraftforge.fml.loading.FMLPaths;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @EventBusSubscriber
/*     */ public class Config
/*     */ {
/*     */   private static boolean run = true;
/*     */   public static final String CATEGORY_GENERAL = "general";
/*     */   public static final String CATEGORY_FURNACE = "furnaces";
/*     */   public static final String CATEGORY_MODDED_FURNACE = "modded_furnaces";
/*     */   public static final String CATEGORY_JEI = "jei";
/*     */   public static final String CATEGORY_UPDATES = "updates";
/*     */   public static final String CATEGORY_MISC = "misc";
/*     */   public static ForgeConfigSpec COMMON_CONFIG;
/*     */   public static ForgeConfigSpec CLIENT_CONFIG;
/*     */   public static ForgeConfigSpec.IntValue ironFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue goldFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue diamondFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue emeraldFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue obsidianFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue crystalFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue netheriteFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue copperFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue silverFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue millionFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue millionFurnacePowerToGenerate;
/*     */   public static ForgeConfigSpec.IntValue ironFurnaceGeneration;
/*     */   public static ForgeConfigSpec.IntValue goldFurnaceGeneration;
/*     */   public static ForgeConfigSpec.IntValue diamondFurnaceGeneration;
/*     */   public static ForgeConfigSpec.IntValue emeraldFurnaceGeneration;
/*     */   public static ForgeConfigSpec.IntValue obsidianFurnaceGeneration;
/*     */   public static ForgeConfigSpec.IntValue crystalFurnaceGeneration;
/*     */   public static ForgeConfigSpec.IntValue netheriteFurnaceGeneration;
/*     */   public static ForgeConfigSpec.IntValue copperFurnaceGeneration;
/*     */   public static ForgeConfigSpec.IntValue silverFurnaceGeneration;
/*     */   public static ForgeConfigSpec.IntValue millionFurnaceGeneration;
/*     */   public static ForgeConfigSpec.IntValue furnaceEnergyCapacityTier0;
/*     */   public static ForgeConfigSpec.IntValue furnaceEnergyCapacityTier1;
/*     */   public static ForgeConfigSpec.IntValue furnaceEnergyCapacityTier2;
/*     */   public static ForgeConfigSpec.IntValue ironFurnaceTier;
/*     */   public static ForgeConfigSpec.IntValue goldFurnaceTier;
/*     */   public static ForgeConfigSpec.IntValue diamondFurnaceTier;
/*     */   public static ForgeConfigSpec.IntValue emeraldFurnaceTier;
/*     */   public static ForgeConfigSpec.IntValue obsidianFurnaceTier;
/*     */   public static ForgeConfigSpec.IntValue crystalFurnaceTier;
/*     */   public static ForgeConfigSpec.IntValue netheriteFurnaceTier;
/*     */   public static ForgeConfigSpec.IntValue copperFurnaceTier;
/*     */   public static ForgeConfigSpec.IntValue silverFurnaceTier;
/*     */   public static ForgeConfigSpec.IntValue millionFurnaceTier;
/*     */   public static ForgeConfigSpec.IntValue recipeMaxXPLevel;
/*     */   public static ForgeConfigSpec.BooleanValue enableJeiPlugin;
/*     */   public static ForgeConfigSpec.BooleanValue enableJeiCatalysts;
/*     */   public static ForgeConfigSpec.BooleanValue enableJeiClickArea;
/*     */   public static ForgeConfigSpec.BooleanValue checkUpdates;
/*     */   public static ForgeConfigSpec.BooleanValue enableRainbowContent;
/*     */   public static ForgeConfigSpec.BooleanValue showErrors;
/*     */   public static ForgeConfigSpec.BooleanValue disableWebContent;
/*     */   public static ForgeConfigSpec.BooleanValue disableLightupdates;
/*     */   public static ForgeConfigSpec.IntValue cache_capacity;
/*     */   public static ForgeConfigSpec.IntValue vibraniumFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue unobtainiumFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue allthemodiumFurnaceSpeed;
/*     */   public static ForgeConfigSpec.IntValue vibraniumFurnaceSmeltMult;
/*     */   public static ForgeConfigSpec.IntValue unobtainiumFurnaceSmeltMult;
/*     */   public static ForgeConfigSpec.IntValue allthemodiumFurnaceSmeltMult;
/*     */   public static ForgeConfigSpec.IntValue allthemodiumGeneration;
/*     */   public static ForgeConfigSpec.IntValue vibraniumGeneration;
/*     */   public static ForgeConfigSpec.IntValue unobtainiumGeneration;
/*     */   public static ForgeConfigSpec.IntValue allthemodiumFurnaceTier;
/*     */   public static ForgeConfigSpec.IntValue vibraniumFurnaceTier;
/*     */   public static ForgeConfigSpec.IntValue unobtainiumFurnaceTier;
/*     */   
/*     */   static {
/* 123 */     ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
/* 124 */     ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
/*     */     
/* 126 */     CLIENT_BUILDER.comment("Settings").push("general");
/* 127 */     CLIENT_BUILDER.pop();
/*     */     
/* 129 */     CLIENT_BUILDER.comment("Furnace Settings").push("furnaces");
/*     */     
/* 131 */     setupFurnacesConfig(COMMON_BUILDER, CLIENT_BUILDER);
/* 132 */     setupGenerationConfig(COMMON_BUILDER, CLIENT_BUILDER);
/*     */     
/* 134 */     CLIENT_BUILDER.pop();
/*     */     
/* 136 */     CLIENT_BUILDER.comment("Modded Furnace Settings").push("modded_furnaces");
/*     */     
/* 138 */     setupModdedFurnacesConfig(COMMON_BUILDER, CLIENT_BUILDER);
/*     */     
/* 140 */     CLIENT_BUILDER.pop();
/*     */     
/* 142 */     CLIENT_BUILDER.comment("JEI Settings").push("jei");
/*     */     
/* 144 */     setupJEIConfig(COMMON_BUILDER, CLIENT_BUILDER);
/*     */     
/* 146 */     CLIENT_BUILDER.pop();
/*     */ 
/*     */     
/* 149 */     CLIENT_BUILDER.comment("Misc").push("misc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     enableRainbowContent = CLIENT_BUILDER.comment(" Enable or disable the Rainbow Content").define("misc.rainbow", true);
/*     */ 
/*     */     
/* 157 */     showErrors = CLIENT_BUILDER.comment(" Show furnace settings errors in chat, used for debugging").define("misc.errors", false);
/*     */ 
/*     */     
/* 160 */     disableWebContent = CLIENT_BUILDER.comment(" Enable or disable version checking and player identification through the web, true = disabled, if your server is using firewall software you might want to disable this").define("misc.web", false);
/*     */ 
/*     */     
/* 163 */     disableLightupdates = CLIENT_BUILDER.comment(" Enable or disable light-updates, furances will no longer emit light, true = disable").define("misc.lightupdates", false);
/*     */ 
/*     */     
/* 166 */     CLIENT_BUILDER.pop();
/*     */     
/* 168 */     CLIENT_BUILDER.comment("Update Checker Settings").push("updates");
/*     */     
/* 170 */     setupUpdatesConfig(COMMON_BUILDER, CLIENT_BUILDER);
/*     */     
/* 172 */     CLIENT_BUILDER.pop();
/*     */ 
/*     */     
/* 175 */     COMMON_CONFIG = COMMON_BUILDER.build();
/* 176 */     CLIENT_CONFIG = CLIENT_BUILDER.build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setupGenerationConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
/* 182 */     ironFurnaceGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 40").defineInRange("iron_furnace.generation", 40, 1, 100000);
/*     */ 
/*     */     
/* 185 */     goldFurnaceGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 160").defineInRange("gold_furnace.generation", 160, 1, 100000);
/*     */ 
/*     */     
/* 188 */     diamondFurnaceGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 240").defineInRange("diamond_furnace.generation", 240, 1, 100000);
/*     */ 
/*     */     
/* 191 */     emeraldFurnaceGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 320").defineInRange("emerald_furnace.generation", 320, 1, 100000);
/*     */ 
/*     */     
/* 194 */     obsidianFurnaceGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 500").defineInRange("obsidian_furnace.generation", 500, 1, 100000);
/*     */ 
/*     */     
/* 197 */     crystalFurnaceGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 360").defineInRange("crystal_furnace.generation", 360, 1, 100000);
/*     */ 
/*     */     
/* 200 */     netheriteFurnaceGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 1000").defineInRange("netherite_furnace.generation", 1000, 1, 100000);
/*     */ 
/*     */     
/* 203 */     millionFurnaceGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 2000").defineInRange("rainbow_furnace.generation", 2000, 1, 100000);
/*     */ 
/*     */     
/* 206 */     copperFurnaceGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 40").defineInRange("copper_furnace.generation", 40, 1, 100000);
/*     */ 
/*     */     
/* 209 */     silverFurnaceGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 100").defineInRange("silver_furnace.generation", 100, 1, 100000);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setupFurnacesConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
/* 218 */     furnaceEnergyCapacityTier0 = CLIENT_BUILDER.comment(" How much energy can be stored in tier 0 furnaces.\n Default: 80 000").defineInRange("energy.tier_0", 80000, 4000, 2147483647);
/*     */ 
/*     */ 
/*     */     
/* 222 */     furnaceEnergyCapacityTier1 = CLIENT_BUILDER.comment(" How much energy can be stored in tier 1 furnaces.\n Default: 200 000").defineInRange("energy.tier_1", 200000, 4000, 2147483647);
/*     */ 
/*     */ 
/*     */     
/* 226 */     furnaceEnergyCapacityTier2 = CLIENT_BUILDER.comment(" How much energy can be stored in tier 2 furnaces.\n Default: 1 000 000").defineInRange("energy.tier_2", 1000000, 4000, 2147483647);
/*     */ 
/*     */ 
/*     */     
/* 230 */     ironFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 0").defineInRange("iron_furnace.tier", 0, 0, 2);
/*     */ 
/*     */ 
/*     */     
/* 234 */     copperFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 0").defineInRange("copper_furnace.tier", 0, 0, 2);
/*     */ 
/*     */ 
/*     */     
/* 238 */     goldFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 1").defineInRange("gold_furnace.tier", 1, 0, 2);
/*     */ 
/*     */ 
/*     */     
/* 242 */     diamondFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 1").defineInRange("diamond_furnace.tier", 2, 0, 2);
/*     */ 
/*     */ 
/*     */     
/* 246 */     emeraldFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 1").defineInRange("emerald_furnace.tier", 2, 0, 2);
/*     */ 
/*     */ 
/*     */     
/* 250 */     silverFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 1").defineInRange("silver_furnace.tier", 1, 0, 2);
/*     */ 
/*     */ 
/*     */     
/* 254 */     crystalFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 2").defineInRange("crystal_furnace.tier", 2, 0, 2);
/*     */ 
/*     */ 
/*     */     
/* 258 */     obsidianFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 2").defineInRange("obsidian_furnace.tier", 2, 0, 2);
/*     */ 
/*     */ 
/*     */     
/* 262 */     netheriteFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 2").defineInRange("netherite_furnace.tier", 2, 0, 2);
/*     */ 
/*     */ 
/*     */     
/* 266 */     millionFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 2").defineInRange("million_furnace.tier", 2, 0, 2);
/*     */ 
/*     */ 
/*     */     
/* 270 */     cache_capacity = CLIENT_BUILDER.comment(" The capacity of the recipe cache, higher values use more memory.\n Default: 10").defineInRange("recipe_cache", 10, 1, 100);
/*     */ 
/*     */ 
/*     */     
/* 274 */     ironFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 160").defineInRange("iron_furnace.speed", 160, 2, 72000);
/*     */ 
/*     */ 
/*     */     
/* 278 */     goldFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 120").defineInRange("gold_furnace.speed", 120, 2, 72000);
/*     */ 
/*     */ 
/*     */     
/* 282 */     diamondFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 80").defineInRange("diamond_furnace.speed", 80, 2, 72000);
/*     */ 
/*     */ 
/*     */     
/* 286 */     emeraldFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 40").defineInRange("emerald_furnace.speed", 40, 2, 72000);
/*     */ 
/*     */ 
/*     */     
/* 290 */     obsidianFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 20").defineInRange("obsidian_furnace.speed", 20, 2, 72000);
/*     */ 
/*     */ 
/*     */     
/* 294 */     crystalFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 40").defineInRange("crystal_furnace.speed", 40, 2, 72000);
/*     */ 
/*     */ 
/*     */     
/* 298 */     netheriteFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 5").defineInRange("netherite_furnace.speed", 5, 2, 72000);
/*     */ 
/*     */ 
/*     */     
/* 302 */     copperFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 180").defineInRange("copper_furnace.speed", 180, 2, 72000);
/*     */ 
/*     */ 
/*     */     
/* 306 */     silverFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 140").defineInRange("silver_furnace.speed", 140, 2, 72000);
/*     */ 
/*     */ 
/*     */     
/* 310 */     millionFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 20").defineInRange("rainbow_furnace.speed", 20, 2, 72000);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 315 */     millionFurnacePowerToGenerate = CLIENT_BUILDER.comment(" How much power the Rainbow Furnace will generate.\n Default: 50000").defineInRange("rainbow_furnace.rainbow_generation", 50000, 1, 100000000);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     recipeMaxXPLevel = CLIENT_BUILDER.comment(" How many levels of experience that can be stored in recipes stored in the furnace, after the experience stored in the recipe reaches this value (in levels) it will be voided.\n Default: 100 \n 100 levels is 30971 XP").defineInRange("recipeMaxXPLevel.level", 100, 1, 1000);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setupModdedFurnacesConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
/* 328 */     allthemodiumFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 5").defineInRange("allthemodium_furnace.speed", 5, 1, 72000);
/*     */ 
/*     */     
/* 331 */     vibraniumFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 3").defineInRange("vibranium_furnace.speed", 3, 1, 72000);
/*     */ 
/*     */     
/* 334 */     unobtainiumFurnaceSpeed = CLIENT_BUILDER.comment(" Number of ticks to complete one smelting operation.\n 200 ticks is what a regular furnace takes.\n Default: 1").defineInRange("unobtainium_furnace.speed", 1, 1, 72000);
/*     */ 
/*     */     
/* 337 */     allthemodiumFurnaceSmeltMult = CLIENT_BUILDER.comment(" Number of items that can be smelted at once. The regular furnace only smelts 1 item at once of course.\n Default: 16").defineInRange("allthemodium_furnace.mult", 16, 1, 64);
/*     */ 
/*     */     
/* 340 */     vibraniumFurnaceSmeltMult = CLIENT_BUILDER.comment(" Number of items that can be smelted at once. The regular furnace only smelts 1 item at once of course.\n Default: 32").defineInRange("vibranium_furnace.mult", 32, 1, 64);
/*     */ 
/*     */     
/* 343 */     unobtainiumFurnaceSmeltMult = CLIENT_BUILDER.comment(" Number of items that can be smelted at once. The regular furnace only smelts 1 item at once of course.\n Default: 64").defineInRange("unobtainium_furnace.mult", 64, 1, 64);
/*     */ 
/*     */     
/* 346 */     allthemodiumGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 2000").defineInRange("allthemodium_furnace.generation", 2000, 1, 100000);
/*     */ 
/*     */     
/* 349 */     vibraniumGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 3000").defineInRange("vibranium_furnace.generation", 3000, 1, 100000);
/*     */ 
/*     */     
/* 352 */     unobtainiumGeneration = CLIENT_BUILDER.comment(" How much RF to generate per tick\n Default: 5000").defineInRange("unobtainium_furnace.generation", 5000, 1, 100000);
/*     */ 
/*     */ 
/*     */     
/* 356 */     allthemodiumFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 2").defineInRange("allthemodium_furnace.tier", 2, 0, 2);
/*     */ 
/*     */ 
/*     */     
/* 360 */     vibraniumFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 2").defineInRange("vibranium_furnace.tier", 2, 0, 2);
/*     */ 
/*     */ 
/*     */     
/* 364 */     unobtainiumFurnaceTier = CLIENT_BUILDER.comment(" What tier this furnace should be.\n Default: 2").defineInRange("unobtainium_furnace.tier", 2, 0, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setupJEIConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
/* 371 */     enableJeiPlugin = CLIENT_BUILDER.comment(" Enable or disable the JeiPlugin of Iron Furnaces.").define("jei.enable_jei", true);
/*     */ 
/*     */     
/* 374 */     enableJeiCatalysts = CLIENT_BUILDER.comment(" Enable or disable the Catalysts in Jei for Iron Furnaces.").define("jei.enable_jei_catalysts", true);
/*     */ 
/*     */     
/* 377 */     enableJeiClickArea = CLIENT_BUILDER.comment(" Enable or disable the Click Area inside the GUI in all of Iron Furnaces' furnaces.").define("jei.enable_jei_click_area", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setupUpdatesConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
/* 385 */     checkUpdates = CLIENT_BUILDER.comment(" true = check for updates, false = don't check for updates.\n Default: true.").define("check_updates.updates", true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadConfig(ForgeConfigSpec spec, Path path) {
/* 390 */     IronFurnaces.LOGGER.debug("Loading config file {}", path);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 396 */     CommentedFileConfig configData = (CommentedFileConfig)CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
/*     */     
/* 398 */     IronFurnaces.LOGGER.debug("Built TOML config for {}", path.toString());
/* 399 */     configData.load();
/* 400 */     IronFurnaces.LOGGER.debug("Loaded TOML config file {}", path.toString());
/* 401 */     spec.setConfig((CommentedConfig)configData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void onLoad(ModConfigEvent.Loading configEvent) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void onReload(ModConfigEvent.Reloading configEvent) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void onWorldLoad(LevelEvent.Load event) {
/* 416 */     loadConfig(CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces-client.toml"));
/* 417 */     loadConfig(COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces.toml"));
/* 418 */     run = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void player(TickEvent.PlayerTickEvent event) {
/* 425 */     if (((Boolean)disableWebContent.get()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 430 */     if (!run) {
/*     */       return;
/*     */     }
/*     */     
/* 434 */     if (!(event.player.level()).isClientSide && 
/* 435 */       event.player.getServer().getAdvancements() != null) {
/*     */       
/* 437 */       Advancement adv = event.player.getServer().getAdvancements().getAdvancement(new ResourceLocation("ironfurnaces", "coal"));
/* 438 */       if (adv != null)
/*     */       {
/* 440 */         if (!((ServerPlayer)event.player).getAdvancements().getOrStartProgress(adv).isDone()) {
/* 441 */           Player player = getPlayer(event.player.level());
/* 442 */           if (player != null && player == event.player) {
/* 443 */             event.player.level().addFreshEntity((Entity)new ItemEntity(event.player.level(), (event.player.position()).x, (event.player.position()).y, (event.player.position()).z, new ItemStack((ItemLike)Registration.RAINBOW_COAL.get())));
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 454 */     run = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Player getPlayer(Level world) {
/* 463 */     if (((Boolean)disableWebContent.get()).booleanValue())
/*     */     {
/* 465 */       return null;
/*     */     }
/*     */     
/* 468 */     if (world == null) {
/* 469 */       return null;
/*     */     }
/*     */     try {
/* 472 */       URL newestURL = new URL("https://raw.githubusercontent.com/Qelifern/IronFurnaces/1.20.1/update/uuids.json");
/* 473 */       JsonParser jp = new JsonParser();
/* 474 */       JsonElement root = jp.parse(new InputStreamReader(newestURL.openStream()));
/* 475 */       JsonObject rootobj = root.getAsJsonObject();
/* 476 */       JsonArray array = rootobj.get("values").getAsJsonArray();
/* 477 */       for (int i = 0; i < array.size(); i++) {
/* 478 */         if (world.getPlayerByUUID(UUID.fromString(array.get(i).getAsString())) != null) {
/* 479 */           return world.getPlayerByUUID(UUID.fromString(array.get(i).getAsString()));
/*     */         }
/*     */       } 
/* 482 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 486 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\Config.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */