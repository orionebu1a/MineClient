package net.hackedclient.hack.AutoFarm;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalXZ;

public class AutoFarmProcess implements Runnable {
    private volatile boolean isRunning = true;
    public AutoFarmHack autoFarmHack;

    public void intercept() {
        this.isRunning = false;
    }
    AutoFarmProcess(AutoFarmHack autoFarmHack){
        this.autoFarmHack = autoFarmHack;
    }

    @Override
    public void run() {
        BaritoneAPI.getSettings().allowSprint.value = true;
        BaritoneAPI.getSettings().primaryTimeoutMS.value = 2000L;
        while (isRunning) {
            //autoFarmHack.tick();
            BaritoneAPI.getProvider().getPrimaryBaritone().getMineProcess().mineByName(autoFarmHack.getFindBlock().toString());
            System.out.printf("%s\n", autoFarmHack.getState().toString());
        }
    }
}
