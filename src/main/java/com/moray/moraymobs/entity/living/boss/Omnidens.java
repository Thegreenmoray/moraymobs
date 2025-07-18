package com.moray.moraymobs.entity.living.boss;

import com.moray.moraymobs.ai.*;
import com.moray.moraymobs.ai.omnidensgoals.*;
import com.moray.moraymobs.entity.projectiles.Sea_Mine;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class Omnidens extends Monster implements GeoEntity,PowerableMob {

    private static final EntityDataAccessor<Integer> BOOMERANGATTACK= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BURROW= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LEAP= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> GRIP= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> WHIRLPOOL= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ROAR= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LEAPGRAB= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> NONVISBLE= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SUMMON= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BOUNCE= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SLAM= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SEAMINE= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> ANIMATION= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);

    protected static final EntityDimensions UPRIGHT_DIMESIONS_JAW=EntityDimensions.fixed(6.0F, 3.0F);
    protected static final EntityDimensions UPRIGHT_DIMESIONS=EntityDimensions.fixed(4.0F, 3.0F);
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossEvent= (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);;
public final float oneforth=(this.getMaxHealth())/4;
public final float onehalf=(this.getMaxHealth())/2;
public final float threeforths= (float) ((this.getMaxHealth())*.75);
private int ejection=0;

    public Omnidens(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl =new MoveControl(this);
        this.xpReward=500;
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    public boolean isPushedByFluid() {
        return false;
    }



  /*  @Override
   // public SoundEvent getBossMusic() {
  //      return ;
  //  }
   will do later    */

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public void knockback(double pStrength, double pX, double pZ) {
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    public boolean hurt(DamageSource damageSource, float amount){

        if (isPowered()){
            amount= (float) (amount*.7);
        }

        if (damageSource.is(DamageTypes.ARROW)){
            amount= (float) (amount*.6);
        } else if (damageSource.is(DamageTypes.TRIDENT)) {
          amount = (float) (amount*1.1);
        }


        return amount>30&&!damageSource.is(DamageTypes.GENERIC_KILL)? super.hurt(damageSource,30):super.hurt(damageSource,amount);
    }

    public static AttributeSupplier.@NotNull Builder createMonsterAttributes() {
        return Monster.createMobAttributes().add(Attributes.ATTACK_DAMAGE,5).add(Attributes.MAX_HEALTH,600)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY,0.4f).add(Attributes.ARMOR,15f).add(Attributes.ARMOR_TOUGHNESS,5f)
                .add(Attributes.MOVEMENT_SPEED,0.4f).add(Attributes.STEP_HEIGHT,1).add(Attributes.FOLLOW_RANGE,50f).add(Attributes.ATTACK_KNOCKBACK,3);
    }
    public boolean isPowered() {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }
    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        if (pose == Pose.ROARING) {
            return UPRIGHT_DIMESIONS;
        } else if (pose==Pose.LONG_JUMPING) {
            return UPRIGHT_DIMESIONS_JAW;
        } else {
            return super.getDefaultDimensions(pose);
        }
    }


    @Override
    protected void tickDeath() {
        ++this.deathTime;

        List<Entity> seamineremoval=this.level().getEntities(this,this.getBoundingBox().inflate(100));

        for (Entity entity:seamineremoval){
            if (entity instanceof Sea_Mine){
                entity.remove(RemovalReason.DISCARDED);
            }
        }


        if (this.deathTime == 70 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
      this.goalSelector.addGoal(4,new Omnidenleapgoal(this));//done
      this.goalSelector.addGoal(6,new Whirlpoolgoal(this));//done
         this.goalSelector.addGoal(8,new OmnidensJumpattackgoal(this,50));//done
     this.goalSelector.addGoal(5,new Roaromnidensgoal(this));//done
     this.goalSelector.addGoal(3,new Omnidenprojectilegoal(this));//done
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(0,new RandomStrollGoal(this,0.5f) {
            @Override
           public boolean canUse() {
              return this.mob instanceof Omnidens omnidens&&omnidens.canuseskill()&&getsummontime()>=50&&super.canUse();
            }
        });
      this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 20.0F));
      this.goalSelector.addGoal(0, new MeleeomnidensGoal(this, 0.5f,false) {
          @Override
          public boolean canUse() {
             return mob instanceof Omnidens omnidens&&omnidens.canuseskill()&&getsummontime()>=50&&super.canUse();
          }
      });
       this.goalSelector.addGoal(7,new Burrowgoal(this));//done
       this.goalSelector.addGoal(9,new BouncingBallgoal(this));//done
       this.goalSelector.addGoal(8,new Slamgoal(this));//done
       this.goalSelector.addGoal(7,new Sea_Mine_Goal(this));//done
    }



    public boolean canuseskill(){

        return  getjumpgrab()<=0&& getroar()<=0&&
                 getboomerangtime()<=0&& getwhirlpool()<=0&& getBurrow()<=0&&
        !getleap()&&getbouncetime()<=0&&getslam()<=0&& getseamine()<=0;
    }

    public boolean canmeleejump(){

        return getjumpgrab()<=0&&getroar()<=0&&
    getboomerangtime()<=0&& getwhirlpool()<=0&&getBurrow()<=0&&
        getbouncetime()<=0&&getslam()<=0&&getseamine()<=0;
    }

    @Override
    public void aiStep() {

        if (getsummontime()<50){
            setsummontime(getsummontime()+1);
        }


        if (getsummontime()>=50){


                setBurrow(getBurrow()-1);


   setjumpgrab(getjumpgrab()-1);


          setboomerangtime(getboomerangtime()-1);


            setRoar(getroar()-1);



           setbouncetime(getbouncetime()-1);



          setslam(getslam()-1);



        setwhirlpool(getwhirlpool()-1);

setseamine(getseamine()-1);



       if (isPowered()&&random.nextInt(15)==3){
           for(int i = 0; i < 10; ++i) {
               this.level().addParticle(ParticleTypes.ENCHANTED_HIT, this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5), 0.0, 0.0, 0.0);
               this.level().addParticle(ParticleTypes.FALLING_OBSIDIAN_TEAR, this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5), 0.0, 0.0, 0.0);
           }
       }
this.bossEvent.setProgress(this.getHealth()/this.getMaxHealth());}
        super.aiStep();
    }

    protected @NotNull PathNavigation createNavigation(@NotNull Level pLevel) {
        return new NoSpinAminaphinnavigation(this, pLevel);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.EXPLOSION)||this.getsummontime()<50||source.is(DamageTypes.LAVA)|| source.is(DamageTypes.DROWN)||source.is(DamageTypes.IN_WALL)||source.is(DamageTypes.FALL)||super.isInvulnerableTo(source);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
controllers.add(new AnimationController<>(this,
        "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Omnidens> omnidensAnimationState) {

        if (getsummontime()<50){
            omnidensAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.omnidens.summon", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }


        if (this.getHealth()<=0.01){
            omnidensAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.omnidens.death", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if (getanimation()==4){
            omnidensAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.omnidens.slam", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }


        if (getanimation()==5&&this.getgrip()){
            omnidensAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.omni.grab", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if (this.getleap()&&getanimation()==0){
            omnidensAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.omni.jumpbite", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (getanimation()==6){
            omnidensAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.omni.roar", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (getanimation()==7){
            omnidensAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.omni.shoot", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (getanimation()==8){
            omnidensAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.omni.gesyer", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (getanimation()==9&&this.getnonvisble()){
            omnidensAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.omni.burrow", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }


        if (omnidensAnimationState.isMoving()){
            omnidensAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.omni.walks", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!omnidensAnimationState.isMoving()){
            omnidensAnimationState.getController().setAnimation(RawAnimation.begin().then("omni.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        return PlayState.STOP;
    }

    public int getboomerangtime(){
        return this.entityData.get(BOOMERANGATTACK);
    }
    public void setboomerangtime(int bommerangtime){
        this.entityData.set(BOOMERANGATTACK,bommerangtime);
    }

    public int getbouncetime(){
        return this.entityData.get(BOUNCE);
    }
    public void setbouncetime(int bounce){
        this.entityData.set(BOUNCE,bounce);
    }

    public int getsummontime(){
        return this.entityData.get(SUMMON);
    }
    public void setsummontime(int summontime){
        this.entityData.set(SUMMON,summontime);
    }

    public int getroar(){
        return this.entityData.get(ROAR);
    }
    public void setRoar(int roar){
        this.entityData.set(ROAR,roar);
    }

    public int getBurrow(){
        return this.entityData.get(BURROW);
    }
    public void setBurrow(int burrow){
        this.entityData.set(BURROW,burrow);
    }

    public boolean getleap(){
        return this.entityData.get(LEAP);
    }
    public void setleap(boolean leap){
        this.entityData.set(LEAP,leap);
    }

    public boolean getgrip(){
        return this.entityData.get(GRIP);
    }
    public void setgrip(boolean grip){
        this.entityData.set(GRIP,grip);
    }

    public boolean getnonvisble(){
        return this.entityData.get(NONVISBLE);
    }
    public void setnonvisble(boolean grip){
        this.entityData.set(NONVISBLE,grip);
    }
    public int getwhirlpool(){
        return this.entityData.get(WHIRLPOOL);
    }
    public void setwhirlpool(int whirlpool){
        this.entityData.set(WHIRLPOOL,whirlpool);
    }

    public int getjumpgrab(){
        return this.entityData.get(LEAPGRAB);
    }
    public void setjumpgrab(int leapgrab){
        this.entityData.set(LEAPGRAB,leapgrab);
    }

    public int getslam(){
        return this.entityData.get(SLAM);
    }
    public void setslam(int slam){
        this.entityData.set(SLAM,slam);
    }

    public int getseamine(){
        return this.entityData.get(SEAMINE);
    }
    public void setseamine(int sea){
        this.entityData.set(SEAMINE,sea);
    }

    public int getanimation(){
        return this.entityData.get(ANIMATION);
    }
    public void setanimation(int animation){
        this.entityData.set(ANIMATION,animation);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setboomerangtime(compound.getInt("boomerang"));
        this.setRoar(compound.getInt("roar"));
        this.setBurrow(compound.getInt("burrow"));
        this.setleap(compound.getBoolean("leap"));
        this.setgrip(compound.getBoolean("grip"));
        this.setnonvisble(compound.getBoolean("nonvisble"));
        this.setjumpgrab(compound.getInt("jumpgrab"));
        this.setwhirlpool(compound.getInt("whirlpool"));
        this.setsummontime(compound.getInt("summon"));
        this.setbouncetime(compound.getInt("bounce"));
        this.setslam(compound.getInt("slam"));
        this.setseamine(compound.getInt("seamine"));
        this.setanimation(compound.getInt("animation"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("boomerang",this.getboomerangtime());
        compound.putInt("roar",this.getroar());
        compound.putInt("burrow",this.getBurrow());
        compound.putBoolean("leap",this.getleap());
        compound.putBoolean("grip",this.getgrip());
        compound.putBoolean("nonvisble",this.getnonvisble());
        compound.putInt("jumpgrab",this.getjumpgrab());
        compound.putInt("whirlpool",this.getwhirlpool());
        compound.putInt("summon",this.getsummontime());
        compound.putInt("bounce",this.getbouncetime());
        compound.putInt("slam",this.getslam());
        compound.putInt("seamine",this.getseamine());
        compound.putInt("animation",this.getanimation());
    }

    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BOOMERANGATTACK,0);
        builder.define(ROAR,0);
        builder.define(BURROW,0);
        builder.define(LEAP,false);
        builder.define(GRIP,false);
        builder.define(NONVISBLE,false);
        builder.define(LEAPGRAB,0);
        builder.define(WHIRLPOOL,0);
        builder.define(SUMMON,0);
        builder.define(BOUNCE,0);
        builder.define(SLAM,0);
        builder.define(SEAMINE,0);
        builder.define(ANIMATION,0);
    }

    public boolean addEffect(MobEffectInstance effectInstance, @Nullable Entity entity) {
        return false;
    }
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    public void positionRider(Entity passenger, Entity.MoveFunction moveFunc){
     float theta= this.yHeadRot*Mth.DEG_TO_RAD;
     float x_rotation=4*Mth.cos(theta+Mth.HALF_PI);
     float z_rotation=4*Mth.sin(theta+Mth.HALF_PI);
     float x_postion= (float) (this.getX()+x_rotation);
     float z_postion= (float) (this.getZ()+z_rotation);

        ejection++;
moveFunc.accept(passenger,x_postion,this.getY(),z_postion);



if (ejection==20){
   this.ejectPassengers();
   ejection=0;
}

   }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        this.bossEvent.addPlayer(serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
