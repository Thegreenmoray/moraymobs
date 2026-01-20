package com.moray.moraymobs.ai.animalsornpcgoals;

import com.moray.moraymobs.entity.living.animalornpc.Spriggan;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class Sprigganattackgoal extends Goal {
    Spriggan spriggan;

    public Sprigganattackgoal(Spriggan spriggan){
        this.spriggan=spriggan;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void tick() {

        if (this.spriggan.getTarget()!=null){

            this.spriggan.getLookControl().setLookAt(this.spriggan.getTarget(), 30.0F, 30.0F);

            checkAndPerformAttack();

        }
        super.tick();
    }


    @Override
    public boolean canUse() {

        LivingEntity livingentity=this.spriggan.getTarget();

        if (livingentity==null){
            return false;
        }

            return this.spriggan.distanceTo(livingentity)<=2.5&&spriggan.getbeamtime()<=0
                    &&spriggan.getslashingtime()<=0&&spriggan.getbombtime()<=0;
    }



    protected void checkAndPerformAttack() {
      LivingEntity entity =this.spriggan.getTarget();
           if (entity!=null) {
               this.spriggan.doHurtTarget(entity);
               double d0 = (this.spriggan.getBoundingBox().minX + this.spriggan.getBoundingBox().maxX) / 2.0;
               double d1 = (this.spriggan.getBoundingBox().minZ + this.spriggan.getBoundingBox().maxZ) / 2.0;
               double d2 = entity.getX() - d0;
               double d3 = entity.getZ() - d1;
               double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

               ((LivingEntity) entity).knockback(1, -(d2 / d4 * 4.0), -(d3 / d4 * 4.0));
           }



    }
}

