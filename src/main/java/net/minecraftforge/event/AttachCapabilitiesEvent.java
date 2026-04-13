package net.minecraftforge.event;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.Event;

public class AttachCapabilitiesEvent<T> extends Event {
  private final T object;
  private final Map<ResourceLocation, ICapabilityProvider> providers = new LinkedHashMap<>();

  public AttachCapabilitiesEvent(T object) {
    this.object = object;
  }

  public T getObject() {
    return object;
  }

  public void addCapability(ResourceLocation key, ICapabilityProvider provider) {
    this.providers.put(key, provider);
  }

  public Map<ResourceLocation, ICapabilityProvider> getProviders() {
    return this.providers;
  }
}
