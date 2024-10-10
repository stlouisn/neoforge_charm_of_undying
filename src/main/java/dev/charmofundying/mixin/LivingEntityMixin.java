package dev.charmofundying.mixin;

import com.mojang.datafixers.util.Pair;
import dev.charmofundying.CharmOfUndyingCommon;
import dev.charmofundying.common.ITotemEffectProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

  @Unique
  Pair<ITotemEffectProvider, ItemStack> charmofundying$totem;

  @Inject(at = @At(value = "HEAD"), method = "checkTotemDeathProtection", cancellable = true)
  private void charmofundying$precheckTotemDeathProtection(DamageSource src, CallbackInfoReturnable<Boolean> cir) {
    charmofundying$totem = CharmOfUndyingCommon.getEffectProvider((LivingEntity) (Object) this).orElse(null);
    //noinspection DataFlowIssue
    if (charmofundying$totem != null && charmofundying$totem.getFirst().bypassInvul() && CharmOfUndyingCommon.useTotem(charmofundying$totem, src, (LivingEntity) (Object) this)) {
      cir.setReturnValue(true);
    }
  }

  @Inject(at = @At(value = "INVOKE", target = "net/minecraft/world/InteractionHand.values()[Lnet/minecraft/world/InteractionHand;"), method = "checkTotemDeathProtection", cancellable = true)
  private void charmofundying$checkTotemDeathProtection(DamageSource src, CallbackInfoReturnable<Boolean> cir) {
    //noinspection ConstantValue
    if (charmofundying$totem != null && !charmofundying$totem.getFirst().bypassInvul() && CharmOfUndyingCommon.useTotem(charmofundying$totem, src, (LivingEntity) (Object) this)) {
      cir.setReturnValue(true);
    }
  }
}
