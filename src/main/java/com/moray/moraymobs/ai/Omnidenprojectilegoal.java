package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.projectiles.Boomerrang;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Omnidenprojectilegoal extends Goal {
  private Omnidens omnidens;
  int timer;
  int count;
public Omnidenprojectilegoal(Omnidens omnidens,int timer) {
    this.omnidens=omnidens;
       this.timer=timer;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity=this.omnidens.getTarget();
        omnidens.stopInPlace();
        if (livingEntity!=null){
          this.omnidens.lookAt(livingEntity, (float) livingEntity.getY(), (float) livingEntity.getX());

          count++;


            if(count==10){
                Boomerrang boomerrang = new Boomerrang(this.omnidens.level());
                Vec3 vec3=this.omnidens.getViewVector(1);
                boomerrang.setPos(this.omnidens.getX() + vec3.x * 4.0, this.omnidens.getY(0.33333)+0.5, this.omnidens.getZ() + vec3.z * 4.0);
                double d0 = livingEntity.getX() - this.omnidens.getX();
               double d1 = livingEntity.getY(0.3333333333333333) - boomerrang.getY();
                double d2 = livingEntity.getZ() - this.omnidens.getZ();
                double d3 = Math.sqrt(d0 * d0 + d2 * d2);
                Vec3 vec31=new Vec3(Mth.floor(d0), Mth.floor(d1 + d3 * 0.20000000298023224)-2, Mth.floor(d2));
                boomerrang.setDeltaMovement(vec31.normalize().scale(0.0009));


              this.omnidens.level().addFreshEntity(boomerrang);
            }}


        }

    @Override
    public void start() {
        count=0;

    }

    @Override
    public void stop() {
        count=0;
        this.omnidens.setboomerangtime(0);
    }

    @Override
    public boolean canContinueToUse() {

        if (this.omnidens.getTarget()==null){
            return false;
        }


        return timer>count;
    }

    @Override
    public boolean canUse() {
        if(this.omnidens.getTarget()==null){
            this.omnidens.setboomerangtime(0);
            return false;
        }



        return this.omnidens.getboomerangtime()>100;

    }

    }



