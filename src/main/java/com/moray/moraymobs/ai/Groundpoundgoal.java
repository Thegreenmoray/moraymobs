package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.monster.Volcanoback;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class Groundpoundgoal extends Goal {
   Volcanoback volcanoback;
  int groundpoundtime;
  int amounttime;

   public Groundpoundgoal(Volcanoback volcanoback,int groundpoundtime){
       this.volcanoback=volcanoback;
  this.groundpoundtime=groundpoundtime;
   }


    @Override
    public void tick() {
        amounttime++;


        if (amounttime==1){
            this.volcanoback.setanimationtimer(10);
      this.volcanoback.issmashing=true;
        }

        if (amounttime==10){
            this.volcanoback.issmashing=false;
            List<Entity> entities=this.volcanoback.level().getEntities(this.volcanoback,this.volcanoback.getBoundingBox().inflate(4), Entity::onGround);
        for (Entity entity:entities){
            if(entity instanceof LivingEntity){
                entity.hurt(this.volcanoback.damageSources().generic(),10f);
                double d0 = (this.volcanoback.getBoundingBox().minX + this.volcanoback.getBoundingBox().maxX) / 2.0;
                double d1 = (this.volcanoback.getBoundingBox().minZ + this.volcanoback.getBoundingBox().maxZ) / 2.0;
                double d2 = entity.getX() - d0;
                double d3 = entity.getZ() - d1;
                double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

                ((LivingEntity) entity).knockback(4,-(d2 / d4 * 4.0),-(d3 / d4 * 4.0));
            }
        }
        }


    }

    @Override
    public void start() {
       amounttime=0;
        this.volcanoback.setanimationable(2);
   }

    @Override
    public boolean canUse() {


       if (volcanoback.getslapcooldown()>=50){
           return false;
       }

       if (volcanoback.getTarget()==null){
           return false;
       }

        return this.volcanoback.distanceTo(this.volcanoback.getTarget())<6.5&&volcanoback.getgroundpound()>=100;
    }

    @Override
    public boolean canContinueToUse() {
        return groundpoundtime > amounttime;
    }

    @Override
    public void stop() {
      amounttime=0;
volcanoback.setgroundpound(0);
this.volcanoback.setanimationable(0);
   }
}
