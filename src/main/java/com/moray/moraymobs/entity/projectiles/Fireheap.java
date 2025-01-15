package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.entity.living.monster.Volcanoback;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Fireheap extends AbstractHurtingProjectile implements GeoEntity {
    public Fireheap(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Fireheap(Level level) {
   super(Mobregistries.FIREHEAP.get(),level);
    }
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);


    public boolean isPickable() {
        return false;
    }

    public void tick() {
        super.tick();

        Vec3 vec3=getDeltaMovement();

        this.setDeltaMovement(vec3.x,vec3.y-0.05,vec3.z);
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level().isClientSide) {
        Entity entity = pResult.getEntity();
    if(!(entity instanceof Volcanoback)){
      entity.hurt(this.damageSources().onFire(),6);
        entity.igniteForSeconds(7);
    remove(RemovalReason.DISCARDED);
    }}



    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        remove(RemovalReason.DISCARDED);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
