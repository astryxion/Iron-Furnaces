/*
 * Copyright 2025 Astryxion
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

/**
 * Same role as NeoForge {@code SimpleEnergyHandler} for this mod: integer RF-style storage with
 * separate receive/extract caps. No third-party energy API — other mods do not attach automatically.
 */
public class FEnergyStorage {

    protected long energy;
    protected long capacity;
    protected long maxInsert;
    protected long maxExtract;

    public FEnergyStorage(int capacity) {
        this(capacity, capacity, capacity, 0);
    }

    public FEnergyStorage(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public FEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public FEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        if (capacity < 0 || maxReceive < 0 || maxExtract < 0 || energy < 0) {
            throw new IllegalArgumentException("Energy storage amounts must be non-negative");
        }
        this.capacity = capacity;
        this.maxInsert = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = Math.min(energy, capacity);
    }

    protected void onEnergyChanged(int previousAmount) {
    }

    /**
     * @return amount actually inserted (Forge / Neo-style)
     */
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (maxInsert <= 0 || maxReceive <= 0) {
            return 0;
        }
        long space = capacity - energy;
        if (space <= 0) {
            return 0;
        }
        long toReceive = Math.min(maxReceive, Math.min(maxInsert, space));
        if (toReceive <= 0) {
            return 0;
        }
        if (!simulate) {
            int prev = getEnergy();
            energy += toReceive;
            onEnergyChanged(prev);
        }
        return (int) Math.min(Integer.MAX_VALUE, toReceive);
    }

    /**
     * @return amount actually extracted
     */
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (this.maxExtract <= 0 || maxExtract <= 0) {
            return 0;
        }
        long toExtract = Math.min(maxExtract, Math.min(this.maxExtract, energy));
        if (toExtract <= 0) {
            return 0;
        }
        if (!simulate) {
            int prev = getEnergy();
            energy -= toExtract;
            onEnergyChanged(prev);
        }
        return (int) Math.min(Integer.MAX_VALUE, toExtract);
    }

    public int getEnergy() {
        return (int) Math.min(Integer.MAX_VALUE, energy);
    }

    public int getCapacityAsInt() {
        return (int) Math.min(Integer.MAX_VALUE, capacity);
    }

    public FEnergyStorage setCapacity(int capacity) {
        int previous = getEnergy();
        this.capacity = capacity;
        if (energy > capacity) {
            energy = capacity;
        }
        onEnergyChanged(previous);
        return this;
    }

    public FEnergyStorage setMaxTransfer(int maxTransfer) {
        setMaxReceive(maxTransfer);
        setMaxExtract(maxTransfer);
        return this;
    }

    public FEnergyStorage setMaxReceive(int maxReceive) {
        this.maxInsert = maxReceive;
        return this;
    }

    public FEnergyStorage setMaxExtract(int maxExtract) {
        this.maxExtract = maxExtract;
        return this;
    }

    public int getMaxReceive() {
        return (int) Math.min(Integer.MAX_VALUE, maxInsert);
    }

    public int getMaxExtract() {
        return (int) Math.min(Integer.MAX_VALUE, maxExtract);
    }

    public void setEnergy(int energy) {
        int previous = getEnergy();
        this.energy = Math.min(Math.max(energy, 0L), capacity);
        onEnergyChanged(previous);
    }

    public void setCapacityDirectly(int capacity) {
        this.capacity = capacity;
    }

    public void setEnergyDirectly(int energy) {
        this.energy = energy;
    }

    public boolean canReceiveEnergy() {
        return maxInsert > 0;
    }

    public boolean canExtractEnergy() {
        return maxExtract > 0;
    }
}
