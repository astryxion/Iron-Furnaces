package net.minecraftforge.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class TickEvent extends Event {
  public static class PlayerTickEvent extends TickEvent {
    public final Player player;

    public PlayerTickEvent(Player player) {
      this.player = player;
    }
  }

  public static class ClientTickEvent extends TickEvent {}
}
