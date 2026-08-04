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
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ClientSetup {

    public static void init(final RegisterMenuScreensEvent event) {
            event.register(ironfurnaces.init.Registration.IRON_FURNACE_CONTAINER.get(), BlockIronFurnaceScreen::new);
            event.register(ironfurnaces.init.Registration.GOLD_FURNACE_CONTAINER.get(), BlockGoldFurnaceScreen::new);
            event.register(ironfurnaces.init.Registration.DIAMOND_FURNACE_CONTAINER.get(), BlockDiamondFurnaceScreen::new);
            event.register(ironfurnaces.init.Registration.EMERALD_FURNACE_CONTAINER.get(), BlockEmeraldFurnaceScreen::new);
            event.register(ironfurnaces.init.Registration.OBSIDIAN_FURNACE_CONTAINER.get(), BlockObsidianFurnaceScreen::new);
            event.register(ironfurnaces.init.Registration.CRYSTAL_FURNACE_CONTAINER.get(), BlockCrystalFurnaceScreen::new);
            event.register(ironfurnaces.init.Registration.NETHERITE_FURNACE_CONTAINER.get(), BlockNetheriteFurnaceScreen::new);
            event.register(ironfurnaces.init.Registration.COPPER_FURNACE_CONTAINER.get(), BlockCopperFurnaceScreen::new);
            event.register(ironfurnaces.init.Registration.SILVER_FURNACE_CONTAINER.get(), BlockSilverFurnaceScreen::new);
            event.register(ironfurnaces.init.Registration.MILLION_FURNACE_CONTAINER.get(), BlockMillionFurnaceScreen::new);
            event.register(ironfurnaces.init.Registration.HEATER_CONTAINER.get(), BlockWirelessEnergyHeaterScreen::new);


            event.register(ironfurnaces.init.Registration.ALLTHEMODIUM_FURNACE_CONTAINER.get(), BlockAllthemodiumFurnaceScreen::new);
            event.register(ironfurnaces.init.Registration.VIBRANIUM_FURNACE_CONTAINER.get(), BlockVibraniumFurnaceScreen::new);
            event.register(ironfurnaces.init.Registration.UNOBTAINIUM_FURNACE_CONTAINER.get(), BlockUnobtainiumFurnaceScreen::new);




    }

}