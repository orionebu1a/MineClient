package net.hackedclient.event.listeners;

import net.hackedclient.event.CancellableEvent;
import net.minecraft.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.EventListener;


public interface RenderBlockEntityListener extends EventListener {
    void onRenderBlockEntity(RenderBlockEntityEvent event);

    class RenderBlockEntityEvent
            extends CancellableEvent<RenderBlockEntityListener> {
        private final BlockEntity blockEntity;

        public RenderBlockEntityEvent(BlockEntity blockEntity) {
            this.blockEntity = blockEntity;
        }

        public BlockEntity getBlockEntity() {
            return blockEntity;
        }

        @Override
        public void fire(ArrayList<RenderBlockEntityListener> listeners) {
            for (RenderBlockEntityListener listener : listeners) {
                listener.onRenderBlockEntity(this);

                if (isCancelled())
                    break;
            }
        }

        @Override
        public Class<RenderBlockEntityListener> getListenerType() {
            return RenderBlockEntityListener.class;
        }
    }
}