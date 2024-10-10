package dev.charmofundying.common;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class VanillaTotemEffectProvider implements ITotemEffectProvider {

  @Override
  public boolean applyEffects(LivingEntity livingEntity, DamageSource damageSource, ItemStack stack) {
    livingEntity.setHealth(1.0F);
    livingEntity.removeAllEffects();
    livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
    livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
    livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
    return true;
  }
}
