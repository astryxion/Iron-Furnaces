package net.minecraftforge.eventbus.api;

public class Event {
  private boolean canceled;

  public boolean isCanceled() {
    return this.canceled;
  }

  public void setCanceled(boolean cancel) {
    this.canceled = cancel;
  }
}
