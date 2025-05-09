package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

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
    public void start() {
        count=0;
    }

    @Override
    public void stop() {
        count=0;
        omnidens.setnonvisble(false);
        omnidens.setInvulnerable(false);
        omnidens.setInvisible(false);
        omnidens.setBurrow(0);
    }

    @Override
    public void tick() {
       LivingEntity entity =this.omnidens.getTarget();

      if (entity!=null){
        count++;

        if (count==20){ omnidens.setnonvisble(true);}

          if (count==35){
              omnidens.setInvisible(true);
              omnidens.setInvulnerable(true);
          }

          if (count==55){
              omnidens.setPos(entity.getX(),entity.getY(),entity.getZ());
          }

          if (count==70){
              omnidens.setInvisible(false);
              omnidens.setInvulnerable(false);
              omnidens.setnonvisble(false);
         List<Entity> entityList= omnidens.level().getEntities(omnidens,omnidens.getBoundingBox().inflate(1.5));
         for (Entity entity1:entityList){
             double d0 = (this.omnidens.getBoundingBox().minX + this.omnidens.getBoundingBox().maxX) / 2.0;
             double d1 = (this.omnidens.getBoundingBox().minZ + this.omnidens.getBoundingBox().maxZ) / 2.0;
             double d2 = entity.getX() - d0;
             double d3 = entity.getZ() - d1;
             double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);
             if (entity instanceof Player player&&!player.isBlocking()) {
                 ((LivingEntity) entity).knockback(2, -(d2 / d4 * 3.0), -(d3 / d4 * 3.0));
                 entity1.hurt(this.omnidens.damageSources().generic(),20);
             }

             if (entity instanceof Player player&&player.isBlocking()) {
                 ((LivingEntity) entity).knockback(1, -(d2 / d4 * 3.0), -(d3 / d4 * 3.0));
                 player.getUseItem().hurtAndBreak((int) 30, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
                 player.getCooldowns().addCooldown(player.getUseItem().getItem(), 150);
                 player.stopUsingItem();
                 entity1.hurt(this.omnidens.damageSources().generic(), 10);
                 this.omnidens.level().broadcastEntityEvent(player, (byte) 30);
             }

         }

          }


      }


    }

    @Override
    public boolean canContinueToUse() {
        return timer>count;
    }

    @Override
    public boolean canUse() {
      Entity entity=this.omnidens.getTarget();
      if (entity==null){
          return false;
      }


        return omnidens.getBurrow()>200&&omnidens.getRandom().nextInt(15)==6;
    }
}
