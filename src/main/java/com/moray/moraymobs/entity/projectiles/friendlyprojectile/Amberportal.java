package com.moray.moraymobs.entity.projectiles.friendlyprojectile;

import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Amberportal extends Entity implements GeoEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    int time=0;

    public Amberportal(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public Amberportal( Level level) {
        super(Mobregistries.AMBERPORTAL.get(), level);
    }

    @Override
    public void tick() {
        super.tick();
        time++;

        if (time>=60){
            this.remove(RemovalReason.DISCARDED);
        }

    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Amberportal> amberportalAnimationState) {

        amberportalAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.amberportal.spin", Animation.LoopType.LOOP));



        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
