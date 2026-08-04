package net.minecraftforge.common;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraftforge.fml.config.IConfigSpec;
import org.jetbrains.annotations.Nullable;

public class ForgeConfigSpec implements IConfigSpec {
  private static final Joiner DOT_JOINER = Joiner.on('.');
  private static final Splitter DOT_SPLITTER = Splitter.on('.');

  private final List<Value<?>> values = new ArrayList<>();

  @Nullable private CommentedConfig childConfig;

  ForgeConfigSpec(List<Value<?>> values) {
    this.values.addAll(values);
    for (Value<?> v : this.values) {
      v.spec = this;
    }
  }

  @Nullable
  CommentedConfig getChildConfig() {
    return this.childConfig;
  }

  @Override
  public void acceptConfig(CommentedConfig data) {
    setConfig(data);
  }

  public void setConfig(CommentedConfig config) {
    this.childConfig = config;
    for (Value<?> v : this.values) {
      v.clearCache();
    }
    afterReload();
  }

  public void afterReload() {}

  public static class Builder {
    private final List<String> currentPath = new ArrayList<>();
    private final List<Value<?>> staged = new ArrayList<>();

    private static List<String> split(String path) {
      return new ArrayList<>(DOT_SPLITTER.splitToList(path));
    }

    private List<String> buildPath(List<String> path) {
      if (this.currentPath.isEmpty()) {
        return path;
      }
      List<String> tmp = new ArrayList<>(this.currentPath);
      tmp.addAll(path);
      return tmp;
    }

    public Builder comment(String value) {
      return this;
    }

    public Builder push(String path) {
      this.currentPath.addAll(split(path));
      return this;
    }

    public Builder pop() {
      if (this.currentPath.isEmpty()) {
        throw new IllegalStateException("Attempted to pop when path stack is empty");
      }
      this.currentPath.remove(this.currentPath.size() - 1);
      return this;
    }

    public BooleanValue define(String path, boolean defaultValue) {
      return define(path, defaultValue, o -> o instanceof Boolean);
    }

    public BooleanValue define(String path, boolean defaultValue, Predicate<Object> validator) {
      List<String> full = buildPath(split(path));
      BooleanValue v = new BooleanValue(full, () -> defaultValue, validator);
      this.staged.add(v);
      return v;
    }

    public IntValue defineInRange(String path, int defaultValue, int min, int max) {
      List<String> full = buildPath(split(path));
      IntValue v = new IntValue(full, () -> defaultValue, min, max);
      this.staged.add(v);
      return v;
    }

    public ForgeConfigSpec build() {
      return new ForgeConfigSpec(new ArrayList<>(this.staged));
    }
  }

  public abstract static class Value<T> {
    ForgeConfigSpec spec;
    final List<String> path;
    final Supplier<T> defaultSupplier;

    @Nullable private T cache;

    Value(List<String> path, Supplier<T> defaultSupplier) {
      this.path = path;
      this.defaultSupplier = defaultSupplier;
    }

    public void clearCache() {
      this.cache = null;
    }

    public T get() {
      if (this.cache != null) {
        return this.cache;
      }
      CommentedConfig cfg = this.spec != null ? this.spec.childConfig : null;
      if (cfg == null) {
        this.cache = this.defaultSupplier.get();
        return this.cache;
      }
      this.cache = read(cfg);
      return this.cache;
    }

    protected abstract T read(CommentedConfig cfg);
  }

  public static final class BooleanValue extends Value<Boolean> {
    private final Predicate<Object> validator;

    BooleanValue(List<String> path, Supplier<Boolean> defaultSupplier, Predicate<Object> validator) {
      super(path, defaultSupplier);
      this.validator = validator;
    }

    @Override
    protected Boolean read(CommentedConfig cfg) {
      Object raw = cfg.get(this.path);
      if (raw == null) {
        return this.defaultSupplier.get();
      }
      if (raw instanceof Boolean b) {
        return this.validator.test(b) ? b : this.defaultSupplier.get();
      }
      if (raw instanceof Number n) {
        boolean b = n.intValue() != 0;
        return this.validator.test(b) ? Boolean.valueOf(b) : this.defaultSupplier.get();
      }
      return this.defaultSupplier.get();
    }
  }

  public static final class IntValue extends Value<Integer> {
    private final int min;
    private final int max;

    IntValue(List<String> path, Supplier<Integer> defaultSupplier, int min, int max) {
      super(path, defaultSupplier);
      this.min = min;
      this.max = max;
    }

    @Override
    protected Integer read(CommentedConfig cfg) {
      Object raw = cfg.get(this.path);
      if (raw == null) {
        return clamp(this.defaultSupplier.get());
      }
      int v;
      if (raw instanceof Number n) {
        v = n.intValue();
      } else {
        v = this.defaultSupplier.get();
      }
      return clamp(v);
    }

    private int clamp(int v) {
      return Math.min(this.max, Math.max(this.min, v));
    }
  }
}
