/*    */ package ironfurnaces.util;
/*    */ 
/*    */ import ironfurnaces.capability.PlayerFurnacesListProvider;
/*    */ import ironfurnaces.capability.PlayerShowConfigProvider;
/*    */ import ironfurnaces.init.Registration;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*    */ import net.minecraftforge.event.AttachCapabilitiesEvent;
/*    */ import net.minecraftforge.event.level.ExplosionEvent;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
/*    */ public class EventHandler
/*    */ {
/*    */   @SubscribeEvent
/*    */   public static void playerEvent(AttachCapabilitiesEvent<Entity> event) {
/* 29 */     if (event.getObject() instanceof net.minecraft.world.entity.player.Player) {
/*    */       
/* 31 */       event.addCapability(new ResourceLocation("ironfurnaces", "show_config"), (ICapabilityProvider)new PlayerShowConfigProvider());
/* 32 */       event.addCapability(new ResourceLocation("ironfurnaces", "furnaces_list"), (ICapabilityProvider)new PlayerFurnacesListProvider());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public static void explosionEvent(ExplosionEvent event) {
/* 39 */     List<BlockPos> list = event.getExplosion().getToBlow();
/* 40 */     for (BlockPos pos : list) {
/*    */       
/* 42 */       Level world = event.getLevel();
/* 43 */       if (world.getBlockEntity(pos) instanceof ironfurnaces.tileentity.furnaces.BlockMillionFurnaceTile) {
/*    */         
/* 45 */         event.getExplosion().getToBlow().remove(pos);
/* 46 */         world.removeBlockEntity(pos);
/* 47 */         world.removeBlock(pos, false);
/*    */         
/* 49 */         world.addFreshEntity((Entity)new ItemEntity(world, pos.getX(), (pos.getY() + 6.0F), pos.getZ(), new ItemStack((ItemLike)Registration.RAINBOW_COAL.get())));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnace\\util\EventHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */