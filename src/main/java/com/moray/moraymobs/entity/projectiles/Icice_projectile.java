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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class Icice_projectile extends AbstractHurtingProjectile implements GeoEntity {
    private static final EntityDataAccessor<Boolean> RAGED= SynchedEntityData.defineId(Icice_projectile.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    public Icice_projectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public Icice_projectile( LivingEntity owner, Level level,boolean israged) {
        super(Mobregistries.ICICE.get(), level);
        this.setOwner(owner);
        this.setrage(israged);
    }

    public boolean israge(){
        return this.entityData.get(RAGED);
    }
    public void setrage(boolean rage){
        this.entityData.set(RAGED,rage);
    }

    protected float getInertia() {
        return this.israge() ? 1.05F : 0.7F;
    }

    public void tick() {
        if (this.israge()) {
            this.noPhysics = true;
            super.tick();
            this.noPhysics = false;}else{
            super.tick();
        }

        Vec3 vec3=getDeltaMovement();

        if (this.israge()) {
            this.setDeltaMovement(vec3.x, vec3.y - 0.1, vec3.z);
        } else {
            this.setDeltaMovement(vec3.x, vec3.y - 0.05, vec3.z);
        }

        if (!this.level().isClientSide()) {

            List<Entity> entities_in_list=this.level().getEntities(this,this.getBoundingBox().inflate(1));
            if (!entities_in_list.isEmpty()){
                for (Entity entity : entities_in_list) {
                    if (entity instanceof LivingEntity) {
                        entity.hurt(this.damageSources().fallingBlock(this),israge()?12.0F:6.0F);
                    }
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
       if (!israge()){
        this.remove(RemovalReason.DISCARDED);}

    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setrage(compound.getBoolean("rage"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("rage",this.israge());
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(RAGED,false);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
