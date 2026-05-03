package com.moray.moraymobs.entity.living.monster;

import com.moray.moraymobs.ai.monstergoals.dullahangoals.*;
import com.moray.moraymobs.tags.MorayKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class Dullahan extends Monster implements GeoEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> RAGE_BUILDUP= SynchedEntityData.defineId(Dullahan.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> RAGE= SynchedEntityData.defineId(Dullahan.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AXE_THROW=SynchedEntityData.defineId(Dullahan.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> NO_AXE=SynchedEntityData.defineId(Dullahan.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> MARKIPILER_PUNCH=SynchedEntityData.defineId(Dullahan.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AXE_SLASH=SynchedEntityData.defineId(Dullahan.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SOULFIREBALL=SynchedEntityData.defineId(Dullahan.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ICE_ATTACK=SynchedEntityData.defineId(Dullahan.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AXE_WAIT=SynchedEntityData.defineId(Dullahan.class, EntityDataSerializers.INT);
    //----------------------------------RAGE------------------------------------------
    private static final EntityDataAccessor<Integer> RAGE_SOULFIRE=SynchedEntityData.defineId(Dullahan.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AXE_SPIN=SynchedEntityData.defineId(Dullahan.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> RAGE_ICE_ATTACK=SynchedEntityData.defineId(Dullahan.class, EntityDataSerializers.INT);

    private final ServerBossEvent Dullahan_rage= (ServerBossEvent)(new ServerBossEvent(Component.nullToEmpty("Dullahan's Rage"), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.NOTCHED_6)).setDarkenScreen(true);
final int Max_rage=6;

    boolean tposing=false;


    public Dullahan(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward=100;
    }

    public int getaxewait(){
        return this.entityData.get(AXE_WAIT);
    }

    public void setaxewait(int hasaxe){
        this.entityData.set(AXE_WAIT,hasaxe);
    }

    public boolean hasaxe(){
        return this.entityData.get(NO_AXE);
    }

    public void sethasaxe(boolean hasaxe){
        this.entityData.set(NO_AXE,hasaxe);
    }

    public int getcurrentragebuildup(){
        return this.entityData.get(RAGE_BUILDUP);
    }

    public void setcurrentragebuildup(int ragebuildup){
        this.entityData.set(RAGE_BUILDUP,ragebuildup);
    }

    public int getcurrentrage(){
        return this.entityData.get(RAGE);
    }

    public void setcurrentrage(int rage){
        this.entityData.set(RAGE,rage);
    }

    public int getaxethrow(){
        return this.entityData.get(AXE_THROW);
    }

    public void setaxethrow(int axethrow){
        this.entityData.set(AXE_THROW,axethrow);
    }

    public int getmarkiplierpuch(){
        return this.entityData.get(MARKIPILER_PUNCH);
    }

    public void setmarkiplierpuch(int punch){
        this.entityData.set(MARKIPILER_PUNCH,punch);
    }

    public int getaxeslash(){
        return this.entityData.get(AXE_SLASH);
    }

    public void setaxeslash(int axeslash){
        this.entityData.set(AXE_SLASH,axeslash);
    }

    public int geticeattack(){
        return this.entityData.get(ICE_ATTACK);
    }

    public void seticeattack(int iceattack){
        this.entityData.set(ICE_ATTACK,iceattack);
    }

    public int getragesoulfireball(){
        return this.entityData.get(RAGE_SOULFIRE);
    }

    public void setragefireball(int soulfireball){
        this.entityData.set(RAGE_SOULFIRE,soulfireball);
    }

    public int getaxespin(){
        return this.entityData.get(AXE_SPIN);
    }

    public void setaxespin(int axesspin){
        this.entityData.set(AXE_SPIN,axesspin);
    }

    public int getrageiceattack(){
        return this.entityData.get(RAGE_ICE_ATTACK);
    }

    public void setrageiceattack(int iceattack){
        this.entityData.set(RAGE_ICE_ATTACK,iceattack);
    }

    public int getsoulfireball(){
        return this.entityData.get(SOULFIREBALL);
    }

    public void setsoulfireball(int soulfireball){
        this.entityData.set(SOULFIREBALL,soulfireball);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
      if (source.getDirectEntity() instanceof Projectile){
              setcurrentragebuildup(getcurrentragebuildup() + 7);
          return super.hurt(source, amount);
          }

      if (source.getDirectEntity() instanceof IronGolem){
          setcurrentragebuildup(getcurrentragebuildup() + 7);
          return super.hurt(source, (float) (amount*0.60));
      }


        if (source.getDirectEntity() instanceof Player player) {
            if(player.getMainHandItem().is(MorayKeys.IS_GOLDEN)){
                setcurrentragebuildup(getcurrentragebuildup()-1);
                return super.hurt(source,amount+4);
            }
        }


        return super.hurt(source, amount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.PLAYER_EXPLOSION)||source.is(DamageTypes.EXPLOSION)||source.is(DamageTypes.FALL)||
                source.is(DamageTypes.ON_FIRE)||source.is(DamageTypes.FREEZE)
                ||super.isInvulnerableTo(source);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Dullahan> dullahanAnimationState) {

        if (getHealth()<=0.01){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("death_normal", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if(!this.hasaxe()&&this.getrageiceattack()>0){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("rage_ice", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if(!this.hasaxe()&&this.getragesoulfireball()>0){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("rage_soulfire", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if(!this.hasaxe()&&this.geticeattack()>0){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("ice_attack", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }


        if(!this.hasaxe()&&this.getsoulfireball()>0){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("soulfire", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if (this.getaxespin()>25&&this.hasaxe()){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("spinslash", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if (this.getaxespin()<=25&&this.getaxespin()>0&&this.hasaxe()){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("actualspin", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if(this.getaxethrow()>30&&0<this.getaxethrow()){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("throwaxe", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

      //  if (!hasaxe()&&getaxewait()<=-150){
     //       dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("retrevie_axe", Animation.LoopType.HOLD_ON_LAST_FRAME));
      //      return PlayState.CONTINUE;
     //   }

    if (this.getmarkiplierpuch()<75&&this.getmarkiplierpuch()>=1){
        dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("markpilierpunch", Animation.LoopType.HOLD_ON_LAST_FRAME));
        return PlayState.CONTINUE;
    }

        if (this.getmarkiplierpuch()<100&&this.getmarkiplierpuch()>=75){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("markipilerpunchjump", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if(this.getaxeslash()>0&&hasaxe()){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("axe_slash", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if(dullahanAnimationState.isMoving()&&!hasaxe()){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("dull_walk_no_axe", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!dullahanAnimationState.isMoving()&&!hasaxe()){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if(dullahanAnimationState.isMoving()&&hasaxe()){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!dullahanAnimationState.isMoving()&&hasaxe()){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("idle_axe", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    public boolean canusespecial(){

        return getaxethrow()<=0&&getmarkiplierpuch()<=0
                &&getaxespin()<=0
                &&getaxeslash()<=0
                &&geticeattack()<=0
                &&getsoulfireball()<=0
                &&getrageiceattack()<=0
                &&getragesoulfireball()<=0&&getaxewait()>-150;
    }

    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        if (!this.tposing && name != null && (name.getString().equalsIgnoreCase("Tpose")||name.getString().equalsIgnoreCase("Dominance"))) {
            this.tposing = true;
        }

    }

    @Override
    protected void customServerAiStep() {


        if (getcurrentrage()<6&&getcurrentragebuildup()>=100) {
            this.setcurrentrage(getcurrentrage()+1);
            this.setcurrentragebuildup(0);
        }


        if (getcurrentrage()<6&&getcurrentragebuildup()<100&&getHealth()/getMaxHealth()<0.3&&random.nextInt(5)==0) {
            this.setcurrentragebuildup(getcurrentragebuildup()+4);
        }

        if (getcurrentrage()<6&&getcurrentragebuildup()<100&&getHealth()/getMaxHealth()>=0.3&&getHealth()/getMaxHealth()<0.6&&random.nextInt(5)==0) {

            this.setcurrentragebuildup(getcurrentragebuildup()+3);
        }


        if (getcurrentrage()<6&&getcurrentragebuildup()<100&&getHealth()/getMaxHealth()>=0.6&&random.nextInt(5)==0) {

            this.setcurrentragebuildup(getcurrentragebuildup()+2);
        }



        if(this.getaxethrow()<=0&&this.getaxeslash()<=0&&this.getaxespin()<=0
                &&this.geticeattack()<=0&&this.getsoulfireball()<=0&&getragesoulfireball()<=0&&getrageiceattack()<=0&&getaxewait()>-150){
            this.setmarkiplierpuch(getmarkiplierpuch() - 1);}

        if(this.getaxethrow()<=0&&this.getaxeslash()<=0&&this.getaxespin()<=0
                &&this.getsoulfireball()<=0&&getmarkiplierpuch()<=0&&getrageiceattack()<=0&&getragesoulfireball()<=0&&getaxewait()>-150){
            this.seticeattack(geticeattack() - 1);
        }



         if (this.getcurrentrage() > 0&&this.getmarkiplierpuch()<=0&&getaxewait()>-150) {
             if(this.getaxethrow()<=0&&this.getaxeslash()<=0&&this.getaxespin()<=0
                     &&this.geticeattack()<=0&&this.getsoulfireball()<=0&&getrageiceattack()<=0&&getaxewait()>-150){
             setragefireball(getragesoulfireball() - 1);}
             if(this.getaxethrow()<=0&&this.getaxeslash()<=0&&this.getaxespin()<=0
                     &&this.geticeattack()<=0&&this.getsoulfireball()<=0&&getragesoulfireball()<=0&&getaxewait()>-150){
             setrageiceattack(getrageiceattack() - 1);}
         }


         if (this.getcurrentrage() >= 1&&getmarkiplierpuch()<=0&&this.getaxethrow()<=0&&this.getaxeslash()<=0&&this.getaxespin()<=0
                 &&this.geticeattack()<=0&&this.getsoulfireball()<=0&&getrageiceattack()<=0&&getragesoulfireball()<=0&&getaxewait()>-150) {
             this.setaxespin(getaxespin() - 1);

         }
             if(getaxethrow()<=0&&getaxeslash()<=0&&getaxespin()<=0
             &&geticeattack()<=0&&getmarkiplierpuch()<=0&&getrageiceattack()<=0&&getragesoulfireball()<=0&&getaxewait()>-150){
         setsoulfireball(getsoulfireball() - 1);}

        if(getaxethrow()<=0&&geticeattack()<=0&&getaxespin()<=0
                &&getsoulfireball()<=0&&getmarkiplierpuch()<=0&&getrageiceattack()<=0&&getragesoulfireball()<=0&&getaxewait()>-150){
         setaxeslash(getaxeslash() - 1);}

        if(getsoulfireball()<=0&&geticeattack()<=0&&getaxespin()<=0
                &&geticeattack()<=0&&getmarkiplierpuch()<=0&&getrageiceattack()<=0&&getragesoulfireball()<=0){
         setaxethrow(getaxethrow() - 1);}


         if (!hasaxe()&&getmarkiplierpuch()<=0&&this.getaxethrow()<=0&&this.getaxeslash()<=0&&this.getaxespin()<=0
                &&this.geticeattack()<=0&&this.getsoulfireball()<=0&&getrageiceattack()<=0&&getragesoulfireball()<=0) {
             //set wait time for it
             setaxewait(getaxewait() - 1);
         }
         if (getaxewait()==-150){ this.sethasaxe(true);
             this.setaxewait(0);
         }



        this.Dullahan_rage.setProgress( (float) this.getcurrentrage() / this.Max_rage);
        super.customServerAiStep();
    }

    @Override
    protected void registerGoals() {
    goalSelector.addGoal(0, new FloatGoal(this));
   // goalSelector.addGoal(0,new Funnygoal(this));
        //done
    goalSelector.addGoal(2,new MarkiplierpunchGoal(this,23));
        //done
   goalSelector.addGoal(3,new AxeslashGoal(this,25));
   //doneish
    goalSelector.addGoal(5,new Axethrowgoal(this,15));

    goalSelector.addGoal(6,new AxespinGoal(this,30));

   goalSelector.addGoal(4,new SoulfireballGoal(this,20));

  goalSelector.addGoal(4,new IceattackGoal(this,20));
    targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    targetSelector.addGoal(1, new HurtByTargetGoal(this));
    goalSelector.addGoal(1,new Dullahanmove(this,0.5,true));
  //done
      //  goalSelector.addGoal(3,new getaxebackgoal(this,30));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("rage",this.getcurrentrage());
        compound.putInt("axethrow",this.getaxethrow());
        compound.putInt("markiplierpuch",this.getmarkiplierpuch());
        compound.putInt("axeslash",this.getaxeslash());
        compound.putInt("soulfireball",this.getsoulfireball());
        compound.putInt("iceattack",this.geticeattack());
        compound.putInt("axespin",this.getaxespin());
        compound.putInt("ragesoulfireball",this.getragesoulfireball());
        compound.putInt("rageiceattack",this.getrageiceattack());
        compound.putBoolean("hasaxe",this.hasaxe());
        compound.putInt("ragebuildup",this.getcurrentragebuildup());
        compound.putInt("waitforaxe",this.getaxewait());
        this.Dullahan_rage.setName((Component.nullToEmpty("Dullahan's Rage")));
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setaxethrow(compound.getInt("axethrow"));
        this.setcurrentrage(compound.getInt("rage"));
        this.setmarkiplierpuch(compound.getInt("markiplierpuch"));
        this.setaxeslash(compound.getInt("axeslash"));
        this.setsoulfireball(compound.getInt("soulfireball"));
        this.seticeattack(compound.getInt("iceattack"));
        this.setaxespin(compound.getInt("axespin"));
        this.setragefireball(compound.getInt("ragesoulfireball"));
        this.setrageiceattack(compound.getInt("rageiceattack"));
        this.sethasaxe(compound.getBoolean("hasaxe"));
        this.setcurrentragebuildup(compound.getInt("ragebuildup"));
        this.setaxewait(compound.getInt("waitforaxe"));
        this.Dullahan_rage.setName(Component.nullToEmpty("Dullahan's Rage"));


    }

    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(AXE_THROW,0);
        builder.define(RAGE,0);
        builder.define(MARKIPILER_PUNCH,0);
        builder.define(AXE_SLASH,0);
        builder.define(SOULFIREBALL,0);
        builder.define(ICE_ATTACK,0);
        builder.define(RAGE_SOULFIRE,0);
        builder.define(AXE_SPIN,0);
        builder.define(RAGE_ICE_ATTACK,0);
        builder.define(NO_AXE,true);
        builder.define(RAGE_BUILDUP,0);
        builder.define(AXE_WAIT,0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH,200).add(Attributes.FOLLOW_RANGE, 60.0).add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 18.0).add(Attributes.KNOCKBACK_RESISTANCE,20)
                .add(Attributes.ARMOR,10);
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }


    @Override
    protected void tickDeath() {
        ++this.deathTime;


        if ( this.deathTime==1){
           // setanimation(0);
        }
        if (this.deathTime == 55 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }


    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        this.Dullahan_rage.addPlayer(serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.Dullahan_rage.removePlayer(serverPlayer);
    }



public class Funnygoal extends Goal {
       Dullahan dullahan;
        public Funnygoal(Dullahan dullahan) {
         this.dullahan = dullahan;
        }

        @Override
        public boolean canUse() {
            return dullahan.tposing;
        }





        @Override
        public boolean canContinueToUse() {
            return dullahan.tposing;
        }
    }
}
