package com.moray.moraymobs.ai.monstergoals;

import com.moray.moraymobs.entity.living.monster.Amber_golem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class Ambergolemsucc extends Goal {
Amber_golem ambergolem;

    public Ambergolemsucc(Amber_golem ambergolem){
    this.ambergolem=ambergolem;
    }

    @Override
    public boolean canContinueToUse() {
        return ambergolem.getsucc()>0;
    }


    @Override
    public void tick() {

        ambergolem.stopInPlace();
  if (ambergolem.getsucc()==70){
      ambergolem.setanimation(2);
  }
   if (ambergolem.getsucc()<=68){

       List<Entity> damage = this.ambergolem.level().getEntities(this.ambergolem, this.ambergolem.getBoundingBox().inflate(10), e ->  e instanceof LivingEntity//&& (!(e instanceof Animal))||e instanceof Player
       );
       for (Entity ent : damage) {


       if(ent.distanceTo(ambergolem)<=3){
           ent.hurt(ent.damageSources().mobAttack(ambergolem),4);
           double d0 = (this.ambergolem.getBoundingBox().minX + this.ambergolem.getBoundingBox().maxX) / 2.0;
           double d1 = (this.ambergolem.getBoundingBox().minZ + this.ambergolem.getBoundingBox().maxZ) / 2.0;
           double d2 = ent.getX() - d0;
           double d3 = ent.getZ() - d1;
           double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

           ((LivingEntity) ent).knockback(3,-(d2 / d4 * 4.0),-(d3 / d4 * 4.0));}

       }



}

        super.tick();
    }

    @Override
    public void stop() {
      ambergolem.setanimation(0);
    }

    @Override
    public void start() {
      ambergolem.setsucc(100);
      ambergolem.stopInPlace();
      ambergolem.setanimation(3);
    }

    @Override
    public boolean canUse() {
        LivingEntity entity=this.ambergolem.getTarget();

      if (entity==null){
          return false;
      }



        return this.ambergolem.level().random.nextInt(20)==6
                &&ambergolem.getsucc()<=-100&&ambergolem.isready();//something
    }
}
