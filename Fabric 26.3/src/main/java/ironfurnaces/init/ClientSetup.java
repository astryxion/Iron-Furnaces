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

package ironfurnaces.init;

import ironfurnaces.gui.BlockWirelessEnergyHeaterScreen;
import ironfurnaces.gui.furnaces.*;
import ironfurnaces.gui.furnaces.other.BlockAllthemodiumFurnaceScreen;
import ironfurnaces.gui.furnaces.other.BlockUnobtainiumFurnaceScreen;
import ironfurnaces.gui.furnaces.other.BlockVibraniumFurnaceScreen;
import net.minecraft.client.gui.screens.MenuScreens;

public class ClientSetup {

    public static void init() {
        MenuScreens.register(Registration.IRON_FURNACE_CONTAINER, BlockIronFurnaceScreen::new);
        MenuScreens.register(Registration.GOLD_FURNACE_CONTAINER, BlockGoldFurnaceScreen::new);
        MenuScreens.register(Registration.DIAMOND_FURNACE_CONTAINER, BlockDiamondFurnaceScreen::new);
        MenuScreens.register(Registration.EMERALD_FURNACE_CONTAINER, BlockEmeraldFurnaceScreen::new);
        MenuScreens.register(Registration.OBSIDIAN_FURNACE_CONTAINER, BlockObsidianFurnaceScreen::new);
        MenuScreens.register(Registration.CRYSTAL_FURNACE_CONTAINER, BlockCrystalFurnaceScreen::new);
        MenuScreens.register(Registration.NETHERITE_FURNACE_CONTAINER, BlockNetheriteFurnaceScreen::new);
        MenuScreens.register(Registration.COPPER_FURNACE_CONTAINER, BlockCopperFurnaceScreen::new);
        MenuScreens.register(Registration.SILVER_FURNACE_CONTAINER, BlockSilverFurnaceScreen::new);
        MenuScreens.register(Registration.MILLION_FURNACE_CONTAINER, BlockMillionFurnaceScreen::new);
        MenuScreens.register(Registration.HEATER_CONTAINER, BlockWirelessEnergyHeaterScreen::new);
        MenuScreens.register(Registration.ALLTHEMODIUM_FURNACE_CONTAINER, BlockAllthemodiumFurnaceScreen::new);
        MenuScreens.register(Registration.VIBRANIUM_FURNACE_CONTAINER, BlockVibraniumFurnaceScreen::new);
        MenuScreens.register(Registration.UNOBTAINIUM_FURNACE_CONTAINER, BlockUnobtainiumFurnaceScreen::new);
    }

}
