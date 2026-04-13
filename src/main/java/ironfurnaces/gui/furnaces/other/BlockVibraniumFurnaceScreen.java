/*    */ package ironfurnaces.gui.furnaces.other;
/*    */ 
/*    */ import ironfurnaces.container.furnaces.BlockIronFurnaceContainerBase;
/*    */ import ironfurnaces.container.furnaces.other.BlockVibraniumFurnaceContainer;
/*    */ import ironfurnaces.gui.furnaces.BlockIronFurnaceScreenBase;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ 
/*    */ @OnlyIn(Dist.CLIENT)
/*    */ public class BlockVibraniumFurnaceScreen
/*    */   extends BlockIronFurnaceScreenBase<BlockVibraniumFurnaceContainer>
/*    */ {
/*    */   public BlockVibraniumFurnaceScreen(BlockVibraniumFurnaceContainer container, Inventory inv, Component name) {
/* 16 */     super(container, inv, name);
/* 17 */     this.GUI = GUI_VIB;
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\gui\furnaces\other\BlockVibraniumFurnaceScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */