package com.moray.moraymobs.ai.animalsornpcgoals;

import com.moray.moraymobs.entity.living.animalornpc.Lamprey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec3;

public class LampreyLatch extends Goal {
    Lamprey lamprey;


    public LampreyLatch(Lamprey lamprey) {
    this.lamprey = lamprey;

    }


    @Override
    public void tick() {
        super.tick();
        LivingEntity entity =lamprey.getTarget();
        if (entity != null) {


            if (lamprey.getVehicle()== null) {
            lamprey.startRiding(entity);}

            lamprey.rotate(Rotation.CLOCKWISE_180);

            lamprey.setPos(entity.getBoundingBox().getCenter());
//perhaps add some kind blood suck counter so it can run away later?

           if (lamprey.tickCount%20==0) {
            entity.hurt(entity.level().damageSources().mobAttack(lamprey),1f);
           entity.knockback(0.0F,0.0F,0.0F);


            lamprey.setbloodsackstorage(lamprey.getbloodsackstorage()+1);
           }
        }


    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {
      lamprey.stopRiding();

    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity entity =lamprey.getTarget();

        if (entity == null) return false;

int healthcheck= entity.getHealth()<=1?1:(int) (100*entity.getHealth()/entity.getMaxHealth());

        return lamprey.getAirSupply()>50&&entity.getRandom().nextInt(healthcheck)==0;
    }

    @Override
    public boolean canUse() {
     LivingEntity entity =lamprey.getTarget();


if(entity==null) {return false;}



        return entity.getHealth()>1&&entity.isInWater()&&lamprey.getAirSupply()>50
                &&entity.position().distanceTo(lamprey.position())<4;
    }
}
