package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class Burrowgoal extends Goal {
    private Omnidens omnidens;
    int timer;
    int count;

    public Burrowgoal(Omnidens omnidens, int timer) {
        this.omnidens=omnidens;
        this.timer=timer;
    }

    @Override
    public void tick() {
       LivingEntity entity =this.omnidens.getTarget();
omnidens.setInvulnerable(true);
      if (entity!=null){
        --count;

          if (count==30){
              omnidens.setInvisible(true);

          }


          if (count==10){
         omnidens.setPos(entity.getX()+5,entity.getY(),entity.getZ()+5);
         List<Entity> entityList= omnidens.level().getEntities(omnidens,omnidens.getBoundingBox().inflate(1.5));
         for (Entity entity1:entityList){
             entity1.hurt(this.omnidens.damageSources().generic(),25);
             double d0 = (this.omnidens.getBoundingBox().minX + this.omnidens.getBoundingBox().maxX) / 2.0;
             double d1 = (this.omnidens.getBoundingBox().minZ + this.omnidens.getBoundingBox().maxZ) / 2.0;
             double d2 = entity.getX() - d0;
             double d3 = entity.getZ() - d1;
             double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

             ((LivingEntity) entity).knockback(3,-(d2 / d4 * 3.0),-(d3 / d4 * 3.0));

         }
          }


      }


    }

    @Override
    public boolean canUse() {
        return omnidens.getBurrow()>200;
    }
}
