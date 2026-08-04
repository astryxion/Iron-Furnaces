/*    */ package ironfurnaces.blocks.furnaces;
/*    */ 
/*    */ import ironfurnaces.init.Registration;
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import ironfurnaces.tileentity.furnaces.BlockMillionFurnaceTile;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class BlockMillionFurnace
/*    */   extends BlockIronFurnaceBase {
/*    */   public static final String MILLION_FURNACE = "million_furnace";
/* 28 */   public static final BooleanProperty RAINBOW_GENERATING = BooleanProperty.create("rainbow");
/*    */ 
/*    */   
/*    */   public BlockMillionFurnace(BlockBehaviour.Properties properties) {
/* 32 */     super(properties);
/*    */   }
/*    */   public BlockEntity newBlockEntity(BlockPos p_153277_, BlockState p_153278_) {
/* 35 */     return (BlockEntity)new BlockMillionFurnaceTile(p_153277_, p_153278_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource rand) {
/* 40 */     if (world.getBlockEntity(pos) != null && world.getBlockEntity(pos) instanceof BlockMillionFurnaceTile)
/*    */     {
/* 42 */       if (((BlockMillionFurnaceTile)world.getBlockEntity(pos)).getItem(5).getItem() == Registration.GENERATOR_AUGMENT.get())
/*    */       {
/* 44 */         if (((Boolean)state.getValue((Property)RAINBOW_GENERATING)).booleanValue()) {
/* 45 */           for (Direction direction : Direction.values()) {
/* 46 */             if (Direction.from3DDataValue(direction.get3DDataValue()) != Direction.UP && 
/* 47 */               Direction.from3DDataValue(direction.get3DDataValue()) != Direction.DOWN) {
/* 48 */               double d0 = pos.getX() + 0.5D;
/* 49 */               double d1 = pos.getY();
/* 50 */               double d2 = pos.getZ() + 0.5D;
/* 51 */               Direction.Axis direction$axis = direction.getAxis();
/* 52 */               double d3 = 0.52D;
/* 53 */               double d4 = rand.nextDouble() * 0.6D - 0.3D;
/* 54 */               double d5 = (direction$axis == Direction.Axis.X) ? (direction.getStepX() * 0.52D) : d4;
/* 55 */               double d6 = rand.nextDouble() * 6.0D / 16.0D;
/* 56 */               double d7 = (direction$axis == Direction.Axis.Z) ? (direction.getStepZ() * 0.52D) : d4;
/*    */               
/* 58 */               for (int i = 0; i < 10; i++) {
/* 59 */                 world.addParticle((ParticleOptions)ParticleTypes.CRIT, d0 + d5, d1 + d6, d2 + d7, rand.nextGaussian() * 0.05D, 0.0D, rand.nextGaussian() * 0.05D);
/* 60 */                 world.addParticle((ParticleOptions)ParticleTypes.AMBIENT_ENTITY_EFFECT, d0 + d5, d1 + d6, d2 + d7, rand.nextGaussian() * 0.05D, 0.0D, rand.nextGaussian() * 0.05D);
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 68 */     super.animateTick(state, world, pos, rand);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
/* 75 */     return createFurnaceTicker(level, type, (BlockEntityType<? extends BlockIronFurnaceTileBase>)Registration.MILLION_FURNACE_TILE.get());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
/* 80 */     builder.add(new Property[] { (Property)BlockStateProperties.HORIZONTAL_FACING, (Property)BlockStateProperties.LIT, (Property)TYPE, (Property)JOVIAL, (Property)RAINBOW_GENERATING });
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\blocks\furnaces\BlockMillionFurnace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */