package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.animalornpc.Spriggan;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class Yawn_goal extends Goal {

    Spriggan spriggan;

    public Yawn_goal(Spriggan spriggan){
        this.spriggan=spriggan;
    }


    @Override
    public void tick() {
        spriggan.stopInPlace();





        super.tick();
    }


    @Override
    public void start() {
        spriggan.setIsstreching(60);
    }


    @Override
    public void stop() {
        spriggan.setIsstreching(0);
    }


    @Override
    public boolean canContinueToUse() {
        LivingEntity entity=spriggan.getTarget();



        return !spriggan.issleeping()&&entity==null&&spriggan.isstreching()>0&&spriggan.getbeamtime()<=0;
    }

    @Override
    public boolean canUse() {
        LivingEntity entity=spriggan.getTarget();



        return !spriggan.issleeping()&&entity==null&&this.spriggan.level().random.nextInt(50)==5;
    }





}
