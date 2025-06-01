package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.living.dungeonentities.Walliserops;
import net.minecraft.util.Mth;
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

        count=0;
    }

    @Override
    public void tick() {
        LivingEntity entity= this.walliserops.getTarget();


        if (entity!=null){
            if (count<5){
                distance_x= (float) (entity.getX()-walliserops.getX());
                distance_z= (float) (entity.getZ()-walliserops.getZ());
                walliserops.setYBodyRot((float) Mth.atan2(distance_z,distance_x)*Mth.RAD_TO_DEG);
            }


            count++;

            if (count>=10&&count<15){
                Vec3 vec=new Vec3(-distance_x,0,-distance_z);
                Vec3 jump= vec.normalize().scale(0.001);
                walliserops.addDeltaMovement(jump.add(-distance_x*0.00009,jump.y()+0.01,-distance_z*0.00009).normalize());}

            if (count>=20) {

                if (walliserops.distanceTo(entity)<3){

                    entity.hurt(walliserops.damageSources().generic(),4);}
            }
            walliserops.addDeltaMovement(new Vec3(distance_x * 0.00001, 0, distance_z * 0.00001).normalize());


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
