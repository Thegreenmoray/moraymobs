package com.moray.moraymobs.ai.monstergoals.dullahangoals;

import com.moray.moraymobs.entity.living.monster.Dullahan;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class AxespinGoal extends Goal {
    Dullahan dullahan;
    int start;
    int go;
    public AxespinGoal(Dullahan dullahan,int start) {
        this.dullahan = dullahan;
        this.start = start;
    }


    @Override
    public void start() {
        go=start;
        this.dullahan.setaxespin(48);
    }
    @Override
    public void tick() {
        LivingEntity livingEntity =dullahan.getTarget();
        go--;
        if (livingEntity != null){


            if (15>go) {
                List<Entity> range = dullahan.level().getEntities(this.dullahan, dullahan.getBoundingBox().inflate(2));
                for(Entity e:range){
                    if (e instanceof LivingEntity){
                        dullahan.doHurtTarget(e);
                        double d0 = (this.dullahan.getBoundingBox().minX + this.dullahan.getBoundingBox().maxX) / 2.0;
                        double d1 = (this.dullahan.getBoundingBox().minZ + this.dullahan.getBoundingBox().maxZ) / 2.0;
                        double d2 = e.getX() - d0;
                        double d3 = e.getZ() - d1;
                        double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

                        ((LivingEntity) e).knockback(4,-(d2 / d4 * 4.0),-(d3 / d4 * 4.0));

                    }

                }


            }
        }

    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null){
            return false;
        }

        return (dullahan.getaxespin()<-50&&dullahan.getcurrentrage()>=3)
                &&dullahan.hasaxe()&& dullahan.distanceTo(livingEntity)<5
                &&dullahan.canusespecial()
                &&dullahan.level().random.nextInt(2)==1
                ;
    }


    public void stop() {
        if (go<=10){
            dullahan.setcurrentrage(dullahan.getcurrentrage()-3);
        }
        this.dullahan.setaxespin(0);
        go=0;
    }


    public boolean canContinueToUse() {
        LivingEntity livingEntity =dullahan.getTarget();
        if (livingEntity == null){
            return false;
        }
        return go > 0 && livingEntity.isAlive();
    }



}
