package dev.charmofundying.common.network;

import dev.charmofundying.CharmOfUndyingConstants;
import dev.charmofundying.client.ClientPacketHandler;
import javax.annotation.Nonnull;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record SPacketUseTotem(int entityId, ItemStack stack) implements CustomPacketPayload {

  public static final Type<SPacketUseTotem> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CharmOfUndyingConstants.MOD_ID, "use_totem"));
  public static final StreamCodec<RegistryFriendlyByteBuf, SPacketUseTotem> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT,
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
