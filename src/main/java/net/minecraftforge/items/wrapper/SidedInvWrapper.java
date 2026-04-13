package net.minecraftforge.items.wrapper;

import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public final class SidedInvWrapper {
  private SidedInvWrapper() {}

  @SuppressWarnings("unchecked")
  public static LazyOptional<? extends IItemHandler>[] create(WorldlyContainer inv, Direction[] sides) {
    LazyOptional<? extends IItemHandler>[] arr = new LazyOptional[sides.length];
    for (int i = 0; i < sides.length; i++) {
      arr[i] = LazyOptional.of(() -> (IItemHandler) new InvWrapper(inv));
    }
    return arr;
  }
}
