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

package ironfurnaces.init;

import ironfurnaces.gui.BlockWirelessEnergyHeaterScreen;
import ironfurnaces.gui.furnaces.*;
import ironfurnaces.gui.furnaces.other.BlockAllthemodiumFurnaceScreen;
import ironfurnaces.gui.furnaces.other.BlockUnobtainiumFurnaceScreen;
import ironfurnaces.gui.furnaces.other.BlockVibraniumFurnaceScreen;
import ironfurnaces.mixin.MenuScreensInvoker;
import net.fabricmc.api.ClientModInitializer;

public class ClientSetup implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MenuScreensInvoker.register(Registration.IRON_FURNACE_CONTAINER.get(), BlockIronFurnaceScreen::new);
        MenuScreensInvoker.register(Registration.GOLD_FURNACE_CONTAINER.get(), BlockGoldFurnaceScreen::new);
        MenuScreensInvoker.register(Registration.DIAMOND_FURNACE_CONTAINER.get(), BlockDiamondFurnaceScreen::new);
        MenuScreensInvoker.register(Registration.EMERALD_FURNACE_CONTAINER.get(), BlockEmeraldFurnaceScreen::new);
        MenuScreensInvoker.register(Registration.OBSIDIAN_FURNACE_CONTAINER.get(), BlockObsidianFurnaceScreen::new);
        MenuScreensInvoker.register(Registration.CRYSTAL_FURNACE_CONTAINER.get(), BlockCrystalFurnaceScreen::new);
        MenuScreensInvoker.register(Registration.NETHERITE_FURNACE_CONTAINER.get(), BlockNetheriteFurnaceScreen::new);
        MenuScreensInvoker.register(Registration.COPPER_FURNACE_CONTAINER.get(), BlockCopperFurnaceScreen::new);
        MenuScreensInvoker.register(Registration.SILVER_FURNACE_CONTAINER.get(), BlockSilverFurnaceScreen::new);
        MenuScreensInvoker.register(Registration.MILLION_FURNACE_CONTAINER.get(), BlockMillionFurnaceScreen::new);
        MenuScreensInvoker.register(Registration.HEATER_CONTAINER.get(), BlockWirelessEnergyHeaterScreen::new);
        MenuScreensInvoker.register(Registration.ALLTHEMODIUM_FURNACE_CONTAINER.get(), BlockAllthemodiumFurnaceScreen::new);
        MenuScreensInvoker.register(Registration.VIBRANIUM_FURNACE_CONTAINER.get(), BlockVibraniumFurnaceScreen::new);
        MenuScreensInvoker.register(Registration.UNOBTAINIUM_FURNACE_CONTAINER.get(), BlockUnobtainiumFurnaceScreen::new);
    }
}
