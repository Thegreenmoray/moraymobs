package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.monster.Volcanoback;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class VolcanobackMovetowardsGoal extends Goal {

    final double speedModifier;
  final boolean followingTargetEvenIfNotSeen;
    Volcanoback volcanoback;
    int animantion_time=0;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private long lastCanUseCheck;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;


   //largely copied from melee attack but with some changes
    public VolcanobackMovetowardsGoal(Volcanoback volcanoback, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        this.speedModifier = pSpeedModifier;
        this.followingTargetEvenIfNotSeen = pFollowingTargetEvenIfNotSeen;
        this.volcanoback=volcanoback;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }


    @Override
    public void tick() {
        LivingEntity livingentity = this.volcanoback.getTarget();
        if (livingentity != null) {
            this.volcanoback.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
           this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            animantion_time--;
            if ((this.followingTargetEvenIfNotSeen || this.volcanoback.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.volcanoback.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.volcanoback.getRandom().nextInt(7);
                double d0 = this.volcanoback.distanceToSqr(livingentity);
                if (this.canPenalize) {
                    this.ticksUntilNextPathRecalculation += this.failedPathFindingPenalty;
                    if (this.volcanoback.getNavigation().getPath() != null) {
                        Node finalPathPoint = this.volcanoback.getNavigation().getPath().getEndNode();
                        if (finalPathPoint != null && livingentity.distanceToSqr((double)finalPathPoint.x, (double)finalPathPoint.y, (double)finalPathPoint.z) < 1.0) {
                            this.failedPathFindingPenalty = 0;
                        } else {
                            this.failedPathFindingPenalty += 10;
                        }
                    } else {
                        this.failedPathFindingPenalty += 10;
                    }
                }

                if (d0 > 1024.0) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (d0 > 256.0) {
                    this.ticksUntilNextPathRecalculation += 5;
                }

                if (!this.volcanoback.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }

                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);  //this should be changed


    }




    }




    public boolean canUse() {

        LivingEntity livingentity = this.volcanoback.getTarget();
        if (livingentity == null) {
            return false;}

        if(volcanoback.getslapcooldown()>=50&&this.getAttackReachSqr(livingentity) > 7.5){
            return false;
        }

        if (this.volcanoback.getgroundpound()>=100&&this.getAttackReachSqr(livingentity) > 7.5){
            return false;
        }
            if (!livingentity.isAlive()) {
                return false;
            } else {
                this.path = this.volcanoback.getNavigation().createPath(livingentity, 0);
                if (this.path != null) {
                    return true;
                }
                else {
                    return this.getAttackReachSqr(livingentity) > 7.5;
                }}
    }

    public boolean canContinueToUse() {
        LivingEntity livingentity = this.volcanoback.getTarget();

        if (livingentity == null) {
            return false;}


    if(volcanoback.getslapcooldown()>=50&&this.volcanoback.distanceTo(livingentity)<6.5){
        return false;
   }

       if (this.volcanoback.getgroundpound()>=100&&this.volcanoback.distanceTo(livingentity)<6.5){
           return false;
       }

        if (!livingentity.isAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.volcanoback.getNavigation().isDone();
        } else if (!this.volcanoback.isWithinRestriction(livingentity.blockPosition())) {
            return false;
        } else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
        }
    }

    public void start() {

       this.volcanoback.getNavigation().moveTo(this.path, this.speedModifier);
        this.volcanoback.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    public void stop() {
        LivingEntity livingentity = this.volcanoback.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.volcanoback.setTarget((LivingEntity)null);
        }
        this.volcanoback.setAggressive(false);
        this.volcanoback.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return (double)(this.volcanoback.getBbWidth() * this.volcanoback.getBbWidth() + pAttackTarget.getBbWidth());
    }

}

