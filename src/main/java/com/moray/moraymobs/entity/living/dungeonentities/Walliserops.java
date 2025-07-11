package com.moray.moraymobs.entity.living.dungeonentities;

import com.moray.moraymobs.ai.monstergoals.Walliseropsleapgoal;
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
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Walliserops extends Monster implements GeoEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(Walliserops.class, EntityDataSerializers.INT);

    public int gettimer(){
        return this.entityData.get(TIMER);
    }
    public void settimer(int timer){
        this.entityData.set(TIMER,timer);
    }


    public Walliserops(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl =new MoveControl(this);
        this.xpReward=5;
    }


    @Override
    public void tick() {
        super.tick();

        if (gettimer()<=30){
            settimer(gettimer()+1);
        }


    }


    protected void registerGoals() {
        this.goalSelector.addGoal(0,new RandomStrollGoal(this,0.5f));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class,30));
        this.goalSelector.addGoal(1,new MeleeAttackGoal(this,0.5,false));
   this.goalSelector.addGoal(3,new Walliseropsleapgoal(this,30));
    }



    public boolean isPushedByFluid() {
        return false;
    }

    public static AttributeSupplier.@NotNull Builder createMonsterAttributes() {
        return Monster.createMobAttributes().add(Attributes.ATTACK_DAMAGE,4).add(Attributes.MAX_HEALTH,18)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY,0.3f).add(Attributes.ARMOR,2f)
                .add(Attributes.ATTACK_KNOCKBACK,3).add(Attributes.FOLLOW_RANGE,40f);
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
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.EXPLOSION)||source.is(DamageTypes.DROWN)||super.isInvulnerable();
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }


    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }


    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,
                "Controller",this::animations));
    }


    @Override
    protected void tickDeath() {
        ++this.deathTime;


        if (this.deathTime == 40 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }


    private PlayState animations(AnimationState<Walliserops> walliseropsAnimationState) {

        if (getHealth()<=0.01){
            walliseropsAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.walliserops.death", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;

        }

        if (gettimer()>30){
            walliseropsAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.walliserops.stab", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;

        }


        if (walliseropsAnimationState.isMoving()){
            walliseropsAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.walliserops.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!walliseropsAnimationState.isMoving()){
            walliseropsAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.walliserops.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }



        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
