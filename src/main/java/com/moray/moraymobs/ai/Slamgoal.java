package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.living.monster.Volcanoback;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class Slamgoal extends Goal {

    Omnidens omnidens;
    int groundpoundtime;
    int amounttime;

    public Slamgoal(Omnidens omnidens, int groundpoundtime){
        this.omnidens=omnidens;
        this.groundpoundtime=groundpoundtime;
    }


    @Override
    public void tick() {
        amounttime++;




        if (amounttime==24){

            List<Entity> entities=this.omnidens.level().getEntities(this.omnidens,this.omnidens.getBoundingBox().inflate(4));
            for (Entity entity:entities){
                if(entity instanceof LivingEntity){
                    entity.hurt(this.omnidens.damageSources().generic(),15f);
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
        amounttime=0;

    }

    @Override
    public boolean canUse() {




        if (omnidens.getTarget()==null){
            return false;
        }

        if (this.omnidens.distanceTo(this.omnidens.getTarget())>=9.5){
          omnidens.setslam(50); //we'll set it to half-ish instead
            return false;
        }



        return omnidens.getslam()>150;
    }

    @Override
    public boolean canContinueToUse() {
        return groundpoundtime > amounttime;
    }

    @Override
    public void stop() {
        amounttime=0;
        omnidens.setslam(0);
    }
}
