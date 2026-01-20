package com.moray.moraymobs.entity.living.animalornpc;

import com.moray.moraymobs.ai.animalsornpcgoals.Pronghorneatgoal;
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

public class Pronghorn extends Animal implements GeoEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    protected static final EntityDataAccessor<Integer> EATING= SynchedEntityData.defineId(Pronghorn.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> RUNNING= SynchedEntityData.defineId(Pronghorn.class, EntityDataSerializers.INT);

    public Pronghorn(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public int getrunning(){
        return this.entityData.get(RUNNING);
    }

    public void setrunning(int run){
        this.entityData.set(RUNNING,run);
    }


    public int geteating(){
        return this.entityData.get(EATING);
    }

    public void seteating(int eating){
        this.entityData.set(EATING,eating);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes().add(Attributes.MAX_HEALTH,20).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.FOLLOW_RANGE,10)
        .add(Attributes.STEP_HEIGHT, 1.0); }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 4.25){
            @Override
            public void start() {
               super.start();
                if (this.mob instanceof Pronghorn pronghorn){
                    pronghorn.setrunning(40);
                    pronghorn.seteating(0);
              }
            }

          @Override
           public void stop() {
               super.stop();
                if (this.mob instanceof Pronghorn pronghorn){
                   pronghorn.setrunning(0);
              }
            }
        });
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(new ItemLike[]{Items.WHEAT}), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0){
            @Override
            public boolean canUse() {
                return this.mob instanceof Pronghorn pronghorn&&pronghorn.geteating()==0&&super.canUse();
            }
        });
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
       this.goalSelector.addGoal(8,new Pronghorneatgoal(this));
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("eating", this.geteating());
        pCompound.putInt("running", this.getrunning());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.seteating(pCompound.getInt("eating"));
        this.seteating(pCompound.getInt("running"));
    }

    protected void defineSynchedData (SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(EATING, 0);
        builder.define(RUNNING,0);
    }

    @Override
    public void aiStep() {

        if (getrunning()>0){
            setrunning(getrunning()-1);
        }


        if(geteating()>0){
            seteating(geteating()-1);
        }

        super.aiStep();
    }

    public boolean isFood(ItemStack itemStack){
        return itemStack.is(Items.WHEAT);
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return Mobregistries.PRONGHORN.get().create(serverLevel);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Pronghorn> pronghornAnimationState) {
        if (this.geteating()>=10){
            pronghornAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pronghorn.eat", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (this.getrunning()>=10){
            pronghornAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pronghorn.run", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        if (pronghornAnimationState.isMoving()&&this.geteating()<10&&this.getrunning()<10){
            pronghornAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pronghorn.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        if(!pronghornAnimationState.isMoving()&&this.geteating()<10&&this.getrunning()<10){
            pronghornAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.idle.pronghorn", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }




        return PlayState.STOP;
    }



    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
