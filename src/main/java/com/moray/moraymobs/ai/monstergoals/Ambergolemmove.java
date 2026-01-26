package com.moray.moraymobs.ai.monstergoals;

import com.moray.moraymobs.entity.living.monster.Amber_golem;
import com.moray.moraymobs.entity.living.monster.Volcanoback;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;
//honest just merge most of the move classes into one class
//this is fine for now, but next major update rework this
//make most mobs an abstract class of something,
//unless its unique enough in order require separate code
public class Ambergolemmove extends Goal {

    final double speedModifier;
    final boolean followingTargetEvenIfNotSeen;
   Amber_golem amberGolem;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;


    //largely copied from melee attack but with some changes
    public Ambergolemmove(Amber_golem amberGolem, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        this.speedModifier = pSpeedModifier;
        this.followingTargetEvenIfNotSeen = pFollowingTargetEvenIfNotSeen;
        this.amberGolem=amberGolem;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }


    @Override
    public void tick() {
        LivingEntity livingentity = this.amberGolem.getTarget();
        if (livingentity != null) {
            this.amberGolem.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);


            if ((this.followingTargetEvenIfNotSeen || this.amberGolem.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.amberGolem.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.amberGolem.getRandom().nextInt(7);
                double d0 = this.amberGolem.distanceToSqr(livingentity);
                if (this.canPenalize) {
                    this.ticksUntilNextPathRecalculation += this.failedPathFindingPenalty;
                    if (this.amberGolem.getNavigation().getPath() != null) {
                        Node finalPathPoint = this.amberGolem.getNavigation().getPath().getEndNode();
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

                if (!this.amberGolem.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }

                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);  //this should be changed


        }




    }




    public boolean canUse() {

        LivingEntity livingentity = this.amberGolem.getTarget();
        if (livingentity == null|| amberGolem.getanimation() !=0) {
            return false;}



        if(amberGolem.isready()){

        if (!livingentity.isAlive()) {
            return false;
        } else {
            this.path = this.amberGolem.getNavigation().createPath(livingentity, 0);
            if (this.path != null) {
                return true;
            }
            else {
                return this.getAttackReachSqr(livingentity) > 5.5;
            }}}
        return false;
    }

    public boolean canContinueToUse() {
        LivingEntity livingentity = this.amberGolem.getTarget();


        if (livingentity == null) {
            return false;}

        if (amberGolem.isready()){

        if (!livingentity.isAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.amberGolem.getNavigation().isDone();
        } else if (!this.amberGolem.isWithinRestriction(livingentity.blockPosition())) {
            return false;
        } else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative()&&amberGolem.isready();
        }}
        return false;
    }

    public void start() {

        this.amberGolem.getNavigation().moveTo(this.path, this.speedModifier);
        this.amberGolem.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    public void stop() {
        LivingEntity livingentity = this.amberGolem.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.amberGolem.setTarget((LivingEntity)null);
        }
        this.amberGolem.setAggressive(false);
        this.amberGolem.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return (double)(this.amberGolem.getBbWidth() * this.amberGolem.getBbWidth() + pAttackTarget.getBbWidth());
    }
}
