package net.hackedclient.hack;

import net.hackedclient.Category;
import net.hackedclient.event.listeners.UpdateListener;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class FlyHack extends Hack implements UpdateListener {

    int toggle = 0;
    int MAX_SPEED = 3;
    double FALL_SPEED = -0.04;
    double acceleration = 0.1;

    public FlyHack() {
        super("Fly");
        setCategory(Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        EVENTS.add(UpdateListener.class, this);
    }

    @Override
    public void onDisable() {
        EVENTS.remove(UpdateListener.class, this);
    }

    @Override
    public void onUpdate() {
        if (MC.player != null) {
            boolean jumpPressed = MC.options.keyJump.isPressed();
            boolean forwardPressed = MC.options.keyForward.isPressed();
            boolean leftPressed = MC.options.keyLeft.isPressed();
            boolean rightPressed = MC.options.keyRight.isPressed();
            boolean backPressed = MC.options.keyBack.isPressed();

            Entity object = MC.player;
            Vec3d velocity = object.getVelocity();
            Vec3d newVelocity = new Vec3d(velocity.x, -FALL_SPEED, velocity.z);
            if (jumpPressed) {
                if (forwardPressed) {
                    newVelocity = MC.player.getRotationVector().multiply(acceleration);
                }
                if (leftPressed) {
                    newVelocity = MC.player.getRotationVector().multiply(acceleration).rotateY(3.1415927F / 2);
                    newVelocity = new Vec3d(newVelocity.x, 0, newVelocity.z);
                }
                if (rightPressed) {
                    newVelocity = MC.player.getRotationVector().multiply(acceleration).rotateY(-3.1415927F / 2);
                    newVelocity = new Vec3d(newVelocity.x, 0, newVelocity.z);
                }
                if (backPressed) {
                    newVelocity = MC.player.getRotationVector().negate().multiply(acceleration);
                }

                newVelocity = new Vec3d(newVelocity.x, (toggle == 0 && newVelocity.y > FALL_SPEED) ? FALL_SPEED : newVelocity.y, newVelocity.z);

                object.setVelocity(newVelocity);
                if (forwardPressed || leftPressed || rightPressed || backPressed) {
                    if (acceleration < MAX_SPEED) {
                        acceleration += 0.1;
                    }
                } else if (acceleration > 0.2) {
                    acceleration -= 0.2;
                }

            }

            if (toggle == 0 || newVelocity.y <= -0.04) {
                toggle = 40;
            }
            toggle--;
        }
    }
}
