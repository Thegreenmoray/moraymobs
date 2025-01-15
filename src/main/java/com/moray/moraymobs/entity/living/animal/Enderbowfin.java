package com.moray.moraymobs.entity.living.animal;

import com.moray.moraymobs.entity.abstractentity.Abstractfishmoray;
import com.moray.moraymobs.registries.Itemregististeries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
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

public class Enderbowfin extends Abstractfishmoray implements GeoEntity {
    public Enderbowfin(EntityType<? extends WaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(PathType.WATER,0.0f);
        this.moveControl = new FishMoveControl(this);
       this.navigation=new WaterBoundPathNavigation(this,this.level());
        this.xpReward=5;
    }
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);




    public static AttributeSupplier.Builder createAttributes() {
        return AbstractFish.createMobAttributes().add(Attributes.MAX_HEALTH,10).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.FOLLOW_RANGE,10);
    }

    protected void handleAirSupply(int pAirSupply) {
       //Bowfins are air breathing fish, so I'll just set their air supply like this.
            this.setAirSupply(300);



    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(4, new FishSwimGoal(this));

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Enderbowfin> enderbowfinAnimationState) {
        if(enderbowfinAnimationState.isMoving()){
            enderbowfinAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.move.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if (!enderbowfinAnimationState.isMoving()){
            enderbowfinAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.idle.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        return PlayState.STOP;
    }



    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }

    static class FishSwimGoal extends RandomSwimmingGoal {
        private final Enderbowfin enderbowfin;

        public FishSwimGoal(Enderbowfin enderbowfin) {
            super(enderbowfin, 1.0, 40);
            this.enderbowfin = enderbowfin;
        }

        public boolean canUse() {
            return this.enderbowfin.canRandomSwim() && super.canUse();
        }
    }



    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(Itemregististeries.BUCKETED_BOWFIN.get());
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


}

