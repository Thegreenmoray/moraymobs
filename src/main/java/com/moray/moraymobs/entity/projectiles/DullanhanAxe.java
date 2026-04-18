package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.entity.living.monster.Dullahan;
import com.moray.moraymobs.registries.Itemregististeries;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DullanhanAxe extends AbstractArrow implements GeoEntity {
    private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(DullanhanAxe.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ISBEINGCALLEDBACK= SynchedEntityData.defineId(DullanhanAxe.class, EntityDataSerializers.BOOLEAN);
    private boolean dealtDamage;
    private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.defineId(DullanhanAxe.class, EntityDataSerializers.BYTE);
    public int clientSideReturnTridentTickCount;
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);

    public DullanhanAxe(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);

        this.pickup =  Pickup.DISALLOWED;

    }


    public int gettimer(){
        return this.entityData.get(TIMER);
    }
    public void settimer(int timer){
        this.entityData.set(TIMER,timer);
    }

    public boolean iscalledback(){
        return this.entityData.get(ISBEINGCALLEDBACK);
    }
    public void setcalledback(boolean bounce){
        this.entityData.set(ISBEINGCALLEDBACK,bounce);
    }

    private byte getLoyaltyFromItem(ItemStack stack) {
        Level var3 = this.level();
        byte var10000;
        if (var3 instanceof ServerLevel serverlevel) {
            var10000 = (byte) Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverlevel, stack, this), 0, 127);
        } else {
            var10000 = 0;
        }

        return var10000;
    }


    public DullanhanAxe( LivingEntity owner, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(Mobregistries.DULLAHAN_AXE.get(), owner, level, pickupItemStack, firedFromWeapon);
        this.setOwner(owner);
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(pickupItemStack));
        this.pickup = this.getOwner() ==null?Pickup.ALLOWED:this.getOwner() instanceof Player ? Pickup.ALLOWED:Pickup.DISALLOWED;
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setcalledback(compound.getBoolean("calledback"));
        this.settimer(compound.getInt("timer"));
        this.dealtDamage = compound.getBoolean("DealtDamage");
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(this.getPickupItemStackOrigin()));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("calledback",this.iscalledback());
        compound.putInt("timer",this.gettimer());
        compound.putBoolean("DealtDamage", this.dealtDamage);
    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_LOYALTY, (byte) 0);
        builder.define(ISBEINGCALLEDBACK,false);
        builder.define(TIMER,1000);
    }


    private boolean isAcceptibleReturnOwner() {
        Entity entity = this.getOwner();
        return entity != null && entity.isAlive() && (!(entity instanceof ServerPlayer) || !entity.isSpectator());
    }

    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
if (entity instanceof Player) {
  //  int i = (Byte) this.entityData.get(ID_LOYALTY);
    if ( (this.dealtDamage || this.isNoPhysics())) {
        if (!this.isAcceptibleReturnOwner()) {
            if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
                this.spawnAtLocation(this.getPickupItem(), 0.1F);
            }

            this.discard();
        } else {
            this.setNoPhysics(true);
            Vec3 vec3 = entity.getEyePosition().subtract(this.position());
            this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 * (double) 3, this.getZ());
            if (this.level().isClientSide) {
                this.yOld = this.getY();
            }

            double d0 = 0.05 * (double) 3;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3.normalize().scale(d0)));


            ++this.clientSideReturnTridentTickCount;
        }
   }} else if (entity instanceof Dullahan) {
        if ( (this.dealtDamage || this.isNoPhysics())) {
            if (!this.isAcceptibleReturnOwner()) {


                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = entity.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 , this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05;




                    this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3.normalize().scale(d0)));



            }
}}
        super.tick();
    }

    @javax.annotation.Nullable
    protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
        return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
    }

    protected void hitBlockEnchantmentEffects(ServerLevel level, BlockHitResult hitResult, ItemStack stack) {
        Vec3 vec3 = hitResult.getBlockPos().clampLocationWithin(hitResult.getLocation());
        Entity var6 = this.getOwner();
        LivingEntity var10002;
        if (var6 instanceof LivingEntity livingentity) {
            var10002 = livingentity;
        } else {
            var10002 = null;
        }

        EnchantmentHelper.onHitBlock(level, stack, var10002, this, (EquipmentSlot)null, vec3, level.getBlockState(hitResult.getBlockPos()), (p_348680_) -> this.kill());
    }


    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        float f = 15.0F;
        Entity entity1 = this.getOwner();
        DamageSource damagesource = this.damageSources().trident(this, (Entity) (entity1 == null ? this : entity1));
        Level var7 = this.level();
        if (var7 instanceof ServerLevel serverlevel) {
            f = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, f);
        }

        this.dealtDamage = true;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            var7 = this.level();
            if (var7 instanceof ServerLevel serverlevel1) {
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, entity, damagesource, this.getWeaponItem());
            }

            if (entity instanceof LivingEntity livingentity) {
                this.doKnockback(livingentity, damagesource);
                this.doPostHurtEffects(livingentity);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
    }

        @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return new ItemStack(Itemregististeries.DULLAHAN_AXE.asItem());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
}
