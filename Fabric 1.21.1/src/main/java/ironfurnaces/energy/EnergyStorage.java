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

package ironfurnaces.energy;

import net.minecraft.util.Mth;

public class EnergyStorage implements IEnergyStorage {

    protected int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public EnergyStorage(int capacity) {
        this(capacity, capacity, capacity, 0);
    }

    public EnergyStorage(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public EnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public EnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = Mth.clamp(energy, 0, capacity);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive()) {
            return 0;
        }
        int received = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            energy += received;
        }
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract()) {
            return 0;
        }
        int extracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            energy -= extracted;
        }
        return extracted;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return maxReceive > 0;
    }
}
