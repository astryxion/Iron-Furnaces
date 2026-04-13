package net.minecraftforge.energy;

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
    this.energy = Math.max(0, Math.min(energy, capacity));
  }

  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {
    if (!canReceive()) {
      return 0;
    }
    int received = Math.min(this.capacity - this.energy, Math.min(this.maxReceive, maxReceive));
    if (!simulate) {
      this.energy += received;
    }
    return received;
  }

  @Override
  public int extractEnergy(int maxExtract, boolean simulate) {
    if (!canExtract()) {
      return 0;
    }
    int extracted = Math.min(this.energy, Math.min(this.maxExtract, maxExtract));
    if (!simulate) {
      this.energy -= extracted;
    }
    return extracted;
  }

  @Override
  public int getEnergyStored() {
    return this.energy;
  }

  @Override
  public int getMaxEnergyStored() {
    return this.capacity;
  }

  @Override
  public boolean canExtract() {
    return this.maxExtract > 0;
  }

  @Override
  public boolean canReceive() {
    return this.maxReceive > 0;
  }
}
