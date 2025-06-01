package com.moray.moraymobs.entity.living.dungeonentities;

import com.moray.moraymobs.entity.abstractentity.Abstractfishmoray;
import com.moray.moraymobs.entity.living.animal.Enderbowfin;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
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

public class Schinderhannes extends Monster implements GeoEntity {
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);


    public Schinderhannes(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl =new SchinderhannesMoveControl(this);
        this.xpReward=5;
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }



    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,0.5,1));
this.goalSelector.addGoal(1,new MeleeAttackGoal(this,0.5,false));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.@NotNull Builder createMonsterAttributes() {
        return Monster.createMobAttributes().add(Attributes.ATTACK_DAMAGE,8).add(Attributes.MAX_HEALTH,25)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY,0.5f).add(Attributes.ARMOR,5f)
                .add(Attributes.FOLLOW_RANGE,20f);
    }

    public boolean isPushedByFluid() {
        return false;
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }


    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.HOT_FLOOR)||source.is(DamageTypes.DROWN)||super.isInvulnerable();
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }



    private static class SchinderhannesMoveControl extends MoveControl {
        private final Schinderhannes schinderhannes;

        public SchinderhannesMoveControl( Schinderhannes schinderhannes) {
            super(schinderhannes);
            this.schinderhannes = schinderhannes;
        }

        public void tick() {
            if (this.schinderhannes.isEyeInFluid(FluidTags.WATER)) {
                this.schinderhannes.setDeltaMovement(this.schinderhannes.getDeltaMovement().add(0.0, 0.005, 0.0));
            }

            if (this.operation == Operation.MOVE_TO && !this.schinderhannes.getNavigation().isDone()) {
                float $$0 = (float)(this.speedModifier * this.schinderhannes.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.schinderhannes.setSpeed(Mth.lerp(0.125F, this.schinderhannes.getSpeed(), $$0));
                double $$1 = this.wantedX - this.schinderhannes.getX();
                double $$2 = this.wantedY - this.schinderhannes.getY();
                double $$3 = this.wantedZ - this.schinderhannes.getZ();
                if ($$2 != 0.0) {
                    double $$4 = Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
                    this.schinderhannes.setDeltaMovement(this.schinderhannes.getDeltaMovement().add(0.0, (double)this.schinderhannes.getSpeed() * ($$2 / $$4) * 0.1, 0.0));
                }

                if ($$1 != 0.0 || $$3 != 0.0) {
                    float $$5 = (float)(Mth.atan2($$3, $$1) * 57.2957763671875) - 90.0F;
                    this.schinderhannes.setYRot(this.rotlerp(this.schinderhannes.getYRot(), $$5, 90.0F));
                    this.schinderhannes.yBodyRot = this.schinderhannes.getYRot();
                }

            } else {
                this.schinderhannes.setSpeed(0.0F);
            }
        }}


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Schinderhannes> schinderhannesAnimationState) {

        if (schinderhannesAnimationState.isMoving()){
            schinderhannesAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.schinderhannes.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!schinderhannesAnimationState.isMoving()){
            schinderhannesAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.schinderhannes.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }



        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
