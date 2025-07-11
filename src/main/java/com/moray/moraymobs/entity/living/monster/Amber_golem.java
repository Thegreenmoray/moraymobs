package com.moray.moraymobs.entity.living.monster;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Amber_golem extends Raider implements GeoEntity {

    private static final EntityDataAccessor<Integer> ANIMATION= SynchedEntityData.defineId(Amber_golem.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SLAM= SynchedEntityData.defineId(Amber_golem.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SUCC= SynchedEntityData.defineId(Amber_golem.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PUNCH= SynchedEntityData.defineId(Amber_golem.class, EntityDataSerializers.INT);



    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    public Amber_golem(EntityType<? extends Raider> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH,80).add(Attributes.FOLLOW_RANGE, 20.0).add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ATTACK_DAMAGE, 9.0).add(Attributes.KNOCKBACK_RESISTANCE,2)
                .add(Attributes.ARMOR,5);
    }

    public int getpunch(){
        return this.entityData.get(PUNCH);
    }
    public void setpunch(int punch){
        this.entityData.set(PUNCH,punch);
    }

    public int getsucc(){
        return this.entityData.get(SUCC);
    }
    public void setsucc(int succ){
        this.entityData.set(SUCC,succ);
    }

    public int getslam(){
        return this.entityData.get(SLAM);
    }
    public void setslam(int slam){
        this.entityData.set(SLAM,slam);
    }

    public int getanimation(){
        return this.entityData.get(ANIMATION);
    }
    public void setanimation(int animation){
        this.entityData.set(ANIMATION,animation);
    }


    @Override
    protected void registerGoals() {

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Monster.class, true){

            @Override
            public boolean canUse() {
                return super.canUse()&&!(mob.getTarget()instanceof Raider);
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this){

            @Override
            public boolean canUse() {
                return super.canUse()&&!(mob.getTarget()instanceof Raider);
            }


        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
    }

    @Override
    public void applyRaidBuffs(ServerLevel serverLevel, int i, boolean b) {

    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.AMETHYST_BLOCK_CHIME;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Amber_golem> amber_golemAnimationState) {







        if(amber_golemAnimationState.isMoving()){
            amber_golemAnimationState.getController().setAnimation(RawAnimation.begin().then("", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!amber_golemAnimationState.isMoving()){
            amber_golemAnimationState.getController().setAnimation(RawAnimation.begin().then("", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
