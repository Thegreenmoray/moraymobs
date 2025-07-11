package com.moray.moraymobs.ai.omnidensgoals;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.projectiles.Sea_Mine;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.ArrayList;
import java.util.List;

public class Sea_Mine_Goal extends Goal {
    private Omnidens omnidens;
List<Sea_Mine> seaMines=new ArrayList<>();
    public Sea_Mine_Goal(Omnidens omnidens ) {
        this.omnidens=omnidens;

    }

    @Override
    public void tick() {
        LivingEntity livingEntity=this.omnidens.getTarget();
        omnidens.stopInPlace();
        if (livingEntity!=null){
            this.omnidens.lookAt(livingEntity, (float) -livingEntity.getY(), (float) livingEntity.getX());
int seamines= omnidens.getseamine();



           if (seamines==109) {
               for (int i = 2; i < this.seaMines.toArray().length-1; i++) {

                       float radian=livingEntity.level().random.nextInt(360)*Mth.DEG_TO_RAD;
                       float x_sign= (float) (livingEntity.getX()+i*Mth.sin(radian));
                       float z_sign= (float) (livingEntity.getZ()+i*Mth.cos(radian));


                   Sea_Mine seamine=seaMines.get(i);
                   seamine.setPos(x_sign, livingEntity.getY(), z_sign);



                   this.omnidens.level().addFreshEntity(seamine);
               }
           }






        }


    }

    @Override
    public void start() {

     omnidens.setseamine(121);
        omnidens.setanimation(7);

        for (int i = 0; i < 18; i++) {
            Sea_Mine seamine = new Sea_Mine(this.omnidens.level());
        seaMines.add(seamine);
        }
    }

    @Override
    public void stop() {
        for (Sea_Mine sea_mine : this.seaMines) {
            if (sea_mine.isAlive()) {
                sea_mine.discard();

            }}
        seaMines.clear();
        omnidens.setanimation(0);
    }

    @Override
    public boolean canContinueToUse() {

        if (this.omnidens.getTarget()==null){
            return false;
        }


        return omnidens.getseamine()>0&& this.omnidens.hurtTime == 0;
    }

    @Override
    public boolean canUse() {
        if(this.omnidens.getTarget()==null){

            return false;
        }



        return omnidens.canuseskill()
                &&omnidens.getHealth()<=omnidens.threeforths
                &&omnidens.getseamine()<=-151//this is the cooldown
                &&omnidens.level().random.nextInt(15)==7
                ;

    }



}
