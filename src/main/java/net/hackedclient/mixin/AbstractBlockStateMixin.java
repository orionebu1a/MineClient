package net.hackedclient.mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.hackedclient.HackedClient;
import net.hackedclient.event.EventManager;
import net.hackedclient.event.listeners.GetAmbientOcclusionLightLevelListener;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin extends State<Block, BlockState> {
    private AbstractBlockStateMixin(HackedClient wurst, Block object,
                                    ImmutableMap<Property<?>, Comparable<?>> immutableMap,
                                    MapCodec<BlockState> mapCodec) {
        super(object, immutableMap, mapCodec);
    }

    @Inject(at = @At("TAIL"),
            method = {
                    "getAmbientOcclusionLightLevel(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F"},
            cancellable = true)
    private void onGetAmbientOcclusionLightLevel(BlockView blockView,
                                                 BlockPos blockPos, CallbackInfoReturnable<Float> cir) {
        GetAmbientOcclusionLightLevelListener.GetAmbientOcclusionLightLevelEvent event =
                new GetAmbientOcclusionLightLevelListener.GetAmbientOcclusionLightLevelEvent((BlockState) (Object) this,
                        cir.getReturnValueF());

        EventManager.fire(event);
        cir.setReturnValue(event.getLightLevel());
    }
}
