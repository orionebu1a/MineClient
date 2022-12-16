package net.hackedclient;

import net.fabricmc.api.ModInitializer;

public class ClientInitializer implements ModInitializer {
	private static boolean initialized = false;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		if (initialized) {
			throw new RuntimeException(
					"ClientInitializer.onInitialize() ran twice!");
		}
		HackedClient.INSTANCE.initialize();
		initialized = true;
	}
}
