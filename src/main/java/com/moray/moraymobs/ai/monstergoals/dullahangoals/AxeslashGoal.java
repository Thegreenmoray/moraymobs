package com.moray.moraymobs.ai.monstergoals.dullahangoals;

import com.moray.moraymobs.entity.living.monster.Dullahan;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.IronGolem;

import java.util.List;

public class AxeslashGoal extends Goal {

    Dullahan dullahan;
    int start;
    int go;
    public AxeslashGoal(Dullahan dullahan,int start) {
        this.dullahan = dullahan;
        this.start = start;
    }

    @Override
    public void start() {
        go=start;
        dullahan.setaxeslash(50);
    }


    public void tick(){
        LivingEntity livingEntity =dullahan.getTarget();
        go--;
        if (livingEntity != null){
            if (go>10){
                dullahan.lookAt((Entity) livingEntity, (float) livingEntity.getY(), (float) livingEntity.getX());
            }
            if (go==10){
                List<Entity> range=dullahan.level().getEntities(this.dullahan,dullahan.getBoundingBox().inflate(2), e->dullahan.position().normalize().dot(livingEntity.position().normalize())>=0.0&& e instanceof LivingEntity);
                for(Entity e:range){
                    if (e instanceof LivingEntity living){
                        living.hurt(living.damageSources().mobAttack(dullahan), living instanceof IronGolem ?25.0f:11.0F);
                        double d0 = (this.dullahan.getBoundingBox().minX + this.dullahan.getBoundingBox().maxX) / 2.0;
                        double d1 = (this.dullahan.getBoundingBox().minZ + this.dullahan.getBoundingBox().maxZ) / 2.0;
                        double d2 = e.getX() - d0;
                        double d3 = e.getZ() - d1;
                        double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

                        living.knockback(3,-(d2 / d4 * 4.0),-(d3 / d4 * 4.0));

                    }
                }
            }
        }
    }


    public void stop() {
        super.stop();
        go=0;
        dullahan.setaxeslash(0);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null){
            return false;
        }


        return livingEntity.isAlive()&&go>0;
    }

    @Override
    public boolean canUse() {

        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null){
            return false;
        }


        return dullahan.getaxeslash()<=-25
                &&dullahan.hasaxe()
                &&dullahan.distanceTo(livingEntity)<5
                &&dullahan.canusespecial()
                &&dullahan.level().random.nextInt(2)==0
                ;
    }
}
