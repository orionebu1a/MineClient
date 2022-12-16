package net.hackedclient.event.listeners;

import net.hackedclient.event.Event;
import net.minecraft.block.BlockState;

import java.util.ArrayList;
import java.util.EventListener;

public interface ShouldDrawSideListener extends EventListener {
    void onShouldDrawSide(ShouldDrawSideEvent event);

    class ShouldDrawSideEvent
            extends Event<ShouldDrawSideListener> {
        private final BlockState state;
        private Boolean rendered;

        public ShouldDrawSideEvent(BlockState state) {
            this.state = state;
        }

        public BlockState getState() {
            return state;
        }

        public Boolean isRendered() {
            return rendered;
        }

        public void setRendered(boolean rendered) {
            this.rendered = rendered;
        }

        @Override
        public void fire(ArrayList<ShouldDrawSideListener> listeners) {
            for (ShouldDrawSideListener listener : listeners)
                listener.onShouldDrawSide(this);
        }

        @Override
        public Class<ShouldDrawSideListener> getListenerType() {
            return ShouldDrawSideListener.class;
        }
    }
}
