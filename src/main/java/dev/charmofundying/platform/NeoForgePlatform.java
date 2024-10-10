package dev.charmofundying.platform;

import dev.charmofundying.common.TotemProviders;
import dev.charmofundying.common.network.SPacketUseTotem;
import dev.charmofundying.platform.services.IPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class NeoForgePlatform implements IPlatform {

  @Override
  public ItemStack findTotem(LivingEntity livingEntity) {
    return CuriosApi.getCuriosInventory(livingEntity)
                    .map(inv -> inv.findFirstCurio(stack -> TotemProviders.IS_TOTEM.test(stack.getItem())).map(SlotResult::stack).orElse(ItemStack.EMPTY))
                    .orElse(ItemStack.EMPTY);
  }

  @Override
  public String getRegistryName(Item item) {
    ResourceLocation rl = BuiltInRegistries.ITEM.getKey(item);
    return rl.toString();
  }

  @Override
  public void broadcastTotemEvent(LivingEntity livingEntity, ItemStack stack) {
    PacketDistributor.sendToPlayersTrackingEntityAndSelf(livingEntity, new SPacketUseTotem(livingEntity.getId(), stack));
  }
}
