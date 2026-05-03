package com.moray.moraymobs.ai.monstergoals.dullahangoals;

import com.moray.moraymobs.entity.living.monster.Dullahan;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class Dullahanmove extends Goal {

    final double speedModifier;
    final boolean followingTargetEvenIfNotSeen;
    Dullahan dullahan;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;


    //largely copied from melee attack but with some changes
    public Dullahanmove(Dullahan dullahan, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        this.speedModifier = pSpeedModifier;
        this.followingTargetEvenIfNotSeen = pFollowingTargetEvenIfNotSeen;
        this.dullahan =dullahan;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }


    @Override
    public void tick() {
        LivingEntity livingentity = this.dullahan.getTarget();
        if (livingentity != null) {
            this.dullahan.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);


            if ((this.followingTargetEvenIfNotSeen || this.dullahan.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.dullahan.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.dullahan.getRandom().nextInt(7);
                double d0 = this.dullahan.distanceToSqr(livingentity);
                if (this.canPenalize) {
                    this.ticksUntilNextPathRecalculation += this.failedPathFindingPenalty;
                    if (this.dullahan.getNavigation().getPath() != null) {
                        Node finalPathPoint = this.dullahan.getNavigation().getPath().getEndNode();
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

                if (!this.dullahan.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }

                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);


        }




    }




    public boolean canUse() {

        if (!dullahan.canusespecial()
        ) {
            return false;
        }





        LivingEntity livingentity = this.dullahan.getTarget();
        if (livingentity == null) {
            return false;}

        if (!livingentity.isAlive()) {
            return false;
        } else {
            this.path = this.dullahan.getNavigation().createPath(livingentity, 0);
            if (this.path != null) {
                return true;
            }
            else {
                return this.getAttackReachSqr(livingentity) > 4.5;
            }}

    }

    public boolean canContinueToUse() {
        LivingEntity livingentity = this.dullahan.getTarget();
        if (!dullahan.canusespecial()
        ) {
            return false;
        }

        if (livingentity == null) {
            return false;}



        if (!livingentity.isAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.dullahan.getNavigation().isDone();
        } else if (!this.dullahan.isWithinRestriction(livingentity.blockPosition())) {
            return false;
        } else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
        }

    }

    public void start() {

        this.dullahan.getNavigation().moveTo(this.path, this.speedModifier);
        this.dullahan.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    public void stop() {
        LivingEntity livingentity = this.dullahan.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.dullahan.setTarget((LivingEntity)null);
        }
        this.dullahan.setAggressive(false);
        this.dullahan.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return (double)(this.dullahan.getBbWidth() * this.dullahan.getBbWidth() + pAttackTarget.getBbWidth());
    }
}
