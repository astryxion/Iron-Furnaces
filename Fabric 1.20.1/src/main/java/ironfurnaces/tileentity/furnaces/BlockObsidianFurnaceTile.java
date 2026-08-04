/*    */ package ironfurnaces.tileentity.furnaces;
/*    */ import ironfurnaces.Config;
/*    */ import ironfurnaces.container.furnaces.BlockObsidianFurnaceContainer;
/*    */ import ironfurnaces.init.Registration;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraftforge.common.ForgeConfigSpec;
/*    */ 
/*    */ public class BlockObsidianFurnaceTile extends BlockIronFurnaceTileBase {
/*    */   public BlockObsidianFurnaceTile(BlockPos pos, BlockState state) {
/* 15 */     super((BlockEntityType)Registration.OBSIDIAN_FURNACE_TILE.get(), pos, state);
/*    */   }
/*    */ 
/*    */   
/*    */   public ForgeConfigSpec.IntValue getCookTimeConfig() {
/* 20 */     return Config.obsidianFurnaceSpeed;
/*    */   }
/*    */ 
/*    */   
/*    */   public String IgetName() {
/* 25 */     return "container.ironfurnaces.obsidian_furnace";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
/* 32 */     return (AbstractContainerMenu)new BlockObsidianFurnaceContainer(i, this.level, this.worldPosition, playerInventory, playerEntity);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTier() {
/* 37 */     return ((Integer)Config.obsidianFurnaceTier.get()).intValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\tileentity\furnaces\BlockObsidianFurnaceTile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */