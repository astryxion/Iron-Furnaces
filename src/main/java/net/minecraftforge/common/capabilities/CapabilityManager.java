package net.minecraftforge.common.capabilities;

import java.util.concurrent.ConcurrentHashMap;

public final class CapabilityManager {
  private static final ConcurrentHashMap<Class<?>, Capability<?>> CACHE = new ConcurrentHashMap<>();

  public static <T> Capability<T> get(CapabilityToken<T> token) {
    Class<T> type = token.resolveRawType();
    @SuppressWarnings("unchecked")
    Capability<T> cap =
        (Capability<T>)
            CACHE.computeIfAbsent(type, t -> new Capability<>("cap:" + t.getName()));
    return cap;
  }

  private CapabilityManager() {}
}
