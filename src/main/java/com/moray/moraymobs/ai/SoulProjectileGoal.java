package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.monster.Soulcatcher;
import com.moray.moraymobs.entity.projectiles.Soulpiece;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class SoulProjectileGoal extends Goal {
    Soulcatcher soulcatcher;
int timer;
int count;
    public SoulProjectileGoal(Soulcatcher soulcatcher,int timer){
        this.soulcatcher=soulcatcher;
        this.timer=timer;
    }

    @Override
    public void start() {
        count=0;
    }

    @Override
    public void stop() {
        count=0;
        this.soulcatcher.settimer(0);
    }

    @Override
    public boolean canContinueToUse() {
        return timer > count;
    }


    @Override
    public void tick() {

       LivingEntity entity=this.soulcatcher.getTarget();
++count;
       if(entity!=null) {


if (count==14){
            Soulpiece soulpiece = new Soulpiece(this.soulcatcher.level());
    Vec3 vec3=soulcatcher.getViewVector(1);
            soulpiece.setPos(this.soulcatcher.getX() + vec3.x * 4.0, this.soulcatcher.getY(0.5) + 0.5, this.soulcatcher.getZ() + vec3.z * 4.0);
            double d0 = entity.getX() - this.soulcatcher.getX();
            double d1 = entity.getY(0.3333333333333333) - soulpiece.getY();
            double d2 = entity.getZ() - this.soulcatcher.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            Vec3 vec31=new Vec3(d0, d1 + d3 * 0.20000000298023224, d2);
            soulpiece.setDeltaMovement(vec31.normalize());
            this.soulcatcher.level().addFreshEntity(soulpiece);}
        }


    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity=this.soulcatcher.getTarget();
       if (livingEntity==null){
           return false;
       }

        return this.soulcatcher.gettimer()>50&&this.soulcatcher.getbeamtimer()<140;
    }
}
