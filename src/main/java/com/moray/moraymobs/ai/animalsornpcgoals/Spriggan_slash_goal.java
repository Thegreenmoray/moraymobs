package com.moray.moraymobs.ai.animalsornpcgoals;

import com.moray.moraymobs.entity.living.animalornpc.Spriggan;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Spriggan_slash_goal extends Goal {
   //implement this later, work on animation of slash, doesnt even have to be this update
    //just at some point, maybe on 1.6.1
Spriggan spriggan;
int time_left;
Vec3 vec3;
int go;

public Spriggan_slash_goal(Spriggan spg,int time_left){
    spriggan=spg;
this.time_left=time_left;

}
    @Override
    public void start() {
        go=0;
        this.spriggan.setslashingtime(55);
    }


    public void stop() {
        this.spriggan.setslashingtime(0);
        go=0;
    }



@Override
 public void tick() {
    LivingEntity entity =spriggan.getTarget();
    if(entity!=null){

       go++;
       //start time left as 55
        if (go>10&&go<20) {
           float x = (float) (entity.getX() - spriggan.getX());
           float z = (float) (entity.getZ() - spriggan.getZ());

          vec3 = new Vec3(x, 0, z);
            Vec3 jump= vec3.normalize().scale(0.1);

            spriggan.addDeltaMovement(jump.add(x ,jump.y(),z ).normalize().scale(0.9));

            spriggan.setYBodyRot((float) Mth.atan2(z,x)*Mth.RAD_TO_DEG);
           spriggan.lookAt(entity, (float) -entity.getY(), (float) entity.getX());
      }


      if (go>40&&go<50){
          List<Entity> range=spriggan.level().getEntities(spriggan,spriggan.getBoundingBox().inflate(1.2),e->this.spriggan.position().normalize().dot(e.position().normalize()) >= 0.0);

      for(Entity e:range){
          if(e instanceof Player living&&living.isBlocking()){
              living.getUseItem().hurtAndBreak(10, living, LivingEntity.getSlotForHand(living.getUsedItemHand()));
              living.getCooldowns().addCooldown(living.getUseItem().getItem(), 200);
              living.stopUsingItem();
              this.spriggan.level().broadcastEntityEvent(living, (byte) 30);
              living.hurt(living.damageSources().mobAttack(spriggan), 4.0F);
          }else if(e instanceof LivingEntity living){
              living.hurt(living.damageSources().mobAttack(spriggan), 8.0F);
          }}
      }



    }


    }




    @Override
    public boolean canContinueToUse() {
        return time_left>go;
    }

    @Override
    public boolean canUse() {
     LivingEntity entity =spriggan.getTarget();

     if (entity==null) return false;



    return
            entity.distanceTo(spriggan)>4&&
        !spriggan.isSleeping()&&spriggan.getRandom().nextInt(8)==0
            &&spriggan.getbeamtime()<0 &&spriggan.isstreching()<=0
          && spriggan.getslashingtime()<-100&&spriggan.getbombtime()<0
            ;
    }
}
