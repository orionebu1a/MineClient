package net.hackedclient.mixin;

import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.hackedclient.event.EventManager;
import net.hackedclient.event.listeners.TesselateBlockListener;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TerrainRenderContext.class)
public class TerrainRenderContextMixin {
    @Inject(at = {@At("HEAD")},
            method = {"tesselateBlock"},
            cancellable = true,
            remap = false)
    private void tesselateBlock(BlockState blockState, BlockPos blockPos,
                                final BakedModel model, MatrixStack matrixStack,
                                CallbackInfoReturnable<Boolean> cir) {
        TesselateBlockListener.TesselateBlockEvent event = new TesselateBlockListener.TesselateBlockEvent(blockState);
        EventManager.fire(event);

        if (event.isCancelled())
            cir.cancel();
    }
}
