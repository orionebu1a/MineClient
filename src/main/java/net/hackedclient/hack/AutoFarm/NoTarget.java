package net.hackedclient.hack.AutoFarm;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalBlock;
import net.hackedclient.HackedClient;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class NoTarget implements State{
    private int distX;
    private int distY;
    private int distZ;
    private AutoFarmHack autoFarmHack;
    NoTarget(AutoFarmHack autoFarmHack){
        this.autoFarmHack = autoFarmHack;
        this.distX = autoFarmHack.distX;
        this.distY = autoFarmHack.distY;
        this.distZ = autoFarmHack.distZ;
    }

    @Override
    public void process(State state) {
        this.newTargetBlock();
        if(autoFarmHack.getTargetBlock() != null){
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalBlock(autoFarmHack.getTargetBlock().getX(), autoFarmHack.getTargetBlock().getY(), autoFarmHack.getTargetBlock().getZ()));
            autoFarmHack.setState(new GoToTarget(autoFarmHack));
        }
    }

    public void newTargetBlock(){
        if(HackedClient.MC.world == null){
            return;
        }
        double len;
        double minLen = distX * distY * distZ;
        for (int i = -distX; i < distX; i++) {
            for (int j = -distY; j < distY; j++) {
                for (int k = -distZ; k < distZ; k++) {
                    BlockPos blockPosition = HackedClient.MC.player.getBlockPos().add(i, j, k);
                    BlockState blockstate = HackedClient.MC.world.getBlockState(blockPosition);
                    String string = blockstate.getBlock().toString();
                    if (string.equals(autoFarmHack.getFindBlock())) {
                        System.out.printf("%s\n", string);
                        len = Math.sqrt(i * i + j * j + k * k);
                        if (len < minLen) {
                            autoFarmHack.setTargetBlock(blockPosition);
                            minLen = len;
                        }
                    }
                }
            }
        }
    }
}
