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

package ironfurnaces.capability;

import ironfurnaces.energy.IEnergyStorage;
import ironfurnaces.inventory.IItemHandler;
import ironfurnaces.inventory.SlottedStorageItemHandler;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class FabricCapabilityQueries {

    private FabricCapabilityQueries() {
    }

    public static IItemHandler getItemHandler(Level level, BlockPos pos, Direction side) {
        Storage<ItemVariant> st = ItemStorage.SIDED.find(level, pos, side);
        if (st == null) {
            return null;
        }
        if (st instanceof SlottedStorage<ItemVariant> slotted) {
            return new SlottedStorageItemHandler(slotted);
        }
        return null;
    }

    public static IEnergyStorage getEnergyStorage(Level level, BlockPos pos, Direction side) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BlockIronFurnaceTileBase furnace) {
            return furnace.energyStorage;
        }
        if (be instanceof BlockWirelessEnergyHeaterTile heater) {
            return heater.energyStorage;
        }
        return null;
    }
}
