/*    */ package ironfurnaces.gui.furnaces;
/*    */ 
/*    */ import ironfurnaces.container.furnaces.BlockNetheriteFurnaceContainer;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockNetheriteFurnaceScreen
/*    */   extends BlockIronFurnaceScreenBase<BlockNetheriteFurnaceContainer>
/*    */ {
/*    */   public BlockNetheriteFurnaceScreen(BlockNetheriteFurnaceContainer container, Inventory inv, Component name) {
/* 13 */     super(container, inv, name);
/* 14 */     this.GUI = GUI_NETHERITE;
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\gui\furnaces\BlockNetheriteFurnaceScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */