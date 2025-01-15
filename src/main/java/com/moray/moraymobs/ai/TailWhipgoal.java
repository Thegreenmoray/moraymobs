package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.animal.Thresher_shark;
import com.moray.moraymobs.entity.projectiles.Stunwave;
import com.moray.moraymobs.registries.Effectregisteries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class TailWhipgoal extends Goal {
 Thresher_shark thresherShark;
int time;
int count;
    public TailWhipgoal(Thresher_shark thresherShark,int time) {
    this.thresherShark=thresherShark;
    this.time=time;
    }

    @Override
    public void tick() {

        LivingEntity entity=this.thresherShark.getTarget();

        if(entity!=null){
          count++;
this.thresherShark.lookAt((Entity) entity, (float) entity.getY(), (float) entity.getX());
          if (count==10){
              this.thresherShark.setanime(2);
          }
       if(count==13){
        Stunwave stunwave = new Stunwave(this.thresherShark.level());
        Vec3 vec3=this.thresherShark.getViewVector(1);
        stunwave.setPos(this.thresherShark.getX() + vec3.x * 2.0, this.thresherShark.getY(0.33333) + 0.5, this.thresherShark.getZ() + vec3.z * 2.0);
        double d0 = entity.getX() - this.thresherShark.getX();
        double d1 = entity.getY(0.3333333333333333) - stunwave.getY();
        double d2 = entity.getZ() - this.thresherShark.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        Vec3 vec31=new Vec3(d0, d1 + d3 * 0.20000000298023224, d2);
        stunwave.setDeltaMovement(vec31.normalize());


        this.thresherShark.level().addFreshEntity(stunwave); }}
        super.tick();
    }



    @Override
    public void start() {
        this.count=-1;
        this.thresherShark.setwhiptime(50);
    }


    @Override
    public void stop() {
        count=-1;

this.thresherShark.setanime(0);

    }

    @Override
    public boolean canUse() {
LivingEntity entity= this.thresherShark.getTarget();
        return entity != null&&!entity.hasEffect(Effectregisteries.STUN)
   &&this.thresherShark.getwhiptime()>=0;
    }


    @Override
    public boolean canContinueToUse() {
        return time>count;
    }
}
