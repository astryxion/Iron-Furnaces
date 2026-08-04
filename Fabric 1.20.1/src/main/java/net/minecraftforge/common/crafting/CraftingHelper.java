package net.minecraftforge.common.crafting;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public final class CraftingHelper {
  private CraftingHelper() {}

  public static void register(IConditionSerializer<?> serializer) {
    ResourceLocation id = serializer.getID();
    ResourceConditions.register(
        id,
        (jsonObject) -> {
          ICondition cond = serializer.read((JsonObject) jsonObject);
          return cond.test(ICondition.IContext.EMPTY);
        });
  }
}
