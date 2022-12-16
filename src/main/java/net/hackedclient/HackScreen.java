package net.hackedclient;

import net.hackedclient.gui.ClickGui;
import net.hackedclient.gui.FeatureButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;

public class HackScreen extends Screen {

    private final Screen parent;
    public ClickGui GUI;

    public HackScreen(Screen parent) {
        super(new LiteralText("Hack Client"));
        this.parent = parent;
    }

    protected void init() {
        if (GUI == null) {
            GUI = HackedClient.INSTANCE.getGui(this.width, this.height);
        }
        for (FeatureButton button : GUI.getButtons()) {
            System.out.println("BUTTON_Y: " + button.getY());
            ButtonWidget newFeatureButton = new ButtonWidget(
                    button.getX(),
                    button.getY(),
                    button.getWidth(),
                    button.getHeight(),
                    new LiteralText(button.getFeatureText()),
                    (btn) -> {
                        button.handleMouseClick();
                        btn.setMessage(new LiteralText(button.getFeatureText()));
                    }
            );
            this.addButton(newFeatureButton);
        }
        ButtonWidget backButton = new ButtonWidget(
                this.width / 2 - 100,
                this.height / 6 + 138,
                200,
                20,
                ScreenTexts.BACK,
                (button) -> this.client.openScreen(this.parent)
        );
        this.addButton(backButton);
    }
}
