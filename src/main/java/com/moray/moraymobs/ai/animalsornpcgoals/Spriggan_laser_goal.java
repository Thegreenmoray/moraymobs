package com.moray.moraymobs.ai.animalsornpcgoals;

import com.moray.moraymobs.entity.living.animalornpc.Spriggan;
import com.moray.moraymobs.entity.projectiles.Treebeam;
import com.moray.moraymobs.registries.Particlesregistries;
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
     int time;
int go;

    public Spriggan_laser_goal(Spriggan spriggan,int time) {
        this.spriggan=spriggan;
        this.time =time;
    }


    @Override
    public void start() {
        this.spriggan.setbeamtime(70);
       go= 0;
    }

    @Override
    public void stop() {
        this.spriggan.setbeamtime(0);
    }


// Would not have figured this out without BobMowzie https://github.com/BobMowzie/MowziesMobs-Public/tree/main

    @Override
    public void tick() {
        LivingEntity livingEntity=this.spriggan.getTarget();
        if (livingEntity!=null){

            //using spherical cooridantes
            go++;
            // 50 && 60
            if (go >10&& go <=20){
                float xdiff= (float) (livingEntity.getX()-spriggan.getX());
                float ydiff= (float) (livingEntity.getY()-spriggan.getY());
                float zdiff= (float) (livingEntity.getZ()-spriggan.getZ());


                rho=(float) Math.sqrt((xdiff*xdiff)+(ydiff*ydiff)+(zdiff*zdiff));
                theta=(float) Mth.atan2(zdiff,xdiff);
                phi=(float) Math.acos(ydiff/rho);
               spriggan.getLookControl().setLookAt(livingEntity);
            }

            if(go ==20){
                treebeam=new Treebeam(spriggan.level());

                treebeam.setPos(spriggan.getX()+(23*Mth.sin(phi)*Mth.cos(theta)),
                        spriggan.getY()+(23*Mth.cos(phi))+1.5,
                        spriggan.getZ()+(23*Mth.sin(phi)*Mth.sin(theta)));
                spriggan.level().addFreshEntity(treebeam);}

            if(!spriggan.level().isClientSide&& go >30){

                int new_rho= 20;
                for (int i=3;i<=new_rho+3;i++){
                    float distance=i*Mth.sin(phi);
                    Vec3 w=this.spriggan.position();

                    Vec3 more_distance=new Vec3(w.x()+(distance*Mth.cos(theta)),
                            w.y()+(i*Mth.cos(phi))+1.5,
                            w.z()+(distance*Mth.sin(theta)));
                    ((ServerLevel) this.spriggan.level()).sendParticles(Particlesregistries.BEAM_PARTICLES.get(),
                            more_distance.x(),more_distance.y(),more_distance.z(),
                            1,0,0,0,0);

                    if(go>40){
                        List<Entity> entities=this.spriggan.level().getEntities(this.spriggan,
                                new AABB(Math.floor(spriggan.getX()),Math.floor(spriggan.getY()),
                                        Math.floor(spriggan.getZ()),Math.floor(this.treebeam.getX()),
                                        Math.floor(this.treebeam.getY()),Math.floor(this.treebeam.getZ())));


                        for (Entity entity:entities){
                            entity.hurt(this.spriggan.damageSources().campfire(),2);
                        }


                    }
                }
            }


        }





        super.tick();
    }


    @Override
    public boolean canContinueToUse() {
        LivingEntity livingEntity=this.spriggan.getTarget();

        if (livingEntity==null){
            return false;
        }


        return time >go;
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity=this.spriggan.getTarget();

        if (livingEntity==null){
            return false;
        }
        return this.spriggan.getbeamtime()< -120&&!spriggan.issleeping()&&spriggan.isstreching()<=0
                &&spriggan.distanceTo(livingEntity)>5&&this.spriggan.level().random.nextInt(10)==5
                &&this.spriggan.getslashingtime()<0&&spriggan.getbombtime()<=0
                ;
    }
}
