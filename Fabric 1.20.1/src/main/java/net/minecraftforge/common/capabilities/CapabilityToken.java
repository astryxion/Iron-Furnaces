package net.minecraftforge.common.capabilities;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class CapabilityToken<T> {
  @SuppressWarnings("unchecked")
  Class<T> resolveRawType() {
    Type s = getClass().getGenericSuperclass();
    if (s instanceof ParameterizedType p) {
      Type arg = p.getActualTypeArguments()[0];
      if (arg instanceof Class<?> c) {
        return (Class<T>) c;
      }
    }
    throw new IllegalStateException("CapabilityToken must be subclassed with concrete type: " + getClass());
  }
}
