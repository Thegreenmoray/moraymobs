package com.moray.moraymobs.entity.living.monster;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Body_Snatcher extends Monster implements GeoEntity {


    public Body_Snatcher(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl =new MoveControl(this);
    this.navigation=new GroundPathNavigation(this,level());
    }
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH,15).add(Attributes.FOLLOW_RANGE, 20.0).add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.ATTACK_DAMAGE, 3.0).add(Attributes.ARMOR, 2.0);
    }


    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.3, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public void aiStep() {

      if (!this.level().isClientSide()){

        for(int i = 0; i < 4; ++i) {
            this.level().addParticle(ParticleTypes.MYCELIUM, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
        }
      }

        super.aiStep();
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));

    }

    private PlayState animations(AnimationState<Body_Snatcher> bodySnatcherAnimationState) {

        if(bodySnatcherAnimationState.isMoving()){
            bodySnatcherAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!bodySnatcherAnimationState.isMoving()){
            bodySnatcherAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bodysnatcher.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }



        return PlayState.STOP;

    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
