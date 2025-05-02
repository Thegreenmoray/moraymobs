package com.moray.moraymobs.entity.living.boss;

import com.moray.moraymobs.ai.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class Omnidens extends Monster implements GeoEntity {

    private static final EntityDataAccessor<Integer> BOOMERANGATTACK= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BURROW= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LEAP= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WHIRLPOOL= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ROAR= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LEAPGRAB= SynchedEntityData.defineId(Omnidens.class, EntityDataSerializers.INT);

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossEvent;

    public Omnidens(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);

        this.bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);

    }
    @Override
    public void knockback(double pStrength, double pX, double pZ) {
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    public boolean hurt(DamageSource damageSource, float amount){
        if (damageSource.is(DamageTypes.ARROW)){
            amount= (float) (amount*.8);
        }else if(damageSource.is(DamageTypes.EXPLOSION)){
            amount= (float) (amount*.3);
        } else if (damageSource.is(DamageTypes.TRIDENT)) {
          amount = (float) (amount*1.1);
        }


        return amount>30? super.hurt(damageSource,30):super.hurt(damageSource,amount);
    }

    public static AttributeSupplier.Builder createMonsterAttributes() {
        return Monster.createMobAttributes().add(Attributes.ATTACK_DAMAGE,10).add(Attributes.MAX_HEALTH,600)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY,0.4f).add(Attributes.ARMOR,15f)
                .add(Attributes.MOVEMENT_SPEED,0.4f).add(Attributes.FOLLOW_RANGE,20f);
    }
    

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3,new Omnidenprojectilegoal(this,50));
       // this.goalSelector.addGoal(7,new Burrowgoal(this,300));
       // this.goalSelector.addGoal(4,new Omnidenleapgoal(this,80));
        //this.goalSelector.addGoal(5,new Roaromnidensgoal(this,50));
        //this.goalSelector.addGoal(6,new Whirlpoolgoal(this,70));
       // this.goalSelector.addGoal(8,new OmnidensJumpattackgoal(this,0.5,false));

    }

    @Override
    public void aiStep() {
        super.aiStep();

        //setleap(getleap()+1);
       setboomerangtime(getboomerangtime()+1);
        //setwhirlpool(getwhirlpool()+1);
       // setjumpgrab(getjumpgrab()+1);
       // setleap(getleap()+1);
       // setRoar(getroar()+1);




        grabb();

bossEvent.setProgress(this.getHealth()/this.getMaxHealth());

    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.LAVA)|| source.is(DamageTypes.DROWN)||source.is(DamageTypes.FALL)||super.isInvulnerableTo(source);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
controllers.add(new AnimationController<>(this,
        "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Omnidens> omnidensAnimationState) {



        return PlayState.STOP;
    }

    public int getboomerangtime(){
        return this.entityData.get(BOOMERANGATTACK);
    }
    public void setboomerangtime(int bommerangtime){
        this.entityData.set(BOOMERANGATTACK,bommerangtime);
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

    public int getleap(){
        return this.entityData.get(LEAP);
    }
    public void setleap(int leap){
        this.entityData.set(ROAR,leap);
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

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setboomerangtime(compound.getInt("boomerang"));
        this.setRoar(compound.getInt("roar"));
        this.setBurrow(compound.getInt("burrow"));
        this.setleap(compound.getInt("leap"));
        this.setjumpgrab(compound.getInt("jumpgrab"));
        this.setwhirlpool(compound.getInt("whirlpool"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("boomerang",this.getboomerangtime());
        compound.putInt("roar",this.getroar());
        compound.putInt("burrow",this.getBurrow());
        compound.putInt("leap",this.getleap());
        compound.putInt("jumpgrab",this.getjumpgrab());
        compound.putInt("whirlpool",this.getwhirlpool());
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BOOMERANGATTACK,0);
        builder.define(ROAR,0);
        builder.define(BURROW,0);
        builder.define(LEAP,0);
        builder.define(LEAPGRAB,0);
        builder.define(WHIRLPOOL,0);
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

    public void grabb(){
   LivingEntity livingEntity =this.getTarget();

   if (livingEntity!=null){

       float f1 = Mth.cos(this.yBodyRot * ((float)Mth.DEG_TO_RAD)) ;
       float f2 = Mth.sin(this.yBodyRot * ((float)Mth.DEG_TO_RAD)) ;

       float theta=(yBodyRot*(Mth.DEG_TO_RAD))+(Mth.HALF_PI);

       float sin=Mth.cos(theta);
       float cos=Mth.sin(theta);


       float x= (float) (this.getX()+10*sin+f1);
       float z= (float) (this.getZ()+10*cos+f2);
livingEntity.setPos(x,this.getY(),z);
        if (livingEntity.isShiftKeyDown()) {
            livingEntity.setShiftKeyDown(false);
        }

       if(this.getPassengers().isEmpty()){
           if (!this.level().isClientSide) {
               livingEntity.startRiding(this, true);
           }

   }

    }}


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
