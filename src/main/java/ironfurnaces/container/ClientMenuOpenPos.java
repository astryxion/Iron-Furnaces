/*
 * Copyright 2025 Astryxion
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

package ironfurnaces.container;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Resolves the block position for {@link net.minecraft.world.inventory.MenuType} factories on the logical client.
 * The dedicated server never invokes those factories (menus are created via {@link net.minecraft.world.MenuProvider}).
 */
public final class ClientMenuOpenPos {
    private ClientMenuOpenPos() {
    }

    public static BlockPos forInventory(Inventory inv) {
        if (!inv.player.level().isClientSide()) {
            return BlockPos.ZERO;
        }
        var hit = Minecraft.getInstance().hitResult;
        return hit instanceof BlockHitResult br ? br.getBlockPos() : BlockPos.ZERO;
    }
}
