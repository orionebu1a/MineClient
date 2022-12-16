package net.hackedclient.event.listeners;

import net.hackedclient.event.Event;

import java.util.ArrayList;
import java.util.EventListener;

public interface SearchTreeListener extends EventListener{
    void SearchTree(SearchTreeListenerEvent event);

    class SearchTreeListenerEvent extends Event<SearchTreeListener> {
        public static final UpdateListener.UpdateEvent INSTANCE = new UpdateListener.UpdateEvent();

        @Override
        public void fire(ArrayList<SearchTreeListener> listeners) {
            for (SearchTreeListener listener : listeners)
                listener.SearchTree(this);
        }

        @Override
        public Class<SearchTreeListener> getListenerType() {
            return SearchTreeListener.class;
        }
    }
}


