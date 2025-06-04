package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.living.dungeonentities.Microdictyon;
import com.moray.moraymobs.entity.projectiles.Bouncy_ball;
import com.moray.moraymobs.entity.projectiles.Microprojectile;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class BouncingBallgoal extends Goal {

    Omnidens omnidens;

    public BouncingBallgoal(Omnidens omnidens) {
        this.omnidens = omnidens;

    }


    @Override
    public void tick() {

        LivingEntity living = omnidens.getTarget();
        if (living!=null){
            this.omnidens.lookAt(living, (float) -living.getY(), (float) living.getX());

            if(omnidens.getbouncetime()==10){
                Bouncy_ball bouncyBall = new Bouncy_ball(this.omnidens.level());
                Vec3 vec3=this.omnidens.getViewVector(1);
                bouncyBall.setPos(this.omnidens.getX() + vec3.x * 4.0, this.omnidens.getY(0.33333)+0.5, this.omnidens.getZ() + vec3.z * 4.0);
                double d0 = living.getX() - this.omnidens.getX();
                double d1 = living.getY(0.3333333333333333) - bouncyBall.getY();
                double d2 = living.getZ() - this.omnidens.getZ();
                double d3 = Math.sqrt(d0 * d0 + d2 * d2);
                Vec3 vec31=new Vec3(Mth.floor(d0), Mth.floor(d1 + d3 * 0.20000000298023224)-0.05, Mth.floor(d2));
                bouncyBall.setDeltaMovement(vec31.normalize().scale(0.0009));
                this.omnidens.level().addFreshEntity(bouncyBall);
            }
        }
    }



    @Override
    public void start() {
        omnidens.setbouncetime(36);
        omnidens.setanimation(7);
    }

    @Override
    public void stop() {
        omnidens.setanimation(0);

    }


    @Override
    public boolean canContinueToUse() {
        if (this.omnidens.getTarget()==null){
            return false;
        }



        return omnidens.getbouncetime()>0;
    }


    @Override
    public boolean canUse() {
        LivingEntity entity=this.omnidens.getTarget();

        if (entity==null){
            return false;
        }


        return this.omnidens.getbouncetime()<=-50
                &&omnidens.canuseskill()&&omnidens.level().random.nextInt(25)==8;
    }
}
