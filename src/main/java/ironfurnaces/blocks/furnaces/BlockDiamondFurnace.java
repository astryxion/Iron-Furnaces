/*    */ package ironfurnaces.blocks.furnaces;
/*    */ 
/*    */ import ironfurnaces.init.Registration;
/*    */ import ironfurnaces.tileentity.furnaces.BlockDiamondFurnaceTile;
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
/*    */ public class BlockDiamondFurnace extends BlockIronFurnaceBase {
/*    */   public static final String DIAMOND_FURNACE = "diamond_furnace";
/*    */   
/*    */   public BlockDiamondFurnace(BlockBehaviour.Properties properties) {
/* 19 */     super(properties);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
/* 25 */     return (BlockEntity)new BlockDiamondFurnaceTile(p_153215_, p_153216_);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
/* 31 */     return createFurnaceTicker(level, type, (BlockEntityType<? extends BlockIronFurnaceTileBase>)Registration.DIAMOND_FURNACE_TILE.get());
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\blocks\furnaces\BlockDiamondFurnace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */