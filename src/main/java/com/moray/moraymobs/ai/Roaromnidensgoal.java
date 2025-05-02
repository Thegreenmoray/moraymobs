package com.moray.moraymobs.ai;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class Roaromnidensgoal extends Goal {

    private Omnidens omnidens;
    int timer;
    int count;
    public Roaromnidensgoal(Omnidens omnidens, int timer) {
        this.omnidens=omnidens;
        this.timer=timer;
    }


    @Override
    public void tick() {
        List<Entity> entities=  this.omnidens.level().getEntities(this.omnidens,this.omnidens.getBoundingBox().inflate(5));
        for (Entity entity:entities){
            if(entity instanceof LivingEntity livingEntity){
    livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,150,1));
            }}
    }

    @Override
    public boolean canUse() {
        return omnidens.getroar()>150;
    }
}
