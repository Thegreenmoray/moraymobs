package com.moray.moraymobs.ai.monstergoals;

import com.moray.moraymobs.entity.abstractentity.Abstract_tesseract;
import com.moray.moraymobs.entity.living.monster.Lesser_Tesseract;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Tesseractattackgoal extends Goal {

Abstract_tesseract tesseract;
Vec3 vec3=Vec3.ZERO;
    float radius = 1;
    float theta = 0;
   public Tesseractattackgoal(Abstract_tesseract tesseract){
          this.tesseract=  tesseract;
   }


    @Override
    public void tick() {
        LivingEntity livingEntity=this.tesseract.getTarget();
       if (livingEntity!=null){

           if (tesseract instanceof Lesser_Tesseract) {


               if (((Lesser_Tesseract) tesseract).getcharge()==88){
                   ((Lesser_Tesseract) tesseract).setanimation(2);
               }





              if (((Lesser_Tesseract) tesseract).getcharge()<70&&((Lesser_Tesseract) tesseract).getcharge()>40){
               vec3 = livingEntity.getPosition(1);

               float x_distance = (float) (livingEntity.getX() - tesseract.getX());
               float y_distance= (float) (livingEntity.getY() - tesseract.getY());
               float z_distance = (float) (livingEntity.getZ() - tesseract.getZ());


                radius = (float) Math.sqrt(x_distance * x_distance + z_distance * z_distance);
               theta = (float) Mth.atan2(x_distance, z_distance);


                  this.tesseract.setYRot((-(float) Mth.atan2(y_distance,vec3.length())) * Mth.RAD_TO_DEG);
                  this.tesseract.yBodyRot = this.tesseract.getYRot();
              }


              if (((Lesser_Tesseract) tesseract).getcharge()<=40&&((Lesser_Tesseract) tesseract).getcharge()>=20){
               float x = radius * Mth.sin(theta);
               float z = radius * Mth.cos(theta);
               Vec3 vec = tesseract.getDeltaMovement().normalize().add(x, livingEntity.getY() - tesseract.getY(), z).scale(0.09);

               tesseract.setDeltaMovement(vec);
               if (tesseract.distanceTo(livingEntity)<2){
               tesseract.doHurtTarget(livingEntity);}

                   tesseract.getNavigation().moveTo(vec.x() + Mth.cos(theta) * radius, livingEntity.getY(),
                           vec.z() + Mth.sin(theta) * radius, 2);}


              if (((Lesser_Tesseract) tesseract).getcharge()==18){
                 ((Lesser_Tesseract) tesseract).setanimation(3);
              }


           }

       }

    }

    @Override
    public void stop() {
        super.stop();

        if (((Lesser_Tesseract) tesseract).getanimation() == 2) {
            ((Lesser_Tesseract) tesseract).setanimation(0);
        } else {
            ((Lesser_Tesseract) tesseract).setanimation(3);
        }
    }

    @Override
    public void start() {

     if (tesseract instanceof Lesser_Tesseract){
         ((Lesser_Tesseract) tesseract).setcharge(100);
         ((Lesser_Tesseract) tesseract).setanimation(1);
     }


        super.start();
    }

    @Override
    public boolean canContinueToUse() {

        LivingEntity livingEntity=this.tesseract.getTarget();

        if (livingEntity==null){
            return false;
        }



        return tesseract instanceof Lesser_Tesseract&&((Lesser_Tesseract) tesseract).getcharge()>0;
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
