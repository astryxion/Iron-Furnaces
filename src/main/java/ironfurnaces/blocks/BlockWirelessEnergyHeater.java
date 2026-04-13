/*     */ package ironfurnaces.blocks;
/*     */ 
/*     */ import ironfurnaces.init.Registration;
/*     */ import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.EntityBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ /*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*     */ import net.minecraftforge.energy.IEnergyStorage;
/*     */ import net.minecraftforge.network.NetworkHooks;
/*     */ 
/*     */ public class BlockWirelessEnergyHeater extends Block implements EntityBlock {
/*     */   public static final String HEATER = "heater";
/*     */   
/*     */   public BlockWirelessEnergyHeater(BlockBehaviour.Properties properties) {
/*  35 */     super(properties);
/*  36 */     registerDefaultState(defaultBlockState());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
/*  42 */     return (BlockEntity)new BlockWirelessEnergyHeaterTile(p_153215_, p_153216_);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
/*  48 */     return createTicker(level, type, (BlockEntityType<? extends BlockWirelessEnergyHeaterTile>)Registration.HEATER_TILE.get());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
/*  53 */     return (p_152134_ == p_152133_) ? (BlockEntityTicker)p_152135_ : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<? extends BlockWirelessEnergyHeaterTile> p_151990_) {
/*  58 */     return p_151988_.isClientSide ? null : createTickerHelper(p_151989_, p_151990_, BlockWirelessEnergyHeaterTile::tick);
/*     */   }
/*     */ 
/*     */   
/*     */   @Override
  public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
/*  63 */     if (!world.isClientSide) {
/*  64 */       BlockWirelessEnergyHeaterTile te = (BlockWirelessEnergyHeaterTile)world.getBlockEntity(pos);
/*  65 */       ItemStack stack = new ItemStack((ItemLike)Registration.HEATER.get());
/*  66 */       if (te.hasCustomName()) {
/*  67 */         stack.setHoverName(te.getDisplayName());
/*     */       }
/*  69 */       if (te.getEnergy() > 0) {
/*  70 */         stack.getOrCreateTag().putInt("Energy", te.getEnergy());
/*     */       }
/*  72 */       if (!player.isCreative()) Containers.dropItemStack(world, te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ(), stack); 
/*     */     } 
/*  74 */     super.playerWillDestroy(world, pos, state, player);
/*     */   }
/*     */   
/*     */   public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
/*  78 */     if (entity != null) {
/*  79 */       BlockWirelessEnergyHeaterTile te = (BlockWirelessEnergyHeaterTile)world.getBlockEntity(pos);
/*  80 */       if (stack.hasCustomHoverName()) {
/*  81 */         te.setCustomName(stack.getDisplayName());
/*     */       }
/*  83 */       if (stack.hasTag()) {
/*  84 */         te.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(h -> h.receiveEnergy(stack.getTag().getInt("Energy"), false));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState p_225533_1_, Level world, BlockPos pos, Player player, InteractionHand p_225533_5_, BlockHitResult p_225533_6_) {
/*  93 */     if (!world.isClientSide) {
/*  94 */       interactWith(world, pos, player);
/*     */     }
/*  96 */     return InteractionResult.SUCCESS;
/*     */   }
/*     */ 
/*     */   
/*     */   private void interactWith(Level world, BlockPos pos, Player player) {
/* 101 */     BlockEntity tileEntity = world.getBlockEntity(pos);
/* 102 */     if (tileEntity instanceof MenuProvider) {
/* 103 */       NetworkHooks.openScreen((ServerPlayer)player, (MenuProvider)tileEntity, tileEntity.getBlockPos());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean p_196243_5_) {
/* 109 */     if (state.getBlock() != oldState.getBlock()) {
/* 110 */       BlockEntity te = world.getBlockEntity(pos);
/* 111 */       if (te instanceof BlockWirelessEnergyHeaterTile) {
/* 112 */         Containers.dropContents(world, pos, (Container)te);
/* 113 */         world.updateNeighbourForOutputSignal(pos, this);
/*     */       } 
/*     */       
/* 116 */       super.onRemove(state, world, pos, oldState, p_196243_5_);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\blocks\BlockWirelessEnergyHeater.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */