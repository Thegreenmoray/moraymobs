package com.moray.moraymobs.ai.omnidensgoals;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Omnidenleapgoal extends Goal {
    private Omnidens omnidens;


    float distance_x = 0;
    float distance_z = 0;
    public Omnidenleapgoal(Omnidens omnidens) {
        this.omnidens=omnidens;

    }

    @Override
    public void start() {
       omnidens.setjumpgrab(70);
        omnidens.setanimation(0);
        omnidens.stopInPlace();
    }

    @Override
    public void stop() {
        omnidens.setgrip(false);
        omnidens.setanimation(1);
    }

    @Override
    public void tick() {
        LivingEntity entity= this.omnidens.getTarget();


        if (entity!=null){
         if (omnidens.getjumpgrab()>46){
            distance_x= (float) (entity.getX()-omnidens.getX());
        distance_z= (float) (entity.getZ()-omnidens.getZ());
         omnidens.setYBodyRot((float) Mth.atan2(distance_z,distance_x)*Mth.RAD_TO_DEG);
         }

            this.omnidens.lookAt(entity, (float) -entity.getY(), (float) entity.getX());



       if (omnidens.getjumpgrab()<=42&&omnidens.getjumpgrab()>30){
           Vec3 vec=new Vec3(-distance_x,0,-distance_z);
        Vec3 jump= vec.normalize().scale(0.1);
         omnidens.addDeltaMovement(jump.add(-distance_x*0.00009,jump.y()+0.01,-distance_z*0.00009).normalize());}

if (omnidens.getjumpgrab()<=22) {

    if (omnidens.distanceTo(entity)<9&&!omnidens.hasPassenger(entity)){
        omnidens.setgrip(true);
        entity.startRiding(omnidens,true);
    }
    if (omnidens.hasPassenger(entity)){
        if (omnidens.getjumpgrab()%2==0){
        entity.hurt(omnidens.damageSources().generic(),4);}
        omnidens.stopInPlace();}

    if (!omnidens.hasPassenger(entity)){
    omnidens.addDeltaMovement(new Vec3(distance_x * 0.00002, 0, distance_z * 0.00002).normalize());
}}

        }


    }

    @Override
    public boolean canContinueToUse() {
        return omnidens.getjumpgrab()>0;
    }

    @Override
    public boolean canUse() {

        if (this.omnidens.getTarget()==null){

            return false;}


        return omnidens.getjumpgrab()<=-250
                &&omnidens.getHealth()<=omnidens.onehalf
                &&omnidens.canuseskill()
                &&omnidens.level().random.nextInt(15)==9;

    }
}
