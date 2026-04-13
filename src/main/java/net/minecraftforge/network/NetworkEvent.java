package net.minecraftforge.network;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class NetworkEvent {
  public static class Context {
    private final ServerPlayer sender;
    private final MinecraftServer server;

    public Context(ServerPlayer sender, MinecraftServer server) {
      this.sender = sender;
      this.server = server;
    }

    public void enqueueWork(Runnable work) {
      this.server.execute(work);
    }

    public ServerPlayer getSender() {
      return this.sender;
    }

    public void setPacketHandled(boolean handled) {}
  }
}
