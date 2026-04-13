/*     */ package ironfurnaces.blocks.furnaces;
/*     */ import ironfurnaces.Config;
/*     */ import ironfurnaces.capability.CapabilityPlayerFurnacesList;
/*     */ import ironfurnaces.capability.IPlayerFurnacesList;
/*     */ import ironfurnaces.init.Registration;
/*     */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.WorldlyContainer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.EntityBlock;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraftforge.common.capabilities.CapabilityHooks;
/*     */ import net.minecraftforge.common.world.LevelForgeHooks;
/*     */ import net.minecraftforge.network.NetworkHooks;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public abstract class BlockIronFurnaceBase extends Block implements EntityBlock {
/*  56 */   public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 2);
/*  57 */   public static final IntegerProperty JOVIAL = IntegerProperty.create("jovial", 0, 2);
/*     */   
/*     */   public BlockIronFurnaceBase(BlockBehaviour.Properties properties) {
/*  60 */     super(properties.destroyTime(3.0F));
/*  61 */     registerDefaultState((BlockState)((BlockState)((BlockState)defaultBlockState().setValue((Property)BlockStateProperties.LIT, Boolean.valueOf(false))).setValue((Property)TYPE, Integer.valueOf(0))).setValue((Property)JOVIAL, Integer.valueOf(0)));
/*     */   }
/*     */   
/*     */   public MenuProvider getMenuProvider(@NotNull BlockState p_49234_, Level p_49235_, @NotNull BlockPos p_49236_) {
/*  65 */     BlockEntity blockentity = p_49235_.getBlockEntity(p_49236_);
/*  66 */     return (blockentity instanceof MenuProvider) ? (MenuProvider)blockentity : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
/*  71 */     if (((Boolean)Config.disableLightupdates.get()).booleanValue())
/*     */     {
/*  73 */       return 0;
/*     */     }
/*  75 */     return ((Boolean)state.getValue((Property)BlockStateProperties.LIT)).booleanValue() ? 14 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
/*  80 */     return (BlockState)defaultBlockState().setValue((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)ctx.getHorizontalDirection().getOpposite());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState p_180633_3_, @Nullable LivingEntity entity, @NotNull ItemStack stack) {
/*  85 */     if (entity != null) {
/*  86 */       BlockIronFurnaceTileBase te = (BlockIronFurnaceTileBase)world.getBlockEntity(pos);
/*  87 */       if (stack.hasCustomHoverName() && 
/*  88 */         !stack.getDisplayName().getString().contains("["))
/*     */       {
/*  90 */         ((BlockIronFurnaceTileBase)Objects.<BlockIronFurnaceTileBase>requireNonNull(te)).setCustomName(stack.getDisplayName());
/*     */       }
/*     */       
/*  93 */       ((BlockIronFurnaceTileBase)Objects.requireNonNull(te)).totalCookTime = ((Integer)te.getCookTimeConfig().get()).intValue();
/*  94 */       te.placeConfig();
/*  95 */       if (entity instanceof Player) { Player player = (Player)entity;
/*     */         
/*  97 */         CapabilityHooks.get(player, CapabilityPlayerFurnacesList.FURNACES_LIST).ifPresent(h -> h.add(pos));
/*  98 */         if (te instanceof ironfurnaces.tileentity.furnaces.BlockMillionFurnaceTile)
/*     */         {
/* 100 */           te.owner = player.getUUID();
/*     */         } }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public InteractionResult use(@NotNull BlockState state, Level world, @NotNull BlockPos pos, Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult p_225533_6_) {
/* 109 */     ItemStack stack = player.getItemInHand(handIn).copy();
/* 110 */     if (world.isClientSide) {
/* 111 */       return InteractionResult.SUCCESS;
/*     */     }
/* 113 */     if (player.getItemInHand(handIn).getItem() instanceof ironfurnaces.items.augments.ItemAugment && !player.isCrouching())
/* 114 */       return interactAugment(world, pos, player, handIn, stack); 
/* 115 */     if (player.getItemInHand(handIn).getItem() instanceof ironfurnaces.items.ItemSpooky && !player.isCrouching())
/* 116 */       return interactJovial(world, pos, player, handIn, 1); 
/* 117 */     if (player.getItemInHand(handIn).getItem() instanceof ironfurnaces.items.ItemXmas && !player.isCrouching())
/* 118 */       return interactJovial(world, pos, player, handIn, 2); 
/* 119 */     if (player.getItemInHand(handIn).isEmpty() && player.isCrouching())
/* 120 */       return interactJovial(world, pos, player, handIn, 0); 
/* 121 */     if (player.getItemInHand(handIn).getItem() instanceof ironfurnaces.items.ItemFurnaceCopy && !player.isCrouching()) {
/* 122 */       return interactCopy(world, pos, player);
/*     */     }
/* 124 */     interactWith(world, pos, player);
/*     */     
/* 126 */     return InteractionResult.SUCCESS;
/*     */   }
/*     */ 
/*     */   
/*     */   private InteractionResult interactCopy(Level world, BlockPos pos, Player player) {
/* 131 */     int j = (player.getInventory()).selected;
/* 132 */     ItemStack stack = player.getInventory().getItem(j);
/* 133 */     if (!(stack.getItem() instanceof ironfurnaces.items.ItemFurnaceCopy)) {
/* 134 */       return InteractionResult.SUCCESS;
/*     */     }
/* 136 */     BlockEntity te = world.getBlockEntity(pos);
/* 137 */     if (!(te instanceof BlockIronFurnaceTileBase)) {
/* 138 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/* 141 */     int[] settings = new int[((BlockIronFurnaceTileBase)te).furnaceSettings.size()];
/* 142 */     for (int i = 0; i < ((BlockIronFurnaceTileBase)te).furnaceSettings.size(); i++)
/*     */     {
/* 144 */       settings[i] = ((BlockIronFurnaceTileBase)te).furnaceSettings.get(i);
/*     */     }
/* 146 */     stack.getOrCreateTag().putIntArray("settings", settings);
/*     */     
/* 148 */     ((BlockIronFurnaceTileBase)te).onUpdateSent();
/* 149 */     player.sendSystemMessage((Component)Component.literal("Settings copied"));
/* 150 */     return InteractionResult.SUCCESS;
/*     */   }
/*     */   private InteractionResult interactAugment(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack stack) {
/* 153 */     if (!(player.getItemInHand(handIn).getItem() instanceof ironfurnaces.items.augments.ItemAugment)) {
/* 154 */       return InteractionResult.SUCCESS;
/*     */     }
/* 156 */     BlockEntity te = world.getBlockEntity(pos);
/* 157 */     if (!(te instanceof BlockIronFurnaceTileBase)) {
/* 158 */       return InteractionResult.SUCCESS;
/*     */     }
/* 160 */     int slot = (player.getItemInHand(handIn).getItem() instanceof ironfurnaces.items.augments.ItemAugmentRed) ? 3 : ((player.getItemInHand(handIn).getItem() instanceof ironfurnaces.items.augments.ItemAugmentGreen) ? 4 : 5);
/* 161 */     if (!((WorldlyContainer)te).getItem(slot).isEmpty() && 
/* 162 */       !player.isCreative()) {
/* 163 */       world.addFreshEntity((Entity)new ItemEntity(world, pos.getX(), (pos.getY() + 1), pos.getZ(), ((WorldlyContainer)te).getItem(slot)));
/*     */     }
/*     */     
/* 166 */     ItemStack newStack = new ItemStack((ItemLike)stack.getItem(), 1);
/* 167 */     newStack.setTag(stack.getTag());
/* 168 */     ((WorldlyContainer)te).setItem(slot, newStack);
/* 169 */     world.playSound(null, te.getBlockPos(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 0.05F, 1.0F);
/* 170 */     if (!player.isCreative()) {
/* 171 */       player.getItemInHand(handIn).shrink(1);
/*     */     }
/* 173 */     ((BlockIronFurnaceTileBase)te).onUpdateSent();
/* 174 */     LevelForgeHooks.markAndNotifyBlock(((Level)Objects.<Level>requireNonNull(te.getLevel())), pos, player.level().getChunkAt(pos), te.getLevel().getBlockState(pos).getBlock().defaultBlockState(), te.getLevel().getBlockState(pos), 2, 0);
/* 175 */     return InteractionResult.SUCCESS;
/*     */   }
/*     */   private InteractionResult interactJovial(Level world, BlockPos pos, Player player, InteractionHand handIn, int jovial) {
/* 178 */     if (!(player.getItemInHand(handIn).getItem() instanceof ironfurnaces.items.ItemSpooky) && player
/* 179 */       .getItemInHand(handIn).getItem() instanceof ironfurnaces.items.ItemXmas && player
/* 180 */       .getItemInHand(handIn).isEmpty()) {
/* 181 */       return InteractionResult.SUCCESS;
/*     */     }
/* 183 */     BlockEntity te = world.getBlockEntity(pos);
/* 184 */     if (!(te instanceof BlockIronFurnaceTileBase)) {
/* 185 */       return InteractionResult.SUCCESS;
/*     */     }
/* 187 */     ((BlockIronFurnaceTileBase)te).setJovial(jovial);
/* 188 */     return InteractionResult.SUCCESS;
/*     */   }
/*     */   
/*     */   private void interactWith(Level world, BlockPos pos, Player player) {
/* 192 */     if (!world.isClientSide) {
/*     */       
/* 194 */       BlockEntity tileEntity = world.getBlockEntity(pos);
/* 195 */       if (tileEntity instanceof MenuProvider) {
/* 196 */         NetworkHooks.openScreen((ServerPlayer)player, (MenuProvider)tileEntity, tileEntity.getBlockPos());
/* 197 */         player.awardStat(Stats.INTERACT_WITH_FURNACE);
/* 198 */         if (tileEntity instanceof BlockIronFurnaceTileBase)
/*     */         {
/* 200 */           ((BlockIronFurnaceTileBase)tileEntity).furnaceSettings.set(10, 0);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public void animateTick(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource rand) {
/* 209 */     if (((Boolean)state.getValue((Property)BlockStateProperties.LIT)).booleanValue()) {
/* 210 */       BlockIronFurnaceTileBase tile; if (world.getBlockEntity(pos) == null) {
/*     */         return;
/*     */       }
/*     */       
/* 214 */       BlockEntity blockEntity = world.getBlockEntity(pos); if (blockEntity instanceof BlockIronFurnaceTileBase) { tile = (BlockIronFurnaceTileBase)blockEntity; }
/*     */       else
/*     */       { return; }
/*     */       
/* 218 */       if (((BlockIronFurnaceTileBase)Objects.<BlockIronFurnaceTileBase>requireNonNull(tile)).getItem(3).getItem() == Registration.SMOKING_AUGMENT.get()) {
/*     */         
/* 220 */         double lvt_5_1_ = pos.getX() + 0.5D;
/* 221 */         double lvt_7_1_ = pos.getY();
/* 222 */         double lvt_9_1_ = pos.getZ() + 0.5D;
/* 223 */         if (rand.nextDouble() < 0.1D) {
/* 224 */           world.playLocalSound(lvt_5_1_, lvt_7_1_, lvt_9_1_, SoundEvents.SMOKER_SMOKE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*     */         }
/*     */         
/* 227 */         world.addParticle((ParticleOptions)ParticleTypes.SMOKE, lvt_5_1_, lvt_7_1_ + 1.1D, lvt_9_1_, 0.0D, 0.0D, 0.0D);
/*     */       
/*     */       }
/* 230 */       else if (tile.getItem(3).getItem() == Registration.BLASTING_AUGMENT.get()) {
/*     */         
/* 232 */         double lvt_5_1_ = pos.getX() + 0.5D;
/* 233 */         double lvt_7_1_ = pos.getY();
/* 234 */         double lvt_9_1_ = pos.getZ() + 0.5D;
/* 235 */         if (rand.nextDouble() < 0.1D) {
/* 236 */           world.playLocalSound(lvt_5_1_, lvt_7_1_, lvt_9_1_, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*     */         }
/*     */         
/* 239 */         Direction lvt_11_1_ = (Direction)state.getValue((Property)BlockStateProperties.HORIZONTAL_FACING);
/* 240 */         Direction.Axis lvt_12_1_ = lvt_11_1_.getAxis();
/* 241 */         double lvt_13_1_ = 0.52D;
/* 242 */         double lvt_15_1_ = rand.nextDouble() * 0.6D - 0.3D;
/* 243 */         double lvt_17_1_ = (lvt_12_1_ == Direction.Axis.X) ? (lvt_11_1_.getStepX() * 0.52D) : lvt_15_1_;
/* 244 */         double lvt_19_1_ = rand.nextDouble() * 9.0D / 16.0D;
/* 245 */         double lvt_21_1_ = (lvt_12_1_ == Direction.Axis.Z) ? (lvt_11_1_.getStepZ() * 0.52D) : lvt_15_1_;
/* 246 */         world.addParticle((ParticleOptions)ParticleTypes.SMOKE, lvt_5_1_ + lvt_17_1_, lvt_7_1_ + lvt_19_1_, lvt_9_1_ + lvt_21_1_, 0.0D, 0.0D, 0.0D);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 251 */         double d0 = pos.getX() + 0.5D;
/* 252 */         double d1 = pos.getY();
/* 253 */         double d2 = pos.getZ() + 0.5D;
/* 254 */         if (rand.nextDouble() < 0.1D) {
/* 255 */           world.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*     */         }
/*     */ 
/*     */         
/* 259 */         Direction direction = (Direction)state.getValue((Property)BlockStateProperties.HORIZONTAL_FACING);
/* 260 */         Direction.Axis direction$axis = direction.getAxis();
/* 261 */         double d3 = 0.52D;
/* 262 */         double d4 = rand.nextDouble() * 0.6D - 0.3D;
/* 263 */         double d5 = (direction$axis == Direction.Axis.X) ? (direction.getStepX() * 0.52D) : d4;
/* 264 */         double d6 = rand.nextDouble() * 6.0D / 16.0D;
/* 265 */         double d7 = (direction$axis == Direction.Axis.Z) ? (direction.getStepZ() * 0.52D) : d4;
/* 266 */         world.addParticle((ParticleOptions)ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
/* 267 */         world.addParticle((ParticleOptions)ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState state, @NotNull Level world, @NotNull BlockPos pos, BlockState oldState, boolean p_196243_5_) {
/* 275 */     if (state.getBlock() != oldState.getBlock()) {
/* 276 */       BlockEntity te = world.getBlockEntity(pos);
/* 277 */       if (te instanceof BlockIronFurnaceTileBase) { BlockIronFurnaceTileBase furnace = (BlockIronFurnaceTileBase)te;
/* 278 */         if (furnace.owner != null)
/*     */         {
/* 280 */           if (world.getPlayerByUUID(furnace.owner) != null)
/*     */           {
/* 282 */             CapabilityHooks.get((Player)Objects.<Player>requireNonNull(world.getPlayerByUUID(furnace.owner)), CapabilityPlayerFurnacesList.FURNACES_LIST).ifPresent(h -> h.remove(te.getBlockPos()));
/*     */           }
/*     */         }
/* 285 */         Containers.dropContents(world, pos, (Container)furnace);
/* 286 */         furnace.grantStoredRecipeExperience((ServerLevel)world, new Vec3(pos.getX(), pos.getY(), pos.getZ()));
/* 287 */         world.updateNeighbourForOutputSignal(pos, this); }
/*     */ 
/*     */ 
/*     */       
/* 291 */       super.onRemove(state, world, pos, oldState, p_196243_5_);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getComparatorInputOverride(BlockState state, Level world, BlockPos pos) {
/* 296 */     List<Integer> slots = new ArrayList<>();
/* 297 */     if (world.getBlockEntity(pos) instanceof BlockIronFurnaceTileBase) {
/*     */ 
/*     */       
/* 300 */       BlockIronFurnaceTileBase te = (BlockIronFurnaceTileBase)world.getBlockEntity(pos);
/* 301 */       if (((BlockIronFurnaceTileBase)Objects.<BlockIronFurnaceTileBase>requireNonNull(te)).isFurnace()) slots.add(Integer.valueOf(0));  slots.add(Integer.valueOf(1)); slots.add(Integer.valueOf(2));
/* 302 */       if (te.isGenerator()) slots.add(Integer.valueOf(6)); 
/* 303 */       if (te.isFactory()) {
/*     */         
/* 305 */         int tier = te.getTier();
/* 306 */         if (tier >= 0) {
/*     */           
/* 308 */           slots.add(Integer.valueOf(9));
/* 309 */           slots.add(Integer.valueOf(10));
/* 310 */           slots.add(Integer.valueOf(15));
/* 311 */           slots.add(Integer.valueOf(16));
/* 312 */           if (tier >= 1) {
/*     */             
/* 314 */             slots.add(Integer.valueOf(8));
/* 315 */             slots.add(Integer.valueOf(11));
/* 316 */             slots.add(Integer.valueOf(14));
/* 317 */             slots.add(Integer.valueOf(17));
/* 318 */             if (tier >= 2) {
/*     */               
/* 320 */               slots.add(Integer.valueOf(7));
/* 321 */               slots.add(Integer.valueOf(12));
/* 322 */               slots.add(Integer.valueOf(13));
/* 323 */               slots.add(Integer.valueOf(18));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 329 */       return getRedstoneSignalFromContainer((Container)world.getBlockEntity(pos), slots);
/*     */     } 
/*     */     
/* 332 */     return 0;
/*     */   }
/*     */   
/*     */   public static int getRedstoneSignalFromContainer(@Nullable Container container, List<Integer> slots) {
/* 336 */     if (container == null) {
/* 337 */       return 0;
/*     */     }
/* 339 */     int i = 0;
/* 340 */     float f = 0.0F;
/*     */     
/* 342 */     for (Iterator<Integer> iterator = slots.iterator(); iterator.hasNext(); ) { int slot = ((Integer)iterator.next()).intValue();
/* 343 */       ItemStack itemstack = container.getItem(slot);
/* 344 */       if (!itemstack.isEmpty()) {
/* 345 */         f += itemstack.getCount() / Math.min(container.getMaxStackSize(), itemstack.getMaxStackSize());
/* 346 */         i++;
/*     */       }  }
/*     */ 
/*     */     
/* 350 */     f /= slots.size();
/* 351 */     return Mth.floor(f * 14.0F) + ((i > 0) ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public RenderShape getRenderShape(@NotNull BlockState p_60550_) {
/* 357 */     return RenderShape.MODEL;
/*     */   }
/*     */   @NotNull
/*     */   public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
/* 361 */     return (BlockState)p_185499_1_.setValue((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)p_185499_2_.rotate((Direction)p_185499_1_.getValue((Property)BlockStateProperties.HORIZONTAL_FACING)));
/*     */   }
/*     */   @NotNull
/*     */   public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
/* 365 */     return p_185471_1_.rotate(p_185471_2_.getRotation((Direction)p_185471_1_.getValue((Property)BlockStateProperties.HORIZONTAL_FACING)));
/*     */   }
/*     */   
/*     */   private int calculateOutput(Level worldIn, BlockPos pos, BlockState state) {
/* 369 */     BlockIronFurnaceTileBase tile = (BlockIronFurnaceTileBase)worldIn.getBlockEntity(pos);
/* 370 */     int i = getComparatorInputOverride(state, worldIn, pos);
/* 371 */     if (tile != null) {
/*     */       
/* 373 */       int j = tile.furnaceSettings.get(9);
/* 374 */       return (tile.furnaceSettings.get(8) == 4) ? Math.max(i - j, 0) : i;
/*     */     } 
/* 376 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(@NotNull BlockState p_149744_1_) {
/* 381 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSignal(@NotNull BlockState p_180656_1_, @NotNull BlockGetter p_180656_2_, @NotNull BlockPos p_180656_3_, @NotNull Direction p_180656_4_) {
/* 387 */     return getDirectSignal(p_180656_1_, p_180656_2_, p_180656_3_, p_180656_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(@NotNull BlockState blockState, BlockGetter world, @NotNull BlockPos pos, @NotNull Direction direction) {
/* 392 */     BlockIronFurnaceTileBase furnace = (BlockIronFurnaceTileBase)world.getBlockEntity(pos);
/* 393 */     if (furnace != null) {
/*     */       
/* 395 */       int mode = furnace.furnaceSettings.get(8);
/* 396 */       if (mode == 0)
/*     */       {
/* 398 */         return 0;
/*     */       }
/* 400 */       if (mode == 1)
/*     */       {
/* 402 */         return 0;
/*     */       }
/* 404 */       if (mode == 2)
/*     */       {
/* 406 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 410 */       return calculateOutput(Objects.<Level>requireNonNull(furnace.getLevel()), pos, blockState);
/*     */     } 
/*     */     
/* 413 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
/* 418 */     builder.add(new Property[] { (Property)BlockStateProperties.HORIZONTAL_FACING, (Property)BlockStateProperties.LIT, (Property)TYPE, (Property)JOVIAL });
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
/* 423 */     return (p_152134_ == p_152133_) ? (BlockEntityTicker)p_152135_ : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected static <T extends BlockEntity> BlockEntityTicker<T> createFurnaceTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<? extends BlockIronFurnaceTileBase> p_151990_) {
/* 428 */     return p_151988_.isClientSide ? null : createTickerHelper(p_151989_, p_151990_, BlockIronFurnaceTileBase::tick);
/*     */   }
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\blocks\furnaces\BlockIronFurnaceBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */