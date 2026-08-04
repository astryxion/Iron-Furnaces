package net.minecraftforge.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public final class ForgeRegistries {
  public static final ItemsWrapper ITEMS = new ItemsWrapper();

  private ForgeRegistries() {}

  public static final class ItemsWrapper {
    public Holder.Reference<Item> getDelegateOrThrow(Item item) {
      return BuiltInRegistries.ITEM.getResourceKey(item)
          .map(BuiltInRegistries.ITEM::getHolderOrThrow)
          .orElseThrow();
    }
  }
}
