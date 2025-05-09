package com.moray.moraymobs.entity.living.monster;

import com.moray.moraymobs.ai.SoulBeamGoal;
import com.moray.moraymobs.ai.SoulCatcherFloatAroundGoal;
import com.moray.moraymobs.ai.SoulProjectileGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;

public class Soulcatcher extends FlyingMob implements GeoEntity {
    public Soulcatcher(EntityType<Soulcatcher> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl=new SoulcatcherMoveControl(this);
        this.xpReward=10;
    }

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> ANIMATION= SynchedEntityData.defineId(Soulcatcher.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(Soulcatcher.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BEAMTIMER= SynchedEntityData.defineId(Soulcatcher.class, EntityDataSerializers.INT);

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    public int getbeamtimer(){
        return this.entityData.get(BEAMTIMER);
    }
    public void setBeamtimer(int anime){
        this.entityData.set(BEAMTIMER,anime);
    }

    public int getanimation(){
        return this.entityData.get(ANIMATION);
    }
    public void setanimation(int anime){
        this.entityData.set(ANIMATION,anime);
    }

    public int gettimer(){
        return this.entityData.get(TIMER);
    }
    public void settimer(int timer){
        this.entityData.set(TIMER,timer);
    }


    public static boolean checkMonsterSpawnruleschance(EntityType<? extends FlyingMob> pType, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {


        return pLevel.getDifficulty() != Difficulty.PEACEFUL;}



@Override
   public void aiStep() {

if (getbeamtimer()==140||gettimer()==50){
    this.setanimation(100);}


        if (getanimation()>0){
    setanimation((getanimation()-1));
}

        if (getbeamtimer()<140){
    settimer(gettimer()+1);}


if (gettimer()<50){
    setBeamtimer(getbeamtimer()+1);}

   super.aiStep();
}


    public void readAdditionalSaveData(CompoundTag compound) {
        this.setanimation(compound.getByte("animation"));
        this.settimer(compound.getInt("timer"));
        this.setBeamtimer(compound.getInt("soulanimation"));
        super.readAdditionalSaveData(compound);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("animation",this.getanimation());
        compound.putInt("timer",this.gettimer());
        compound.putInt( "soulanimation",this.getbeamtimer());

        super.addAdditionalSaveData(compound);
    }

    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANIMATION, 0);
        builder.define(TIMER,0);
        builder.define(BEAMTIMER,0);

    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new FlyingPathNavigation(this, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH,15).add(Attributes.FOLLOW_RANGE, 25.0).add(Attributes.FLYING_SPEED,0.6);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_289460_) -> {
            return Math.abs(p_289460_.getY() - this.getY()) <= 4.0;}));
        this.targetSelector.addGoal(1,new SoulLookGoal(this));
        this.goalSelector.addGoal(5, new SoulCatcherFloatAroundGoal(this));
  this.goalSelector.addGoal(2,new SoulProjectileGoal(this,15));
   this.goalSelector.addGoal(3,new SoulBeamGoal(this,30));
    }

    static class SoulLookGoal extends Goal {
        private final Soulcatcher soulcatcher;

        public SoulLookGoal(Soulcatcher soulcatcher) {
            this.soulcatcher = soulcatcher;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        public boolean canUse() {
            return true;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.soulcatcher.getTarget() == null) {
                Vec3 $$0 = this.soulcatcher.getDeltaMovement();
                this.soulcatcher.setYRot(-((float)Mth.atan2($$0.x, $$0.z)) * Mth.RAD_TO_DEG);
                this.soulcatcher.yBodyRot = this.soulcatcher.getYRot();
            } else {
                LivingEntity $$1 = this.soulcatcher.getTarget();

                if ($$1.distanceToSqr(this.soulcatcher) < 4096.0) {
                    double $$3 = $$1.getX() - this.soulcatcher.getX();
                    double $$4 = $$1.getZ() - this.soulcatcher.getZ();
                    this.soulcatcher.setYRot(-((float)Mth.atan2($$3, $$4)) * Mth.RAD_TO_DEG);
                    this.soulcatcher.yBodyRot = this.soulcatcher.getYRot();
                }
            }

        }
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
this.setDeltaMovement(0,-1,0);

        if ( this.deathTime==1){
            setanimation(0);
        }
        if (this.deathTime == 60 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 30);
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Soulcatcher> soulcatcherAnimationState) {
        if (this.getHealth()<=0.01){

            soulcatcherAnimationState.getController().setAnimation(RawAnimation.begin().then("soulcatcher.death", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if(getanimation()>0&&getbeamtimer()>=140){
            soulcatcherAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.beam.soulcatcher", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if(getanimation()>0&&gettimer()>=50){
            soulcatcherAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.projectilespin.soulcatcher", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!soulcatcherAnimationState.isMoving()){
            soulcatcherAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.soulcatcher.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if (soulcatcherAnimationState.isMoving()){
            soulcatcherAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.soulcatcher.float", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }

//may want to modify this.
    private static class SoulcatcherMoveControl extends MoveControl {
        private final Soulcatcher soulcatcher;
        private int floatDuration;

        public SoulcatcherMoveControl(Soulcatcher soulcatcher) {
            super(soulcatcher);
            this.soulcatcher = soulcatcher;
        }

        public void tick() {




if(this.soulcatcher.getTarget()!=null &&this.soulcatcher.getbeamtimer()<140){
    if (this.floatDuration-- <= 0) {
        this.floatDuration += this.soulcatcher.getRandom().nextInt(1) + 2;
        Vec3 $$0 = new Vec3(this.wantedX - this.soulcatcher.getX(), 0, this.wantedZ - this.soulcatcher.getZ());
        double $$1 = $$0.length();
        $$0 = $$0.normalize();
        if (this.canReach($$0, Mth.ceil($$1))) {
            this.soulcatcher.setDeltaMovement(this.soulcatcher.getDeltaMovement().add($$0.scale(0.01)));
        } else {
            this.operation = Operation.WAIT;
        }}
}


            if (this.operation == Operation.MOVE_TO&&this.soulcatcher.getTarget()==null) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.soulcatcher.getRandom().nextInt(1) + 2;
                    Vec3 $$0 = new Vec3(this.wantedX - this.soulcatcher.getX(), this.wantedY - this.soulcatcher.getY(), this.wantedZ - this.soulcatcher.getZ());
                    double $$1 = $$0.length();
                    $$0 = $$0.normalize();
                    if (this.canReach($$0, Mth.ceil($$1))) {
                        this.soulcatcher.setDeltaMovement(this.soulcatcher.getDeltaMovement().add($$0.scale(0.01)));
                    } else {
                        this.operation = Operation.WAIT;
                    }
                }

            }
        }

        private boolean canReach(Vec3 pPos, int pLength) {
            AABB $$2 = this.soulcatcher.getBoundingBox();

            for(int $$3 = 1; $$3 < pLength; ++$$3) {
                $$2 = $$2.move(pPos);
                if (!this.soulcatcher.level().noCollision(this.soulcatcher, $$2)) {
                    return false;
                }
            }

            return true;
        }
    }


}
