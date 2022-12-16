package net.hackedclient.event;

import java.util.ArrayList;
import java.util.EventListener;

public abstract class Event<T extends EventListener> {
    public abstract void fire(ArrayList<T> listeners);

    public abstract Class<T> getListenerType();
}
