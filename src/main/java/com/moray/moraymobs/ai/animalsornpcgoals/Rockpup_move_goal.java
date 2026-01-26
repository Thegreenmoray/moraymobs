package com.moray.moraymobs.ai.animalsornpcgoals;

import com.moray.moraymobs.entity.living.animalornpc.Rockpup;
import com.moray.moraymobs.entity.living.animalornpc.Spriggan;
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
public class Rockpup_move_goal extends Goal {

    final double speedModifier;
    final boolean followingTargetEvenIfNotSeen;
    Rockpup rockpup;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;


    //largely copied from melee attack but with some changes
    public Rockpup_move_goal(Rockpup rockpup, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        this.speedModifier = pSpeedModifier;
        this.followingTargetEvenIfNotSeen = pFollowingTargetEvenIfNotSeen;
      this.rockpup = rockpup;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }


    @Override
    public void tick() {
        LivingEntity livingentity = this.rockpup.getTarget();
        if (livingentity != null) {
            this.rockpup.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);


            if ((this.followingTargetEvenIfNotSeen || this.rockpup.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.rockpup.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.rockpup.getRandom().nextInt(7);
                double d0 = this.rockpup.distanceToSqr(livingentity);
                if (this.canPenalize) {
                    this.ticksUntilNextPathRecalculation += this.failedPathFindingPenalty;
                    if (this.rockpup.getNavigation().getPath() != null) {
                        Node finalPathPoint = this.rockpup.getNavigation().getPath().getEndNode();
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

                if (!this.rockpup.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }

                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);  //this should be changed


        }




    }




    public boolean canUse() {

        if (rockpup.getdizzy()){
            return false;
        }

        if (rockpup.isintimdating()){
            return false;
        }

        if (rockpup.isstartrolling()){
            return false;
        }

        LivingEntity livingentity = this.rockpup.getTarget();



        if (livingentity == null) {
            return false;}


        if (!livingentity.isAlive()) {
            return false;
        } else {
            this.path = this.rockpup.getNavigation().createPath(livingentity, 0);
            if (this.path != null) {
                return true;
            }
            else {
                return this.getAttackReachSqr(livingentity) > 2.5;
            }}

    }

    public boolean canContinueToUse() {
        LivingEntity livingentity = this.rockpup.getTarget();

        if (rockpup.getdizzy()){
            return false;
        }

        if (rockpup.isintimdating()){
            return false;
        }

        if (rockpup.isstartrolling()){
            return false;
        }


        if (livingentity == null) {
            return false;}



        if (!livingentity.isAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.rockpup.getNavigation().isDone();
        } else if (!this.rockpup.isWithinRestriction(livingentity.blockPosition())) {
            return false;
        } else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
        }

    }

    public void start() {

        this.rockpup.getNavigation().moveTo(this.path, this.speedModifier);
        this.rockpup.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    public void stop() {
        LivingEntity livingentity = this.rockpup.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.rockpup.setTarget((LivingEntity)null);
        }
        this.rockpup.setAggressive(false);
        this.rockpup.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return (double)(this.rockpup.getBbWidth() * this.rockpup.getBbWidth() + pAttackTarget.getBbWidth());
    }
}

