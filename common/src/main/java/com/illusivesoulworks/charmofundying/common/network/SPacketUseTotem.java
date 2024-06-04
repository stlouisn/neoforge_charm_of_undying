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

package com.illusivesoulworks.charmofundying.common.network;

import com.illusivesoulworks.charmofundying.CharmOfUndyingConstants;
import com.illusivesoulworks.charmofundying.client.ClientPacketHandler;
import javax.annotation.Nonnull;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record SPacketUseTotem(int entityId, ItemStack stack) implements CustomPacketPayload {

  public static final Type<SPacketUseTotem> TYPE =
      new Type<>(new ResourceLocation(CharmOfUndyingConstants.MOD_ID, "use_totem"));
  public static final StreamCodec<RegistryFriendlyByteBuf, SPacketUseTotem> STREAM_CODEC =
      StreamCodec.composite(
          ByteBufCodecs.INT,
          SPacketUseTotem::entityId,
          ItemStack.STREAM_CODEC,
          SPacketUseTotem::stack,
          SPacketUseTotem::new);

  public static void handle(SPacketUseTotem msg) {
    ClientPacketHandler.handle(msg);
  }

  @Nonnull
  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
