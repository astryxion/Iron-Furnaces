package net.minecraftforge.api.distmarker;

public enum Dist {
  CLIENT,
  DEDICATED_SERVER;

  public static Dist validateDist(Dist dist, Dist current) {
    return current;
  }
}
