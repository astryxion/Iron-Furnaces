/*    */ package ironfurnaces.tileentity.furnaces;
/*    */ import com.google.common.collect.Lists;
/*    */ import ironfurnaces.Config;
/*    */ import ironfurnaces.container.furnaces.BlockMillionFurnaceContainer;
/*    */ import ironfurnaces.init.Registration;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraftforge.common.ForgeConfigSpec;
/*    */ 
/*    */ public class BlockMillionFurnaceTile extends BlockIronFurnaceTileBase {
/*    */   public List<BlockIronFurnaceTileBase> furnaces;
/*    */   
/*    */   public BlockMillionFurnaceTile(BlockPos pos, BlockState state) {
/* 19 */     super(Registration.MILLION_FURNACE_TILE.get(), pos, state);
/*    */ 
/*    */     
/* 22 */     this.furnaces = Lists.newArrayList();
/* 23 */     this.furnaces_to_load = Lists.newArrayList();
/*    */   }
/*    */   public List<BlockPos> furnaces_to_load;
/*    */   public void saveAdditional(CompoundTag tag) {
/* 27 */     super.saveAdditional(tag);
/* 28 */     CompoundTag furnaces = new CompoundTag();
/* 29 */     for (int i = 0; i < this.furnaces.size(); i++) {
/*    */       
/* 31 */       CompoundTag tag2 = new CompoundTag();
/* 32 */       tag2.putInt("X", ((BlockIronFurnaceTileBase)this.furnaces.get(i)).getBlockPos().getX());
/* 33 */       tag2.putInt("Y", ((BlockIronFurnaceTileBase)this.furnaces.get(i)).getBlockPos().getY());
/* 34 */       tag2.putInt("Z", ((BlockIronFurnaceTileBase)this.furnaces.get(i)).getBlockPos().getZ());
/* 35 */       furnaces.put("Furnace" + i, (Tag)tag2);
/*    */     } 
/* 37 */     tag.put("Furnaces", (Tag)furnaces);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void load(CompoundTag tag) {
/* 43 */     super.load(tag);
/* 44 */     CompoundTag furnaces = tag.getCompound("Furnaces");
/* 45 */     for (int i = 0; i < furnaces.size(); i++) {
/*    */       
/* 47 */       CompoundTag furnace = furnaces.getCompound("Furnace" + i);
/* 48 */       this.furnaces_to_load.add(new BlockPos(furnace.getInt("X"), furnace.getInt("Y"), furnace.getInt("Z")));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ForgeConfigSpec.IntValue getCookTimeConfig() {
/* 54 */     return Config.millionFurnaceSpeed;
/*    */   }
/*    */ 
/*    */   
/*    */   public String IgetName() {
/* 59 */     return "container.ironfurnaces.million_furnace";
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
/* 64 */     return (AbstractContainerMenu)new BlockMillionFurnaceContainer(i, this.level, this.worldPosition, playerInventory, playerEntity);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTier() {
/* 69 */     return ((Integer)Config.millionFurnaceTier.get()).intValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\tileentity\furnaces\BlockMillionFurnaceTile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */