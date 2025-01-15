package com.moray.moraymobs.entity.living.monster;


import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Morayjaw extends Monster implements GeoEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> ANIMATION= SynchedEntityData.defineId(Morayjaw.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Optional<UUID>> PARENT_UUID = SynchedEntityData.defineId(Morayjaw.class, EntityDataSerializers.OPTIONAL_UUID);

    private static final EntityDataAccessor<Integer> TIMER = SynchedEntityData.defineId(Morayjaw.class, EntityDataSerializers.INT);

    public int gettimer(){
        return this.entityData.get(TIMER);
    }
    public void setTimer(int timer){
        this.entityData.set(TIMER,timer);
    }

    public int getanimation(){
        return this.entityData.get(ANIMATION);
    }
    public void setanimation(int anime){
       this.entityData.set(ANIMATION,anime);
    }

    @Nullable
    public UUID getParentId() {
        return this.entityData.get(PARENT_UUID).orElse(null);
    }

    public void setParentId(@Nullable UUID uniqueId) {
        this.entityData.set(PARENT_UUID, Optional.ofNullable(uniqueId));
    }
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

compound.putInt("animation",this.getanimation());
compound.putInt("timer",this.gettimer());
    }


    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("ParentUUID")) {
            this.setParentId(compound.getUUID("ParentUUID"));
        }
this.setanimation(compound.getInt("animation"));
this.setTimer(compound.getInt("timer"));
    }

    @Override
    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PARENT_UUID, Optional.empty());
    builder.define(ANIMATION,0);
    builder.define(TIMER,15);}

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 25.0D).add(Attributes.MOVEMENT_SPEED, 0.15F);
    }

    public Morayjaw(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Morayjaw( Level pLevel) {
        super(Mobregistries.MORAYJAW.get(), pLevel);

    }
    @Override
    public boolean isPushable() {
        return false;
    }
    public void pushEntities() {
        List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expandTowards(0.2D, 0.0D, 0.2D));
        Entity parent = this.getParent();
        if (parent != null) {
            entities.stream().filter(entity -> entity != parent && !(entity instanceof Morayjaw) && entity.isPushable()).forEach(entity -> entity.push(parent));
        }
    }

    @Override
    public void tick() {

        Entity leader = this.getParent();
        if (leader!=null) {
            setNoGravity(true);

        }

if (leader!=null&&gettimer()>0){
    setTimer(gettimer()-1);
}

if (gettimer()<=0){
    remove(RemovalReason.DISCARDED);
}


pushEntities();

        super.tick();
    }



    public boolean hurt(DamageSource source, float damage) {
        final Entity parent = getParent();

        return parent != null && parent.hurt(source, (float) (damage*.5));
    }



    public Entity getParent() {
        UUID id = getParentId();
        if (id != null && !this.level().isClientSide) {
            return ((ServerLevel) level()).getEntity(id);
        }
        return null;
    }

    public void setParent(Entity entity) {
        this.setParentId(entity.getUUID());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Morayjaw> morayjawAnimationState) {
        if (getanimation()==1){
            morayjawAnimationState.getController().setAnimation(RawAnimation.begin().then("jaw.bite", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
