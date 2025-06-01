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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class Sea_Mine extends AbstractHurtingProjectile implements GeoEntity {
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(Sea_Mine.class, EntityDataSerializers.INT);



    public Sea_Mine(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }
    public Sea_Mine(Level level) {
        super(Mobregistries.SEA_MINE.get(),level);
    }

    public boolean isPickable() {
        return false;
    }


    public int gettimer(){
        return this.entityData.get(TIMER);
    }
    public void settimer(int timer){
        this.entityData.set(TIMER,timer);
    }


    @Override
    public void tick() {

        if (gettimer()<120){
          settimer(gettimer()+1);
        }


        List<Entity> entityList=level().getEntities(this,this.getBoundingBox());
        for (Entity entity:entityList){
            if (entity instanceof LivingEntity entity1&&!(entity1 instanceof Omnidens omnidens)){
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 7, false, Level.ExplosionInteraction.MOB);
                remove(RemovalReason.DISCARDED);
                return;
            }
        }

        if (gettimer()>=121){
            remove(RemovalReason.DISCARDED);
        }


    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.settimer(compound.getInt("timer"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("timer",this.gettimer());
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TIMER,0);
    }





    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
