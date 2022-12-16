package net.hackedclient;

import com.mojang.authlib.GameProfile;
import net.hackedclient.command.CmdList;
import net.hackedclient.command.CmdProcessor;
import net.hackedclient.event.EventManager;
import net.hackedclient.event.listeners.ChatOutputListener;
import net.hackedclient.event.listeners.GUIRenderListener;
import net.hackedclient.event.listeners.UpdateListener;
import net.hackedclient.gui.ClickGui;
import net.hackedclient.gui.InGameHud;
import net.hackedclient.hack.HackList;
import net.hackedclient.navigator.Navigator;
import net.hackedclient.update.HackedClientUpdater;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.Packet;

public enum HackedClient {


    INSTANCE;

    private Navigator navigator;
    private CmdList cmds;
    private CmdProcessor cmdProcessor;

    private InGameHud hud;
    public static int blockCounter = 0;

    public static final MinecraftClient MC = MinecraftClient.getInstance();
    private HackList hax;
    private ClickGui gui;
    private EventManager eventManager;
    private HackedClientUpdater updater;

    private static boolean guiInitialized;

    public void initialize() {
        System.out.println("Starting Hacked Client...");
        eventManager = new EventManager(this);
        cmds = new CmdList();
        hud = new InGameHud();
        eventManager.add(GUIRenderListener.class, hud);
        hax = new HackList();
        gui = new ClickGui();
        cmdProcessor = new CmdProcessor(cmds);
        eventManager.add(ChatOutputListener.class, cmdProcessor);
        navigator = new Navigator(hax, cmds);
    }

    public boolean isEnabled() {
        return true;
    }

    public CmdProcessor getCmdProcessor()
    {
        return cmdProcessor;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public HackList getHax() {
        return hax;
    }

    public ClickGui getGui(int w, int h) {
        if (!guiInitialized) {
            guiInitialized = true;
            gui.init(w, h);
        }
        return gui;
    }

    public ClientWorld getWorld() {
        return MC.world;
    }

    public ClientPlayerEntity getLocalPlayer() {
        return MC.player;
    }

    public void sendPacket(Packet<?> packet) {
        if (MC.player != null) {
            MC.player.networkHandler.sendPacket(packet);
        }
    }
    public CmdList getCmds()
    {
        return cmds;
    }
    public Navigator getNavigator()
    {
        return navigator;
    }

}
