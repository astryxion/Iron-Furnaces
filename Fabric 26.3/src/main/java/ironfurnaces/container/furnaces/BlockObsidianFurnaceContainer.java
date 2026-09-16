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

package ironfurnaces.container.furnaces;

import ironfurnaces.container.ClientMenuOpenPos;
import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.BlockObsidianFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BlockObsidianFurnaceContainer extends BlockIronFurnaceContainerBase {

    public BlockObsidianFurnaceContainer(int syncId, Inventory inv) {
        this(syncId, inv.player.level(), ClientMenuOpenPos.forInventory(inv), inv, inv.player);
    }

    public BlockObsidianFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(ironfurnaces.init.Registration.OBSIDIAN_FURNACE_CONTAINER, windowId, world, pos, playerInventory, player);
        this.te = (BlockObsidianFurnaceTile) world.getBlockEntity(pos);
    }




}