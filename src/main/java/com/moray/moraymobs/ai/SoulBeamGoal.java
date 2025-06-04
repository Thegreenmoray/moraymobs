package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.monster.Soulcatcher;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class SoulBeamGoal extends Goal {
    Soulcatcher soulcatcher;
    int timer;
    int count;
    Vec3 vec3d;
    Vec3 vec3d2;
    Vec3 vec3d3;
    public SoulBeamGoal(Soulcatcher soulcatcher,int timer){
        this.soulcatcher=soulcatcher;
        this.timer=timer;
    }

    @Override
    public void start() {
count=0;
    }

    @Override
    public void stop() {
count=0;
this.soulcatcher.setBeamtimer(0);
    }

    @Override
    public void tick() {
        LivingEntity livingEntity=this.soulcatcher.getTarget();
         ++count;
        if (livingEntity!=null){
if (count<15){
   float x_diff= (float) (livingEntity.getX()-this.soulcatcher.getX());
   float z_diff=(float) (livingEntity.getZ()-this.soulcatcher.getZ());
    this.soulcatcher.setYRot((float) Mth.atan2(z_diff,x_diff));
}

if (count>=25&&count<29) {
    vec3d = this.soulcatcher.position().add(0.0D, 1.600000023841858D, 0.0D);
    vec3d2 = livingEntity.getEyePosition().subtract(vec3d);
    vec3d3 = vec3d2.normalize();
}


if (count==29){
        for (int i = 1; i < Mth.floor(vec3d2.length()) + 7; ++i) {
            Vec3 vec3d4 = vec3d.add(vec3d3.scale((double) i));
            ((ServerLevel) this.soulcatcher.level()).sendParticles(ParticleTypes.SOUL, vec3d4.x, vec3d4.y + 1.0D, vec3d4.z, i, 0.0D, 0.0D, 0.0D, 0.0D);
      if (livingEntity.position().distanceTo(vec3d4)<2){
          livingEntity.hurt(this.soulcatcher.damageSources().sonicBoom(soulcatcher),10);
      }

        }

}}
    }

    @Override
    public boolean canContinueToUse() {
        return timer > count;
    }


    @Override
    public boolean canUse() {
        LivingEntity livingEntity=this.soulcatcher.getTarget();
        if (livingEntity==null){
            return false;
        }

        return this.soulcatcher.distanceTo(livingEntity)<7&&this.soulcatcher.gettimer()<50&&this.soulcatcher.getbeamtimer()>140;
    }
}
