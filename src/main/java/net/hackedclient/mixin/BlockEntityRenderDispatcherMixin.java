package net.hackedclient.mixin;

import net.hackedclient.event.EventManager;
import net.hackedclient.event.listeners.RenderBlockEntityListener;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {
    @Inject(at = {@At("HEAD")},
            method = {
                    "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"},
            cancellable = true)
    private <E extends BlockEntity> void onRender(E blockEntity_1,
                                                  float float_1, MatrixStack matrixStack_1,
                                                  VertexConsumerProvider vertexConsumerProvider_1, CallbackInfo ci) {
        RenderBlockEntityListener.RenderBlockEntityEvent event =
                new RenderBlockEntityListener.RenderBlockEntityEvent(blockEntity_1);
        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
    }
}
