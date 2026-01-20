package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.datagen.Moraydamagetags;
import com.moray.moraymobs.registries.Itemregististeries;
import com.moray.moraymobs.registries.Mobregistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class Pawpawbomb extends ThrowableItemProjectile {


    public Pawpawbomb(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }


    public Pawpawbomb(Level level,LivingEntity entity) {
        super(Mobregistries.BOMBA.get(), entity,level);
    }


    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            List<Entity> entities=this.level().getEntities(this,this.getBoundingBox().inflate(3));
            this.playSound(SoundEvents.FIREWORK_ROCKET_LARGE_BLAST,1, this.level().getRandom().nextFloat() * 0.1F + 0.9F);


            for (Entity entity1:entities){
                if(entity1 instanceof LivingEntity){
                    int i = 10;
                    entity1.hurt(this.damageSources().source(Moraydamagetags.PAWPAWBOMB), (float)i);
                   ((LivingEntity) entity1).addEffect(new MobEffectInstance(MobEffects.POISON,
                            100,0));
                    ((LivingEntity) entity1).addEffect(new MobEffectInstance(MobEffects.WITHER,
                            100,0));

                    ((LivingEntity) entity1).addEffect(new MobEffectInstance(MobEffects.CONFUSION,
                            100,0));}
            }
            this.discard();
        }

    }




    @Override
    protected Item getDefaultItem() {
        return Itemregististeries.PAWPAW_BOMB.get();
    }


    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
      //  Entity entity = result.getEntity();
        List<Entity> entities=this.level().getEntities(this,this.getBoundingBox().inflate(3));
        this.playSound(SoundEvents.FIREWORK_ROCKET_LARGE_BLAST,1, this.level().getRandom().nextFloat() * 0.1F + 0.9F);

for (Entity entity1:entities){
if(entity1 instanceof LivingEntity){
        int i = 10;
        entity1.hurt(this.damageSources().thrown(this, this.getOwner()), (float)i);
        ((LivingEntity) entity1).addEffect(new MobEffectInstance(MobEffects.POISON,
                100,0));
           ((LivingEntity) entity1).addEffect(new MobEffectInstance(MobEffects.WITHER,
                   100,0));

           ((LivingEntity) entity1).addEffect(new MobEffectInstance(MobEffects.CONFUSION,
                   100,0));}}}
       }













