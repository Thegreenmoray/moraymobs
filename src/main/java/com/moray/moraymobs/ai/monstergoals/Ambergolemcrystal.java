package com.moray.moraymobs.ai.monstergoals;

import com.moray.moraymobs.entity.living.monster.Amber_golem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class Ambergolemcrystal extends Goal {
    Amber_golem ambergolem;

 public Ambergolemcrystal(Amber_golem ambergolem){
        this.ambergolem=ambergolem;
    }



    @Override
    public boolean canUse() {
        LivingEntity entity=this.ambergolem.getTarget();

        if (entity==null){
            return false;
        }

        return this.ambergolem.level().random.nextInt(15)==1
                &&ambergolem.getslam()<=-150;//something
    }
}
