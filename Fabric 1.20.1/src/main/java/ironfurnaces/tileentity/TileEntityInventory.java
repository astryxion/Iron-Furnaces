/*     */ package ironfurnaces.tileentity;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.WorldlyContainer;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*     */ import net.minecraftforge.common.util.LazyOptional;
/*     */ import net.minecraftforge.common.world.LevelForgeHooks;
/*     */ 
/*     */ public abstract class TileEntityInventory
/*     */   extends BlockEntity
/*     */   implements ITileInventory, WorldlyContainer, MenuProvider, ICapabilityProvider {
/*     */   public NonNullList<ItemStack> inventory;
/*     */   protected Component name;
/*     */   
/*     */   public TileEntityInventory(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state, int sizeInventory) {
/*  32 */     super(tileEntityTypeIn, pos, state);
/*  33 */     this.inventory = NonNullList.withSize(sizeInventory, ItemStack.EMPTY);
/*     */   }

/*     */   @Override
/*     */   public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
/*     */     return LazyOptional.empty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleUpdateTag(CompoundTag tag) {
/*  39 */     load(tag);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/*  45 */     setChanged();
/*  46 */     return ClientboundBlockEntityDataPacket.create(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
/*  51 */     CompoundTag tag = pkt.getTag();
/*  52 */     load(tag);
/*  53 */     setChanged();
/*  54 */     LevelForgeHooks.markAndNotifyBlock(this.level, this.worldPosition, this.level.getChunkAt(this.worldPosition), this.level.getBlockState(this.worldPosition).getBlock().defaultBlockState(), this.level.getBlockState(this.worldPosition), 2, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/*  61 */     CompoundTag tag = new CompoundTag();
/*  62 */     saveAdditional(tag);
/*  63 */     return tag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomName(Component name) {
/*  68 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getName() {
/*  73 */     return (this.name != null) ? this.name : (Component)Component.translatable(IgetName());
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getSlotsForFace(Direction side) {
/*  78 */     return IgetSlotsForFace(side);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceItem(int i, ItemStack itemStack) {
/*  83 */     return IisItemValidForSlot(i, itemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
/*  88 */     return IisItemValidForSlot(i, itemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
/*  93 */     return IcanExtractItem(i, itemStack, direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/*  98 */     return this.inventory.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 103 */     for (ItemStack itemstack : this.inventory) {
/* 104 */       if (!itemstack.isEmpty()) {
/* 105 */         return false;
/*     */       }
/*     */     } 
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int slot) {
/* 113 */     return (ItemStack)this.inventory.get(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(int i, int i1) {
/* 118 */     return ContainerHelper.removeItem((List)this.inventory, i, i1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItemNoUpdate(int i) {
/* 123 */     return ContainerHelper.takeItem((List)this.inventory, i);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int index, ItemStack stack) {
/* 128 */     ItemStack itemstack = (ItemStack)this.inventory.get(index);
/* 129 */     boolean flag = (!stack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, stack));
/* 130 */     this.inventory.set(index, stack);
/* 131 */     if (stack.getCount() > getMaxStackSize()) {
/* 132 */       stack.setCount(getMaxStackSize());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 138 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(CompoundTag nbt) {
/* 146 */     super.load(nbt);
/* 147 */     this.inventory = NonNullList.withSize(this.inventory.size(), ItemStack.EMPTY);
/* 148 */     ContainerHelper.loadAllItems(nbt, this.inventory);
/* 149 */     if (nbt.contains("CustomName", 8)) {
/* 150 */       this.name = (Component)Component.Serializer.fromJson(nbt.getString("CustomName"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CompoundTag save(CompoundTag tag) {
/* 157 */     super.saveAdditional(tag);
/*     */     
/* 159 */     return tag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag nbt) {
/* 165 */     super.saveAdditional(nbt);
/* 166 */     if (this.name != null) {
/* 167 */       nbt.putString("CustomName", Component.Serializer.toJson(this.name));
/*     */     }
/* 169 */     ContainerHelper.saveAllItems(nbt, this.inventory);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player playerEntity) {
/* 174 */     if (this.level.getBlockEntity(this.worldPosition) != this) {
/* 175 */       return false;
/*     */     }
/* 177 */     return (playerEntity.distanceToSqr(this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 0.5D, this.worldPosition.getZ() + 0.5D) <= 64.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 183 */     return (this.name != null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component getCustomName() {
/* 189 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getDisplayName() {
/* 194 */     return getName();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
/* 200 */     return IcreateMenu(i, inventory, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 205 */     this.inventory.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\tileentity\TileEntityInventory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */