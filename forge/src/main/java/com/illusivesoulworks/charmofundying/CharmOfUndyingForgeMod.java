/*
 * Copyright (C) 2019-2022 Illusive Soulworks
 *
 * Charm of Undying is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Charm of Undying is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and the GNU Lesser General Public License along with Charm of Undying.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.illusivesoulworks.charmofundying;

import com.illusivesoulworks.charmofundying.client.CurioTotemRenderer;
import com.illusivesoulworks.charmofundying.common.TotemProviders;
import com.illusivesoulworks.charmofundying.common.network.CharmOfUndyingForgeNetwork;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Mod(CharmOfUndyingConstants.MOD_ID)
public class CharmOfUndyingForgeMod {

  public CharmOfUndyingForgeMod() {
    CharmOfUndyingCommonMod.init();
    CharmOfUndyingConfig.setup();
    final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(this::setup);
    eventBus.addListener(this::clientSetup);
    eventBus.addListener(this::attachCapabilities);
  }

  private void setup(final FMLCommonSetupEvent evt) {
    CharmOfUndyingForgeNetwork.setup();
  }

  private void clientSetup(final FMLClientSetupEvent evt) {
    Set<String> items = new HashSet<>(TotemProviders.getItems());

    if (ModList.get().isLoaded("friendsandfoes")) {
      items.add("friendsandfoes:totem_of_freezing");
      items.add("friendsandfoes:totem_of_illusion");
    }

    for (String name : items) {
      Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(name));

      if (item != null && item != Items.AIR) {
        CuriosRendererRegistry.register(item, CurioTotemRenderer::new);
      }
    }
  }

  private void attachCapabilities(RegisterCapabilitiesEvent evt) {
    ICurioItem curio = new ICurioItem() {

      @Override
      public boolean canEquipFromUse(SlotContext ctx, ItemStack stack) {
        return true;
      }
    };

    for (Item item : ForgeRegistries.ITEMS.getValues()) {

      if (TotemProviders.IS_TOTEM.test(item)) {
        CuriosApi.registerCurio(item, curio);
      }
    }
  }
}