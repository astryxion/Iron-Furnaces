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

package ironfurnaces.mixin;

import ironfurnaces.blocks.furnaces.BlockMillionFurnace;
import ironfurnaces.init.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

/** Million furnaces drop rainbow coal; run at interactWithBlocks HEAD (not during position calc). */
@Mixin(ServerExplosion.class)
public abstract class ServerExplosionMixin {

    @Shadow
    public abstract ServerLevel level();

    @Inject(method = "interactWithBlocks", at = @At("HEAD"))
    private void ironfurnaces$handleMillionFurnaceDestruction(List<BlockPos> list, CallbackInfo ci) {
        if (list.isEmpty()) {
            return;
        }
        ServerLevel world = level();
        for (BlockPos pos : new ArrayList<>(list)) {
            if (!(world.getBlockState(pos).getBlock() instanceof BlockMillionFurnace)) {
                continue;
            }
            list.remove(pos);
            world.removeBlockEntity(pos);
            world.removeBlock(pos, false);
            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY() + 6f, pos.getZ(), new ItemStack(Registration.RAINBOW_COAL)));
        }
    }
}
