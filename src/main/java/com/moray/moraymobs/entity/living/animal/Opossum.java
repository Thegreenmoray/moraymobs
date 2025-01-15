package com.moray.moraymobs.entity.living.animal;

import com.moray.moraymobs.ai.PossumFaintgoal;
import com.moray.moraymobs.ai.PossumScreamgoal;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Opossum extends Animal implements GeoEntity {
    protected static final EntityDataAccessor<Boolean> FAINTED= SynchedEntityData.defineId(Opossum.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SCREAM= SynchedEntityData.defineId(Opossum.class, EntityDataSerializers.BOOLEAN);


    public Opossum(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public boolean isscream(){
        return this.entityData.get(SCREAM);
    }

    public void setScream(boolean scream){
        this.entityData.set(SCREAM,scream);
    }

    public boolean isfainted(){
        return this.entityData.get(FAINTED);
    }

    public void setFainted(boolean fainted){
        this.entityData.set(FAINTED,fainted);
    }


    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void aiStep() {
//I will add something for the opposum later

        super.aiStep();
    }

    protected void defineSynchedData (SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FAINTED, false);
        builder.define(SCREAM,false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes().add(Attributes.MAX_HEALTH,10).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.FOLLOW_RANGE,10);
    }



    protected void registerGoals() {
        this.goalSelector.addGoal(0,new FloatGoal(this));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0){
            @Override
            public boolean canUse() {
                return super.canUse()&&(partner instanceof Opossum opossum&&!opossum.isfainted())&&(animal instanceof Opossum opossum1&&!opossum1.isfainted());
            }
        });
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.25, Ingredient.of(new ItemLike[]{
                Items.ROTTEN_FLESH}), false){
            @Override
            public boolean canUse() {
                return super.canUse()&&(mob instanceof Opossum&&!isfainted());
            }
        });
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0){
            @Override
            public boolean canUse() {
                return super.canUse()&&(mob instanceof Opossum&&!isfainted())&&(mob instanceof Opossum&&!isscream());
            }
        });
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F){
            @Override
            public boolean canUse() {
                return super.canUse()&&(mob instanceof Opossum&&!isfainted());
            }
        });
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    this.goalSelector.addGoal(2,new PossumScreamgoal(this,50));
    this.goalSelector.addGoal(3, new PossumFaintgoal(this,100));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return Mobregistries.OPOSSUM.get().create(level());
    }


    public  boolean isFood(ItemStack itemStack){
        return itemStack.is(Items.ROTTEN_FLESH);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("fiantied", this.isfainted());
pCompound.putBoolean("screm",this.isscream());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setFainted(pCompound.getBoolean("fiantied"));
this.setScream(pCompound.getBoolean("screm"));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Opossum> opossumAnimationState) {
        if (isscream()){
            opossumAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.screm", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if(isfainted()){
            opossumAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.faint", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if (opossumAnimationState.isMoving()){
            opossumAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.possum.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!opossumAnimationState.isMoving()){
            opossumAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.possum.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;

    }



    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
