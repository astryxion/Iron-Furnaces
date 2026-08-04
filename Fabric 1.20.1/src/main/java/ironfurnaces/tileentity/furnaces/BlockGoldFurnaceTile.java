/*    */ package ironfurnaces.tileentity.furnaces;
/*    */ import ironfurnaces.Config;
/*    */ import ironfurnaces.container.furnaces.BlockGoldFurnaceContainer;
/*    */ import ironfurnaces.init.Registration;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraftforge.common.ForgeConfigSpec;
/*    */ 
/*    */ public class BlockGoldFurnaceTile extends BlockIronFurnaceTileBase {
/*    */   public BlockGoldFurnaceTile(BlockPos pos, BlockState state) {
/* 15 */     super((BlockEntityType)Registration.GOLD_FURNACE_TILE.get(), pos, state);
/*    */   }
/*    */ 
/*    */   
/*    */   public ForgeConfigSpec.IntValue getCookTimeConfig() {
/* 20 */     return Config.goldFurnaceSpeed;
/*    */   }
/*    */ 
/*    */   
/*    */   public String IgetName() {
/* 25 */     return "container.ironfurnaces.gold_furnace";
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
/* 30 */     return (AbstractContainerMenu)new BlockGoldFurnaceContainer(i, this.level, this.worldPosition, playerInventory, playerEntity);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTier() {
/* 35 */     return ((Integer)Config.goldFurnaceTier.get()).intValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\tileentity\furnaces\BlockGoldFurnaceTile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */