package net.hackedclient.hack.AutoFarm;

import net.hackedclient.HackedClient;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.Vec3d;

public class Stack implements State{
    private Vec3d clearTarget;
    private double delta;
    private AutoFarmHack autoFarmHack;
    Stack(AutoFarmHack autoFarmHack){
        this.autoFarmHack = autoFarmHack;
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

    @Override
    public void process(State state) {
        createDirection();
        turnToTarget();
        if(HackedClient.MC.world.getBlockState(autoFarmHack.getClearBlock()).getMaterial() != Material.AIR){
            HackedClient.MC.options.keyAttack.setPressed(true);
        }
        else{
            HackedClient.MC.options.keyForward.setPressed(false);
            HackedClient.MC.options.keyAttack.setPressed(false);
            autoFarmHack.setClearBlock(null);
            autoFarmHack.setState(new GoToTarget(autoFarmHack));
            return;
        }
    }
    public void turnToTarget(){
        double deltaAngle, deltaAngleVertical, angleLook, angleLookVertical, angleTarget, angleTargetVertical;
        angleLook = getHorizontalAngle(HackedClient.MC.player.getRotationVector());
        angleLookVertical = getVerticalAngle(HackedClient.MC.player.getRotationVector());
        angleTarget = getHorizontalAngle(clearTarget);
        angleTargetVertical = getVerticalAngle(clearTarget);
        deltaAngleVertical = angleLookVertical - angleTargetVertical;
        deltaAngle = angleLook - angleTarget;

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
        this.clearTarget = new Vec3d(
                autoFarmHack.getClearBlock().getX() + 0.5 - HackedClient.MC.player.getPos().getX(),
                autoFarmHack.getClearBlock().getY() - 2 - HackedClient.MC.player.getPos().getY(),
                autoFarmHack.getClearBlock().getZ() + 0.5 - HackedClient.MC.player.getPos().getZ());
    }
}
