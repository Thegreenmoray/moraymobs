package com.moray.moraymobs.entity.living.animalornpc;

import com.moray.moraymobs.ai.animalsornpcgoals.LampreyLatch;
import com.moray.moraymobs.ai.animalsornpcgoals.LampreyRunaway;
import com.moray.moraymobs.entity.abstractentity.Abstractfishmoray;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Lamprey extends Abstractfishmoray implements GeoEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    protected static final EntityDataAccessor<Integer> BLOOD= SynchedEntityData.defineId(Lamprey.class, EntityDataSerializers.INT);




    public Lamprey(EntityType<? extends WaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(PathType.WATER,1.0f);
        this.moveControl = new Lamprey.FishMoveControl(this);
        this.navigation=new WaterBoundPathNavigation(this,this.level());
        this.xpReward=5;
    }
    public int getbloodsackstorage(){
        return this.entityData.get(BLOOD);
    }

    public void setbloodsackstorage(int bloodsackstorage){

        this.entityData.set(BLOOD,bloodsackstorage);
    }

    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    builder.define(BLOOD, 0);

    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    pCompound.putInt("bloodsackstorage",this.getbloodsackstorage());
    }


    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
    setbloodsackstorage(pCompound.getInt("bloodsackstorage"));
    }


    @Override
    protected void customServerAiStep() {

      if (getbloodsackstorage()>0&getHealth()<getMaxHealth()) {
          this.heal(1);
          setbloodsackstorage(getbloodsackstorage()-1);
      }






        super.customServerAiStep();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractFish.createMobAttributes().add(Attributes.MAX_HEALTH,10).add(Attributes.MOVEMENT_SPEED, 1.75).add(Attributes.FOLLOW_RANGE,30);
    }

    protected void handleAirSupply(int pAirSupply) {
        if (this.isAlive() && (!this.isInWater())) {
            this.setAirSupply(pAirSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }



    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new Lamprey.FishSwimGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                this, Mob.class, true){
            @Override
            public boolean canUse() {
                return super.canUse()&&getbloodsackstorage()<=0&& !(target instanceof Lamprey);
            }
        });
        this.goalSelector.addGoal(2,new LampreyLatch(this));
        //this.goalSelector.addGoal(6,new LampreyRunaway(this));


    }

    @Override
    protected void pushEntities() {

    }

    static class FishSwimGoal extends RandomSwimmingGoal {
        private final Lamprey lamprey;

        public FishSwimGoal(Lamprey lamprey) {
            super(lamprey, 1.0, 40);
            this.lamprey = lamprey;
        }

        public boolean canUse() {
            return this.lamprey.canRandomSwim() && super.canUse();
        }
    }


    private static class FishMoveControl extends MoveControl {
        private final Abstractfishmoray fish;

        FishMoveControl(Abstractfishmoray pFish) {
            super(pFish);
            this.fish = pFish;
        }

        public void tick() {
            if (this.fish.isEyeInFluid(FluidTags.WATER)) {
                this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0, 0.005, 0.0));
            }

            if (this.operation == Operation.MOVE_TO && !this.fish.getNavigation().isDone()) {
                float $$0 = (float)(this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.fish.setSpeed(Mth.lerp(0.125F, this.fish.getSpeed(), $$0));
                double $$1 = this.wantedX - this.fish.getX();
                double $$2 = this.wantedY - this.fish.getY();
                double $$3 = this.wantedZ - this.fish.getZ();
                if ($$2 != 0.0) {
                    double $$4 = Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
                    this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0, (double)this.fish.getSpeed() * ($$2 / $$4) * 0.1, 0.0));
                }

                if ($$1 != 0.0 || $$3 != 0.0) {
                    float $$5 = (float)(Mth.atan2($$3, $$1) * 57.2957763671875) - 90.0F;
                    this.fish.setYRot(this.rotlerp(this.fish.getYRot(), $$5, 90.0F));
                    this.fish.yBodyRot = this.fish.getYRot();
                }

            } else {
                this.fish.setSpeed(0.0F);
            }
        }}


    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Lamprey> lampreyAnimationState) {

        if (getHealth()<=0.01){
            lampreyAnimationState.getController().setAnimation(RawAnimation.begin().then("death", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }




        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
