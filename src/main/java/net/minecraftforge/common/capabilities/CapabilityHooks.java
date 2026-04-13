package net.minecraftforge.common.capabilities;

import org.jetbrains.annotations.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;

/** Compile-time helpers where vanilla types do not declare Forge {@link ICapabilityProvider}. */
public final class CapabilityHooks {
    private CapabilityHooks() {}

    public static <T> LazyOptional<T> get(BlockEntity be, Capability<T> cap, @Nullable Direction side) {
        if (be instanceof ICapabilityProvider p) {
            return p.getCapability(cap, side);
        }
        return LazyOptional.empty();
    }

    public static <T> LazyOptional<T> get(Entity entity, Capability<T> cap, @Nullable Direction side) {
        if (entity instanceof ICapabilityProvider p) {
            return p.getCapability(cap, side);
        }
        return LazyOptional.empty();
    }

    public static <T> LazyOptional<T> get(BlockEntity be, Capability<T> cap) {
        return get(be, cap, null);
    }

    public static <T> LazyOptional<T> get(Entity entity, Capability<T> cap) {
        return get(entity, cap, null);
    }
}
