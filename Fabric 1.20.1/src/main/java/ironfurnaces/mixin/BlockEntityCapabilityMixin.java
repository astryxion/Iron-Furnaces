package ironfurnaces.mixin;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockEntity.class)
public abstract class BlockEntityCapabilityMixin implements ICapabilityProvider {
  @Override
  public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
    return LazyOptional.empty();
  }
}
