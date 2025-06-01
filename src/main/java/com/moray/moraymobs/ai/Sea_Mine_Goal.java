package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.projectiles.Sea_Mine;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class Sea_Mine_Goal extends Goal {
    private Omnidens omnidens;
    int timer;
    int count;
    Vec3 pos = Vec3.ZERO;
List<Sea_Mine> seaMines=new ArrayList<>();
    public Sea_Mine_Goal(Omnidens omnidens, int timer) {
        this.omnidens=omnidens;
        this.timer=timer;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity=this.omnidens.getTarget();
        omnidens.stopInPlace();
        if (livingEntity!=null){
            this.omnidens.lookAt(livingEntity, (float) -livingEntity.getY(), (float) livingEntity.getX());


            if (count==0){
                double d0 = 5;
                double d1 = 5;
                double d2 = livingEntity.getX() - d0;
                double d3 = livingEntity.getZ() - d1;
                double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);
                livingEntity.knockback(10,-(d2 / d4*6 ),-(d3 / d4*6 ));
            }


            count++;

           if (count==1) {
               for (Sea_Mine seamine:this.seaMines) {
                   int random = 2+ (this.omnidens.level().random.nextBoolean() ? 1 : -1)
                           * this.omnidens.level().random.nextIntBetweenInclusive(3, 5);
                   seamine.setPos(livingEntity.getX() + random, livingEntity.getY(), livingEntity.getZ() + random);

                   while (seamine.position().equals(pos)) {
                       seamine.setPos(livingEntity.getX() + random, Mth.floor(livingEntity.getY()), livingEntity.getZ() + random);
                       pos = seamine.position();
                   }

                   this.omnidens.level().addFreshEntity(seamine);
               }
           }






        }


    }

    @Override
    public void start() {
        count=0;
        for (int i = 0; i < 8; i++) {
            Sea_Mine seamine = new Sea_Mine(this.omnidens.level());
        seaMines.add(seamine);
        }
    }

    @Override
    public void stop() {
        count=0;
        for (Sea_Mine sea_mine : this.seaMines) {
            if (sea_mine.isAlive()) {
                sea_mine.discard();

            }}
        seaMines.clear();
    }

    @Override
    public boolean canContinueToUse() {

        if (this.omnidens.getTarget()==null){
            return false;
        }


        return timer>count&& this.omnidens.hurtTime == 0;
    }

    @Override
    public boolean canUse() {
        if(this.omnidens.getTarget()==null){

            return false;
        }



        return this.omnidens.getboomerangtime()>100;

    }



}
