package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.registries.Effectregisteries;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Stunwave extends AbstractHurtingProjectile implements GeoEntity {

    int timer=100;

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    public Stunwave(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Stunwave(Level pLevel) {
        super(Mobregistries.STUNWAVE.get(), pLevel);
    }

    @Override
    public void tick() {
timer--;
if (timer<=0){remove(RemovalReason.DISCARDED);}
        this.setNoGravity(true);
super.tick();
    }

@Override
    protected float getInertia() {
        return this.isInWater() ? 1.50F : 1.0f;
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level().isClientSide) {
            LivingEntity entity = (LivingEntity) pResult.getEntity();

            entity.addEffect(new MobEffectInstance(Effectregisteries.STUN,
                    100,0));

                remove(RemovalReason.DISCARDED);
            }


    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        remove(RemovalReason.DISCARDED);
    }


    public boolean isPickable() {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
