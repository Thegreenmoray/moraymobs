package com.moray.moraymobs.entity.living.monster;

import com.moray.moraymobs.ai.monstergoals.Eelmeeleeattackgoal;
import com.moray.moraymobs.ai.Grabjawgoal;
import com.moray.moraymobs.ai.monstergoals.MoraySwimGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;


public class Moray extends Monster implements GeoEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Integer> ANIMATION= SynchedEntityData.defineId(Moray.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(Moray.class, EntityDataSerializers.INT);

    public Moray(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new MorayMoveControl(this);
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


    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
   this.setanimation(compound.getInt("animation"));
    this.settimer(compound.getInt("timer"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("animation",this.getanimation());
        compound.putInt("timer",this.gettimer());
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANIMATION,0);
        builder.define(TIMER,0);
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH,25).add(Attributes.FOLLOW_RANGE, 20.0).add(Attributes.MOVEMENT_SPEED, 0.55).add(Attributes.ATTACK_DAMAGE, 5.0);
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    protected void registerGoals() {
       this.goalSelector.addGoal(1, new MoraySwimGoal(this));
           this.goalSelector.addGoal(5, new Eelmeeleeattackgoal(this, 1.0, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
       this.targetSelector.addGoal(4,new NearestAttackableTargetGoal<>(this, TropicalFish.class,true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
   this.goalSelector.addGoal(3,new Grabjawgoal(this,14));
    }

    public boolean canBeAffected(MobEffectInstance pPotioneffect) {
        return pPotioneffect.getEffect() != MobEffects.POISON;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        return pSource.is(DamageTypes.DROWN) || pSource.is(DamageTypes.IN_WALL)||super.isInvulnerableTo(pSource);
    }

    public static boolean checkDrownedSpawnRules(EntityType<Moray> drowned, ServerLevelAccessor serverLevel, MobSpawnType mobSpawnType, BlockPos pos, RandomSource random) {
        int i = serverLevel.getSeaLevel();
        int j = i - 13;
        return pos.getY() >= j && pos.getY() <= i && serverLevel.getFluidState(pos.below()).is(FluidTags.WATER) && serverLevel.getBlockState(pos.above()).is(Blocks.WATER);

    }

    private static boolean isDeepEnoughToSpawn(LevelAccessor level, BlockPos pos) {
        return pos.getY() < level.getSeaLevel() - 5;
    }


    @Override
    public void aiStep() {
       if (gettimer()<50){
       settimer(gettimer()+1);}

        super.aiStep();
    }

  //  public MobType getMobType() {
   //     return MobType.UNDEAD;
   // }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Moray> morayAnimationState) {
        if (this.getHealth()<=0.01){
            morayAnimationState.getController().setAnimation(RawAnimation.begin().then("moray.death", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }


        if (getanimation()==1){
            morayAnimationState.getController().setAnimation(RawAnimation.begin().then("bite.moray", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (getanimation()==2){
            morayAnimationState.getController().setAnimation(RawAnimation.begin().then("strech.moray", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (!morayAnimationState.isMoving()){
            morayAnimationState.getController().setAnimation(RawAnimation.begin().then("moray.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (morayAnimationState.isMoving()){
            morayAnimationState.getController().setAnimation(RawAnimation.begin().then("eel.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        return PlayState.STOP;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }

    @SuppressWarnings("")
    private static class MorayMoveControl extends MoveControl {
        private final Moray moray;

        MorayMoveControl(Moray moray) {
            super(moray);
            this.moray = moray;
        }

        public void tick() {

            if (this.moray.isEyeInFluid(FluidTags.WATER)) {
                this.moray.setDeltaMovement(this.moray.getDeltaMovement().add(0.0, 0.005, 0.0));
            }

            if (this.operation == Operation.MOVE_TO && !this.moray.getNavigation().isDone()) {
                float $$0 = (float)(this.speedModifier * this.moray.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.moray.setSpeed(Mth.lerp(0.125F, this.moray.getSpeed(), $$0));
                double $$1 = this.wantedX - this.moray.getX();
                double $$2 = this.wantedY - this.moray.getY();
                double $$3 = this.wantedZ - this.moray.getZ();
                if ($$2 != 0.0) {
                    double $$4 = Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
                    this.moray.setDeltaMovement(this.moray.getDeltaMovement().add(0.0, (double)this.moray.getSpeed() * ($$2 / $$4) * 0.1, 0.0));
                }

                if ($$1 != 0.0 || $$3 != 0.0) {
                    float $$5 = (float)(Mth.atan2($$3, $$1) * 57.2957763671875) - 90.0F;
                    this.moray.setYRot(this.rotlerp(this.moray.getYRot(), $$5, 90.0F));
                    this.moray.yBodyRot = this.moray.getYRot();
                }

            } else {
                this.moray.setSpeed(0.0F);
            }
        }
    }
    @Override
    protected void tickDeath() {
        ++this.deathTime;


        if ( this.deathTime==1){
            setanimation(0);
        }
        if (this.deathTime == 60 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }





    }







