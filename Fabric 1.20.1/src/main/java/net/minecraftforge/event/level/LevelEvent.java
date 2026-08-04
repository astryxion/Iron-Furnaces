package net.minecraftforge.event.level;

import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;

public class LevelEvent extends Event {
  private final Level level;

  public LevelEvent(Level level) {
    this.level = level;
  }

  public Level getLevel() {
    return this.level;
  }

  public static class Load extends LevelEvent {
    public Load(Level level) {
      super(level);
    }
  }
}
