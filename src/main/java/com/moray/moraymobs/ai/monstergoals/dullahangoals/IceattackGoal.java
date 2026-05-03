package com.moray.moraymobs.ai.monstergoals.dullahangoals;

import com.moray.moraymobs.entity.living.monster.Dullahan;
import com.moray.moraymobs.entity.projectiles.Icice_projectile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class IceattackGoal extends Goal {
    Dullahan dullahan;
    int time;
    int go;
    float x_diff=0;
    float y_diff=0;
    float z_diff=0;


    public IceattackGoal(Dullahan dullahan,int time) {
        this.dullahan = dullahan;
        this.time = time;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity =dullahan.getTarget();
        go--;
        if (livingEntity != null){
            if(go>15){
                x_diff= (float) (livingEntity.getX());
                y_diff = (float) (livingEntity.getY());
                z_diff= (float) (livingEntity.getZ());
            }


            if (go==15){

                if (dullahan.getcurrentrage()>=2&&dullahan.getrageiceattack()>0){
                    Icice_projectile ice =new Icice_projectile(this.dullahan,this.dullahan.level(),true);
                    y_diff= y_diff+10;
                    ice.setPos(x_diff,y_diff,z_diff);
                    this.dullahan.level().addFreshEntity(ice);
                } else if (dullahan.getcurrentrage()<2) {
                    Icice_projectile ice =new Icice_projectile(this.dullahan,this.dullahan.level(),false);
                    y_diff= y_diff+10;
                    ice.setPos(x_diff,y_diff,z_diff);
                    this.dullahan.level().addFreshEntity(ice);
                }

            }


        }


    }

    @Override
    public void start() {
        go=time;
        if ((dullahan.getrageiceattack() <= -200 && dullahan.getcurrentrage() >= 2)) {
            dullahan.setrageiceattack(100);
        } else {
            dullahan.seticeattack(100);
        }
    }


    @Override
    public void stop() {
        if (go<=10&&dullahan.getcurrentrage()>=2&&dullahan.getrageiceattack()>0){
            dullahan.setcurrentrage(dullahan.getcurrentrage()-2);
            dullahan.setrageiceattack(0);
            dullahan.seticeattack(0);
        } else {
            dullahan.seticeattack(0);
        }


        go=0;
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


        if(!dullahan.hasaxe()
        &&dullahan.getrageiceattack()<=-205
         &&dullahan.getcurrentrage()>=2
         &&dullahan.canusespecial()){
            return true;
        }


        return !dullahan.hasaxe()
           &&dullahan.geticeattack()<=-101
                &&dullahan.canusespecial()
                &&dullahan.level().random.nextInt(4)==0
                ;
    }
}
