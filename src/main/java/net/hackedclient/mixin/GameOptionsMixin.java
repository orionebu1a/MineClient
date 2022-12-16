package net.hackedclient.mixin;

import net.hackedclient.mixinterfaces.IGammaOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

@Mixin(GameOptions.class)
public class GameOptionsMixin implements IGammaOption {
    @Shadow
    public double gamma;

    @Override
    public void forceSetGamma(double newValue) {
        if (!MinecraftClient.getInstance().isRunning()) {
            gamma = newValue;
            return;
        }

        if (!Objects.equals(gamma, newValue)) {
            gamma = newValue;
        }
    }

}
