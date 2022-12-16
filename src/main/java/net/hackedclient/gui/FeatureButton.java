package net.hackedclient.gui;

import net.hackedclient.Feature;
import net.hackedclient.HackedClient;
import net.minecraft.client.MinecraftClient;

import java.util.Objects;

public final class FeatureButton extends Component {
    private final MinecraftClient MC = HackedClient.MC;

    private final Feature feature;

    public FeatureButton(Feature feature, int x, int y) {
        this.feature = Objects.requireNonNull(feature);
        setX(x);
        setY(y);
    }

    @Override
    public void handleMouseClick() {
        feature.doPrimaryAction();
    }

    public String getFeatureText() {
        return feature.getName() + feature.getPrimaryAction();
    }
}
