package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class Burrowgoal extends Goal {
    private Omnidens omnidens;


    public Burrowgoal(Omnidens omnidens) {
        this.omnidens=omnidens;
    }

    @Override
    public void start() {
       omnidens.setBurrow(120);
        omnidens.setanimation(9);
    }

    @Override
    public void stop() {
        omnidens.setnonvisble(false);
        omnidens.setInvulnerable(false);
        omnidens.setInvisible(false);
        omnidens.setanimation(0);
    }

    @Override
    public void tick() {
       LivingEntity entity =this.omnidens.getTarget();

      if (entity!=null){


        if (omnidens.getBurrow()==80){

            omnidens.setnonvisble(true);}

          if (omnidens.getBurrow()==66){
              omnidens.setInvisible(true);
              omnidens.setInvulnerable(true);
          }

          if (omnidens.getBurrow()==28){
              omnidens.setPos(entity.getX(),entity.getY(),entity.getZ());
          }

          if (omnidens.getBurrow()==10){
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
        return omnidens.getBurrow()>0;
    }

    @Override
    public boolean canUse() {
      Entity entity=this.omnidens.getTarget();
      if (entity==null){
          return false;
      }


        return this.omnidens.getBurrow()<=-100
                &&omnidens.canuseskill()
                &&omnidens.getHealth()<=omnidens.oneforth
                &&omnidens.level().random.nextInt(25)==10;
    }
}
