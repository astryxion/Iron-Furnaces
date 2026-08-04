/*
 * Copyright 2025 pizzaatime and XenoMustache
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ironfurnaces.inventory;

import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SidedInvWrapper implements IItemHandlerModifiable {

    private final WorldlyContainer inventory;
    private final Direction side;
    private final int[] slotIndexes;

    public SidedInvWrapper(WorldlyContainer inventory, Direction side) {
        this.inventory = inventory;
        this.side = side;
        this.slotIndexes = inventory.getSlotsForFace(side);
    }

    @Override
    public int getSlots() {
        return slotIndexes.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getItem(slotIndexes[slot]);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        int actual = slotIndexes[slot];
        if (!inventory.canPlaceItemThroughFace(actual, stack, side)) {
            return stack;
        }
        ItemStack existing = inventory.getItem(actual);
        int limit = Math.min(inventory.getMaxStackSize(), stack.getMaxStackSize());
        if (!existing.isEmpty()) {
            if (!ItemStack.isSameItemSameComponents(stack, existing)) {
                return stack;
            }
            limit -= existing.getCount();
        }
        if (limit <= 0) {
            return stack;
        }
        boolean reachedLimit = stack.getCount() > limit;
        ItemStack toInsert;
        if (!existing.isEmpty()) {
            toInsert = stack.copyWithCount(Math.min(stack.getCount(), limit));
        } else {
            toInsert = reachedLimit ? stack.copyWithCount(limit) : stack.copy();
        }
        if (!simulate) {
            if (existing.isEmpty()) {
                inventory.setItem(actual, toInsert);
            } else {
                existing.grow(toInsert.getCount());
                inventory.setItem(actual, existing);
            }
            if (inventory instanceof BlockEntity be) {
                be.setChanged();
            }
        }
        if (reachedLimit) {
            ItemStack remainder = stack.copy();
            remainder.shrink(limit);
            return remainder;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0) {
            return ItemStack.EMPTY;
        }
        int actual = slotIndexes[slot];
        ItemStack existing = inventory.getItem(actual);
        if (!inventory.canTakeItemThroughFace(actual, existing, side)) {
            return ItemStack.EMPTY;
        }
        if (existing.isEmpty()) {
            return ItemStack.EMPTY;
        }
        int toExtract = Math.min(amount, existing.getMaxStackSize());
        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                inventory.setItem(actual, ItemStack.EMPTY);
                if (inventory instanceof BlockEntity be) {
                    be.setChanged();
                }
                return existing;
            }
            return existing.copy();
        }
        if (!simulate) {
            inventory.setItem(actual, existing.copyWithCount(existing.getCount() - toExtract));
            if (inventory instanceof BlockEntity be) {
                be.setChanged();
            }
        }
        return existing.copyWithCount(toExtract);
    }

    @Override
    public int getSlotLimit(int slot) {
        return inventory.getMaxStackSize();
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        int actual = slotIndexes[slot];
        return inventory.canPlaceItemThroughFace(actual, stack, side) && inventory.canPlaceItem(actual, stack);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        int actual = slotIndexes[slot];
        inventory.setItem(actual, stack);
        if (inventory instanceof BlockEntity be) {
            be.setChanged();
        }
    }
}
