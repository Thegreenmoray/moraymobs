package com.moray.moraymobs.ai.monstergoals;

import com.moray.moraymobs.entity.living.monster.Amber_golem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class AmbergolemMelee extends Goal {

 Amber_golem ambergolem;

    public AmbergolemMelee(Amber_golem ambergolem){
        this.ambergolem=ambergolem;
    }

    @Override
    public void start() {
        ambergolem.setpunch(50);
    }

    @Override
    public void stop() {


    }

    @Override
    public void tick() {

        if (this.ambergolem.getTarget()!=null){

            this.ambergolem.getLookControl().setLookAt(this.ambergolem.getTarget(), 30.0F, 30.0F);

            checkAndPerformAttack();}
        super.tick();
    }

    @Override
    public boolean canContinueToUse() {
        return ambergolem.getpunch() >= 0;
    }

    @Override
    public boolean canUse() {

        LivingEntity livingentity=this.ambergolem.getTarget();

        if (livingentity==null){
            return false;
        }

        return this.ambergolem.distanceTo(livingentity)<=6.5;
    }



    protected void checkAndPerformAttack() {

            List<Entity> damage = this.ambergolem.level().getEntities(this.ambergolem, this.ambergolem.getBoundingBox().inflate(1.5), e -> this.ambergolem.position().normalize().dot(e.position().normalize()) >= 0.25&& e instanceof LivingEntity);
            for (Entity entity : damage) {

                this.ambergolem.doHurtTarget(entity);
                double d0 = (this.ambergolem.getBoundingBox().minX + this.ambergolem.getBoundingBox().maxX) / 2.0;
                double d1 = (this.ambergolem.getBoundingBox().minZ + this.ambergolem.getBoundingBox().maxZ) / 2.0;
                double d2 = entity.getX() - d0;
                double d3 = entity.getZ() - d1;
                double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

                ((LivingEntity) entity).knockback(5,-(d2 / d4 * 4.0),-(d3 / d4 * 4.0));



        }


    }
}
