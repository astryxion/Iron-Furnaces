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

package ironfurnaces.mixin;

import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.furnaces.BlockMillionFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Explosion.class)
public class ExplosionMixin {

    @Shadow
    @Final
    private Level level;

    @Inject(method = "finalizeExplosion", at = @At("HEAD"))
    private void ironfurnaces$handleMillionExplosion(boolean spawnParticles, CallbackInfo ci) {
        Explosion explosion = (Explosion) (Object) this;
        List<BlockPos> toBlow = explosion.getToBlow();
        for (BlockPos pos : new ArrayList<>(toBlow)) {
            if (level.getBlockEntity(pos) instanceof BlockMillionFurnaceTile) {
                toBlow.remove(pos);
                level.removeBlockEntity(pos);
                level.removeBlock(pos, false);
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY() + 6f, pos.getZ(), new ItemStack(Registration.RAINBOW_COAL.get())));
            }
        }
    }
}
