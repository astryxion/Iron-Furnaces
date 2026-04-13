/*    */ package ironfurnaces.container.furnaces;
/*    */ 
/*    */ import ironfurnaces.init.Registration;
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.MenuType;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class BlockNetheriteFurnaceContainer
/*    */   extends BlockIronFurnaceContainerBase {
/*    */   public BlockNetheriteFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
/* 14 */     super((MenuType)Registration.NETHERITE_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
/* 15 */     this.te = (BlockIronFurnaceTileBase)world.getBlockEntity(pos);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\container\furnaces\BlockNetheriteFurnaceContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */