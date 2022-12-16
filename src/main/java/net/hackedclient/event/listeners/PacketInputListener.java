package net.hackedclient.event.listeners;

import net.hackedclient.event.CancellableEvent;
import net.minecraft.network.Packet;

import java.util.ArrayList;
import java.util.EventListener;

public interface PacketInputListener extends EventListener
{
    void onReceivedPacket(PacketInputEvent event);

    class PacketInputEvent
            extends CancellableEvent<PacketInputListener>
    {
        private final Packet<?> packet;

        public PacketInputEvent(Packet<?> packet)
        {
            this.packet = packet;
        }

        public Packet<?> getPacket()
        {
            return packet;
        }

        @Override
        public void fire(ArrayList<PacketInputListener> listeners)
        {
            for(PacketInputListener listener : listeners)
            {
                listener.onReceivedPacket(this);

                if(isCancelled())
                    break;
            }
        }

        @Override
        public Class<PacketInputListener> getListenerType()
        {
            return PacketInputListener.class;
        }
    }
}
