package com.moray.moraymobs.ai.omnidensgoals;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class Roaromnidensgoal extends Goal {

    private Omnidens omnidens;

    public Roaromnidensgoal(Omnidens omnidens) {
        this.omnidens=omnidens;

    }

    @Override
    public void start() {
    omnidens.setRoar(75);
omnidens.setanimation(6);
    }

    @Override
    public void tick() {

        omnidens.stopInPlace();
        if (omnidens.getroar()==39){
       this.omnidens.setPose(Pose.ROARING);
        List<Entity> entities=  this.omnidens.level().getEntities(this.omnidens,this.omnidens.getBoundingBox().inflate(7));
        for (Entity entity:entities){
            if(entity instanceof LivingEntity livingEntity){
                livingEntity.addEffect(omnidens.getHealth()>omnidens.getMaxHealth()/2?new MobEffectInstance(MobEffects.WEAKNESS,150,0):new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,150,0));
            }}}
    }


    @Override
    public void stop() {
        this.omnidens.setPose(Pose.STANDING);
        omnidens.setanimation(0);
    }

    @Override
    public boolean canContinueToUse() {
        return omnidens.getroar()>0;
    }

    @Override
    public boolean canUse() {
     LivingEntity entity=this.omnidens.getTarget();
     if (entity==null){

         return false;
     }


        return omnidens.getroar()<=-150
                &&omnidens.canuseskill()
                &&omnidens.level().random.nextInt(15)==3;


    }
}
