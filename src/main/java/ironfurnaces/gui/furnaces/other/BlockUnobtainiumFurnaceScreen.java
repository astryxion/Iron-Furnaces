/*    */ package ironfurnaces.gui.furnaces.other;
/*    */ 
/*    */ import ironfurnaces.container.furnaces.BlockIronFurnaceContainerBase;
/*    */ import ironfurnaces.container.furnaces.other.BlockUnobtainiumFurnaceContainer;
/*    */ import ironfurnaces.gui.furnaces.BlockIronFurnaceScreenBase;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ 
/*    */ @OnlyIn(Dist.CLIENT)
/*    */ public class BlockUnobtainiumFurnaceScreen
/*    */   extends BlockIronFurnaceScreenBase<BlockUnobtainiumFurnaceContainer> {
/*    */   public BlockUnobtainiumFurnaceScreen(BlockUnobtainiumFurnaceContainer container, Inventory inv, Component name) {
/* 15 */     super(container, inv, name);
/* 16 */     this.GUI = GUI_UNOB;
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\gui\furnaces\other\BlockUnobtainiumFurnaceScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */