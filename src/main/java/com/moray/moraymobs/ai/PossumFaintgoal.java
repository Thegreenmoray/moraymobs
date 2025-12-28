package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.animalornpc.Opossum;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class PossumFaintgoal extends Goal {

   Opossum opossum;
int faint_time;
 int out;
    public PossumFaintgoal(Opossum opossum,int faint_time_max) {
   this.opossum=opossum;
    this.faint_time=faint_time_max;
    }

    public boolean isInterruptable() {
        return false;
    }
    @Override
    public void start() {
       this.out=0;
    }

    @Override
    public void stop() {
        opossum.setFainted(false);
        this.out=0;

    }

    @Override
    public void tick() {
        super.tick();
             ++out;
        List<Entity> snetch=this.opossum.level().getEntities(this.opossum,this.opossum.getBoundingBox().inflate(5), e->e instanceof LivingEntity);

for (Entity entity:snetch){

    ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.WEAKNESS,200,2),this.opossum);
    ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.CONFUSION,200,2),this.opossum);
}
    }

    @Override
    public boolean canContinueToUse() {
        return out<faint_time;
    }


    @Override
    public boolean canUse() {
        return this.opossum.isfainted();
    }
}
