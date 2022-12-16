package net.hackedclient.hack;

import net.hackedclient.HackedClient;
import net.hackedclient.event.EventManager;
import net.hackedclient.event.listeners.UpdateListener;
import net.hackedclient.hack.AutoFarm.AutoFarmHack;
import net.hackedclient.hack.bypass.XRayBypass;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;

public final class HackList implements UpdateListener {
    public final FlyHack flyHack = new FlyHack();
    public final XRayHack xRayHack = new XRayHack();
    public final XRayBypass xRayBypassHack = new XRayBypass();
    public final AutoFarmWoodHack autoFarmWoodHack = new AutoFarmWoodHack();
    public final AutoFarmHack autoFarmHack = new AutoFarmHack();


    private final TreeMap<String, Hack> hax =
            new TreeMap<>(String::compareToIgnoreCase);

    private final EventManager eventManager = HackedClient.INSTANCE.getEventManager();

    public HackList() {
        try {
            for (Field field : HackList.class.getDeclaredFields()) {
                if (!field.getName().endsWith("Hack")) {
                    continue;
                }
                Hack hack = (Hack) field.get(this);
                hax.put(hack.getName(), hack);
            }
        } catch (Exception e) {
            String message = "Initializing Wurst hacks";
            CrashReport report = CrashReport.create(e, message);
            throw new CrashException(report);
        }
        eventManager.add(UpdateListener.class, this);
    }

    @Override
    public void onUpdate() {
        eventManager.remove(UpdateListener.class, this);
    }

    public Collection<Hack> getAllHax() {
        return Collections.unmodifiableCollection(hax.values());
    }
}
