package com.illusivesoulworks.charmofundying;

import com.illusivesoulworks.spectrelib.config.SpectreConfigInitializer;

public class CharmOfUndyingConfigInitializer implements SpectreConfigInitializer {

  @Override
  public void onInitializeConfig() {
    CharmOfUndyingConfig.setup();
  }
}
