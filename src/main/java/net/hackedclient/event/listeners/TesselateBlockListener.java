package net.hackedclient.event.listeners;

import net.hackedclient.event.CancellableEvent;
import net.minecraft.block.BlockState;

import java.util.ArrayList;
import java.util.EventListener;

public interface TesselateBlockListener extends EventListener {
    void onTesselateBlock(TesselateBlockEvent event);

    class TesselateBlockEvent
            extends CancellableEvent<TesselateBlockListener> {
        private final BlockState state;

        public TesselateBlockEvent(BlockState state) {
            this.state = state;
        }

        public BlockState getState() {
            return state;
        }

        @Override
        public void fire(ArrayList<TesselateBlockListener> listeners) {
            for (TesselateBlockListener listener : listeners) {
                listener.onTesselateBlock(this);

                if (isCancelled())
                    break;
            }
        }

        @Override
        public Class<TesselateBlockListener> getListenerType() {
            return TesselateBlockListener.class;
        }
    }
}
