package com.moray.moraymobs.entity.living.monster;

import com.moray.moraymobs.entity.projectiles.DullanhanAxe;
import com.moray.moraymobs.entity.projectiles.Icice_projectile;
import com.moray.moraymobs.item.ItemDullahanaxe;
import com.moray.moraymobs.tags.MorayKeys;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
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
      if (source.getDirectEntity() instanceof Projectile projectile){
          if (projectile instanceof SpectralArrow) {
              setcurrentragebuildup(getcurrentragebuildup() - 5);
          } else {
              setcurrentragebuildup(getcurrentragebuildup() + 7);
          }
      }

        if (source.getDirectEntity() instanceof Player player) {
            if(player.getMainHandItem().is(MorayKeys.IS_GOLDEN)){
                setcurrentragebuildup(getcurrentragebuildup()-1);
                super.hurt(source,amount+6);
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
                setcurrentragebuildup(getcurrentragebuildup()+3);
        }

        if (getcurrentrage()<6&&getcurrentragebuildup()<100&&getHealth()/getMaxHealth()>=0.3&&getHealth()/getMaxHealth()<0.6&&random.nextInt(5)==0) {

                setcurrentragebuildup(getcurrentragebuildup()+2);
        }


        if (getcurrentrage()<6&&getcurrentragebuildup()<100&&getHealth()/getMaxHealth()>=0.6&&random.nextInt(5)==0) {

            setcurrentragebuildup(getcurrentragebuildup()+1);
        }



        if (!hasaxe()){
            //set wait time for it
        }





        this.Dullahan_rage.setProgress( (float) this.getcurrentrage() / this.Max_rage);
        super.customServerAiStep();
    }

    @Override
    protected void registerGoals() {
    goalSelector.addGoal(0,new Funnygoal(this));
    goalSelector.addGoal(5,new MarkiplierpunchGoal(this,60));
    goalSelector.addGoal(3,new AxeslashGoal(this,30));
    goalSelector.addGoal(3,new Axethrowgoal(this,25));
    goalSelector.addGoal(3,new AxespinGoal(this,40));


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
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH,200).add(Attributes.FOLLOW_RANGE, 60.0).add(Attributes.MOVEMENT_SPEED, 0.25)
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
        if (this.deathTime == 35 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
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
               DullanhanAxe dullanhanAxe=new DullanhanAxe(this.dullahan,this.dullahan.level(),new ItemStack(new ItemDullahanaxe(new Item.Properties())),new ItemStack(new ItemDullahanaxe(new Item.Properties())));

               dullanhanAxe.setPos(dullahan.getViewVector(1));
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

            return dullahan.getaxethrow()<0&&livingEntity.distanceTo(dullahan)>6&&
                    dullahan.hasaxe()&&dullahan.level().random.nextInt(10)==2;
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
    }

    @Override
    public void tick() {

        LivingEntity livingEntity =dullahan.getTarget();
go-=1;
        if (livingEntity != null){


         if (go>40){
           if (!ismarked){
           jump_vector=getjumpvector();
           ismarked=true;
           }

            dullahan.setNoGravity(true);

            dullahan.moveTo(jump_vector.x,jump_vector.y,jump_vector.z);

         }

         if (go==40){
             postionx= (int) livingEntity.position().x();
             postiony= (int) livingEntity.position().y();
             postionz= (int) livingEntity.position().z();
         }


         if (go<40){
             dullahan.setNoGravity(false);
           //have a static position dont just follow the player
             dullahan.moveTo(postionx,postiony,postionz);

             if (dullahan.position().distanceTo(livingEntity.position())<=0.5){
                 livingEntity.hurt(livingEntity.damageSources().mobAttack(dullahan), 9.0F);
                  stop=true;
             } else if (dullahan.onGround()) {
                 stop=true;
             }

         }


        }





        super.tick();
    }

    @Override
    public void stop() {
        go=0;
        dullahan.setmarkiplierpuch(0);
    }

    private Vec3 getjumpvector() {

      Vec3 vec3 =dullahan.position();
      RandomSource random1 =dullahan.random;
      int extra_y=random1.nextInt(20-10)+10;
      int extra_x=random1.nextInt(10-5)+5;
      int extra_z=random1.nextInt(10-5)+5;
     boolean neg_one= random1.nextBoolean();
     boolean neg_two= random1.nextBoolean();
     float ajustment_x= (float) (neg_one?-vec3.x():vec3.x());
     float ajustment_z= (float) (neg_two?-vec3.z():vec3.z());

        return new Vec3(ajustment_x+extra_x,vec3.y()+extra_y,ajustment_z+extra_z);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null||!livingEntity.isAlive()){
            return false;
        }


        return go>0||stop;
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null){
            return false;
        }


        return dullahan.getmarkiplierpuch()<0&&!dullahan.hasaxe();
    }
}

public static class SoulfireballGoal extends Goal {
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
    }


    @Override
    public void stop() {
        go=0;
    }

    @Override
    public boolean canUse() {

        LivingEntity livingEntity =dullahan.getTarget();

        if (livingEntity == null){
            return false;
        }



     return dullahan.getsoulfireball()<0||(dullahan.getragesoulfireball()<0&&dullahan.getcurrentrage()>=2)&&!dullahan.hasaxe();
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
        super.start();
        go=start;
    }


    public void tick(){
        LivingEntity livingEntity =dullahan.getTarget();
       go--;
        if (livingEntity != null){
           if (go>10){
              dullahan.lookAt((Entity) livingEntity, (float) livingEntity.getY(), (float) livingEntity.getX());
           }
         if (go==10){
            List<Entity> range=dullahan.level().getEntities(this.dullahan,dullahan.getBoundingBox(),entity->dullahan.position().normalize().dot(entity.position().normalize())<=0.25); //75 degrees
            for(Entity e:range){
                if (e instanceof LivingEntity){
                    livingEntity.hurt(livingEntity.damageSources().mobAttack(dullahan), 11.0F);
                    double d0 = (this.dullahan.getBoundingBox().minX + this.dullahan.getBoundingBox().maxX) / 2.0;
                    double d1 = (this.dullahan.getBoundingBox().minZ + this.dullahan.getBoundingBox().maxZ) / 2.0;
                    double d2 = e.getX() - d0;
                    double d3 = e.getZ() - d1;
                    double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);

                    ((LivingEntity) e).knockback(3,-(d2 / d4 * 4.0),-(d3 / d4 * 4.0));

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


        return dullahan.getaxeslash()>50&&dullahan.hasaxe()&&dullahan.distanceTo(livingEntity)<5;
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

        return (dullahan.getaxespin()<0&&dullahan.getcurrentrage()>=4)&&dullahan.hasaxe()&& dullahan.distanceTo(livingEntity)<5;
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

public static class IceattackGoal extends Goal {
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



            return dullahan.geticeattack()<0||(dullahan.getrageiceattack()<0&&dullahan.getcurrentrage()>=2)&&!dullahan.hasaxe();
    }
}



}
