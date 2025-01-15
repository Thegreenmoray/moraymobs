package com.moray.moraymobs.entity.living.animal;

import com.moray.moraymobs.ai.Basaltliskeatgoal;
import com.moray.moraymobs.registries.Blockregistrires;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class Basaltlisk extends Animal implements NeutralMob, GeoEntity {
    private static final EntityDataAccessor<Boolean> EATEN=SynchedEntityData.defineId(Basaltlisk.class,EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TOUNGE=SynchedEntityData.defineId(Basaltlisk.class,EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TOUNGE_TIMER=SynchedEntityData.defineId(Basaltlisk.class,EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Basaltlisk.class, EntityDataSerializers.BYTE);;
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    private static final UniformInt PERSISTENT_ANGER_TIME= TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    public int gettoungetimer(){
        return this.entityData.get(TOUNGE_TIMER);
    }

    public void settoungetimer(int toungetimer){
        this.entityData.set(TOUNGE_TIMER,toungetimer);
    }

  public int gettoungetime(){
      return this.entityData.get(TOUNGE);
  }

  public void settoungetime(int toungetime){
      this.entityData.set(TOUNGE,toungetime);
  }

public boolean has_eaten(){
    return this.entityData.get(EATEN);
}

public void set_eaten(boolean eaten){
    this.entityData.set(EATEN,eaten);
}

    public Basaltlisk(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(PathType.LAVA, -1.0F);

    }

    protected @NotNull PathNavigation createNavigation(Level pLevel) {
        return new WallClimberNavigation(this, pLevel);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes().add(Attributes.MAX_HEALTH, 40.0).add(Attributes.MOVEMENT_SPEED, 0.2).add(Attributes.ATTACK_DAMAGE, 3.0).add(Attributes.FOLLOW_RANGE, 16.0);
    }

    public boolean isOnFire() {
        return false;
    }


    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }

    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        this.addPersistentAngerSaveData(pCompound);
     pCompound.putBoolean("eat",this.has_eaten());
     pCompound.putInt("tounge",this.gettoungetime());
        pCompound.putInt("tounge_timer",this.gettoungetimer());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.readPersistentAngerSaveData(this.level(), pCompound);
        this.set_eaten(pCompound.getBoolean("eat"));
       this.settoungetime(pCompound.getInt("tounge"));
        this.settoungetimer(pCompound.getInt("tounge_timer"));
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return ((Byte)this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setClimbing(boolean pClimbing) {
        byte b0 = (Byte)this.entityData.get(DATA_FLAGS_ID);
        if (pClimbing) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 &= -2;
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false){

            @Override
            public boolean canUse() {
                return super.canUse()&&this.mob.getTarget()!=null&&this.mob.distanceTo(mob.getTarget())>9;
            }

            protected double getAttackReachSqr(LivingEntity pAttackTarget) {
                return 1.0D;
            }
        });
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.25, Ingredient.of(new ItemLike[]{
                Items.MAGMA_CREAM}), false));
        this.goalSelector.addGoal(1,new Basaltliskeatgoal(this));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MagmaCube.class, true, false));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
  }


    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL) || source.is(DamageTypes.IN_WALL) || super.isInvulnerableTo(source);
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(EATEN,false);
        builder.define(TOUNGE,0);
        builder.define(DATA_FLAGS_ID, (byte)0);
        builder.define(TOUNGE_TIMER,0);
  }
    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
    boolean isnexttolava=false;
       for (Direction direction:Direction.values()){

       BlockPos blockPos= pPos.relative(direction);

        isnexttolava =  (pLevel.getBlockState(blockPos).getFluidState().is(FluidTags.LAVA));

       if (isnexttolava){
           break;
       }
       }

        return isnexttolava ? 14f:8f;
    }



    @Override
    protected void customServerAiStep() {

      if(this.gettoungetimer()>0){
          settoungetimer(gettoungetimer()-1);
      }



      if (has_eaten()) {
          this.spawnAtLocation(Blockregistrires.BASALTLAMP.get().asItem());
      set_eaten(false);
      }
        super.customServerAiStep();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {

        return Mobregistries.BASALTISK.get().create(serverLevel);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {
        this.remainingPersistentAngerTime = i;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        this.persistentAngerTarget = uuid;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    public  boolean isFood(ItemStack itemStack){
        return itemStack.is(Items.MAGMA_CREAM);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    controllers.add(new AnimationController<>(this,"Controller",this::animations));
    }

    private PlayState animations(AnimationState<Basaltlisk> basaltliskAnimationState) {
        if (this.gettoungetime()== 1){
            basaltliskAnimationState.getController().setAnimation(RawAnimation.begin().then("eat", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if(basaltliskAnimationState.isMoving()){
            basaltliskAnimationState.getController().setAnimation(RawAnimation.begin().then("basaltlisk.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if (!basaltliskAnimationState.isMoving()){
            basaltliskAnimationState.getController().setAnimation(RawAnimation.begin().then("basaltlisk.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        return PlayState.STOP;

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
