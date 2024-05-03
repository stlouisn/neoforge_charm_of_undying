package com.illusivesoulworks.charmofundying.common.network;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CharmOfUndyingClientPayloadHandler {

  private static final CharmOfUndyingClientPayloadHandler INSTANCE =
      new CharmOfUndyingClientPayloadHandler();

  public static CharmOfUndyingClientPayloadHandler getInstance() {
    return INSTANCE;
  }

  public void handleUseTotem(SPacketUseTotem msg, IPayloadContext ctx) {
    ctx.enqueueWork(() -> SPacketUseTotem.handle(msg))
        .exceptionally(e -> {
          ctx.disconnect(
              Component.translatable("charmofundying.networking.failed", e.getMessage()));
          return null;
        });
  }
}
