package com.moray.moraymobs.ai.monstergoals.dullahangoals;


import com.moray.moraymobs.entity.living.monster.Dullahan;
import net.minecraft.world.entity.ai.goal.Goal;

public class getaxebackgoal extends Goal {
    Dullahan dullahan;
    int time;
    int go;
    public getaxebackgoal(Dullahan dullahan,int time) {
        this.dullahan = dullahan;
        this.time = time;
    }

    @Override
    public boolean canContinueToUse() {
        return !dullahan.hasaxe()&&go>0&&!dullahan.canusespecial();
    }
    @Override
    public void start() {
        go=time;
    }

    @Override
    public void stop() {

        go=0;

    }

    @Override
    public void tick() {
        super.tick();
        go--;
    }

    @Override
    public boolean canUse() {

        return !dullahan.hasaxe()&&dullahan.getaxewait()<=-150
                &&!dullahan.canusespecial()
                ;
    }
}