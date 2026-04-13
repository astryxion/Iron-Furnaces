package net.minecraftforge.items.wrapper;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

public class InvWrapper implements IItemHandlerModifiable {
  private final Container inv;

  public InvWrapper(Container inv) {
    this.inv = inv;
  }

  public Container getInv() {
    return this.inv;
  }

  @Override
  public int getSlots() {
    return this.inv.getContainerSize();
  }

  @Override
  public @NotNull ItemStack getStackInSlot(int slot) {
    return this.inv.getItem(slot);
  }

  @Override
  public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
    if (stack.isEmpty()) {
      return ItemStack.EMPTY;
    }
    ItemStack stackInSlot = this.inv.getItem(slot);
    int m;
    if (!stackInSlot.isEmpty()) {
      if (stackInSlot.getCount() >= Math.min(stackInSlot.getMaxStackSize(), getSlotLimit(slot))) {
        return stack;
      }
      if (!ItemHandlerHelper.canItemStacksStack(stack, stackInSlot)) {
        return stack;
      }
      if (!this.inv.canPlaceItem(slot, stack)) {
        return stack;
      }
      m = Math.min(stack.getMaxStackSize(), getSlotLimit(slot)) - stackInSlot.getCount();
      if (stack.getCount() <= m) {
        if (!simulate) {
          ItemStack copy = stack.copy();
          copy.grow(stackInSlot.getCount());
          this.inv.setItem(slot, copy);
          this.inv.setChanged();
        }
        return ItemStack.EMPTY;
      } else {
        stack = stack.copy();
        if (!simulate) {
          ItemStack copy = stack.copy();
          copy.setCount(m);
          copy.grow(stackInSlot.getCount());
          this.inv.setItem(slot, copy);
          this.inv.setChanged();
          stack.shrink(m);
          return stack;
        } else {
          stack.shrink(m);
          return stack;
        }
      }
    } else {
      if (!this.inv.canPlaceItem(slot, stack)) {
        return stack;
      }
      m = Math.min(stack.getMaxStackSize(), getSlotLimit(slot));
      if (m < stack.getCount()) {
        stack = stack.copy();
        if (!simulate) {
          ItemStack place = stack.copy();
          place.setCount(m);
          this.inv.setItem(slot, place);
          this.inv.setChanged();
          stack.shrink(m);
          return stack;
        } else {
          stack.shrink(m);
          return stack;
        }
      } else {
        if (!simulate) {
          this.inv.setItem(slot, stack);
          this.inv.setChanged();
        }
        return ItemStack.EMPTY;
      }
    }
  }

  @Override
  public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
    if (amount == 0) {
      return ItemStack.EMPTY;
    }
    ItemStack stackInSlot = this.inv.getItem(slot);
    if (stackInSlot.isEmpty()) {
      return ItemStack.EMPTY;
    }
    if (simulate) {
      if (stackInSlot.getCount() < amount) {
        return stackInSlot.copy();
      } else {
        ItemStack copy = stackInSlot.copy();
        copy.setCount(amount);
        return copy;
      }
    } else {
      int m = Math.min(stackInSlot.getCount(), amount);
      ItemStack decr = this.inv.removeItem(slot, m);
      this.inv.setChanged();
      return decr;
    }
  }

  @Override
  public void setStackInSlot(int slot, @NotNull ItemStack stack) {
    this.inv.setItem(slot, stack);
    this.inv.setChanged();
  }

  @Override
  public int getSlotLimit(int slot) {
    return this.inv.getMaxStackSize();
  }

  @Override
  public boolean isItemValid(int slot, @NotNull ItemStack stack) {
    return this.inv.canPlaceItem(slot, stack);
  }
}
