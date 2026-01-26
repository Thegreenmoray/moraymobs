package com.moray.moraymobs.ai.animalsornpcgoals.animalornpc;

import com.moray.moraymobs.entity.living.animalornpc.Rockpup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class Intimidatetargetgoal extends Goal {
   Rockpup rockpup;

   public Intimidatetargetgoal(Rockpup rockpup) {
       this.rockpup = rockpup;
   }

    @Override
    public void start() {
        super.start();
        rockpup.setIntimdating(true);
    }


    @Override
    public void stop() {
        super.stop();
        rockpup.setIntimdating(false);
    }

    @Override
    public void tick() {
        LivingEntity livingentity = this.rockpup.getTarget();
        if (livingentity != null) {

            //playsound

        }


    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingentity = this.rockpup.getTarget();
        if (livingentity == null||rockpup.getdizzy()
        ||rockpup.isstartrolling()||rockpup.isrolling()) {return false;}

        return rockpup.distanceTo(livingentity) > 3.0F;
    }

    @Override
    public boolean canUse() {
       LivingEntity livingentity = this.rockpup.getTarget();
       if (livingentity == null||rockpup.getdizzy()
               ||rockpup.isstartrolling()||rockpup.isrolling()) {return false;}

        return rockpup.isAngryAt(livingentity)&&rockpup.distanceTo(livingentity) > 3.0F;
    }
}
