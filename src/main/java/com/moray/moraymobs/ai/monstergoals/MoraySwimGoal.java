package com.moray.moraymobs.ai.monstergoals;

import com.moray.moraymobs.entity.living.monster.Moray;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;

public class MoraySwimGoal extends RandomSwimmingGoal {
    private final Moray moray;

    public MoraySwimGoal(Moray moray) {
        super(moray, 1.0, 40);
        this.moray = moray;
    }

    protected boolean canRandomSwim() {
        return true;
    }
    public boolean canUse() {
        return this.canRandomSwim() && super.canUse();
    }
}