package dev.charmofundying.common;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ITotemEffectProvider {

  default boolean bypassInvul() {
    return false;
  }

  boolean applyEffects(LivingEntity livingEntity, DamageSource damageSource, ItemStack stack);

  default void modifyStack(ItemStack stack) {
    stack.shrink(1);
  }

}
