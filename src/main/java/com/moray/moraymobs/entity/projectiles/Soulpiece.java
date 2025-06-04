package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.entity.living.monster.Soulcatcher;
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

public class Soulpiece extends AbstractHurtingProjectile implements GeoEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);


    public Soulpiece(Level pLevel) {
        super(Mobregistries.SOULPROJECTILE.get(), pLevel);
    }

    public boolean isPickable() {
        return false;
    }
    @Override
    public void tick() {
        super.tick();

        Vec3 vec3=new Vec3(this.getX(),this.getY()-0.05,this.getZ());

   this.setDeltaMovement(vec3.normalize());

    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level().isClientSide) {
            Entity entity = pResult.getEntity();
            if(!(entity instanceof Soulcatcher)){
                entity.hurt(this.damageSources().generic(),4);
               // remove(RemovalReason.DISCARDED);
            }}}

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
       // remove(RemovalReason.DISCARDED);
    }

    public Soulpiece(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
