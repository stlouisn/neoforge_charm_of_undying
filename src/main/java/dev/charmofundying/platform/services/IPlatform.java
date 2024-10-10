package dev.charmofundying.platform.services;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IPlatform {

  ItemStack findTotem(LivingEntity livingEntity);

  String getRegistryName(Item item);

  void broadcastTotemEvent(LivingEntity livingEntity, ItemStack stack);
}
