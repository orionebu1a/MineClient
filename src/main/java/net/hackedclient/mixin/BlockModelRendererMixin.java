package net.hackedclient.mixin;

import net.hackedclient.event.EventManager;
import net.hackedclient.event.listeners.ShouldDrawSideListener;
import net.hackedclient.event.listeners.TesselateBlockListener;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BlockModelRenderer.class)
public abstract class BlockModelRendererMixin {
    @Inject(at = {@At("HEAD")},
            method = {
                    "renderSmooth(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;ZLjava/util/Random;JI)Z",
                    "renderFlat(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;ZLjava/util/Random;JI)Z",},
            cancellable = true)
    private void onRenderSmoothOrFlat(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, CallbackInfoReturnable<Boolean> cir) {
        TesselateBlockListener.TesselateBlockEvent event = new TesselateBlockListener.TesselateBlockEvent(state);
        EventManager.fire(event);

        if (event.isCancelled()) {
            cir.cancel();
            return;
        }

        if (!cull)
            return;

        ShouldDrawSideListener.ShouldDrawSideEvent event2 = new ShouldDrawSideListener.ShouldDrawSideEvent(state);
        EventManager.fire(event2);
        if (!Boolean.TRUE.equals(event2.isRendered()))
            return;

        renderSmooth(world, model, state, pos, buffer, vertexConsumer, false, random, seed, overlay);
    }

    @Shadow
    public abstract boolean renderSmooth(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay);
}
