package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.monster.Volcanoback;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class VolcanobackMeleeAttackgoal extends Goal {

   Volcanoback volcanoback;
    boolean isinattack=false;
    int animantion_time=-1;
int time;

   public VolcanobackMeleeAttackgoal(Volcanoback volcanoback,int time){
       this.volcanoback=volcanoback;
  this.time=time;
   }

    @Override
    public void start() {
     animantion_time=-1;

   }

    @Override
    public void stop() {
        volcanoback.setSlapcooldownTimer(0);
        this.volcanoback.setanimationable(0);
       animantion_time=-1;

   }

    @Override
    public void tick() {

       if (this.volcanoback.getTarget()!=null){
           animantion_time++;
           this.volcanoback.getLookControl().setLookAt(this.volcanoback.getTarget(), 30.0F, 30.0F);

           checkAndPerformAttack();}
        super.tick();
    }

    @Override
    public boolean canContinueToUse() {
        return time > animantion_time;
    }

    @Override
    public boolean canUse() {

     LivingEntity livingentity=this.volcanoback.getTarget();

     if (volcanoback.getgroundpound()>=100){
         return false;
     }


     if (livingentity==null){
         return false;
     }

        return this.volcanoback.distanceTo(livingentity)<=6.5&&volcanoback.getslapcooldown()>=50;
    }



    protected void checkAndPerformAttack() {

            if (this.animantion_time==0) {
                isinattack = true;
              this.volcanoback.setanimationtimer(23);
                return;
            }

        if (animantion_time==1){this.volcanoback.isslashing = true;
        }

        if (this.animantion_time==17&&isinattack) {
            this.volcanoback.isslashing = false;
            List<Entity> damage = this.volcanoback.level().getEntities(this.volcanoback, this.volcanoback.getBoundingBox().inflate(2), e -> this.volcanoback.position().normalize().dot(e.position().normalize()) >= 0.0&& e instanceof LivingEntity);
            for (Entity entity : damage) {

                    this.volcanoback.doHurtTarget(entity);
                    double d0 = (this.volcanoback.getBoundingBox().minX + this.volcanoback.getBoundingBox().maxX) / 2.0;
                    double d1 = (this.volcanoback.getBoundingBox().minZ + this.volcanoback.getBoundingBox().maxZ) / 2.0;
                    double d2 = entity.getX() - d0;
                    double d3 = entity.getZ() - d1;
                    double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

                    ((LivingEntity) entity).knockback(3,-(d2 / d4 * 4.0),-(d3 / d4 * 4.0));


            }
            isinattack = false;

        }




    }




}
