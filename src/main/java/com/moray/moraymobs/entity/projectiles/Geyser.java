package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.core.particles.ParticleTypes;
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

public class Geyser extends AbstractHurtingProjectile implements GeoEntity {

    private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(Geyser.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> START= SynchedEntityData.defineId(Geyser.class, EntityDataSerializers.INT);

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

    public int getstart(){
        return this.entityData.get(START);
    }
    public void setstart(int start){
        this.entityData.set(START,start);
    }


    public boolean isPickable() {
        return false;
    }



    @Override
    public void tick() {

        if (getstart()<30){
            setstart(getstart()+1);
        }


        if (getstart()>=30){
        settimer(gettimer()+1);

    List<Entity> entityList=level().getEntities(this,this.getBoundingBox());
     for (Entity entity:entityList){
         if (entity instanceof LivingEntity&&!(entity instanceof Omnidens omnidens)){
             entity.hurt(this.damageSources().magic(),5f);
         }
     }
        if (random.nextInt(15)==3){
     for(int i = 0; i < 10; ++i) {
            this.level().addParticle(ParticleTypes.DRIPPING_WATER, this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5), 0.0, 0.0, 0.0);
        }}

        if (gettimer()>=50){
            remove(RemovalReason.DISCARDED);
        }}
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Geyser> geyserAnimationState) {

       if (getstart()<30){
        geyserAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.whirlpool.rise", Animation.LoopType.HOLD_ON_LAST_FRAME));
           return PlayState.CONTINUE;
       }

       if (getstart()>=30)
       {
           geyserAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.whirlpool.spin", Animation.LoopType.LOOP));
           return PlayState.CONTINUE;
       }
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.settimer(compound.getInt("timer"));
        this.setstart(compound.getInt("start"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("timer",this.gettimer());
        compound.putInt("start",this.getstart());
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TIMER,0);
        builder.define(START,0);
    }

}
