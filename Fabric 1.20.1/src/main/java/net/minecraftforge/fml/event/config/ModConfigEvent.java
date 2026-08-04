package net.minecraftforge.fml.event.config;

import net.minecraftforge.eventbus.api.Event;

public class ModConfigEvent extends Event {
  public static class Loading extends ModConfigEvent {}

  public static class Reloading extends ModConfigEvent {}
}
