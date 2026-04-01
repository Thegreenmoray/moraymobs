package com.moray.moraymobs.entity.projectiles;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Soulfireball extends Fireball {

    private static final EntityDataAccessor<Boolean> RAGED= SynchedEntityData.defineId(Soulfireball.class, EntityDataSerializers.BOOLEAN);



    public Soulfireball(EntityType<? extends Fireball> entityType, Level level) {
        super(entityType, level);
    }


    public Soulfireball(Level level, LivingEntity owner, Vec3 movement, boolean inrage) {
        super(EntityType.FIREBALL, owner, movement, level);
        this.setrage(inrage);

    }

    public boolean israge(){
        return this.entityData.get(RAGED);
    }
    public void setrage(boolean rage){
        this.entityData.set(RAGED,rage);
    }


    /**
     * Called when this EntityFireball hits a block or entity.
     */
    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            //may want to look into EventHooks later
            boolean flag = net.neoforged.neoforge.event.EventHooks.canEntityGrief(this.level(), this.getOwner());
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), israge()? 7.0f:3.0f, flag, Level.ExplosionInteraction.MOB);
            this.discard();
        }
    }

    /**
     * Called when the arrow hits an entity
     */
    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level() instanceof ServerLevel serverlevel) {
            Entity entity1 = result.getEntity();
            Entity $$4 = this.getOwner();
            DamageSource $$5 = this.damageSources().fireball(this, $$4);
            entity1.hurt($$5, israge() ?15:4);
            EnchantmentHelper.doPostAttackEffects(serverlevel, entity1, $$5);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.setrage(compound.getBoolean("rage"));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        compound.putBoolean("rage",this.israge());
    }


    @Override
    public boolean isPickable() {
        return !israge();
    }
}
