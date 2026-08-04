package net.minecraftforge.common.capabilities;

import net.minecraft.core.Direction;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

public interface ICapabilityProvider {
  default <T> LazyOptional<T> getCapability(Capability<T> cap) {
    return getCapability(cap, null);
  }

  <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side);
}
