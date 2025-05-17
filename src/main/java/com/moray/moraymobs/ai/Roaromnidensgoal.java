package com.moray.moraymobs.ai;

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
    int timer;
    int count;
    public Roaromnidensgoal(Omnidens omnidens, int timer) {
        this.omnidens=omnidens;
        this.timer=timer;
    }

    @Override
    public void start() {
      count=0;

    }

    @Override
    public void tick() {
      count++;
        omnidens.stopInPlace();
        if (count==10){
       this.omnidens.setPose(Pose.ROARING);
        List<Entity> entities=  this.omnidens.level().getEntities(this.omnidens,this.omnidens.getBoundingBox().inflate(7));
        for (Entity entity:entities){
            if(entity instanceof LivingEntity livingEntity){
                livingEntity.addEffect(omnidens.getHealth()>omnidens.getMaxHealth()/2?new MobEffectInstance(MobEffects.WEAKNESS,150,0):new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,150,0));
            }}}
    }


    @Override
    public void stop() {
        count=0;
        this.omnidens.setPose(Pose.STANDING);
        this.omnidens.setRoar(0);
    }

    @Override
    public boolean canContinueToUse() {
        return timer>count;
    }

    @Override
    public boolean canUse() {
     LivingEntity entity=this.omnidens.getTarget();
     if (entity==null){
         this.omnidens.setRoar(0);
         return false;
     }


        return omnidens.getroar()>150;//&&omnidens.getRandom().nextInt(10)==4;
    }
}
