package net.minecraftforge.fml;

import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class ModLoadingContext {
  private static final ModLoadingContext INSTANCE = new ModLoadingContext();

  public static ModLoadingContext get() {
    return INSTANCE;
  }

  public void registerConfig(ModConfig.Type type, IConfigSpec spec) {}
}
