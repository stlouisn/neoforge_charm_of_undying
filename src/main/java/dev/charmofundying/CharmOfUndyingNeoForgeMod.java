package dev.charmofundying;

import dev.charmofundying.common.TotemProviders;
import dev.charmofundying.common.network.CharmOfUndyingClientPayloadHandler;
import dev.charmofundying.common.network.SPacketUseTotem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

@Mod(CharmOfUndyingConstants.MOD_ID)
public class CharmOfUndyingNeoForgeMod {

  public CharmOfUndyingNeoForgeMod(IEventBus eventBus) {
    CharmOfUndyingCommonMod.init();
    eventBus.addListener(this::registerCapabilities);
    eventBus.addListener(this::clientSetup);
    eventBus.addListener(this::registerPayloadHandler);
  }

  private void clientSetup(final FMLClientSetupEvent evt) {
  }

  private void registerPayloadHandler(final RegisterPayloadHandlersEvent evt) {
    evt.registrar(CharmOfUndyingConstants.MOD_ID).playToClient(SPacketUseTotem.TYPE, SPacketUseTotem.STREAM_CODEC, CharmOfUndyingClientPayloadHandler.getInstance()::handleUseTotem);
  }

  private void registerCapabilities(final RegisterCapabilitiesEvent evt) {
    for (Item item : BuiltInRegistries.ITEM) {
      if (TotemProviders.IS_TOTEM.test(item)) {
        evt.registerItem(CuriosCapability.ITEM, (stack, ctx) -> new ICurio() {
          @Override
          public ItemStack getStack() {
            return stack;
          }

          @Override
          public boolean canEquipFromUse(SlotContext ctx) {
            return true;
          }
        }, item);
      }
    }
  }
}