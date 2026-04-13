package net.minecraftforge.common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

public final class MinecraftForge {
  public static final IEventBus EVENT_BUS = new EventBusImpl();

  private static boolean fabricCallbacksRegistered;

  private MinecraftForge() {}

  public static synchronized void initFabricCallbacks() {
    if (fabricCallbacksRegistered) {
      return;
    }
    fabricCallbacksRegistered = true;
    ServerWorldEvents.LOAD.register(
        (server, world) -> EVENT_BUS.post(new LevelEvent.Load(world)));
    ServerTickEvents.END_WORLD_TICK.register(
        world -> {
          if (!world.isClientSide) {
            for (Player p : world.players()) {
              EVENT_BUS.post(new TickEvent.PlayerTickEvent(p));
            }
          }
        });
    ClientTickEvents.END_CLIENT_TICK.register(
        client -> EVENT_BUS.post(new TickEvent.ClientTickEvent()));
  }

  public static final class EventBusImpl implements IEventBus {
    private final List<TypedListener<?>> typedListeners = new CopyOnWriteArrayList<>();
    private final List<RegisteredHandler> handlers = new CopyOnWriteArrayList<>();
    private final Map<Object, List<RegisteredHandler>> byTarget = new IdentityHashMap<>();

    @Override
    public <T extends Event> void addListener(Class<T> eventType, Consumer<T> listener) {
      this.typedListeners.add(new TypedListener<>(eventType, listener));
    }

    @Override
    public void register(Object target) {
      Class<?> clazz = target instanceof Class<?> ? (Class<?>) target : target.getClass();
      List<RegisteredHandler> added = new ArrayList<>();
      for (Method m : collectMethods(clazz)) {
        if (!m.isAnnotationPresent(SubscribeEvent.class)) {
          continue;
        }
        if (m.getParameterCount() != 1) {
          continue;
        }
        Class<?> param = m.getParameterTypes()[0];
        if (!Event.class.isAssignableFrom(param)) {
          continue;
        }
        m.setAccessible(true);
        @Nullable Object invokeTarget = target instanceof Class<?> ? null : target;
        if (!Modifier.isStatic(m.getModifiers()) && invokeTarget == null) {
          continue;
        }
        RegisteredHandler h =
            new RegisteredHandler((Class<? extends Event>) param, m, invokeTarget);
        this.handlers.add(h);
        added.add(h);
      }
      if (!(target instanceof Class<?>)) {
        this.byTarget.computeIfAbsent(target, t -> new CopyOnWriteArrayList<>()).addAll(added);
      }
    }

    private static List<Method> collectMethods(Class<?> clazz) {
      List<Method> out = new ArrayList<>();
      Class<?> c = clazz;
      while (c != null && c != Object.class) {
        for (Method m : c.getDeclaredMethods()) {
          out.add(m);
        }
        c = c.getSuperclass();
      }
      return out;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean post(Event event) {
      for (TypedListener<?> tl : this.typedListeners) {
        if (tl.eventType.isInstance(event)) {
          ((TypedListener) tl).accept(event);
        }
      }
      for (RegisteredHandler h : this.handlers) {
        if (h.eventType.isAssignableFrom(event.getClass())) {
          h.invoke(event);
        }
      }
      return !event.isCanceled();
    }

    @Override
    public void unregister(Object target) {
      List<RegisteredHandler> mine = this.byTarget.remove(target);
      if (mine != null) {
        this.handlers.removeAll(mine);
      }
    }
  }

  private static final class TypedListener<T extends Event> {
    final Class<T> eventType;
    final Consumer<T> consumer;

    TypedListener(Class<T> eventType, Consumer<T> consumer) {
      this.eventType = eventType;
      this.consumer = consumer;
    }

    void accept(Event e) {
      this.consumer.accept(this.eventType.cast(e));
    }
  }

  private static final class RegisteredHandler {
    final Class<? extends Event> eventType;
    final Method method;
    @Nullable final Object instance;

    RegisteredHandler(Class<? extends Event> eventType, Method method, @Nullable Object instance) {
      this.eventType = eventType;
      this.method = method;
      this.instance = instance;
    }

    void invoke(Event event) {
      try {
        this.method.invoke(this.instance, event);
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
