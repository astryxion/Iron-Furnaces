/*    */ package ironfurnaces.capability;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraftforge.common.capabilities.Capability;
/*    */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*    */ import net.minecraftforge.common.capabilities.ICapabilitySerializable;
/*    */ import net.minecraftforge.common.util.LazyOptional;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public class PlayerFurnacesListProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
/* 15 */   public PlayerFurnacesList furnacesList = new PlayerFurnacesList();
/* 16 */   private LazyOptional<PlayerFurnacesList> lazyList = LazyOptional.of(() -> this.furnacesList);
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
/* 21 */     return (cap == CapabilityPlayerFurnacesList.FURNACES_LIST) ? this.lazyList.cast() : LazyOptional.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
/* 27 */     return (cap == CapabilityPlayerFurnacesList.FURNACES_LIST) ? this.lazyList.cast() : LazyOptional.empty();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CompoundTag serializeNBT() {
/* 33 */     CompoundTag tag = new CompoundTag();
/* 34 */     CompoundTag furnaces = new CompoundTag();
/* 35 */     for (int i = 0; i < this.furnacesList.listFurances.size(); i++) {
/*    */       
/* 37 */       CompoundTag blockpos = new CompoundTag();
/* 38 */       blockpos.putInt("X", ((BlockPos)this.furnacesList.listFurances.get(i)).getX());
/* 39 */       blockpos.putInt("Y", ((BlockPos)this.furnacesList.listFurances.get(i)).getY());
/* 40 */       blockpos.putInt("Z", ((BlockPos)this.furnacesList.listFurances.get(i)).getZ());
/* 41 */       furnaces.put("furnace" + i, (Tag)blockpos);
/*    */     } 
/*    */ 
/*    */     
/* 45 */     tag.put("furnaces", (Tag)furnaces);
/* 46 */     tag.putInt("count", this.furnacesList.listFurances.size());
/* 47 */     return tag;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deserializeNBT(CompoundTag tag) {
/* 52 */     int size = tag.getInt("count");
/* 53 */     CompoundTag furances = tag.getCompound("furnaces");
/* 54 */     for (int i = 0; i < size; i++) {
/*    */       
/* 56 */       CompoundTag furance = furances.getCompound("furnace" + i);
/* 57 */       BlockPos pos = new BlockPos(furance.getInt("X"), furance.getInt("Y"), furance.getInt("Z"));
/* 58 */       this.furnacesList.listFurances.add(pos);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\capability\PlayerFurnacesListProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */