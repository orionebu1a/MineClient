package net.hackedclient.gui;

import net.hackedclient.Feature;
import net.hackedclient.HackedClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public final class ClickGui {
    private static final HackedClient HACKED_CLIENT = HackedClient.INSTANCE;
    private int widthCounter = 0;
    private int heightCounter = 0;

    private final ArrayList<FeatureButton> buttons = new ArrayList<>();

    public void init(int w, int h) {

        ArrayList<Feature> features = new ArrayList<>(HACKED_CLIENT.getHax().getAllHax());

        for (Feature f : features) {
            FeatureButton fButton = new FeatureButton(f, widthCounter, heightCounter);
            buttons.add(fButton);
            if (heightCounter + fButton.getHeight() >= h) {
                widthCounter += fButton.getWidth() + 4;
                heightCounter = 0;
            } else {
                heightCounter += fButton.getHeight() + 4;
            }
        }
    }

    public Collection<FeatureButton> getButtons() {
        return Collections.unmodifiableCollection(buttons);
    }
}
