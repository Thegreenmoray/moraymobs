package com.moray.moraymobs.ai.omnidensgoals;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.projectiles.Geyser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Whirlpoolgoal extends Goal {

    private Omnidens omnidens;
    boolean ready=true;
    BlockPos newblockPos = BlockPos.ZERO;
    public Whirlpoolgoal(Omnidens omnidens) {
        this.omnidens=omnidens;
    }


    @Override
    public void tick() {
        LivingEntity living=omnidens.getTarget();

       if (living!=null) {

           if (omnidens.getwhirlpool() % 10 == 0 && ready) {
               newblockPos = living.blockPosition();
           }

           if (omnidens.getwhirlpool() % 10 == 0 && omnidens.getwhirlpool() % 20 != 0) {
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

           if (omnidens.getwhirlpool() % 20 == 0) {
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
        return omnidens.getwhirlpool()>0;
    }

    @Override
    public void start() {
        omnidens.setanimation(8);
        omnidens.setwhirlpool(120);
    }

    @Override
    public void stop() {
omnidens.setanimation(0);
    }

    @Override
    public boolean canUse() {

       if (omnidens.getTarget()==null){
           return false;
       }


        return omnidens.getHealth()<=omnidens.oneforth
                &&this.omnidens.getwhirlpool()<=-100
                &&omnidens.canuseskill()
                &&omnidens.level().random.nextInt(20)==10;
    }
}
