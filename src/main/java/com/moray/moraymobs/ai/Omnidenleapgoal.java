package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Omnidenleapgoal extends Goal {
    private Omnidens omnidens;
    int count;
    public Omnidenleapgoal(Omnidens omnidens,int timer) {
        this.omnidens=omnidens;
        this.count=timer;
    }


    @Override
    public void stop() {
        count=0;
    }

    @Override
    public void tick() {
        LivingEntity entity= this.omnidens.getTarget();

        if (entity!=null){
         float distance_x= (float) (entity.getX()-omnidens.getX());
         float distance_z= (float) (entity.getZ()-omnidens.getZ());
         omnidens.setYRot((float) Mth.atan2(distance_z,distance_x));

         Vec3 vec=new Vec3(-distance_x,0,-distance_z);
        Vec3 jump= vec.normalize().scale(0.1);
         omnidens.addDeltaMovement(jump.add(0,jump.y()+0.05,0));


        omnidens.addDeltaMovement(new Vec3(distance_x*2,0,distance_z*2).normalize().scale(0.1));


        }


    }

    @Override
    public boolean canUse() {

        if (this.omnidens.getTarget()==null){
        return false;}


        return omnidens.getleap()>200;
    }
}
