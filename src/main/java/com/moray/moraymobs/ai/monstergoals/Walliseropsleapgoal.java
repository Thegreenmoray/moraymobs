package com.moray.moraymobs.ai.monstergoals;

import com.moray.moraymobs.entity.living.dungeonentities.Walliserops;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Walliseropsleapgoal extends Goal {

    private Walliserops walliserops;
    int count;
    int timer;
    float distance_x = 0;
    float distance_z = 0;
    public Walliseropsleapgoal(Walliserops walliserops, int timer) {
        this.walliserops=walliserops;
        this.timer=timer;
    }

    @Override
    public void start() {
        count=0;

    }

    @Override
    public void stop() {
walliserops.settimer(0);
        count=0;
    }

    @Override
    public void tick() {
        LivingEntity entity= this.walliserops.getTarget();


        if (entity!=null){
            if (count<5){
                distance_x= (float) (entity.getX()-walliserops.getX());
                distance_z= (float) (entity.getZ()-walliserops.getZ());
           }
            this.walliserops.lookAt(entity, (float) -entity.getY(), (float) entity.getX());


            count++;

            if (count>=10&&count<15){
                Vec3 vec=new Vec3(-distance_x,0,-distance_z);
                Vec3 jump= vec.normalize().scale(0.0001);
                walliserops.addDeltaMovement(jump.add(-distance_x*0.009,jump.y()+0.01,-distance_z*0.009).normalize().scale(0.7));}

            if (count>=20) {
                walliserops.addDeltaMovement(new Vec3(distance_x * 0.001, 0, distance_z * 0.001).normalize());

                if (walliserops.distanceTo(entity)<3){
                    double d0 = (9) / 2.0;
                    double d1 = (9) / 2.0;
                    double d2 = entity.getX() - d0;
                    double d3 = entity.getZ() - d1;
                    double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

                    entity.hurt(walliserops.damageSources().generic(),4);
                    entity.knockback(4,-(d2 / d4*3 ),-(d3 / d4*3 ));
                }



            }


        }


    }

    @Override
    public boolean canContinueToUse() {
        return timer>count;
    }

    @Override
    public boolean canUse() {

        if (this.walliserops.getTarget()==null){

            return false;}


        return walliserops.gettimer()>30;
    }



}
