package com.moray.moraymobs.entity.living.dungeonentities;

import com.moray.moraymobs.ai.monstergoals.Microdictyonprojectilegoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Microdictyon extends Monster implements GeoEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(Microdictyon.class, EntityDataSerializers.INT);

    public int gettimer(){
        return this.entityData.get(TIMER);
    }
    public void settimer(int timer){
        this.entityData.set(TIMER,timer);
    }

    public Microdictyon(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl =new MoveControl(this);
        this.xpReward=5;
    }


    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }


    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.TRIDENT)||source.is(DamageTypes.DROWN)||super.isInvulnerable();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH,15).add(Attributes.FOLLOW_RANGE, 20.0).add(Attributes.WATER_MOVEMENT_EFFICIENCY, 1.5);
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new RandomStrollGoal(this,0.5f));
        this.targetSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class,30));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3,new Microdictyonprojectilegoal(this,25));
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





    @Override
    public void tick() {
super.tick();
      if (gettimer()<=30){
          settimer(gettimer()+1);
      }


    }

    public boolean isPushedByFluid() {
        return false;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,
                "Controller",this::animations));
    }



    @Override
    protected void tickDeath() {
        ++this.deathTime;


        if (this.deathTime == 35 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }




    private PlayState animations(AnimationState<Microdictyon> microdictyonAnimationState) {

        if (getHealth()<=0.01){
            microdictyonAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.microdictyon.death", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }



       if (gettimer()>30){
           microdictyonAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.mircodictyon.shoot", Animation.LoopType.LOOP));
           return PlayState.CONTINUE;
       }


        if (microdictyonAnimationState.isMoving()){
            microdictyonAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.microdictyon.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!microdictyonAnimationState.isMoving()){
            microdictyonAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.microdictyon.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }



    return PlayState.STOP;
    }


    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
