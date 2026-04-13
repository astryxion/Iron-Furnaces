/*    */ package ironfurnaces.init;
/*    */ 
/*    */ import net.minecraft.client.gui.screens.MenuScreens;
/*    */ import net.minecraft.world.inventory.MenuType;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(modid = "ironfurnaces", value = {Dist.CLIENT}, bus = Mod.EventBusSubscriber.Bus.MOD)
/*    */ public class ClientSetup
/*    */ {
/*    */   public static void init(FMLClientSetupEvent event) {
/* 18 */     event.enqueueWork(() -> {
/*    */           MenuScreens.register((MenuType)Registration.IRON_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.BlockIronFurnaceScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.GOLD_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.BlockGoldFurnaceScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.DIAMOND_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.BlockDiamondFurnaceScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.EMERALD_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.BlockEmeraldFurnaceScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.OBSIDIAN_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.BlockObsidianFurnaceScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.CRYSTAL_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.BlockCrystalFurnaceScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.NETHERITE_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.BlockNetheriteFurnaceScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.COPPER_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.BlockCopperFurnaceScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.SILVER_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.BlockSilverFurnaceScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.MILLION_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.BlockMillionFurnaceScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.HEATER_CONTAINER.get(), ironfurnaces.gui.BlockWirelessEnergyHeaterScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.ALLTHEMODIUM_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.other.BlockAllthemodiumFurnaceScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.VIBRANIUM_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.other.BlockVibraniumFurnaceScreen::new);
/*    */           MenuScreens.register((MenuType)Registration.UNOBTAINIUM_FURNACE_CONTAINER.get(), ironfurnaces.gui.furnaces.other.BlockUnobtainiumFurnaceScreen::new);
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\init\ClientSetup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */