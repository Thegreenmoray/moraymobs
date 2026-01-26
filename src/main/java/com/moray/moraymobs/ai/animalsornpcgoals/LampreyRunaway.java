package com.moray.moraymobs.ai.animalsornpcgoals;

import com.moray.moraymobs.entity.living.animalornpc.Lamprey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class LampreyRunaway extends Goal {
Lamprey lamprey;

    public LampreyRunaway(Lamprey lamprey) {
   this.lamprey = lamprey;
    }


    @Override
    public void tick() {
        LivingEntity entity =lamprey.getTarget();

        if (entity != null){
           float x = (float) (entity.getX()-lamprey.getX());
           float z = (float) (entity.getZ()-lamprey.getZ());
           float theta= (float) Mth.atan2(z,x);
           lamprey.setYBodyRot(-theta*Mth.RAD_TO_DEG);
           lamprey.addDeltaMovement(new Vec3(-x,0,-z).normalize().scale(0.5));

        }
    }

    @Override
    public boolean canUse() {
        LivingEntity entity =lamprey.getTarget();

        if (entity == null) return false;


        return lamprey.getbloodsackstorage()>0;
    }
}
