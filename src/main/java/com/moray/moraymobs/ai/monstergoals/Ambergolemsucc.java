package com.moray.moraymobs.ai.monstergoals;

import com.moray.moraymobs.entity.living.monster.Amber_golem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class Ambergolemsucc extends Goal {
Amber_golem ambergolem;

    public Ambergolemsucc(Amber_golem ambergolem){
    this.ambergolem=ambergolem;
    }

    @Override
    public boolean canContinueToUse() {
        return ambergolem.getsucc()<=0;
    }


    @Override
    public void tick() {







        super.tick();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void start() {
      ambergolem.setsucc(50);
    }

    @Override
    public boolean canUse() {
        LivingEntity entity=this.ambergolem.getTarget();

      if (entity==null){
          return false;
      }



        return this.ambergolem.level().random.nextInt(20)==6
                &&ambergolem.getsucc()<=-100;//something
    }
}
