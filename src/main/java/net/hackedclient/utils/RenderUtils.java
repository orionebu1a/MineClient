package net.hackedclient.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import net.hackedclient.HackedClient;
import net.hackedclient.hack.bypass.XRayData;
import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class RenderUtils {

    public static void renderXrayData(ArrayList<XRayData> arrayList, float f2) {
        ArrayList<XRayData> blocksFound = new ArrayList<>(arrayList);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        ClientPlayerEntity entityPlayerSP = HackedClient.INSTANCE.getLocalPlayer();
        double d2 = entityPlayerSP.lastRenderX + (entityPlayerSP.getX() - entityPlayerSP.lastRenderX) * (double) f2;
        double d3 = entityPlayerSP.lastRenderY + (entityPlayerSP.getY() - entityPlayerSP.lastRenderY) * (double) f2;
        double d4 = entityPlayerSP.lastRenderZ + (entityPlayerSP.getZ() - entityPlayerSP.lastRenderZ) * (double) f2;
        for (XRayData xrayData : blocksFound) {
            if (xrayData == null) continue;
            BlockPos blockPos = xrayData.getPosition();
            Block block = xrayData.getBlock();
            if (blockPos == null || block == null) continue;
            try {
                Box axisAlignedBB = BlockUtils.getBoundingBox(blockPos).stretch(0.002f, 0.002f, 0.002f).offset(-d2, -d3, -d4);
                RenderUtils.render(axisAlignedBB);
            } catch (Exception ex) {
                // nothing
            }
        }
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void render(Box axisAlignedBB) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, VertexFormats.POSITION);
        bufferBuilder.vertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.vertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.vertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.vertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.vertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        tessellator.draw();
        bufferBuilder.begin(3, VertexFormats.POSITION);
        bufferBuilder.vertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.vertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.vertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.vertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.vertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        tessellator.draw();
        bufferBuilder.begin(1, VertexFormats.POSITION);
        bufferBuilder.vertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.vertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.vertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.vertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.vertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.vertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.vertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.vertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        tessellator.draw();
    }
}
