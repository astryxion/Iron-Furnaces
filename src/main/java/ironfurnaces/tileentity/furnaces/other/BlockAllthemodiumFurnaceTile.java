/*    */ package ironfurnaces.tileentity.furnaces.other;
/*    */ import ironfurnaces.Config;
/*    */ import ironfurnaces.container.furnaces.other.BlockAllthemodiumFurnaceContainer;
/*    */ import ironfurnaces.init.Registration;
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraftforge.common.ForgeConfigSpec;
/*    */ 
/*    */ public class BlockAllthemodiumFurnaceTile extends BlockIronFurnaceTileBase {
/*    */   public BlockAllthemodiumFurnaceTile(BlockPos pos, BlockState state) {
/* 16 */     super((BlockEntityType)Registration.ALLTHEMODIUM_FURNACE_TILE.get(), pos, state);
/*    */   }
/*    */ 
/*    */   
/*    */   public ForgeConfigSpec.IntValue getCookTimeConfig() {
/* 21 */     return Config.allthemodiumFurnaceSpeed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String IgetName() {
/* 28 */     return "container.ironfurnaces.allthemodium_furnace";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
/* 34 */     return (AbstractContainerMenu)new BlockAllthemodiumFurnaceContainer(i, this.level, this.worldPosition, playerInventory, playerEntity);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTier() {
/* 40 */     return ((Integer)Config.allthemodiumFurnaceTier.get()).intValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\tileentity\furnaces\other\BlockAllthemodiumFurnaceTile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */