package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.entity.living.monster.Dullahan;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.client.renderer.entity.layers.PandaHoldsItemLayer;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class DullanhanAxe extends AbstractArrow {
    private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(DullanhanAxe.class, EntityDataSerializers.INT);



    public DullanhanAxe(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);

        this.pickup =  Pickup.DISALLOWED;

    }

    public DullanhanAxe( LivingEntity owner, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(Mobregistries.DULLAHAN_AXE.get(), owner, level, pickupItemStack, firedFromWeapon);
        this.setOwner(owner);

        this.pickup = this.getOwner() ==null?Pickup.ALLOWED:this.getOwner() instanceof Player ? Pickup.ALLOWED:Pickup.DISALLOWED;
    }






    @Override
    protected ItemStack getDefaultPickupItem() {
        return null;
    }
}
