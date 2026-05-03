package com.moray.moraymobs.ai.monstergoals.dullahangoals;

import com.moray.moraymobs.entity.living.monster.Dullahan;
import com.moray.moraymobs.entity.projectiles.DullanhanAxe;
import com.moray.moraymobs.registries.Itemregististeries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Axethrowgoal extends Goal {
    Dullahan dullahan;
    int time;
    int go;
    float x_diff=0;
    float y_diff=0;
    float z_diff=0;

    public Axethrowgoal(Dullahan dullahan,int time) {
        this.dullahan = dullahan;
        this.time=time;
    }

    @Override
    public void stop() {
        if (go <=15){
            this.dullahan.sethasaxe(false);
        }
        go=0;
        x_diff=0;
        y_diff=0;
        z_diff=0;
        this.dullahan.setaxethrow(0);

    }

    @Override
    public void start() {
        go=time;
        this.dullahan.setaxethrow(50);
    }

    @Override
    public void tick() {
        super.tick();
        go--;
        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity != null){
            if(go>15){
                x_diff= (float) (livingEntity.getX()-dullahan.getX());
                y_diff = (float) (livingEntity.getY(0.3333333333333333));
                z_diff= (float) (livingEntity.getZ()-dullahan.getZ());

            }

            if (go==10){                                                                                              //replace with the registry call
                DullanhanAxe dullanhanAxe=new DullanhanAxe(this.dullahan,this.dullahan.level(), Itemregististeries.DULLAHAN_AXE.toStack(),null);
                Vec3 vec3=dullahan.getViewVector(1);
                dullanhanAxe.setPos(this.dullahan.getX() + vec3.x * 3.0, this.dullahan.getY(0.5) + 0.5, this.dullahan.getZ() + vec3.z * 3.0);
                y_diff= (float) (y_diff-dullanhanAxe.getY());
                double d3 = Math.sqrt(x_diff * x_diff + z_diff * z_diff);
                dullanhanAxe.shoot(x_diff,y_diff+d3*0.4,z_diff,1.6F, (float)(14 - dullahan.level().getDifficulty().getId() * 4));
                this.dullahan.level().addFreshEntity(dullanhanAxe);

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

        return dullahan.getaxethrow()<=-40 //-100?
                &&dullahan.level().random.nextInt(3)==1
                &&dullahan.hasaxe()&&dullahan.canusespecial();

    }
}

