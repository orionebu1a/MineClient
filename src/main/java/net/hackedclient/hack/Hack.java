package net.hackedclient.hack;

import net.hackedclient.Category;
import net.hackedclient.Feature;

import java.util.Objects;

public abstract class Hack extends Feature {
    private final String name;
    private Category category;

    private boolean enabled;

    public Hack(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Category getCategory() {
        return category;
    }

    protected final void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean enabled) {
        if (this.enabled == enabled)
            return;

        this.enabled = enabled;

        if (enabled)
            onEnable();
        else
            onDisable();
    }

    @Override
    public final String getPrimaryAction() {
        return enabled ? " Enabled" : " Disabled";
    }

    @Override
    public final void doPrimaryAction() {
        setEnabled(!enabled);
    }

    protected void onEnable() {

    }

    protected void onDisable() {

    }
}
