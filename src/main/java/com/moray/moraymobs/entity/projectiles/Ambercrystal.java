package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.entity.living.monster.Amber_golem;
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
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class Ambercrystal extends AbstractHurtingProjectile implements GeoEntity {


    private static final EntityDataAccessor<Boolean> UP= SynchedEntityData.defineId(Ambercrystal.class, EntityDataSerializers.BOOLEAN);


    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

int time=0;


    public Ambercrystal(Level level) {
        super(Mobregistries.AMBERCRYSTAL.get(),level);
    }

    public Ambercrystal(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }
    public boolean getup(){
        return this.entityData.get(UP);
    }
    public void setup(boolean up){
        this.entityData.set(UP,up);
    }


    public void tick() {
        super.tick();

time++;


if (time==30) {
         setup(true);
        }

if (!this.level().isClientSide&&getup()) {
            List<Entity> damage = this.level().getEntities(this, this.getBoundingBox().inflate(1), e ->  e instanceof LivingEntity//&& (!(e instanceof Animal))||e instanceof Player
            );
            for (Entity entity : damage) {
            entity.hurt(this.damageSources().magic(),4);}

        }

        if (time>=60){
            remove(RemovalReason.DISCARDED);
        }
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setup(compound.getBoolean("up"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
compound.putBoolean("up",this.getup());
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
builder.define(UP,false);
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Ambercrystal> ambercrystalAnimationState) {

        ambercrystalAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.amberspike.rise", Animation.LoopType.HOLD_ON_LAST_FRAME));



        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
