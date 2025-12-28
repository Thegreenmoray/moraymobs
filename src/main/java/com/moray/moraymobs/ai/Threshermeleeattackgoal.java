package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.animalornpc.Thresher_shark;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class Threshermeleeattackgoal extends Goal {
    private Thresher_shark thresherShark;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private long lastUpdateTime;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;



    public Threshermeleeattackgoal(Thresher_shark thresherShark, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {

        this.thresherShark = thresherShark;
        this.speedModifier = pSpeedModifier;
        this.followingTargetEvenIfNotSeen = pFollowingTargetEvenIfNotSeen;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }


    public boolean canUse() {
        //credit to the graveyard mod for this.
        long l = this.thresherShark.level().getGameTime();
        if (l - this.lastUpdateTime < 20L) {
            this.lastUpdateTime -= 20;
            return canUse();
        } else {
            this.lastUpdateTime = l;
            LivingEntity livingEntity = this.thresherShark.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else {
                this.path = this.thresherShark.getNavigation().createPath(livingEntity, 0);
                if (this.path != null) {
                    return true;
                } else {
                    return this.getAttackReachSqr(livingEntity) >= this.thresherShark.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                }
            }
        }
    }

    public boolean canContinueToUse() {
        LivingEntity livingentity = this.thresherShark.getTarget();



        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.thresherShark.getNavigation().isDone();
        } else if (!this.thresherShark.isWithinRestriction(livingentity.blockPosition())) {
            return false;
        } else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
        }
    }

    public void start() {
        this.thresherShark.getNavigation().moveTo(this.path, this.speedModifier);
        this.thresherShark.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    public void stop() {
        LivingEntity livingentity = this.thresherShark.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.thresherShark.setTarget((LivingEntity)null);
        }

        this.thresherShark.setAggressive(false);
        this.thresherShark.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        LivingEntity livingentity = this.thresherShark.getTarget();
        if (livingentity != null) {

            this.thresherShark.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            double d0 = this.thresherShark.distanceToSqr(livingentity);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            if ((this.followingTargetEvenIfNotSeen || this.thresherShark.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.thresherShark.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.thresherShark.getRandom().nextInt(7);
                if (this.canPenalize) {
                    this.ticksUntilNextPathRecalculation += this.failedPathFindingPenalty;
                    if (this.thresherShark.getNavigation().getPath() != null) {
                        Node finalPathPoint = this.thresherShark.getNavigation().getPath().getEndNode();
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

                if (!this.thresherShark.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }

                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }

            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            this.checkAndPerformAttack(livingentity, d0);
        }

    }

    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        double d0 = this.getAttackReachSqr(pEnemy);
        if (pDistToEnemySqr <= d0 && this.ticksUntilNextAttack <= 0) {
            this.resetAttackCooldown();
            this.thresherShark.swing(InteractionHand.MAIN_HAND);
            this.thresherShark.doHurtTarget(pEnemy);
            thresherShark.setanime(1);
        }

    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(20);
    }

    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return (double)(this.thresherShark.getBbWidth()  * this.thresherShark.getBbWidth() *2 + pAttackTarget.getBbWidth());
    }
}
