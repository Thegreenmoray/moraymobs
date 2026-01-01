package com.moray.moraymobs.entity.living.monster;

import com.moray.moraymobs.ai.monstergoals.AmbergolemMelee;
import com.moray.moraymobs.ai.monstergoals.Ambergolemcrystal;
import com.moray.moraymobs.ai.monstergoals.Ambergolemmove;
import com.moray.moraymobs.ai.monstergoals.Ambergolemsucc;
import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class Amber_golem extends Raider implements GeoEntity {

    private static final EntityDataAccessor<Integer> ANIMATION= SynchedEntityData.defineId(Amber_golem.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SLAM= SynchedEntityData.defineId(Amber_golem.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SUCC= SynchedEntityData.defineId(Amber_golem.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PUNCH= SynchedEntityData.defineId(Amber_golem.class, EntityDataSerializers.INT);



    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    public Amber_golem(EntityType<? extends Raider> entityType, Level level) {
        super(entityType, level);
    }



    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setanimation(compound.getInt("animation"));
        this.setsucc(compound.getInt("succ"));
        this.setslam(compound.getInt("slam"));
        this.setpunch(compound.getInt("punch"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("animation",this.getanimation());
        compound.putInt("succ",this.getsucc());
        compound.putInt("slam",this.getpunch());
        compound.putInt("punch",this.getslam());
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANIMATION,0);
        builder.define(SUCC,0);
        builder.define(SLAM,0);
        builder.define(PUNCH,0);
    }



    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH,80).add(Attributes.FOLLOW_RANGE, 20.0).add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ATTACK_DAMAGE, 9.0).add(Attributes.KNOCKBACK_RESISTANCE,2)
                .add(Attributes.ARMOR,5);
    }

    public int getpunch(){
        return this.entityData.get(PUNCH);
    }
    public void setpunch(int punch){
        this.entityData.set(PUNCH,punch);
    }

    public int getsucc(){
        return this.entityData.get(SUCC);
    }
    public void setsucc(int succ){
        this.entityData.set(SUCC,succ);
    }

    public int getslam(){
        return this.entityData.get(SLAM);
    }
    public void setslam(int slam){
        this.entityData.set(SLAM,slam);
    }

    public int getanimation(){
        return this.entityData.get(ANIMATION);
    }
    public void setanimation(int animation){
        this.entityData.set(ANIMATION,animation);
    }


    @Override
    protected void tickDeath() {
        ++this.deathTime;


        if ( this.deathTime==1){
            setanimation(0);
        }
        if (this.deathTime == 35 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }


    public boolean isready(){
        return getpunch()<=0&&getsucc()<=0&&getslam()<=0;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        return pSource.is(DamageTypes.DROWN) || super.isInvulnerableTo(pSource);
    }



    @Override
    public void tick() {

            setpunch(getpunch()-1);

            setsucc(getsucc()-1);

            setslam(getslam()-1);


            if (getsucc()<=68&&getsucc()>0){
        List<Entity> damage = this.level().getEntities(this, this.getBoundingBox().inflate(10), e ->  e instanceof LivingEntity//&& (!(e instanceof Animal))||e instanceof Player
        );
        for (Entity ent : damage) {
            float x_diff= (float) (ent.getX()-this.getX());
            float z_diff= (float) (ent.getZ()-this.getZ());

            float theta= (float) Mth.atan2(z_diff,x_diff);

            Vec3 pull=ent.position().subtract(this.position().add(1.2*Mth.cos(theta),0,1.2*Mth.sin(theta)));
            pull=pull.normalize().scale(0.07);
            ent.setDeltaMovement(ent.getDeltaMovement().subtract(pull));}}

        super.tick();
    }

    @Override
    protected void registerGoals() {
      this.targetSelector.addGoal(6,new Ambergolemsucc(this));
        this.targetSelector.addGoal(5,new Ambergolemcrystal(this));
       this.targetSelector.addGoal(4,new AmbergolemMelee(this));
        this.targetSelector.addGoal(2,new Ambergolemmove(this,0.5,false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Monster.class, true){

            @Override
            public boolean canUse() {
                return super.canUse()&&!(mob.getTarget()instanceof Raider);
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this){

            @Override
            public boolean canUse() {
                return super.canUse()&&!(mob.getTarget()instanceof Raider);
            }


        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6){
            @Override
            public boolean canUse() {
                return super.canUse()&&!isready();
            }
        });
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
    }

    @Override
    public void applyRaidBuffs(ServerLevel serverLevel, int i, boolean b) {

    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.AMETHYST_BLOCK_CHIME;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,
                "Controller",this::animations));
    }

    private PlayState animations(AnimationState<Amber_golem> amber_golemAnimationState) {


        if (getHealth()<=0.01){
            amber_golemAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.ambergolem.death", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }



        if (getanimation()==1){
            amber_golemAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.ambergolem.punch", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (getanimation()==2){
            amber_golemAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.ambergolem.succspin", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (getanimation()==3){
            amber_golemAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.ambergolem.succ", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if (getanimation()==4){
            amber_golemAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.ambergolem.groundpunch", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }


        if(amber_golemAnimationState.isMoving()){
            amber_golemAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.ambergolem.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (!amber_golemAnimationState.isMoving()){
            amber_golemAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.ambergolem.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
