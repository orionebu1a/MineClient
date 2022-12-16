/*
 * Copyright (c) 2014-2022 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.hackedclient.gui;

import net.hackedclient.HackedClient;
import net.hackedclient.event.listeners.GUIRenderListener;

import net.minecraft.client.util.math.MatrixStack;

public final class InGameHud implements GUIRenderListener
{

    private final CounterBlock counterBlock = new CounterBlock();

    @Override
    public void onRenderGUI(MatrixStack matrixStack, float partialTicks)
    {
        if(!HackedClient.INSTANCE.isEnabled())
            return;

        counterBlock.render(matrixStack);
    }
}