package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.monster.Soulcatcher;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SoulCatcherFloatAroundGoal extends Goal {

        private final Soulcatcher soulcatcher;

        public SoulCatcherFloatAroundGoal(Soulcatcher soulcatcher) {
            this.soulcatcher = soulcatcher;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            MoveControl $$0 = this.soulcatcher.getMoveControl();
            if (!$$0.hasWanted()) {
                return true;
            } else {
                double $$1 = $$0.getWantedX() - this.soulcatcher.getX();
                double $$2 = $$0.getWantedY() - this.soulcatcher.getY();
                double $$3 = $$0.getWantedZ() - this.soulcatcher.getZ();
                double $$4 = $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
                return $$4 < 1.0 || $$4 > 3600.0;
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void start() {
            RandomSource $$0 = this.soulcatcher.getRandom();
            double $$1 = this.soulcatcher.getX() + (double)(($$0.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double $$2 = this.soulcatcher.getY() + (double)(($$0.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double $$3 = this.soulcatcher.getZ() + (double)(($$0.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.soulcatcher.getMoveControl().setWantedPosition($$1, $$2, $$3, 1.0);
        }


}
