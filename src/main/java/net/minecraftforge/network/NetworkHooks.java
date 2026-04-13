package net.minecraftforge.network;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

public final class NetworkHooks {
  private NetworkHooks() {}

  public static void openScreen(ServerPlayer player, MenuProvider provider, BlockPos pos) {
    player.openMenu(
        new ExtendedScreenHandlerFactory() {
          @Override
          public void writeScreenOpeningData(ServerPlayer serverPlayer, FriendlyByteBuf buf) {
            buf.writeBlockPos(pos);
          }

          @Override
          public Component getDisplayName() {
            return provider.getDisplayName();
          }

          @Nullable
          @Override
          public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player2) {
            return provider.createMenu(syncId, inv, player2);
          }
        });
  }
}
