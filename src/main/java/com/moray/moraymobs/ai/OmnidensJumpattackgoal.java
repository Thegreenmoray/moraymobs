package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.world.entity.LivingEntity;
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
        return this.omnidens.getHitbox().inflate(2.5,10,2.5).intersects(entity.getHitbox());
    }



    public boolean canContinueToUse() {
        return timer>count;
    }

    public void start() {
count=0;
    }

    public void stop() {
        count=0;
        omnidens.setleap(false);
    }



    public void tick() {
        LivingEntity livingentity = this.omnidens.getTarget();
        if (livingentity != null) {
if (count==0){
    omnidens.setleap(true);
}

            count++;
if (count==25){
boolean flag=isWithinMeleeAttackRange(livingentity);
    if (flag){
livingentity.hurt(omnidens.damageSources().generic(),20f);}
}
        }

    }



}


