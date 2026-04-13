package ironfurnaces.tileentity;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public interface ITileInventory {
  int[] IgetSlotsForFace(Direction paramDirection);
  
  boolean IcanExtractItem(int paramInt, ItemStack paramItemStack, Direction paramDirection);
  
  String IgetName();
  
  boolean IisItemValidForSlot(int paramInt, ItemStack paramItemStack);
  
  AbstractContainerMenu IcreateMenu(int paramInt, Inventory paramInventory, Player paramPlayer);
}


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\tileentity\ITileInventory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */