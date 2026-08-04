package net.minecraftforge.items;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SlotItemHandler extends Slot {
  private static final Container EMPTY_INVENTORY = new SimpleContainer(0);
  private final IItemHandler itemHandler;
  private final int index;

  public SlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
    super(EMPTY_INVENTORY, index, xPosition, yPosition);
    this.itemHandler = itemHandler;
    this.index = index;
  }

  @Override
  public boolean mayPlace(@NotNull ItemStack stack) {
    if (stack.isEmpty()) {
      return false;
    }
    return this.itemHandler.isItemValid(this.index, stack);
  }

  @Override
  @NotNull
  public ItemStack getItem() {
    return this.getItemHandler().getStackInSlot(this.index);
  }

  @Override
  public void set(@NotNull ItemStack stack) {
    ((IItemHandlerModifiable) this.getItemHandler()).setStackInSlot(this.index, stack);
    this.setChanged();
  }

  @Override
  public int getMaxStackSize() {
    return this.itemHandler.getSlotLimit(this.index);
  }

  @Override
  public int getMaxStackSize(@NotNull ItemStack stack) {
    ItemStack maxAdd = stack.copy();
    int maxInput = stack.getMaxStackSize();
    maxAdd.setCount(maxInput);

    IItemHandler handler = this.getItemHandler();
    ItemStack currentStack = handler.getStackInSlot(this.index);
    if (handler instanceof IItemHandlerModifiable handlerModifiable) {

      handlerModifiable.setStackInSlot(this.index, ItemStack.EMPTY);

      ItemStack remainder = handlerModifiable.insertItem(this.index, maxAdd, true);

      handlerModifiable.setStackInSlot(this.index, currentStack);

      return maxInput - remainder.getCount();
    } else {
      ItemStack remainder = handler.insertItem(this.index, maxAdd, true);

      int current = currentStack.getCount();
      int added = maxInput - remainder.getCount();
      return current + added;
    }
  }

  @Override
  public boolean mayPickup(Player playerIn) {
    return !this.getItemHandler().extractItem(this.index, 1, true).isEmpty();
  }

  @Override
  @NotNull
  public ItemStack remove(int amount) {
    return this.getItemHandler().extractItem(this.index, amount, false);
  }

  public IItemHandler getItemHandler() {
    return this.itemHandler;
  }
}
