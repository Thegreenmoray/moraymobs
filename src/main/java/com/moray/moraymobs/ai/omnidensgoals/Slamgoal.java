package com.moray.moraymobs.ai.omnidensgoals;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class Slamgoal extends Goal {

    Omnidens omnidens;



    public Slamgoal(Omnidens omnidens){
        this.omnidens=omnidens;

    }


    @Override
    public void tick() {

        if (omnidens.getslam()==6){

            List<Entity> entities=this.omnidens.level().getEntities(this.omnidens,this.omnidens.getBoundingBox().inflate(4));
            for (Entity entity:entities){
                if(entity instanceof LivingEntity){
                    entity.hurt(this.omnidens.damageSources().mobAttack(omnidens),8f);
                    double d0 = (this.omnidens.getBoundingBox().minX + this.omnidens.getBoundingBox().maxX) / 2.0;
                    double d1 = (this.omnidens.getBoundingBox().minZ + this.omnidens.getBoundingBox().maxZ) / 2.0;
                    double d2 = entity.getX() - d0;
                    double d3 = entity.getZ() - d1;
                    double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

                    ((LivingEntity) entity).knockback(6,-(d2 / d4 * 4.0),-(d3 / d4 * 4.0));
                }
            }
        }


    }

    @Override
    public void start() {
       this.omnidens.setslam(50);
        omnidens.setanimation(4);
    }

    @Override
    public boolean canUse() {

        if (omnidens.getTarget()==null){
            return false;
        }

        if (this.omnidens.distanceTo(this.omnidens.getTarget())>=9.5){

            return false;
        }



        return omnidens.canuseskill() &&omnidens.getHealth()<=omnidens.threeforths
                &&omnidens.getslam()<=-151&&omnidens.level().random.nextInt(18)==7;
    }

    @Override
    public boolean canContinueToUse() {
        return omnidens.getslam()>0;
    }

    @Override
    public void stop() {
        omnidens.setanimation(0);

    }
}
