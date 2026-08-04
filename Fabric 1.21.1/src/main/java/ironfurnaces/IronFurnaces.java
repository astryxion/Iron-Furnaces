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

package ironfurnaces;

import com.mojang.logging.LogUtils;
import ironfurnaces.init.ModSetup;
import ironfurnaces.init.Registration;
import ironfurnaces.network.Messages;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.slf4j.Logger;

public class IronFurnaces implements ModInitializer {

    public static final String MOD_ID = "ironfurnaces";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String GITHUB_BRANCH = "1.21.1";

    public static CreativeModeTab tabIronFurnaces;

    @Override
    public void onInitialize() {
        Config.load();
        Registration.bootstrap();
        tabIronFurnaces = Registration.tabIronFurnaces.get();
        ModSetup.init();
        Messages.register();
    }

    /**
     * Replaces removed {@code Level#markAndNotifyBlock}: keeps the same call sites and forwards the first flag to
     * {@link Level#sendBlockUpdated}.
     */
    public static void markAndNotifyBlock(Level level, BlockPos pos, LevelChunk chunk, BlockState oldState, BlockState newState, int flags, int depth) {
        BlockState state = level.getBlockState(pos);
        level.sendBlockUpdated(pos, state, state, flags);
    }
}
