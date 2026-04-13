/*    */ package ironfurnaces.capability;
/*    */ 
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
/*    */ public class PlayerShowConfigProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
/* 14 */   public PlayerShowConfig config = new PlayerShowConfig(0);
/* 15 */   private LazyOptional<PlayerShowConfig> lazyConfig = LazyOptional.of(() -> this.config);
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
/* 20 */     return (cap == CapabilityPlayerShowConfig.CONFIG) ? this.lazyConfig.cast() : LazyOptional.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
/* 26 */     return (cap == CapabilityPlayerShowConfig.CONFIG) ? this.lazyConfig.cast() : LazyOptional.empty();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CompoundTag serializeNBT() {
/* 32 */     CompoundTag tag = new CompoundTag();
/* 33 */     tag.putInt("show", this.config.value);
/* 34 */     return tag;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deserializeNBT(CompoundTag nbt) {
/* 39 */     this.config.value = nbt.getInt("show");
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\capability\PlayerShowConfigProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */