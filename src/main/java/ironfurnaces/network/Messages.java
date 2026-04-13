/*
 * Copyright 2025 pizzaatime and XenoMustache
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ironfurnaces.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class Messages {

    public static void register() {
        PayloadTypeRegistry.playC2S().register(PacketFurnaceSettings.TYPE, PacketFurnaceSettings.CODEC);
        PayloadTypeRegistry.playC2S().register(PacketShowConfig.TYPE, PacketShowConfig.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(PacketFurnaceSettings.TYPE, (payload, context) -> payload.handle(context.player()));
        ServerPlayNetworking.registerGlobalReceiver(PacketShowConfig.TYPE, (payload, context) -> payload.handle(context.player()));
    }

    public static <MSG extends CustomPacketPayload> void sendToPlayer(MSG message, ServerPlayer player) {
        ServerPlayNetworking.send(player, message);
    }
}
