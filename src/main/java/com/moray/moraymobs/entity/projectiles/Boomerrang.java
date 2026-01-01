package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public  class Boomerrang extends AbstractHurtingProjectile implements GeoEntity {

    private static final EntityDataAccessor<Integer> BACKWARDS= SynchedEntityData.defineId(Boomerrang.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(Boomerrang.class, EntityDataSerializers.INT);


    public int getbackwards(){
        return this.entityData.get(BACKWARDS);
    }
    public void setBackwards(int backwards){
        this.entityData.set(BACKWARDS,backwards);
    }

    public int gettimer(){
        return this.entityData.get(TIMER);
    }
    public void settimer(int timer){
        this.entityData.set(TIMER,timer);
    }

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);


    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBackwards(compound.getInt("backwards"));
        this.settimer(compound.getInt("timer"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("backwards",this.getbackwards());
        compound.putInt("timer",this.gettimer());
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BACKWARDS,0);
        builder.define(TIMER,0);
    }


    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();

        double d0 = (9) / 2.0;
        double d1 = (9) / 2.0;
        double d2 = entity.getX() - d0;
        double d3 = entity.getZ() - d1;
        double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);
        if (entity instanceof LivingEntity entity1&&getbackwards()<20&& !(entity1 instanceof Omnidens)){
            entity1.hurt(this.damageSources().magic(),5);



            ((LivingEntity) entity).knockback(1,-(d2 / d4 ),-(d3 / d4 ));

        }

        if (entity instanceof LivingEntity entity1&&getbackwards()>=20&& !(entity1 instanceof Omnidens)){
            entity1.hurt(this.damageSources().magic(),10);
            ((LivingEntity) entity).knockback(1,-(d2 / d4*2 ),-(d3 / d4*2 ));
        }
    }

    public Boomerrang(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }
    public Boomerrang(Level level) {
        super(Mobregistries.BOOMERANG.get(),level);
    }



    @Override
    public void tick() {
        super.tick();
setBackwards(getbackwards()+1);
settimer(gettimer()+1);
        Vec3 vec3=getDeltaMovement();
if (getbackwards()==20){
    this.setDeltaMovement(new Vec3(-Mth.floor(vec3.x),vec3.y-0.05,-Mth.floor(vec3.z)).normalize().scale(0.0009));
}

if (gettimer()>=50){
    remove(RemovalReason.DISCARDED);
}
    }



    protected float getInertia() {
        return this.isInWater() ? 2.50F : 1.0f;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Boomerrang> boomerrangAnimationState) {
        boomerrangAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.boomerang.spin", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
