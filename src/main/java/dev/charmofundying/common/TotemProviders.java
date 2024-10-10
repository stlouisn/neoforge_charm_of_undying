package dev.charmofundying.common;

import com.google.common.collect.ImmutableSet;
import dev.charmofundying.platform.Services;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import net.minecraft.world.item.Item;

public class TotemProviders {

  public static Predicate<Item> IS_TOTEM = new Predicate<>() {
    @Override
    public boolean test(Item item) {
      return EFFECT_PROVIDERS.containsKey(Services.PLATFORM.getRegistryName(item));
    }
  };
  private static final Map<String, ITotemEffectProvider> EFFECT_PROVIDERS = new ConcurrentHashMap<>();

  public static void init() {
    EFFECT_PROVIDERS.put("minecraft:totem_of_undying", new VanillaTotemEffectProvider());
  }

  @SuppressWarnings("unused")
  public static Set<String> getItems() {
    return ImmutableSet.copyOf(EFFECT_PROVIDERS.keySet());
  }

  public static Optional<ITotemEffectProvider> getEffectProvider(final Item item) {
    return Optional.ofNullable(EFFECT_PROVIDERS.get(Services.PLATFORM.getRegistryName(item)));
  }

  @SuppressWarnings("unused")
  public static void putEffectProvider(String key, ITotemEffectProvider provider) {
    EFFECT_PROVIDERS.put(key, provider);
  }
}
