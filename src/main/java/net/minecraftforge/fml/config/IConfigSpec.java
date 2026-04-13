package net.minecraftforge.fml.config;

import com.electronwill.nightconfig.core.CommentedConfig;

public interface IConfigSpec {
  void acceptConfig(CommentedConfig data);
}
