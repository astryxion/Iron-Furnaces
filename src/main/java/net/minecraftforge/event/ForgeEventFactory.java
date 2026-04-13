package net.minecraftforge.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

public final class ForgeEventFactory {
  private ForgeEventFactory() {}

  public static void firePlayerSmeltedEvent(Player player, ItemStack stack) {
    MinecraftForge.EVENT_BUS.post(new PlayerEvent.ItemSmeltedEvent(player, stack));
  }
}
