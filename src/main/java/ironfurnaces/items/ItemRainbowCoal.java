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

package ironfurnaces.items;

import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemRainbowCoal extends Item {

    public ItemRainbowCoal(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isBarVisible(ItemStack p_150899_) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return (int) ((int) 13 * (1 - (double) stack.getDamageValue() / (double) 5120));
    }

    @Override
    public int getBarColor(ItemStack p_150901_) {
        float f = Math.max(0.0F, ((float) 5120 - (float) p_150901_.getDamageValue()) / (float) 5120);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }
}
