package net.hackedclient.hack.bypass;

import net.hackedclient.HackedClient;
import net.minecraft.util.math.BlockPos;

public class XRayBypassProcess implements Runnable {
    private BlockPos playerPosition;
    private volatile boolean isRunning = true;
    public final XRayBypass bypassHack;

    public XRayBypassProcess(XRayBypass xRayBypass) {
        this.bypassHack = xRayBypass;
    }

    public void intercept() {
        this.isRunning = false;
    }

    @Override
    public void run() {
        if (!this.isRunning) {
            return;
        }
        playerPosition = HackedClient.MC.player.getBlockPos();
        int r = 3;
        while (isRunning) {
            if(checkCube(r) && r < bypassHack.radius) {
                r++;
            }
            else{
                r = 3;
                playerPosition = HackedClient.MC.player.getBlockPos();
            }
        }
    }


    public boolean checkCube(int r) {
        int x, y, z;
        y = r;
        for (x = -r; x <= r; x = x + bypassHack.skip) {
            for (z = -r; z <= r; z = z + bypassHack.skip) {
                if (HackedClient.INSTANCE.getLocalPlayer() == null || HackedClient.INSTANCE.getWorld() == null || !this.isRunning) {
                    this.bypassHack.close();
                    return false;
                }
                if (playerPosition != HackedClient.MC.player.getBlockPos()) {
                    return false;
                }
                doBreak(x, y, z);
                System.out.printf("y = %d , r = %d\n", y, r);
            }
        }
        y = -r;
        for (x = -r; x <= r; x = x + bypassHack.skip) {
            for (z = -r; z <= r; z = z + bypassHack.skip) {
                if (HackedClient.INSTANCE.getLocalPlayer() == null || HackedClient.INSTANCE.getWorld() == null || !this.isRunning) {
                    this.bypassHack.close();
                    return false;
                }
                if (playerPosition != HackedClient.MC.player.getBlockPos()) {
                    return false;
                }
                doBreak(x, y, z);
                System.out.printf("y = %d , r = %d\n", y, r);
            }
        }
        x = r;
        for (y = -r; y <= r; y = y + bypassHack.skip) {
            for (z = -r; z <= r; z = z + bypassHack.skip) {
                if (HackedClient.INSTANCE.getLocalPlayer() == null || HackedClient.INSTANCE.getWorld() == null || !this.isRunning) {
                    this.bypassHack.close();
                    return false;
                }
                if (playerPosition != HackedClient.MC.player.getBlockPos()) {
                    return false;
                }
                doBreak(x, y, z);
                System.out.printf("y = %d , r = %d\n", y, r);
            }
        }
        x = -r;
        for (y = -r; y <= r; y = y + bypassHack.skip) {
            for (z = -r; z <= r; z = z + bypassHack.skip) {
                if (HackedClient.INSTANCE.getLocalPlayer() == null || HackedClient.INSTANCE.getWorld() == null || !this.isRunning) {
                    this.bypassHack.close();
                    return false;
                }
                if (playerPosition != HackedClient.MC.player.getBlockPos()) {
                    return false;
                }
                doBreak(x, y, z);
                System.out.printf("y = %d , r = %d\n", y, r);
            }
        }
        z = r;
        for (y = -r; y <= r; y = y + bypassHack.skip) {
            for (x = -r; x <= r; x = x + bypassHack.skip) {
                if (HackedClient.INSTANCE.getLocalPlayer() == null || HackedClient.INSTANCE.getWorld() == null || !this.isRunning) {
                    this.bypassHack.close();
                    return false;
                }
                if (playerPosition != HackedClient.MC.player.getBlockPos()) {
                    return false;
                }
                doBreak(x, y, z);
                System.out.printf("y = %d , r = %d\n", y, r);
            }
        }
        z = -r;
        for (y = -r; y <= r; y = y + bypassHack.skip) {
            for (x = -r; x <= r; x = x + bypassHack.skip) {
                if (HackedClient.INSTANCE.getLocalPlayer() == null || HackedClient.INSTANCE.getWorld() == null || !this.isRunning) {
                    this.bypassHack.close();
                    return false;
                }
                if (playerPosition != HackedClient.MC.player.getBlockPos()) {
                    return false;
                }
                doBreak(x, y, z);
                System.out.printf("y = %d , r = %d\n", y, r);
            }
        }
        return true;
    }

    public void doBreak(int x, int y, int z){
        /*while(HackedClient.MC.options.keyAttack.isPressed()){
            try {
                Thread.sleep(bypassHack.delayMs);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }*/
        if(y + HackedClient.MC.player.getBlockPos().getY()  >= 64)
            return;
        if(bypassHack.CheckedPosition.contains(HackedClient.MC.player.getBlockPos().add(x ,y, z))){
            System.out.printf("EXIST\n");
            return;
        }
        this.bypassHack.counter++;
        this.bypassHack.sendInteractionPackets(HackedClient.MC.player.getBlockPos().add(x ,y, z));
        if(this.bypassHack.counter != 0) {
            try {
                Thread.sleep(bypassHack.delayMs);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        bypassHack.CheckedPosition.add(HackedClient.MC.player.getBlockPos().add(x ,y, z));
    }
}


