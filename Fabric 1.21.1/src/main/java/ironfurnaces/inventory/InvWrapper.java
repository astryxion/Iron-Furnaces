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

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class InvWrapper implements IItemHandlerModifiable {

    private final Container inventory;

    public InvWrapper(Container inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getSlots() {
        return inventory.getContainerSize();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getItem(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        if (!isItemValid(slot, stack)) {
            return stack;
        }
        ItemStack existing = inventory.getItem(slot);
        int limit = getStackLimit(slot, stack);
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
                inventory.setItem(slot, toInsert);
            } else {
                existing.grow(toInsert.getCount());
                inventory.setItem(slot, existing);
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
        ItemStack existing = inventory.getItem(slot);
        if (existing.isEmpty()) {
            return ItemStack.EMPTY;
        }
        int toExtract = Math.min(amount, existing.getMaxStackSize());
        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                inventory.setItem(slot, ItemStack.EMPTY);
                if (inventory instanceof BlockEntity be) {
                    be.setChanged();
                }
                return existing;
            }
            return existing.copy();
        }
        if (!simulate) {
            inventory.setItem(slot, existing.copyWithCount(existing.getCount() - toExtract));
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

    protected int getStackLimit(int slot, ItemStack stack) {
        return Math.min(getSlotLimit(slot), stack.getMaxStackSize());
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return inventory.canPlaceItem(slot, stack);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        inventory.setItem(slot, stack);
        if (inventory instanceof BlockEntity be) {
            be.setChanged();
        }
    }
}
