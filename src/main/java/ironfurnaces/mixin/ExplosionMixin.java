package ironfurnaces.mixin;

import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.ExplosionEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
  @Shadow @Final private Level level;

  @Inject(method = "finalizeExplosion", at = @At("HEAD"))
  private void ironfurnaces$postExplosionEvent(boolean spawnParticles, CallbackInfo ci) {
    MinecraftForge.EVENT_BUS.post(new ExplosionEvent(this.level, (Explosion) (Object) this));
  }
}
