package net.hackedclient.event.listeners;

import net.hackedclient.event.CancellableEvent;

import java.util.ArrayList;
import java.util.EventListener;

public interface RenderWorldLastEventListener extends EventListener {
    void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent);

    class RenderWorldLastEvent extends CancellableEvent<RenderWorldLastEventListener> {
        public float tickDelta;

        public RenderWorldLastEvent(float tickDelta) {
            this.tickDelta = tickDelta;
        }

        @Override
        public void fire(ArrayList<RenderWorldLastEventListener> listeners) {
            for (RenderWorldLastEventListener listener : listeners) {
                listener.onRenderWorldLastEvent(this);

                if (isCancelled())
                    break;
            }
        }

        @Override
        public Class<RenderWorldLastEventListener> getListenerType() {
            return RenderWorldLastEventListener.class;
        }
    }
}
