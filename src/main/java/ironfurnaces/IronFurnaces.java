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

package ironfurnaces;

import com.mojang.logging.LogUtils;
import ironfurnaces.init.ModSetup;
import ironfurnaces.init.Registration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelValueEvents;
import ironfurnaces.network.Messages;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.fabricmc.fabric.api.transfer.v1.item.ContainerStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.slf4j.Logger;

public class IronFurnaces implements ModInitializer {

    public static final String MOD_ID = "ironfurnaces";

    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        Config.load();
        ModSetup.init();
        FuelValueEvents.BUILD.register((builder, context) -> builder.add(Registration.RAINBOW_COAL, 200));
        Messages.register();
        registerTransferApis();
    }

    private static void registerTransferApis() {
        ItemStorage.SIDED.registerForBlockEntity(
                (be, side) -> ContainerStorage.of((Container) be, side),
                Registration.HEATER_TILE);
        registerFurnaceItems(Registration.IRON_FURNACE_TILE);
        registerFurnaceItems(Registration.GOLD_FURNACE_TILE);
        registerFurnaceItems(Registration.DIAMOND_FURNACE_TILE);
        registerFurnaceItems(Registration.EMERALD_FURNACE_TILE);
        registerFurnaceItems(Registration.OBSIDIAN_FURNACE_TILE);
        registerFurnaceItems(Registration.CRYSTAL_FURNACE_TILE);
        registerFurnaceItems(Registration.NETHERITE_FURNACE_TILE);
        registerFurnaceItems(Registration.COPPER_FURNACE_TILE);
        registerFurnaceItems(Registration.SILVER_FURNACE_TILE);
        registerFurnaceItems(Registration.ALLTHEMODIUM_FURNACE_TILE);
        registerFurnaceItems(Registration.VIBRANIUM_FURNACE_TILE);
        registerFurnaceItems(Registration.UNOBTAINIUM_FURNACE_TILE);
        registerFurnaceItems(Registration.MILLION_FURNACE_TILE);
    }

    private static void registerFurnaceItems(BlockEntityType<? extends BlockIronFurnaceTileBase> type) {
        ItemStorage.SIDED.registerForBlockEntity(
                (be, side) -> ContainerStorage.of((Container) be, side),
                type);
    }
}
