package ironfurnaces.mixin;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityCapabilityMixin implements ICapabilityProvider {
  @Unique
  private static final WeakHashMap<Entity, Map<ResourceLocation, ICapabilityProvider>> ironfurnaces$ATTACHED =
      new WeakHashMap<>();

  @Inject(method = "<init>", at = @At("RETURN"))
  private void ironfurnaces$attachCapabilities(CallbackInfo ci) {
    Entity self = (Entity) (Object) this;
    AttachCapabilitiesEvent<Entity> evt = new AttachCapabilitiesEvent<>(self);
    MinecraftForge.EVENT_BUS.post(evt);
    if (!evt.getProviders().isEmpty()) {
      ironfurnaces$ATTACHED.computeIfAbsent(self, e -> new LinkedHashMap<>()).putAll(evt.getProviders());
    }
  }

  @Override
  public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
    Map<ResourceLocation, ICapabilityProvider> map =
        ironfurnaces$ATTACHED.get((Entity) (Object) this);
    if (map != null) {
      for (ICapabilityProvider provider : map.values()) {
        LazyOptional<T> opt = provider.getCapability(cap, side);
        if (opt.isPresent()) {
          return opt;
        }
      }
    }
    return LazyOptional.empty();
  }
}
