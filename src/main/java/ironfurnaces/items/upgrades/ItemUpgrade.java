/*     */ package ironfurnaces.items.upgrades;
/*     */ 
/*     */ import ironfurnaces.energy.FEnergyStorage;
/*     */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*     */ import ironfurnaces.util.FurnaceSettings;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.TooltipFlag;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraftforge.common.world.LevelForgeHooks;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ 
/*     */ 
/*     */ public class ItemUpgrade
/*     */   extends Item
/*     */ {
/*     */   private Block from;
/*     */   private Block to;
/*     */   
/*     */   public ItemUpgrade(Item.Properties properties, Block from, Block to) {
/*  37 */     super(properties);
/*  38 */     this.from = from;
/*  39 */     this.to = to;
/*     */   }
/*     */ 
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
/*  45 */     tooltip.add(Component.translatable("tooltip.ironfurnaces.upgrade_right_click").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useOn(UseOnContext ctx) {
/*  50 */     Level world = ctx.getLevel();
/*  51 */     BlockPos pos = ctx.getClickedPos();
/*  52 */     if (!world.isClientSide) {
/*  53 */       BlockEntity te = world.getBlockEntity(pos);
/*  54 */       BlockPlaceContext ctx2 = new BlockPlaceContext(ctx);
/*  55 */       if (te.getBlockState().getBlock() != this.from)
/*     */       {
/*  57 */         return InteractionResult.PASS;
/*     */       }
/*  59 */       BlockState next = (this.to.getStateForPlacement(ctx2) != Blocks.AIR.getStateForPlacement(ctx2)) ? this.to.getStateForPlacement(ctx2) : world.getBlockState(pos);
/*  60 */       if (next == world.getBlockState(pos)) {
/*  61 */         return InteractionResult.PASS;
/*     */       }
/*  63 */       if (te instanceof FurnaceBlockEntity) {
/*  64 */         FurnaceBlockEntity furnace = (FurnaceBlockEntity)te;
/*  65 */         for (int i = 0; i < 3; i++) {
/*  66 */           ItemStack stack = furnace.getItem(i);
/*  67 */           Containers.dropItemStack(furnace.getLevel(), furnace.getBlockPos().getX(), furnace.getBlockPos().getY(), furnace.getBlockPos().getZ(), stack);
/*     */         } 
/*  69 */         world.removeBlockEntity(te.getBlockPos());
/*  70 */         world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
/*  71 */         world.setBlock(pos, next, 3);
/*  72 */         LevelForgeHooks.markAndNotifyBlock(world,pos, world.getChunkAt(pos), world.getBlockState(pos).getBlock().defaultBlockState(), world.getBlockState(pos), 3, 3);
/*  73 */         BlockEntity te2 = world.getBlockEntity(pos);
/*  74 */         if (te2 instanceof BlockIronFurnaceTileBase) {
/*  75 */           ((BlockIronFurnaceTileBase)te2).placeConfig();
/*     */         }
/*     */       } 
/*  78 */       if (te instanceof BlockIronFurnaceTileBase) {
/*  79 */         FEnergyStorage energyStorage = ((BlockIronFurnaceTileBase)te).energyStorage;
/*  80 */         int[] FACTORY_COOKTIME = ((BlockIronFurnaceTileBase)te).factoryCookTime;
/*  81 */         int[] FACTORY_TOTALCOOKTIME = ((BlockIronFurnaceTileBase)te).factoryTotalCookTime;
/*  82 */         double[] usedRF = ((BlockIronFurnaceTileBase)te).usedRF;
/*  83 */         double generatorBurn = ((BlockIronFurnaceTileBase)te).generatorBurn;
/*  84 */         int generatorRecentRecipeRF = ((BlockIronFurnaceTileBase)te).generatorRecentRecipeRF;
/*  85 */         double gottenRF = ((BlockIronFurnaceTileBase)te).gottenRF;
/*  86 */         int furnaceBurnTime = ((BlockIronFurnaceTileBase)te).furnaceBurnTime;
/*  87 */         int cookTime = ((BlockIronFurnaceTileBase)te).cookTime;
/*  88 */         int totalCookTime = ((BlockIronFurnaceTileBase)te).totalCookTime;
/*  89 */         int recipesUsed = ((BlockIronFurnaceTileBase)te).recipesUsed;
/*  90 */         FurnaceSettings settings = ((BlockIronFurnaceTileBase)te).furnaceSettings;
/*  91 */         NonNullList<ItemStack> inventory = ((BlockIronFurnaceTileBase)te).inventory;
/*  92 */         world.removeBlockEntity(te.getBlockPos());
/*  93 */         world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
/*  94 */         world.setBlock(pos, next, 3);
/*  95 */         BlockEntity newTe = world.getBlockEntity(pos);
/*  96 */         if (newTe instanceof BlockIronFurnaceTileBase) {
/*     */           
/*  98 */           ((BlockIronFurnaceTileBase)newTe).energyStorage = energyStorage;
/*  99 */           ((BlockIronFurnaceTileBase)newTe).factoryCookTime = FACTORY_COOKTIME;
/* 100 */           ((BlockIronFurnaceTileBase)newTe).factoryTotalCookTime = FACTORY_TOTALCOOKTIME;
/* 101 */           ((BlockIronFurnaceTileBase)newTe).usedRF = usedRF;
/* 102 */           ((BlockIronFurnaceTileBase)newTe).generatorBurn = generatorBurn;
/* 103 */           ((BlockIronFurnaceTileBase)newTe).generatorRecentRecipeRF = generatorRecentRecipeRF;
/* 104 */           ((BlockIronFurnaceTileBase)newTe).gottenRF = gottenRF;
/* 105 */           ((BlockIronFurnaceTileBase)newTe).furnaceBurnTime = furnaceBurnTime;
/* 106 */           ((BlockIronFurnaceTileBase)newTe).cookTime = cookTime;
/* 107 */           ((BlockIronFurnaceTileBase)newTe).totalCookTime = totalCookTime;
/* 108 */           ((BlockIronFurnaceTileBase)newTe).recipesUsed = recipesUsed;
/* 109 */           ((BlockIronFurnaceTileBase)newTe).furnaceSettings = settings;
/* 110 */           ((BlockIronFurnaceTileBase)newTe).inventory = inventory;
/*     */         } 
/* 112 */         LevelForgeHooks.markAndNotifyBlock(world,pos, world.getChunkAt(pos), world.getBlockState(pos).getBlock().defaultBlockState(), world.getBlockState(pos), 3, 3);
/*     */       } 
/* 114 */       if (!ctx.getPlayer().isCreative()) {
/* 115 */         ctx.getItemInHand().shrink(1);
/*     */       }
/*     */     } 
/* 118 */     return super.useOn(ctx);
/*     */   }
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\item\\upgrades\ItemUpgrade.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */