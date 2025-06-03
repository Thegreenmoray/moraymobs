package com.moray.moraymobs.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class MeleeomnidensGoal extends MeleeAttackGoal {

    public MeleeomnidensGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
    }

    protected boolean canPerformAttack(LivingEntity entity) {
        return this.isTimeToAttack()  && this.mob.getSensing().hasLineOfSight(entity)&& this.mob.distanceTo(entity)<4.5;
    }

}
