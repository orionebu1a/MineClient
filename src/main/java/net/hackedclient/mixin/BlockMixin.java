package net.hackedclient.mixin;

import net.hackedclient.HackedClient;
import net.hackedclient.event.EventManager;
import net.hackedclient.event.listeners.ShouldDrawSideListener;
import net.hackedclient.hack.HackList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin implements ItemConvertible {
    @Inject(at = {@At("HEAD")},
            method = {
                    "shouldDrawSide(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"},
            cancellable = true)
    private static void onShouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> cir) {
        ShouldDrawSideListener.ShouldDrawSideEvent event = new ShouldDrawSideListener.ShouldDrawSideEvent(state);
        EventManager.fire(event);

        if (event.isRendered() != null)
            cir.setReturnValue(event.isRendered());

    }

    @Inject(at = {@At("HEAD")},
            method = {"getVelocityMultiplier()F"},
            cancellable = true)
    private void onGetVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        HackList hax = HackedClient.INSTANCE.getHax();
        if (hax == null)
            return;

        if (cir.getReturnValueF() < 1)
            cir.setReturnValue(1F);
    }
}
