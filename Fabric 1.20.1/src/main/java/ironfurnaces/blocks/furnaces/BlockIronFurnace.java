/*    */ package ironfurnaces.blocks.furnaces;
/*    */ 
/*    */ import ironfurnaces.init.Registration;
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTile;
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class BlockIronFurnace extends BlockIronFurnaceBase {
/*    */   public static final String IRON_FURNACE = "iron_furnace";
/*    */   
/*    */   public BlockIronFurnace(BlockBehaviour.Properties properties) {
/* 19 */     super(properties);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
/* 27 */     return createFurnaceTicker(level, type, (BlockEntityType<? extends BlockIronFurnaceTileBase>)Registration.IRON_FURNACE_TILE.get());
/*    */   }
/*    */   
/*    */   public BlockEntity newBlockEntity(BlockPos p_153277_, BlockState p_153278_) {
/* 31 */     return (BlockEntity)new BlockIronFurnaceTile(p_153277_, p_153278_);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\blocks\furnaces\BlockIronFurnace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */