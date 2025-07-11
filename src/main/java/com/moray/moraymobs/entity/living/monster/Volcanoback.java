package com.moray.moraymobs.entity.living.monster;


import com.moray.moraymobs.ai.monstergoals.Eurptiongoal;
import com.moray.moraymobs.ai.monstergoals.Groundpoundgoal;
import com.moray.moraymobs.ai.monstergoals.VolcanobackMeleeAttackgoal;
import com.moray.moraymobs.ai.monstergoals.VolcanobackMovetowardsGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Volcanoback extends Monster implements GeoEntity {
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> ANIMATION= SynchedEntityData.defineId(Volcanoback.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_ATTACK_TIMER= SynchedEntityData.defineId(Volcanoback.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GROUNDPOUND_TIMER= SynchedEntityData.defineId(Volcanoback.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SLAPCOOLDOWN_TIMER= SynchedEntityData.defineId(Volcanoback.class, EntityDataSerializers.INT);

    public boolean isslashing=false;
    public boolean issmashing=false;

    public int getslapcooldown(){
        return this.entityData.get(SLAPCOOLDOWN_TIMER);
    }

    public void setSlapcooldownTimer(int slap){
        this.entityData.set(SLAPCOOLDOWN_TIMER,slap);
    }


    public int getanimationable(){
        return this.entityData.get(ANIMATION);
    }

    public void setanimationable(int animation){
        this.entityData.set(ANIMATION,animation);
    }

    public int getanimation_timer(){
        return this.entityData.get(ANIMATION_ATTACK_TIMER);
    }

    public void setanimationtimer(int amimationtimer){
        this.entityData.set(ANIMATION_ATTACK_TIMER,amimationtimer);
    }

    public int getgroundpound(){
        return this.entityData.get(GROUNDPOUND_TIMER);
    }

    public void setgroundpound(int groundpound){
        this.entityData.set(GROUNDPOUND_TIMER,groundpound);
    }

    public Volcanoback(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        this.moveControl =new MoveControl(this);
    this.xpReward=50;
    }

    protected @NotNull PathNavigation createNavigation(Level pLevel) {
        return new GroundPathNavigation(this, pLevel);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 100.0).add(Attributes.MOVEMENT_SPEED, 0.3f).add(Attributes.ARMOR,5f).add(Attributes.ATTACK_DAMAGE,12f)
                .add(Attributes.FOLLOW_RANGE,20).add(Attributes.STEP_HEIGHT,1);
    }

    protected void registerGoals() {
    this.goalSelector.addGoal(2,new VolcanobackMovetowardsGoal(this,0.5f,false));
   this.goalSelector.addGoal(2,new VolcanobackMeleeAttackgoal(this,23));
         this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5){
           @Override
           public boolean canUse() {
                return super.canUse()&&this.mob instanceof Volcanoback volcanoback&&volcanoback.getgroundpound()<100&&volcanoback.getslapcooldown()<50;
            }
        });
      this.goalSelector.addGoal(3,new Eurptiongoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
this.goalSelector.addGoal(3, new Groundpoundgoal(this,11));
 }

    @Override
    public void aiStep() {
if (getgroundpound()<100&&isAggressive()&&getslapcooldown()!=50){
     setgroundpound(getgroundpound()+1);}

     if (getslapcooldown()<50&&isAggressive()&&getgroundpound()!=100){
         setSlapcooldownTimer(getslapcooldown()+1);
     }


     if (getgroundpound()>=100){
         isslashing= false;
     }

    if (getanimation_timer()==0&&issmashing){
        setanimationtimer(0);
        setanimationable(0);
        this.issmashing=false;
    }
    if (this.getanimation_timer()>0&&issmashing) {
        this.setanimationtimer(this.getanimation_timer() - 1);
    }
if (this.getanimation_timer()>0&&getgroundpound()==100){
        float circle_radius=Mth.ceil(Mth.PI*4*4);
        for(int i = 0; i < circle_radius; i++) {
            float radian=this.random.nextInt(360)*Mth.DEG_TO_RAD;
            float x_sign= (float) (getX()+5*Mth.sin(radian));
            float z_sign= (float) (getZ()+5*Mth.cos(radian));
            this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, x_sign, this.getY(), z_sign, 0.0, 0.0, 0.0);
        }}


     if (getanimation_timer()==0&&isslashing){
         setanimationtimer(0);
          setanimationable(0);
     }


         if (this.getanimation_timer() > 0&&isslashing) {
             this.setanimationtimer(this.getanimation_timer() - 1);
         }


     if (issmashing&&getanimationable()==0){
         this.setanimationable(2);
     }



     if (isslashing&&getanimationable()==0){
         this.setanimationable(1);
     }




        super.aiStep();
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;


        if ( this.deathTime==1){
            setanimationable(0);
        }
        if (this.deathTime == 60 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }


    public static boolean checkMonsterSpawnRuleschance(EntityType<? extends Monster> pType, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {

        return pLevel.getDifficulty() != Difficulty.PEACEFUL && !pLevel.getBlockState(pPos.below()).is(Blocks.NETHER_WART_BLOCK);
    }

   // public MobType getMobType() {
  //      return MobType.ARTHROPOD;
   // }

    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypeTags.IS_PROJECTILE) || super.isInvulnerableTo(source);
    }

    @Override
    public void knockback(double pStrength, double pX, double pZ) {

    }

    @Override
    public boolean isPushable() {
        return false;
    }

    public boolean hurt(DamageSource damageSource, float amount){

        if (damageSource.is(DamageTypes.EXPLOSION)){
          amount  =  (amount * 0.85f);
          return super.hurt(damageSource,amount);
        }

        return super.hurt(damageSource,amount);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));}

    private PlayState animations(AnimationState<Volcanoback> volcanobackAnimationState) {
        if (this.getHealth()<=0.01){
            setanimationable(0);
            volcanobackAnimationState.getController().setAnimation(RawAnimation.begin().then("volcanoback.death", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if(getanimationable()==2&&getanimation_timer()>0){
            volcanobackAnimationState.getController().setAnimation(RawAnimation.begin().then("pound.volcanoback", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (getanimationable()==1&&getanimation_timer()>0){
            volcanobackAnimationState.getController().setAnimation(RawAnimation.begin().then("swipe.volcanoback", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }


        if (volcanobackAnimationState.isMoving()){
            volcanobackAnimationState.getController().setAnimation(RawAnimation.begin().then("volcanoback.walk", Animation.LoopType.LOOP)); ///volcanoback.walk
            return PlayState.CONTINUE;
        }

        if (!volcanobackAnimationState.isMoving()){
            volcanobackAnimationState.getController().setAnimation(RawAnimation.begin().then("volcanoback.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }

    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANIMATION, 0);
        builder.define(ANIMATION_ATTACK_TIMER,0);
        builder.define(GROUNDPOUND_TIMER,0);
        builder.define(SLAPCOOLDOWN_TIMER,0);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("animation", this.getanimationable());
        pCompound.putInt("animationtimer",this.getanimation_timer());
       pCompound.putInt("groundpound",this.getgroundpound());
        pCompound.putInt("slapcooldown",this.getslapcooldown());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setanimationable(pCompound.getInt("animation"));
        this.setanimationtimer(pCompound.getInt("animationtimer"));
         this.setgroundpound(pCompound.getInt("groundpound"));
   this.setSlapcooldownTimer(pCompound.getInt("slapcooldown"));
    }

}
