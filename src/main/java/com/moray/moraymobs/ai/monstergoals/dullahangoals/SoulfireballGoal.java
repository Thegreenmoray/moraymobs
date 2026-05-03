package com.moray.moraymobs.ai.monstergoals.dullahangoals;

import com.moray.moraymobs.entity.living.monster.Dullahan;
import com.moray.moraymobs.entity.projectiles.Soulfireball;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class SoulfireballGoal extends Goal {
    Dullahan dullahan;
    int time;
    int go;



    public SoulfireballGoal(Dullahan dullahan,int time) {
        this.dullahan = dullahan;
        this.time=time;
    }

    @Override
    public void start() {
        go=time;
        if ((dullahan.getragesoulfireball() < -200 && dullahan.getcurrentrage() >= 2)) {
            dullahan.setragefireball(100);
        } else {
            dullahan.setsoulfireball(100);
        }

    }


    @Override
    public void stop() {
        if (go<=10&&dullahan.getcurrentrage()>=2&&dullahan.getragesoulfireball()>0){
            dullahan.setcurrentrage(dullahan.getcurrentrage()-2);
            dullahan.setragefireball(0);
            dullahan.setsoulfireball(0);
        }
        else{
            dullahan.setsoulfireball(0);
        }


        go=0;
    }


    @Override
    public void tick() {
        super.tick();

        LivingEntity livingEntity =dullahan.getTarget();
        go--;
        if (livingEntity != null){


            if (go==10){

                if (dullahan.getcurrentrage()>=2&&dullahan.getragesoulfireball()>0){
                    Vec3 vec3 = this.dullahan.getViewVector(1.0F);
                    double d2 = livingEntity.getX() - (this.dullahan.getX() + vec3.x * (double)4.0F);
                    double d3 = livingEntity.getY((double)0.5F) - ((double)0.5F + this.dullahan.getY((double)0.5F));
                    double d4 = livingEntity.getZ() - (this.dullahan.getZ() + vec3.z * (double)4.0F);

                    Vec3 vec31 = new Vec3(-d2, d3, -d4);
                    Soulfireball soulfireball =new Soulfireball(this.dullahan.level(), this.dullahan,vec31.normalize(),true);
                    this.dullahan.level().addFreshEntity(soulfireball);
                } else  {
                    Vec3 vec3 = this.dullahan.getViewVector(1.0F);
                    double d2 = livingEntity.getX() - (this.dullahan.getX() + vec3.x * (double)4.0F);
                    double d3 = livingEntity.getY((double)0.5F) - ((double)0.5F + this.dullahan.getY((double)0.5F));
                    double d4 = livingEntity.getZ() - (this.dullahan.getZ() + vec3.z * (double)4.0F);

                    Vec3 vec31 = new Vec3(-d2, d3, -d4);
                    Soulfireball soulfireball =new Soulfireball(this.dullahan.level(), this.dullahan,vec31.normalize(),false);
                    this.dullahan.level().addFreshEntity(soulfireball);
                }

            }
        }
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


        if (dullahan.getragesoulfireball()<-200&&dullahan.getcurrentrage()>=2 &&!dullahan.hasaxe()
                &&dullahan.canusespecial()){
            return true;
        }

        return dullahan.getsoulfireball()<-105
                &&!dullahan.hasaxe()
                &&dullahan.canusespecial()
                &&dullahan.level().random.nextInt(5)==1
                ;
    }
}
