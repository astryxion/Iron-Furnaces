package net.minecraftforge.common;

import java.util.Map;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public final class ForgeHooks {
  private ForgeHooks() {}

  public static int getBurnTime(ItemStack stack, RecipeType<?> recipeType) {
    if (stack.isEmpty()) {
      return 0;
    }
    Map<Item, Integer> fuelTimes = AbstractFurnaceBlockEntity.getFuel();
    Integer time = fuelTimes.get(stack.getItem());
    return time != null ? time : 0;
  }

  /** Forge {@code ItemStack#getCraftingRemainingItem()} parity for vanilla 1.20.1 Item remainder. */
  public static ItemStack getCraftingRemainder(ItemStack stack) {
    Item item = stack.getItem().getCraftingRemainingItem();
    if (item == null || item == Items.AIR) {
      return ItemStack.EMPTY;
    }
    return new ItemStack(item);
  }
}
