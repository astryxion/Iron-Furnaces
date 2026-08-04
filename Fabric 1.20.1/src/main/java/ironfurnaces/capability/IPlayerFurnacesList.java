package ironfurnaces.capability;

import java.util.List;
import net.minecraft.core.BlockPos;

public interface IPlayerFurnacesList {
  List<BlockPos> get();
  
  void add(BlockPos paramBlockPos);
  
  void remove(BlockPos paramBlockPos);
}


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\capability\IPlayerFurnacesList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */