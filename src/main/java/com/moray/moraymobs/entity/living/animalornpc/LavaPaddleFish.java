package com.moray.moraymobs.entity.living.animalornpc;

import com.moray.moraymobs.ai.Navigaton;
import com.moray.moraymobs.entity.abstractentity.Abstractfishmoray;
import com.moray.moraymobs.registries.Itemregististeries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LavaPaddleFish extends Abstractfishmoray implements GeoEntity {
  //  private static final EntityDataAccessor<Integer>  DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(LavaPaddleFish.class, EntityDataSerializers.INT);
//variants will be added eventally
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    public LavaPaddleFish(EntityType<? extends Abstractfishmoray> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
      this.setPathfindingMalus(PathType.LAVA,0.0f);
       this.moveControl = new FishMoveControl(this);
        this.navigation=new Navigaton(this,this.level());
        this.xpReward=5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractFish.createMobAttributes().add(Attributes.MAX_HEALTH,10).add(Attributes.MOVEMENT_SPEED, 1.75).add(Attributes.FOLLOW_RANGE,10);
    }

  //  private void setPackedVariant(int pPackedVariant) {
   //     this.entityData.set(DATA_ID_TYPE_VARIANT, pPackedVariant);
   // }

  //  private int getPackedVariant() {
   //     return this.entityData.get(DATA_ID_TYPE_VARIANT);
   // }


    protected void handleAirSupply(int pAirSupply) {
        if (this.isAlive() && (!this.isInLava())) {
            this.setAirSupply(pAirSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }



    }


    public static boolean checkPaddlefishSpawnRules(EntityType<LavaPaddleFish> pfish, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        BlockPos.MutableBlockPos blockpos$mutable = pPos.mutable();

        do {
            blockpos$mutable.move(Direction.UP);
        } while(pLevel.getFluidState(blockpos$mutable).is(FluidTags.LAVA));

        return pLevel.getBlockState(blockpos$mutable).isAir();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL) || source.is(DamageTypes.IN_WALL) ||source.is(DamageTypes.LAVA) ||super.isInvulnerableTo(source);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(4, new FishSwimGoal(this));
    }



    static class FishSwimGoal extends RandomSwimmingGoal {
        private final Abstractfishmoray fish;

        public FishSwimGoal(Abstractfishmoray pFish) {
            super(pFish, 1.0, 40);
            this.fish = pFish;
        }

        public boolean canUse() {
            return this.fish.canRandomSwim() && super.canUse();
        }
    }




    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
     //   this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
     //   pCompound.putInt("Variant", this.getPackedVariant());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
     //   this.setPackedVariant(pCompound.getInt("Variant"));
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(Itemregististeries.BUCKETED_PADDLEFISH.get());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<LavaPaddleFish> lavaPaddleFishAnimationState) {
        if (!lavaPaddleFishAnimationState.isMoving()){
            lavaPaddleFishAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.paddlefish.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (lavaPaddleFishAnimationState.isMoving()){
            lavaPaddleFishAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.paddlefish.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }

    private static class FishMoveControl extends MoveControl {
        private final Abstractfishmoray fish;

        FishMoveControl(Abstractfishmoray pFish) {
            super(pFish);
            this.fish = pFish;
        }

        public void tick() {
            if (this.fish.isEyeInFluid(FluidTags.LAVA)) {
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


}
