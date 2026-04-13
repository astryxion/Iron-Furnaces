/*    */ package ironfurnaces.blocks.furnaces;
/*    */ 
/*    */ import ironfurnaces.init.Registration;
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import ironfurnaces.tileentity.furnaces.BlockNetheriteFurnaceTile;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class BlockNetheriteFurnace extends BlockIronFurnaceBase {
/*    */   public static final String NETHERITE_FURNACE = "netherite_furnace";
/*    */   
/*    */   public BlockNetheriteFurnace(BlockBehaviour.Properties properties) {
/* 30 */     super(properties);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
/* 36 */     return createFurnaceTicker(level, type, (BlockEntityType<? extends BlockIronFurnaceTileBase>)Registration.NETHERITE_FURNACE_TILE.get());
/*    */   }
/*    */ 
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public void animateTick(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource rand) {
/* 42 */     if (((Boolean)state.getValue((Property)BlockStateProperties.LIT)).booleanValue()) {
/* 43 */       if (world.getBlockEntity(pos) == null) {
/*    */         return;
/*    */       }
/*    */       
/* 47 */       if (!(world.getBlockEntity(pos) instanceof BlockIronFurnaceTileBase)) {
/*    */         return;
/*    */       }
/*    */       
/* 51 */       BlockIronFurnaceTileBase tile = (BlockIronFurnaceTileBase)world.getBlockEntity(pos);
/* 52 */       if (tile.getItem(3).getItem() == Registration.SMOKING_AUGMENT.get()) {
/* 53 */         super.animateTick(state, world, pos, rand);
/* 54 */       } else if (tile.getItem(3).getItem() == Registration.BLASTING_AUGMENT.get()) {
/* 55 */         super.animateTick(state, world, pos, rand);
/*    */       }
/*    */       else {
/*    */         
/* 59 */         double d0 = pos.getX() + 0.5D;
/* 60 */         double d1 = pos.getY();
/* 61 */         double d2 = pos.getZ() + 0.5D;
/* 62 */         if (rand.nextDouble() < 0.1D) {
/* 63 */           world.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*    */         }
/*    */         
/* 66 */         Direction direction = (Direction)state.getValue((Property)BlockStateProperties.HORIZONTAL_FACING);
/* 67 */         Direction.Axis direction$axis = direction.getAxis();
/* 68 */         double d3 = 0.52D;
/* 69 */         double d4 = rand.nextDouble() * 0.6D - 0.3D;
/* 70 */         double d5 = (direction$axis == Direction.Axis.X) ? (direction.getStepX() * 0.52D) : d4;
/* 71 */         double d6 = rand.nextDouble() * 6.0D / 16.0D;
/* 72 */         double d7 = (direction$axis == Direction.Axis.Z) ? (direction.getStepZ() * 0.52D) : d4;
/* 73 */         world.addParticle((ParticleOptions)ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
/* 74 */         world.addParticle((ParticleOptions)ParticleTypes.SOUL_FIRE_FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
/* 82 */     return (BlockEntity)new BlockNetheriteFurnaceTile(p_153215_, p_153216_);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\blocks\furnaces\BlockNetheriteFurnace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */