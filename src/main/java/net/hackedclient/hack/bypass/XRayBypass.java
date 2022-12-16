package net.hackedclient.hack.bypass;

import net.hackedclient.HackedClient;
import net.hackedclient.event.listeners.PacketInputListener;
import net.hackedclient.event.listeners.RenderWorldLastEventListener;
import net.hackedclient.hack.Hack;
import net.hackedclient.utils.RenderUtils;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerActionResponseS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.HashSet;

public class XRayBypass extends Hack implements RenderWorldLastEventListener,
        PacketInputListener {
    public IntegerValue horizontalDistance;
    public HashSet<BlockPos> CheckedPosition = new HashSet<>();
    public IntegerValue verticalDistance;
    public long delayMs = 12;

    public long radius = 30;
    public int skip = 2;
    public XRayBypassProcess runnableProcess;
    public Thread xrayThread;
    public ArrayList<XRayData> blocksFound;
    public int counter = 0;

    public XRayBypass() {
        super("XRay-Bypass");
        this.horizontalDistance = new IntegerValue("Horizontal distance", 10, 4, 100);
        this.verticalDistance = new IntegerValue("Vertical distance", 10, 4, 100);
    }

    @Override
    public void onEnable() {
        runProcessRendering();
        EVENTS.add(RenderWorldLastEventListener.class, this);
        EVENTS.add(PacketInputListener.class, this);
    }

    @Override
    public void onDisable() {
        this.close();
        EVENTS.remove(RenderWorldLastEventListener.class, this);
        EVENTS.remove(PacketInputListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent) {
        if (this.blocksFound == null || this.blocksFound.isEmpty()) {
            return;
        }
        RenderUtils.renderXrayData(this.blocksFound, renderWorldLastEvent.tickDelta);
    }

    public void runProcessRendering() {
        if (this.runnableProcess != null && this.xrayThread != null) {
            return;
        }
        this.blocksFound = new ArrayList<>();
        this.runnableProcess = new XRayBypassProcess(this);
        this.xrayThread = new Thread(this.runnableProcess);
        this.xrayThread.start();
    }

    public void close() {
        if (this.runnableProcess != null) {
            this.runnableProcess.intercept();
        }
        this.runnableProcess = null;
        this.xrayThread = null;
    }

    public void sendInteractionPackets(BlockPos blockPos) {//
        if(HACKED_CLIENT.getHax().xRayBypassHack.counter == 10){
            HackedClient.INSTANCE.sendPacket(new PlayerActionC2SPacket(
                    PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, blockPos, Direction.SOUTH));
            HACKED_CLIENT.getHax().xRayBypassHack.counter = 0;
            System.out.printf("First packet\n");
        }
        else{
            HackedClient.INSTANCE.sendPacket(new PlayerActionC2SPacket(
                    PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, blockPos, Direction.SOUTH));
            System.out.printf("Second packet\n");
        }
        System.out.printf("%d, %d, %d\n", blockPos.getX(), blockPos.getY(), blockPos.getZ() );
    }

    public void addBlockData(BlockState iBlockState, BlockPos blockPos) {
        if (HACKED_CLIENT.getHax().xRayHack.isVisible(iBlockState.getBlock())) {
            XRayData xRayData = new XRayData(blockPos, iBlockState.getBlock());
            this.blocksFound.add(xRayData);
            System.out.printf("%s\n", iBlockState.getBlock().getName().toString());
        }
    }

    @Override
    public void onReceivedPacket(PacketInputEvent event) {
        if (event.getPacket() instanceof PlayerActionResponseS2CPacket) {
            PlayerActionResponseS2CPacket packet = (PlayerActionResponseS2CPacket) event.getPacket();
            this.addBlockData(packet.getBlockState(), packet.getBlockPos());
        }
        if (event.getPacket() instanceof BlockUpdateS2CPacket) {
            BlockUpdateS2CPacket sPacketBlockChange = (BlockUpdateS2CPacket) event.getPacket();
            this.addBlockData(sPacketBlockChange.getState(), sPacketBlockChange.getPos());
        }
    }
}
