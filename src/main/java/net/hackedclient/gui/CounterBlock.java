/*
 * Copyright (c) 2014-2022 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.hackedclient.gui;

import net.hackedclient.HackedClient;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;



public final class CounterBlock
{
    public void render(MatrixStack matrixStack)
    {
        String string = String.valueOf(HackedClient.blockCounter);
        TextRenderer tr = HackedClient.MC.textRenderer;
        // draw version string
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        tr.draw(matrixStack, string, 0, 0, 1000);
    }
}
