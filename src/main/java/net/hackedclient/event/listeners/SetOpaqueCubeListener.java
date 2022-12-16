package net.hackedclient.event.listeners;

import net.hackedclient.event.CancellableEvent;

import java.util.ArrayList;
import java.util.EventListener;

public interface SetOpaqueCubeListener extends EventListener {
    void onSetOpaqueCube(SetOpaqueCubeEvent event);

    class SetOpaqueCubeEvent
            extends CancellableEvent<SetOpaqueCubeListener> {
        @Override
        public void fire(ArrayList<SetOpaqueCubeListener> listeners) {
            for (SetOpaqueCubeListener listener : listeners) {
                listener.onSetOpaqueCube(this);

                if (isCancelled())
                    break;
            }
        }

        @Override
        public Class<SetOpaqueCubeListener> getListenerType() {
            return SetOpaqueCubeListener.class;
        }
    }
}
