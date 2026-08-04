package net.minecraftforge.eventbus.api;

import java.util.function.Consumer;

public interface IEventBus {
  <T extends Event> void addListener(Class<T> eventType, Consumer<T> listener);

  void register(Object target);

  boolean post(Event event);

  void unregister(Object target);
}
