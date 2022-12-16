package net.hackedclient.hack;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalXZ;
import net.minecraft.client.MinecraftClient;

public class WoodFarmProcess implements Runnable {

    private final MinecraftClient MC;
    private volatile boolean isRunning = true;

    public void intercept() {
        this.isRunning = false;
    }
    WoodFarmProcess(AutoFarmWoodHack autoFarmWoodHack){
        this.MC = autoFarmWoodHack.MC;
        this.autoFarmWoodHack = autoFarmWoodHack;
    }
    public AutoFarmWoodHack autoFarmWoodHack;
    @Override
    public void run() {
        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalXZ(10000, 20000));
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (isRunning) {
            try {
                Thread.sleep(12);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            autoFarmWoodHack.cycle();
        }*/

    }
}