package net.hackedclient.hack.bypass;

public class IntegerValue
        extends Value {
    public int min;
    public int max;

    public IntegerValue(String string, int value, int min, int max) {
        super(string, value);
        this.min = min;
        this.max = max;
    }

    public Integer getValue() {
        Number number = (Number) super.getObjectValue();
        return number.intValue();
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    @Override
    public Object getObjectValue() {
        return this.getValue();
    }
}
