package com.moray.moraymobs.ai.animalsornpcgoals;

import com.moray.moraymobs.entity.living.animalornpc.Spriggan;
import com.moray.moraymobs.entity.projectiles.Pawpawbomb;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Spriggan_bomb_goal extends Goal {
 Spriggan spriggan;
 int i;
 int power;

    public Spriggan_bomb_goal(Spriggan spriggan, int i) {
    this.spriggan = spriggan;
    this.i = i;
    }


    @Override
    public void tick() {
        LivingEntity entity=spriggan.getTarget();

      if(entity!=null) {
             power++;


          if (power==15) {
              Pawpawbomb pawpawbomb = new Pawpawbomb(this.spriggan.level(),this.spriggan);
              spriggan.lookAt( entity, (float) -entity.getY(), (float) entity.getX());
              Vec3 vec3 = this.spriggan.getViewVector(1);
              pawpawbomb.setPos(this.spriggan.getX() + vec3.x * 2.0, this.spriggan.getY(0.33333) + 0.5, this.spriggan.getZ() + vec3.z * 2.0);
              double d0 = entity.getEyeY() - (double) 1.1F;
              double d1 = entity.getX() - this.spriggan.getX();
              double d2 = d0 - pawpawbomb.getY();
              double d3 = entity.getZ() - this.spriggan.getZ();
              double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double) 0.2F;
              pawpawbomb.shoot(d1, d2 + d4, d3, 1.1F, 0F);
              this.spriggan.level().addFreshEntity(pawpawbomb);
          }


      }




    }


    @Override
    public void start() {
        spriggan.setbombtime(30);
        power=0;
    }

    @Override
    public void stop() {
        spriggan.setbombtime(0);
        power=0;
    }

    @Override
    public boolean canContinueToUse() {

        LivingEntity entity=spriggan.getTarget();
        if (entity==null) {return false;}



        return i>power;
    }

    @Override
    public boolean canUse() {
        LivingEntity entity=spriggan.getTarget();
      if (entity==null) {return false;}




        return this.spriggan.getbeamtime()< 0&&!spriggan.issleeping()&&spriggan.isstreching()<=0
                &&spriggan.distanceTo(entity)>6&&this.spriggan.level().random.nextInt(9)==5
                &&this.spriggan.getslashingtime()<0&&spriggan.getbombtime()<=-120;
    }
}

