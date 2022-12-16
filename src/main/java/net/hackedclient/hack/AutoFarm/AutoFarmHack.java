package net.hackedclient.hack.AutoFarm;

import net.hackedclient.Category;
import net.hackedclient.HackedClient;
import net.hackedclient.hack.Hack;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;

public class AutoFarmHack extends Hack {
    public double distanceOfCollection = 1.3;
    public int distX = 10;
    public int distY = 10;
    public int distZ = 10;
    public double delta = 0.01;
    private BlockPos targetBlock;
    private BlockPos clearBlock;
    private String findBlock = "Block{minecraft:iron_ore}";
    private State state;
    private AutoFarmProcess autoFarmProcess;
    private Thread autoFarmThread;
    public void tick(){
        state.process(state);
    }

    public AutoFarmHack() {
        super("AutoFarmHack");
        setCategory(Category.BLOCKS);
    }

    @Override
    public void onEnable() {
        //MC.player.inventory.
        state = new NoTarget(this);
        this.autoFarmProcess = new AutoFarmProcess(this);
        this.autoFarmThread = new Thread(this.autoFarmProcess);
        this.autoFarmThread.start();
    }

    @Override
    public void onDisable() {
        if (this.autoFarmProcess != null) {
            this.autoFarmProcess.intercept();
        }
        this.autoFarmProcess = null;
        this.autoFarmThread = null;
    }

    public void setFindBlock(String string) {
        this.findBlock = string;
    }

    public String getFindBlock() {
        return findBlock;
    }

    public BlockPos getTargetBlock(){
        return targetBlock;
    }

    public void setTargetBlock(BlockPos target){
        targetBlock = target;
    }

    public BlockPos getClearBlock(){
        return clearBlock;
    }

    public void setClearBlock(BlockPos target){
        clearBlock = target;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }

    public boolean isStack(){
        BlockPos block;
        BlockState State;
        block = HackedClient.MC.player.getBlockPos().add(0, 3, 0);
        State = MC.world.getBlockState(block);
        if (State.getMaterial() != Material.AIR && !State.getBlock().toString().equals(findBlock)){//&& State.getMaterial() != Material.LAVA && State.getMaterial() != Material.WATER) {
            clearBlock = block;
            return true;
        }
        /*if(Math.abs(HackedClient.MC.player.getRotationVector().getX()) > Math.abs(HackedClient.MC.player.getRotationVector().getZ())){
            block = HackedClient.MC.player.getBlockPos().add(Math.signum(HackedClient.MC.player.getRotationVector().getX()) * 1, 3, 0);
        }
        else{
            block = HackedClient.MC.player.getBlockPos().add(0, 3, HackedClient.MC.player.getRotationVector().getZ() * 1);
        }
        State = MC.world.getBlockState(block);
        if (State.getMaterial() != Material.AIR && !State.getBlock().toString().equals(findBlock)){//&& State.getMaterial() != Material.LAVA && State.getMaterial() != Material.WATER) {
            clearBlock = block;
            return true;
        }*/
        return false;
    }
}


