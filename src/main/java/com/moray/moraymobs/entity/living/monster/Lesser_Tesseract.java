package com.moray.moraymobs.entity.living.monster;

import com.moray.moraymobs.ai.monstergoals.Tesseractattackgoal;
import com.moray.moraymobs.entity.abstractentity.Abstract_tesseract;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Lesser_Tesseract extends Abstract_tesseract implements GeoEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);


    private static final EntityDataAccessor<Integer> ANIMATION= SynchedEntityData.defineId(Lesser_Tesseract.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHARGE= SynchedEntityData.defineId(Lesser_Tesseract.class, EntityDataSerializers.INT);




    public Lesser_Tesseract(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH,25).add(Attributes.FOLLOW_RANGE, 20.0).add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 12.0).add(Attributes.KNOCKBACK_RESISTANCE,0.7)
                .add(Attributes.ARMOR,5);
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new FlyingPathNavigation(this, pLevel);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
this.goalSelector.addGoal(5,new Tesseractattackgoal(this));
//this.goalSelector.addGoal(5,new MeleeAttackGoal(this,0.5,false));
    }

    public int getanimation(){
        return this.entityData.get(ANIMATION);
    }
    public void setanimation(int animation){
        this.entityData.set(ANIMATION,animation);
    }

    public int getcharge(){
        return this.entityData.get(CHARGE);
    }
    public void setcharge(int charge){
        this.entityData.set(CHARGE,charge);
    }





    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setanimation(compound.getInt("animation"));
        this.setcharge(compound.getInt("charge"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("animation",this.getanimation());
        compound.putInt("charge",this.getcharge());
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANIMATION,0);
        builder.define(CHARGE,0);
    }



    @Override
    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);


       setcharge(getcharge()-1);



    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;

        if (this.deathTime==2){
            this.playSound(SoundEvents.ENDER_DRAGON_DEATH,0.4f,1f);
        }

        if (this.deathTime==80){
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2, false, Level.ExplosionInteraction.MOB);
        }


        if (this.deathTime == 90 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 60);

            this.remove(RemovalReason.KILLED);
        }
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Lesser_Tesseract> lesserTesseractAnimationState) {

        if (getHealth()<=0.01){
            lesserTesseractAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.lesser_teseract.death", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }


       if (getanimation()==3){
           lesserTesseractAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.lesser_teserract.reconvert", Animation.LoopType.HOLD_ON_LAST_FRAME));
           return PlayState.CONTINUE;
        }

        if (getanimation()==1){
            lesserTesseractAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.lesser_teserract.blade_shift", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if (getanimation()==2){
            lesserTesseractAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.lesser_teserract.attack", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if (!lesserTesseractAnimationState.isMoving()){
            lesserTesseractAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.lesser_teserract.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }





        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
