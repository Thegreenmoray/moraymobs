package com.moray.moraymobs.ai.omnidensgoals;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class OmnidensJumpattackgoal extends Goal {

    protected final Omnidens omnidens;
    private int timer;
    private int count;

    public OmnidensJumpattackgoal(Omnidens omnidens,int timer) {
        this.omnidens = omnidens;
        this.timer=timer;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        LivingEntity livingentity = this.omnidens.getTarget();
        if (livingentity == null) {
            return false;
        }

        return(livingentity.getY()-omnidens.getY()>5)&&omnidens.canmeleejump();


    }




    public boolean isWithinMeleeAttackRange(LivingEntity entity) {
        return this.omnidens.getHitbox().inflate(6.5,12,6.5).intersects(entity.getHitbox());
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    public boolean canContinueToUse() {
        return timer>count;
    }

    public void start() {
count=0;
        omnidens.setleap(true);
        omnidens.setanimation(0);
        omnidens.setPose(Pose.LONG_JUMPING);
    }

    public void stop() {
        count=0;
        omnidens.setleap(false);
        omnidens.setanimation(1);
        omnidens.setPose(Pose.STANDING);
    }



    public void tick() {
        LivingEntity livingentity = this.omnidens.getTarget();
        if (livingentity != null) {


            count++;
if (count==25){
boolean flag=isWithinMeleeAttackRange(livingentity);
    if (flag){
livingentity.hurt(omnidens.damageSources().mobAttack(livingentity),12f);}
    double d0 = (this.omnidens.getBoundingBox().minX + this.omnidens.getBoundingBox().maxX) / 2.0;
    double d1 = (this.omnidens.getBoundingBox().minZ + this.omnidens.getBoundingBox().maxZ) / 2.0;
    double d2 = livingentity.getX() - d0;
    double d3 = livingentity.getZ() - d1;
    double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

    ((LivingEntity) livingentity).knockback(1,-(d2 / d4 * 4.0),-(d3 / d4 * 4.0));

}
        }

    }

}


