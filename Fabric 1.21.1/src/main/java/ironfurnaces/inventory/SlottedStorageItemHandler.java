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

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.item.ItemStack;

public class SlottedStorageItemHandler implements IItemHandlerModifiable {

    private final SlottedStorage<ItemVariant> storage;

    public SlottedStorageItemHandler(SlottedStorage<ItemVariant> storage) {
        this.storage = storage;
    }

    @Override
    public int getSlots() {
        return storage.getSlotCount();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        SingleSlotStorage<ItemVariant> s = storage.getSlot(slot);
        ItemVariant r = s.getResource();
        if (r.isBlank()) {
            return ItemStack.EMPTY;
        }
        int amt = (int) Math.min(Integer.MAX_VALUE, s.getAmount());
        return r.toStack(amt);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemVariant variant = ItemVariant.of(stack);
        try (Transaction tx = Transaction.openOuter()) {
            SingleSlotStorage<ItemVariant> s = storage.getSlot(slot);
            long inserted = s.insert(variant, stack.getCount(), tx);
            if (!simulate) {
                tx.commit();
            }
            int ins = (int) inserted;
            if (ins >= stack.getCount()) {
                return ItemStack.EMPTY;
            }
            return stack.copyWithCount(stack.getCount() - ins);
        }
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0) {
            return ItemStack.EMPTY;
        }
        try (Transaction tx = Transaction.openOuter()) {
            SingleSlotStorage<ItemVariant> s = storage.getSlot(slot);
            ItemVariant r = s.getResource();
            if (r.isBlank()) {
                return ItemStack.EMPTY;
            }
            long ext = s.extract(r, amount, tx);
            if (!simulate) {
                tx.commit();
            }
            if (ext <= 0) {
                return ItemStack.EMPTY;
            }
            return r.toStack((int) Math.min(Integer.MAX_VALUE, ext));
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        SingleSlotStorage<ItemVariant> s = storage.getSlot(slot);
        ItemVariant r = s.getResource();
        if (r.isBlank()) {
            return 64;
        }
        return r.getItem().getDefaultMaxStackSize();
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return insertItem(slot, stack, true).getCount() < stack.getCount() || stack.isEmpty();
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        try (Transaction tx = Transaction.openOuter()) {
            SingleSlotStorage<ItemVariant> s = storage.getSlot(slot);
            ItemVariant current = s.getResource();
            if (!current.isBlank()) {
                s.extract(current, s.getAmount(), tx);
            }
            if (!stack.isEmpty()) {
                s.insert(ItemVariant.of(stack), stack.getCount(), tx);
            }
            tx.commit();
        }
    }
}
