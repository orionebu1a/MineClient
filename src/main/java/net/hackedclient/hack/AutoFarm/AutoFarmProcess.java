package net.hackedclient.hack.AutoFarm;

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
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        while (isRunning) {
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            autoFarmHack.tick();
            System.out.printf("%s\n", autoFarmHack.getState().toString());*/
        }
    }
}
