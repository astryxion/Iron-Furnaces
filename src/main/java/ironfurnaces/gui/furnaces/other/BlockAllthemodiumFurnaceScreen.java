/*    */ package ironfurnaces.gui.furnaces.other;
/*    */ 
/*    */ import ironfurnaces.container.furnaces.BlockIronFurnaceContainerBase;
/*    */ import ironfurnaces.container.furnaces.other.BlockAllthemodiumFurnaceContainer;
/*    */ import ironfurnaces.gui.furnaces.BlockIronFurnaceScreenBase;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ 
/*    */ 
/*    */ 
/*    */ @OnlyIn(Dist.CLIENT)
/*    */ public class BlockAllthemodiumFurnaceScreen
/*    */   extends BlockIronFurnaceScreenBase<BlockAllthemodiumFurnaceContainer>
/*    */ {
/*    */   public BlockAllthemodiumFurnaceScreen(BlockAllthemodiumFurnaceContainer container, Inventory inv, Component name) {
/* 18 */     super(container, inv, name);
/* 19 */     this.GUI = GUI_ATM;
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\gui\furnaces\other\BlockAllthemodiumFurnaceScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */