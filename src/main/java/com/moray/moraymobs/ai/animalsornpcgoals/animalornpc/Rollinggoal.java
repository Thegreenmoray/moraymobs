package com.moray.moraymobs.ai.animalsornpcgoals.animalornpc;

import com.moray.moraymobs.entity.living.animalornpc.Rockpup;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Rollinggoal extends Goal {
   Rockpup rockpup;
int timee;
int go;

    public Rollinggoal(Rockpup rockpup,int timee) {
   this.rockpup = rockpup;
   this.timee = timee;
    }


    @Override
    public void start() {
    go=0;
        super.start();
    }

    @Override
    public void tick() {
        LivingEntity livingentity = this.rockpup.getTarget();

        if (livingentity != null) {
         go++;

         if (go==2){
             rockpup.setstartrolling(true);
         }


         if (go<20){
             rockpup.getNavigation().stop();
         }

         if (go == 20) {
             rockpup.setstartrolling(false);
             rockpup.setrolling(true);
         }
         if (go >20) {
             float x = (float) (livingentity.getX() - rockpup.getX());
             float z = (float) (livingentity.getZ() - rockpup.getZ());

             float theta= (float) Mth.atan2(z,x);
             rockpup.setYBodyRot(-theta*Mth.RAD_TO_DEG);
             rockpup.addDeltaMovement(new Vec3(x,0,z).normalize().scale(0.5));


         List<Entity> ent=rockpup.level().getEntities(rockpup, rockpup.getBoundingBox());
         for (Entity e:ent) {
             if (e instanceof Rockpup rockpup1 && rockpup1.isrolling()) {
                 //playsound
                 Vec3 bonk = rockpup.position().normalize().cross(rockpup1.position().normalize());
                 rockpup.addDeltaMovement(bonk);
                 rockpup1.addDeltaMovement(bonk.scale(-1));


                 rockpup.setrolling(false);
                 rockpup1.setrolling(false);

                 rockpup.setdizzy(true);
                 rockpup1.setdizzy(true);
                 continue;

             }

             if (e instanceof LivingEntity entity) {
                 entity.hurt(rockpup.damageSources().mobAttack(entity), 5);

                 float x_q = (float) (entity.getX() - rockpup.getX());
                 float z_q = (float) (entity.getZ() - rockpup.getZ());
                 entity.knockback(1, -x_q * 2, -z_q * 2);

             }



         }




         }

        }

    }

    @Override
    public void stop() {
        rockpup.setstartrolling(false);
        rockpup.setrolling(false);
        rockpup.setdizzy(true);
        super.stop();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingentity = this.rockpup.getTarget();
        if (livingentity == null) {return false;}

        return timee>go||rockpup.getdizzy();
    }

    @Override
    public boolean canUse() {

        LivingEntity livingentity = this.rockpup.getTarget();
        if (livingentity == null||rockpup.getdizzy()) {return false;}


        return rockpup.distanceTo(livingentity) <= 3.0D;
    }
}
