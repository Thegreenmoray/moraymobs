package com.moray.moraymobs.ai.animalsornpcgoals;

import com.moray.moraymobs.entity.living.animalornpc.Spriggan;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class Sleep_goal extends Goal {
    Spriggan spriggan;

   public Sleep_goal(Spriggan spriggan){
       this.spriggan=spriggan;
   }


    @Override
    public void tick() {
    spriggan.stopInPlace();


//particals


        super.tick();
    }


    @Override
    public void start() {
        spriggan.setIssleeping(true);
    }


    @Override
    public void stop() {
        spriggan.setIssleeping(false);
    }


    @Override
    public boolean canContinueToUse() {
        LivingEntity entity=spriggan.getTarget();



        return !spriggan.level().isDay()&&entity==null;
    }

    @Override
    public boolean canUse() {
        LivingEntity entity=spriggan.getTarget();



        return !spriggan.level().isDay()&&entity==null;
    }
}
