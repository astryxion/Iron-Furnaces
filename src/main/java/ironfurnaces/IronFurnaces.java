/*    */ package ironfurnaces;

/*    */ import ironfurnaces.init.ModSetup;
/*    */ import ironfurnaces.init.Registration;
/*    */ import ironfurnaces.network.Messages;
/*    */ import ironfurnaces.update.UpdateChecker;
/*    */ import ironfurnaces.util.EventHandler;
/*    */ import net.fabricmc.api.EnvType;
/*    */ import net.fabricmc.api.ModInitializer;
/*    */ import net.fabricmc.loader.api.FabricLoader;
/*    */ import net.minecraft.world.item.CreativeModeTab;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
/*    */ import net.minecraftforge.eventbus.api.IEventBus;
/*    */ import net.minecraftforge.fml.ModBusHolder;
/*    */ import net.minecraftforge.fml.ModLoadingContext;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.config.IConfigSpec;
/*    */ import net.minecraftforge.fml.config.ModConfig;
/*    */ import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
/*    */ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
/*    */ import net.minecraftforge.fml.loading.FMLPaths;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;

/*    */ @Mod("ironfurnaces")
/*    */ @EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
/*    */ public class IronFurnaces implements ModInitializer {
/*    */   public static final String MOD_ID = "ironfurnaces";
/*    */   public static final String VERSION = "418";
/*    */   public static final String RELEASE_TYPE = "-beta";
/*    */   public static final String MC_VERSION = "1.20.1";
/*    */   public static final String GITHUB_BRANCH = "1.20.1";
/*    */   public static final Logger LOGGER = LogManager.getLogger();

/*    */   public static IEventBus MOD_EVENT_BUS;

/*    */   public static CreativeModeTab tabIronFurnaces;

/*    */   @Override
/*    */   public void onInitialize() {
/*    */     ModBusHolder.BUS = new MinecraftForge.EventBusImpl();
/*    */     MOD_EVENT_BUS = ModBusHolder.BUS;

/*    */     MinecraftForge.initFabricCallbacks();

/*    */     Messages.registerMessages("ironfurnaces_network");

/*    */     FMLJavaModLoadingContext.get()
/*    */         .getModEventBus()
/*    */         .addListener(FMLCommonSetupEvent.class, ModSetup::init);

/*    */     FMLJavaModLoadingContext.get().getModEventBus().register(ModSetup.class);

/*    */     ModLoadingContext.get()
/*    */         .registerConfig(ModConfig.Type.CLIENT, (IConfigSpec) Config.CLIENT_CONFIG);
/*    */     ModLoadingContext.get()
/*    */         .registerConfig(ModConfig.Type.SERVER, (IConfigSpec) Config.COMMON_CONFIG);

/*    */     FMLJavaModLoadingContext.get().getModEventBus().register(Registration.class);

/*    */     Registration.init();

/*    */     MinecraftForge.EVENT_BUS.register(EventHandler.class);
/*    */     MinecraftForge.EVENT_BUS.register(Config.class);

/*    */     ModBusHolder.BUS.post(new RegisterCapabilitiesEvent());
/*    */     ModBusHolder.BUS.post(new FMLCommonSetupEvent());

/*    */     Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces-client.toml"));
/*    */     Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("ironfurnaces.toml"));

/*    */     if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT
/*    */         && Config.checkUpdates.get()) {
/*    */       new UpdateChecker();
/*    */     } else if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
/*    */       LOGGER.warn(
/*    */           "You have disabled Iron Furnaces's Update Checker, to re-enable: change the value of Update Checker in .minecraft->config->ironfurnaces-client.toml to 'true'.");
/*    */     }
/*    */   }
/*    */ }
