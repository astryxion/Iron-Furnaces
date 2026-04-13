/*    */ package ironfurnaces.blocks.furnaces.other;
/*    */ 
/*    */ import ironfurnaces.blocks.furnaces.BlockIronFurnaceBase;
/*    */ import ironfurnaces.init.Registration;
/*    */ import ironfurnaces.tileentity.furnaces.other.BlockAllthemodiumFurnaceTile;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class BlockAllthemodiumFurnace
/*    */   extends BlockIronFurnaceBase {
/*    */   public static final String ALLTHEMODIUM_FURNACE = "allthemodium_furnace";
/*    */   
/*    */   public BlockAllthemodiumFurnace(BlockBehaviour.Properties properties) {
/* 20 */     super(properties);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
/* 26 */     return (BlockEntity)new BlockAllthemodiumFurnaceTile(p_153215_, p_153216_);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
/* 32 */     return createFurnaceTicker(level, type, (BlockEntityType)Registration.ALLTHEMODIUM_FURNACE_TILE.get());
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\blocks\furnaces\other\BlockAllthemodiumFurnace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */