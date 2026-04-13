package net.minecraftforge.common.crafting.conditions;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public interface ICondition {
  ResourceLocation getID();

  boolean test(IContext context);

  interface IContext {
    IContext EMPTY = new IContext() {};
  }
}
