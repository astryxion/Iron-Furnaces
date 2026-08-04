package net.minecraftforge.common.capabilities;

import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

public final class ForgeCapabilities {
  public static final Capability<IEnergyStorage> ENERGY = new Capability<>("forge:energy");
  public static final Capability<IItemHandler> ITEM_HANDLER = new Capability<>("forge:item_handler");

  private ForgeCapabilities() {}
}
