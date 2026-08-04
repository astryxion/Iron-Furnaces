/*    */ package ironfurnaces.init;
/*    */ 
/*    */ import ironfurnaces.capability.CapabilityPlayerFurnacesList;
/*    */ import ironfurnaces.capability.CapabilityPlayerShowConfig;
/*    */ import ironfurnaces.util.RainbowEnabledCondition;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
/*    */ import net.minecraftforge.common.crafting.CraftingHelper;
/*    */ import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
/*    */ public class ModSetup
/*    */ {
/* 24 */   public static final Map<Holder.Reference<Item>, Integer> SMOKING_BURNS = new HashMap<>();
/* 25 */   public static final Map<Holder.Reference<Item>, Boolean> HAS_RECIPE = new HashMap<>();
/* 26 */   public static final Map<Holder.Reference<Item>, Boolean> HAS_RECIPE_SMOKING = new HashMap<>();
/* 27 */   public static final Map<Holder.Reference<Item>, Boolean> HAS_RECIPE_BLASTING = new HashMap<>();
/*    */   
/*    */   public static void init(FMLCommonSetupEvent event) {
/* 30 */     CraftingHelper.register((IConditionSerializer)RainbowEnabledCondition.Serializer.INSTANCE);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public static void registerCapabilities(RegisterCapabilitiesEvent event) {
/* 36 */     CapabilityPlayerShowConfig.register(event);
/* 37 */     CapabilityPlayerFurnacesList.register(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\init\ModSetup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */