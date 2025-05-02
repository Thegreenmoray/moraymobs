package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;

public class OmnidensJumpattackgoal extends Goal {

    protected final Omnidens omnidens;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private final int attackInterval = 20;
    private long lastCanUseCheck;
    private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;

    public OmnidensJumpattackgoal(Omnidens omnidens, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        this.omnidens = omnidens;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        long i = this.omnidens.level().getGameTime();
        if (i - this.lastCanUseCheck < 20L) {
            return false;
        } else {
            this.lastCanUseCheck = i;
            LivingEntity livingentity = this.omnidens.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (this.canPenalize) {
                if (--this.ticksUntilNextPathRecalculation <= 0) {
                    this.path = this.omnidens.getNavigation().createPath(livingentity, 0);
                    this.ticksUntilNextPathRecalculation = 4 + this.omnidens.getRandom().nextInt(7);
                    return this.path != null;
                } else {
                    return true;
                }
            } else if(livingentity.getY()-omnidens.getY()>=10) {
                this.path = this.omnidens.getNavigation().createPath(livingentity, 0);
                return this.path != null ? true : this.isWithinMeleeAttackRange(livingentity);
            }else {return false;}
        }



    }




    public boolean isWithinMeleeAttackRange(LivingEntity entity) {
        return this.omnidens.getHitbox().inflate(-10,20,-10).intersects(entity.getHitbox());
    }



    public boolean canContinueToUse() {
        LivingEntity livingentity = this.omnidens.getTarget();
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.omnidens.getNavigation().isDone();
        } else {
            return !this.omnidens.isWithinRestriction(livingentity.blockPosition()) ? false : !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
        }
    }

    public void start() {
        this.omnidens.getNavigation().moveTo(this.path, this.speedModifier);
        this.omnidens.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    public void stop() {
        LivingEntity livingentity = this.omnidens.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.omnidens.setTarget((LivingEntity)null);
        }

        this.omnidens.setAggressive(false);
        this.omnidens.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        LivingEntity livingentity = this.omnidens.getTarget();
        if (livingentity != null) {
            this.omnidens.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            if ((this.followingTargetEvenIfNotSeen || this.omnidens.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == (double)0.0F && this.pathedTargetY == (double)0.0F && this.pathedTargetZ == (double)0.0F || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= (double)1.0F || this.omnidens.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.omnidens.getRandom().nextInt(7);
                double d0 = this.omnidens.distanceToSqr(livingentity);
                if (this.canPenalize) {
                    this.ticksUntilNextPathRecalculation += this.failedPathFindingPenalty;
                    if (this.omnidens.getNavigation().getPath() != null) {
                        Node finalPathPoint = this.omnidens.getNavigation().getPath().getEndNode();
                        if (finalPathPoint != null && livingentity.distanceToSqr((double)finalPathPoint.x, (double)finalPathPoint.y, (double)finalPathPoint.z) < (double)1.0F) {
                            this.failedPathFindingPenalty = 0;
                        } else {
                            this.failedPathFindingPenalty += 10;
                        }
                    } else {
                        this.failedPathFindingPenalty += 10;
                    }
                }

                if (d0 > (double)1024.0F) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (d0 > (double)256.0F) {
                    this.ticksUntilNextPathRecalculation += 5;
                }

                if (!this.omnidens.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }

                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }

            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            this.checkAndPerformAttack(livingentity);
        }

    }

    protected void checkAndPerformAttack(LivingEntity target) {
        if (this.canPerformAttack(target)) {
            this.resetAttackCooldown();
            this.omnidens.swing(InteractionHand.MAIN_HAND);
            this.omnidens.doHurtTarget(target);
        }

    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(20);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean canPerformAttack(LivingEntity entity) {
        return this.isTimeToAttack() && this.omnidens.isWithinMeleeAttackRange(entity) && this.omnidens.getSensing().hasLineOfSight(entity);
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    protected int getAttackInterval() {
        return this.adjustedTickDelay(20);
    }
}


