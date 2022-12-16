package net.hackedclient.hack.AutoFarm;

public class InDanger implements State{
    AutoFarmHack autoFarmHack;
    InDanger(AutoFarmHack autoFarmHack){
        this.autoFarmHack = autoFarmHack;
    }
    @Override
    public void process(State state) {
        if(autoFarmHack.isStack()){
            autoFarmHack.setState(new Stack(autoFarmHack));
        }
    }
}
