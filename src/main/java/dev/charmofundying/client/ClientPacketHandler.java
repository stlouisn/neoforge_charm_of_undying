package dev.charmofundying.client;

import dev.charmofundying.common.network.SPacketUseTotem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;

public class ClientPacketHandler {

  public static void handle(SPacketUseTotem msg) {
    Minecraft mc = Minecraft.getInstance();
    ClientLevel level = mc.level;
    if (level != null) {
      Entity entity = mc.level.getEntity(msg.entityId());
      if (entity != null) {
        mc.particleEngine.createTrackingEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
        entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, entity.getSoundSource(), 1.0F, 1.0F, false);
        if (entity == mc.player) {
          mc.gameRenderer.displayItemActivation(msg.stack());
        }
      }
    }
  }
}
