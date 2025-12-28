package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.animalornpc.Pronghorn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.neoforged.neoforge.event.EventHooks;

import java.util.EnumSet;
import java.util.function.Predicate;

public class Pronghorneatgoal extends Goal {
   Pronghorn pronghorn;
   Level level;
    private static final Predicate<BlockState> IS_TALL_GRASS =BlockStatePredicate.forBlock(Blocks.GRASS_BLOCK);

   public Pronghorneatgoal(Pronghorn pronghorn){
      this.pronghorn=pronghorn;
       this.level = pronghorn.level();
       this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
   }


    public boolean canUse() {
        if (this.pronghorn.getRandom().nextInt(this.pronghorn.isBaby() ? 50 : 500) != 0) {
            return false;
        } else {
            BlockPos blockpos = this.pronghorn.blockPosition();
            return IS_TALL_GRASS.test(this.level.getBlockState(blockpos)) ? true : this.level.getBlockState(blockpos.below()).is(Blocks.GRASS_BLOCK);
        }
    }

    public void start() {
     this.pronghorn.seteating(40);
        this.level.broadcastEntityEvent(this.pronghorn, (byte)10);
        this.pronghorn.getNavigation().stop();
    }

    public void stop() {
        this.pronghorn.seteating(0);
    }

    public boolean canContinueToUse() {
        return this.pronghorn.geteating() > 0&&this.pronghorn.getTarget()==null;
    }

    public int getEatAnimationTick() {
        return this.pronghorn.geteating();
    }

    public void tick() {
     pronghorn.seteating(Math.max(0, this.pronghorn.geteating() - 1));
        if (this.pronghorn.geteating()>0) {
            BlockPos blockpos = this.pronghorn.blockPosition();
            if (IS_TALL_GRASS.test(this.level.getBlockState(blockpos))) {
                if (EventHooks.canEntityGrief(this.level, this.pronghorn)) {
                    this.level.destroyBlock(blockpos, false);
                }

                this.pronghorn.ate();
            } else {
                BlockPos blockpos1 = blockpos.below();
                if (this.level.getBlockState(blockpos1).is(Blocks.GRASS_BLOCK)) {
                    if (EventHooks.canEntityGrief(this.level, this.pronghorn)) {
                        this.level.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                        this.level.setBlock(blockpos1, Blocks.DIRT.defaultBlockState(), 2);
                    }

                    this.pronghorn.ate();
                }
            }
        }

    }


}
