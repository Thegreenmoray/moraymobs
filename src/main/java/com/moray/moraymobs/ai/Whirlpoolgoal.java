package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.projectiles.Geyser;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Whirlpoolgoal extends Goal {

    private Omnidens omnidens;
    int timer;
    int count;
    boolean ready=true;
    BlockPos newblockPos = BlockPos.ZERO;
    public Whirlpoolgoal(Omnidens omnidens, int timer) {
        this.omnidens=omnidens;
        this.timer=timer;
    }


    @Override
    public void tick() {
        LivingEntity living=omnidens.getTarget();

       if (living!=null) {

           if (count % 10 == 0 && ready) {
               newblockPos = living.blockPosition();
           }

           count++;
           if (count % 10 == 0 && count % 20 != 0) {
               while (newblockPos.getY() > omnidens.level().getMinBuildHeight()) {

                   //create a start up to give the player a chance to escape
                   if (!omnidens.level().isEmptyBlock(newblockPos)) {
                       for (int i = 0; i <= 7; i++) {
                           ((ServerLevel) omnidens.level()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, newblockPos.getX(), newblockPos.getY() + i, newblockPos.getZ(), i, 0.0D, 0.0D, 0.0D, 0.0D);
                           ((ServerLevel) omnidens.level()).sendParticles(ParticleTypes.BUBBLE_COLUMN_UP, newblockPos.getX(), newblockPos.getY() + i, newblockPos.getZ(), i, 0.0D, 0.0D, 0.0D, 0.0D);
                       }
                     ready=false;
                       break;
                   }
                   newblockPos = newblockPos.below();

               }

           }

           if (count % 20 == 0) {
               if (!omnidens.level().isEmptyBlock(newblockPos)) {
                   Geyser geyser = new Geyser(this.omnidens.level());
                   geyser.setPos(Vec3.atLowerCornerOf(newblockPos));
                   omnidens.level().addFreshEntity(geyser);
               ready=true;
               }
           }

       }
    }




    @Override
    public boolean canContinueToUse() {
        return timer>count;
    }

    @Override
    public void start() {
        count= 0;
    }

    @Override
    public void stop() {
        count=0;
        omnidens.setwhirlpool(0);
    }

    @Override
    public boolean canUse() {

       if (omnidens.getTarget()==null){
           return false;
       }


        return omnidens.getwhirlpool()>100&&omnidens.getRandom().nextInt(20)==10;
    }
}
