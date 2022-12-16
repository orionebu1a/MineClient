package net.hackedclient.hack.bypass;

public class Value {
    public Object c;
    private final String name;
    private final Object b;

    public Value(String string, Object object) {
        this.name = string;
        this.b = object;
        this.c = object;
    }

    public String getName() {
        return this.name;
    }

    public Object getDefaultValue() {
        return this.b;
    }

    public Object getObjectValue() {
        return this.c;
    }

    public void setValue(Object object) {
        this.c = object;
    }
}
