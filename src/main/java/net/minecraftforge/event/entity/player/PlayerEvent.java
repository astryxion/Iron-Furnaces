package net.minecraftforge.event.entity.player;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

public class PlayerEvent extends Event {
  public final Player player;

  public PlayerEvent(Player player) {
    this.player = player;
  }

  public static class ItemSmeltedEvent extends PlayerEvent {
    public final ItemStack smelted;

    public ItemSmeltedEvent(Player player, ItemStack smelted) {
      super(player);
      this.smelted = smelted;
    }
  }
}
