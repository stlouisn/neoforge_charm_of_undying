

package dev.charmofundying.platform;

import dev.charmofundying.platform.services.IClientPlatform;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class NeoForgeClientPlatform implements IClientPlatform {

  @Override
  public void translateToPosition(LivingEntity livingEntity,
                                  EntityModel<? extends LivingEntity> model, PoseStack poseStack) {
    ICurioRenderer.translateIfSneaking(poseStack, livingEntity);
    ICurioRenderer.rotateIfSneaking(poseStack, livingEntity);
    poseStack.translate(0.0F, 0.4F, -0.15F);
  }
}
