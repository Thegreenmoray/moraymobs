package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.projectiles.Geyser;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class Whirlpoolgoal extends Goal {

    private Omnidens omnidens;
    int timer;
    int count;

    public Whirlpoolgoal(Omnidens omnidens, int timer) {
        this.omnidens=omnidens;
        this.timer=timer;
    }


    @Override
    public void tick() {
        BlockPos blockpos= this.omnidens.blockPosition();

        BlockPos newblockPos = blockpos.offset(omnidens.getRandom().nextInt(10) + 5, 0, omnidens.getRandom().nextInt(10) + 5);

        count--;
        if (count%20==0) {
            while (newblockPos.getY()>omnidens.level().getMinBuildHeight()){

       newblockPos = blockpos.offset(omnidens.getRandom().nextInt(10) + 5, 0, omnidens.getRandom().nextInt(10) + 5);

         //create a start up to give the player a chance to escape

            if (!omnidens.level().isEmptyBlock(newblockPos)) {
                Geyser geyser = new Geyser(this.omnidens.level());
                geyser.setPos(Vec3.atLowerCornerOf(newblockPos));
                omnidens.level().addFreshEntity(geyser);
                break;
            }
       newblockPos =newblockPos.below();

            }

        }




    }




    @Override
    public boolean canContinueToUse() {
        return count>=0;
    }

    @Override
    public void start() {
        count= timer;
    }

    @Override
    public boolean canUse() {

       if (omnidens.getTarget()==null){
           return false;
       }


        return omnidens.getwhirlpool()>100;//omnidens.gethealth()>=omnidens.maxhealth()/2&&omnidens.gethealth()>=omnidens.maxhealth()/4
    }
}
