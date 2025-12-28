package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Treebeam extends AbstractHurtingProjectile implements GeoEntity {
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);


    private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(Treebeam.class, EntityDataSerializers.INT);


    public int gettimer(){
        return this.entityData.get(TIMER);
    }
    public void settimer(int timer){
        this.entityData.set(TIMER,timer);
    }


    public Treebeam(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }



    public Treebeam(Level level){

        super(Mobregistries.TREE_BEAM.get(),level);
    }

    @Override
    public void tick() {
        super.tick();

        settimer(gettimer()+1);


        if(gettimer()>=130){
            this.remove(RemovalReason.DISCARDED);

        }



    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
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

}
