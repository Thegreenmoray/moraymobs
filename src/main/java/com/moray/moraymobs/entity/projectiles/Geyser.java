package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Geyser extends AbstractHurtingProjectile implements GeoEntity {

    private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(Geyser.class, EntityDataSerializers.INT);


    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    public Geyser(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }
    public Geyser(Level level) {
        super(Mobregistries.GESYER.get(),level);
    }

    public int gettimer(){
        return this.entityData.get(TIMER);
    }
    public void settimer(int timer){
        this.entityData.set(TIMER,timer);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
       Entity entity = result.getEntity();

       if (entity instanceof LivingEntity entity1){
           entity1.hurt(this.damageSources().generic(),10);
       }

    }

    @Override
    public void tick() {
     settimer(gettimer()+1);

        if (gettimer()>=50){
            remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.settimer(compound.getInt("timer"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("timer",this.gettimer());
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TIMER,0);
    }

}
