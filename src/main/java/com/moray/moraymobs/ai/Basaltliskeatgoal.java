package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.animal.Basaltlisk;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Slime;

public class Basaltliskeatgoal extends Goal {
   Basaltlisk basaltlisk;
int timer;
   public Basaltliskeatgoal(Basaltlisk basaltlisk){
       this.basaltlisk=basaltlisk;
   }


    @Override
    public void stop() {
        this.basaltlisk.settoungetimer(50);
       this.basaltlisk.settoungetime((byte) 0);
this.timer=50;
   }

    @Override
    public void start() {
       this.timer=50;

   }

    public boolean canContinueToUse() {

        LivingEntity entity= this.basaltlisk.getTarget();



        if (entity == null){
            return false;
        }
        if (this.basaltlisk.gettoungetimer()>0){
            return false;
        }
        if (timer<=0){
            return false;
        }
        return entity.isAlive()&&this.basaltlisk.distanceTo(entity)<=9&&
                this.basaltlisk.hasLineOfSight(entity);
    }



    @Override
    public void tick() {
       LivingEntity entity= this.basaltlisk.getTarget();

       if (entity!=null){
           timer--;
       if (entity.isAlive()&&entity instanceof Slime slime &&!this.basaltlisk.has_eaten()){
         int size =slime.getSize();
         if (timer<=35&&size==1){
             this.basaltlisk.settoungetime((byte) 1);
     float x_pull= (float) ((slime.getX()-basaltlisk.getX())*1.2);
     float y_pull= (float) ((slime.getY()-basaltlisk.getY())*1.2);
     float z_pull= (float) ((slime.getZ()-basaltlisk.getZ())*1.2);
      slime.setDeltaMovement(-x_pull,-y_pull,-z_pull);
      basaltlisk.set_eaten(true);
      slime.remove(Entity.RemovalReason.KILLED);}

       }


      if (entity.isAlive()&&this.basaltlisk.hasLineOfSight(entity)&&timer<35&& !(entity instanceof Slime slime&&slime.getSize()==1)){
           this.basaltlisk.settoungetime((byte) 1);
           entity.hurt(this.basaltlisk.damageSources().generic(),4.0f);  //may want to add a cooldown
       entity.knockback(2,-(basaltlisk.getX()-entity.getX()),-(basaltlisk.getZ()-entity.getZ()));
timer=0;
       }


       }




        super.tick();
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity= this.basaltlisk.getTarget();
        if (livingEntity ==null){
            return false;
        }
         if (this.basaltlisk.gettoungetimer()>0){
            return false;
         }

        return basaltlisk.position().distanceTo(livingEntity.position())<=9&&this.basaltlisk.hasLineOfSight(livingEntity);
    }
}
