package net.hackedclient.settings;

import com.google.gson.JsonElement;
import net.hackedclient.gui.Component;
import net.hackedclient.utils.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;

public final class BlockListSetting extends Setting{

    //private final String defaultName;
    private final ArrayList<String> blockNames = new ArrayList<>();

    public BlockListSetting(String name, String description, String... blocks) {
        super(name, description);
        Arrays.stream(blocks).parallel()
                .map(s -> Registry.BLOCK.get(new Identifier(s))).map(BlockUtils::getName).distinct()
                .sorted().forEachOrdered(blockNames::add);
    }

    public void resetToDefault()
    {
        //blockNames = defaultName;
        //HackedClient.INSTANCE.saveSettings();
    }

    public List<String> getBlockNames() {
        return Collections.unmodifiableList(blockNames);
    }

    @Override
    public Component getComponent() {
        return null;
    }

    @Override
    public void fromJson(JsonElement json) {

    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    public void add(Block block)
    {
        String name = BlockUtils.getName(block);
        if(Collections.binarySearch(blockNames, name) >= 0)
            return;

        blockNames.add(name);
        Collections.sort(blockNames);
        //HackedClient.INSTANCE.saveSettings();
    }

    public void remove(int index)
    {
        if(index < 0 || index >= blockNames.size())
            return;

        blockNames.remove(index);
        //HackedClient.INSTANCE.saveSettings();
    }
}
