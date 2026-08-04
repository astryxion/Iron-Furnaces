package net.minecraftforge.common.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

public final class LazyOptional<T> {
  private enum Mode {
    EMPTY,
    DIRECT,
    LAZY
  }

  private static final LazyOptional<?> EMPTY = new LazyOptional<>(Mode.EMPTY, null, null);

  private final Mode mode;
  @Nullable private final T direct;
  @Nullable private final Supplier<? extends T> supplier;
  @Nullable private T resolved;
  private boolean invalidated;

  private LazyOptional(Mode mode, @Nullable T direct, @Nullable Supplier<? extends T> supplier) {
    this.mode = mode;
    this.direct = direct;
    this.supplier = supplier;
  }

  @Nullable
  private T resolveValue() {
    if (this.invalidated) {
      return null;
    }
    if (this.mode == Mode.EMPTY) {
      return null;
    }
    if (this.mode == Mode.DIRECT) {
      return this.direct;
    }
    if (this.resolved == null && this.supplier != null) {
      this.resolved = this.supplier.get();
    }
    return this.resolved;
  }

  public static <T> LazyOptional<T> of(@Nullable T value) {
    if (value == null) {
      return empty();
    }
    return new LazyOptional<>(Mode.DIRECT, value, null);
  }

  public static <T> LazyOptional<T> of(Supplier<? extends T> supplier) {
    Objects.requireNonNull(supplier, "supplier");
    return new LazyOptional<>(Mode.LAZY, null, supplier);
  }

  @SuppressWarnings("unchecked")
  public static <T> LazyOptional<T> empty() {
    return (LazyOptional<T>) EMPTY;
  }

  public boolean isPresent() {
    return resolveValue() != null;
  }

  public void ifPresent(Consumer<? super T> consumer) {
    T v = resolveValue();
    if (v != null) {
      consumer.accept(v);
    }
  }

  public <U> LazyOptional<U> map(Function<? super T, ? extends U> mapper) {
    T v = resolveValue();
    if (v == null) {
      return empty();
    }
    return of(mapper.apply(v));
  }

  public T orElse(T other) {
    T v = resolveValue();
    return v != null ? v : other;
  }

  public T orElseGet(Supplier<? extends T> supplier) {
    T v = resolveValue();
    return v != null ? v : supplier.get();
  }

  public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
    T v = resolveValue();
    if (v != null) {
      return v;
    }
    throw exceptionSupplier.get();
  }

  public Optional<T> resolve() {
    return Optional.ofNullable(resolveValue());
  }

  public Stream<T> stream() {
    T v = resolveValue();
    return v != null ? Stream.of(v) : Stream.empty();
  }

  public LazyOptional<T> filter(Predicate<? super T> predicate) {
    T v = resolveValue();
    if (v == null) {
      return empty();
    }
    return predicate.test(v) ? this : empty();
  }

  @SuppressWarnings("unchecked")
  public <X> LazyOptional<X> cast() {
    return (LazyOptional<X>) this;
  }

  public void invalidate() {
    this.invalidated = true;
  }

  public boolean isValid() {
    return !this.invalidated;
  }
}
