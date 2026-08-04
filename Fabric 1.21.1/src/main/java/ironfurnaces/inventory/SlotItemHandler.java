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

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotItemHandler extends Slot {

    private static final net.minecraft.world.Container EMPTY_INVENTORY = new SimpleContainer(0);
    private final IItemHandler itemHandler;
    protected final int index;

    public SlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(EMPTY_INVENTORY, index, xPosition, yPosition);
        this.itemHandler = itemHandler;
        this.index = index;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        return itemHandler.isItemValid(index, stack);
    }

    @Override
    public ItemStack getItem() {
        return getItemHandler().getStackInSlot(index);
    }

    @Override
    public void set(ItemStack stack) {
        ((IItemHandlerModifiable) getItemHandler()).setStackInSlot(index, stack);
        setChanged();
    }

    @Override
    public int getMaxStackSize() {
        return itemHandler.getSlotLimit(index);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return Math.min(stack.getMaxStackSize(), itemHandler.getSlotLimit(index));
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        return !getItemHandler().extractItem(index, 1, true).isEmpty();
    }

    @Override
    public ItemStack remove(int amount) {
        return getItemHandler().extractItem(index, amount, false);
    }

    public IItemHandler getItemHandler() {
        return itemHandler;
    }
}
