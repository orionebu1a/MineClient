package net.hackedclient.hack.bypass;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class XRayData {
    private final BlockPos position;
    private final Block block;

    public XRayData(BlockPos blockPos, Block xRayData) {
        this.position = blockPos;
        this.block = xRayData;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public Block getBlock() {
        return this.block;
    }
}
