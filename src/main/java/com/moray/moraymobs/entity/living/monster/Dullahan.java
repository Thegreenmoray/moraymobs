package com.moray.moraymobs.entity.living.monster;

import com.moray.moraymobs.entity.projectiles.DullanhanAxe;
import com.moray.moraymobs.entity.projectiles.Icice_projectile;
import com.moray.moraymobs.entity.projectiles.Soulfireball;
import com.moray.moraymobs.item.ItemDullahanaxe;
import com.moray.moraymobs.registries.Itemregististeries;
import com.moray.moraymobs.tags.MorayKeys;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

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

    private final ServerBossEvent Dullahan_rage= (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.NOTCHED_6)).setDarkenScreen(true);
final int Max_rage=6;
    static boolean ismarked=false;
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
          }

      if (source.getDirectEntity() instanceof IronGolem){
          setcurrentragebuildup(getcurrentragebuildup() + 7);
          return super.hurt(source, (float) (amount*0.80));
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
        return source.is(DamageTypes.FALL)||source.is(DamageTypes.ON_FIRE)||source.is(DamageTypes.FREEZE)||super.isInvulnerableTo(source);
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

        if(this.getaxethrow()>30&&0<this.getaxethrow()){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("throwaxe", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (!hasaxe()&&getaxewait()<=-150){
            dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("retrevie_axe", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

    if (this.getmarkiplierpuch()<75&&this.getmarkiplierpuch()>0){
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


       // if(this.getaxeslash()>0&&hasaxe()){
       //     dullahanAnimationState.getController().setAnimation(RawAnimation.begin().then("axe_slash", Animation.LoopType.HOLD_ON_LAST_FRAME));
       //     return PlayState.CONTINUE;
       // }

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

    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        if (!this.tposing && name != null && (name.getString().equalsIgnoreCase("Tpose")||name.getString().equalsIgnoreCase("Dominance"))) {
            this.tposing = true;
        }

    }

    @Override
    protected void customServerAiStep() {


        if (getcurrentrage()<6&&getcurrentragebuildup()>=100) {
            setcurrentrage(getcurrentrage()+1);
            setcurrentragebuildup(0);
        }


        if (getcurrentrage()<6&&getcurrentragebuildup()<100&&getHealth()/getMaxHealth()<0.3&&random.nextInt(5)==0) {
                setcurrentragebuildup(getcurrentragebuildup()+4);
        }

        if (getcurrentrage()<6&&getcurrentragebuildup()<100&&getHealth()/getMaxHealth()>=0.3&&getHealth()/getMaxHealth()<0.6&&random.nextInt(5)==0) {

                setcurrentragebuildup(getcurrentragebuildup()+3);
        }


        if (getcurrentrage()<6&&getcurrentragebuildup()<100&&getHealth()/getMaxHealth()>=0.6&&random.nextInt(5)==0) {

            setcurrentragebuildup(getcurrentragebuildup()+2);
        }

      //  setmarkiplierpuch(getmarkiplierpuch()-1);



        //      seticeattack(geticeattack()-1);
      //  setrageiceattack(getrageiceattack()-1);

      if(getcurrentrage()>2){
      //  setaxespin(getaxespin()-1);
      }
   //     setragefireball(getragesoulfireball()-1);
    //    setrageiceattack(getrageiceattack()-1);


      //  setaxeslash(getaxeslash()-1);



       setaxethrow(getaxethrow()-1);





        if (!hasaxe()){
            //set wait time for it
            setaxewait(getaxewait()-1);
        }





        this.Dullahan_rage.setProgress( (float) this.getcurrentrage() / this.Max_rage);
        super.customServerAiStep();
    }

    @Override
    protected void registerGoals() {
    goalSelector.addGoal(0, new FloatGoal(this));
   // goalSelector.addGoal(0,new Funnygoal(this));
        //done
        //   goalSelector.addGoal(5,new MarkiplierpunchGoal(this,25));
        //done
        //   goalSelector.addGoal(3,new AxeslashGoal(this,25));
   //doneish
    //goalSelector.addGoal(3,new Axethrowgoal(this,20));

    goalSelector.addGoal(3,new AxespinGoal(this,40));

   // goalSelector.addGoal(3,new SoulfireballGoal(this,45));
  //  goalSelector.addGoal(3,new IceattackGoal(this,30));
    targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    targetSelector.addGoal(1, new HurtByTargetGoal(this));
    goalSelector.addGoal(2,new Dullahanmove(this,0.5,true));
  //done
        // goalSelector.addGoal(3,new getaxebackgoal(this,30));
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
                .add(Attributes.ATTACK_DAMAGE, 15.0).add(Attributes.KNOCKBACK_RESISTANCE,20)
                .add(Attributes.ARMOR,5);
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



public static class Funnygoal extends Goal {
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

private static class Axethrowgoal extends Goal {
Dullahan dullahan;
int time;
int go;
float x_diff=0;
float y_diff=0;
float z_diff=0;

        public Axethrowgoal(Dullahan dullahan,int time) {
       this.dullahan = dullahan;
       this.time=time;
        }

    @Override
    public void stop() {
      if (go <=15){
          this.dullahan.sethasaxe(false);
      }
        go=0;
        x_diff=0;
        y_diff=0;
        z_diff=0;
        this.dullahan.setaxethrow(0);

    }

    @Override
        public void start() {
            go=time;
        this.dullahan.setaxethrow(50);
        }

        @Override
        public void tick() {
            super.tick();
           go--;
            LivingEntity livingEntity =dullahan.getTarget();

            if (livingEntity != null){
             if(go>15){
              x_diff= (float) (livingEntity.getX()-dullahan.getX());
            y_diff = (float) (livingEntity.getY(0.3333333333333333));
              z_diff= (float) (livingEntity.getZ()-dullahan.getZ());

             }

           if (go==15){                                                                                              //replace with the registry call
               DullanhanAxe dullanhanAxe=new DullanhanAxe(this.dullahan,this.dullahan.level(), Itemregististeries.DULLAHAN_AXE.toStack(),null);
               Vec3 vec3=dullahan.getViewVector(1);
               dullanhanAxe.setPos(this.dullahan.getX() + vec3.x * 3.0, this.dullahan.getY(0.5) + 0.5, this.dullahan.getZ() + vec3.z * 3.0);
              y_diff= (float) (y_diff-dullanhanAxe.getY());
               double d3 = Math.sqrt(x_diff * x_diff + z_diff * z_diff);
               dullanhanAxe.shoot(x_diff,y_diff+d3*0.4,z_diff,1.6F, (float)(14 - dullahan.level().getDifficulty().getId() * 4));
               this.dullahan.level().addFreshEntity(dullanhanAxe);

           }



            }



        }

    @Override
    public boolean canContinueToUse() {

        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null||!livingEntity.isAlive()){
            return false;
        }

        return go>0;
    }

    @Override
        public boolean canUse() {
     LivingEntity livingEntity =dullahan.getTarget();

     if (livingEntity == null){
         return false;
     }

            return dullahan.getaxethrow()<-40 //-100?
                    //&& livingEntity.distanceTo(dullahan)>6
                 //   &&dullahan.level().random.nextInt(10)==2
           &&dullahan.hasaxe() &&dullahan.getmarkiplierpuch()<=0&&dullahan.getaxeslash()<=0;
        }
    }

private static class MarkiplierpunchGoal extends Goal {
        Dullahan dullahan;
    int time;
    int go;
    //nevermind just use isalive();
float postionx=0;
float postiony=0;
float postionz=0;
Vec3 jump_vector;
boolean stop;
        public MarkiplierpunchGoal(Dullahan dullahan,int time) {
        this.dullahan = dullahan;
        this.time=time;
    }

    @Override
    public void start() {
        go=time;
        dullahan.setmarkiplierpuch(100);
        stop=false;
        ismarked=false;
    }

    @Override
    public void tick() {

        LivingEntity livingEntity =dullahan.getTarget();
         go--;
        if (livingEntity != null){


         if (go>20){
           if (!ismarked){
           jump_vector=getjumpvector();
           ismarked=true;
           }



          dullahan.setDeltaMovement(jump_vector.x,jump_vector.y,jump_vector.z);

         }

         if (go==15){
             postionx= (int) livingEntity.position().x();
             postiony= (int) livingEntity.position().y();
             postionz= (int) livingEntity.position().z();
         }


         if (go<15){

           //have a static position dont just follow the player
             dullahan.moveTo(postionx,postiony,postionz);

             if (go<=0){
                 stop=true;
             }


             if (dullahan.position().distanceTo(new Vec3(postionx,postiony,postionz))<=1.5){
                 livingEntity.hurt(livingEntity.damageSources().mobAttack(dullahan), 9.0F);

             }





         }


        }





        super.tick();
    }

    @Override
    public void stop() {
        go=0;
        dullahan.setmarkiplierpuch(0);
        stop=true;
        ismarked=true;
    }

    private Vec3 getjumpvector() {


      RandomSource random1 =dullahan.random;
      int extra_y=random1.nextInt(10)+10;
      int extra_x=random1.nextInt(5)+5;
      int extra_z=random1.nextInt(5)+5;
     boolean neg_one= random1.nextBoolean();
     boolean neg_two= random1.nextBoolean();
     float ajustment_x= (float) (neg_one?-1:1);
     float ajustment_z= (float) (neg_two?-1:1);

        return new Vec3(ajustment_x*extra_x,extra_y,ajustment_z*extra_z).normalize().scale(1.5);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null||!livingEntity.isAlive()){
            return false;
        }


        return go>0&&!stop;
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null){
            return false;
        }


        int dull=dullahan.getmarkiplierpuch();
        int axe=dullahan.getaxeslash();


        return dull<-40 //set to -90
                //&&!dullahan.hasaxe()
                &&axe<=0
                ;
    }
}

private static class SoulfireballGoal extends Goal {
    Dullahan dullahan;
    int time;
    int go;



        public SoulfireballGoal(Dullahan dullahan,int time) {
   this.dullahan = dullahan;
   this.time=time;
    }

    @Override
    public void start() {
        go=time;
        if ((dullahan.getragesoulfireball() < 0 && dullahan.getcurrentrage() >= 2)) {
            dullahan.setragefireball(100);
        } else {
            dullahan.setsoulfireball(100);
        }

    }


    @Override
    public void stop() {
        if (go<=10&&dullahan.getcurrentrage()>=2&&dullahan.getragesoulfireball()>0){
            dullahan.setcurrentrage(dullahan.getcurrentrage()-2);
            dullahan.setragefireball(0);
        }
       else{
            dullahan.setsoulfireball(0);
        }


        go=0;
    }


    @Override
    public void tick() {
        super.tick();

        LivingEntity livingEntity =dullahan.getTarget();
        go--;
        if (livingEntity != null){



            if (go==15){

                if (dullahan.getcurrentrage()>=2&&dullahan.getragesoulfireball()>0){
                   Soulfireball ice =new Soulfireball(this.dullahan.level(), this.dullahan,this.dullahan.position(),true);

                    ice.setPos(dullahan.getViewVector(1));
                    this.dullahan.level().addFreshEntity(ice);
                } else  {
                    Soulfireball ice =new Soulfireball(this.dullahan.level(), this.dullahan,this.dullahan.position(),false);
                    ice.setPos(dullahan.getViewVector(1));
                    this.dullahan.level().addFreshEntity(ice);
                }

            }
        }
    }

    @Override
    public boolean canContinueToUse() {

        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null||!livingEntity.isAlive()){
            return false;
        }


        return go>0;
    }
    @Override
    public boolean canUse() {

        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null){
            return false;
        }



     return (dullahan.getsoulfireball()<-100||(dullahan.getragesoulfireball()<-200&&dullahan.getcurrentrage()>=2))&&!dullahan.hasaxe();
    }
}

private static class AxeslashGoal extends Goal {

    Dullahan dullahan;
    int start;
    int go;
    public AxeslashGoal(Dullahan dullahan,int start) {
        this.dullahan = dullahan;
        this.start = start;
    }

    @Override
    public void start() {
        go=start;
        dullahan.setaxeslash(50);
    }


    public void tick(){
        LivingEntity livingEntity =dullahan.getTarget();
       go--;
        if (livingEntity != null){
           if (go>10){
              dullahan.lookAt((Entity) livingEntity, (float) livingEntity.getY(), (float) livingEntity.getX());
           }
         if (go==10){
            List<Entity> range=dullahan.level().getEntities(this.dullahan,dullahan.getBoundingBox().inflate(2),e->dullahan.position().normalize().dot(livingEntity.position().normalize())>=0.0&& e instanceof LivingEntity);
            for(Entity e:range){
                if (e instanceof LivingEntity living){
                    living.hurt(living.damageSources().mobAttack(dullahan), living instanceof IronGolem ?25.0f:11.0F);
                    double d0 = (this.dullahan.getBoundingBox().minX + this.dullahan.getBoundingBox().maxX) / 2.0;
                    double d1 = (this.dullahan.getBoundingBox().minZ + this.dullahan.getBoundingBox().maxZ) / 2.0;
                    double d2 = e.getX() - d0;
                    double d3 = e.getZ() - d1;
                    double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

                    living.knockback(3,-(d2 / d4 * 4.0),-(d3 / d4 * 4.0));

                }
            }
         }
        }
    }


    public void stop() {
        super.stop();
        go=0;
        dullahan.setaxeslash(0);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null){
            return false;
        }


        return livingEntity.isAlive()&&go>0;
    }

    @Override
    public boolean canUse() {

        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null){
            return false;
        }


        return dullahan.getaxeslash()<=-25
                &&dullahan.hasaxe()
                &&dullahan.distanceTo(livingEntity)<5
                &&dullahan.getmarkiplierpuch()<0;
    }
}

private static class AxespinGoal extends Goal {
        Dullahan dullahan;
        int start;
        int go;
        public AxespinGoal(Dullahan dullahan,int start) {
            this.dullahan = dullahan;
            this.start = start;
        }


        @Override
        public void start() {
            go=start;
            this.dullahan.setaxespin(150);
        }
        @Override
        public void tick() {
            LivingEntity livingEntity =dullahan.getTarget();
           go--;
            if (livingEntity != null){


              if (15<go) {
                  List<Entity> range = dullahan.level().getEntities(this.dullahan, dullahan.getBoundingBox().inflate(2));
                  for(Entity e:range){
                      if (e instanceof LivingEntity){
                          dullahan.doHurtTarget(e);
                          double d0 = (this.dullahan.getBoundingBox().minX + this.dullahan.getBoundingBox().maxX) / 2.0;
                          double d1 = (this.dullahan.getBoundingBox().minZ + this.dullahan.getBoundingBox().maxZ) / 2.0;
                          double d2 = e.getX() - d0;
                          double d3 = e.getZ() - d1;
                          double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

                          ((LivingEntity) e).knockback(4,-(d2 / d4 * 4.0),-(d3 / d4 * 4.0));

                      }

                  }


              }
            }

        }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null){
            return false;
        }

        return (dullahan.getaxespin()<-200&&dullahan.getcurrentrage()>=3)
                &&dullahan.hasaxe()&& dullahan.distanceTo(livingEntity)<5;
    }


    public void stop() {
            if (go<=10){
                dullahan.setcurrentrage(dullahan.getcurrentrage()-4);
            }
        this.dullahan.setaxespin(0);
            go=0;
    }


    public boolean canContinueToUse() {
            LivingEntity livingEntity =dullahan.getTarget();
        if (livingEntity == null){
            return false;
        }
        return go > 0 && livingEntity.isAlive();
    }



}

private static class IceattackGoal extends Goal {
Dullahan dullahan;
 int time;
 int go;
 float x_diff=0;
float y_diff=0;
 float z_diff=0;


        public IceattackGoal(Dullahan dullahan,int time) {
            this.dullahan = dullahan;
            this.time = time;
        }

    @Override
    public void tick() {
        LivingEntity livingEntity =dullahan.getTarget();
        go--;
        if (livingEntity != null){
            if(go>15){
                x_diff= (float) (livingEntity.getX());
                y_diff = (float) (livingEntity.getY());
                z_diff= (float) (livingEntity.getZ());
            }


            if (go==15){

                if (dullahan.getcurrentrage()>=2&&dullahan.getrageiceattack()<0){
                     Icice_projectile ice =new Icice_projectile(this.dullahan,this.dullahan.level(),true);
                    y_diff= y_diff+10;
                     ice.setPos(x_diff,y_diff,z_diff);
                    this.dullahan.level().addFreshEntity(ice);
                } else if (dullahan.getcurrentrage()<2) {
                    Icice_projectile ice =new Icice_projectile(this.dullahan,this.dullahan.level(),false);
                    y_diff= y_diff+10;
                     ice.setPos(x_diff,y_diff,z_diff);
                    this.dullahan.level().addFreshEntity(ice);
                }

            }


        }


    }

    @Override
    public void start() {
        go=time;
        if ((dullahan.getrageiceattack() < 0 && dullahan.getcurrentrage() >= 2)) {
            dullahan.setrageiceattack(100);
        } else {
            dullahan.seticeattack(100);
        }
    }


    @Override
    public void stop() {
            if (go<=10&&dullahan.getcurrentrage()>=2&&dullahan.getrageiceattack()<0){
                dullahan.setcurrentrage(dullahan.getcurrentrage()-2);
            }
        if (dullahan.getrageiceattack() < 0) {
            dullahan.setrageiceattack(0);
        } else {
            dullahan.seticeattack(0);
        }


        go=0;
    }

    @Override
    public boolean canContinueToUse() {

        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null||!livingEntity.isAlive()){
            return false;
        }


        return go>0;
    }

    @Override
    public boolean canUse() {

        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null){
            return false;
        }



            return dullahan.geticeattack()<-100||(dullahan.getrageiceattack()<-200&&dullahan.getcurrentrage()>=2)&&!dullahan.hasaxe();
    }
}

private class Dullahanmove extends Goal {

        final double speedModifier;
        final boolean followingTargetEvenIfNotSeen;
        Dullahan dullahan;
        private Path path;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;
        private int ticksUntilNextAttack;
        private int failedPathFindingPenalty = 0;
        private boolean canPenalize = false;


        //largely copied from melee attack but with some changes
        public Dullahanmove(Dullahan dullahan, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
            this.speedModifier = pSpeedModifier;
            this.followingTargetEvenIfNotSeen = pFollowingTargetEvenIfNotSeen;
            this.dullahan =dullahan;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }


        @Override
        public void tick() {
            LivingEntity livingentity = this.dullahan.getTarget();
            if (livingentity != null) {
                this.dullahan.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);


                if ((this.followingTargetEvenIfNotSeen || this.dullahan.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.dullahan.getRandom().nextFloat() < 0.05F)) {
                    this.pathedTargetX = livingentity.getX();
                    this.pathedTargetY = livingentity.getY();
                    this.pathedTargetZ = livingentity.getZ();
                    this.ticksUntilNextPathRecalculation = 4 + this.dullahan.getRandom().nextInt(7);
                    double d0 = this.dullahan.distanceToSqr(livingentity);
                    if (this.canPenalize) {
                        this.ticksUntilNextPathRecalculation += this.failedPathFindingPenalty;
                        if (this.dullahan.getNavigation().getPath() != null) {
                            Node finalPathPoint = this.dullahan.getNavigation().getPath().getEndNode();
                            if (finalPathPoint != null && livingentity.distanceToSqr((double)finalPathPoint.x, (double)finalPathPoint.y, (double)finalPathPoint.z) < 1.0) {
                                this.failedPathFindingPenalty = 0;
                            } else {
                                this.failedPathFindingPenalty += 10;
                            }
                        } else {
                            this.failedPathFindingPenalty += 10;
                        }
                    }

                    if (d0 > 1024.0) {
                        this.ticksUntilNextPathRecalculation += 10;
                    } else if (d0 > 256.0) {
                        this.ticksUntilNextPathRecalculation += 5;
                    }

                    if (!this.dullahan.getNavigation().moveTo(livingentity, this.speedModifier)) {
                        this.ticksUntilNextPathRecalculation += 15;
                    }

                    this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
                }
                this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);


            }




        }




        public boolean canUse() {

            if (dullahan.getrageiceattack()>0||dullahan.geticeattack()>0
            ||dullahan.getragesoulfireball()>0||dullahan.getsoulfireball()>0
            || dullahan.getaxethrow()>0 ||dullahan.getmarkiplierpuch()>0
            || dullahan.getaxespin()>0||dullahan.getaxeslash()>0
            ) {
                return false;
            }





            LivingEntity livingentity = this.dullahan.getTarget();
            if (livingentity == null) {
                return false;}

                if (!livingentity.isAlive()) {
                    return false;
                } else {
                    this.path = this.dullahan.getNavigation().createPath(livingentity, 0);
                    if (this.path != null) {
                        return true;
                    }
                    else {
                        return this.getAttackReachSqr(livingentity) > 4.5;
                    }}

        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = this.dullahan.getTarget();
            if (dullahan.getrageiceattack()>0||dullahan.geticeattack()>0
                    ||dullahan.getragesoulfireball()>0||dullahan.getsoulfireball()>0
                    || dullahan.getaxethrow()>0 ||dullahan.getmarkiplierpuch()>0
                    || dullahan.getaxespin()>0||dullahan.getaxeslash()>0
            ) {
                return false;
            }

            if (livingentity == null) {
                return false;}



                if (!livingentity.isAlive()) {
                    return false;
                } else if (!this.followingTargetEvenIfNotSeen) {
                    return !this.dullahan.getNavigation().isDone();
                } else if (!this.dullahan.isWithinRestriction(livingentity.blockPosition())) {
                    return false;
                } else {
                    return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
                }

        }

        public void start() {

            this.dullahan.getNavigation().moveTo(this.path, this.speedModifier);
            this.dullahan.setAggressive(true);
            this.ticksUntilNextPathRecalculation = 0;
            this.ticksUntilNextAttack = 0;
        }

        public void stop() {
            LivingEntity livingentity = this.dullahan.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.dullahan.setTarget((LivingEntity)null);
            }
            this.dullahan.setAggressive(false);
            this.dullahan.getNavigation().stop();
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        protected double getAttackReachSqr(LivingEntity pAttackTarget) {
            return (double)(this.dullahan.getBbWidth() * this.dullahan.getBbWidth() + pAttackTarget.getBbWidth());
        }
    }

private class getaxebackgoal extends Goal{
Dullahan dullahan;
int time;
int go;
    public getaxebackgoal(Dullahan dullahan,int time) {
        this.dullahan = dullahan;
        this.time = time;
    }

    @Override
    public boolean canContinueToUse() {
        return !hasaxe()&&go>0;
    }
    @Override
    public void start() {
     go=time;
    }

    @Override
    public void stop() {
        dullahan.sethasaxe(true);
        go=0;
        setaxewait(0);
    }

    @Override
    public void tick() {
       super.tick();
       go--;
    }

    @Override
    public boolean canUse() {

        return !hasaxe()&&getaxewait()<-150;
    }
}
}
