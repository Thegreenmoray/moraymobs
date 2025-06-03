package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.projectiles.Boomerrang;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Omnidenprojectilegoal extends Goal {
  private Omnidens omnidens;
public Omnidenprojectilegoal(Omnidens omnidens) {
    this.omnidens=omnidens;

    }

    @Override
    public void tick() {
        LivingEntity livingEntity=this.omnidens.getTarget();
        omnidens.stopInPlace();
        if (livingEntity!=null){
          this.omnidens.lookAt(livingEntity, (float) -livingEntity.getY(), (float) livingEntity.getX());

            if(omnidens.getboomerangtime()==20){

                for (int i = 0; i < 3; i++) {
                   float angle=Mth.PI/6;
                    Boomerrang boomerrang = new Boomerrang(this.omnidens.level());
                    Vec3 vec3=this.omnidens.getViewVector(4);
                    boomerrang.setPos(this.omnidens.getX() + vec3.x * 5.0, this.omnidens.getY(0.33333), this.omnidens.getZ() + vec3.z * 5.0);

                    double d0 = livingEntity.getX() - this.omnidens.getX();
                    double d1 = livingEntity.getY(0.3333333333333333) - boomerrang.getY();
                    double d2 = livingEntity.getZ() - this.omnidens.getZ();
                    double d3 = Math.sqrt(d0 * d0 + d2 * d2);

                 if (i<2){
                   angle=angle*i;}


                   if (i==2){
                       angle=angle*-1;
                   }


                    Vec3 vec31=new Vec3(Mth.floor(d0)*Mth.cos(angle), Mth.floor(d1 + d3 * 0.20000000298023224), Mth.floor(d2)*Mth.sin(angle));
                    boomerrang.setDeltaMovement(vec31.normalize().scale(0.0009));


                    this.omnidens.level().addFreshEntity(boomerrang);
                }

            }}


        }

    @Override
    public void start() {
        omnidens.setboomerangtime(40);
omnidens.setanimation(7);
    }

    @Override
    public void stop() {
omnidens.setanimation(0);
    }

    @Override
    public boolean canContinueToUse() {

        if (this.omnidens.getTarget()==null){
            return false;
        }


        return omnidens.getboomerangtime()>0;
    }

    @Override
    public boolean canUse() {
        if(this.omnidens.getTarget()==null){

            return false;
        }



        return omnidens.getboomerangtime()<=-100
                &&omnidens.canuseskill()
                &&omnidens.level().random.nextInt(8)==3;

    }

    }



