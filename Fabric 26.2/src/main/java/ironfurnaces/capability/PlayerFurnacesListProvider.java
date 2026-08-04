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

package ironfurnaces.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

public class PlayerFurnacesListProvider {

    public static final Codec<PlayerFurnacesListProvider> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.listOf().fieldOf("positions").forGetter(p -> p.furnacesList.listFurances)
    ).apply(instance, list -> {
        PlayerFurnacesListProvider provider = new PlayerFurnacesListProvider();
        provider.furnacesList.listFurances.clear();
        provider.furnacesList.listFurances.addAll(list);
        return provider;
    }));

    public PlayerFurnacesList furnacesList = new PlayerFurnacesList();
}
