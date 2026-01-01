package com.moray.moraymobs.entity.living.animalornpc;

import com.moray.moraymobs.ai.*;
import com.moray.moraymobs.item.basis.Pawpawbomb;
import com.moray.moraymobs.trades.Spriggan_trades;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.UUID;

public class Spriggan extends AbstractVillager implements GeoEntity, NeutralMob {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
   // private static final EntityDataAccessor<Optional<GlobalPos>> HOME_POINT = SynchedEntityData.defineId(Spriggan.class, EntityDataSerializers.OPTIONAL_GLOBAL_POS);

    private static final EntityDataAccessor<Integer> BEAM= SynchedEntityData.defineId(Spriggan.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACKTIME= SynchedEntityData.defineId(Spriggan.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> ISSLEEPING= SynchedEntityData.defineId(Spriggan.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ISSTRECHING= SynchedEntityData.defineId(Spriggan.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME= TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    public int getattacktime(){
        return this.entityData.get(ATTACKTIME);
    }

    public void setattacktime(int attacktime){
        this.entityData.set(ATTACKTIME,attacktime);
    }

    public int getbeamtime(){
        return this.entityData.get(BEAM);
    }

    public void setbeamtime(int beamtime){
        this.entityData.set(BEAM,beamtime);
    }

    public boolean issleeping(){
        return this.entityData.get(ISSLEEPING);
    }

    public void setIssleeping(boolean issleeping){
        this.entityData.set(ISSLEEPING,issleeping);
    }

    public int isstreching(){
        return this.entityData.get(ISSTRECHING);
    }

    public void setIsstreching(int streching){
        this.entityData.set(ISSTRECHING,streching);
    }


    public Spriggan(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F){

            @Override
            public boolean canUse() {
                return super.canUse()&&!issleeping()&&isstreching()<=0&&getbeamtime()<=0;
            }
        });
        this.goalSelector.addGoal(2, new Spriggan_move_goal(this, 0.7, false));
//this.goalSelector.addGoal(4,new RandomStrollGoal(this,0.7));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this) {

            @Override
            public boolean canUse() {
                return super.canUse()&&!issleeping()&&isstreching()<=0&&getbeamtime()<=0;
            }
        });
        // add slash in later time
  this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
this.targetSelector.addGoal(4,new Sprigganattackgoal(this));
this.goalSelector.addGoal(5,new Sleep_goal(this));
this.goalSelector.addGoal(5,new Yawn_goal(this));
this.goalSelector.addGoal(5,new Spriggan_laser_goal(this));
this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,this::isAngryAt));

    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 100.0).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.ATTACK_DAMAGE, 10.0).add(Attributes.FOLLOW_RANGE, 64.0);
    }

    @Override
    protected void customServerAiStep() {
        this.updatePersistentAnger((ServerLevel)this.level(), true);

        super.customServerAiStep();
    }

    @Override
    public void aiStep() {


        if(isstreching()>0){
         setIsstreching(isstreching()-1);
        }
        if(getbeamtime()>=-200){
            setbeamtime(getbeamtime()-1);
        }

        super.aiStep();
    }


    @Override
    public boolean hurt(DamageSource source, float amount) {

        if(source.is(DamageTypes.ON_FIRE)){
            return super.hurt(source,2*amount);
        }
if (source.getEntity() instanceof Pawpawbomb){
    return false;
}

        return super.hurt(source, amount);
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return false;
    }
    @Override
    protected void tickDeath() {
        ++this.deathTime;


        if ( this.deathTime==1){

        }
        if (this.deathTime == 60 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }


    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.remainingPersistentAngerTime = time;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }





    @Override
    protected void rewardTradeXp(MerchantOffer merchantOffer) {

    }
    public void setTradingPlayer(@Nullable Player player) {
        boolean flag = this.getTradingPlayer() != null && player == null;
        super.setTradingPlayer(player);
        if (flag) {
            this.stopTrading();
        }

    }

    protected void stopTrading() {
        super.stopTrading();

    }
    public boolean showProgressBar() {
        return false;
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        if (this.isAlive() && !this.isTrading()
      && !issleeping()&&!this.level().isClientSide&&!isAggressive()) { //should be more like !issleeping but this will do for now
            if (!this.getOffers().isEmpty()) {
                if (!this.level().isClientSide) {
                    this.setTradingPlayer(player);
                    this.openTradingScreen(player, this.getDisplayName(), 1);
                 }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public boolean addEffect(MobEffectInstance effectInstance, @org.jetbrains.annotations.Nullable Entity entity) {
        return false;
    }

    public SoundEvent getNotifyTradeSound() {
        return null;
    }

    protected SoundEvent getTradeUpdatedSound(boolean p_35890_) {
        return null;
    }


    @Override
    public void knockback(double strength, double x, double z) {
        super.knockback(strength/2, x/2, z/2);
    }

    protected void updateTrades() {
        VillagerTrades.ItemListing[] factorys= Spriggan_trades.SPRIGGAN_TRADES.get(1); //add her trades here when finished
        if (factorys != null) {
            MerchantOffers tradeOfferList = this.getOffers();
            int offers = random.nextInt(3) + 7;
            this.addOffersFromItemListings(tradeOfferList, factorys, offers);
        }
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Spriggan> sprigganAnimationState) {

        if (getHealth()<=0.01){
            sprigganAnimationState.getController().setAnimation(RawAnimation.begin().then("death", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if(getbeamtime()>0){
            sprigganAnimationState.getController().setAnimation(RawAnimation.begin().then("laser", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;}


        if(isstreching()>0){
            sprigganAnimationState.getController().setAnimation(RawAnimation.begin().then("strech", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }


        if(issleeping()){
            sprigganAnimationState.getController().setAnimation(RawAnimation.begin().then("sleep", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if(sprigganAnimationState.isMoving()&& !issleeping()){ //&& !issleeping()
            sprigganAnimationState.getController().setAnimation(RawAnimation.begin().then("spriggan.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if (!sprigganAnimationState.isMoving()){
            sprigganAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }




        return PlayState.STOP;
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACKTIME,0);
        builder.define(BEAM,0);
        builder.define(ISSLEEPING,false);
        builder.define(ISSTRECHING,0);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
pCompound.putInt("attack",this.getattacktime());
pCompound.putInt("beam",this.getbeamtime());
pCompound.putBoolean("issleeping",this.issleeping());
pCompound.putInt("isstreching",this.isstreching());
        this.readPersistentAngerSaveData(this.level(), pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
this.setattacktime(pCompound.getInt("attack"));
this.setbeamtime(pCompound.getInt("beam"));
this.setIssleeping(pCompound.getBoolean("issleeping"));
this.setIsstreching(pCompound.getInt("isstreching"));
        this.addPersistentAngerSaveData(pCompound);
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public @org.jetbrains.annotations.Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

}
