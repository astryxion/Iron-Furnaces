package net.minecraftforge.items;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ItemHandlerHelper {
  public static boolean canItemStacksStack(@NotNull ItemStack a, @NotNull ItemStack b) {
    return ItemStack.isSameItemSameTags(a, b);
  }

  @NotNull
  public static ItemStack copyStackWithSize(@NotNull ItemStack stack, int size) {
    if (size == 0) {
      return ItemStack.EMPTY;
    }
    ItemStack copy = stack.copy();
    copy.setCount(size);
    return copy;
  }

  private ItemHandlerHelper() {}
}
