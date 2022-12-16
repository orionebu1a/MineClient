package net.hackedclient.mixin;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;
import net.hackedclient.HackedClient;
import net.hackedclient.event.EventManager;
import net.hackedclient.event.listeners.PacketInputListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.Future;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(at = @At("TAIL"), method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", cancellable = true)
    public void send(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> callback, CallbackInfo ci) {
        //System.out.println("SEND PACKET: " + packet.getClass().getName());
    }

    @Inject(at = {@At(value = "INVOKE",
            target = "Lnet/minecraft/network/ClientConnection;handlePacket(Lnet/minecraft/network/Packet;Lnet/minecraft/network/listener/PacketListener;)V",
            ordinal = 0)},
            method = {
                    "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V"},
            cancellable = true)
    private void onChannelRead0(ChannelHandlerContext channelHandlerContext,
                                Packet<?> packet, CallbackInfo ci)
    {
        //System.out.println("RECEIVE PACKET: " + packet.getClass().getName());
        PacketInputListener.PacketInputEvent event = new PacketInputListener.PacketInputEvent(packet);
        EventManager.fire(event);

        if(event.isCancelled())
            ci.cancel();
    }
}
