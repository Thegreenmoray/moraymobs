package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Bouncy_ball extends AbstractHurtingProjectile implements GeoEntity {

    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(Bouncy_ball.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BOUNCE= SynchedEntityData.defineId(Bouncy_ball.class, EntityDataSerializers.INT);


    public Bouncy_ball(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public Bouncy_ball(Level level) {
        super(Mobregistries.BOUNCE_BALL.get(),level);
    }


    public int gettimer(){
        return this.entityData.get(TIMER);
    }
    public void settimer(int timer){
        this.entityData.set(TIMER,timer);
    }

    public int getbounce(){
        return this.entityData.get(BOUNCE);
    }
    public void setbounce(int bounce){
        this.entityData.set(BOUNCE,bounce);
    }


    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setbounce(compound.getInt("bounce"));
        this.settimer(compound.getInt("timer"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("bounce",this.getbounce());
        compound.putInt("timer",this.gettimer());
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BOUNCE,0);
        builder.define(TIMER,0);
    }


    @Override
    public void tick() {
        super.tick();

        if (gettimer()<60){
          settimer(gettimer()+1);
        }

        if (gettimer()>=60){
            remove(RemovalReason.DISCARDED);
        }


    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        double d0 = (9) / 2.0;
        double d1 = (9) / 2.0;
        double d2 = entity.getX() - d0;
        double d3 = entity.getZ() - d1;
        double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);
        if (entity instanceof LivingEntity entity1&&!(entity1 instanceof Omnidens)){
            entity1.hurt(this.damageSources().generic(),5+getbounce());
            entity1.knockback(2.5,-(d2 / d4*3 ),-(d3 / d4*3 ));
            remove(RemovalReason.DISCARDED);
        }

    }


    protected void onHitBlock(BlockHitResult result) {
        BlockState blockstate = this.level().getBlockState(result.getBlockPos());
        if (blockstate.canOcclude()){
            if (this.horizontalCollision) {
              this.setDeltaMovement(-this.getDeltaMovement().x() * 1.01, this.getDeltaMovement().y(), -this.getDeltaMovement().z() * 1.01);
                 setbounce(getbounce()+1);
            }

            if (this.verticalCollision) {
                this.setDeltaMovement(this.getDeltaMovement().x(), -this.getDeltaMovement().y()*1.01, this.getDeltaMovement().z());
                setbounce(getbounce()+1);
            }

        }


    }



    public boolean isPickable() {
        return false;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
