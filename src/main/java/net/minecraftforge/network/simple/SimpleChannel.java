package net.minecraftforge.network.simple;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class SimpleChannel {
  private final ResourceLocation id;
  private final Int2ObjectOpenHashMap<Handler<?>> handlers = new Int2ObjectOpenHashMap<>();
  private final Map<Class<?>, Integer> classToDiscriminator = new HashMap<>();

  public SimpleChannel(ResourceLocation id) {
    this.id = id;
    if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
      ServerPlayNetworking.registerGlobalReceiver(
          id,
          (server, player, handler, buf, responseSender) -> {
            FriendlyByteBuf fbb = new FriendlyByteBuf(buf.copy());
            int disc = fbb.readVarInt();
            Handler<?> h = this.handlers.get(disc);
            if (h == null) {
              return;
            }
            h.handle(server, player, fbb);
          });
    }
  }

  public <M> void registerMessage(
      int index,
      Class<M> messageType,
      BiConsumer<M, ByteBuf> encoder,
      Function<ByteBuf, M> decoder,
      BiConsumer<M, Supplier<NetworkEvent.Context>> messageConsumer) {
    this.classToDiscriminator.put(messageType, index);
    this.handlers.put(
        index,
        new Handler<>(encoder, decoder, messageConsumer));
  }

  @Environment(EnvType.CLIENT)
  public void sendToServer(Object message) {
    if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
      return;
    }
    Integer disc = this.classToDiscriminator.get(message.getClass());
    if (disc == null) {
      throw new IllegalStateException("No packet id for " + message.getClass());
    }
    Handler<?> h = this.handlers.get((int) disc);
    FriendlyByteBuf buf = PacketByteBufs.create();
    buf.writeVarInt((int) disc);
    @SuppressWarnings("unchecked")
    Handler<Object> ho = (Handler<Object>) h;
    ho.encoder().accept(message, buf);
    ClientPlayNetworking.send(this.id, buf);
  }

  private record Handler<M>(
      BiConsumer<M, ByteBuf> encoder,
      Function<ByteBuf, M> decoder,
      BiConsumer<M, Supplier<NetworkEvent.Context>> consumer) {
    void handle(
        net.minecraft.server.MinecraftServer server, ServerPlayer player, FriendlyByteBuf buf) {
      M msg = this.decoder.apply(buf);
      NetworkEvent.Context ctx = new NetworkEvent.Context(player, server);
      this.consumer.accept(msg, () -> ctx);
    }
  }
}
