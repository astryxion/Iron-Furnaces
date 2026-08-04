/*     */ package ironfurnaces.blocks.furnaces;
/*     */ import ironfurnaces.init.Registration;
/*     */ import ironfurnaces.tileentity.furnaces.BlockCrystalFurnaceTile;
/*     */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.SimpleWaterloggedBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class BlockCrystalFurnace extends BlockIronFurnaceBase implements SimpleWaterloggedBlock {
/*  34 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED; public static final String CRYSTAL_FURNACE = "crystal_furnace";
/*     */   
/*     */   public BlockCrystalFurnace(BlockBehaviour.Properties properties) {
/*  37 */     super(properties);
/*  38 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)defaultBlockState().setValue((Property)BlockStateProperties.LIT, Boolean.valueOf(false))).setValue((Property)TYPE, Integer.valueOf(0))).setValue((Property)JOVIAL, Integer.valueOf(0))).setValue((Property)WATERLOGGED, Boolean.FALSE));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
/*  44 */     return createFurnaceTicker(level, type, (BlockEntityType<? extends BlockIronFurnaceTileBase>)Registration.CRYSTAL_FURNACE_TILE.get());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
/*  49 */     FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
/*  50 */     return (BlockState)((BlockState)defaultBlockState().setValue((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)ctx.getHorizontalDirection().getOpposite())).setValue((Property)WATERLOGGED, Boolean.valueOf((fluidState.getType() == Fluids.WATER)));
/*     */   }
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public void animateTick(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource rand) {
/*     */     BlockIronFurnaceTileBase tile;
/*  56 */     double d0 = pos.getX() + 0.5D;
/*  57 */     double d1 = pos.getY();
/*  58 */     double d2 = pos.getZ() + 0.5D;
/*     */     
/*  60 */     Direction direction = (Direction)state.getValue((Property)BlockStateProperties.HORIZONTAL_FACING);
/*  61 */     Direction.Axis direction$axis = direction.getAxis();
/*  62 */     double d3 = 0.52D;
/*  63 */     double d4 = rand.nextDouble() * 0.6D - 0.3D;
/*  64 */     double d5 = (direction$axis == Direction.Axis.X) ? (direction.getStepX() * 0.52D) : d4;
/*  65 */     double d6 = rand.nextDouble() * 6.0D / 16.0D;
/*  66 */     double d7 = (direction$axis == Direction.Axis.Z) ? (direction.getStepZ() * 0.52D) : d4;
/*  67 */     world.addParticle((ParticleOptions)ParticleTypes.PORTAL, d0 + d5, d1 + d6 - 0.5D, d2 + d7, 0.0D, 0.0D, 0.0D);
/*  68 */     world.addParticle((ParticleOptions)ParticleTypes.PORTAL, d0 + d5, d1 + d6 - 0.5D, d2 + d7, 0.0D, 0.0D, 0.0D);
/*     */     
/*  70 */     if (world.getBlockEntity(pos) == null) {
/*     */       return;
/*     */     }
/*     */     
/*  74 */     BlockEntity blockEntity = world.getBlockEntity(pos); if (blockEntity instanceof BlockIronFurnaceTileBase) { tile = (BlockIronFurnaceTileBase)blockEntity; }
/*     */     else
/*     */     { return; }
/*     */     
/*  78 */     if (tile.getItem(3).getItem() == Registration.SMOKING_AUGMENT.get()) {
/*  79 */       double lvt_5_1_ = pos.getX() + 0.5D;
/*  80 */       double lvt_7_1_ = pos.getY();
/*  81 */       double lvt_9_1_ = pos.getZ() + 0.5D;
/*     */       
/*  83 */       world.addParticle((ParticleOptions)ParticleTypes.PORTAL, lvt_5_1_, lvt_7_1_ + 1.1D, lvt_9_1_, 0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */     
/*  86 */     super.animateTick(state, world, pos, rand);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public FluidState getFluidState(BlockState state) {
/*  91 */     return ((Boolean)state.getValue((Property)WATERLOGGED)).booleanValue() ? Fluids.WATER.getSource(false) : super.getFluidState(state);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
/*  96 */     if (((Boolean)stateIn.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  97 */       worldIn.scheduleTick(currentPos, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)worldIn));
/*     */     }
/*  99 */     return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
/* 104 */     super.createBlockStateDefinition(builder.add(new Property[] { (Property)WATERLOGGED }));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity newBlockEntity(@NotNull BlockPos p_153215_, @NotNull BlockState p_153216_) {
/* 110 */     return (BlockEntity)new BlockCrystalFurnaceTile(p_153215_, p_153216_);
/*     */   }
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\blocks\furnaces\BlockCrystalFurnace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */