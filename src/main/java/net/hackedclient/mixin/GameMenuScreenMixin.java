package net.hackedclient.mixin;

import net.hackedclient.HackScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    private HackScreen hackScreen;

    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "initWidgets")
    private void initWidgets(CallbackInfo ci) {
        if (this.hackScreen == null)
            this.hackScreen = new HackScreen(this);
        ButtonWidget hackMenuButton = new ButtonWidget(
                10,
                10,
                90,
                20,
                new LiteralText("Hack Menu"),
                (button) -> this.client.openScreen(hackScreen)
        );
        addButton(hackMenuButton);
    }
}
