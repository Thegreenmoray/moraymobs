package com.moray.moraymobs.entity.living.boss;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Omnidens extends Monster implements GeoEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);


    public Omnidens(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMonsterAttributes() {
        return Monster.createMobAttributes().add(Attributes.ATTACK_DAMAGE,10).add(Attributes.MAX_HEALTH,400)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY,0.4f)
                .add(Attributes.MOVEMENT_SPEED,0.4f).add(Attributes.FOLLOW_RANGE,20f);
    }
    

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.DROWN)||source.is(DamageTypes.FALL)||super.isInvulnerableTo(source);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
controllers.add(new AnimationController<>(this,
        "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Omnidens> omnidensAnimationState) {



        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
