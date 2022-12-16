package net.hackedclient.event;

import net.hackedclient.HackedClient;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;

import java.util.*;

public final class EventManager {
    private final HackedClient hackedClient;
    private final HashMap<Class<? extends EventListener>, ArrayList<? extends EventListener>> listenerMap =
            new HashMap<>();

    public EventManager(HackedClient hackedClient) {
        this.hackedClient = hackedClient;
    }

    /**
     * Fires the given {@link Event} if Wurst is enabled and the
     * {@link EventManager} is ready to accept events. This method is safe to
     * call even when the EventManager hasn't been initialized yet.
     */
    public static <L extends EventListener, E extends Event<L>> void fire(E event) {
        EventManager eventManager = HackedClient.INSTANCE.getEventManager();
        if (eventManager == null)
            return;

        eventManager.fireImpl(event);
    }

    private <L extends EventListener, E extends Event<L>> void fireImpl(E event) {
        if (!hackedClient.isEnabled())
            return;

        try {
            Class<L> type = event.getListenerType();
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>) listenerMap.get(type);

            if (listeners == null || listeners.isEmpty())
                return;

            // Creating a copy of the list to avoid concurrent modification issues.
            ArrayList<L> copyOfListeners = new ArrayList<>(listeners);

            // remove() sets an element to null before removing it. When one
            // thread calls remove() while another calls fire(), it is possible
            // for this list to contain null elements, which need to be filtered
            // out.
            copyOfListeners.removeIf(Objects::isNull);

            event.fire(copyOfListeners);

        } catch (Throwable e) {
            e.printStackTrace();

            CrashReport report = CrashReport.create(e, "Firing some event");
            CrashReportSection section = report.addElement("Affected event");
            section.add("Event class", () -> event.getClass().getName());

            throw new CrashException(report);
        }
    }

    public <L extends EventListener> void add(Class<L> type, L listener) {
        try {
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>) listenerMap.get(type);

            if (listeners == null) {
                listeners = new ArrayList<>(Arrays.asList(listener));
                listenerMap.put(type, listeners);
                return;
            }

            listeners.add(listener);

        } catch (Throwable e) {
            e.printStackTrace();

            CrashReport report =
                    CrashReport.create(e, "Adding some event listener");
            CrashReportSection section = report.addElement("Affected listener");
            section.add("Listener type", type::getName);
            section.add("Listener class", () -> listener.getClass().getName());

            throw new CrashException(report);
        }
    }

    public <L extends EventListener> void remove(Class<L> type, L listener) {
        try {
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>) listenerMap.get(type);

            if (listeners != null)
                listeners.remove(listener);

        } catch (Throwable e) {
            e.printStackTrace();

            CrashReport report =
                    CrashReport.create(e, "Removing some event listener");
            CrashReportSection section = report.addElement("Affected listener");
            section.add("Listener type", type::getName);
            section.add("Listener class", () -> listener.getClass().getName());

            throw new CrashException(report);
        }
    }
}
