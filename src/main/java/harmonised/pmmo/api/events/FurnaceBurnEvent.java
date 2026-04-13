package harmonised.pmmo.api.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FurnaceBurnEvent {
  public final ItemStack stack;
  public final Level level;
  public final BlockPos pos;

  public FurnaceBurnEvent(ItemStack stack, Level level, BlockPos pos) {
    this.stack = stack;
    this.level = level;
    this.pos = pos;
  }
}
