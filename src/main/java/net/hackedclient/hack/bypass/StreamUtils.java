package net.hackedclient.hack.bypass;

import net.hackedclient.HackedClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.stream.Stream;

public class StreamUtils {

    public static Stream<?> createBlockStream(int x_length, int y_length, int z_length) {
        if (HackedClient.INSTANCE.getLocalPlayer() == null || HackedClient.INSTANCE.getWorld() == null) {
            return null;
        }
        ClientPlayerEntity entityPlayerSP = HackedClient.INSTANCE.getLocalPlayer();
        BlockPos blockPos = new BlockPos(entityPlayerSP.getX() - (double) x_length, entityPlayerSP.getY() - (double) y_length, entityPlayerSP.getZ() - (double) z_length);
        BlockPos blockPos2 = new BlockPos(entityPlayerSP.getX() + (double) x_length, entityPlayerSP.getY() + (double) y_length, entityPlayerSP.getZ() + (double) z_length);
        System.out.println("X:" + blockPos.getX() + "Y:" + blockPos.getY() + "Z:" + blockPos.getZ());
        System.out.println("X:" + blockPos2.getX() + "Y:" + blockPos2.getY() + "Z:" + blockPos2.getZ());
        return BlockPos.stream(blockPos, blockPos2);
    }
}
