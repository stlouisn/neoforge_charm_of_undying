

package dev.charmofundying;

import dev.charmofundying.common.ITotemEffectProvider;
import dev.charmofundying.common.TotemProviders;
import dev.charmofundying.platform.Services;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CharmOfUndyingCommonMod {

  public static void init() {
    TotemProviders.init();
  }

  public static Optional<Pair<ITotemEffectProvider, ItemStack>> getEffectProvider(
      LivingEntity livingEntity) {
    ItemStack stack = Services.PLATFORM.findTotem(livingEntity);

    if (!stack.isEmpty()) {
      Optional<ITotemEffectProvider> totemEffectProvider =
          TotemProviders.getEffectProvider(stack.getItem());

      if (totemEffectProvider.isPresent()) {
        return Optional.of(Pair.of(totemEffectProvider.get(), stack));
      }
    }
    return Optional.empty();
  }

  public static boolean useTotem(Pair<ITotemEffectProvider, ItemStack> totem,
                                 DamageSource damageSource, LivingEntity livingEntity) {
    ItemStack stack = totem.getSecond();
    ITotemEffectProvider effectProvider = totem.getFirst();

    if (!stack.isEmpty()) {
      ItemStack copy = stack.copy();
      effectProvider.modifyStack(stack);

      if (livingEntity instanceof ServerPlayer player) {
        player.awardStat(Stats.ITEM_USED.get(copy.getItem()), 1);
        CriteriaTriggers.USED_TOTEM.trigger(player, copy);
      }

      if (effectProvider.applyEffects(livingEntity, damageSource, copy)) {
        Services.PLATFORM.broadcastTotemEvent(livingEntity, copy);
        return true;
      }
    }
    return false;
  }
}