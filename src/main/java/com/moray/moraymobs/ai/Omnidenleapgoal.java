package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Omnidenleapgoal extends Goal {
    private Omnidens omnidens;
    int count;
int timer;
    float distance_x = 0;
    float distance_z = 0;
    public Omnidenleapgoal(Omnidens omnidens,int timer) {
        this.omnidens=omnidens;
        this.timer=timer;
    }

    @Override
    public void start() {
        count=0;
        omnidens.stopInPlace();
    }

    @Override
    public void stop() {
        omnidens.setjumpgrab(0);
        omnidens.setgrip(false);
        count=0;
    }

    @Override
    public void tick() {
        LivingEntity entity= this.omnidens.getTarget();


        if (entity!=null){

        distance_x= (float) (entity.getX()-omnidens.getX());
        distance_z= (float) (entity.getZ()-omnidens.getZ());


            count++;

       if (count>=20&&count<25){
           Vec3 vec=new Vec3(-distance_x,0,-distance_z);
        Vec3 jump= vec.normalize().scale(0.1);
         omnidens.addDeltaMovement(jump.add(-distance_x*0.0001,jump.y()+0.02,-distance_z*0.0001).normalize());}

if (count>=35) {

    if (omnidens.distanceTo(entity)<10&&!omnidens.hasPassenger(entity)){
        omnidens.setgrip(true);
        entity.startRiding(omnidens);
    }
    if (omnidens.hasPassenger(entity)){
    omnidens.stopInPlace();}

    if (!omnidens.hasPassenger(entity)){
    omnidens.addDeltaMovement(new Vec3(distance_x * 0.0002, 0, distance_z * 0.0002).normalize());
}}

        }


    }

    @Override
    public boolean canContinueToUse() {
        return timer>count;
    }

    @Override
    public boolean canUse() {

        if (this.omnidens.getTarget()==null){
        return false;}


        return omnidens.getjumpgrab()>200&&omnidens.getRandom().nextInt(15)==6;
    }
}
