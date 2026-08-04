/*    */ package ironfurnaces.container.furnaces.other;
/*    */ 
/*    */ import ironfurnaces.container.furnaces.BlockIronFurnaceContainerBase;
/*    */ import ironfurnaces.init.Registration;
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.MenuType;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class BlockUnobtainiumFurnaceContainer
/*    */   extends BlockIronFurnaceContainerBase {
/*    */   public BlockUnobtainiumFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
/* 15 */     super((MenuType)Registration.UNOBTAINIUM_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
/* 16 */     this.te = (BlockIronFurnaceTileBase)world.getBlockEntity(pos);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\furnaces\other\BlockUnobtainiumFurnaceContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */