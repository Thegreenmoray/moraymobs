package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;

public class Geyser extends AbstractHurtingProjectile implements GeoEntity {
    public Geyser(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }
    public Geyser(Level level) {
        super(Mobregistries.GESYER.get(),level);
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }
}
