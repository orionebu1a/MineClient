package net.hackedclient.mixin;

import net.hackedclient.event.EventManager;
import net.hackedclient.event.listeners.ShouldDrawSideListener.ShouldDrawSideEvent;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {
    @Inject(at = {@At("HEAD")},
            method = {
                    "isSideCovered(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;F)Z"
            },
            cancellable = true)
    private static void onIsSideCovered(BlockView world, BlockPos pos, Direction direction, float maxDeviation, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = world.getBlockState(pos);
        ShouldDrawSideEvent event = new ShouldDrawSideEvent(state);
        EventManager.fire(event);

        if (event.isRendered() != null)
            cir.setReturnValue(!event.isRendered());
    }
}
