package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.animalornpc.Spriggan;
import com.moray.moraymobs.entity.projectiles.Treebeam;
import com.moray.moraymobs.registries.Particlesregistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Spriggan_laser_goal extends Goal {
    Spriggan spriggan;
    Treebeam treebeam;
    float rho;
    float theta;
    float phi;
    Vec3 player;
    public Spriggan_laser_goal(Spriggan spriggan) {
        this.spriggan=spriggan;

    }


    @Override
    public void start() {

        this.spriggan.setbeamtime(200);
    }

    @Override
    public void stop() {

        this.spriggan.setbeamtime(0);
    }



    @Override
    public void tick() {
        LivingEntity livingEntity=this.spriggan.getTarget();
        if (livingEntity!=null){

        //using spherical cooridantes

            if (spriggan.getbeamtime()>170&&spriggan.getbeamtime()<=180){
            float xdiff= (float) (livingEntity.getX()-spriggan.getX());
            float ydiff= (float) (livingEntity.getY()-spriggan.getY());
            float zdiff= (float) (livingEntity.getZ()-spriggan.getZ());


             rho=(float) Math.sqrt((xdiff*xdiff)+(ydiff*ydiff)+(zdiff*zdiff));
             theta=(float) Mth.atan2(zdiff,xdiff);
             phi=(float) Math.acos(ydiff/rho);
            spriggan.setYRot(theta);
            spriggan.setXRot(phi);
         }

         if(spriggan.getbeamtime()==170){
            treebeam=new Treebeam(spriggan.level(), (float) (-this.spriggan.getXRot() * Math.PI / 180),(float)((this.spriggan.yHeadRot+90) * Math.PI / 180),this.spriggan,(float) livingEntity.getX(),(float)livingEntity.getY(),(float)livingEntity.getZ());

            spriggan.level().addFreshEntity(treebeam);}
            }
        super.tick();
    }


    @Override
    public boolean canContinueToUse() {
        LivingEntity livingEntity=this.spriggan.getTarget();

        if (livingEntity==null){
            return false;
        }


        return this.spriggan.getbeamtime()>0;
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity=this.spriggan.getTarget();

        if (livingEntity==null){
            return false;
        }
        return this.spriggan.getbeamtime()< -120&&!spriggan.issleeping()&&spriggan.isstreching()<=0
                &&spriggan.distanceTo(livingEntity)>5&&this.spriggan.level().random.nextInt(10)==5;
    }
}
