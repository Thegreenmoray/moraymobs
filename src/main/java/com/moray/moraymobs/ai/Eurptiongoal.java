package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.monster.Volcanoback;
import com.moray.moraymobs.entity.projectiles.Fireheap;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Eurptiongoal extends Goal {
    Volcanoback volcanoback;


    public Eurptiongoal(Volcanoback volcanoback){
     this.volcanoback=volcanoback;

    }

    @Override
    public void tick() {

       spawn_projectiles();

        super.tick();
    }

    private void spawn_projectiles(){

        float radians =this.volcanoback.level().random.nextInt(360)* Mth.DEG_TO_RAD;
float direction=(this.volcanoback.getRandom().nextBoolean()?1:-1)*this.volcanoback.getRandom().nextInt(5);
        Fireheap fireheap= new Fireheap(this.volcanoback.level());

        fireheap.setPos(this.volcanoback.getX(),this.volcanoback.getY()+5,this.volcanoback.getZ());
        Vec3 vec3 =new Vec3(direction+(2*Mth.sin(radians)),10,direction+(2*Mth.cos(radians)));
        fireheap.setDeltaMovement(vec3.normalize());
        fireheap.setOwner(this.volcanoback);
       volcanoback.level().addFreshEntity(fireheap);

    }



    @Override
    public boolean canUse() {
        return this.volcanoback.getRandom().nextInt(10)==5;
    }
}
