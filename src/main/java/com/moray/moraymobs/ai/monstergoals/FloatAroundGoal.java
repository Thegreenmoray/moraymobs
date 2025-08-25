package com.moray.moraymobs.ai.monstergoals;

import com.moray.moraymobs.entity.living.monster.Soulcatcher;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class FloatAroundGoal extends Goal {

        private final FlyingMob mob;

        public FloatAroundGoal(FlyingMob mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            MoveControl $$0 = this.mob.getMoveControl();
            if (!$$0.hasWanted()) {
                return true;
            } else {
                double $$1 = $$0.getWantedX() - this.mob.getX();
                double $$2 = $$0.getWantedY() - this.mob.getY();
                double $$3 = $$0.getWantedZ() - this.mob.getZ();
                double $$4 = $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
                return $$4 < 1.0 || $$4 > 3600.0;
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void start() {
            RandomSource $$0 = this.mob.getRandom();
            double $$1 = this.mob.getX() + (double)(($$0.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double $$2 = this.mob.getY() + (double)(($$0.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double $$3 = this.mob.getZ() + (double)(($$0.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.mob.getMoveControl().setWantedPosition($$1, $$2, $$3, 1.0);
        }


}
