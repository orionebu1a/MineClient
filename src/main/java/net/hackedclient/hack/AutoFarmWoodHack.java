package net.hackedclient.hack;

import java.util.Comparator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import net.hackedclient.Category;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AutoFarmWoodHack extends Hack {
    //private AutoFarmState;
    private int blockCounter = 0;
    public boolean safe = true;
    private double minDistance = 30;
    private boolean clear = true;
    public BlockPos playerPositionBlock;
    public Vec3d playerLook;
    public Vec3d playerPosition;
    private WoodFarmProcess woodFarmProcess;
    private Thread woodFarmThread;
    private final int distation = 100;
    public BlockPos targetBlock = null;
    private double delta = 0.01;
    private Vec3d target = null;
    private boolean isReached = false;


    public AutoFarmWoodHack() {
        super("AutoFarmWood");
        setCategory(Category.BLOCKS);
    }

    @Override
    public void onEnable() {
        //MC.player.inventory.
        safe = true;
        this.woodFarmProcess = new WoodFarmProcess(this);
        this.woodFarmThread = new Thread(this.woodFarmProcess);
        this.woodFarmThread.start();
    }

    @Override
    public void onDisable() {
        this.targetBlock = null;
        if (this.woodFarmProcess != null) {
            this.woodFarmProcess.intercept();
        }
        this.woodFarmProcess = null;
        this.woodFarmThread = null;
        MC.options.keyForward.setPressed(false);
        MC.options.keyAttack.setPressed(false);
        MC.options.keyUse.setPressed(false);
    }

    public void ifInWater() {
        BlockState State = MC.world.getBlockState(playerPositionBlock);
        Material material = State.getMaterial();
        if (material == Material.WATER) {
            MC.options.keyJump.setPressed(true);
        } else {
            MC.options.keyJump.setPressed(false);
        }
    }

    public void newTargetBlock() {
        if(MC.world == null){
            return;
        }
        double minLen = distation * distation * distation;
        for (int i = -distation; i < distation; i++) {
            for (int j = -5; j < 5; j++) {
                for (int k = -distation; k < distation; k++) {
                    BlockPos blockPosition = playerPositionBlock.add(i, j, k);
                    BlockState State = MC.world.getBlockState(blockPosition);
                    Material material = State.getMaterial();
                    if (material == Material.WOOD) {
                        double len = Math.sqrt(i * i + j * j + k * k);
                        if (len < minLen) {
                            targetBlock = blockPosition;
                            minLen = len;
                        }
                    }
                }
            }
        }
        getAxe();
    }

    public void newTarget() {
        target = new Vec3d(
                targetBlock.getX() + 0.5 - playerPosition.getX(),
                targetBlock.getY() + 1 - playerPosition.getY(),
                targetBlock.getZ() + 0.5 - playerPosition.getZ());
        isReached = false;
    }

    public void getBlock(){
        int bestSlot = 0;
        for(int i = 0; i < 9; i++)
        {
            // skip empty slots
            if(MC.player.inventory.getStack(i).isEmpty())
                continue;

            Item item = MC.player.inventory.getStack(i).getItem();
            if(item instanceof BlockItem){
                bestSlot = i;
            }
        }
        MC.player.inventory.selectedSlot = bestSlot;
    }
    public void getAxe(){
        int bestSlot = 0;
        for(int i = 0; i < 9; i++)
        {
            // skip empty slots
            if(MC.player.inventory.getStack(i).isEmpty())
                continue;

            Item item = MC.player.inventory.getStack(i).getItem();
            if(item instanceof AxeItem){
                bestSlot = i;
            }
        }
        MC.player.inventory.selectedSlot = bestSlot;
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

    public void turnToTarget() {
        if (MC.player != null) {
            double deltaAngle, deltaAngleVertical, angleLook, angleLookVertical, angleTarget, angleTargetVertical;
            angleLook = getHorizontalAngle(playerLook);
            angleLookVertical = getVerticalAngle(playerLook);
            angleTarget = getHorizontalAngle(target);
            angleTargetVertical = getVerticalAngle(target);
            deltaAngleVertical = angleLookVertical - angleTargetVertical;
            deltaAngle = angleLook - angleTarget;

            if (Math.abs(deltaAngleVertical) > delta) {
                MC.player.changeLookDirection(0, deltaAngleVertical * 180);
            }
            if (Math.abs(deltaAngle) > delta) {
                MC.player.changeLookDirection(deltaAngle * 180, 0);
            }
        }
    }

    public void beSafe() {
        if(safe == false){
            getBlock();
            targetBlock = playerPositionBlock.add(0, -10, 0);
            newTarget();
            turnToTarget();
            targetBlock = null;
            MC.options.keyJump.setPressed(true);
            MC.options.keyUse.setPressed(true);
        }
        else{
            MC.options.keyJump.setPressed(false);
            MC.options.keyUse.setPressed(false);
        }
        if(MC.player == null || MC.world == null){
            return;
        }

        Stream<Entity> stream = StreamSupport
                .stream(MC.world.getEntities().spliterator(), true)
                .filter(e -> e instanceof Monster
                        && ((LivingEntity) e).getHealth() > 0
                        || e instanceof EndCrystalEntity)
                .filter(e -> e != MC.player);

        Entity entity = stream
                .min(
                        Comparator.comparingDouble(e -> MC.player.squaredDistanceTo(e)))
                .orElse(null);
        if (entity == null)
            return;

        if (MC.player.squaredDistanceTo(entity) < minDistance) {
            safe = false;
        } else {
            safe = true;
        }
    }

    public void clearWay() {
        if(clear) {
            BlockState State = MC.world.getBlockState(playerPositionBlock.add(0, 2, 0));
            if (State.getMaterial() == Material.SOIL || State.getMaterial() == Material.STONE || State.getMaterial() == Material.LEAVES) {
                targetBlock = playerPositionBlock.add(0, 2, 0);
                clear = false;
                //System.out.printf("1\n");
            } else {
                State = MC.world.getBlockState(playerPositionBlock.add(Math.signum(playerLook.getX()) * 1, 2, 0));
                if (State.getMaterial() == Material.SOIL || State.getMaterial() == Material.STONE || State.getMaterial() == Material.LEAVES) {
                    targetBlock = playerPositionBlock.add(Math.signum(playerLook.getX()) * 1, 2, 0);
                    clear = false;
                    //System.out.printf("2\n");
                } else {
                    State = MC.world.getBlockState(playerPositionBlock.add(0, 2, Math.signum(playerLook.getX()) * 1));
                    if (State.getMaterial() == Material.SOIL || State.getMaterial() == Material.STONE || State.getMaterial() == Material.LEAVES) {
                        targetBlock = playerPositionBlock.add(0, 2, Math.signum(playerLook.getZ()) * 1);
                        clear = false;
                        //System.out.printf("3\n");
                    }
                    else{
                        State = MC.world.getBlockState(playerPositionBlock.add(0, 2, Math.signum(playerLook.getX()) * 1));
                        if (State.getMaterial() == Material.SOIL || State.getMaterial() == Material.STONE || State.getMaterial() == Material.LEAVES) {
                            targetBlock = playerPositionBlock.add(0, 1, Math.signum(playerLook.getZ()) * 1);
                            clear = false;
                            System.out.printf("4\n");
                        }
                        else{
                            State = MC.world.getBlockState(playerPositionBlock.add(0, 2, Math.signum(playerLook.getX()) * 1));
                            if (State.getMaterial() == Material.SOIL || State.getMaterial() == Material.STONE || State.getMaterial() == Material.LEAVES) {
                                targetBlock = playerPositionBlock.add(Math.signum(playerLook.getX()) * 1, 1, 0);
                                clear = false;
                                System.out.printf("5\n");
                            }
                        }
                    }
                }
            }
        }
    }

    public void goToTarget() {
        if (!isReached) {
            MC.options.keyForward.setPressed(true);
            if (Math.sqrt(Math.pow(target.getX(), 2) + Math.pow(target.getZ(), 2)) < 1.5) {
                isReached = true;
                MC.options.keyForward.setPressed(false);
            }
        }
    }

    public void mineTree() {
        if (targetBlock != null) {
            MC.options.keyAttack.setPressed(true);
        } else {
            MC.options.keyAttack.setPressed(false);
        }
    }

    public void checkMine() {
        if (MC.world != null) {
            if (MC.world.getBlockState(targetBlock).getMaterial() == Material.AIR) {
                MC.options.keyForward.setPressed(true);
                if (Math.sqrt(target.getX() * target.getX() + target.getZ() * target.getZ()) < 1.5) {
                    targetBlock = null;
                    blockCounter++;
                    clear = true;
                }
            }
        }
    }

    public void cycle(){
        if (MC.player != null) {
            playerLook = MC.player.getRotationVector();
            playerPosition = MC.player.getPos().add(0, 2, 0);
            playerPositionBlock = MC.player.getBlockPos();
            newTargetBlock();
            while(targetBlock != null){
                if(!clear){
                    mineTree();
                    checkMine();
                    clearWay();
                    newTarget();
                    turnToTarget();
                    goToTarget();
                }
                else if(safe && clear){
                    mineTree();
                    checkMine();
                    clearWay();
                    beSafe();
                    ifInWater();
                    newTarget();
                    turnToTarget();
                    goToTarget();
                }
                else if(!safe){
                    beSafe();
                }
                if(MC.player != null) {
                    playerPosition = MC.player.getPos().add(0, 2, 0);
                    playerPositionBlock = MC.player.getBlockPos();
                    playerLook = MC.player.getRotationVector();
                }
                System.out.printf("%d\n", blockCounter);
            }
            MC.options.keyForward.setPressed(false);
            MC.options.keyAttack.setPressed(false);
        }
    }
    }