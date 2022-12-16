package net.hackedclient.mixin;

/*
 * Copyright (c) 2014-2022 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

import net.hackedclient.HackedClient;
import net.hackedclient.event.EventManager;
import net.hackedclient.event.listeners.GUIRenderListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(InGameHud.class)
public class GameHudMixin extends DrawableHelper
{
    @Inject(
            at = {@At(value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V",
                    ordinal = 4)},
            method = {"render(Lnet/minecraft/client/util/math/MatrixStack;F)V"})
    private void onRender(MatrixStack matrixStack, float partialTicks,
                          CallbackInfo ci)
    {
        if(HackedClient.MC.options.debugEnabled)
            return;
        GUIRenderListener.GUIRenderEvent event = new GUIRenderListener.GUIRenderEvent(matrixStack, partialTicks);
        EventManager.fire(event);
    }
}

