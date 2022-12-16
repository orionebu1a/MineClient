package net.hackedclient.hack;

import net.hackedclient.Category;
import net.hackedclient.SearchTags;
import net.hackedclient.event.listeners.*;
import net.hackedclient.mixinterfaces.IGammaOption;
import net.hackedclient.settings.BlockListSetting;
import net.hackedclient.utils.BlockUtils;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.Collections;
@SearchTags({"XRay", "x ray", "OreFinder", "ore finder"})

public final class XRayHack extends Hack implements UpdateListener,
        SetOpaqueCubeListener, GetAmbientOcclusionLightLevelListener,
        ShouldDrawSideListener, TesselateBlockListener, RenderBlockEntityListener {
    private final BlockListSetting ores = new BlockListSetting("Ores", "",
            "minecraft:ancient_debris", "minecraft:anvil", "minecraft:beacon",
            "minecraft:bone_block", "minecraft:bookshelf",
            "minecraft:brewing_stand", "minecraft:chain_command_block",
            "minecraft:chest", "minecraft:clay", "minecraft:coal_block",
            "minecraft:coal_ore", "minecraft:command_block", "minecraft:copper_ore",
            "minecraft:crafting_table", "minecraft:deepslate_coal_ore",
            "minecraft:deepslate_copper_ore", "minecraft:deepslate_diamond_ore",
            "minecraft:deepslate_gold_ore", "minecraft:deepslate_iron_ore",
            "minecraft:deepslate_lapis_ore", "minecraft:deepslate_redstone_ore",
            "minecraft:diamond_block", "minecraft:diamond_ore",
            "minecraft:dispenser", "minecraft:dropper", "minecraft:emerald_block",
            "minecraft:emerald_ore", "minecraft:enchanting_table",
            "minecraft:end_portal", "minecraft:end_portal_frame",
            "minecraft:ender_chest", "minecraft:furnace", "minecraft:glowstone",
            "minecraft:gold_block", "minecraft:gold_ore", "minecraft:hopper",
            "minecraft:iron_block", "minecraft:iron_ore", "minecraft:ladder",
            "minecraft:lapis_block", "minecraft:lapis_ore", "minecraft:lava",
            "minecraft:lodestone", "minecraft:mossy_cobblestone",
            "minecraft:nether_gold_ore", "minecraft:nether_portal",
            "minecraft:nether_quartz_ore", "minecraft:raw_copper_block",
            "minecraft:raw_gold_block", "minecraft:raw_iron_block",
            "minecraft:redstone_block", "minecraft:redstone_ore",
            "minecraft:repeating_command_block", "minecraft:spawner",
            "minecraft:tnt", "minecraft:torch", "minecraft:trapped_chest",
            "minecraft:water");

    private ArrayList<String> oreNames;

    public XRayHack() {
        super("X-Ray");
        setCategory(Category.RENDER);
        addSetting(ores);
    }

    @Override
    public void onEnable() {
        oreNames = new ArrayList<>(ores.getBlockNames());

        EVENTS.add(UpdateListener.class, this);
        EVENTS.add(SetOpaqueCubeListener.class, this);
        EVENTS.add(GetAmbientOcclusionLightLevelListener.class, this);
        EVENTS.add(ShouldDrawSideListener.class, this);
        EVENTS.add(TesselateBlockListener.class, this);
        EVENTS.add(RenderBlockEntityListener.class, this);
        MC.worldRenderer.reload();
    }

    @Override
    public void onDisable() {
        EVENTS.remove(UpdateListener.class, this);
        EVENTS.remove(SetOpaqueCubeListener.class, this);
        EVENTS.remove(GetAmbientOcclusionLightLevelListener.class, this);
        EVENTS.remove(ShouldDrawSideListener.class, this);
        EVENTS.remove(TesselateBlockListener.class, this);
        EVENTS.remove(RenderBlockEntityListener.class, this);
        MC.worldRenderer.reload();
    }

    @Override
    public void onUpdate() {
        IGammaOption gammaOption = (IGammaOption) MC.options;
        gammaOption.forceSetGamma(16.0);
    }

    @Override
    public void onSetOpaqueCube(SetOpaqueCubeEvent event) {
        event.cancel();
    }

    @Override
    public void onGetAmbientOcclusionLightLevel(
            GetAmbientOcclusionLightLevelEvent event) {
        event.setLightLevel(1);
    }

    @Override
    public void onShouldDrawSide(ShouldDrawSideEvent event) {
        event.setRendered(isVisible(event.getState().getBlock()));
    }

    @Override
    public void onTesselateBlock(TesselateBlockEvent event) {
        if (!isVisible(event.getState().getBlock()))
            event.cancel();
    }

    @Override
    public void onRenderBlockEntity(RenderBlockEntityEvent event) {
        if (!isVisible(BlockUtils.getBlock(event.getBlockEntity().getPos())))
            event.cancel();
    }

    public boolean isVisible(Block block) {
        String name = BlockUtils.getName(block);
        int index = Collections.binarySearch(oreNames, name);
        return index >= 0;
    }
}
