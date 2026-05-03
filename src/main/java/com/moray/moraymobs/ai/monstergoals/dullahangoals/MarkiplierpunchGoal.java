package com.moray.moraymobs.ai.monstergoals.dullahangoals;

import com.moray.moraymobs.entity.living.monster.Dullahan;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class MarkiplierpunchGoal extends Goal {
    Dullahan dullahan;
    int time;
    int go;

    float postionx=0;
    float postiony=0;
    float postionz=0;
    Vec3 jump_vector;

    boolean ismarked=false;
    public MarkiplierpunchGoal(Dullahan dullahan,int time) {
        this.dullahan = dullahan;
        this.time=time;
    }

    @Override
    public void start() {
        go=time;
        dullahan.setmarkiplierpuch(100);

        ismarked=false;
    }

    @Override
    public void tick() {

        LivingEntity livingEntity =dullahan.getTarget();
        go--;
        if (livingEntity != null){


            if (go>20){
                if (!ismarked){
                    jump_vector=getjumpvector();
                    ismarked=true;
                }



                dullahan.setDeltaMovement(jump_vector.x,jump_vector.y,jump_vector.z);

            }

            if (go==15){
                postionx= (int) livingEntity.position().x();
                postiony= (int) livingEntity.position().y();
                postionz= (int) livingEntity.position().z();
            }


            if (go<10){

                //have a static position dont just follow the player
                dullahan.moveTo(postionx,postiony,postionz);


                if (dullahan.position().distanceTo(new Vec3(postionx,postiony,postionz))<=1.5){
                    livingEntity.hurt(livingEntity.damageSources().mobAttack(dullahan), 11.0F);

                }





            }


        }





        super.tick();
    }

    @Override
    public void stop() {
        go=0;
        dullahan.setmarkiplierpuch(0);
        ismarked=true;
    }

    private Vec3 getjumpvector() {


        RandomSource random1 =dullahan.level().getRandom();
        int extra_y=random1.nextInt(5)+5;
        int extra_x=random1.nextInt(1)+1;
        int extra_z=random1.nextInt(1)+1;
        boolean neg_one= random1.nextBoolean();
        boolean neg_two= random1.nextBoolean();
        float ajustment_x= (float) (neg_one?-1:1);
        float ajustment_z= (float) (neg_two?-1:1);

        return new Vec3(ajustment_x*extra_x,extra_y,ajustment_z*extra_z).normalize().scale(1.5);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null||!livingEntity.isAlive()){
            return false;
        }


        return go>0;
    }




    @Override
    public boolean canUse() {
        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null){
            return false;
        }

        return dullahan.getmarkiplierpuch()<=-90
                &&!dullahan.hasaxe()
                &&dullahan.canusespecial()
                &&dullahan.level().random.nextInt(2)==1
                ;
    }
}
