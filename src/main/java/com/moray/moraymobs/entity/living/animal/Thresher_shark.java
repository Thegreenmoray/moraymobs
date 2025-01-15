package com.moray.moraymobs.entity.living.animal;

import com.moray.moraymobs.ai.TailWhipgoal;
import com.moray.moraymobs.ai.Threshermeleeattackgoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Thresher_shark extends WaterAnimal implements GeoEntity {
    protected static final EntityDataAccessor<Integer> WHIPTIME= SynchedEntityData.defineId(Thresher_shark.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> BITE= SynchedEntityData.defineId(Thresher_shark.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> ANIMATION= SynchedEntityData.defineId(Thresher_shark.class, EntityDataSerializers.INT);


    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);


    public Thresher_shark(EntityType<? extends WaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createMobAttributes().add(Attributes.MAX_HEALTH,30).add(Attributes.MOVEMENT_SPEED, 0.50).add(Attributes.FOLLOW_RANGE,20).add(Attributes.ATTACK_DAMAGE,8).add(Attributes.ARMOR,2.0f);
    }

    public int getbite(){
        return this.entityData.get(BITE);
    }

    public void setbite(int bite){
        this.entityData.set(BITE,bite);
    }

    public int getanime(){
        return this.entityData.get(ANIMATION);
    }

    public void setanime(int anime){
        this.entityData.set(ANIMATION,anime);
    }

    public int getwhiptime(){
        return this.entityData.get(WHIPTIME);
    }

    public void setwhiptime(int whiptime){
        this.entityData.set(WHIPTIME,whiptime);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("whiptime", this.getwhiptime());
    pCompound.putInt("bite",this.getbite());
        pCompound.putInt("anime",this.getanime());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setwhiptime(pCompound.getInt("whiptime"));
    this.setwhiptime(pCompound.getInt("bite"));
    this.setanime(pCompound.getInt("anime"));
    }

    protected void defineSynchedData (SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(WHIPTIME, 0);
        builder.define(BITE,0);
        builder.define(ANIMATION,0);
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }

    @Override
    public void tick() {


        if (getwhiptime()>0){setwhiptime(getwhiptime()-1);}

        if (getbite()>0){setbite(getbite()-1);}
        super.tick();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new Threshermeleeattackgoal(this, 1.2000000476837158, true));
this.targetSelector.addGoal(5,new HurtByTargetGoal(this));
this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 10));
this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, TropicalFish.class, true, false));
      this.goalSelector.addGoal(3,new TailWhipgoal(this,20));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Thresher_shark> thresherSharkAnimationState) {
        if(getanime()==1&&getbite()>0){
            thresherSharkAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.new", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        if(getanime()==2&&getwhiptime()>0){
            thresherSharkAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.thresher.whip", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        if (thresherSharkAnimationState.isMoving()&&getbite()<=1&&getwhiptime()<=1){
            thresherSharkAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.thresher.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!thresherSharkAnimationState.isMoving()&&getbite()<=1&&getwhiptime()<=1){
            thresherSharkAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.thresher.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;

    }



    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
