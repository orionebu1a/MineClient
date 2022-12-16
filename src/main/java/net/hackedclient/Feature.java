package net.hackedclient;

import net.hackedclient.event.EventManager;
import net.hackedclient.settings.Setting;
import net.minecraft.client.MinecraftClient;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Feature {
    protected static final HackedClient HACKED_CLIENT = HackedClient.INSTANCE;
    protected static final EventManager EVENTS = HACKED_CLIENT.getEventManager();
    public static final MinecraftClient MC = HackedClient.MC;
    private final LinkedHashMap<String, Setting> settings =
            new LinkedHashMap<>();

    public abstract String getName();

    public Category getCategory() {
        return null;
    }

    public abstract String getPrimaryAction();

    public void doPrimaryAction() {
    }

    public boolean isEnabled() {
        return false;
    }

    protected final void addSetting(Setting setting)
    {
        String key = setting.getName().toLowerCase();

        if(settings.containsKey(key))
            throw new IllegalArgumentException(
                    "Duplicate setting: " + getName() + " " + key);

        settings.put(key, setting);
    }

    public final Map<String, Setting> getSettings()
    {
        return Collections.unmodifiableMap(settings);
    }

}
