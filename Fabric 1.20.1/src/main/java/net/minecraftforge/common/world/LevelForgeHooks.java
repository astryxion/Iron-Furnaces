package net.minecraftforge.common.world;

import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

/**
 * Forge exposes {@code Level#markAndNotifyBlock} as a split from {@code Level#setBlock};
 * vanilla does not. This mirrors the Forge branch that runs when the world state already
 * matches {@code newState} but block updates / hooks must still run (used after BE sync).
 */
public final class LevelForgeHooks {
    private LevelForgeHooks() {}

    public static void markAndNotifyBlock(
            Level level,
            BlockPos pos,
            @Nullable LevelChunk chunk,
            BlockState oldState,
            BlockState newState,
            int flags,
            int recursionLeft) {
        BlockState current = level.getBlockState(pos);
        if (current == newState) {
            if (oldState != current) {
                level.setBlocksDirty(pos, oldState, current);
            }
            level.onBlockStateChange(pos, oldState, current);
        }
    }
}
