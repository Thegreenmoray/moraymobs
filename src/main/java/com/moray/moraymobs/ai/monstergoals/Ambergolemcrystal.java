package com.moray.moraymobs.ai.monstergoals;

import com.moray.moraymobs.entity.living.monster.Amber_golem;
import com.moray.moraymobs.entity.projectiles.Ambercrystal;
import com.moray.moraymobs.entity.projectiles.friendlyprojectile.Amberportal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class Ambergolemcrystal extends Goal {
    Amber_golem ambergolem;

    public Ambergolemcrystal(Amber_golem ambergolem){
        this.ambergolem=ambergolem;
    }

    @Override
    public void tick() {
        LivingEntity lentity=this.ambergolem.getTarget();

     if (lentity!=null){

     if (ambergolem.getslam()==24){

         List<Entity> damage = this.ambergolem.level().getEntities(this.ambergolem, this.ambergolem.getBoundingBox().inflate(2), e -> e instanceof LivingEntity&&e.onGround());
         for (Entity entity : damage) {
             double d0 = (this.ambergolem.getBoundingBox().minX + this.ambergolem.getBoundingBox().maxX) / 2.0;
             double d1 = (this.ambergolem.getBoundingBox().minZ + this.ambergolem.getBoundingBox().maxZ) / 2.0;
             double d2 = entity.getX() - d0;
             double d3 = entity.getZ() - d1;
             double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);
             entity.hurt(ambergolem.damageSources().mobAttack((LivingEntity) entity),10);
             ((LivingEntity) entity).knockback(5, -(d2 / d4 * 4.0), -(d3 / d4 * 4.0));}


         }




         if (ambergolem.getslam()==10){
             List<Entity> damage = this.ambergolem.level().getEntities(this.ambergolem, this.ambergolem.getBoundingBox().inflate(10),entity -> entity.distanceTo(ambergolem)>=4);
             for (Entity entity : damage) {

                     Ambercrystal ambercrystal= new Ambercrystal(ambergolem.level());
                             ambergolem.level().addFreshEntity(ambercrystal);
                 Ambercrystal ambercrystal1=new Ambercrystal(ambergolem.level());
                         ambergolem.level().addFreshEntity(ambercrystal1);
                 Ambercrystal ambercrystal2=new Ambercrystal(ambergolem.level());
                         ambergolem.level().addFreshEntity(ambercrystal2);
                 Ambercrystal ambercrystal3=new Ambercrystal(ambergolem.level());
                         ambergolem.level().addFreshEntity(ambercrystal3);
                 Ambercrystal ambercrystal4=new Ambercrystal(ambergolem.level());
                         ambergolem.level().addFreshEntity(ambercrystal4);
                 if (!entity.onGround()){

                     Amberportal amberportal=new Amberportal(ambergolem.level());
                     amberportal.setPos(entity.getX(),entity.getY(),entity.getZ());
                 }
                 ambercrystal.setPos(entity.getX(),entity.getY(),entity.getZ());
                 ambercrystal1.setPos(entity.getX()+1,entity.getY(),entity.getZ());
                 ambercrystal2.setPos(entity.getX()-1,entity.getY(),entity.getZ());
                 ambercrystal3.setPos(entity.getX(),entity.getY(),entity.getZ()+1);
                 ambercrystal4.setPos(entity.getX(),entity.getY(),entity.getZ()-1);
             }



         }


     }




    }

    @Override
    public boolean canContinueToUse() {
        return ambergolem.getslam()>=0;
    }


    @Override
    public void start() {
        ambergolem.setslam(50);
   ambergolem.setanimation(4);
    }

    @Override
    public void stop() {
        ambergolem.setanimation(0);

        super.stop();
    }


    @Override
    public boolean canUse() {
        LivingEntity entity=this.ambergolem.getTarget();

        if (entity==null){
            return false;
        }

        return this.ambergolem.level().random.nextInt(7)==1&&ambergolem.isready()
                &&ambergolem.getslam()<=-150&&ambergolem.getHealth()<=ambergolem.getMaxHealth()/2;//something
    }
}
