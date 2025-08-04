package com.moray.moraymobs.ai.monstergoals;

import com.moray.moraymobs.entity.abstractentity.Abstract_tesseract;
import com.moray.moraymobs.entity.living.monster.Lesser_Tesseract;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Tesseractattackgoal extends Goal {

Abstract_tesseract tesseract;

   public Tesseractattackgoal(Abstract_tesseract tesseract){
          this.tesseract=  tesseract;
   }


    @Override
    public void tick() {
        LivingEntity livingEntity=this.tesseract.getTarget();
       if (livingEntity!=null){

           if (tesseract instanceof Lesser_Tesseract) {
               Vec3 vec3 = livingEntity.getPosition(1);

               float x_distance = (float) (livingEntity.getX() - tesseract.getX());
               float z_distance = (float) (livingEntity.getZ() - tesseract.getX());


               float radius = (float) Math.sqrt(x_distance * x_distance + z_distance * z_distance);
               float theta = (float) Mth.atan2(x_distance, z_distance);

               float x = radius * Mth.sin(theta);
               float z = radius * Mth.cos(theta);


               Vec3 vec = tesseract.getDeltaMovement().normalize().add(x, 0, z).scale(0.001);

               tesseract.setDeltaMovement(vec);


                   tesseract.getNavigation().moveTo(vec3.x() + Mth.cos(theta) * radius, vec3.y(),
                           vec3.z() + Mth.sin(theta) * radius, 4);


               this.tesseract.setYRot(-((float) Mth.atan2(tesseract.getX(), tesseract.getZ())) * Mth.RAD_TO_DEG);
               this.tesseract.yBodyRot = this.tesseract.getYRot();
           }


       }




    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    @Override
    public boolean canUse() {

        LivingEntity livingEntity=this.tesseract.getTarget();

        if (livingEntity==null){
            return false;
        }



        return tesseract instanceof Lesser_Tesseract&&((Lesser_Tesseract) tesseract).getcharge()<-120&&tesseract.level().random.nextInt(9)==4;
    }
}
