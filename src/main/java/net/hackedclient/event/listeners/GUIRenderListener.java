/*
 * Copyright (c) 2014-2022 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.hackedclient.event.listeners;

import java.util.ArrayList;
import java.util.EventListener;

import net.hackedclient.event.CancellableEvent;
import net.minecraft.client.util.math.MatrixStack;

public interface GUIRenderListener extends EventListener
{
    void onRenderGUI(MatrixStack matrixStack, float partialTicks);

    class GUIRenderEvent extends CancellableEvent<GUIRenderListener>
    {
        private final float partialTicks;
        private final MatrixStack matrixStack;

        public GUIRenderEvent(MatrixStack matrixStack, float partialTicks)
        {
            this.matrixStack = matrixStack;
            this.partialTicks = partialTicks;
        }

        @Override
        public void fire(ArrayList<GUIRenderListener> listeners)
        {
            for(GUIRenderListener listener : listeners)
                listener.onRenderGUI(matrixStack, partialTicks);
        }

        @Override
        public Class<GUIRenderListener> getListenerType()
        {
            return GUIRenderListener.class;
        }
    }
}