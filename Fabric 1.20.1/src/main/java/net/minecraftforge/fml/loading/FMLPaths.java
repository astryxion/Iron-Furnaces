package net.minecraftforge.fml.loading;

import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public class FMLPaths {
  public static Path getOrCreateGameRelativePath(Path path) {
    return FabricLoader.getInstance().getGameDir().resolve(path);
  }

  public static final class FMLPath {
    private final Path path;

    FMLPath(Path path) {
      this.path = path;
    }

    public Path get() {
      return this.path;
    }
  }

  public static final FMLPath CONFIGDIR =
      new FMLPath(FabricLoader.getInstance().getConfigDir().normalize());
}
