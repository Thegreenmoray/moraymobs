package com.moray.moraymobs.entity.living.animalornpc;


import com.moray.moraymobs.ai.animalsornpcgoals.Rockpup_move_goal;
import com.moray.moraymobs.ai.animalsornpcgoals.animalornpc.Intimidatetargetgoal;
import com.moray.moraymobs.ai.animalsornpcgoals.animalornpc.Rollinggoal;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class Rockpup extends TamableAnimal implements GeoEntity, NeutralMob {
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);
    private static final Direction[] ALL_DIRECTIONS = Direction.values();
    protected static final EntityDataAccessor<Boolean> DIZZY= SynchedEntityData.defineId(Rockpup.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> INTIMDATING= SynchedEntityData.defineId(Rockpup.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> ROLL= SynchedEntityData.defineId(Rockpup.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> START_ROLL= SynchedEntityData.defineId(Rockpup.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> TIME= SynchedEntityData.defineId(Rockpup.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME= TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;
    public RawAnimation ball =RawAnimation.begin().then("ball", Animation.LoopType.HOLD_ON_LAST_FRAME);
    public Rockpup(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return super.createNavigation(level);
    }

    public boolean getdizzy(){
        return this.entityData.get(DIZZY);
    }

    public void setdizzy(boolean dizzy){
        this.entityData.set(DIZZY,dizzy);
    }

    public boolean isintimdating(){
        return this.entityData.get(INTIMDATING);
    }

    public void setIntimdating(boolean intimdating){
        this.entityData.set(INTIMDATING,intimdating);
    }

    public boolean isrolling(){
        return this.entityData.get(ROLL);
    }

    public void setrolling(boolean rolling){
        this.entityData.set(ROLL,rolling);
    }

    public boolean isstartrolling(){
        return this.entityData.get(START_ROLL);
    }

    public void setstartrolling(boolean startrolling){
        this.entityData.set(START_ROLL,startrolling);
    }

    public int gettimer(){
        return this.entityData.get(TIME);
    }

    public void settimer(int timer){
        this.entityData.set(TIME,timer);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("dizzy", this.getdizzy());
        pCompound.putBoolean("intimdating", this.isintimdating());
        this.addPersistentAngerSaveData(pCompound);
        pCompound.putBoolean("rolling", this.isrolling());
        pCompound.putBoolean("startrolling", this.isstartrolling());
        pCompound.putInt("timer",this.gettimer());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setdizzy(pCompound.getBoolean("dizzy"));
        this.setIntimdating(pCompound.getBoolean("intimdating"));
        this.readPersistentAngerSaveData(this.level(), pCompound);
        this.setrolling(pCompound.getBoolean("rolling"));
        this.setstartrolling(pCompound.getBoolean("startrolling"));
        this.settimer(pCompound.getInt("timer"));
    }

    protected void defineSynchedData (SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DIZZY, false);
        builder.define(INTIMDATING, false);
        builder.define(ROLL, false);
        builder.define(START_ROLL, false);
        builder.define(TIME, 0);
    }

    @Override
    public void aiStep() {

        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level(), true);
        }
        if (this.getTarget() != null) {
            this.maybeAlertOthers();
        }

        if (this.isAngry()) {
            this.lastHurtByPlayerTime = this.tickCount;
        }


        if (getdizzy()){
            settimer(gettimer()+1);
        }
        if (gettimer()>=60){
            setdizzy(false);
            settimer(0);
        }

        super.aiStep();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractFish.createMobAttributes().add(Attributes.MAX_HEALTH,15).add(Attributes.MOVEMENT_SPEED, 1).add(Attributes.FOLLOW_RANGE,30)
                .add(Attributes.ARMOR,8).add(Attributes.ATTACK_DAMAGE,6);
    }




    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.AMETHYST_SHARD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return Mobregistries.ROCKPUP.get().create(level());

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Rockpup> rockpupAnimationState) {

        if (getdizzy()&&gettimer()>30){ //&&plays when snapping out of dizziness
            rockpupAnimationState.getController().setAnimation(RawAnimation.begin().then("dizzy2", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (getdizzy()){
            rockpupAnimationState.getController().setAnimation(RawAnimation.begin().then("dizzy", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (isintimdating()){
            rockpupAnimationState.getController().setAnimation(RawAnimation.begin().then("intimidate", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (isstartrolling()){
            rockpupAnimationState.getController().setAnimation(ball);
            return PlayState.CONTINUE;
        }

        if (isrolling()){
            rockpupAnimationState.getController().setAnimation(RawAnimation.begin().then("rolling", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!rockpupAnimationState.isMoving()){
            rockpupAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (rockpupAnimationState.isMoving()&&!isrolling()){
            rockpupAnimationState.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }


    @Override
    protected void registerGoals() {
  this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, this::isAngryAt){
            @Override
            public boolean canUse() {
                return super.canUse()&& !(target instanceof Rockpup);
            }
        });
 this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
this.goalSelector.addGoal(3,new Intimidatetargetgoal(this));
this.goalSelector.addGoal(5,new Rockpup_move_goal(this,0.7f,false));
 this.goalSelector.addGoal(4,new Rollinggoal(this,80));
this.targetSelector.addGoal(0, new HurtByTargetGoal(this){
            @Override
            public boolean canUse() {
                return super.canUse()&&!(mob.getTarget()instanceof Rockpup);
            }
        });
this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 0.5F, 10.0F, 2.0F));
this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
  this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, (double)1.0F));
        this.goalSelector.addGoal(7, new BreedGoal(this, (double)1.0F));

    }

    public boolean canMate(Animal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (otherAnimal instanceof Rockpup) {
           Rockpup rockpup= (Rockpup)otherAnimal;
            if (!rockpup.isTame()) {
                return false;
            } else {
                return rockpup.isInSittingPose() ? false : this.isInLove() && rockpup.isInLove();
            }
        } else {
            return false;
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isTame()) {
            if (this.isOwnedBy(player)) {
                if (isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!this.level().isClientSide()) {

                        this.heal(2.0f);
                        this.usePlayerItem(player, hand, itemstack);
                    }

                    return InteractionResult.sidedSuccess(this.level().isClientSide());
                }

                InteractionResult interactionresult = super.mobInteract(player, hand);
                if (!interactionresult.consumesAction()) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    return InteractionResult.sidedSuccess(this.level().isClientSide());
                }

                return interactionresult;
            }
        } else if (isFood(itemstack)&&!isAngry()) {
            if (!this.level().isClientSide()) {
                this.usePlayerItem(player, hand, itemstack);
                this.tryToTame(player);
                this.setPersistenceRequired();
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }

        InteractionResult interactionresult1 = super.mobInteract(player, hand);
        if (interactionresult1.consumesAction()) {
            this.setPersistenceRequired();
        }


        return super.mobInteract(player, hand);
    }


    private void maybeAlertOthers() {

            if (this.getSensing().hasLineOfSight(this.getTarget())) {
                this.alertOthers();
            }
    }

    private void alertOthers() {
        double d0 = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        AABB aabb = AABB.unitCubeFromLowerCorner(this.position()).inflate(d0, (double)10.0F, d0);
        this.level().getEntitiesOfClass(Rockpup.class, aabb, EntitySelector.NO_SPECTATORS).stream().filter((p_34463_) -> p_34463_ != this).filter((p_325818_) -> p_325818_.getTarget() == null).filter((p_325817_) -> !p_325817_.isAlliedTo(this.getTarget())).forEach((p_325816_) -> p_325816_.setTarget(this.getTarget()));
    }





    public static boolean checkRockpupSpawnRules(EntityType<Rockpup> pup, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom){



        return pLevel.getBlockState(pPos.below()).is(Blocks.DRIPSTONE_BLOCK);
    }





    private void tryToTame(Player player) {
        if (this.random.nextInt(3) == 0 && !EventHooks.onAnimalTame(this, player)) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget((LivingEntity)null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, (byte)7);
        } else {
            this.level().broadcastEntityEvent(this, (byte)6);
        }

    }





    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {
        this.remainingPersistentAngerTime = i;
    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
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
}
