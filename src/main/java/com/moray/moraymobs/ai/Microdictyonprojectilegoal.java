package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.dungeonentities.Microdictyon;
import com.moray.moraymobs.entity.projectiles.Microprojectile;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Microdictyonprojectilegoal extends Goal {
    Microdictyon microdictyon;
    int timer;
    int count;
    public Microdictyonprojectilegoal(Microdictyon microdictyon, int timer) {
        this.microdictyon = microdictyon;
        this.timer=timer;
    }


    @Override
    public void tick() {
count++;
        LivingEntity living = microdictyon.getTarget();
if (living!=null){
    this.microdictyon.lookAt(living, (float) -living.getY(), (float) living.getX());

    if(count==20){
        this.microdictyon.lookAt(living, (float) -living.getY(), (float) living.getX());
        Microprojectile microprojectile = new Microprojectile(this.microdictyon.level());
        Vec3 vec3=this.microdictyon.getViewVector(1);
        microprojectile.setPos(this.microdictyon.getX() + vec3.x * 2.0, this.microdictyon.getY(0.33333)+0.5, this.microdictyon.getZ() + vec3.z * 2.0);
        double d0 = living.getX() - this.microdictyon.getX();
        double d1 = living.getY(0.3333333333333333) - microprojectile.getY();
        double d2 = living.getZ() - this.microdictyon.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        Vec3 vec31=new Vec3(Mth.floor(d0), Mth.floor(d1 + d3 * 0.20000000298023224)-0.05, Mth.floor(d2));
        microprojectile.setDeltaMovement(vec31.normalize().scale(0.09));

        this.microdictyon.level().addFreshEntity(microprojectile);
    }
}
    }

    @Override
    public void start() {
        count=0;
    }

    @Override
    public void stop() {
       count=0;
        microdictyon.settimer(0);
    }


    @Override
    public boolean canContinueToUse() {
        return timer>count;
    }

    @Override
    public boolean canUse() {
       LivingEntity living = microdictyon.getTarget();
        if (living==null){
            return false;
        }


        return microdictyon.gettimer()>30;
    }
}
