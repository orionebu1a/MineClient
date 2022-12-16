package net.hackedclient.hack.AutoFarm;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalBlock;
import net.hackedclient.HackedClient;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.Vec3d;

class GoToTarget implements State{
    private Vec3d target;
    private double delta;
    private AutoFarmHack autoFarmHack;
    GoToTarget(AutoFarmHack autoFarmHack){
        this.delta = autoFarmHack.delta;
        this.autoFarmHack = autoFarmHack;
    }

    @Override
    public void process(State state) {
        //BaritoneAPI.getProvider().getPrimaryBaritone().getMineProcess().
        if(Math.sqrt(
                Math.pow(autoFarmHack.getTargetBlock().getX() - HackedClient.MC.player.getPos().getX(), 2) +
                        Math.pow(autoFarmHack.getTargetBlock().getZ() - HackedClient.MC.player.getPos().getZ(), 2) +
                                Math.pow(autoFarmHack.getTargetBlock().getY() - HackedClient.MC.player.getPos().getY(), 2))
                < autoFarmHack.distanceOfCollection){
            autoFarmHack.setTargetBlock(null);
            HackedClient.blockCounter = HackedClient.blockCounter + 1;
            autoFarmHack.setState(new NoTarget(autoFarmHack));
        }
        /*createDirection();
        turnToTarget();
        System.out.printf("%d %d %d\n", autoFarmHack.getTargetBlock().getX(), autoFarmHack.getTargetBlock().getY(), autoFarmHack.getTargetBlock().getZ());
        System.out.printf("%f %f %f\n", target.getX(), target.getY(), target.getZ());
        if(autoFarmHack.isStack()){
            HackedClient.MC.options.keyForward.setPressed(false);
            HackedClient.MC.options.keyAttack.setPressed(false);
            autoFarmHack.setState(new Stack(autoFarmHack));
            return;
        }
        System.out.printf("%s\n", HackedClient.MC.world.getBlockState(autoFarmHack.getTargetBlock()).getBlock().toString());
        if(HackedClient.MC.world.getBlockState(autoFarmHack.getTargetBlock()).getBlock().toString().equals(autoFarmHack.getFindBlock())){
            WaterByPass();
            HackedClient.MC.options.keyForward.setPressed(true);
            HackedClient.MC.options.keyAttack.setPressed(true);
        }
        else{
            if(Math.sqrt(
                    Math.pow(autoFarmHack.getTargetBlock().getX() - HackedClient.MC.player.getPos().getX(), 2) +
                    Math.pow(autoFarmHack.getTargetBlock().getZ() - HackedClient.MC.player.getPos().getZ(), 2))
                    < autoFarmHack.distanceOfCollection){
                HackedClient.MC.options.keyForward.setPressed(false);
                HackedClient.MC.options.keyAttack.setPressed(false);
                autoFarmHack.setTargetBlock(null);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                HackedClient.blockCounter = HackedClient.blockCounter + 1;
                autoFarmHack.setState(new NoTarget(autoFarmHack));
            }
            HackedClient.MC.options.keyAttack.setPressed(false);
        }*/
    }

    public void WaterByPass(){
        BlockState State = HackedClient.MC.world.getBlockState(HackedClient.MC.player.getBlockPos());
        Material material = State.getMaterial();
        if (material == Material.WATER) {
            HackedClient.MC.options.keyJump.setPressed(true);
        } else {
            HackedClient.MC.options.keyJump.setPressed(false);
        }
    }

    public void turnToTarget(){
        double deltaAngle, deltaAngleVertical, angleLook, angleLookVertical, angleTarget, angleTargetVertical;
        angleLook = getHorizontalAngle(HackedClient.MC.player.getRotationVector());
        angleLookVertical = getVerticalAngle(HackedClient.MC.player.getRotationVector());
        angleTarget = getHorizontalAngle(target);
        angleTargetVertical = getVerticalAngle(target);
        deltaAngleVertical = angleLookVertical - angleTargetVertical;
        deltaAngle = angleLook - angleTarget;
        System.out.printf("%f\n", deltaAngle);
        System.out.printf("%f\n", deltaAngleVertical);
        if (Math.abs(deltaAngleVertical) > delta) {
            HackedClient.MC.player.changeLookDirection(0, deltaAngleVertical * 180);
        }
        if (Math.abs(deltaAngle) > delta) {
            HackedClient.MC.player.changeLookDirection(deltaAngle * 180, 0);
        }
    }

    public double getVerticalAngle(Vec3d vector) {
        Vec3d norm = vector.multiply(1 / vector.length());
        double angle, cos = Math.sqrt(Math.pow(norm.getX(), 2) + Math.pow(norm.getZ(), 2)),
                sin = norm.getY();
        if (sin > 0) {
            angle = Math.acos(cos);
        } else {
            angle = -Math.acos(cos);
        }
        return angle;
    }

    public double getHorizontalAngle(Vec3d vector) {
        Vec3d norm = vector.multiply(1 / vector.length());
        double angle, cos = norm.getZ(), sin = norm.getX();
        if (sin > 0) {
            angle = Math.acos(cos);
        } else {
            angle = -Math.acos(cos);
        }
        return angle;
    }

    public void createDirection() {
        this.target = new Vec3d(
                autoFarmHack.getTargetBlock().getX() + 0.5 - HackedClient.MC.player.getPos().getX(),
                autoFarmHack.getTargetBlock().getY() - 1 - HackedClient.MC.player.getPos().getY(),
                autoFarmHack.getTargetBlock().getZ() + 0.5 - HackedClient.MC.player.getPos().getZ());
    }
}
