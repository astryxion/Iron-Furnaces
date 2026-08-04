package net.minecraftforge.event.level;

import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;

public class ExplosionEvent extends Event {
  private final Level level;
  private final Explosion explosion;

  public ExplosionEvent(Level level, Explosion explosion) {
    this.level = level;
    this.explosion = explosion;
  }

  public Level getLevel() {
    return this.level;
  }

  public Explosion getExplosion() {
    return this.explosion;
  }
}
